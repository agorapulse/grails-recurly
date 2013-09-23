package grails.plugin.recurly

import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyInvoiceProcessor

class RecurlyInvoice extends  RecurlyRESTResource {

    String uuid
    String accountCode
    String state
    Integer invoiceNumber
    String poNumber
    String vatNumber
    Integer subtotalInCents
    Integer taxInCents
    Integer totalInCents
    String currency
    Date createdAt

    String toString() {
        "RecurlyInvoice(uuid:'$uuid', state:'$state', invoiceNumber:'$invoiceNumber', totalInCents:'$totalInCents', currency:'$currency', createdAt:'$createdAt')"
    }

    // STATIC REST METHODS

    static List query(Map query = [:]) {
        if (query.max) query.per_page = query.max
        handleResponse(new RecurlyInvoiceProcessor().listInvoices(query)) as List
    }

}
