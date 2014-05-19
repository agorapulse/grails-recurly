package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlyAccount
import grails.plugin.recurly.RecurlySubscription
import grails.plugin.recurly.RecurlyTransaction
import grails.plugin.recurly.enums.RecurlySubscriptionState
import grails.plugin.recurly.enums.WebHookResponseType
import grails.plugin.recurly.helpers.GenericNodeTypeCaster
import grails.plugin.recurly.helpers.WebHookNotification
import grails.plugin.recurly.notifications.*

class WebHookNotificationProcessor extends GenericNodeTypeCaster {
    String receivedXml
    def parsedXml

    WebHookNotificationProcessor(String xml) {
        this.receivedXml = xml
    }

    public WebHookNotification process() {
        WebHookNotification webHookNotification
        parsedXml = new XmlParser().parseText(receivedXml)
        switch(parsedXml.name()) {
            // Account notifications
            case "new_account_notification":
                webHookNotification = processNewAccountNotification()
                break
            case "canceled_account_notification":
                webHookNotification = processCanceledAccountNotification()
                break
            case "billing_info_updated_notification":
                webHookNotification = processBillingInfoUpdatedNotification()
                break
            case "reactivated_account_notification":
                webHookNotification = processReactivatedAccountNotification()
                break
            // Payment notifications
            case "successful_payment_notification":
                webHookNotification = processSuccessfulPaymentNotification()
                break
            case "successful_refund_notification":
                webHookNotification = processSuccessfulRefundNotification()
                break
            case "failed_payment_notification":
                webHookNotification = processFailedPaymentNotification()
                break
            case "void_payment_notification":
                webHookNotification = processVoidPaymentNotification()
                break
            // Subscription notifications
            case "expired_subscription_notification":
                webHookNotification = processExpiredSubscriptionNotification()
                break
            case "updated_subscription_notification":
                webHookNotification = processSubscriptionChangeNotification()
                break
            case "canceled_subscription_notification":
                webHookNotification = processCanceledSubscriptionNotification()
                break
            case "renewed_subscription_notification":
                webHookNotification = processRenewedSubscriptionNotification()
                break
            case "new_subscription_notification":
                webHookNotification = processNewSubscriptionNotification()
                break
        }
        return webHookNotification
    }

    // Account notifications

    private RecurlyNewAccountWebHookNotification processNewAccountNotification() {
        RecurlyNewAccountWebHookNotification recurlyNewAccountWebHookNotification = new RecurlyNewAccountWebHookNotification()
        recurlyNewAccountWebHookNotification.webHookResponseType = WebHookResponseType.NEW_ACCOUNT_NOTIFICATION
        recurlyNewAccountWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        return recurlyNewAccountWebHookNotification
    }

    private RecurlyCanceledAccountWebHookNotification processCanceledAccountNotification() {
        RecurlyCanceledAccountWebHookNotification recurlyCanceledAccountWebHookNotification = new RecurlyCanceledAccountWebHookNotification()
        recurlyCanceledAccountWebHookNotification.webHookResponseType = WebHookResponseType.CANCELED_ACCOUNT_NOTIFICATION
        recurlyCanceledAccountWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        return recurlyCanceledAccountWebHookNotification
    }

    private RecurlyBillingInfoUpdatedWebHookNotification processBillingInfoUpdatedNotification() {
        RecurlyBillingInfoUpdatedWebHookNotification recurlyBillingInfoUpdatedWebHookNotification = new RecurlyBillingInfoUpdatedWebHookNotification()
        recurlyBillingInfoUpdatedWebHookNotification.webHookResponseType = WebHookResponseType.BILLING_INFO_UPDATED_NOTIFICATION
        recurlyBillingInfoUpdatedWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        return recurlyBillingInfoUpdatedWebHookNotification
    }

    private RecurlyReactivatedAccountWebHookNotification processReactivatedAccountNotification() {
        RecurlyReactivatedAccountWebHookNotification recurlyReactivatedAccountWebHookNotification = new RecurlyReactivatedAccountWebHookNotification()
        recurlyReactivatedAccountWebHookNotification.webHookResponseType = WebHookResponseType.REACTIVATED_ACCOUNT_NOTIFICATION
        recurlyReactivatedAccountWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        recurlyReactivatedAccountWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()
        return recurlyReactivatedAccountWebHookNotification
    }

    // Payment notifications

    private RecurlyFailedPaymentWebHookNotification processFailedPaymentNotification() {
        RecurlyFailedPaymentWebHookNotification recurlyFailedPaymentWebHookNotification = new RecurlyFailedPaymentWebHookNotification()
        recurlyFailedPaymentWebHookNotification.webHookResponseType = WebHookResponseType.FAILED_PAYMENT_NOTIFICATION
        recurlyFailedPaymentWebHookNotification.recurlyAccount = this.parseAndGetRecurlyAccount()
        recurlyFailedPaymentWebHookNotification.recurlyTransaction = this.parseAndGetRecurlyTransaction()
        return recurlyFailedPaymentWebHookNotification
    }

    private RecurlySuccessfulPaymentWebHookNotification processSuccessfulPaymentNotification() {
        RecurlySuccessfulPaymentWebHookNotification recurlySuccessfulPaymentWebHookNotification = new RecurlySuccessfulPaymentWebHookNotification()
        recurlySuccessfulPaymentWebHookNotification.webHookResponseType = WebHookResponseType.SUCCESSFUL_PAYMENT_NOTIFICATION
        recurlySuccessfulPaymentWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        recurlySuccessfulPaymentWebHookNotification.recurlyTransaction = parseAndGetRecurlyTransaction()
        return recurlySuccessfulPaymentWebHookNotification
    }

    private RecurlySuccessfulRefundWebHookNotification processSuccessfulRefundNotification() {
        RecurlySuccessfulRefundWebHookNotification recurlySuccessfulRefundWebHookNotification = new RecurlySuccessfulRefundWebHookNotification()
        recurlySuccessfulRefundWebHookNotification.webHookResponseType = WebHookResponseType.SUCCESSFUL_REFUND_NOTIFICATION
        recurlySuccessfulRefundWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        recurlySuccessfulRefundWebHookNotification.recurlyTransaction = parseAndGetRecurlyTransaction()
        return recurlySuccessfulRefundWebHookNotification
    }

    private RecurlyVoidPaymentWebHookNotification processVoidPaymentNotification() {
        RecurlyVoidPaymentWebHookNotification recurlyVoidPaymentWebHookNotification = new RecurlyVoidPaymentWebHookNotification()
        recurlyVoidPaymentWebHookNotification.webHookResponseType = WebHookResponseType.VOID_PAYMENT_NOTIFICATION
        recurlyVoidPaymentWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        recurlyVoidPaymentWebHookNotification.recurlyTransaction = parseAndGetRecurlyTransaction()
        return recurlyVoidPaymentWebHookNotification
    }

    // Subscription notifications

    private RecurlyExpiredSubscriptionWebHookNotification processExpiredSubscriptionNotification() {
        RecurlyExpiredSubscriptionWebHookNotification recurlyExpiredSubscriptionWebHookNotification = new RecurlyExpiredSubscriptionWebHookNotification()
        recurlyExpiredSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.EXPIRED_SUBSCRIPTION_NOTIFICATION
        recurlyExpiredSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        recurlyExpiredSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()
        return recurlyExpiredSubscriptionWebHookNotification
    }

    private RecurlyUpdatedSubscriptionWebHookNotification processSubscriptionChangeNotification() {
        RecurlyUpdatedSubscriptionWebHookNotification recurlyUpdatedSubscriptionWebHookNotification = new RecurlyUpdatedSubscriptionWebHookNotification()
        recurlyUpdatedSubscriptionWebHookNotification.webHookResponseType = WebHookResponseType.UPDATED_SUBSCRIPTION_NOTIFICATION
        recurlyUpdatedSubscriptionWebHookNotification.recurlyAccount = parseAndGetRecurlyAccount()
        recurlyUpdatedSubscriptionWebHookNotification.recurlySubscription = parseAndGetRecurlySubscription()
        return recurlyUpdatedSubscriptionWebHookNotification

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
                accountCode: parsedXml.account?.account_code?.text(),
                uuid: parsedXml.transaction?.id?.text(),
                invoiceUuid: parsedXml.transaction?.invoice_id?.text(),
                subscriptionUuid: parsedXml.transaction?.subscription_id?.text(),
                action: parsedXml.transaction?.action?.text(),
                amountInCents: convertNodeToInteger(parsedXml.transaction?.amount_in_cents?.text()),
                status: parsedXml.transaction?.status?.text(),
                message: parsedXml.transaction?.message?.text(),
                reference: parsedXml.transaction?.reference?.text(),
                source: parsedXml.transaction?.source?.text(),
                cvvResult: parsedXml.transaction?.cvv_result?.text(),
                avsResult: parsedXml.transaction?.avs_result?.text(),
                avsResultStreet: parsedXml.transaction?.avs_result_street?.text(),
                avsResultPostal: parsedXml.transaction?.avs_result_postal?.text(),
                test: parsedXml.transaction?.test?.text() as Boolean,
                voidable: parsedXml.transaction?.voidable?.text() as Boolean,
                refundable: parsedXml.transaction?.refundable?.text() as Boolean,
                createdAt: convertNodeToDate(parsedXml.transaction?.date?.text())
        )
    }

    private RecurlySubscription parseAndGetRecurlySubscription() {
        def state = parsedXml.subscription?.state?.text()
        try {
            state = state.toString().toUpperCase() as RecurlySubscriptionState
        } catch (Exception e) {
            // Ignore
        }
        return new RecurlySubscription(
                accountCode: parsedXml.account?.account_code?.text(),
                uuid: parsedXml.subscription?.uuid?.text(),
                planCode: parsedXml.subscription?.plan?.plan_code?.text(),
                planName: parsedXml.subscription?.plan?.name?.text(),
                state: state,
                quantity: convertNodeToInteger(parsedXml.subscription?.quantity?.text()),
                unitAmountInCents: convertNodeToInteger(parsedXml.subscription?.total_amount_in_cents?.text()),
                activatedAt: convertNodeToDate(parsedXml.subscription?.activated_at?.text()),
                cancelledAt: convertNodeToDate(parsedXml.subscription?.canceled_at?.text()),
                expiresAt: convertNodeToDate(parsedXml.subscription?.expires_at?.text()),
                currentPeriodStartedAt: convertNodeToDate(parsedXml.subscription?.current_period_started_at?.text()),
                currentPeriodEndsAt: convertNodeToDate(parsedXml.subscription?.current_period_ends_at?.text()),
                trialStartedAt: convertNodeToDate(parsedXml.subscription?.trial_started_at?.text()),
                trialEndsAt: convertNodeToDate(parsedXml.subscription?.trial_ends_at?.text())
        )
    }
}