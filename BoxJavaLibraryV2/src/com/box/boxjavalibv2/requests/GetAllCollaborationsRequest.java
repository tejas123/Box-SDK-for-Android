package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxGetAllCollabsRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get all collaborations. (Currently only support getting all pending collaborations.)
 */
public class GetAllCollaborationsRequest extends DefaultBoxRequest {

    private static final String URI = "/collaborations";

    public GetAllCollaborationsRequest(final IBoxConfig config, final IBoxJSONParser parser, BoxGetAllCollabsRequestObject collabObject)
        throws BoxRestException {
        super(config, parser, getUri(), RestMethod.GET, collabObject);
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
