package org.devunited.grails.plugin.recurly

class RecurlyAccount {

    String accountCode
    String userName
    String firstName
    String lastName
    String email
    String companyName
    String hostedLoginToken
    String acceptLanguage = "en-us,en;q=0.5"
    String balanceInCents
    String closed
    String createdAt

    RecurlyAccount() {}


}
