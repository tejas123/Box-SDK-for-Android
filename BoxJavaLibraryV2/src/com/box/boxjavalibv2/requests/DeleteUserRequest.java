package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserDeleteRequestObject;

public class DeleteUserRequest extends DefaultBoxRequest {

    private static String URI = "/users/%s";

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
    public DeleteUserRequest(final IBoxConfig config, final IBoxJSONParser parser, final String userId, BoxUserDeleteRequestObject requestObject)
            throws BoxRestException {
        super(config, parser, getUri(userId), RestMethod.DELETE, requestObject);
        setExpectedResponseCode(HttpStatus.SC_NO_CONTENT);
    }

    public static String getUri(final String groupId) {
        return String.format(URI, groupId);
    }
}
