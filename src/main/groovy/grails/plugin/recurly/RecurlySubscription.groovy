package grails.plugin.recurly

import grails.plugin.recurly.enums.RecurlySubscriptionChangeTimeFrame
import grails.plugin.recurly.enums.RecurlySubscriptionRefund
import grails.plugin.recurly.enums.RecurlySubscriptionState
import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlySubscriptionProcessor

class RecurlySubscription extends RecurlyRESTResource {

    String uuid
    String accountCode
    String planCode
    String planName
    RecurlySubscriptionState state
    Integer unitAmountInCents
    String currency
    Integer quantity
    String couponCode
    Date activatedAt
    Date cancelledAt
    Date expiresAt
    Date currentPeriodStartedAt
    Date currentPeriodEndsAt
    Date trialStartedAt
    Date trialEndsAt
    List<RecurlySubscriptionAddOn> addOns = []

    RecurlySubscriptionPendingChanges pendingChanges

    //Only Required For Creating Subscription
    RecurlyAccount account
    RecurlyBillingInfo billingInfo

    public Boolean hasPendingChanges() {
         this.pendingChanges.planCode != null && this.pendingChanges.planCode != ''
    }

    String delete(RecurlySubscriptionRefund refund = RecurlySubscriptionRefund.NONE) {
        terminate(this.uuid, refund)
    }

    RecurlySubscription save(RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame = RecurlySubscriptionChangeTimeFrame.NOW) {
        if (!uuid) {
            create(this)
        } else {
            update(this, recurlySubscriptionChangeTimeFrame)
        }
    }

    String toString() {
        "RecurlySubscription(uuid:'$uuid', accountCode:'$accountCode', planCode:'$planCode', state:'$state', unitAmountInCents:'$unitAmountInCents', currency:'$currency')"
    }

    // STATIC REST METHODS

    static String cancel(RecurlySubscription recurlySubscription) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).cancel(recurlySubscription.uuid))
    }

    static RecurlySubscription create(RecurlySubscription recurlySubscription) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).create()) as RecurlySubscription
    }

    static RecurlySubscription fetch(String uuid) {
        handleResponse(new RecurlySubscriptionProcessor().getSubscriptionDetails(uuid)) as RecurlySubscription
    }

    static String reactivate(RecurlySubscription recurlySubscription) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).reactivate(recurlySubscription.uuid))
    }

    static String terminate(String uuid, RecurlySubscriptionRefund refund = RecurlySubscriptionRefund.NONE) {
        handleResponse(new RecurlySubscriptionProcessor().terminate(uuid, refund))
    }

    static RecurlySubscription update(RecurlySubscription recurlySubscription, RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame = RecurlySubscriptionChangeTimeFrame.NOW) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).update(recurlySubscriptionChangeTimeFrame)) as RecurlySubscription
    }

}