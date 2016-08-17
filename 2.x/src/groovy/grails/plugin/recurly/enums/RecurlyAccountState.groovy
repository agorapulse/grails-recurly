package grails.plugin.recurly.enums

public enum RecurlyAccountState {

    ACTIVE('active'),
    CLOSED('closed'),
    PAST_DUE('past_due')

    String value

    RecurlyAccountState(String value) {
        this.value = value
    }

    public static boolean contains(String test) {
        for (RecurlyAccountState value : RecurlyAccountState.values()) {
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
