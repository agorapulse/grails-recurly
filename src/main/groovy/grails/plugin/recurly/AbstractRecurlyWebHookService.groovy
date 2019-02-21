package grails.plugin.recurly

import com.ning.billing.recurly.model.push.account.BillingInfoUpdateFailedNotification
import com.ning.billing.recurly.model.push.account.BillingInfoUpdatedNotification
import com.ning.billing.recurly.model.push.account.CanceledAccountNotification
import com.ning.billing.recurly.model.push.account.DeletedShippingAddressNotification
import com.ning.billing.recurly.model.push.account.NewAccountNotification
import com.ning.billing.recurly.model.push.account.NewShippingAddressNotification
import com.ning.billing.recurly.model.push.account.UpdatedAccountNotification
import com.ning.billing.recurly.model.push.account.UpdatedShippingAddressNotification
import com.ning.billing.recurly.model.push.creditpayment.NewCreditPaymentNotification
import com.ning.billing.recurly.model.push.creditpayment.VoidedCreditPaymentNotification
import com.ning.billing.recurly.model.push.giftcard.CanceledGiftCardNotification
import com.ning.billing.recurly.model.push.giftcard.PurchasedGiftCardNotification
import com.ning.billing.recurly.model.push.giftcard.RedeemedGiftCardNotification
import com.ning.billing.recurly.model.push.giftcard.RegeneratedGiftCardNotification
import com.ning.billing.recurly.model.push.giftcard.UpdatedBalanceGiftCardNotification
import com.ning.billing.recurly.model.push.giftcard.UpdatedGiftCardNotification
import com.ning.billing.recurly.model.push.invoice.ClosedCreditInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.ClosedInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.FailedChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.NewChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.NewCreditInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.NewDunningEventNotification
import com.ning.billing.recurly.model.push.invoice.NewInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.OpenCreditInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.PaidChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.PastDueChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.PastDueInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.ProcessingChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.ProcessingCreditInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.ProcessingInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.ReopenedChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.ReopenedCreditInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.UpdatedChargeInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.UpdatedCreditInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.UpdatedInvoiceNotification
import com.ning.billing.recurly.model.push.invoice.VoidedCreditInvoiceNotification
import com.ning.billing.recurly.model.push.payment.FailedPaymentNotification
import com.ning.billing.recurly.model.push.payment.FraudInfoUpdatedNotification
import com.ning.billing.recurly.model.push.payment.ProcessingPaymentNotification
import com.ning.billing.recurly.model.push.payment.ScheduledPaymentNotification
import com.ning.billing.recurly.model.push.payment.SuccessfulPaymentNotification
import com.ning.billing.recurly.model.push.payment.SuccessfulRefundNotification
import com.ning.billing.recurly.model.push.payment.TransactionAuthorizedNotification
import com.ning.billing.recurly.model.push.payment.TransactionStatusUpdatedNotification
import com.ning.billing.recurly.model.push.payment.VoidPaymentNotification
import com.ning.billing.recurly.model.push.subscription.CanceledSubscriptionNotification
import com.ning.billing.recurly.model.push.subscription.ExpiredSubscriptionNotification
import com.ning.billing.recurly.model.push.subscription.LowBalanceGiftCardNotification
import com.ning.billing.recurly.model.push.subscription.NewSubscriptionNotification
import com.ning.billing.recurly.model.push.subscription.PausedSubscriptionRenewalNotification
import com.ning.billing.recurly.model.push.subscription.ReactivatedAccountNotification
import com.ning.billing.recurly.model.push.subscription.RenewedSubscriptionNotification
import com.ning.billing.recurly.model.push.subscription.ScheduledSubscriptionPauseNotification
import com.ning.billing.recurly.model.push.subscription.SubscriptionPausedCanceledNotification
import com.ning.billing.recurly.model.push.subscription.SubscriptionPausedModifiedNotification
import com.ning.billing.recurly.model.push.subscription.SubscriptionPausedNotification
import com.ning.billing.recurly.model.push.subscription.SubscriptionResumedNotification
import com.ning.billing.recurly.model.push.subscription.UpdatedSubscriptionNotification
import com.ning.billing.recurly.model.push.usage.NewUsageNotification
import grails.events.bus.EventBusAware
import groovy.transform.CompileStatic

import javax.annotation.PostConstruct

@CompileStatic
abstract class AbstractRecurlyWebHookService implements EventBusAware {

    @PostConstruct
    void init() {
        eventBus.subscribe('billingInfoUpdatedNotification', this.&billingInfoUpdatedNotificationHandler)
        eventBus.subscribe('billingInfoUpdateFailedNotification', this.&billingInfoUpdateFailedNotificationHandler)
        eventBus.subscribe('newShippingAddressNotification', this.&newShippingAddressNotificationHandler)
        eventBus.subscribe('updatedShippingAddressNotification', this.&updatedShippingAddressNotificationHandler)
        eventBus.subscribe('deletedShippingAddressNotification', this.&deletedShippingAddressNotificationHandler)
        eventBus.subscribe('canceledAccountNotification', this.&canceledAccountNotificationHandler)
        eventBus.subscribe('newAccountNotification', this.&newAccountNotificationHandler)
        eventBus.subscribe('failedPaymentNotification', this.&failedPaymentNotificationHandler)
        eventBus.subscribe('scheduledPaymentNotification', this.&scheduledPaymentNotificationHandler)
        eventBus.subscribe('processingPaymentNotification', this.&processingPaymentNotificationHandler)
        eventBus.subscribe('successfulPaymentNotification', this.&successfulPaymentNotificationHandler)
        eventBus.subscribe('successfulRefundNotification', this.&successfulRefundNotificationHandler)
        eventBus.subscribe('voidPaymentNotification', this.&voidPaymentNotificationHandler)
        eventBus.subscribe('fraudInfoUpdatedNotification', this.&fraudInfoUpdatedNotificationHandler)
        eventBus.subscribe('transactionStatusUpdatedNotification', this.&transactionStatusUpdatedNotificationHandler)
        eventBus.subscribe('transactionAuthorizedNotification', this.&transactionAuthorizedNotificationHandler)
        eventBus.subscribe('newCreditPaymentNotification', this.&newCreditPaymentNotificationHandler)
        eventBus.subscribe('voidedCreditPaymentNotification', this.&voidedCreditPaymentNotificationHandler)
        eventBus.subscribe('canceledSubscriptionNotification', this.&canceledSubscriptionNotificationHandler)
        eventBus.subscribe('expiredSubscriptionNotification', this.&expiredSubscriptionNotificationHandler)
        eventBus.subscribe('subscriptionPausedNotification', this.&subscriptionPausedNotificationHandler)
        eventBus.subscribe('subscriptionResumedNotification', this.&subscriptionResumedNotificationHandler)
        eventBus.subscribe('scheduledSubscriptionPauseNotification', this.&scheduledSubscriptionPauseNotificationHandler)
        eventBus.subscribe('subscriptionPausedModifiedNotification', this.&subscriptionPausedModifiedNotificationHandler)
        eventBus.subscribe('pausedSubscriptionRenewalNotification', this.&pausedSubscriptionRenewalNotificationHandler)
        eventBus.subscribe('subscriptionPausedCanceledNotification', this.&subscriptionPausedCanceledNotificationHandler)
        eventBus.subscribe('newSubscriptionNotification', this.&newSubscriptionNotificationHandler)
        eventBus.subscribe('reactivatedAccountNotification', this.&reactivatedAccountNotificationHandler)
        eventBus.subscribe('renewedSubscriptionNotification', this.&renewedSubscriptionNotificationHandler)
        eventBus.subscribe('updatedSubscriptionNotification', this.&updatedSubscriptionNotificationHandler)
        eventBus.subscribe('closedInvoiceNotification', this.&closedInvoiceNotificationHandler)
        eventBus.subscribe('newInvoiceNotification', this.&newInvoiceNotificationHandler)
        eventBus.subscribe('pastDueInvoiceNotification', this.&pastDueInvoiceNotificationHandler)
        eventBus.subscribe('processingInvoiceNotification', this.&processingInvoiceNotificationHandler)
        eventBus.subscribe('updatedAccountNotification', this.&updatedAccountNotificationHandler)
        eventBus.subscribe('newDunningEventNotification', this.&newDunningEventNotificationHandler)
        eventBus.subscribe('newChargeInvoiceNotification', this.&newChargeInvoiceNotificationHandler)
        eventBus.subscribe('processingChargeInvoiceNotification', this.&processingChargeInvoiceNotificationHandler)
        eventBus.subscribe('pastDueChargeInvoiceNotification', this.&pastDueChargeInvoiceNotificationHandler)
        eventBus.subscribe('paidChargeInvoiceNotification', this.&paidChargeInvoiceNotificationHandler)
        eventBus.subscribe('failedChargeInvoiceNotification', this.&failedChargeInvoiceNotificationHandler)
        eventBus.subscribe('reopenedChargeInvoiceNotification', this.&reopenedChargeInvoiceNotificationHandler)
        eventBus.subscribe('updatedChargeInvoiceNotification', this.&updatedChargeInvoiceNotificationHandler)
        eventBus.subscribe('newCreditInvoiceNotification', this.&newCreditInvoiceNotificationHandler)
        eventBus.subscribe('processingCreditInvoiceNotification', this.&processingCreditInvoiceNotificationHandler)
        eventBus.subscribe('closedCreditInvoiceNotification', this.&closedCreditInvoiceNotificationHandler)
        eventBus.subscribe('voidedCreditInvoiceNotification', this.&voidedCreditInvoiceNotificationHandler)
        eventBus.subscribe('reopenedCreditInvoiceNotification', this.&reopenedCreditInvoiceNotificationHandler)
        eventBus.subscribe('openCreditInvoiceNotification', this.&openCreditInvoiceNotificationHandler)
        eventBus.subscribe('updatedCreditInvoiceNotification', this.&updatedCreditInvoiceNotificationHandler)
        eventBus.subscribe('updatedInvoiceNotification', this.&updatedInvoiceNotificationHandler)
        eventBus.subscribe('newUsageNotification', this.&newUsageNotificationHandler)
        eventBus.subscribe('purchasedGiftCardNotification', this.&purchasedGiftCardNotificationHandler)
        eventBus.subscribe('canceledGiftCardNotification', this.&canceledGiftCardNotificationHandler)
        eventBus.subscribe('updatedGiftCardNotification', this.&updatedGiftCardNotificationHandler)
        eventBus.subscribe('regeneratedGiftCardNotification', this.&regeneratedGiftCardNotificationHandler)
        eventBus.subscribe('redeemedGiftCardNotification', this.&redeemedGiftCardNotificationHandler)
        eventBus.subscribe('updatedBalanceGiftCardNotification', this.&updatedBalanceGiftCardNotificationHandler)
        eventBus.subscribe('lowBalanceGiftCardNotification', this.&lowBalanceGiftCardNotificationHandler)
    }

    void billingInfoUpdatedNotificationHandler(BillingInfoUpdatedNotification notification) { }
    void billingInfoUpdateFailedNotificationHandler(BillingInfoUpdateFailedNotification notification) { }
    void newShippingAddressNotificationHandler(NewShippingAddressNotification notification) { }
    void updatedShippingAddressNotificationHandler(UpdatedShippingAddressNotification notification) { }
    void deletedShippingAddressNotificationHandler(DeletedShippingAddressNotification notification) { }
    void canceledAccountNotificationHandler(CanceledAccountNotification notification) { }
    void newAccountNotificationHandler(NewAccountNotification notification) { }
    void failedPaymentNotificationHandler(FailedPaymentNotification notification) { }
    void scheduledPaymentNotificationHandler(ScheduledPaymentNotification notification) { }
    void processingPaymentNotificationHandler(ProcessingPaymentNotification notification) { }
    void successfulPaymentNotificationHandler(SuccessfulPaymentNotification notification) { }
    void successfulRefundNotificationHandler(SuccessfulRefundNotification notification) { }
    void voidPaymentNotificationHandler(VoidPaymentNotification notification) { }
    void fraudInfoUpdatedNotificationHandler(FraudInfoUpdatedNotification notification) { }
    void transactionStatusUpdatedNotificationHandler(TransactionStatusUpdatedNotification notification) { }
    void transactionAuthorizedNotificationHandler(TransactionAuthorizedNotification notification) { }
    void newCreditPaymentNotificationHandler(NewCreditPaymentNotification notification) { }
    void voidedCreditPaymentNotificationHandler(VoidedCreditPaymentNotification notification) { }
    void canceledSubscriptionNotificationHandler(CanceledSubscriptionNotification notification) { }
    void expiredSubscriptionNotificationHandler(ExpiredSubscriptionNotification notification) { }
    void subscriptionPausedNotificationHandler(SubscriptionPausedNotification notification) { }
    void subscriptionResumedNotificationHandler(SubscriptionResumedNotification notification) { }
    void scheduledSubscriptionPauseNotificationHandler(ScheduledSubscriptionPauseNotification notification) { }
    void subscriptionPausedModifiedNotificationHandler(SubscriptionPausedModifiedNotification notification) { }
    void pausedSubscriptionRenewalNotificationHandler(PausedSubscriptionRenewalNotification notification) { }
    void subscriptionPausedCanceledNotificationHandler(SubscriptionPausedCanceledNotification notification) { }
    void newSubscriptionNotificationHandler(NewSubscriptionNotification notification) { }
    void reactivatedAccountNotificationHandler(ReactivatedAccountNotification notification) { }
    void renewedSubscriptionNotificationHandler(RenewedSubscriptionNotification notification) { }
    void updatedSubscriptionNotificationHandler(UpdatedSubscriptionNotification notification) { }
    void closedInvoiceNotificationHandler(ClosedInvoiceNotification notification) { }
    void newInvoiceNotificationHandler(NewInvoiceNotification notification) { }
    void pastDueInvoiceNotificationHandler(PastDueInvoiceNotification notification) { }
    void processingInvoiceNotificationHandler(ProcessingInvoiceNotification notification) { }
    void updatedAccountNotificationHandler(UpdatedAccountNotification notification) { }
    void newDunningEventNotificationHandler(NewDunningEventNotification notification) { }
    void newChargeInvoiceNotificationHandler(NewChargeInvoiceNotification notification) { }
    void processingChargeInvoiceNotificationHandler(ProcessingChargeInvoiceNotification notification) { }
    void pastDueChargeInvoiceNotificationHandler(PastDueChargeInvoiceNotification notification) { }
    void paidChargeInvoiceNotificationHandler(PaidChargeInvoiceNotification notification) { }
    void failedChargeInvoiceNotificationHandler(FailedChargeInvoiceNotification notification) { }
    void reopenedChargeInvoiceNotificationHandler(ReopenedChargeInvoiceNotification notification) { }
    void updatedChargeInvoiceNotificationHandler(UpdatedChargeInvoiceNotification notification) { }
    void newCreditInvoiceNotificationHandler(NewCreditInvoiceNotification notification) { }
    void processingCreditInvoiceNotificationHandler(ProcessingCreditInvoiceNotification notification) { }
    void closedCreditInvoiceNotificationHandler(ClosedCreditInvoiceNotification notification) { }
    void voidedCreditInvoiceNotificationHandler(VoidedCreditInvoiceNotification notification) { }
    void reopenedCreditInvoiceNotificationHandler(ReopenedCreditInvoiceNotification notification) { }
    void openCreditInvoiceNotificationHandler(OpenCreditInvoiceNotification notification) { }
    void updatedCreditInvoiceNotificationHandler(UpdatedCreditInvoiceNotification notification) { }
    void updatedInvoiceNotificationHandler(UpdatedInvoiceNotification notification) { }
    void newUsageNotificationHandler(NewUsageNotification notification) { }
    void purchasedGiftCardNotificationHandler(PurchasedGiftCardNotification notification) { }
    void canceledGiftCardNotificationHandler(CanceledGiftCardNotification notification) { }
    void updatedGiftCardNotificationHandler(UpdatedGiftCardNotification notification) { }
    void regeneratedGiftCardNotificationHandler(RegeneratedGiftCardNotification notification) { }
    void redeemedGiftCardNotificationHandler(RedeemedGiftCardNotification notification) { }
    void updatedBalanceGiftCardNotificationHandler(UpdatedBalanceGiftCardNotification notification) { }
    void lowBalanceGiftCardNotificationHandler(LowBalanceGiftCardNotification notification) { }

}
