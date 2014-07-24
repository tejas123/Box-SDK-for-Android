package com.box.boxjavalibv2.requests;

import java.io.IOException;
import java.util.Map;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class GetFolderCollaborationsRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/folders/123/collaborations", GetFolderCollaborationsRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException {
        String folderId = "testfolderid123";

        GetFolderCollaborationsRequest request = new GetFolderCollaborationsRequest(CONFIG, JSON_PARSER, folderId, new BoxDefaultRequestObject());
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetFolderCollaborationsRequest.getUri(folderId)), HttpStatus.SC_OK, RestMethod.GET);
    }

    @Test
    public void testNoStatusSetInRequestIfInputNullStatus() throws BoxRestException {
        GetFolderCollaborationsRequest request = new GetFolderCollaborationsRequest(CONFIG, JSON_PARSER, "1", null);

        Map<String, String> queries = request.getQueryParams();
        Assert.assertFalse(queries.containsKey("status"));
    }
}
