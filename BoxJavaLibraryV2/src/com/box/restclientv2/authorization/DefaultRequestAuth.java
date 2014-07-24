package com.box.restclientv2.authorization;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;
import com.box.restclientv2.requestsbase.IBoxRequest;

/**
 * Base class for default Auth to be used for DefaultBoxRequest.
 */
public abstract class DefaultRequestAuth implements IBoxRequestAuth {

    public static final String AUTH_HEADER_NAME = "Authorization";

    @Override
    public void setAuth(final IBoxRequest request) throws BoxRestException, AuthFatalFailureException {
        if (!(request instanceof DefaultBoxRequest)) {
            throw new BoxRestException("class does not match, expected:" + DefaultBoxRequest.class.getCanonicalName() + ";current:"
                                       + request.getClass().getCanonicalName());
        }
    }
}
