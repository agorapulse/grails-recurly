package org.devunited.grails.plugin.recurly.enums

enum RecurlyTransactionState {

    SUCCESSFUL('successful'),
    FAILED('failed'),
    VOIDED('voided')

    String value

    RecurlyTransactionState(String value) {
        this.value = value
    }

    public static boolean contains(String test) {
        for (RecurlyTransactionState value : RecurlyTransactionState.values()) {
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
