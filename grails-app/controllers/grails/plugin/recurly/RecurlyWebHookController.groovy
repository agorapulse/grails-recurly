package grails.plugin.recurly

import grails.plugin.recurly.enums.WebHookResponseType
import grails.plugin.recurly.helpers.WebHookNotification
import grails.plugin.recurly.notifications.RecurlyCanceledSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyChangedSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyExpiredSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyFailedRenewalWebHookNotification
import grails.plugin.recurly.notifications.RecurlyNewSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyRenewedSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlySuccessfulPaymentWebHookNotification
import grails.plugin.recurly.processors.WebHookNotificationProcessor
import org.codehaus.groovy.grails.commons.GrailsApplication

class RecurlyWebHookController {

    static handlerBean
    static defaultAction = 'acceptNotice'

    GrailsApplication grailsApplication

    def beforeInterceptor = {
        if (!handlerBean) {
            log.warn 'No webhook handler defined in the application (RecurlyWebHookService must be created in your app and implement RecurlyWebHookListener)'
            response.status = 500
            render 'Handler Not Defined For the Application'
            return false
        }

        if (config.webhook?.user) {
            def authString = request.getHeader('Authorization')
            if (!authString){
                response.status = 401
                render 'Authentication Failure'
                return false
            }

            def encodedPair = authString - 'Basic '
            def decodedPair =  new String(encodedPair.decodeBase64());
            def credentials = decodedPair.tokenize(':')
            if (!credentials || credentials.first() != config.webhook.user || credentials.last() != config.webhook.pass) {
                response.status = 401
                render 'Authentication Failure'
                return false
            }
        }
    }

    def acceptNotice() {
        log.debug 'Accepting notice...'
        String xml
        try {
            StringBuffer stringBuffer = new StringBuffer()
            BufferedReader bufferedReader = request.getReader()
            bufferedReader.eachLine {
                stringBuffer.append(it)
            }
            xml = stringBuffer.toString()
        } catch (Exception e) {
            response.status = 500
            return
        }
        WebHookNotification webHookNotification = new WebHookNotificationProcessor(xml).process()
        if (webHookNotification) {
            switch (webHookNotification.webHookResponseType) {
                case WebHookResponseType.SUCCESSFUL_PAYMENT_NOTIFICATION:
                    handlerBean.successfulPaymentNotificationHandler(webHookNotification as RecurlySuccessfulPaymentWebHookNotification)
                    break
                case WebHookResponseType.FAILED_RENEWAL_PAYMENT_NOTIFICATION:
                    handlerBean.failedRenewalPaymentNotificationHandler(webHookNotification as RecurlyFailedRenewalWebHookNotification)
                    break
                case WebHookResponseType.CANCELED_SUBSCRIPTION_NOTIFICATION:
                    handlerBean.cancelledSubscriptionNotificationHandler(webHookNotification as RecurlyCanceledSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.RENEWED_SUBSCRIPTION_NOTIFICATION:
                    handlerBean.renewedSubscriptionNotificationHandler(webHookNotification as RecurlyRenewedSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.NEW_SUBSCRIPTION_NOTIFICATION:
                    handlerBean.newSubscriptionNotificationHandler(webHookNotification as RecurlyNewSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.EXPIRED_SUBSCRIPTION_NOTIFICATION:
                    handlerBean.expiredSubscriptionNotificationHandler(webHookNotification as RecurlyExpiredSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.SUBSCRIPTION_UPDATED:
                    handlerBean.subscriptionUpdatedNotificationHandler(webHookNotification as RecurlyChangedSubscriptionWebHookNotification)
                    break
            }
            response.status = 201
            render 'Data parsed and accepted'
        } else {
            response.status = 200
            render 'This notification is not processed by the application, but notification was accepted'
        }
    }

    // PRIVATE

    def getConfig() {
        grailsApplication.config.grails?.plugin?.recurly
    }

}
