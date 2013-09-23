package grails.plugin.recurly

import grails.plugin.recurly.enums.RecurlyAccountState
import grails.plugin.recurly.helpers.RecurlyRESTResource
import grails.plugin.recurly.processors.RecurlyAccountProcessor

class RecurlyAccount extends RecurlyRESTResource {

    String accountCode
    String state
    String userName
    String email
    String firstName
    String lastName
    String companyName
    String acceptLanguage = "en-us,en;q=0.5"
    String hostedLoginToken
    String createdAt

    String delete() {
        remove(accountCode)
    }

    RecurlyAccount save() {
        if (!createdAt) {
            create(this)
        } else {
            update(this)
        }
        return this
    }

    String toString() {
        "RecurlyAccount(accountCode:'$accountCode', state:'$state', email:'$email', userName:'$userName', firstName:'$firstName', lastName:'$lastName')"
    }

    // STATIC REST METHODS

    static String create(RecurlyAccount recurlyAccount) {
        handleResponse(new RecurlyAccountProcessor(recurlyAccount).create())
    }

    static RecurlyAccount fetch(String accountCode) {
        handleResponse(new RecurlyAccountProcessor().getAccountDetails(accountCode)) as RecurlyAccount
    }

    static List query(Map query = [:]) {
        if (query.max) query.per_page = query.max
        handleResponse(new RecurlyAccountProcessor().listAccounts(query)) as List
    }

    static String remove(String accountCode) {
        handleResponse(new RecurlyAccountProcessor().delete(accountCode))
    }

    static RecurlyAccount update(RecurlyAccount recurlyAccount) {
        handleResponse(new RecurlyAccountProcessor(recurlyAccount).update()) as RecurlyAccount
    }

}
