package org.devunited.grails.plugin.recurly.helpers

import org.devunited.grails.plugin.recurly.enums.WebHookResponseType

class WebHookNotification {

    public WebHookResponseType webHookResponseType
    public Date responseAt = new Date()

}
