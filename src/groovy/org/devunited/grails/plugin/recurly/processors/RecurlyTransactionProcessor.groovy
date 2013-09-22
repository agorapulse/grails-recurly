package org.devunited.grails.plugin.recurly.processors

import org.devunited.grails.plugin.recurly.helpers.RecurlyProcessor
import org.devunited.grails.plugin.recurly.RecurlyTransaction
import org.devunited.grails.plugin.recurly.templates.Response
import org.devunited.grails.plugin.recurly.helpers.RecurlyURLBuilder
import org.devunited.grails.plugin.recurly.enums.RecurlyUrlActionType

class RecurlyTransactionProcessor extends RecurlyProcessor {


    public RecurlyTransactionProcessor() {
        this.recurlyTransaction = new RecurlyTransaction()
    }

    public RecurlyTransactionProcessor(RecurlyTransaction recurlyTransaction) {
        this.recurlyTransaction = recurlyTransaction
    }

    public Response<RecurlyTransaction> getTransactionDetails(String transactionId) {
        Response<RecurlyTransaction> response = new Response<RecurlyTransaction>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_TRANSACTION_DETAILS, transactionId)
        this.processUsingMethodGET()
        recurlyTransaction = getTransactionBeanFromResponse(httpResponse.entity.getData())
        response.entity = recurlyTransaction
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactions(String accountId) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS, accountId)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            println transaction
            println transaction.id
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsSuccessful(String accountId) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_SUCCESSFUL, accountId)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsFailed(String accountId) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_FAILED, accountId)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsPayments(String accountId) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_PAYMENTS, accountId)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsRefunds(String accountId) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_REFUNDS, accountId)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllAccountTransactionsWhichAreMarkedAsVoided(String accountId) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS_MARKED_AS_VOIDED, accountId)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllTransactions() {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_TRANSACTIONS)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsSuccessful() {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_TRANSACTIONS_MARKED_AS_SUCCESSFUL)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsFailed() {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_TRANSACTIONS_MARKED_AS_FAILED)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsPayments() {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_TRANSACTIONS_MARKED_AS_PAYMENTS)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsRefunds() {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_TRANSACTIONS_MARKED_AS_REFUNDS)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listAllTransactionsWhichAreMarkedAsVoided() {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_TRANSACTIONS_MARKED_AS_VOIDED)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each {transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public void setRecurlyTransaction(RecurlyTransaction recurlyTransaction) {
        this.beanUnderProcess = recurlyTransaction
        this.beanClass = RecurlyTransaction.class
    }

    public RecurlyTransaction getRecurlyTransaction() {
        return this.beanUnderProcess as RecurlyTransaction
    }

    String getDetailsInXML() {""}

    void checkConstraints() {}

    private RecurlyTransaction getTransactionBeanFromResponse(Object responseData) {
        if (!responseData) {
            return null
        }
        return new RecurlyTransaction(
                id: responseData.id,
                accountCode: responseData.account_code,
                type: responseData.type,
                action: responseData.action,
                date: responseData.date,
                amountInCents: convertNodeToInteger(responseData.amount_in_cents),
                status: responseData.status,
                message: responseData.message,
                reference: responseData.reference,
                cvvResult: responseData.cvv_result,
                avsResult: responseData.avs_result,
                avsResultStreet: responseData.avs_result_street,
                avsResultPostal: responseData.avs_result_postal,
                test: responseData.test as Boolean,
                voidable: responseData.voidable as Boolean,
                refundable: responseData.refundable as Boolean
        )
    }
}