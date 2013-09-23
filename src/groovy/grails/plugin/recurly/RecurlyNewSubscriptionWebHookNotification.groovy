package grails.plugin.recurly

import grails.plugin.recurly.helpers.WebHookNotification

class RecurlyNewSubscriptionWebHookNotification extends WebHookNotification {

    public RecurlyAccount recurlyAccount
    public RecurlySubscription recurlySubscription

}
