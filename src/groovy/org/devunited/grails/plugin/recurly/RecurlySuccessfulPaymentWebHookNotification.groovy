package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.helpers.WebHookNotification

class RecurlySuccessfulPaymentWebHookNotification extends WebHookNotification {

    public RecurlyAccount recurlyAccount
    public RecurlyTransaction recurlyTransaction

}
