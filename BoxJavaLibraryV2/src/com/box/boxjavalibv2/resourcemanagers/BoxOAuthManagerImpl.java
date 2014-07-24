package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.CreateOAuthRequest;
import com.box.boxjavalibv2.requests.RefreshOAuthRequest;
import com.box.boxjavalibv2.requests.RevokeOAuthRequest;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxOAuthRequestObject;

/**
 * API for OAuth. Full details about the Box API can be found at <a href="http://developers.box.com/oauth/">http://developers.box.com/docs</a>
 */
public class BoxOAuthManagerImpl extends AbstractBoxResourceManager implements IBoxOAuthManager {

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param resourceHub
     *            resource hub
     * @param parser
     *            json parser
     * @param restClient
     *            REST client to make api calls.
     */
    public BoxOAuthManagerImpl(final IBoxConfig config, IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, null, restClient);
    }

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
     */
    @Override
    public BoxOAuthToken createOAuth(final String code, final String clientId, final String clientSecret, final String redirectUrl) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.createOAuthRequestObject(code, clientId, clientSecret, redirectUrl);
        return createOAuth(obj);
    }

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
     */
    @Override
    public BoxOAuthToken createOAuth(final String code, final String clientId, final String clientSecret, final String redirectUrl, final String deviceId,
        final String deviceName) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.createOAuthRequestObject(code, clientId, clientSecret, redirectUrl).setDevice(deviceId, deviceName);
        return createOAuth(obj);
    }

    public BoxOAuthToken createOAuth(BoxOAuthRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        CreateOAuthRequest request = new CreateOAuthRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxOAuthToken) getResponseAndParseAndTryCast(request, BoxResourceType.OAUTH_DATA, getJSONParser());
    }

    @Override
    public BoxOAuthToken refreshOAuth(String refreshToken, String clientId, String clientSecret, String deviceId, String deviceName) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.refreshOAuthRequestObject(refreshToken, clientId, clientSecret).setDevice(deviceId, deviceName);
        return createOAuth(obj);
    }

    @Override
    public BoxOAuthToken refreshOAuth(String refreshToken, String clientId, String clientSecret) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.refreshOAuthRequestObject(refreshToken, clientId, clientSecret);
        return createOAuth(obj);
    }

    @Override
    public void revokeOAuth(String accessToken, String clientId, String clientSecret) throws BoxServerException, BoxRestException, AuthFatalFailureException {
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.revokeOAuthRequestObject(accessToken, clientId, clientSecret);
        revokeOAuth(obj);
    }

    public BoxOAuthToken refreshOAuth(final BoxOAuthRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        RefreshOAuthRequest request = new RefreshOAuthRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxOAuthToken) getResponseAndParseAndTryCast(request, BoxResourceType.OAUTH_DATA, getJSONParser());
    }

    public void revokeOAuth(final BoxOAuthRequestObject requestObject) throws BoxServerException, BoxRestException, AuthFatalFailureException {
        RevokeOAuthRequest request = new RevokeOAuthRequest(getConfig(), getJSONParser(), requestObject);
        executeRequestWithNoResponseBody(request);
    }
}
