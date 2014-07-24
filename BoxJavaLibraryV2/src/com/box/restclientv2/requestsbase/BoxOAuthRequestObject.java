package com.box.restclientv2.requestsbase;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.CharEncoding;
import org.apache.commons.lang.StringUtils;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;

import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxOAuthRequestObject extends BoxDefaultRequestObject {

    private final static String GRANT_TYPE = "grant_type";
    private final static String CODE = "code";
    private final static String CLIENT_ID = "client_id";
    private final static String CLIENT_SECRET = "client_secret";
    private final static String REDIRECT_URL = "redirect_url";
    private final static String REFRESH_TOKEN = "refresh_token";
    private final static String AUTHORIZATION_CODE = "authorization_code";
    private final static String REVOKE_TOKEN = "token";
    private final static String DEVICE_ID = "box_device_id";
    private final static String DEVICE_NAME = "box_device_name";

    /**
     * Request object to create OAUth.
     * 
     * @param code
     *            The authorization code you retrieved previously used to create OAuth.
     * @param clientId
     *            client id
     * @param clientSecret
     *            client secret
     * @param redirectUri
     *            optional, required only if a redirect URI is not configured at <a href="http://box.com/developers/services">Box Developers Services</a>, use
     *            null if don't want to supply this field.
     * @return BoxOAuthRequestObject
     */
    public static BoxOAuthRequestObject createOAuthRequestObject(final String code, final String clientId, final String clientSecret, final String redirectUrl) {
        BoxOAuthRequestObject obj = new BoxOAuthRequestObject();
        return obj.setAuthCode(code).setClient(clientId, clientSecret).setRedirectUrl(redirectUrl);
    }

    public static BoxOAuthRequestObject refreshOAuthRequestObject(final String refreshToken, final String clientId, final String clientSecret) {
        BoxOAuthRequestObject obj = new BoxOAuthRequestObject();
        return obj.setRefreshToken(refreshToken).setClient(clientId, clientSecret);
    }

    /**
     * Request object to revoke OAuth.
     * 
     * @param revokeToken
     *            The access_token or refresh_token to be destroyed. Only one is required, though both will be destroyed.
     * @param clientId
     * @param clientSecret
     * @return
     */
    public static BoxOAuthRequestObject revokeOAuthRequestObject(final String revokeToken, final String clientId, final String clientSecret) {
        BoxOAuthRequestObject obj = new BoxOAuthRequestObject();
        return obj.setRevokeToken(revokeToken).setClient(clientId, clientSecret);
    }

    /**
     * Set the token to revoke.
     * 
     * @param token
     *            The access_token or refresh_token to be destroyed. Only one is required, though both will be destroyed.
     * @return
     */
    public BoxOAuthRequestObject setRevokeToken(final String token) {
        put(REVOKE_TOKEN, token);
        return this;
    }

    public BoxOAuthRequestObject setRefreshToken(final String refreshToken) {
        put(GRANT_TYPE, REFRESH_TOKEN);
        put(REFRESH_TOKEN, refreshToken);
        return this;
    }

    /**
     * @param code
     *            The authorization code you retrieved previously used to create OAuth.
     * @return
     */
    public BoxOAuthRequestObject setAuthCode(String code) {
        put(GRANT_TYPE, AUTHORIZATION_CODE);
        put(CODE, code);
        return this;
    }

    public BoxOAuthRequestObject setClient(String clientId, String clientSecret) {
        put(CLIENT_ID, clientId);
        put(CLIENT_SECRET, clientSecret);
        return this;
    }

    public BoxOAuthRequestObject setRedirectUrl(String redirectUrl) {
        put(REDIRECT_URL, redirectUrl);
        return this;
    }

    public BoxOAuthRequestObject setDevice(String deviceId, String deviceName) {
        if (StringUtils.isNotEmpty(deviceId) && StringUtils.isNotEmpty(deviceName)) {
            put(DEVICE_ID, deviceId);
            put(DEVICE_NAME, deviceName);
        }
        return this;
    }

    @Override
    public UrlEncodedFormEntity getEntity(IBoxJSONParser parser) throws BoxRestException {
        List<NameValuePair> pairs = new ArrayList<NameValuePair>();
        for (Map.Entry<String, Object> entry : getJSONEntity().entrySet()) {
            Object value = entry.getValue();
            if (value != null && value instanceof String) {
                String strValue = (String) value;
                if (StringUtils.isNotEmpty(strValue)) {
                    pairs.add(new BasicNameValuePair(entry.getKey(), strValue));
                }
            }
        }

        try {
            return new UrlEncodedFormEntity(pairs, CharEncoding.UTF_8);
        }
        catch (UnsupportedEncodingException e) {
            throw new BoxRestException(e);
        }
    }
}
