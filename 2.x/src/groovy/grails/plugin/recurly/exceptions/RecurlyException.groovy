package grails.plugin.recurly.exceptions

import grails.plugin.recurly.templates.Response

class RecurlyException extends Exception {

    public String entityType
    public Map errors
    public String message

    RecurlyException(Response response) {
        super(response.message)
        this.entityType = response.entity.class.toString()
        this.errors = response.errors
    }

    public String toString(){
        return "RecurlyException - message: ${message}, entityType: ${entityType}, errors: ${errors}"
    }

}
