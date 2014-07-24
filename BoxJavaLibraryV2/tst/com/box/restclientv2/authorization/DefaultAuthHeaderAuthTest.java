package com.box.restclientv2.authorization;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class DefaultAuthHeaderAuthTest {

    @Test
    public void testSetAuth() throws BoxRestException, AuthFatalFailureException {
        DefaultBoxRequest request = new DefaultBoxRequest(TestUtils.getConfig(), new BoxJSONParser(new BoxResourceHub()), "fakeuri", RestMethod.GET, null);
        String token = "test token";
        String apiKey = "test api key";
        String deviceId = "f9h30fhflzkhg84ghgzhgr534653";
        String deviceName = "Galaxy Death Star";
        DefaultAuthHeaderAuth auth = new DefaultAuthHeaderAuth(token, apiKey, deviceId, deviceName);
        auth.setAuth(request);
        request.prepareRequest();
        Assert.assertEquals(auth.getAuthString().toString(), request.getRawRequest().getHeaders(DefaultAuthHeaderAuth.AUTH_HEADER_NAME)[0].getValue());
    }
}
