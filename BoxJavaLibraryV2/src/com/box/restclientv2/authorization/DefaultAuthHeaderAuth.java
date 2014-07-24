package com.box.restclientv2.authorization;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import org.apache.commons.codec.CharEncoding;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.IBoxRequest;

/**
 * Auth to be set in http request header.
 */
public class DefaultAuthHeaderAuth extends DefaultRequestAuth {

    private final String mAuthToken;
    private final String mApiKey;
    protected final String mDeviceId;
    protected final String mDeviceName;

    /**
     * Constructor.
     * 
     * @param authToken
     *            auth token.
     * @param apiKey
     *            api key
     * @param deviceId
     *            Device ID.
     * @param deviceName
     *            Device name.
     */
    public DefaultAuthHeaderAuth(final String authToken, final String apiKey, String deviceId, String deviceName) {
        this.mAuthToken = authToken;
        this.mApiKey = apiKey;
        this.mDeviceId = deviceId;
        this.mDeviceName = deviceName;
    }

    @Override
    public void setAuth(final IBoxRequest request) throws BoxRestException, AuthFatalFailureException {
        super.setAuth(request);

        request.addHeader(AUTH_HEADER_NAME, getAuthString().toString());
    }

    /**
     * Get auth header value string.
     * 
     * @return auth header value string.
     */
    public StringBuilder getAuthString() {
        StringBuilder sbr = new StringBuilder();
        sbr.append("BoxAuth api_key=").append(mApiKey).append("&auth_token=").append(mAuthToken);
        try {
            sbr.append("&device_id=").append(URLEncoder.encode(mDeviceId, CharEncoding.UTF_8));
            sbr.append("&device_name=").append(URLEncoder.encode(mDeviceName, CharEncoding.UTF_8));
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sbr;
    }
}
