package grails.plugin.recurly

import grails.plugin.recurly.helpers.WebHookNotification

class RecurlySuccessfulPaymentWebHookNotification extends WebHookNotification {

    public RecurlyAccount recurlyAccount
    public RecurlyTransaction recurlyTransaction

}
