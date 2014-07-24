package com.box.boxjavalibv2.authorization;

import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.lang.StringUtils;

import com.box.restclientv2.httpclientsupport.HttpClientURIBuilder;
import com.box.restclientv2.httpclientsupport.HttpClientURLEncodedUtils;

/**
 * Data on the OAuth WebView.
 */
public class OAuthWebViewData {

    private final String RESPONSE_TYPE = "code";
    private final OAuthDataController mOAuthDataController;
    private String mOptionalState;
    private String redirectUrl;

   public OAuthWebViewData(final OAuthDataController oAuthDataController) {
        this.mOAuthDataController = oAuthDataController;
    }

    /**
     * @return the optionalState
     */
    public String getOptionalState() {
        return mOptionalState;
    }

    /**
     * @param optionalState
     *            the optionalState to set
     */
    public void setOptionalState(final String optionalState) {
        this.mOptionalState = optionalState;
    }

    /**
     * @return the redirectUrl
     */
    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String url) {
        this.redirectUrl = url;
    }

    /**
     * @return the clientId
     */
    public String getClientId() {
        return mOAuthDataController.getClientId();
    }

    /**
     * @return the responseType
     */
    public String getResponseType() {
        return RESPONSE_TYPE;
    }

    /**
     * @return the scheme
     */
    public String getScheme() {
        return mOAuthDataController.getScheme();
    }

    /**
     * @return the host
     */
    public String getHost() {
        return mOAuthDataController.getAuthority();
    }

    public String getUrlPath() {
        return mOAuthDataController.getUrlPath();
    }

    /**
     * @return the client secret
     */
    public String getClientSecret() {
        return mOAuthDataController.getClientSecret();
    }

    /**
     * build the oauth URI.
     * 
     * @return URI uri
     * @throws URISyntaxException
     *             exception
     */
    public URI buildUrl() throws URISyntaxException {
        HttpClientURIBuilder ub = new HttpClientURIBuilder(getUrlPath());
        ub.setHost(getHost());
        ub.setScheme(getScheme());
        ub.addParameter("response_type", getResponseType());
        ub.addParameter("client_id", getClientId());
        if (StringUtils.isNotEmpty(getOptionalState())) {
            ub.addParameter("state", getOptionalState());
        }
        if (StringUtils.isNotEmpty(getRedirectUrl())) {
            ub.addParameter("redirect_uri", getRedirectUrl());
        }
        HttpClientURLEncodedUtils.format(ub.getQueryParams(), "UTF-8");
        return ub.build();
    }
}
