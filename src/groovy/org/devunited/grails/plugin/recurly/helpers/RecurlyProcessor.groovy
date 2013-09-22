package org.devunited.grails.plugin.recurly.helpers

import org.devunited.grails.plugin.recurly.RESTService
import org.codehaus.groovy.grails.commons.ConfigurationHolder
import groovyx.net.http.HttpResponseDecorator
import org.devunited.grails.plugin.recurly.templates.Response

abstract class RecurlyProcessor extends ConstraintsValidator {

    public String password = ConfigurationHolder.config.recurly.password
    public String userName = ConfigurationHolder.config.recurly.userName
    public String targetUrl

    public RESTService restService = new RESTService()

    public Response<HttpResponseDecorator> httpResponse

    public abstract String getDetailsInXML()

    public void processUsingMethodPOST() {
        httpResponse = restService.getResponseUsingMethodPOST(targetUrl, getDetailsInXML(), userName, password)
    }

    public void processUsingMethodGET() {
        httpResponse = restService.getResponseUsingMethodGET(targetUrl, userName, password)
    }

    public void processUsingMethodPUT() {
        httpResponse = restService.getResponseUsingMethodPUT(targetUrl, getDetailsInXML(), userName, password)
    }

    public void processUsingMethodDELETE() {
        httpResponse = restService.getResponseUsingMethodDELETE(targetUrl, userName, password)
    }

    public void findErrors() {
        if (!beanUnderProcess) {
            propertiesWithErrors.put("NULL POINTER EXCEPTION IN ${beanClass}", "Object of ${beanClass} passed is null")
        } else {
            checkConstraints()
        }
    }
}