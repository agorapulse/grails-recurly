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
    String createdAt

    RecurlyAccount() {}

    String toString() {
        "RecurlyAccount(accountCode:'$accountCode', email:'$email', userName:'$userName', firstName:'$firstName', lastName:'$lastName')"
    }

}
