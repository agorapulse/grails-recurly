package org.devunited.grails.plugin.recurly

class RecurlyBillingDetails {

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

    String toString() {
        "RecurlyBillingDetails(firstName:'$firstName', lastName:'$lastName', address1:'$address1', address2:'$address2', city:'$city', country:'$country')"
    }

}
