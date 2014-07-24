package com.box.boxjavalibv2.authorization;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.authorization.DefaultRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.IBoxRequest;

/**
 * Shared link authorization. This authorization can be used in addition to other authorization(currently only OAuth) to support authorization to access a
 * shared link.
 */
public class SharedLinkAuthorization extends DefaultRequestAuth {

    private static final String HEADER_NAME = "BoxApi";

    private final String mSharedLink;
    private String mPassword = null;
    private final OAuthAuthorization mOauth;

    /**
     * Constructor.
     * 
     * @param sharedLink
     *            shared link
     */
    public SharedLinkAuthorization(final OAuthAuthorization oauth, final String sharedLink, final String password) {
        this.mOauth = oauth;
        this.mSharedLink = sharedLink;
        this.mPassword = password;
    }

    /**
     * Set password for the shared link auth, this is needed if a shared file is password protected.
     * 
     * @param password
     *            password
     */
    public void setPassword(final String password) {
        this.mPassword = password;
    }

    @Override
    public void setAuth(final IBoxRequest request) throws BoxRestException, AuthFatalFailureException {
        super.setAuth(request);
        mOauth.setAuth(request);
        request.addHeader(HEADER_NAME, getAuthString().toString());
    }

    /**
     * Get auth header value string.
     * 
     * @return auth header value string.
     */
    @SuppressWarnings("deprecation")
    public StringBuilder getAuthString() {
        StringBuilder sbr = new StringBuilder();
        sbr.append("shared_link=").append(mSharedLink);
        if (StringUtils.isNotEmpty(mPassword)) {
            sbr.append("&shared_link_password=").append(URLEncoder.encode(mPassword));
        }
        return sbr;
    }
}
