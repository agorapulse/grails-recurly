package org.devunited.grails.plugin.recurly.exceptions

import org.devunited.grails.plugin.recurly.templates.Response

class RecurlyApiResponseException extends Exception {

    public String entityType
    public String message
    public String serverReply
    public int statusCode

    RecurlyApiResponseException(Response response) {
        super(response.message)
        this.entityType = response.entity.class.toString()
        this.statusCode = response.status.toInteger()
        this.serverReply = response.errors['SERVER_REPLY']
    }

    public String toString(){
        return "RecurlyApiResponseException - statusCode: ${statusCode}, message: ${message}, entityType: ${entityType}, serverReply: ${serverReply}"
    }

}

