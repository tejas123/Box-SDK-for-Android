package com.box.boxjavalibv2.request.requestobjects;

import junit.framework.Assert;

import org.junit.Test;

import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxOAuthRequestObject;

public class BoxOAuthRequestObjectTest {

    // DO NOT change the hard coded "box_device_id" and "box_device_name" in the test to use prod code strings. If string in prod code changes, this test should
    // fail.
    final String DEVICE_ID = "box_device_id";
    final String DEVICE_NAME = "box_device_name";

    private static final String CLIENT_ID = "testclientId123";
    private static final String CLIENT_SECRET = "testClientSecret324324";

    @Test
    public void testRevokeRequestObject() throws BoxRestException {
        String token = "testtoken1212";
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.revokeOAuthRequestObject(token, CLIENT_ID, CLIENT_SECRET);

        Assert.assertEquals(CLIENT_ID, obj.getFromEntity("client_id"));
        Assert.assertEquals(CLIENT_SECRET, obj.getFromEntity("client_secret"));
        Assert.assertEquals(token, obj.getFromEntity("token"));
    }

    @Test
    public void testCreateRequestObject() throws BoxRestException {
        String code = "testcode";
        String url = "testurl";
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.createOAuthRequestObject(code, CLIENT_ID, CLIENT_SECRET, url);

        Assert.assertEquals(CLIENT_ID, obj.getFromEntity("client_id"));
        Assert.assertEquals(CLIENT_SECRET, obj.getFromEntity("client_secret"));
        Assert.assertEquals(code, obj.getFromEntity("code"));
        Assert.assertEquals(url, obj.getFromEntity("redirect_url"));
    }

    @Test
    public void testRefreshRequestObject() throws BoxRestException {
        String token = "testrefreshtoken";
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.refreshOAuthRequestObject(token, CLIENT_ID, CLIENT_SECRET);

        Assert.assertEquals(CLIENT_ID, obj.getFromEntity("client_id"));
        Assert.assertEquals(CLIENT_SECRET, obj.getFromEntity("client_secret"));
        Assert.assertEquals(token, obj.getFromEntity("refresh_token"));
    }

    @Test
    public void testDeviceNameId() {
        String deviceId = "deviceidtest";
        String deviceName = "devicenametest";
        BoxOAuthRequestObject obj = BoxOAuthRequestObject.createOAuthRequestObject("", "", "", "").setDevice(deviceId, deviceName);

        Assert.assertEquals(deviceId, obj.getFromEntity(DEVICE_ID));
        Assert.assertEquals(deviceName, obj.getFromEntity(DEVICE_NAME));

        BoxOAuthRequestObject obj2 = BoxOAuthRequestObject.refreshOAuthRequestObject("", "", "").setDevice(deviceId, deviceName);

        Assert.assertEquals(deviceId, obj2.getFromEntity(DEVICE_ID));
        Assert.assertEquals(deviceName, obj2.getFromEntity(DEVICE_NAME));
    }
}
