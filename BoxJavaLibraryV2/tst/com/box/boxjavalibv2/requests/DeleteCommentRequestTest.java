package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class DeleteCommentRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/comments/123", DeleteCommentRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        String id = "testid123";

        DeleteCommentRequest request = new DeleteCommentRequest(CONFIG, JSON_PARSER, id, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(DeleteCommentRequest.getUri(id)), HttpStatus.SC_NO_CONTENT, RestMethod.DELETE);

    }
}
