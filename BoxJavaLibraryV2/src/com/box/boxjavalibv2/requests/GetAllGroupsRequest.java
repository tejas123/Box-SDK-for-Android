package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class GetAllGroupsRequest extends DefaultBoxRequest {

    private static final String URI = "/groups";

    public GetAllGroupsRequest(final IBoxConfig config, final IBoxJSONParser parser, BoxDefaultRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(), RestMethod.GET, requestObject);
    }

    public static String getUri() {
        return URI;
    }
}