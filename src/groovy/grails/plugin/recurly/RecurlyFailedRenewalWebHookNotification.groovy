package grails.plugin.recurly

import grails.plugin.recurly.helpers.WebHookNotification

class RecurlyFailedRenewalWebHookNotification extends WebHookNotification{
    RecurlyAccount recurlyAccount
    RecurlyTransaction recurlyTransaction
}
