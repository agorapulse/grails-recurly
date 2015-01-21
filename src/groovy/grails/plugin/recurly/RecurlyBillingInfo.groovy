package grails.plugin.recurly

import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyBillingInfoProcessor

class RecurlyBillingInfo extends RecurlyRESTResource {

    String accountCode
    String firstName
    String lastName
    String address1
    String address2
    String city
    String state
    String zip
    String country
    String ipAddress
    String ipAddressCountry
    String phone
    String vatNumber

    String tokenId

    RecurlyCreditCard creditCard

    String delete() {
        remove(accountCode)
    }

    RecurlyBillingInfo save() {
        createOrUpdate(accountCode, this)
    }

    String toString() {
        "RecurlyBillingInfo(accountCode:'$accountCode', firstName:'$firstName', lastName:'$lastName', address1:'$address1', address2:'$address2', city:'$city', country:'$country')"
    }

    // STATIC REST METHODS

    static RecurlyBillingInfo createOrUpdate(String accountCode, RecurlyBillingInfo recurlyBillingInfo) {
        handleResponse(new RecurlyBillingInfoProcessor(recurlyBillingInfo).createOrUpdate(accountCode)) as RecurlyBillingInfo
    }

    static RecurlyBillingInfo fetch(String accountCode) {
        handleResponse( new RecurlyBillingInfoProcessor().getBillingDetails(accountCode)) as RecurlyBillingInfo
    }

    static String remove(String accountCode) {
        handleResponse(new RecurlyBillingInfoProcessor().delete(accountCode))
    }


}
