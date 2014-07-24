package com.box.boxjavalibv2.requests;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class DeleteEmailAliasRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/users/123/email_aliases/456", DeleteEmailAliasRequest.getUri("123", "456"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException {
        String userId = "testuserid";
        String emailId = "testemailid";
        DeleteEmailAliasRequest request = new DeleteEmailAliasRequest(CONFIG, JSON_PARSER, userId, emailId, null);

        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(DeleteEmailAliasRequest.getUri(userId, emailId)), HttpStatus.SC_OK, RestMethod.DELETE);

    }

}
