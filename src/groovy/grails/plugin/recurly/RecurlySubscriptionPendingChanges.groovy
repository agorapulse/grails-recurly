package grails.plugin.recurly

class RecurlySubscriptionPendingChanges {

    String planCode
    String activatesAt
    Integer quantity

    String toString() {
        "RecurlySubscriptionPendingChanges(planCode:'$planCode', activatesAt:'$activatesAt', quantity:'$quantity')"
    }

}
