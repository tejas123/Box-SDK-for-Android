package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get the trashed items inside a folder. These items can be files, sub-folders, weblinks, and etc.
 */
public class GetFolderTrashItemsRequest extends DefaultBoxRequest {

    private static final String URI = "/folders/trash/items";

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
    public GetFolderTrashItemsRequest(final IBoxConfig config, final IBoxJSONParser parser, BoxPagingRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(), RestMethod.GET, requestObject);
    }

    public static String getUri() {
        return URI;
    }
}
