package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.helpers.WebHookNotification

class RecurlyNewSubscriptionWebHookNotification extends WebHookNotification {

    public RecurlyAccount recurlyAccount
    public RecurlySubscription recurlySubscription

}
