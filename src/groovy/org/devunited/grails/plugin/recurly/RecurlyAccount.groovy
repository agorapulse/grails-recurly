package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.helpers.RecurlyRESTResource
import org.devunited.grails.plugin.recurly.processors.RecurlyAccountProcessor

class RecurlyAccount extends RecurlyRESTResource {

    String accountCode
    String userName
    String firstName
    String lastName
    String email
    String companyName
    String hostedLoginToken
    String acceptLanguage = "en-us,en;q=0.5"
    String createdAt

    String delete() {
        remove(accountCode)
    }

    RecurlyAccount save() {
        if (!createdAt) {
            create(this)
        } else {
            accountCode = update(this)
        }
        return this
    }

    String toString() {
        "RecurlyAccount(accountCode:'$accountCode', email:'$email', userName:'$userName', firstName:'$firstName', lastName:'$lastName')"
    }

    // STATIC REST METHODS

    static String create(RecurlyAccount recurlyAccount) {
        handleResponse(new RecurlyAccountProcessor(recurlyAccount).create())
    }

    static RecurlyAccount fetch(String accountCode) {
        handleResponse(new RecurlyAccountProcessor().getAccountDetails(accountCode))

    }

    static List query(String state = '', int max = 50, String cursor = '') {
        def query = [
                per_page: max,
                state: state
        ]
        if (cursor) query.cursor = cursor
        handleResponse(new RecurlyAccountProcessor().listAccounts(query))
    }

    static String remove(String accountCode) {
        handleResponse(new RecurlyAccountProcessor().delete(accountCode))
    }

    static RecurlyAccount update(RecurlyAccount recurlyAccount) {
        handleResponse(new RecurlyAccountProcessor(recurlyAccount).update())
    }

}
