package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class GetGroupMembershipRequest extends DefaultBoxRequest {

    private static final String URI = "/group_memberships/%s";

    public GetGroupMembershipRequest(final IBoxConfig config, final IBoxJSONParser parser, final String membershipId, final BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(membershipId), RestMethod.GET, requestObject);
    }

    public static String getUri(final String id) {
        return String.format(URI, id);
    }
}