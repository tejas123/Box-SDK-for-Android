package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class DeleteCollaborationRequest extends DefaultBoxRequest {

    private static final String URI = "/collaborations/%s";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param collabId
     *            id of the collaboration
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public DeleteCollaborationRequest(final IBoxConfig config, final IBoxJSONParser parser, String collabId, BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(collabId), RestMethod.DELETE, requestObject);
        setExpectedResponseCode(HttpStatus.SC_NO_CONTENT);
    }

    /**
     * Get uri.
     * 
     * @param collabId
     *            collaboration id
     * @return uri
     */
    public static String getUri(final String collabId) {
        return String.format(URI, collabId);
    }
}
