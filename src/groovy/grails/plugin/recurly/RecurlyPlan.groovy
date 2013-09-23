package grails.plugin.recurly

class RecurlyPlan {

    String planCode
    String name
    String description
    String successUrl
    String cancelUrl
    String createdAt
    String planIntervalUnit
    String trialIntervalUnit
    Integer unitAmountInCents
    Integer setupFeeInCents
    Integer planIntervalLength
    Integer trialIntervalLength

    String toString() {
        "RecurlySubscriptionPlan(planCode:'$planCode', name:'$name', unitAmountInCents:'$unitAmountInCents')"
    }

    // STATIC REST METHODS

}