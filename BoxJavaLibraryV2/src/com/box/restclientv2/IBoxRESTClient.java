package com.box.restclientv2;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.IBoxRequest;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * Interface for Box API Client.
 */
public interface IBoxRESTClient {

    /**
     * Execute the API request and return a response.
     * 
     * @param boxRequest
     *            Incoming API request.
     * @return Response from API.
     * @throws BoxRestException
     * @throws AuthFatalFailureException
     */
    IBoxResponse execute(IBoxRequest boxRequest) throws BoxRestException, AuthFatalFailureException;
}
