package grails.plugin.recurly.enums

enum RecurlyInvoiceState {

    ALL('all'),
    OPEN('open'),
    COLLECTED('collected'),
    FAILED('failed'),
    PAST_DUE('past_due')

    String value

    RecurlyInvoiceState(String value) {
        this.value = value
    }

    public static boolean contains(String test) {
        for (RecurlyInvoiceState value : RecurlyInvoiceState.values()) {
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
