package grails.plugin.recurly

import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyTransactionProcessor

class RecurlyTransaction extends RecurlyRESTResource {

    String uuid
    String accountCode
    String action
    String currency
    Integer amountInCents
    Integer taxInCents
    String status
    String reference
    String source
    Boolean test
    Boolean voidable
    Boolean refundable
    String cvvResult
    String avsResult
    String avsResultPostal
    String avsResultStreet
    String createdAt

    String toString() {
        "RecurlyTransaction(uuid:'$uuid', accountCode:'$accountCode', action:'$action', currency:'$currency', amountInCents:$amountInCents, taxInCents:$taxInCents, status:'$status')"
    }

    // STATIC REST METHODS

    static RecurlyPlan fetch(String uuid) {
        handleResponse(new RecurlyTransactionProcessor().getTransactionDetails(uuid)) as RecurlyPlan
    }

    static List query(Map query = [:]) {
        if (query.max) query.per_page = query.max
        handleResponse(new RecurlyTransactionProcessor().listTransactions(query)) as List
    }

}
