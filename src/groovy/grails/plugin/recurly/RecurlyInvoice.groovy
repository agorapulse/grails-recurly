package grails.plugin.recurly

import grails.plugin.recurly.enums.RecurlyInvoiceState
import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyInvoiceProcessor

class RecurlyInvoice extends  RecurlyRESTResource {

    String uuid
    String accountCode
    RecurlyInvoiceState state
    Integer invoiceNumber
    String poNumber
    String vatNumber
    Integer subtotalInCents
    Integer taxInCents
    Integer totalInCents
    String currency
    Date createdAt

    String toString() {
        "RecurlyInvoice(invoiceNumber:$invoiceNumber, accountCode:'$accountCode', uuid:'$uuid', state:'$state', totalInCents:'$totalInCents', currency:'$currency', createdAt:'$createdAt')"
    }

    // STATIC REST METHODS

    static RecurlyInvoice fetch(Integer invoiceNumber) {
        handleResponse(new RecurlyInvoiceProcessor().getInvoiceDetails(invoiceNumber)) as RecurlyInvoice
    }

    static List query(Map query = [:]) {
        if (query.max) query.per_page = query.max
        handleResponse(new RecurlyInvoiceProcessor().listInvoices(query)) as List
    }

    static byte[] streamPdf(Integer invoiceNumber, Locale locale) {
        new RecurlyInvoiceProcessor().getInvoicePdfStream(invoiceNumber, locale)
    }

}
