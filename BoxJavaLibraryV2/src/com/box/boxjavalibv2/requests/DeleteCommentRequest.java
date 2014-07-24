package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request call to delete a comment.
 */
public class DeleteCommentRequest extends DefaultBoxRequest {

    private static String URI = "/comments/%s";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param commentId
     *            id of the comment to be deleted
     * @param requestObject
     *            object that goes into request.
     * @throws BoxRestException
     *             exception
     */
    public DeleteCommentRequest(final IBoxConfig config, final IBoxJSONParser parser, final String commentId, BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(commentId), RestMethod.DELETE, requestObject);
        setExpectedResponseCode(HttpStatus.SC_NO_CONTENT);
    }

    /**
     * Get uri.
     * 
     * @param commentId
     *            id of the comment
     * @return uri
     */
    public static String getUri(final String commentId) {
        return String.format(URI, commentId);
    }
}
