package grails.plugin.recurly.enums

enum RecurlyTransactionType {

    AUTHORIZATION('authorization'),
    REFUND('refund'),
    PURCHASE('purchase')

    String value

    RecurlyTransactionType(String value) {
        this.value = value
    }

    public static boolean contains(String test) {
        for (RecurlyTransactionType value : RecurlyTransactionType.values()) {
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
