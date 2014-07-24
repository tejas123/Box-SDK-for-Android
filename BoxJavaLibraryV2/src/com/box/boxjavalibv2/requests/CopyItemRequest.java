package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemCopyRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to copy a file/folder to a different parent folder.
 */
public class CopyItemRequest extends DefaultBoxRequest {

    public static final String URI = "/%s/%s/copy";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param id
     *            id of the item to be copied
     * @param requestObject
     *            request object
     * @param type
     *            resource type of the item
     * @throws BoxRestException
     *             exception
     */
    public CopyItemRequest(final IBoxConfig config, final IBoxJSONParser parser, final String id, final BoxItemCopyRequestObject requestObject,
        final BoxResourceType type) throws BoxRestException {
        super(config, parser, getUri(id, type), RestMethod.POST, requestObject);
        this.setExpectedResponseCode(HttpStatus.SC_CREATED);
    }

    /**
     * Get uri.
     * 
     * @param id
     *            id of the item to be copied
     * @param type
     *            whether it is a folder
     * @return uri
     */
    public static String getUri(final String id, final BoxResourceType type) {
        return String.format(URI, type.toPluralString(), id);
    }
}
