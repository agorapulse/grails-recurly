package grails.plugin.recurly.enums

public enum WebHookResponseType {

    // Account notifications
    NEW_ACCOUNT_NOTIFICATION("New Account"),
    CANCELED_ACCOUNT_NOTIFICATION("Canceled Account"),
    BILLING_INFO_UPDATED_NOTIFICATION("Billing Info Updated"),
    REACTIVATED_ACCOUNT_NOTIFICATION("Reactivated Account"),
    // Payment notifications
    SUCCESSFUL_PAYMENT_NOTIFICATION("Successful Payment"),
    SUCCESSFUL_REFUND_NOTIFICATION("Successful Refund"),
    VOID_PAYMENT_NOTIFICATION("Void Payment"),
    FAILED_PAYMENT_NOTIFICATION("Failed Payment"),
    // Subscription notifications
    NEW_SUBSCRIPTION_NOTIFICATION("New Subscription"),
    CANCELED_SUBSCRIPTION_NOTIFICATION("Canceled Subscription"),
    EXPIRED_SUBSCRIPTION_NOTIFICATION("Expired Subscription"),
    RENEWED_SUBSCRIPTION_NOTIFICATION("Renewed Subscription"),
    UPDATED_SUBSCRIPTION_NOTIFICATION("Updated Subscription")

    String value

    WebHookResponseType(String value) {
        this.value = value
    }

    public String toString() {
        return value
    }
}
