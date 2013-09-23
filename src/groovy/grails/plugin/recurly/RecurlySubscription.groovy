package grails.plugin.recurly

class RecurlySubscription {

    String uuid
    String planCode
    String couponCode
    String trialEndsAt
    Integer unitAmountInCents
    Integer quantity
    List<RecurlySubscriptionAddOn> addOns = []
    RecurlyAccount account

    //Read Only Properties
    String planName
    Integer planVersion
    String state
    Integer totalAmountInCents
    String activatedAt
    String cancelledAt
    String expiresAt
    String currentPeriodStartedAt
    String currentPeriodEndsAt
    String trialStartedAt
    RecurlySubscriptionPendingChanges pendingChanges

    //Only Required For Creating Subscription
    RecurlyBillingInfo billingInfo


    public Boolean hasPendingChanges() {
        return (this.pendingChanges.planCode != null && this.pendingChanges.planCode != "")
    }

    String toString() {
        "RecurlySubscription(uuid:'$uuid', planCode:'$planCode', unitAmountInCents:'$unitAmountInCents', account:$account)"
    }

}