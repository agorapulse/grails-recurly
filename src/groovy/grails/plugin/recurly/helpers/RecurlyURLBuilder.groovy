package grails.plugin.recurly.helpers

import grails.util.Holders
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.RecurlyAccount

class RecurlyURLBuilder {

    private static config = Holders.config.grails?.plugin?.recurly

    public static String buildURL(RecurlyUrlActionType recurlyUrlActionType, String id = "",  Map query = [:]) {
        assert config?.subDomain
        String url = "https://${config.subDomain}.recurly.com/v2/"
        url += recurlyUrlActionType.value
        if (query) {
            if (url.indexOf("?") == -1) url += "?"
            url += query.collect { k,v -> "$k=$v" }.join('&')
        }
        return url.replace("[id]", id)
    }

    public static String buildSubscriptionUrl(String planCode, RecurlyAccount recurlyAccount) {
        String url = "https://${config.subDomain}.recurly.com/v2/subscribe/${planCode}/${recurlyAccount.accountCode}/${recurlyAccount.userName.encodeAsURL()}?first_name=${recurlyAccount.firstName.encodeAsURL()}&last_name=${recurlyAccount.lastName.encodeAsURL()}&email=${recurlyAccount.email.encodeAsURL()}"
        return url
    }

    public static String buildAccountManagementUrl(RecurlyAccount recurlyAccount) {
        String url = "https://${config.subDomain}.recurly.com/v2/account/${recurlyAccount.hostedLoginToken}"
        return url
    }
}