package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get the current user's information.
 */
public class GetCurrentUserRequest extends DefaultBoxRequest {

    private static final String URI = "/users/me";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param requestObject
     *            request object
     * @throws BoxRestException
     */
    public GetCurrentUserRequest(IBoxConfig config, final IBoxJSONParser parser, BoxDefaultRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(), RestMethod.GET, requestObject);
    }

    /**
     * Get uri.
     * 
     * @return uri
     */
    public static String getUri() {
        return URI;
    }
}
