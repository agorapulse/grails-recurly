package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlyBillingInfo
import grails.plugin.recurly.RecurlyCreditCard
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.templates.Response
import groovy.xml.MarkupBuilder

class RecurlyBillingInfoProcessor extends RecurlyProcessor {

    public RecurlyBillingInfoProcessor(RecurlyBillingInfo recurlyBillingInfo) {
        this.recurlyBillingInfo = recurlyBillingInfo
    }

    public RecurlyBillingInfoProcessor() {
        this.recurlyBillingInfo = new RecurlyBillingInfo(creditCard: new RecurlyCreditCard())
    }

    void checkConstraints() {
        checkProperty("firstName", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("lastName", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("address1", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("address2", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("city", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("state", MAX_SIZE_50, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("zip", MAX_SIZE_20, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("country", MAX_SIZE_2, OPTIONAL_FIELD, CAN_BE_BLANK)
        checkProperty("ipAddress", MAX_SIZE_20, OPTIONAL_FIELD, CAN_BE_BLANK)
        if (recurlyBillingInfo.creditCard?.creditCardNumber) {
            propertiesWithErrors.putAll(new RecurlyCreditCardProcessor(recurlyBillingInfo.creditCard).errors())
        }
    }

    public Response<RecurlyBillingInfo> getBillingDetails(String accountCode) {
        Response<RecurlyBillingInfo> response = new Response<RecurlyBillingInfo>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_BILLING_INFO, accountCode)
        this.processUsingMethodGET()
        if (httpResponse.status == '200') {
            updateResponse(httpResponse.entity.getData())
            recurlyBillingInfo.accountCode = accountCode
        }
        response.entity = recurlyBillingInfo
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_BILLING_DETAILS Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    Response<RecurlyBillingInfo> createOrUpdate(String accountCode) {
        Response<RecurlyBillingInfo> response = new Response<RecurlyBillingInfo>()
        response.entity = recurlyBillingInfo

        if (this.validate()) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.CREATE_OR_UPDATE_BILLING_INFO, accountCode)
            this.processUsingMethodPUT()
            updateResponse(httpResponse.entity.getData())
            recurlyBillingInfo.accountCode = accountCode
            response.entity = recurlyBillingInfo
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

    Response<String> delete(String accountCode) {
        Response<String> response = new Response<String>()
        response.entity = accountCode

        if (accountCode) {
            this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.DELETE_BILLING_INFO, accountCode)
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

    public void setRecurlyBillingInfo(RecurlyBillingInfo recurlyBillingDetails) {
        this.beanUnderProcess = recurlyBillingDetails
        beanClass = RecurlyBillingInfo.class.toString()
    }

    public RecurlyBillingInfo getRecurlyBillingInfo() {
        return this.beanUnderProcess as RecurlyBillingInfo
    }

    public String getDetailsInXML() {
        StringWriter writer = new StringWriter()
        writer.write '<?xml version="1.0" encoding="UTF-8"?>\n'
        MarkupBuilder xml = new MarkupBuilder(writer)

        xml.billing_info() {
            if (recurlyBillingInfo.tokenId) {
                "token_id"(recurlyBillingInfo.tokenId ?: "")
            } else {
                "first_name"(recurlyBillingInfo.firstName)
                "last_name"(recurlyBillingInfo.lastName)
                if (recurlyBillingInfo.address1) {
                    "address1"(recurlyBillingInfo.address1 ?: "")
                }
                if (recurlyBillingInfo.address2) {
                    "address2"(recurlyBillingInfo.address2 ?: "")
                }
                if (recurlyBillingInfo.city) {
                    "city"(recurlyBillingInfo.city ?: "")
                }
                if (recurlyBillingInfo.state) {
                    "state"(recurlyBillingInfo.state ?: "")
                }
                if (recurlyBillingInfo.zip) {
                    "zip"(recurlyBillingInfo.zip ?: "")
                }
                if (recurlyBillingInfo.country) {
                    "country"(recurlyBillingInfo.country ?: "")
                }
                if (recurlyBillingInfo.ipAddress) {
                    "ip_address"(recurlyBillingInfo.ipAddress ?: "")
                }
                if (recurlyBillingInfo.creditCard.creditCardNumber) {
                    "number"(recurlyBillingInfo.creditCard.creditCardNumber)
                }
                if (recurlyBillingInfo.creditCard.verificationValue) {
                    "verification_value"(recurlyBillingInfo.creditCard.verificationValue)
                }
                if (recurlyBillingInfo.creditCard.year) {
                    "year"(recurlyBillingInfo.creditCard.year)
                }
                if (recurlyBillingInfo.creditCard.month) {
                    "month"(recurlyBillingInfo.creditCard.month)
                }
            }
        }
        return writer.toString()
    }

    private void updateResponse(Object responseData) {
        if (!responseData){
            return
        }
        recurlyBillingInfo.accountCode = responseData.account['@href']?.toString().tokenize('/')?.last()
        if (responseData.first_name) {
            recurlyBillingInfo.firstName = responseData.first_name
        }
        if (responseData.last_name) {
            recurlyBillingInfo.lastName = responseData.last_name
        }
        if (responseData.address1) {
            recurlyBillingInfo.address1 = responseData.address1
        }
        if (responseData.address2) {
            recurlyBillingInfo.address2 = responseData.address2
        }
        if (responseData.city) {
            recurlyBillingInfo.city = responseData.city
        }
        if (responseData.state) {
            recurlyBillingInfo.state = responseData.state
        }
        if (responseData.zip) {
            recurlyBillingInfo.zip = responseData.zip
        }
        if (responseData.country) {
            recurlyBillingInfo.country = responseData.country
        }
        if (responseData.phone) {
            recurlyBillingInfo.phone = responseData.phone
        }
        if (responseData.ip_address) {
            recurlyBillingInfo.ipAddress = responseData.ip_address
        }
        if (!recurlyBillingInfo.creditCard) {
            recurlyBillingInfo.creditCard = new RecurlyCreditCard()
        }
        if (responseData.card_type) {
            recurlyBillingInfo.creditCard.type = responseData.card_type
        }
        if (responseData.month) {
            recurlyBillingInfo.creditCard.month = responseData.month
        }
        if (responseData.year) {
            recurlyBillingInfo.creditCard.year = responseData.year
        }
        if (responseData.first_six) {
            recurlyBillingInfo.creditCard.firstSix = responseData.first_six
        }
        if (responseData.last_four) {
            recurlyBillingInfo.creditCard.lastFour = responseData.last_four
        }
    }
}
