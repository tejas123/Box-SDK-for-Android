package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxFolderDeleteRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to delete a folder.
 */
public class DeleteFolderRequest extends DefaultBoxRequest {

    private static final String URI = "/folders/%s";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param folderId
     *            id of the folder to be deleted
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public DeleteFolderRequest(final IBoxConfig config, final IBoxJSONParser parser, final String folderId, final BoxFolderDeleteRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(folderId), RestMethod.DELETE, requestObject);
        setExpectedResponseCode(HttpStatus.SC_NO_CONTENT);
    }

    /**
     * Get uri.
     * 
     * @param folderId
     *            id of the folder
     * @return uri
     */
    public static String getUri(final String folderId) {
        return String.format(URI, folderId);
    }
}
