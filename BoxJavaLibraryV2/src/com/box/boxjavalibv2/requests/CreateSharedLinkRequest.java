package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to create a shared link for a file or folder.
 */
public class CreateSharedLinkRequest extends DefaultBoxRequest {

    private static final String URI = "/%s/%s";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param id
     *            id of the item
     * @param requestObject
     *            request object
     * @param type
     *            type of the item
     * 
     * @throws BoxRestException
     *             exception
     */
    public CreateSharedLinkRequest(final IBoxConfig config, final IBoxJSONParser parser, final String id, BoxSharedLinkRequestObject requestObject,
        final BoxResourceType type) throws BoxRestException {
        super(config, parser, getUri(id, type), RestMethod.PUT, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param fileFolderId
     *            id of this file/folder
     * @param type
     *            whether it is folder
     * @return uri
     */
    public static String getUri(final String fileFolderId, final BoxResourceType type) {
        return String.format(URI, type.toPluralString(), fileFolderId);
    }
}
