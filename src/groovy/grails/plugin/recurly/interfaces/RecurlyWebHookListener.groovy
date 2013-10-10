package grails.plugin.recurly.interfaces

import grails.plugin.recurly.notifications.RecurlyReactivatedAccountWebHookNotification
import grails.plugin.recurly.notifications.RecurlySuccessfulPaymentWebHookNotification
import grails.plugin.recurly.notifications.RecurlyFailedRenewalWebHookNotification
import grails.plugin.recurly.notifications.RecurlyCanceledSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyRenewedSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyNewSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyExpiredSubscriptionWebHookNotification
import grails.plugin.recurly.notifications.RecurlyChangedSubscriptionWebHookNotification

/**
 * Created by IntelliJ IDEA.
 * User: kushal
 * Date: 10/3/11
 * Time: 7:10 PM
 * To change this template use File | Settings | File Templates.
 */
public interface RecurlyWebHookListener {

    public static recurlyWebHook = true

    public void successfulPaymentNotificationHandler(RecurlySuccessfulPaymentWebHookNotification notification)

    public void failedRenewalPaymentNotificationHandler(RecurlyFailedRenewalWebHookNotification notification)

    public void cancelledSubscriptionNotificationHandler(RecurlyCanceledSubscriptionWebHookNotification notification)

    public void renewedSubscriptionNotificationHandler(RecurlyRenewedSubscriptionWebHookNotification notification)

    public void newSubscriptionNotificationHandler(RecurlyNewSubscriptionWebHookNotification notification)

    public void expiredSubscriptionNotificationHandler(RecurlyExpiredSubscriptionWebHookNotification notification)

    public void subscriptionUpdatedNotificationHandler(RecurlyChangedSubscriptionWebHookNotification notification)

    public void reactivatedAccountNotificationHandler(RecurlyReactivatedAccountWebHookNotification notification)

}