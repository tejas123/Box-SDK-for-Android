package com.box.boxjavalibv2.authorization;

import java.net.URLEncoder;

import org.apache.commons.lang.StringUtils;

import com.box.restclientv2.authorization.DefaultAuthHeaderAuth;

/**
 * Class implementing the shared item authorization used to make api calls on shared link items.
 */
public class SharedItemAuth extends DefaultAuthHeaderAuth {

    private final String sharedLink;
    private final String password;

    /**
     * Constructor.
     * 
     * @param authToken
     *            auth token
     * @param apiKey
     *            api key
     * @param deviceId
     *            Device ID.
     * @param deviceName
     *            Device name.
     * @param sharedLink
     *            shared link
     * @param password
     *            passsword (uses null if there is no password available)
     */
    public SharedItemAuth(String authToken, String apiKey, String deviceId, String deviceName, String sharedLink, String password) {
        super(authToken, apiKey, deviceId, deviceName);
        this.sharedLink = sharedLink;
        this.password = password;
    }

    @SuppressWarnings("deprecation")
    @Override
    public StringBuilder getAuthString() {
        StringBuilder sbr = super.getAuthString().append("&shared_link=").append(URLEncoder.encode(sharedLink));
        if (StringUtils.isNotEmpty(password)) {
            sbr.append("&shared_link_password=").append(URLEncoder.encode(password));
        }
        sbr.append("&device_id=").append(URLEncoder.encode(mDeviceId));
        sbr.append("&device_name=").append(URLEncoder.encode(mDeviceName));
        return sbr;
    }
}
