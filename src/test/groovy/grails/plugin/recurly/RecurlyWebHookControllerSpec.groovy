package grails.plugin.recurly

import com.agorapulse.gru.Gru
import com.agorapulse.gru.grails.Grails
import com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification
import grails.testing.web.controllers.ControllerUnitTest
import org.junit.Rule
import spock.lang.Specification

class RecurlyWebHookControllerSpec extends Specification implements ControllerUnitTest<RecurlyWebHookController> {

    @Rule Gru<Grails<RecurlyWebHookControllerSpec>> gru = Gru.equip(Grails.steal(this)).prepare {
        include RecurlyWebHookUrlMappings
    }

    RecurlyPushNotificationService recurlyPushNotificationService = Mock(RecurlyPushNotificationService)

    void setup() {
        controller.recurlyPushNotificationService = recurlyPushNotificationService
    }

    void 'post web hook payload'() {
        when:
            gru.test {
                post '/recurlyWebHook', {
                     content 'successfulPaymentNotification.xml', 'application/xml'
                }
                expect {
                    status CREATED
                }
            }
        then:
            gru.verify()

            1 * recurlyPushNotificationService.dispatchNotification(_ as SuccessfulPaymentNotification)
    }

    void 'payload is required'() {
        expect:
            gru.test {
                post '/recurlyWebHook'
                expect {
                    status BAD_REQUEST
                }
            }
    }

    void 'post invalid web hook payload'() {
        expect:
            gru.test {
                post '/recurlyWebHook', {
                    content 'invalidTypeNotification.xml', 'application/xml'
                }
                expect {
                    status BAD_REQUEST
                }
            }
    }

    void 'endpoint can be secured'() {
        given:
            config.grails.plugin.recurly.webhook.user = 'Richard'
            config.grails.plugin.recurly.webhook.pass = 'Curly'
        expect:
            gru.test {
                post '/recurlyWebHook'
                expect {
                    status UNAUTHORIZED
                }
            }
    }

    void 'pass the secured endpoint'() {
        given:
            config.grails.plugin.recurly.webhook.user = 'Richard'
            config.grails.plugin.recurly.webhook.pass = 'Curly'
        expect:
            gru.test {
                post '/recurlyWebHook', {
                    headers Authorization: 'Basic UmljaGFyZDpDdXJseQ=='
                    content 'successfulPaymentNotification.xml', 'application/xml'
                }
                expect {
                    status CREATED
                }
            }
    }

    void 'wrong password'() {
        given:
            config.grails.plugin.recurly.webhook.user = 'Richard'
            config.grails.plugin.recurly.webhook.pass = 'Curly'
        expect:
            gru.test {
                post '/recurlyWebHook', {
                    headers Authorization: 'Basic UmljaGFyZDpUaGlyZA=='
                }
                expect {
                    status UNAUTHORIZED
                }
            }
    }

    void 'closed stream'() {
        given:
            config.grails.plugin.recurly.webhook.user = 'Richard'
            config.grails.plugin.recurly.webhook.pass = 'Curly'
        expect:
            gru.test {
                post '/recurlyWebHook', {
                    headers Authorization: 'Basic UmljaGFyZDpDdXJseQ=='
                }
                expect {
                    status INTERNAL_SERVER_ERROR
                }
            }
    }

}
