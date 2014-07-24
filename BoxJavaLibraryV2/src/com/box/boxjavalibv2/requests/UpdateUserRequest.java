package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Used to edit the settings and information about a user. This method only works for enterprise admins.
 */
public class UpdateUserRequest extends DefaultBoxRequest {

    private static final String URI = "/users/%s";

    /**
     * Constructor
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param userId
     *            id of the user.
     * @param
     * @param requestObject
     *            request object
     * @throws BoxRestException
     */
    public UpdateUserRequest(final IBoxConfig config, final IBoxJSONParser parser, final String userId, BoxUserRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(userId), RestMethod.PUT, requestObject);
    }

    /**
     * Get Uri.
     * 
     * @param userId
     *            id of the user
     * @return uri
     */
    public static String getUri(final String userId) {
        return String.format(URI, userId);
    }
}
