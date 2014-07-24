package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to download a file.
 */
public class DownloadFileRequest extends DefaultBoxRequest {

    private static final String URI = "/files/%s/content";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param fileId
     *            id of the file to be downloaded
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public DownloadFileRequest(final IBoxConfig config, final IBoxJSONParser parser, final String fileId, BoxDefaultRequestObject requestObject)
        throws BoxRestException {
        super(config, parser, getUri(fileId), RestMethod.GET, requestObject);
        this.setExpectedResponseCode(HttpStatus.SC_OK);
    }

    /**
     * Get uri.
     * 
     * @param fileId
     *            id of file
     * @return uri
     */
    public static String getUri(final String fileId) {
        return String.format(URI, fileId);
    }

    @Override
    public String getScheme() {
        return getConfig().getDownloadUrlScheme();
    }

    @Override
    public String getAuthority() {
        return getConfig().getDownloadUrlAuthority();
    }
}
