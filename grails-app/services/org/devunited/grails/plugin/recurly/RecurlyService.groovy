package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.templates.Response
import org.devunited.grails.plugin.recurly.processors.RecurlyAccountProcessor
import org.devunited.grails.plugin.recurly.processors.RecurlySubscriptionPlanProcessor
import org.devunited.grails.plugin.recurly.processors.RecurlySubscriptionProcessor
import org.devunited.grails.plugin.recurly.enums.RecurlySubscriptionChangeTimeFrame
import org.devunited.grails.plugin.recurly.helpers.WebHookNotification
import org.devunited.grails.plugin.recurly.processors.WebHookNotificationProcessor
import org.devunited.grails.plugin.recurly.processors.RecurlyTransactionProcessor
import org.devunited.grails.plugin.recurly.processors.RecurlyBillingDetailsProcessor

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

    public Response<List<RecurlyAccount>> listAllAccounts() {
        return new RecurlyAccountProcessor().listAllAccounts()
    }

    public Response<List<RecurlyAccount>> listActiveSubscribers() {
        return new RecurlyAccountProcessor().listActiveSubscribers()
    }

    public Response<List<RecurlyAccount>> listNonSubscribers() {
        return new RecurlyAccountProcessor().listNonSubscribers()
    }

    public Response<List<RecurlyAccount>> listPastDueSubscribers() {
        return new RecurlyAccountProcessor().listPastDueSubscribers()
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

    public Response<String/*accountCode*/> cancelSubscription(String recurlySubscriptionUuid) {
        return new RecurlySubscriptionProcessor().cancel(recurlySubscriptionUuid)
    }

    public Response<String/*accountCode*/> reactivateSubscription(String recurlySubscriptionUuid) {
        return new RecurlySubscriptionProcessor().reactivate(recurlySubscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithPartialRefund(String recurlySubscriptionUuid) {
        new RecurlySubscriptionProcessor().terminateWithPartialRefund(recurlySubscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithFullRefund(String recurlySubscriptionUuid) {
        new RecurlySubscriptionProcessor().terminateWithFullRefund(recurlySubscriptionUuid)
    }

    public Response<String/*accountCode*/> terminateSubscriptionWithNoRefund(String recurlySubscriptionUuid) {
        new RecurlySubscriptionProcessor().terminateWithNoRefund(recurlySubscriptionUuid)
    }

    //BILLING DETAILS RELATED SERVICES

    public Response<RecurlyBillingDetails> getBillingDetails(String accountCode) {
        return new RecurlyBillingDetailsProcessor().getBillingDetails(accountCode)
    }

    public Response<RecurlyBillingDetails> createOrUpdateBillingDetails(RecurlyBillingDetails recurlyBillingDetails, String accountCode) {
        return new RecurlyBillingDetailsProcessor(recurlyBillingDetails).createOrUpdate(accountCode)
    }

    public Response<String/*accountCode*/> deleteBillingDetails(String accountCode) {
        return new RecurlyBillingDetailsProcessor().delete(accountCode)
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