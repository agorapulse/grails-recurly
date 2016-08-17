package grails.plugin.recurly.enums

enum RecurlySubscriptionRefund {

    NONE('none'),
    FULL('full'),
    PARTIAL('partial')

    String value

    RecurlySubscriptionRefund(String value) {
        this.value = value
    }

    public static boolean contains(String test) {
        for (RecurlySubscriptionRefund value : RecurlySubscriptionRefund.values()) {
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
