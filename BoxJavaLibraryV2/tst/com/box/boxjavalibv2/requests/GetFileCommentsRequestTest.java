package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetFileCommentsRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/files/123/comments", GetFileCommentsRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        String id = "testid123";

        GetFileCommentsRequest request = new GetFileCommentsRequest(CONFIG, JSON_PARSER, id, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetFileCommentsRequest.getUri(id)), HttpStatus.SC_OK, RestMethod.GET);

    }
}
