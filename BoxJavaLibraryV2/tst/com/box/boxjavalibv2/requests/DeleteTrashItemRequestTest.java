package com.box.boxjavalibv2.requests;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class DeleteTrashItemRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        String id = "12344";
        Assert.assertEquals("/folders/" + id + "/trash", DeleteTrashItemRequest.getUri(id, BoxResourceType.FOLDER));
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

        DeleteTrashItemRequest request = new DeleteTrashItemRequest(CONFIG, JSON_PARSER, id, type, null);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(DeleteTrashItemRequest.getUri(id, type)), HttpStatus.SC_NO_CONTENT, RestMethod.DELETE);
    }
}
