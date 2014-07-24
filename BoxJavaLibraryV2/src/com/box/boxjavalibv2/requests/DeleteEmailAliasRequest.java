package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to remove an email alias from a user.
 */
public class DeleteEmailAliasRequest extends DefaultBoxRequest {

    private static final String URI = "/users/%s/email_aliases/%s";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param userId
     *            id of user
     * @param emailId
     *            id of email
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public DeleteEmailAliasRequest(final IBoxConfig config, final IBoxJSONParser parser, final String userId, final String emailId,
        BoxDefaultRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(userId, emailId), RestMethod.DELETE, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param userId
     *            id of the user
     * @param emailId
     *            id of the email
     * @return
     */
    public static String getUri(String userId, final String emailId) {
        return String.format(URI, userId, emailId);
    }
}
