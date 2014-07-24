package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetFileVersionsRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/files/123/versions", GetFileVersionsRequest.getUri("123"));
    }

    public void testRequestIsWellFormed(boolean isFolder) throws BoxRestException, AuthFatalFailureException {
        String id = "testid123";

        GetFileVersionsRequest request = new GetFileVersionsRequest(CONFIG, JSON_PARSER, id, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(), GetFileVersionsRequest.getUri(id), HttpStatus.SC_OK, RestMethod.GET);

    }

}
