package grails.plugin.recurly.exceptions

import grails.plugin.recurly.templates.Response

class RecurlyApiResponseException extends Exception {

    public String entityType
    public Map errors
    public String message
    public String serverReply
    public int statusCode

    RecurlyApiResponseException(Response response) {
        super(response.message)
        this.entityType = response.entity.class.toString()
        this.statusCode = response.status.toInteger()
        this.errors = response.errors
        this.serverReply = (response.errors['SERVER_REPLY'].error && response.errors['SERVER_REPLY'].error.toString() != '') ? response.errors['SERVER_REPLY'].error.toString() : response.errors['SERVER_REPLY']
    }

    public String toString(){
        return "RecurlyApiResponseException - statusCode: ${statusCode}, message: ${message}, entityType: ${entityType}, serverReply: ${serverReply}, errors: ${errors}"
    }

}

