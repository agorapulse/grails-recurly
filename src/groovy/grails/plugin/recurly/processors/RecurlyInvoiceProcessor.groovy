package grails.plugin.recurly.processors

import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.RecurlyInvoice
import grails.plugin.recurly.templates.Response
import grails.plugin.recurly.helpers.RecurlyURLBuilder

class RecurlyInvoiceProcessor extends RecurlyProcessor {


    public RecurlyInvoiceProcessor() {
        this.recurlyInvoice = new RecurlyInvoice()
    }

    public RecurlyInvoiceProcessor(RecurlyInvoice recurlyInvoice) {
        this.recurlyInvoice = recurlyInvoice
    }

    public Response<RecurlyInvoice> getInvoiceDetails(String invoiceUuid) {
        Response<RecurlyInvoice> response = new Response<RecurlyInvoice>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_INVOICE_DETAILS, invoiceUuid)
        this.processUsingMethodGET()
        recurlyInvoice = getInvoiceBeanFromResponse(httpResponse.entity.getData())
        response.entity = recurlyInvoice
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_INVOICE_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyInvoice>> listInvoices(Map query = [:]) {
        Response<List<RecurlyInvoice>> response = new Response<List<RecurlyInvoice>>()
        response.entity = []
        if (query.accountCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.LIST_ACCOUNT_INVOICES, query.accountCode.toString(), query)
        } else {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.INVOICES, '', query)
        }

        this.processUsingMethodGET()
        httpResponse.entity.getData()?.invoice?.each { invoice ->
            response.entity.add(getInvoiceBeanFromResponse(invoice))
        }
        if (query.accountCode) {
            response.entity*.accountCode = query.accountCode
        }
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_INVOICE_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public void setRecurlyInvoice(RecurlyInvoice recurlyInvoice) {
        this.beanUnderProcess = recurlyInvoice
        this.beanClass = RecurlyInvoice.class
    }

    public RecurlyInvoice getRecurlyInvoice() {
        return this.beanUnderProcess as RecurlyInvoice
    }

    String getDetailsInXML() {""}

    void checkConstraints() {}

    private RecurlyInvoice getInvoiceBeanFromResponse(Object responseData) {
        if (!responseData) {
            return null
        }
        return new RecurlyInvoice(
                uuid: responseData.uuid,
                accountCode: responseData.account_code,
                state: responseData.state,
                invoiceNumber: convertNodeToInteger(responseData.amount_in_cents),
                poNumber: responseData.po_number,
                vatNumber: responseData.vat_number,
                subtotalInCents: convertNodeToInteger(responseData.subtotal_in_cents),
                taxInCents: convertNodeToInteger(responseData.tax_in_cents),
                totalInCents: convertNodeToInteger(responseData.total_in_cents),
                currency: responseData.currency,
                createdAt: convertNodeToDate(responseData.created_at)
        )
    }
}