package grails.plugin.recurly.enums

enum RecurlySubscriptionState {

    ACTIVE('active'),
    CANCELED('canceled'),
    EXPIRED('expired'),
    FUTURE('future'),
    IN_TRIAL('in_trial'),
    LIVE('live'),
    PAST_DUE('past_due')

    String value

    RecurlySubscriptionState(String value) {
        this.value = value
    }

    public static boolean contains(String test) {
        for (RecurlySubscriptionState value : values()) {
            if (value.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    public String toString() {
        return value
    }

}
