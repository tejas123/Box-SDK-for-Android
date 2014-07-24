package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetSharedItemRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/shared_items", GetSharedItemRequest.getUri());
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {

        GetSharedItemRequest request = new GetSharedItemRequest(CONFIG, JSON_PARSER, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(), TestUtils.getConfig().getApiUrlPath()
            .concat(GetSharedItemRequest.getUri()), HttpStatus.SC_OK, RestMethod.GET);

    }
}