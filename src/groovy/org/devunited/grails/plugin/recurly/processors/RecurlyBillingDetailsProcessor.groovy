package org.devunited.grails.plugin.recurly.processors

import org.devunited.grails.plugin.recurly.RecurlyBillingDetails
import org.devunited.grails.plugin.recurly.helpers.RecurlyProcessor
import org.devunited.grails.plugin.recurly.templates.Response
import org.devunited.grails.plugin.recurly.RecurlyCreditCard
import groovy.xml.MarkupBuilder
import org.devunited.grails.plugin.recurly.helpers.RecurlyURLBuilder
import org.devunited.grails.plugin.recurly.enums.RecurlyUrlActionType

class RecurlyBillingDetailsProcessor extends RecurlyProcessor {

    public RecurlyBillingDetailsProcessor(RecurlyBillingDetails recurlyBillingDetails) {
        this.recurlyBillingDetails = recurlyBillingDetails
    }

    public RecurlyBillingDetailsProcessor() {
        this.recurlyBillingDetails = new RecurlyBillingDetails(creditCard: new RecurlyCreditCard())
    }

    void checkConstraints() {
        checkProperty("firstName", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("lastName", MAX_SIZE_50, REQUIRED_FIELD, CAN_NOT_BE_BLANK)
        checkProperty("address1", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("address2", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("city", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("state", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("zip", MAX_SIZE_20, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("country", MAX_SIZE_2, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("ipAddress", MAX_SIZE_20, OPTIONAL_FIELD, CAN_BE_BLANK)
        if (recurlyBillingDetails.creditCard?.creditCardNumber) {
            propertiesWithErrors.putAll(new RecurlyCreditCardProcessor(recurlyBillingDetails.creditCard).errors())
        }
    }

    public Response<RecurlyBillingDetails> getBillingDetails(String accountCode) {
        Response<RecurlyBillingDetails> response = new Response<RecurlyBillingDetails>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_BILLING_DETAILS, accountCode)
        this.processUsingMethodGET()
        updateResponse(httpResponse.entity.getData())
        recurlyBillingDetails.accountCode = accountCode
        response.entity = recurlyBillingDetails
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_BILLING_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    Response<RecurlyBillingDetails> createOrUpdate(String accountCode) {
        Response<RecurlyBillingDetails> response = new Response<RecurlyBillingDetails>()
        response.entity = recurlyBillingDetails

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.CREATE_OR_UPDATE_BILLING_DETAILS, accountCode)
            this.processUsingMethodPUT()
            updateResponse(httpResponse.entity.getData())
            recurlyBillingDetails.accountCode = accountCode
            response.entity = recurlyBillingDetails
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against UPDATE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = this.errors()
            response.message = "Validation Of Feilds Failed, See Errors Map For Details"
        }
        return response
    }

    Response<String> delete(String accountCode) {
        Response<String> response = new Response<String>()
        response.entity = accountCode

        if (accountCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.DELETE_BILLING_DETAILS, accountCode)
            this.processUsingMethodDELETE()
            response.status = httpResponse?.status
            response.message = "This Response is Generated Against DELETE Request. " + httpResponse?.message
            response.errors = httpResponse?.errors
        } else {
            response.status = "error"
            response.errors = ["accountCode": "accountCode is Null"]
            response.message = "Validation Of Feilds Failed, See Errors Map For Details"
        }
        return response
    }

    public void setRecurlyBillingDetails(RecurlyBillingDetails recurlyBillingDetails) {
        this.beanUnderProcess = recurlyBillingDetails
        beanClass = RecurlyBillingDetails.class
    }

    public RecurlyBillingDetails getRecurlyBillingDetails() {
        return this.beanUnderProcess as RecurlyBillingDetails
    }

    public String getDetailsInXML() {
        StringWriter writer = new StringWriter()
        writer.write '<?xml version="1.0"?>\n'
        MarkupBuilder xml = new MarkupBuilder(writer)

        xml.billing_info() {
            "first_name"(recurlyBillingDetails.firstName)
            "last_name"(recurlyBillingDetails.lastName)
            if (recurlyBillingDetails.address1) {
                "address1"(recurlyBillingDetails.address1 ?: "")
            }
            if (recurlyBillingDetails.address2) {
                "address2"(recurlyBillingDetails.address2 ?: "")
            }
            if (recurlyBillingDetails.city) {
                "city"(recurlyBillingDetails.city ?: "")
            }
            if (recurlyBillingDetails.state) {
                "state"(recurlyBillingDetails.state ?: "")
            }
            if (recurlyBillingDetails.zip) {
                "zip"(recurlyBillingDetails.zip ?: "")
            }
            if (recurlyBillingDetails.country) {
                "country"(recurlyBillingDetails.country ?: "")
            }
            if (recurlyBillingDetails.ipAddress) {
                "ip_address"(recurlyBillingDetails.ipAddress ?: "")
            }
            if (recurlyBillingDetails.creditCard.creditCardNumber) {
                "number"(recurlyBillingDetails.creditCard.creditCardNumber)
            }
            if (recurlyBillingDetails.creditCard.verificationValue) {
                "verification_value"(recurlyBillingDetails.creditCard.verificationValue)
            }
            if (recurlyBillingDetails.creditCard.year) {
                "year"(recurlyBillingDetails.creditCard.year)
            }
            if (recurlyBillingDetails.creditCard.month) {
                "month"(recurlyBillingDetails.creditCard.month)
            }
        }
        return writer.toString()
    }

    private void updateResponse(Object responseData) {
        if(!responseData){
            return
        }
        if (responseData.first_name) {
            recurlyBillingDetails.firstName = responseData.first_name
        }
        if (responseData.last_name) {
            recurlyBillingDetails.lastName = responseData.last_name
        }
        if (responseData.address1) {
            recurlyBillingDetails.address1 = responseData.address1
        }
        if (responseData.address2) {
            recurlyBillingDetails.address2 = responseData.address2
        }
        if (responseData.city) {
            recurlyBillingDetails.city = responseData.city
        }
        if (responseData.state) {
            recurlyBillingDetails.state = responseData.state
        }
        if (responseData.zip) {
            recurlyBillingDetails.zip = responseData.zip
        }
        if (responseData.country) {
            recurlyBillingDetails.country = responseData.country
        }
        if (responseData.phone) {
            recurlyBillingDetails.phone = responseData.phone
        }
        if (responseData.ip_address) {
            recurlyBillingDetails.ipAddress = responseData.ip_address
        }
        if (responseData.credit_card.number) {
            recurlyBillingDetails.creditCard.creditCardNumber = responseData.credit_card.number
        }
        if (responseData.credit_card.verification_value) {
            recurlyBillingDetails.creditCard.verificationValue = responseData.credit_card.verification_value
        }
        if (responseData.credit_card.month) {
            recurlyBillingDetails.creditCard.month = responseData.credit_card.month
        }
        if (responseData.credit_card.year) {
            recurlyBillingDetails.creditCard.year = responseData.credit_card.year
        }
        if (responseData.credit_card.start_month) {
            recurlyBillingDetails.creditCard.startMonth = responseData.credit_card.start_month
        }
        if (responseData.credit_card.start_year) {
            recurlyBillingDetails.creditCard.startYear = responseData.credit_card.start_year
        }
        if (responseData.credit_card.issue_number) {
            recurlyBillingDetails.creditCard.issueNumber = responseData.credit_card.issue_number
        }
    }
}
