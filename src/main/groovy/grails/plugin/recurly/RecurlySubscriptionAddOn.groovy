package grails.plugin.recurly

class RecurlySubscriptionAddOn {

    String addOnCode
    Integer quantity
    Integer unitAmountInCents

    String toString() {
        "RecurlySubscriptionAddOn(addOnCode:'$addOnCode', quantity:'$quantity', unitAmountInCents:'$unitAmountInCents')"
    }

}