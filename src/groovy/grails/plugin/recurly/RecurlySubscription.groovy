package grails.plugin.recurly

import grails.plugin.recurly.enums.RecurlySubscriptionChangeTimeFrame
import grails.plugin.recurly.enums.RecurlySubscriptionRefund
import grails.plugin.recurly.enums.RecurlySubscriptionState
import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlySubscriptionProcessor

class RecurlySubscription extends RecurlyRESTResource {

    String uuid
    String planCode
    String couponCode
    Date trialEndsAt
    Integer unitAmountInCents
    Integer quantity
    String currency
    List<RecurlySubscriptionAddOn> addOns = []
    RecurlyAccount account

    //Read Only Properties
    String planName
    Integer planVersion
    RecurlySubscriptionState state
    Integer totalAmountInCents
    Date activatedAt
    Date cancelledAt
    Date expiresAt
    Date currentPeriodStartedAt
    Date currentPeriodEndsAt
    Date trialStartedAt
    RecurlySubscriptionPendingChanges pendingChanges

    //Only Required For Creating Subscription
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
        "RecurlySubscription(uuid:'$uuid', planCode:'$planCode', state:'$state', unitAmountInCents:'$unitAmountInCents', currency:'$currency')"
    }

    // STATIC REST METHODS

    static String cancel(RecurlySubscription recurlySubscription) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).cancel())
    }

    static RecurlySubscription create(RecurlySubscription recurlySubscription) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).create()) as RecurlySubscription
    }

    static RecurlySubscription fetch(String uuid) {
        handleResponse(new RecurlySubscriptionProcessor().getSubscriptionDetails(uuid)) as RecurlySubscription
    }

    static String reactivate(RecurlySubscription recurlySubscription) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).reactivate())
    }

    static String terminate(String uuid, RecurlySubscriptionRefund refund = RecurlySubscriptionRefund.NONE) {
        handleResponse(new RecurlySubscriptionProcessor().terminate(uuid, refund))
    }

    static RecurlySubscription update(RecurlySubscription recurlySubscription, RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame = RecurlySubscriptionChangeTimeFrame.NOW) {
        handleResponse(new RecurlySubscriptionProcessor(recurlySubscription).update(recurlySubscriptionChangeTimeFrame)) as RecurlySubscription
    }

}