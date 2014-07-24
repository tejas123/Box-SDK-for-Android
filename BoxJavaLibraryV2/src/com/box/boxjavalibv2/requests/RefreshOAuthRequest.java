package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxOAuthRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to refresh OAuth.
 */
public class RefreshOAuthRequest extends DefaultBoxRequest {

    private static final String URI = "/oauth2/token";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public RefreshOAuthRequest(final IBoxConfig config, final IBoxJSONParser parser, final BoxOAuthRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(), RestMethod.POST, requestObject);
    }

    /**
     * Get uri.
     * 
     * @return uri
     */
    public static String getUri() {
        return URI;
    }

    @Override
    public String getScheme() {
        return getConfig().getOAuthUrlScheme();
    }

    @Override
    public String getAuthority() {
        return getConfig().getOAuthUrlAuthority();
    }

    @Override
    public String getApiUrlPath() {
        return getConfig().getOAuthApiUrlPath();
    }
}
