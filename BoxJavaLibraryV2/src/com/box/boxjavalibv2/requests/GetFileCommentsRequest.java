package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get comments of a file.
 */
public class GetFileCommentsRequest extends DefaultBoxRequest {

    private static final String URI = "/files/%s/comments";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param fileId
     *            id of the file
     * @param requestObject
     *            object that goes into request.
     * @throws BoxRestException
     *             exception
     */
    public GetFileCommentsRequest(final IBoxConfig config, final IBoxJSONParser parser, final String fileId, BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(fileId), RestMethod.GET, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param fileId
     *            id of the file
     * @return uri
     */
    public static String getUri(final String fileId) {
        return String.format(URI, fileId);
    }
}
