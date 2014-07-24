package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxImageRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get preview.
 */
public class GetPreviewRequest extends DefaultBoxRequest {

    private final static String URI = "/files/%s/preview.%s";

    public GetPreviewRequest(final IBoxConfig config, final IBoxJSONParser parser, final String fileId, final String fileExtension,
        final BoxImageRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(fileId, fileExtension), RestMethod.GET, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param fileId
     *            id of the file
     * @param fileExtension
     *            extension of the requested preview
     * @return uri
     */
    public static String getUri(String fileId, String fileExtension) {
        return String.format(URI, fileId, fileExtension);
    }
}
