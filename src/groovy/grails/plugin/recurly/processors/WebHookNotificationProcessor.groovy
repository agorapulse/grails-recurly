package grails.plugin.recurly.processors

import grails.plugin.recurly.helpers.WebHookNotification
import grails.plugin.recurly.RecurlySuccessfulPaymentWebHookNotification
import grails.plugin.recurly.enums.WebHookResponseType
import grails.plugin.recurly.RecurlyAccount
import grails.plugin.recurly.RecurlyTransaction
import grails.plugin.recurly.helpers.GenericNodeTypeCaster
import grails.plugin.recurly.RecurlyFailedRenewalWebHookNotification
import grails.plugin.recurly.RecurlyExpiredSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyChangedSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlySubscription
import grails.plugin.recurly.RecurlyCanceledSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyRenewedSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyNewSubscriptionWebHookNotification

class WebHookNotificationProcessor extends GenericNodeTypeCaster {
    String receivedXml
    def parsedXml

    WebHookNotificationProcessor(String xml) {
        this.receivedXml = xml
    }

    public WebHookNotification process() {
        WebHookNotification webHookNotification = null

        parsedXml = new XmlParser().parseText(receivedXml)

        if (parsedXml.name() == "successful_payment_notification") {
            webHookNotification = processSuccessfulNotification()
        }

        if (parsedXml.name() == "failed_payment_notification") {
            webHookNotification = processFailedNotification()
        }

        if (parsedXml.name() == "expired_subscription_notification") {
            webHookNotification = processExpiredSubscriptionNotification()
        }

        if (parsedXml.name() == "updated_subscription_notification") {
            webHookNotification = processSubscriptionChangeNotification()
        }

        if (parsedXml.name() == "canceled_subscription_notification") {
            webHookNotification = processCanceledSubscriptionNotification()
        }

        if (parsedXml.name() == "renewed_subscription_notification") {
            webHookNotification = processRenewedSubscriptionNotification()
        }

        if (parsedXml.name() == "new_subscription_notification") {
            webHookNotification = processNewSubscriptionNotification()
        }

        return webHookNotification
    }

    private RecurlyFailedRenewalWebHookNotification processFailedNotification() {
        RecurlyFailedRenewalWebHookNotification recurlyFailedRenewalWebHookNotification = new RecurlyFailedRenewalWebHookNotification()

        recurlyFailedRenewalWebHookNotification.webHookResponseType = WebHookResponseType.FAILED_RENEWAL_PAYMENT_NOTIFICATION

        recurlyFailedRenewalWebHookNotification.recurlyAccount = this.parseAndGetRecurlyAccount()

        recurlyFailedRenewalWebHookNotification.recurlyTransaction = this.parseAndGetRecurlyTransaction()

        return recurlyFailedRenewalWebHookNotification
    }

    private RecurlySuccessfulPaymentWebHookNotification processSuccessfulNotification() {
        RecurlySuccessfulPaymentWebHookNotification recurlySuccessfulPaymentWebHookNotification = new RecurlySuccessfulPaymentWebHookNotification()

        recurlySuccessfulPaymentWebHookNotification.webHookResponseType = WebHookResponseType.SUCCESSFUL_PAYMENT_NOTIFICATION

        recurlySuccessfulPaymentWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()

        recurlySuccessfulPaymentWebHookNotification.recurlyTransaction = parseAndGetRecurlyTransaction()

        return recurlySuccessfulPaymentWebHookNotification
    }

    private RecurlyExpiredSubscriptionWebHookNotification processExpiredSubscriptionNotification() {
        RecurlyExpiredSubscriptionWebHookNotification recurlyExpiredSubscriptionWebHookNotification = new RecurlyExpiredSubscriptionWebHookNotification()

        recurlyExpiredSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.EXPIRED_SUBSCRIPTION_NOTIFICATION

        recurlyExpiredSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()

        recurlyExpiredSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()

        return recurlyExpiredSubscriptionWebHookNotification
    }

    private RecurlyChangedSubscriptionWebHookNotification processSubscriptionChangeNotification() {
        RecurlyChangedSubscriptionWebHookNotification recurlyChangedSubscriptionWebHookNotification = new RecurlyChangedSubscriptionWebHookNotification()

        recurlyChangedSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.SUBSCRIPTION_UPDATED

        recurlyChangedSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()

        recurlyChangedSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()

        return recurlyChangedSubscriptionWebHookNotification

    }

    private RecurlyRenewedSubscriptionWebHookNotification processRenewedSubscriptionNotification() {
        RecurlyRenewedSubscriptionWebHookNotification recurlyRenewedSubscriptionWebHookNotification = new RecurlyRenewedSubscriptionWebHookNotification()

        recurlyRenewedSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.RENEWED_SUBSCRIPTION_NOTIFICATION

        recurlyRenewedSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()

        recurlyRenewedSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()

        return recurlyRenewedSubscriptionWebHookNotification

    }


    private RecurlyNewSubscriptionWebHookNotification processNewSubscriptionNotification() {

        RecurlyNewSubscriptionWebHookNotification recurlyNewSubscriptionWebHookNotification = new RecurlyNewSubscriptionWebHookNotification()

        recurlyNewSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.NEW_SUBSCRIPTION_NOTIFICATION

        recurlyNewSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()

        recurlyNewSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()

        return recurlyNewSubscriptionWebHookNotification

    }

    private RecurlyCanceledSubscriptionWebHookNotification processCanceledSubscriptionNotification() {
        RecurlyCanceledSubscriptionWebHookNotification recurlyCanceledSubscriptionWebHookNotification = new RecurlyCanceledSubscriptionWebHookNotification()

        recurlyCanceledSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.CANCELED_SUBSCRIPTION_NOTIFICATION

        recurlyCanceledSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()

        recurlyCanceledSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()

        return recurlyCanceledSubscriptionWebHookNotification

    }

    private RecurlyAccount parseAndGetRecurlyAccount() {
        return new RecurlyAccount(
                accountCode: parsedXml.account?.account_code?.text(),
                userName: parsedXml.account?.username?.text(),
                email: parsedXml.account?.email?.text(),
                firstName: parsedXml.account?.first_name?.text(),
                lastName: parsedXml.account?.last_name?.text(),
                companyName: parsedXml.account?.company_name?.text()
        )
    }

    private RecurlyTransaction parseAndGetRecurlyTransaction() {
        return new RecurlyTransaction(
                id: parsedXml.transaction?.id?.text(),
                action: parsedXml.transaction?.action?.text(),
                date: parsedXml.transaction?.date?.text(),
                amountInCents: convertNodeToInteger(parsedXml.transaction?.amount_in_cents?.text()),
                status: parsedXml.transaction?.status?.text(),
                message: parsedXml.transaction?.message?.text(),
                reference: parsedXml.transaction?.reference?.text(),
                cvvResult: parsedXml.transaction?.cvv_result?.text(),
                avsResult: parsedXml.transaction?.avs_result?.text(),
                avsResultStreet: parsedXml.transaction?.avs_result_street?.text(),
                avsResultPostal: parsedXml.transaction?.avs_result_postal?.text(),
                test: parsedXml.transaction?.test?.text() as Boolean,
                voidable: parsedXml.transaction?.voidable?.text() as Boolean,
                refundable: parsedXml.transaction?.refundable?.text() as Boolean
        )
    }

    private RecurlySubscription parseAndGetRecurlySubscription() {
        return new RecurlySubscription(
                planCode: parsedXml.subscription?.plan?.plan_code?.text(),
                planName: parsedXml.subscription?.plan?.plan_code?.text(),
                state: parsedXml.subscription?.state?.text(),
                quantity: convertNodeToInteger(parsedXml.subscription?.quantity?.text()),
                totalAmountInCents: convertNodeToInteger(parsedXml.subscription?.total_amount_in_cents?.text()),
                activatedAt: parsedXml.subscription?.activated_at?.text(),
                cancelledAt: parsedXml.subscription?.canceled_at?.text(),
                expiresAt: parsedXml.subscription?.expires_at?.text(),
                currentPeriodStartedAt: parsedXml.subscription?.current_period_started_at?.text(),
                currentPeriodEndsAt: parsedXml.subscription?.current_period_ends_at?.text(),
                trialStartedAt: parsedXml.subscription?.trial_started_at?.text(),
                trialEndsAt: parsedXml.subscription?.trial_ends_at?.text()
        )
    }
}