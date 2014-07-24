package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class RestoreTrashItemRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        String id = "12344";
        Assert.assertEquals("/folders/" + id, RestoreTrashItemRequest.getUri(id, BoxResourceType.FOLDER));
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

        RestoreTrashItemRequest request = new RestoreTrashItemRequest(CONFIG, JSON_PARSER, id, type, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(RestoreTrashItemRequest.getUri(id, type)), HttpStatus.SC_CREATED, RestMethod.POST);
    }
}
