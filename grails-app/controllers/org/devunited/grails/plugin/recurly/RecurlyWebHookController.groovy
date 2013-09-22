package org.devunited.grails.plugin.recurly

import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.devunited.grails.plugin.recurly.helpers.WebHookNotification
import org.devunited.grails.plugin.recurly.enums.WebHookResponseType

class RecurlyWebHookController {

    def config = ConfigurationHolder.config.recurly

    static def handlerBean

    RecurlyService recurlyService

    def acceptNotice = {
        println('############################ Recurly WebHook Notification Recieved ###################################')
        println params
        if (!handlerBean) {
            println("No Webhook Handler Defined In The Application")
            response.setStatus(500, "Internal Server Error")
            render("Handler Not Defined For the Application")
            return
        }
        String xml
        try {
            StringBuffer stringBuffer = new StringBuffer()
            BufferedReader bufferedReader = request.getReader()
            bufferedReader.eachLine {
                stringBuffer.append(it)
            }
            xml = stringBuffer.toString()
        } catch (Exception e) {
            response.setStatus(500, "Internal Server Error")
            return
        }
        if (params.user && params.pass && params.user == config.webhook.user && params.pass == config.webhook.pass) {
            WebHookNotification webHookNotification = recurlyService.processNotification(xml)
            if (webHookNotification) {
                switch (webHookNotification.webHookResponseType) {
                    case WebHookResponseType.SUCCESSFUL_PAYMENT_NOTIFICATION:
                        handlerBean.successfulPaymentNotificationHandler(webHookNotification as RecurlySuccessfulPaymentWebHookNotification)
                        break;
                    case WebHookResponseType.FAILED_RENEWAL_PAYMENT_NOTIFICATION:
                        handlerBean.failedRenewalPaymentNotificationHandler(webHookNotification as RecurlyFailedRenewalWebHookNotification)
                        break;
                    case WebHookResponseType.CANCELED_SUBSCRIPTION_NOTIFICATION:
                        handlerBean.cancelledSubscriptionNotificationHandler(webHookNotification as RecurlyCanceledSubscriptionWebHookNotification)
                        break;
                    case WebHookResponseType.RENEWED_SUBSCRIPTION_NOTIFICATION:
                        handlerBean.renewedSubscriptionNotificationHandler(webHookNotification as RecurlyRenewedSubscriptionWebHookNotification)
                        break;
                    case WebHookResponseType.NEW_SUBSCRIPTION_NOTIFICATION:
                        handlerBean.newSubscriptionNotificationHandler(webHookNotification as RecurlyNewSubscriptionWebHookNotification)
                        break;
                    case WebHookResponseType.EXPIRED_SUBSCRIPTION_NOTIFICATION:
                        handlerBean.expiredSubscriptionNotificationHandler(webHookNotification as RecurlyExpiredSubscriptionWebHookNotification)
                        break;
                    case WebHookResponseType.SUBSCRIPTION_UPDATED:
                        handlerBean.subscriptionUpdatedNotificationHandler(webHookNotification as RecurlyChangedSubscriptionWebHookNotification)
                        break;
                }
                response.setStatus(201, "OK and Accepted")
                render "Data parsed and accepted"
            } else {
                response.setStatus(200, "Unprocessable Entity But Accepted")
                render "This notification is not processed by the application, but notification was accepted"
            }
        } else {
            response.setStatus(401, "Unauthorized")
            render "Authentication Failure"
        }
    }
}
