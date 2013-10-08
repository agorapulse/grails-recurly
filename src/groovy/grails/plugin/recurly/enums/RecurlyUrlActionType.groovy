package grails.plugin.recurly.enums

public enum RecurlyUrlActionType {
    // Accounts
    ACCOUNTS("accounts"),
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
    SUBSCRIPTIONS("subscriptions"),
    LIST_ACCOUNT_SUBSCRIPTIONS("accounts/[id]/subscriptions"),
    CREATE_SUBSCRIPTION("subscriptions"),
    CANCEL_SUBSCRIPTION("subscriptions/[id]/cancel"),
    REACTIVATE_SUBSCRIPTION("subscriptions/[id]/reactivate"),
    UPDATE_SUBSCRIPTION("subscriptions/[id]"),
    GET_SUBSCRIPTION_DETAILS("subscriptions/[id]"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITH_PARTIAL_REFUND("subscriptions/[id]/terminate?refund=partial"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITH_FULL_REFUND("subscriptions/[id]/terminate?refund=full"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITHOUT_REFUND("subscriptions/[id]/terminate?refund=none"),
    // Billing Info
    GET_BILLING_INFO("accounts/[id]/billing_info"),
    DELETE_BILLING_INFO("accounts/[id]/billing_info"),
    CREATE_OR_UPDATE_BILLING_INFO("accounts/[id]/billing_info"),
    // Transactions
    TRANSACTIONS("transactions"),
    LIST_ACCOUNT_TRANSACTIONS("accounts/[id]/transactions"),
    GET_TRANSACTION_DETAILS("transactions/[id]"),
    // Invoices
    INVOICES("invoices"),
    LIST_ACCOUNT_INVOICES("accounts/[id]/invoices"),
    GET_INVOICE_DETAILS("invoices/[id]"),
    // JS
    GET_JS_RESULT("recurly_js/result/[id]")

    String value

    RecurlyUrlActionType(String value){
        this.value = value
    }

    public String toString() {
        return value
    }
}