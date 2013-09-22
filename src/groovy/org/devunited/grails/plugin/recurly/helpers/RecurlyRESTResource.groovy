package org.devunited.grails.plugin.recurly.helpers

import org.devunited.grails.plugin.recurly.exceptions.RecurlyApiResponseException
import org.devunited.grails.plugin.recurly.exceptions.RecurlyException

abstract class RecurlyRESTResource {

    static def handleResponse(response) {
        if (response.status.isNumber() && response.status.toInteger() in 200..299) {
            return response.entity
        } else if (response.status.isNumber()) {
            throw new RecurlyApiResponseException(response)
        } else {
            throw new RecurlyException(response)
        }
    }

}
