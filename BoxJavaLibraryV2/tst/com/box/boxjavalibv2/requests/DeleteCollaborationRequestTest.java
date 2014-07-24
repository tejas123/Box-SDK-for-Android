package com.box.boxjavalibv2.requests;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class DeleteCollaborationRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/collaborations/123", DeleteCollaborationRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException {
        String collabId = "testcollabid123";

        DeleteCollaborationRequest request = new DeleteCollaborationRequest(CONFIG, JSON_PARSER, collabId, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(DeleteCollaborationRequest.getUri(collabId)), HttpStatus.SC_NO_CONTENT, RestMethod.DELETE);
    }
}
