package org.devunited.grails.plugin.recurly.helpers

import org.devunited.grails.plugin.recurly.enums.RecurlyUrlActionType
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.devunited.grails.plugin.recurly.RecurlyAccount

class RecurlyURLBuilder {
    private static config = ConfigurationHolder.config.recurly

    public static String buildURL(RecurlyUrlActionType recurlyUrlActionType, String id = "", String subDomain = config?.subDomain) {
        String url = "https://${subDomain}.recurly.com/"
        url += recurlyUrlActionType.value
        return url.replace("[id]", id)
    }

    public static String buildSubscriptionUrl(String planCode, RecurlyAccount recurlyAccount, String subDomain = config?.subDomain) {
        String url = "https://${subDomain}.recurly.com/subscribe/${planCode}/${recurlyAccount.accountCode}/${recurlyAccount.userName.encodeAsURL()}?first_name=${recurlyAccount.firstName.encodeAsURL()}&last_name=${recurlyAccount.lastName.encodeAsURL()}&email=${recurlyAccount.email.encodeAsURL()}"
        return url
    }

    public static String buildAccountManagementUrl(RecurlyAccount recurlyAccount, String subDomain = config?.subDomain) {
        String url = "https://${subDomain}.recurly.com/account/${recurlyAccount.hostedLoginToken}"
        return url
    }
}