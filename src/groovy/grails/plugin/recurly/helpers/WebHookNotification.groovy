package grails.plugin.recurly.helpers

import grails.plugin.recurly.enums.WebHookResponseType

class WebHookNotification {

    public WebHookResponseType webHookResponseType
    public Date responseAt = new Date()

}
