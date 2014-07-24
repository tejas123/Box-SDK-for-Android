package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get collaborations on a given folder. Can also request for collaborations of a certain status. Currently only
 * {@link com.box.boxjavalibv2.dao.CollaborationV2.STATUS_PENDING} is allowed.
 */
public class GetFolderCollaborationsRequest extends DefaultBoxRequest {

    private static final String URI = "/folders/%s/collaborations";

    public GetFolderCollaborationsRequest(final IBoxConfig config, final IBoxJSONParser parser, String folderId, BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(folderId), RestMethod.GET, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param folderId
     *            id of the collaborated folder
     * @return uri
     */
    public static String getUri(final String folderId) {
        return String.format(URI, folderId);
    }
}
