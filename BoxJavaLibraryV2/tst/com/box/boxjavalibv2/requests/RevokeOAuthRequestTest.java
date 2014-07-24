package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class RevokeOAuthRequestTest {

    @Test
    public void testUri() {
        Assert.assertEquals("/oauth2/revoke", RevokeOAuthRequest.getUri());
    }

    @Test
    public void testRestMethod() throws BoxRestException, AuthFatalFailureException {
        RevokeOAuthRequest request = new RevokeOAuthRequest(TestUtils.getConfig(), null, null);
        Assert.assertEquals(RestMethod.POST, request.getRestMethod());

    }
}
