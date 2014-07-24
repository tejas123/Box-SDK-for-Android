package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxGroupMembershipRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class UpdateGroupMembershipRequest extends DefaultBoxRequest {

    private static final String URI = "/group_memberships/%s";

    public UpdateGroupMembershipRequest(final IBoxConfig config, final IBoxJSONParser parser, final String membershipId,
        final BoxGroupMembershipRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(membershipId), RestMethod.PUT, requestObject);
    }

    public static String getUri(final String membershipId) {
        return String.format(URI, membershipId);
    }
}