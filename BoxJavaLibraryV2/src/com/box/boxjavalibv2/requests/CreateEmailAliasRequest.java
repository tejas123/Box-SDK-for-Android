package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxEmailAliasRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to adds a new email alias to the given user's account. This feature is currently only available to enterprise admins and the new email must be in a
 * domain associated with the enterprise and can not be a publicly atainable domain (e.g. gmail.com).
 */
public class CreateEmailAliasRequest extends DefaultBoxRequest {

    private static final String URI = "/users/%s/email_aliases";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param userId
     *            id of the user
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public CreateEmailAliasRequest(final IBoxConfig config, final IBoxJSONParser parser, final String userId, final BoxEmailAliasRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(userId), RestMethod.POST, requestObject);
        setExpectedResponseCode(HttpStatus.SC_CREATED);
    }

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param userId
     *            id of the user
     * @param email
     *            email alias
     * @throws BoxRestException
     *             exception
     */
    public CreateEmailAliasRequest(final IBoxConfig config, final IBoxJSONParser parser, final String userId, final String email) throws BoxRestException {
        this(config, parser, userId, BoxEmailAliasRequestObject.addEmailAliasRequestObject(email));
    }

    /**
     * Get uri.
     * 
     * @param userId
     *            id of user.
     * @return uri
     */
    public static String getUri(final String userId) {
        return String.format(URI, userId);
    }
}
