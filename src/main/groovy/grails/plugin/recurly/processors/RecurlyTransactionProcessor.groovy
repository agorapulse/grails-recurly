package grails.plugin.recurly.processors

import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.RecurlyTransaction
import grails.plugin.recurly.templates.Response
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.enums.RecurlyUrlActionType

class RecurlyTransactionProcessor extends RecurlyProcessor {


    public RecurlyTransactionProcessor() {
        this.recurlyTransaction = new RecurlyTransaction()
    }

    public RecurlyTransactionProcessor(RecurlyTransaction recurlyTransaction) {
        this.recurlyTransaction = recurlyTransaction
    }

    public Response<RecurlyTransaction> getTransactionDetails(String transactionUuid) {
        Response<RecurlyTransaction> response = new Response<RecurlyTransaction>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_TRANSACTION_DETAILS, transactionUuid)
        this.processUsingMethodGET()
        if (httpResponse.status == '200') {
            recurlyTransaction = getTransactionBeanFromResponse(httpResponse.entity.getData())
        }
        response.entity = recurlyTransaction
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyTransaction>> listTransactions(Map query = [:]) {
        Response<List<RecurlyTransaction>> response = new Response<List<RecurlyTransaction>>()
        response.entity = []
        if (query.accountCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_TRANSACTIONS, query.accountCode.toString(), query)
        } else {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.TRANSACTIONS, '', query)
        }

        this.processUsingMethodGET()
        httpResponse.entity.getData()?.transaction?.each { transaction ->
            response.entity.add(getTransactionBeanFromResponse(transaction))
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_TRANSACTION_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public void setRecurlyTransaction(RecurlyTransaction recurlyTransaction) {
        this.beanUnderProcess = recurlyTransaction
        this.beanClass = RecurlyTransaction.class.toString()
    }

    public RecurlyTransaction getRecurlyTransaction() {
        return this.beanUnderProcess as RecurlyTransaction
    }

    String getDetailsInXML() {""}

    void checkConstraints() {}

    private static RecurlyTransaction getTransactionBeanFromResponse(Object responseData) {
        if (!responseData) {
            return null
        }
        return new RecurlyTransaction(
                uuid: responseData.uuid,
                accountCode: responseData.account['@href']?.toString()?.tokenize('/')?.last(),
                invoiceUuid: responseData.account['@href']?.toString()?.tokenize('/')?.last(),
                subscriptionUuid: responseData.account['@href']?.toString()?.tokenize('/')?.last(),
                action: responseData.action,
                currency: responseData.currency,
                amountInCents: convertNodeToInteger(responseData.amount_in_cents),
                taxInCents: convertNodeToInteger(responseData.tax_in_cents),
                status: responseData.status,
                reference: responseData.reference,
                source: responseData.source,
                test: responseData.test as Boolean,
                voidable: responseData.voidable as Boolean,
                refundable: responseData.refundable as Boolean,
                cvvResult: responseData.cvv_result,
                avsResult: responseData.avs_result,
                avsResultStreet: responseData.avs_result_street,
                avsResultPostal: responseData.avs_result_postal,
                createdAt: convertNodeToDate(responseData.created_at)
        )
    }
}