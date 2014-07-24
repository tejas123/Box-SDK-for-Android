package com.box.boxjavalibv2.requests;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetCurrentUserRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/users/me", GetCurrentUserRequest.getUri());
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException {
        GetCurrentUserRequest request = new GetCurrentUserRequest(CONFIG, JSON_PARSER, null);

        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetCurrentUserRequest.getUri()), HttpStatus.SC_OK, RestMethod.GET);

    }
}
