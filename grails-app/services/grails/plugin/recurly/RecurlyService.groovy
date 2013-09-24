package grails.plugin.recurly

import grails.plugin.recurly.enums.RecurlyAccountState
import grails.plugin.recurly.enums.RecurlySubscriptionChangeTimeFrame
import grails.plugin.recurly.enums.RecurlySubscriptionRefund
import grails.plugin.recurly.enums.RecurlyTransactionState
import grails.plugin.recurly.enums.RecurlyTransactionType
import grails.plugin.recurly.processors.*
import grails.plugin.recurly.templates.Response

class RecurlyService {

    static transactional = false

    //ACCOUNT RELATED SERVICES

    public Response<RecurlyAccount> createAccount(RecurlyAccount recurlyAccount) {
        return new RecurlyAccountProcessor(recurlyAccount).create()
    }

    public Response<RecurlyAccount> updateAccount(RecurlyAccount recurlyAccount) {
        return new RecurlyAccountProcessor(recurlyAccount).update()
    }

    public Response<String/*accountCode*/> deleteAccount(String accountCode) {
        return new RecurlyAccountProcessor().delete(accountCode)
    }

    public Response<RecurlyAccount> getAccountDetails(String accountCode) {
        return new RecurlyAccountProcessor().getAccountDetails(accountCode)
    }

    public Response<List<RecurlyAccount>> listAccounts(Map query = [:]) {
        return new RecurlyAccountProcessor().listAccounts()
    }

    public Response<List<RecurlyAccount>> listActiveSubscribers() {
        return new RecurlyAccountProcessor().listAccounts(state: RecurlyAccountState.ACTIVE)
    }

    public Response<List<RecurlyAccount>> listNonSubscribers() {
        return new RecurlyAccountProcessor().listAccounts(state: RecurlyAccountState.CLOSED)
    }

    public Response<List<RecurlyAccount>> listPastDueSubscribers() {
        return new RecurlyAccountProcessor().listAccounts(state: RecurlyAccountState.PAST_DUE)
    }

    //SUBSCRIPTION PLAN RELATED SERVICES

    public Response<RecurlyPlan> createSubscriptionPlan(RecurlyPlan recurlySubscriptionPlan) {
        return new RecurlyPlanProcessor(recurlySubscriptionPlan).create()
    }

    public Response<RecurlyPlan> updateSubscriptionPlan(RecurlyPlan recurlySubscriptionPlan) {
        return new RecurlyPlanProcessor(recurlySubscriptionPlan).update()
    }

    public Response<RecurlyPlan> deleteSubscriptionPlan(RecurlyPlan recurlySubscriptionPlan) {
        return new RecurlyPlanProcessor(recurlySubscriptionPlan).delete()
    }

    public Response<List<RecurlyPlan>> listAllSubscriptionPlans() {
        return new RecurlyPlanProcessor().listPlans()
    }

    public Response<RecurlyPlan> getSubscriptionPlanDetails(String planCode) {
        return new RecurlyPlanProcessor().getPlanDetails(planCode)
    }

    //SUBSCRIPTION RELATED SERVICES

    /*public Response<List<RecurlySubscription>> listAllSubscriptions() {
        return new RecurlySubscriptionProcessor().listAllSubscriptions()
    }

    public Response<List<RecurlySubscription>> listAllAccountSubscriptions(String accountCode) {
        return new RecurlySubscription().listAllAccountSubscriptions(accountCode)
    }*/

    public Response<RecurlySubscription> createSubscription(RecurlySubscription recurlySubscription) {
        return new RecurlySubscriptionProcessor(recurlySubscription).create()
    }

    public Response<RecurlySubscription> updateSubscription(RecurlySubscription recurlySubscription, RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame) {
        return new RecurlySubscriptionProcessor(recurlySubscription).update(recurlySubscriptionChangeTimeFrame)
    }

    public Response<RecurlySubscription> getSubscriptionDetails(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().getSubscriptionDetails(subscriptionUuid)
    }

    public Response<String/*accountCode*/> cancelSubscription(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().cancel(subscriptionUuid)
    }

    public Response<String/*accountCode*/> reactivateSubscription(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().reactivate(subscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithPartialRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminate(subscriptionUuid, RecurlySubscriptionRefund.PARTIAL)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithFullRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminate(subscriptionUuid, RecurlySubscriptionRefund.FULL)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithNoRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminate(subscriptionUuid, RecurlySubscriptionRefund.NONE)
    }

    //BILLING DETAILS RELATED SERVICES

    public Response<RecurlyBillingInfo> getBillingDetails(String accountCode) {
        return new RecurlyBillingInfoProcessor().getBillingDetails(accountCode)
    }

    public Response<RecurlyBillingInfo> createOrUpdateBillingDetails(RecurlyBillingInfo billingDetails, String accountCode) {
        return new RecurlyBillingInfoProcessor(billingDetails).createOrUpdate(accountCode)
    }

    public Response<String/*accountCode*/> deleteBillingDetails(String accountCode) {
        return new RecurlyBillingInfoProcessor().delete(accountCode)
    }

    //TRANSACTION RELATED SERVICES

    public Response<RecurlyTransaction> getTransactionDetails(String transactionId) {
        return new RecurlyTransactionProcessor().getTransactionDetails(transactionId)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactions(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsSuccessful(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionState.SUCCESSFUL)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsFailed(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionState.FAILED)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsPayments(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionType.PURCHASE)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsRefunds(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionType.REFUND)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsVoided(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionState.VOIDED)
    }

    public Response<List<RecurlyTransaction>> listAllTransactions() {
        return new RecurlyTransactionProcessor().listTransactions()
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsSuccessful() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionState.SUCCESSFUL)
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsFailed() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionState.FAILED)
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsPayments() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionType.PURCHASE)
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsRefunds() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionType.REFUND)
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsVoided() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionState.VOIDED)
    }

}