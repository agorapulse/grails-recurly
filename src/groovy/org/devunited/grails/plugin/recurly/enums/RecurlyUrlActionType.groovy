package org.devunited.grails.plugin.recurly.enums

public enum RecurlyUrlActionType {
    ACCOUNT("accounts"),
    LIST_ACTIVE_SUBSCRIBERS("accounts?show=active_subscribers"),
    LIST_NON_SUBSCRIBERS("accounts?show=non_subscribers"),
    LIST_PAST_DUE_SUBSCRIBERS("accounts?show=pastdue_subscribers"),
    UPDATE_ACCOUNT("accounts/[id]"),
    DELETE_ACCOUNT("accounts/[id]"),
    GET_ACCOUNT_DETAILS("accounts/[id]"),
    CREATE_PLAN("company/plans"),
    LIST_ALL_PLANS("company/plans"),
    UPDATE_PLAN("company/plans/[id]"),
    DELETE_PLAN("company/plans/[id]"),
    GET_PLAN_DETAILS("company/plans/[id]"),
    CREATE_SUBSCRIPTION("accounts/[id]/subscription"),
    DELETE_SUBSCRIPTION("accounts/[id]/subscription"),
    UPGRADE_SUBSCRIPTION("accounts/[id]/subscription"),
    GET_SUBSCRIPTION_DETAILS("accounts/[id]/subscription"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITH_PARTIAL_REFUND("accounts/[id]/subscription?refund=partial"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITH_FULL_REFUND("accounts/[id]/subscription?refund=full"),
    DELETE_SUBSCRIPTION_INSTANTLY_WITHOUT_REFUND("accounts/[id]/subscription?refund=none"),
    GET_BILLING_DETAILS("accounts/[id]/billing_info"),
    DELETE_BILLING_DETAILS("accounts/[id]/billing_info"),
    CREATE_OR_UPDATE_BILLING_DETAILS("accounts/[id]/billing_info"),
    GET_TRANSACTION_DETAILS("transactions/[id]"),
    LIST_ACCOUNT_TRANSACTIONS("accounts/[id]/transactions"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_SUCCESSFUL("accounts/[id]/transactions?show=successful"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_FAILED("accounts/[id]/transactions?show=failed"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_PAYMENTS("accounts/[id]/transactions?show=payments"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_VOIDED("accounts/[id]/transactions?show=voided"),
    LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_REFUNDS("accounts/[id]/transactions?show=refunds"),
    LIST_TRANSACTIONS("transactions"),
    LIST_TRANSACTIONS_MARKED_AS_SUCCESSFUL("transactions?show=successful"),
    LIST_TRANSACTIONS_MARKED_AS_FAILED("transactions?show=failed"),
    LIST_TRANSACTIONS_MARKED_AS_PAYMENTS("transactions?show=payments"),
    LIST_TRANSACTIONS_MARKED_AS_VOIDED("transactions?show=voided"),
    LIST_TRANSACTIONS_MARKED_AS_REFUNDS("transactions?show=refunds")

    String value

    RecurlyUrlActionType(String value){
        this.value = value
    }

    public String toString() {
        return value
    }
}