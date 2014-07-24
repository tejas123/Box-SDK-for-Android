package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetItemRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/files/123", GetItemRequest.getUri("123", BoxResourceType.FILE));
        Assert.assertEquals("/folders/123", GetItemRequest.getUri("123", BoxResourceType.FOLDER));
    }

    @Test
    public void testFileRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        testRequestIsWellFormed(BoxResourceType.FILE);
    }

    @Test
    public void testFolderRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        testRequestIsWellFormed(BoxResourceType.FOLDER);
    }

    public void testRequestIsWellFormed(BoxResourceType type) throws BoxRestException, AuthFatalFailureException {
        String id = "testid123";

        GetItemRequest request = new GetItemRequest(CONFIG, JSON_PARSER, id, type, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetItemRequest.getUri(id, type)), HttpStatus.SC_OK, RestMethod.GET);

    }
}