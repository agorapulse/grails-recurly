package grails.plugin.recurly

import com.ning.billing.recurly.model.push.Notification
import com.ning.billing.recurly.model.push.account.*
import com.ning.billing.recurly.model.push.creditpayment.NewCreditPaymentNotification
import com.ning.billing.recurly.model.push.creditpayment.VoidedCreditPaymentNotification
import com.ning.billing.recurly.model.push.giftcard.*
import com.ning.billing.recurly.model.push.invoice.*
import com.ning.billing.recurly.model.push.payment.*
import com.ning.billing.recurly.model.push.subscription.*
import com.ning.billing.recurly.model.push.usage.NewUsageNotification
import groovy.transform.CompileStatic

@CompileStatic
class DefaultRecurlyWebHookService extends AbstractRecurlyWebHookService {

    Notification notification

    @Override
    void billingInfoUpdatedNotificationHandler(BillingInfoUpdatedNotification notification) {
        this.notification = notification
    }

    @Override
    void billingInfoUpdateFailedNotificationHandler(BillingInfoUpdateFailedNotification notification) {
        this.notification = notification
    }

    @Override
    void newShippingAddressNotificationHandler(NewShippingAddressNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedShippingAddressNotificationHandler(UpdatedShippingAddressNotification notification) {
        this.notification = notification
    }

    @Override
    void deletedShippingAddressNotificationHandler(DeletedShippingAddressNotification notification) {
        this.notification = notification
    }

    @Override
    void canceledAccountNotificationHandler(CanceledAccountNotification notification) {
        this.notification = notification
    }

    @Override
    void newAccountNotificationHandler(NewAccountNotification notification) {
        this.notification = notification
    }

    @Override
    void failedPaymentNotificationHandler(FailedPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void scheduledPaymentNotificationHandler(ScheduledPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void processingPaymentNotificationHandler(ProcessingPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void successfulPaymentNotificationHandler(SuccessfulPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void successfulRefundNotificationHandler(SuccessfulRefundNotification notification) {
        this.notification = notification
    }

    @Override
    void voidPaymentNotificationHandler(VoidPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void fraudInfoUpdatedNotificationHandler(FraudInfoUpdatedNotification notification) {
        this.notification = notification
    }

    @Override
    void transactionStatusUpdatedNotificationHandler(TransactionStatusUpdatedNotification notification) {
        this.notification = notification
    }

    @Override
    void transactionAuthorizedNotificationHandler(TransactionAuthorizedNotification notification) {
        this.notification = notification
    }

    @Override
    void newCreditPaymentNotificationHandler(NewCreditPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void voidedCreditPaymentNotificationHandler(VoidedCreditPaymentNotification notification) {
        this.notification = notification
    }

    @Override
    void canceledSubscriptionNotificationHandler(CanceledSubscriptionNotification notification) {
        this.notification = notification
    }

    @Override
    void expiredSubscriptionNotificationHandler(ExpiredSubscriptionNotification notification) {
        this.notification = notification
    }

    @Override
    void subscriptionPausedNotificationHandler(SubscriptionPausedNotification notification) {
        this.notification = notification
    }

    @Override
    void subscriptionResumedNotificationHandler(SubscriptionResumedNotification notification) {
        this.notification = notification
    }

    @Override
    void scheduledSubscriptionPauseNotificationHandler(ScheduledSubscriptionPauseNotification notification) {
        this.notification = notification
    }

    @Override
    void subscriptionPausedModifiedNotificationHandler(SubscriptionPausedModifiedNotification notification) {
        this.notification = notification
    }

    @Override
    void pausedSubscriptionRenewalNotificationHandler(PausedSubscriptionRenewalNotification notification) {
        this.notification = notification
    }

    @Override
    void subscriptionPausedCanceledNotificationHandler(SubscriptionPausedCanceledNotification notification) {
        this.notification = notification
    }

    @Override
    void newSubscriptionNotificationHandler(NewSubscriptionNotification notification) {
        this.notification = notification
    }

    @Override
    void reactivatedAccountNotificationHandler(ReactivatedAccountNotification notification) {
        this.notification = notification
    }

    @Override
    void renewedSubscriptionNotificationHandler(RenewedSubscriptionNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedSubscriptionNotificationHandler(UpdatedSubscriptionNotification notification) {
        this.notification = notification
    }

    @Override
    void closedInvoiceNotificationHandler(ClosedInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void newInvoiceNotificationHandler(NewInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void pastDueInvoiceNotificationHandler(PastDueInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void processingInvoiceNotificationHandler(ProcessingInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedAccountNotificationHandler(UpdatedAccountNotification notification) {
        this.notification = notification
    }

    @Override
    void newDunningEventNotificationHandler(NewDunningEventNotification notification) {
        this.notification = notification
    }

    @Override
    void newChargeInvoiceNotificationHandler(NewChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void processingChargeInvoiceNotificationHandler(ProcessingChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void pastDueChargeInvoiceNotificationHandler(PastDueChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void paidChargeInvoiceNotificationHandler(PaidChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void failedChargeInvoiceNotificationHandler(FailedChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void reopenedChargeInvoiceNotificationHandler(ReopenedChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedChargeInvoiceNotificationHandler(UpdatedChargeInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void newCreditInvoiceNotificationHandler(NewCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void processingCreditInvoiceNotificationHandler(ProcessingCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void closedCreditInvoiceNotificationHandler(ClosedCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void voidedCreditInvoiceNotificationHandler(VoidedCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void reopenedCreditInvoiceNotificationHandler(ReopenedCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void openCreditInvoiceNotificationHandler(OpenCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedCreditInvoiceNotificationHandler(UpdatedCreditInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedInvoiceNotificationHandler(UpdatedInvoiceNotification notification) {
        this.notification = notification
    }

    @Override
    void newUsageNotificationHandler(NewUsageNotification notification) {
        this.notification = notification
    }

    @Override
    void purchasedGiftCardNotificationHandler(PurchasedGiftCardNotification notification) {
        this.notification = notification
    }

    @Override
    void canceledGiftCardNotificationHandler(CanceledGiftCardNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedGiftCardNotificationHandler(UpdatedGiftCardNotification notification) {
        this.notification = notification
    }

    @Override
    void regeneratedGiftCardNotificationHandler(RegeneratedGiftCardNotification notification) {
        this.notification = notification
    }

    @Override
    void redeemedGiftCardNotificationHandler(RedeemedGiftCardNotification notification) {
        this.notification = notification
    }

    @Override
    void updatedBalanceGiftCardNotificationHandler(UpdatedBalanceGiftCardNotification notification) {
        this.notification = notification
    }

    @Override
    void lowBalanceGiftCardNotificationHandler(LowBalanceGiftCardNotification notification) {
        this.notification = notification
    }
}