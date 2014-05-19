package grails.plugin.recurly.interfaces

import grails.plugin.recurly.notifications.*

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 10/3/11
 * Time: 7:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RecurlyWebHookListener {

    public static recurlyWebHook = true

    // Account notifications
    public void newAccountNotificationHandler(RecurlyNewAccountWebHookNotification notification)

    public void canceledAccountNotificationHandler(RecurlyCanceledAccountWebHookNotification notification)

    public void billingInfoUpdatedNotificationHandler(RecurlyBillingInfoUpdatedWebHookNotification notification)

    public void reactivatedAccountNotificationHandler(RecurlyReactivatedAccountWebHookNotification notification)

    // Payment notifications
    public void successfulPaymentNotificationHandler(RecurlySuccessfulPaymentWebHookNotification notification)

    public void successfulRefundNotificationHandler(RecurlySuccessfulRefundWebHookNotification notification)

    public void voidPaymentNotificationHandler(RecurlyVoidPaymentWebHookNotification notification)

    public void failedPaymentNotificationHandler(RecurlyFailedPaymentWebHookNotification notification)

    // Subscription notifications
    public void cancelledSubscriptionNotificationHandler(RecurlyCanceledSubscriptionWebHookNotification notification)

    public void renewedSubscriptionNotificationHandler(RecurlyRenewedSubscriptionWebHookNotification notification)

    public void newSubscriptionNotificationHandler(RecurlyNewSubscriptionWebHookNotification notification)

    public void expiredSubscriptionNotificationHandler(RecurlyExpiredSubscriptionWebHookNotification notification)

    public void updatedSubscriptionNotificationHandler(RecurlyUpdatedSubscriptionWebHookNotification notification)

}