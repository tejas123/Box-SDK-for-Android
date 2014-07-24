package com.box.boxjavalibv2.requests;

import java.io.UnsupportedEncodingException;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to update info of a file or folder.
 */
public class UpdateItemInfoRequest extends DefaultBoxRequest {

    private static final String URI = "/%s/%s";

    public UpdateItemInfoRequest(final IBoxConfig config, final IBoxJSONParser parser, final String fileFolderId, final BoxItemRequestObject requestObject,
        final BoxResourceType type) throws BoxRestException, UnsupportedEncodingException {
        super(config, parser, getUri(fileFolderId, type), RestMethod.PUT, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param fileFolderId
     *            id of the file/folder
     * @param type
     *            whether it is a folder
     * @return uri
     */
    public static String getUri(final String fileFolderId, final BoxResourceType type) {
        return String.format(URI, type.toPluralString(), fileFolderId);
    }
}
