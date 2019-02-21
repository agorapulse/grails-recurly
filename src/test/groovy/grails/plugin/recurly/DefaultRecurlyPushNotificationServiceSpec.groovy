package grails.plugin.recurly


import com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification
import grails.async.Promises
import grails.events.bus.EventBus
import grails.testing.services.ServiceUnitTest
import org.grails.async.factory.SynchronousPromiseFactory
import spock.lang.Specification

class DefaultRecurlyPushNotificationServiceSpec extends Specification implements ServiceUnitTest<DefaultRecurlyPushNotificationService>{

    EventBus eventBus = Mock(EventBus)

    void "test notification happens"() {
        given:
            service.targetEventBus = eventBus
        when:
            service.dispatchNotification(new SuccessfulPaymentNotification())
        then:
            1 * eventBus.notify('successfulPaymentNotification', *_)
    }

    void 'service is notified'() {
        given:
            defineBeans {
                testWebHookService(DefaultRecurlyWebHookService)
            }

            DefaultRecurlyWebHookService webHookService = applicationContext.getBean(DefaultRecurlyWebHookService)
            Promises.promiseFactory = new SynchronousPromiseFactory()
        when:
            service.dispatchNotification(new SuccessfulPaymentNotification())
        then:
            webHookService.notification instanceof SuccessfulPaymentNotification
    }
}

