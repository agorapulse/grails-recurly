package grails.plugin.recurly.helpers

import grails.plugin.recurly.exceptions.RecurlyApiResponseException
import grails.plugin.recurly.exceptions.RecurlyException
import grails.plugin.recurly.templates.Response

abstract class RecurlyRESTResource {

    static <T> T handleResponse(Response<T> response) {
        if (response.status.isNumber()) {
            if (response.status.toInteger() in 200..299) {
                return response.entity
            }
            throw new RecurlyApiResponseException(response)
        } else {
            throw new RecurlyException(response)
        }
    }

}
