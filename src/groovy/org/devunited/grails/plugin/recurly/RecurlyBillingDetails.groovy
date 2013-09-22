package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.helpers.RecurlyRESTResource
import org.devunited.grails.plugin.recurly.processors.RecurlyBillingDetailsProcessor

class RecurlyBillingDetails extends RecurlyRESTResource {

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
    String phone

    RecurlyCreditCard creditCard

    String delete() {
        remove(accountCode)
    }

    RecurlyBillingDetails save() {
        createOrUpdate(accountCode, this)
        return this
    }

    String toString() {
        "RecurlyBillingInfo(firstName:'$firstName', lastName:'$lastName', address1:'$address1', address2:'$address2', city:'$city', country:'$country')"
    }

    // STATIC REST METHODS

    static RecurlyBillingDetails createOrUpdate(String accountCode, RecurlyBillingDetails recurlyBillingInfo) {
        handleResponse(new RecurlyBillingDetailsProcessor(recurlyBillingInfo).createOrUpdate(accountCode))
    }

    static RecurlyBillingDetails fetch(String accountCode) {
        handleResponse( new RecurlyBillingDetailsProcessor().getBillingDetails(accountCode))
    }

    static String remove(String accountCode) {
        handleResponse(new RecurlyBillingDetailsProcessor().delete(accountCode))
    }
    

}
