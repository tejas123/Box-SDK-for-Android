package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxFileUploadRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to upload files.
 */
public class UploadFileRequest extends DefaultBoxRequest {

    private static final String URI = "/files/content";

    public UploadFileRequest(final IBoxConfig config, final IBoxJSONParser parser, final BoxFileUploadRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(), RestMethod.POST, requestObject);
        this.setExpectedResponseCode(HttpStatus.SC_CREATED);
    }

    /**
     * Get uri.
     * 
     * @return uri.
     */
    public static String getUri() {
        return URI;
    }

    @Override
    public String getScheme() {
        return getConfig().getUploadUrlScheme();
    }

    @Override
    public String getAuthority() {
        return getConfig().getUploadUrlAuthority();
    }

    @Override
    public String getApiUrlPath() {
        return getConfig().getUploadUrlPath();
    }
}
