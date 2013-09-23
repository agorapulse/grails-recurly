package grails.plugin.recurly.interfaces

import grails.plugin.recurly.RecurlySuccessfulPaymentWebHookNotification
import grails.plugin.recurly.RecurlyFailedRenewalWebHookNotification
import grails.plugin.recurly.RecurlyCanceledSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyRenewedSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyNewSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyExpiredSubscriptionWebHookNotification
import grails.plugin.recurly.RecurlyChangedSubscriptionWebHookNotification

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

}