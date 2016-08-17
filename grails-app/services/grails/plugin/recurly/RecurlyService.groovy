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

    /**
     * Create account
     * @param recurlyAccount
     * @return RecurlyAccount
     */
    public Response<RecurlyAccount> createAccount(RecurlyAccount recurlyAccount) {
        return new RecurlyAccountProcessor(recurlyAccount).create()
    }

    /**
     * Update account
     * @param recurlyAccount
     * @return RecurlyAccount
     */
    public Response<RecurlyAccount> updateAccount(RecurlyAccount recurlyAccount) {
        return new RecurlyAccountProcessor(recurlyAccount).update()
    }

    /**
     * Delete account
     * @param accountCode
     * @return String account code
     */
    public Response<String/*accountCode*/> deleteAccount(String accountCode) {
        return new RecurlyAccountProcessor().delete(accountCode)
    }

    /**
     * Get account details
     * @param accountCode
     * @return RecurlyAccount
     */
    public Response<RecurlyAccount> getAccountDetails(String accountCode) {
        return new RecurlyAccountProcessor().getAccountDetails(accountCode)
    }

    /**
     * List all accounts
     * @param query
     * @return List of accounts
     */
    public Response<List<RecurlyAccount>> listAccounts(Map query = [:]) {
        return new RecurlyAccountProcessor().listAccounts()
    }

    /**
     * List active subscribers
     * @return List of active subscriber accounts
     */
    public Response<List<RecurlyAccount>> listActiveSubscribers() {
        return new RecurlyAccountProcessor().listAccounts(state: RecurlyAccountState.ACTIVE)
    }

    /**
     * List non subscribers
     * @return List of non subscriber accounts
     */
    public Response<List<RecurlyAccount>> listNonSubscribers() {
        return new RecurlyAccountProcessor().listAccounts(state: RecurlyAccountState.CLOSED)
    }

    /**
     * List past due subscribers
     * @return List of past due subscriber accounts
     */
    public Response<List<RecurlyAccount>> listPastDueSubscribers() {
        return new RecurlyAccountProcessor().listAccounts(state: RecurlyAccountState.PAST_DUE)
    }

    //SUBSCRIPTION PLAN RELATED SERVICES

    /**
     * Create subscription plan
     * @param recurlySubscriptionPlan
     * @return RecurlyPlan
     */
    public Response<RecurlyPlan> createSubscriptionPlan(RecurlyPlan recurlySubscriptionPlan) {
        return new RecurlyPlanProcessor(recurlySubscriptionPlan).create()
    }

    /**
     * Update subscription plan
     * @param recurlySubscriptionPlan
     * @return RecurlyPlan
     */
    public Response<RecurlyPlan> updateSubscriptionPlan(RecurlyPlan recurlySubscriptionPlan) {
        return new RecurlyPlanProcessor(recurlySubscriptionPlan).update()
    }

    /**
     * Delete subscription plan
     * @param recurlySubscriptionPlan
     * @return RecurlyPlan
     */
    public Response<String> deleteSubscriptionPlan(RecurlyPlan recurlySubscriptionPlan) {
        return new RecurlyPlanProcessor(recurlySubscriptionPlan).delete(recurlySubscriptionPlan.planCode)
    }

    /**
     * List all subscription plans
     * @return
     */
    public Response<List<RecurlyPlan>> listAllSubscriptionPlans() {
        return new RecurlyPlanProcessor().listPlans()
    }

    /**
     * Get subscription plan details
     * @param planCode
     * @return
     */
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

    /**
     * Create subscription
     * @param recurlySubscription
     * @return RecurlySubscription
     */
    public Response<RecurlySubscription> createSubscription(RecurlySubscription recurlySubscription) {
        return new RecurlySubscriptionProcessor(recurlySubscription).create()
    }

    /**
     * Update subscription
     * @param recurlySubscription
     * @param recurlySubscriptionChangeTimeFrame
     * @return RecurlySubscription
     */
    public Response<RecurlySubscription> updateSubscription(RecurlySubscription recurlySubscription, RecurlySubscriptionChangeTimeFrame recurlySubscriptionChangeTimeFrame) {
        return new RecurlySubscriptionProcessor(recurlySubscription).update(recurlySubscriptionChangeTimeFrame)
    }

    /**
     * Get subscription details
     * @param subscriptionUuid
     * @return RecurlySubscription
     */
    public Response<RecurlySubscription> getSubscriptionDetails(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().getSubscriptionDetails(subscriptionUuid)
    }

    /**
     * Cancel subscription
     * @param subscriptionUuid
     * @return String account code
     */
    public Response<String/*accountCode*/> cancelSubscription(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().cancel(subscriptionUuid)
    }

    /**
     * Reactivate subscription
     * @param subscriptionUuid
     * @return String account code
     */
    public Response<String/*accountCode*/> reactivateSubscription(String subscriptionUuid) {
        return new RecurlySubscriptionProcessor().reactivate(subscriptionUuid)
    }

    /**
     * Terminate subscription with partial refund
     * @param subscriptionUuid
     * @return String account code
     */
    public Response<String/*accountCode*/> terminateSubscriptionWithPartialRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminate(subscriptionUuid, RecurlySubscriptionRefund.PARTIAL)
    }

    /**
     * Terminate subscription with full refund
     * @param subscriptionUuid
     * @return String account code
     */
    public Response<String/*accountCode*/> terminateSubscriptionWithFullRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminate(subscriptionUuid, RecurlySubscriptionRefund.FULL)
    }

    /**
     * Terminate subscription with no refund
     * @param subscriptionUuid
     * @return String account code
     */
    public Response<String/*accountCode*/> terminateSubscriptionWithNoRefund(String subscriptionUuid) {
        new RecurlySubscriptionProcessor().terminate(subscriptionUuid, RecurlySubscriptionRefund.NONE)
    }

    //BILLING DETAILS RELATED SERVICES

    /**
     * Get billing details
     * @param accountCode
     * @return RecurlyBillingInfo
     */
    public Response<RecurlyBillingInfo> getBillingDetails(String accountCode) {
        return new RecurlyBillingInfoProcessor().getBillingDetails(accountCode)
    }

    /**
     * Create or update billing details
     * @param billingDetails
     * @param accountCode
     * @return RecurlyBillingInfo
     */
    public Response<RecurlyBillingInfo> createOrUpdateBillingDetails(RecurlyBillingInfo billingDetails, String accountCode) {
        return new RecurlyBillingInfoProcessor(billingDetails).createOrUpdate(accountCode)
    }

    /**
     * Delete billing details
     * @param accountCode
     * @return String account code
     */
    public Response<String/*accountCode*/> deleteBillingDetails(String accountCode) {
        return new RecurlyBillingInfoProcessor().delete(accountCode)
    }

    //TRANSACTION RELATED SERVICES

    /**
     * Get transaction details
     * @param transactionId
     * @return RecurlyTransaction
     */
    public Response<RecurlyTransaction> getTransactionDetails(String transactionId) {
        return new RecurlyTransactionProcessor().getTransactionDetails(transactionId)
    }

    /**
     * List all account transactions
     * @param accountCode
     * @return List of account transactions
     */
    public Response<List<RecurlyTransaction>> listAllAccountTransactions(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode)
    }

    /**
     * List all account transactions which are marked as successful
     * @param accountCode
     * @return List of successful account transactions
     */
    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsSuccessful(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionState.SUCCESSFUL)
    }

    /**
     * List all account transaction which are marked as failed
     * @param accountCode
     * @return List of failed account transactions
     */
    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsFailed(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionState.FAILED)
    }

    /**
     * List all account transactions which are marked as payments
     * @param accountCode
     * @return List of payment account transactions
     */
    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsPayments(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionType.PURCHASE)
    }

    /**
     * List all account transactions which are marked as refunds
     * @param accountCode
     * @return List of refund account transactions
     */
    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsRefunds(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionType.REFUND)
    }

    /**
     * List all account transactions which are marked as voided
     * @param accountCode
     * @return List of voided account transactions
     */
    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsVoided(String accountCode) {
        return new RecurlyTransactionProcessor().listTransactions(accountCode: accountCode, state: RecurlyTransactionState.VOIDED)
    }

    /**
     * List all transactions
     * @return
     */
    public Response<List<RecurlyTransaction>> listAllTransactions() {
        return new RecurlyTransactionProcessor().listTransactions()
    }

    /**
     * List all transactions which are marked as successful
     * @return List of successful transactions
     */
    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsSuccessful() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionState.SUCCESSFUL)
    }

    /**
     * List all transactions which are marked as failed
     * @return List of failed transactions
     */
    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsFailed() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionState.FAILED)
    }

    /**
     * List all transactions which are marked as payments
     * @return List of payments transactions
     */
    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsPayments() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionType.PURCHASE)
    }

    /**
     * List all transactions which are marked as refunds
     * @return List of refunds account transactions
     */
    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsRefunds() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionType.REFUND)
    }

    /**
     * List all transactions which are marked as voided
     * @return List of voided account transactions
     */
    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsVoided() {
        return new RecurlyTransactionProcessor().listTransactions(state: RecurlyTransactionState.VOIDED)
    }

}