package org.devunited.grails.plugin.recurly

import org.devunited.grails.plugin.recurly.helpers.WebHookNotification

class RecurlyExpiredSubscriptionWebHookNotification extends WebHookNotification{

    public RecurlyAccount recurlyAccount
    public RecurlySubscription recurlySubscription

}
