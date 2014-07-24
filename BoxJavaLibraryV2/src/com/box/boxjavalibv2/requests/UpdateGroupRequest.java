package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxGroupRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class UpdateGroupRequest extends DefaultBoxRequest {

    private static final String URI = "/groups/%s";

    public UpdateGroupRequest(final IBoxConfig config, final IBoxJSONParser parser, final String groupId, final BoxGroupRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(groupId), RestMethod.PUT, requestObject);
    }

    /**
     * Get uri.
     * 
     * @return uri
     */
    public static String getUri(String id) {
        return String.format(URI, id);
    }
}