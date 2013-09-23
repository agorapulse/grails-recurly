package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlyAccount
import grails.plugin.recurly.templates.Response
import groovy.xml.MarkupBuilder
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.helpers.RecurlyProcessor

class RecurlyAccountProcessor extends RecurlyProcessor {

    public RecurlyAccountProcessor(RecurlyAccount recurlyAccount) {
        this.recurlyAccount = recurlyAccount
    }

    public RecurlyAccountProcessor() {
        recurlyAccount = new RecurlyAccount()
    }

    void checkConstraints() {
        checkProperty("accountCode", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("userName", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("firstName", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("lastName", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("email", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("companyName", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("hostedLoginToken", MAX_SIZE_32, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("acceptLanguage", MAX_SIZE_255, OPTIONAL_FIELD, CAN_BE_BLANK)
    }

    public Response<RecurlyAccount> create() {
        Response<RecurlyAccount> response = new Response<RecurlyAccount>()
        response.entity = recurlyAccount

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.ACCOUNT)
            this.processUsingMethodPOST()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlyAccount
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against CREATE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<RecurlyAccount> update() {
        Response<RecurlyAccount> response = new Response<RecurlyAccount>()
        response.entity = recurlyAccount

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.UPDATE_ACCOUNT, recurlyAccount.accountCode)
            this.processUsingMethodPUT()
            updateResponse(httpResponse.entity.getData())
            response.entity = recurlyAccount
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against UPDATE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<String> delete(String accountCode) {
        Response<String> response = new Response<String>()
        response.entity = accountCode

        if (accountCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.DELETE_ACCOUNT, accountCode)
            this.processUsingMethodDELETE()
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against DELETE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = ["accountCode": "accountCode is Null"]
            response.message = "Validation Of Fields Failed, See Errors Map For Details"
        }
        return response
    }

    public Response<RecurlyAccount> getAccountDetails(String accountCode) {
        Response<RecurlyAccount> response = new Response<RecurlyAccount>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_ACCOUNT_DETAILS, accountCode)
        this.processUsingMethodGET()
        updateResponse(httpResponse.entity.getData())
        response.entity = recurlyAccount
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_ACCOUNT_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public Response<List<RecurlyAccount>> listAccounts(Map query = [:]) {
        Response<List<RecurlyAccount>> response = new Response<List<RecurlyAccount>>()
        List<RecurlyAccount> recurlyAccounts = []
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.ACCOUNT, '', query)
        this.processUsingMethodGET()
        httpResponse.entity.getData()?.account?.each {
            recurlyAccounts.add(new RecurlyAccount(
                    userName: it.username,
                    firstName: it.first_name,
                    lastName: it.last_name,
                    accountCode: it.account_code,
                    acceptLanguage: it.accept_language,
                    email: it.email,
                    companyName: it.company_name,
                    hostedLoginToken: it.hosted_login_token,
                    createdAt: it.created_at
            ))
        }
        response.entity = recurlyAccounts
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against LIST_ALL_ACCOUNTS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    public String getDetailsInXML() {
        StringWriter writer = new StringWriter()
        writer.write '<?xml version="1.0"?>\n'
        MarkupBuilder xml = new MarkupBuilder(writer)

        xml.account() {
            "account_code"(recurlyAccount.accountCode)
            "username"(recurlyAccount.userName ?: "")
            "email"(recurlyAccount.email ?: "")
            "first_name"(recurlyAccount.firstName ?: "")
            "last_name"(recurlyAccount.lastName ?: "")
            "company_name"(recurlyAccount.companyName ?: "")
            "accept_language"(recurlyAccount.acceptLanguage ?: "")
        }
        return writer.toString()
    }

    public void setRecurlyAccount(RecurlyAccount recurlyAccount) {
        this.beanUnderProcess = recurlyAccount
        beanClass = RecurlyAccount.class
    }

    public RecurlyAccount getRecurlyAccount() {
        return this.beanUnderProcess as RecurlyAccount
    }

    private void updateResponse(Object responseData = httpResponse.entity?.getData()) {
        if (!responseData) {
            return
        }
        this.recurlyAccount.userName = responseData.username
        this.recurlyAccount.firstName = responseData.first_name
        this.recurlyAccount.lastName = responseData.last_name
        this.recurlyAccount.accountCode = responseData.account_code
        this.recurlyAccount.acceptLanguage = responseData.accept_language
        this.recurlyAccount.email = responseData.email
        this.recurlyAccount.companyName = responseData.company_name
        this.recurlyAccount.hostedLoginToken = responseData.hosted_login_token
        this.recurlyAccount.createdAt = responseData.created_at
    }
}
