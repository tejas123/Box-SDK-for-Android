package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get the items inside a folder. These items can be files, sub-folders, weblinks, and etc.
 */
public class GetFolderItemsRequest extends DefaultBoxRequest {

    private static final String URI = "/folders/%s/items";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param folderId
     *            id of the folder
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public GetFolderItemsRequest(final IBoxConfig config, final IBoxJSONParser parser, final String folderId, BoxPagingRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(folderId), RestMethod.GET, requestObject);
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
