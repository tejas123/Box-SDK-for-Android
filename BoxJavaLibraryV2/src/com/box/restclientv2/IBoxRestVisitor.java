package com.box.restclientv2;

import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * Class to analyse http request and response. This can be used for network anaysis purpose.
 */
public interface IBoxRestVisitor {

    /**
     * Visit the http request right before request sent out.
     * 
     * @param request
     *            http request
     * @param sequenceId
     *            id of the api request
     */
    void visitRequestBeforeSend(HttpRequest request, int sequenceId);

    /**
     * Visit the http response after response is received.
     * 
     * @param response
     *            http response.
     * @param sequenceId
     *            id of the api request
     */
    void visitResponseUponReceiving(HttpResponse response, int sequenceId);

    /**
     * Visit the exception when exception is thrown during http call.
     * 
     * @param e
     *            Exception.
     * @param sequenceId
     *            id of the api request
     */
    void visitException(Exception e, int sequenceId);
}
