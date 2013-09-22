package org.devunited.grails.plugin.recurly

class RecurlySubscription {

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
    RecurlyBillingDetails billingDetails


    public Boolean hasPendingChanges() {
        return (this.pendingChanges.planCode != null && this.pendingChanges.planCode != "")
    }
}