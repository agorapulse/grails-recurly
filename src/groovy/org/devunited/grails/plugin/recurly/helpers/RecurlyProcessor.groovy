package org.devunited.grails.plugin.recurly.helpers

import grails.util.Holders
import org.devunited.grails.plugin.recurly.RESTService
import groovyx.net.http.HttpResponseDecorator
import org.devunited.grails.plugin.recurly.templates.Response

abstract class RecurlyProcessor extends ConstraintsValidator {

    public String apiKey = Holders.config.grails.plugin.recurly.apiKey
    public String targetUrl

    public RESTService restService = new RESTService()

    public Response<HttpResponseDecorator> httpResponse

    public abstract String getDetailsInXML()

    public void processUsingMethodPOST() {
        httpResponse = restService.getResponseUsingMethodPOST(targetUrl, getDetailsInXML(), apiKey)
    }

    public void processUsingMethodGET() {
        httpResponse = restService.getResponseUsingMethodGET(targetUrl, apiKey)
    }

    public void processUsingMethodPUT() {
        httpResponse = restService.getResponseUsingMethodPUT(targetUrl, getDetailsInXML(), apiKey)
    }

    public void processUsingMethodDELETE() {
        httpResponse = restService.getResponseUsingMethodDELETE(targetUrl, apiKey)
    }

    public void findErrors() {
        if (!beanUnderProcess) {
            propertiesWithErrors.put("NULL POINTER EXCEPTION IN ${beanClass}", "Object of ${beanClass} passed is null")
        } else {
            checkConstraints()
        }
    }
}