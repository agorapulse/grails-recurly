package grails.plugin.recurly.notifications

import grails.plugin.recurly.RecurlyAccount
import grails.plugin.recurly.RecurlyTransaction
import grails.plugin.recurly.helpers.WebHookNotification

class RecurlyFailedRenewalWebHookNotification extends WebHookNotification{
    RecurlyAccount recurlyAccount
    RecurlyTransaction recurlyTransaction
}
