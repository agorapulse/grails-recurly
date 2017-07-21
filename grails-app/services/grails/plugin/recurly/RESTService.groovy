package grails.plugin.recurly

import groovyx.net.http.HttpResponseException
import groovyx.net.http.HttpResponseDecorator
import grails.plugin.recurly.templates.Response
import groovyx.net.http.RESTClient

class RESTService {

    static API_VERSION = '2.7'

    public Response<HttpResponseDecorator> getResponseUsingMethodPOST(String url,
                                                                      String data,
                                                                      String apiKey,
                                                                      String apiVersion = API_VERSION,
                                                                      String contentType = "application/xml; charset=utf-8",
                                                                      String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            new RESTClient(url).with {
                httpResponse.entity = post(
                        body: data,
                        contentType: contentType,
                        headers: [
                                "Accept": acceptType,
                                "Authorization": "Basic ${authenticationDigest}",
                                "Content-Type": contentType,
                                "X-Api-Version": apiVersion
                        ]
                )
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

    public Response<HttpResponseDecorator> getResponseUsingMethodPUT(String url,
                                                                     String data,
                                                                     String apiKey,
                                                                     String apiVersion = API_VERSION,
                                                                     String contentType = "application/xml; charset=utf-8",
                                                                     String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            new RESTClient(url).with {
                httpResponse.entity = put(
                        body: data,
                        contentType: contentType,
                        headers: [
                                "Accept": acceptType,
                                "Authorization": "Basic ${authenticationDigest}",
                                "Content-Type": contentType,
                                "X-Api-Version": apiVersion
                        ]
                )
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

    public Response<HttpResponseDecorator> getResponseUsingMethodDELETE(String url,
                                                                        String apiKey,
                                                                        String apiVersion = API_VERSION,
                                                                        String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            new RESTClient(url).with {
                httpResponse.entity = delete(headers: [
                        "Accept": acceptType,
                        "Authorization": "Basic ${authenticationDigest}",
                        "X-Api-Version": apiVersion
                ])
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

    public Response<HttpResponseDecorator> getResponseUsingMethodGET(String url,
                                                                     String apiKey,
                                                                     String apiVersion = API_VERSION,
                                                                     String contentType = "application/xml; charset=utf-8",
                                                                     String acceptType = "application/xml") {

        Response<HttpResponseDecorator> httpResponse = new Response<HttpResponseDecorator>()
        String authenticationDigest = apiKey.bytes.encodeBase64().toString()

        try {
            new RESTClient(url).with {
                httpResponse.entity = get(headers: [
                        "Accept": acceptType,
                        "Authorization": "Basic ${authenticationDigest}",
                        "Content-Type": contentType,
                        "X-Api-Version": apiVersion
                ])
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