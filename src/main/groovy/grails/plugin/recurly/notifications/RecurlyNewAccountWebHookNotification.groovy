package grails.plugin.recurly.notifications

import grails.plugin.recurly.RecurlyAccount
import grails.plugin.recurly.helpers.WebHookNotification

class RecurlyNewAccountWebHookNotification extends WebHookNotification {

    public RecurlyAccount recurlyAccount

}
