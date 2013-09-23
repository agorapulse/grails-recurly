package grails.plugin.recurly

class RecurlySubscriptionPlan {

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

}