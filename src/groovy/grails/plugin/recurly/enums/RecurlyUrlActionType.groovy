package grails.plugin.recurly.enums

public enum RecurlyUrlActionType {
    // Accounts
    ACCOUNT("accounts"),
    LIST_ACTIVE_SUBSCRIBERS("accounts?state=active"),
    LIST_NON_SUBSCRIBERS("accounts?state=closed"),
    LIST_PAST_DUE_SUBSCRIBERS("accounts?state=past_due"),
    UPDATE_ACCOUNT("accounts/[id]"),
    DELETE_ACCOUNT("accounts/[id]"),
    GET_ACCOUNT_DETAILS("accounts/[id]"),
    // Plans
    PLANS("plans"),
    CREATE_PLAN("plans"),
    UPDATE_PLAN("plans/[id]"),
    DELETE_PLAN("plans/[id]"),
    GET_PLAN_DETAILS("plans/[id]"),
    // Subscriptions
    SUBSCRIPTION("subscriptions"),
    LIST_ACCOUNT_SUBSCRIPTIONS("accounts/[id]/subscriptions"),
    CREATE_SUBSCRIPTION("subscriptions"),
    CANCEL_SUBSCRIPTION("subscriptions/[id]/cancel"),
    REACTIVATE_SUBSCRIPTION("subscriptions/[id]/reactivate"),
    UPDATE_SUBSCRIPTION("subscriptions/[id]"),
    GET_SUBSCRIPTION_DETAILS("subscriptions/[id]"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITH_PARTIAL_REFUND("subscriptions/[id]?refund=partial"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITH_FULL_REFUND("subscriptions/[id]?refund=full"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITHOUT_REFUND("subscriptions/[id]?refund=none"),
    // Billing Info
    GET_BILLING_INFO("accounts/[id]/billing_info"),
    DELETE_BILLING_INFO("accounts/[id]/billing_info"),
    CREATE_OR_UPDATE_BILLING_INFO("accounts/[id]/billing_info"),
    // Transactions
    TRANSACTIONS("transactions"),
    LIST_ACCOUNT_TRANSACTIONS("accounts/[id]/transactions"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_SUCCESSFUL("accounts/[id]/transactions?state=successful"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_FAILED("accounts/[id]/transactions?state=failed"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_PAYMENTS("accounts/[id]/transactions?type=purchase"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_VOIDED("accounts/[id]/transactions?state=voided"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_REFUNDS("accounts/[id]/transactions?type=refund"),
    LIST_TRANSACTIONS_MARKED_AS_SUCCESSFUL("transactions?state=successful"),
    LIST_TRANSACTIONS_MARKED_AS_FAILED("transactions?state=failed"),
    LIST_TRANSACTIONS_MARKED_AS_PAYMENTS("transactions?type=purchase"),
    LIST_TRANSACTIONS_MARKED_AS_VOIDED("transactions?state=voided"),
    LIST_TRANSACTIONS_MARKED_AS_REFUNDS("transactions?type=refund"),
    GET_TRANSACTION_DETAILS("transactions/[id]")

    String value

    RecurlyUrlActionType(String value){
        this.value = value
    }

    public String toString() {
        return value
    }
}