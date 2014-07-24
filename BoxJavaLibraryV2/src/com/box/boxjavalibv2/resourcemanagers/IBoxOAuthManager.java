package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.restclientv2.exceptions.BoxRestException;

public interface IBoxOAuthManager extends IBoxResourceManager {

    /**
     * @param code
     *            The authorization code you retrieved previously used to create OAuth.
     * @param clientId
     *            client id
     * @param clientSecret
     *            client secret
     * @param redirectUrl
     *            optional, required only if a redirect URI is not configured at <a href="http://box.com/developers/services">Box Developers Services</a>, use
     *            null if don't want to supply this field.
     * @return
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxOAuthToken createOAuth(final String code, final String clientId, final String clientSecret, final String redirectUrl) throws BoxRestException,
        BoxServerException, AuthFatalFailureException;

    /**
     * 
     * @param code
     *            The authorization code you retrieved previously used to create OAuth.
     * @param clientId
     *            client id
     * @param clientSecret
     *            client secret
     * @param redirectUrl
     *            optional, required only if a redirect URI is not configured at <a href="http://box.com/developers/services">Box Developers Services</a>, use
     *            null if don't want to supply this field.
     * @param deviceId
     *            device id
     * @param deviceName
     *            device name
     * @return
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxOAuthToken createOAuth(final String code, final String clientId, final String clientSecret, final String redirectUrl, final String deviceId,
        final String deviceName) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Refresh OAuth.
     * 
     * @param refreshToken
     *            refresh token.
     * @param clientId
     *            client id.
     * @param clientSecret
     *            client secret.
     * @param deviceId
     *            device id.
     * @param deviceName
     *            device name.
     * @return refreshed BoxOAuthToken.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxOAuthToken refreshOAuth(String refreshToken, String clientId, String clientSecret, String deviceId, String deviceName) throws BoxRestException,
        BoxServerException, AuthFatalFailureException;

    /**
     * Refresh OAuth.
     * 
     * @param refreshToken
     *            refresh token.
     * @param clientId
     *            client id.
     * @param clientSecret
     *            client secret.
     * @return refreshed BoxOAuthToken
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxOAuthToken refreshOAuth(String refreshToken, String clientId, String clientSecret) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Revoke OAuth.
     * 
     * @param accessToken
     *            current access token (to be revoked).
     * @param clientId
     *            client id.
     * @param clientSecret
     *            client secret.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void revokeOAuth(String accessToken, String clientId, String clientSecret) throws BoxServerException, BoxRestException, AuthFatalFailureException;
}