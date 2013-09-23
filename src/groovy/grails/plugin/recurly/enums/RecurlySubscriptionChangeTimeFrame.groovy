package grails.plugin.recurly.enums

public enum RecurlySubscriptionChangeTimeFrame {

    NOW("now"),
    RENEWAL("renewal")

    String value

    RecurlySubscriptionChangeTimeFrame(String value) {
        this.value = value
    }

    public String toString() {
        return value
    }

}