package com.box.restclientv2.authorization;

import java.util.Map;

import junit.framework.Assert;

import org.apache.http.client.methods.HttpRequestBase;
import org.junit.Test;

import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class DefaultUsernamePasswordAuthTest {

    @Test
    public void testSetAuth() {
        try {
            DefaultBoxRequest request = new DefaultBoxRequest(TestUtils.getConfig(), new BoxJSONParser(new BoxResourceHub()), "/uri", RestMethod.GET, null);
            String userName = "testusername";
            String password = "testpassword";

            DefaultUsernamePasswordAuth auth = new DefaultUsernamePasswordAuth(userName, password);
            auth.setAuth(request);
            Map<String, String> queryParams = request.getQueryParams();
            Assert.assertEquals(userName, queryParams.get(DefaultUsernamePasswordAuth.LOGIN));
            Assert.assertEquals(password, queryParams.get(DefaultUsernamePasswordAuth.PASSWORD));

            request.prepareRequest();
            HttpRequestBase rawRequest = request.getRawRequest();
            String query = rawRequest.getURI().getQuery();
            Assert.assertTrue(query.contains(DefaultUsernamePasswordAuth.LOGIN + "=" + userName));
            Assert.assertTrue(query.contains(DefaultUsernamePasswordAuth.PASSWORD + "=" + password));
        }
        catch (Exception e) {
            Assert.fail();
        }
    }
}
