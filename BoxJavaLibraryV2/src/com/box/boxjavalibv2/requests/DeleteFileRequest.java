package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to delete a file.
 */
public class DeleteFileRequest extends DefaultBoxRequest {

    private static final String URI = "/files/%s";

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
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public DeleteFileRequest(final IBoxConfig config, final IBoxJSONParser parser, final String fileId, BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(fileId), RestMethod.DELETE, requestObject);
        setExpectedResponseCode(HttpStatus.SC_NO_CONTENT);
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
