package grails.plugin.recurly

import grails.plugin.recurly.enums.RecurlyAccountState
import grails.plugin.recurly.templates.Response
import grails.plugin.recurly.processors.RecurlyAccountProcessor
import grails.plugin.recurly.processors.RecurlySubscriptionPlanProcessor
import grails.plugin.recurly.processors.RecurlySubscriptionProcessor
import grails.plugin.recurly.enums.RecurlySubscriptionChangeTimeFrame
import grails.plugin.recurly.helpers.WebHookNotification
import grails.plugin.recurly.processors.WebHookNotificationProcessor
import grails.plugin.recurly.processors.RecurlyTransactionProcessor
import grails.plugin.recurly.processors.RecurlyBillingInfoProcessor

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

    public Response<RecurlySubscriptionPlan> createSubscriptionPlan(RecurlySubscriptionPlan recurlySubscriptionPlan) {
        return new RecurlySubscriptionPlanProcessor(recurlySubscriptionPlan).create()
    }

    public Response<RecurlySubscriptionPlan> updateSubscriptionPlan(RecurlySubscriptionPlan recurlySubscriptionPlan) {
        return new RecurlySubscriptionPlanProcessor(recurlySubscriptionPlan).update()
    }

    public Response<RecurlySubscriptionPlan> deleteSubscriptionPlan(RecurlySubscriptionPlan recurlySubscriptionPlan) {
        return new RecurlySubscriptionPlanProcessor(recurlySubscriptionPlan).delete()
    }

    public Response<List<RecurlySubscriptionPlan>> listAllSubscriptionPlans() {
        return new RecurlySubscriptionPlanProcessor().listAllSubscriptionPlans()
    }

    public Response<RecurlySubscriptionPlan> getSubscriptionPlanDetails(String planCode) {
        return new RecurlySubscriptionPlanProcessor().getSubscriptionPlanDetails(planCode)
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

    public Response<RecurlySubscription> getSubscriptionDetails(String accountCode) {
        return new RecurlySubscriptionProcessor().getSubscriptionDetails(accountCode)
    }

    public Response<String/*accountCode*/> cancelSubscription(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().cancel(subscriptionUuid)
    }

    public Response<String/*accountCode*/> reactivateSubscription(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().reactivate(subscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithPartialRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminateWithPartialRefund(subscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithFullRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminateWithFullRefund(subscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithNoRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminateWithNoRefund(subscriptionUuid)
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

    public Response<List<RecurlyTransaction>> listAllAccountTransactions(String accountId) {
        return new RecurlyTransactionProcessor().listAllAccountTransactions(accountId)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsSuccessful(String accountId) {
        return new RecurlyTransactionProcessor().listAllAccountTransactionsWhichAreMarkedAsSuccessful(accountId)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsFailed(String accountId) {
        return new RecurlyTransactionProcessor().listAllAccountTransactionsWhichAreMarkedAsFailed(accountId)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsPayments(String accountId) {
        return new RecurlyTransactionProcessor().listAllAccountTransactionsWhichAreMarkedAsPayments(accountId)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsRefunds(String accountId) {
        return new RecurlyTransactionProcessor().listAllAccountTransactionsWhichAreMarkedAsRefunds(accountId)
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsVoided(String accountId) {
        return new RecurlyTransactionProcessor().listAllAccountTransactionsWhichAreMarkedAsVoided(accountId)
    }

    public Response<List<RecurlyTransaction>> listAllTransactions() {
        return new RecurlyTransactionProcessor().listAllTransactions()
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsSuccessful() {
        return new RecurlyTransactionProcessor().listAllTransactionsWhichAreMarkedAsSuccessful()
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsFailed() {
        return new RecurlyTransactionProcessor().listAllTransactionsWhichAreMarkedAsFailed()
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsPayments() {
        return new RecurlyTransactionProcessor().listAllTransactionsWhichAreMarkedAsPayments()
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsRefunds() {
        return new RecurlyTransactionProcessor().listAllTransactionsWhichAreMarkedAsRefunds()
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsVoided() {
        return new RecurlyTransactionProcessor().listAllTransactionsWhichAreMarkedAsVoided()
    }

    //WEB HOOK RELATED SERVICES

    public WebHookNotification processNotification(String xml) {
        return new WebHookNotificationProcessor(xml).process()
    }
}