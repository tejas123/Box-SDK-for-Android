package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxCollabRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to add a collaboration for a single user to a folder.
 */
public class CreateCollaborationRequest extends DefaultBoxRequest {

    private static final String URI = "/collaborations";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param folderId
     *            id of the folder
     * @param collabObject
     *            object that goes into request body.
     * @throws BoxRestException
     */
    public CreateCollaborationRequest(final IBoxConfig config, final IBoxJSONParser parser, final String folderId, final BoxCollabRequestObject collabObject)
        throws BoxRestException {
        super(config, parser, getUri(folderId), RestMethod.POST, collabObject);
        this.setExpectedResponseCode(HttpStatus.SC_CREATED);
    }

    /**
     * Get uri.
     * 
     * @return uri
     */
    public static String getUri(String folderId) {
        return String.format(URI, folderId);
    }
}
