package grails.plugin.recurly

import grails.plugin.recurly.helpers.WebHookNotification

class RecurlyCanceledSubscriptionWebHookNotification extends WebHookNotification{

    public RecurlyAccount recurlyAccount
    public RecurlySubscription recurlySubscription

}
