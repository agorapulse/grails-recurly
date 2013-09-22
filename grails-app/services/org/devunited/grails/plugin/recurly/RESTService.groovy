package org.devunited.grails.plugin.recurly

import groovyx.net.http.HttpResponseException
import groovyx.net.http.HttpResponseDecorator
import org.devunited.grails.plugin.recurly.templates.Response

class RESTService {

    static transactional = false

    public Response<HttpResponseDecorator> getResponseUsingMethodPOST(String url, String data, String apiKey, String contentType = "application/xml; charset=utf-8", String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            withRest(uri: url) {
                httpResponse.entity = post(body: data, contentType: contentType, headers: ["Accept": acceptType, "Authorization": authenticationDigest])
                httpResponse.message = "Response Generated via URL: ${url}"
                httpResponse.status = httpResponse.entity.status
            }
        } catch (HttpResponseException ex) {
            httpResponse.status = ex.getStatusCode()
            httpResponse.message = ex.getMessage()
            httpResponse.entity = ex.getResponse()
            httpResponse.errors = ["ERROR": ex.toString(), "SERVER_REPLY": ex.response.data]
        }
        return httpResponse
    }

    public Response<HttpResponseDecorator> getResponseUsingMethodPUT(String url, String data, String apiKey, String contentType = "application/xml; charset=utf-8", String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            withRest(uri: url) {
                httpResponse.entity = put(body: data, contentType: contentType, headers: ["Accept": acceptType, "Authorization": authenticationDigest])
                httpResponse.message = "Responce Generated via URL: ${url}"
                httpResponse.status = httpResponse.entity.status
            }
        } catch (HttpResponseException ex) {
            httpResponse.status = ex.getStatusCode()
            httpResponse.message = ex.getMessage()
            httpResponse.entity = ex.getResponse()
            httpResponse.errors = ["ERROR": ex.toString(), "SERVER_REPLY": ex.response.data]
        }
        return httpResponse
    }

    public Response<HttpResponseDecorator> getResponseUsingMethodDELETE(String url, String apiKey, String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            withRest(uri: url) {
                httpResponse.entity = delete(headers: ["Accept": acceptType, "Authorization": authenticationDigest])
                httpResponse.message = "Responce Generated via URL: ${url}"
                httpResponse.status = httpResponse.entity.status
            }
        } catch (HttpResponseException ex) {
            httpResponse.status = ex.getStatusCode()
            httpResponse.message = ex.getMessage()
            httpResponse.entity = ex.getResponse()
            httpResponse.errors = ["ERROR": ex.toString(), "SERVER_REPLY": ex.response.data]
        }
        return httpResponse
    }

    public Response<HttpResponseDecorator> getResponseUsingMethodGET(String url, String apiKey, String contentType = "application/xml; charset=utf-8", String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            withRest(uri: url) {
                httpResponse.entity = get(headers: ["Accept": acceptType, "Authorization": authenticationDigest])
                httpResponse.message = "Responce Generated via URL: ${url}"
                httpResponse.status = httpResponse.entity.status
            }
        } catch (HttpResponseException ex) {
            httpResponse.status = ex.getStatusCode()
            httpResponse.message = ex.getMessage()
            httpResponse.entity = ex.getResponse()
            httpResponse.errors = ["ERROR": ex.toString(), "SERVER_REPLY": ex.response.data]
        }
        return httpResponse
    }
}