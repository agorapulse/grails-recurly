package grails.plugin.recurly.processors

import grails.plugin.recurly.enums.RecurlyInvoiceState
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.RecurlyInvoice
import grails.plugin.recurly.templates.Response
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import groovyx.net.http.ContentType
import groovyx.net.http.HTTPBuilder
import groovyx.net.http.Method

class RecurlyInvoiceProcessor extends RecurlyProcessor {

    public RecurlyInvoiceProcessor() {
        this.recurlyInvoice = new RecurlyInvoice()
    }

    public RecurlyInvoiceProcessor(RecurlyInvoice recurlyInvoice) {
        this.recurlyInvoice = recurlyInvoice
    }

    public Response<RecurlyInvoice> getInvoiceDetails(Integer invoiceNumber) {
        Response<RecurlyInvoice> response = new Response<RecurlyInvoice>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_INVOICE_DETAILS, invoiceNumber.toString())
        this.processUsingMethodGET()
        recurlyInvoice = getInvoiceBeanFromResponse(httpResponse.entity.getData())
        response.entity = recurlyInvoice
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_INVOICE_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public byte[] getInvoicePdfStream(Integer invoiceNumber, Locale locale) {
        def resp
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_INVOICE_DETAILS, invoiceNumber.toString())
        HTTPBuilder http = new HTTPBuilder(this.targetUrl)
        http.request(Method.GET, ContentType.BINARY) {
            headers = [
                    'Accept': 'application/pdf',
                    'Accept-Language': locale.toString().replace('_', '-'),
                    'Authorization': apiKey.bytes.encodeBase64().toString()
            ]
            response.success = { response, InputStream inputStream  ->
                resp = inputStream.bytes
            }
            response.failure = { response, xml ->
                throw new Exception(xml.error.toString())
            }
        }
        return resp
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
        def state = responseData.state
        try {
            state = state.toString().toUpperCase() as RecurlyInvoiceState
        } catch (Exception e) {
            // Ignore
        }
        return new RecurlyInvoice(
                uuid: responseData.uuid,
                accountCode: responseData.account ? responseData.account['@href']?.toString().tokenize('/')?.last() : '',
                state: state,
                invoiceNumber: convertNodeToInteger(responseData.invoice_number),
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