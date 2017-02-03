package grails.plugin.recurly

import grails.plugin.recurly.enums.WebHookResponseType
import grails.plugin.recurly.helpers.WebHookNotification
import grails.plugin.recurly.notifications.*
import grails.plugin.recurly.processors.WebHookNotificationProcessor
import grails.plugins.rest.client.RestBuilder
import grails.core.GrailsApplication

class RecurlyWebHookController {

    static recurlyWebHookService
    static defaultAction = 'acceptNotice'

    GrailsApplication grailsApplication

    def acceptNotice() {
        log.debug 'Accepting notice...'

        if (config?.webhook?.user) {
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

        String notificationXml
        try {
            StringBuffer stringBuffer = new StringBuffer()
            BufferedReader bufferedReader = request.getReader()
            bufferedReader.eachLine {
                stringBuffer.append(it)
            }
            notificationXml = stringBuffer.toString()
        } catch (Exception ignored) {
            response.status = 500
            return
        }
        // Repost xml if required
        if (config.webhook?.repostUrl) {
            def rest = new RestBuilder()
            try {
                def response = rest.post(config.webhook?.repostUrl?.toString()){
                    header('Content-Type', 'application/xml;charset=UTF-8')
                    xml notificationXml
                }
            } catch (Exception e) {
                log.error 'An error occured during webhook notification repost', e
            }
        }
        // Process xml
        WebHookNotification webHookNotification = new WebHookNotificationProcessor(notificationXml).process()
        if (webHookNotification) {
            switch (webHookNotification.webHookResponseType) {
                case WebHookResponseType.SUCCESSFUL_PAYMENT_NOTIFICATION:
                    recurlyWebHookService.successfulPaymentNotificationHandler(webHookNotification as RecurlySuccessfulPaymentWebHookNotification)
                    break
                case WebHookResponseType.SUCCESSFUL_REFUND_NOTIFICATION:
                    recurlyWebHookService.successfulRefundNotificationHandler(webHookNotification as RecurlySuccessfulRefundWebHookNotification)
                    break
                case WebHookResponseType.VOID_PAYMENT_NOTIFICATION:
                    recurlyWebHookService.voidPaymentNotificationHandler(webHookNotification as RecurlyVoidPaymentWebHookNotification)
                    break
                case WebHookResponseType.FAILED_PAYMENT_NOTIFICATION:
                    recurlyWebHookService.failedPaymentNotificationHandler(webHookNotification as RecurlyFailedPaymentWebHookNotification)
                    break
                case WebHookResponseType.CANCELED_SUBSCRIPTION_NOTIFICATION:
                    recurlyWebHookService.cancelledSubscriptionNotificationHandler(webHookNotification as RecurlyCanceledSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.RENEWED_SUBSCRIPTION_NOTIFICATION:
                    recurlyWebHookService.renewedSubscriptionNotificationHandler(webHookNotification as RecurlyRenewedSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.NEW_SUBSCRIPTION_NOTIFICATION:
                    recurlyWebHookService.newSubscriptionNotificationHandler(webHookNotification as RecurlyNewSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.EXPIRED_SUBSCRIPTION_NOTIFICATION:
                    recurlyWebHookService.expiredSubscriptionNotificationHandler(webHookNotification as RecurlyExpiredSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.UPDATED_SUBSCRIPTION_NOTIFICATION:
                    recurlyWebHookService.updatedSubscriptionNotificationHandler(webHookNotification as RecurlyUpdatedSubscriptionWebHookNotification)
                    break
                case WebHookResponseType.REACTIVATED_ACCOUNT_NOTIFICATION:
                    recurlyWebHookService.reactivatedAccountNotificationHandler(webHookNotification as RecurlyReactivatedAccountWebHookNotification)
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
