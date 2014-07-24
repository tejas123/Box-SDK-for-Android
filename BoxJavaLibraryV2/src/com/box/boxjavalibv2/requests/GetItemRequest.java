package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get info of a file or folder.
 */
public class GetItemRequest extends DefaultBoxRequest {

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
     * @param type
     *            resource type
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             excepition
     */
    public GetItemRequest(final IBoxConfig config, final IBoxJSONParser parser, final String id, final BoxResourceType type,
        BoxDefaultRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(id, type), RestMethod.GET, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param id
     *            id of the item
     * @param type
     *            resource type
     * @return uri
     */
    public static String getUri(final String id, final BoxResourceType type) {
        return String.format(URI, type.toPluralString(), id);
    }
}
