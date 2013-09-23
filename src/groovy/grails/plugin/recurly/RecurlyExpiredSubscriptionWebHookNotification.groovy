package grails.plugin.recurly

import grails.plugin.recurly.helpers.WebHookNotification

class RecurlyExpiredSubscriptionWebHookNotification extends WebHookNotification{

    public RecurlyAccount recurlyAccount
    public RecurlySubscription recurlySubscription

}
