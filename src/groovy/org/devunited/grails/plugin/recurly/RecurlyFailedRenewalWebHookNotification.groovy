package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.helpers.WebHookNotification

class RecurlyFailedRenewalWebHookNotification extends WebHookNotification{
    RecurlyAccount recurlyAccount
    RecurlyTransaction recurlyTransaction
}
