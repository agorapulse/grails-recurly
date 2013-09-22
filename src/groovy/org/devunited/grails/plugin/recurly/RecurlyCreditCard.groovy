package org.devunited.grails.plugin.recurly

class RecurlyCreditCard {
    String creditCardNumber
    String verificationValue
    String year
    String month

    //Read only properties
    String lastFour
    String type
    String startMonth
    String startYear
    String issueNumber
}