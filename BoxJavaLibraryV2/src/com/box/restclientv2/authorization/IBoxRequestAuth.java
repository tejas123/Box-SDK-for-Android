package com.box.restclientv2.authorization;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.IBoxRequest;

/**
 * Interface for Auth used for Box API Client.
 */
public interface IBoxRequestAuth {

    /**
     * Set Auth into the API request.
     * 
     * @param request
     *            IBoxRequest
     * @throws BoxRestException
     *             Exception.
     * @throws AuthFatalFailureException
     *             exception
     */
    void setAuth(IBoxRequest request) throws BoxRestException, AuthFatalFailureException;
}
