package grails.plugin.recurly.processors

import grails.plugin.recurly.RecurlyJs
import grails.plugin.recurly.enums.RecurlyUrlActionType
import grails.plugin.recurly.helpers.RecurlyProcessor
import grails.plugin.recurly.helpers.RecurlyURLBuilder
import grails.plugin.recurly.templates.Response

class RecurlyJsProcessor extends RecurlyProcessor {

    void checkConstraints() {}

    public Response<RecurlyJs> fetch(String recurlyToken) {
        Response<RecurlyJs> response = new Response<RecurlyJs>()
        this.targetUrl = RecurlyURLBuilder.buildURL(RecurlyUrlActionType.GET_JS_RESULT, recurlyToken)
        this.processUsingMethodPOST()
        response.entity = getJsBeanFromResponse(httpResponse.entity.getData())
        response.status = httpResponse?.status
        response.message = "This Response is Generated Against GET_JS_RESULT Request. " + httpResponse?.message
        response.errors = httpResponse?.errors
        return response
    }

    private RecurlyJs getJsBeanFromResponse(Object responseData) {
        if (!responseData) {
            return null
        }
        return new RecurlyJs(
                // TODO Convert XML nodes to corresponding bean
        )
    }

    public String getDetailsInXML() {
    }

}
