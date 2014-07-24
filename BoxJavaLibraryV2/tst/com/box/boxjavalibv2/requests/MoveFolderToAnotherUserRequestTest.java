package com.box.boxjavalibv2.requests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.requests.requestobjects.BoxSimpleUserRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class MoveFolderToAnotherUserRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/users/123/folders/456", MoveFolderToAnotherUserRequest.getUri("123", "456"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException, BoxJSONException,
        NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String userId = "testuserid";
        String folderId = "testfolderid";
        boolean notify = true;
        BoxSimpleUserRequestObject obj = BoxSimpleUserRequestObject.moveFolderToAnotherUserRequestEntity(folderId, notify);
        MoveFolderToAnotherUserRequest request = new MoveFolderToAnotherUserRequest(CONFIG, JSON_PARSER, userId, folderId, obj);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(MoveFolderToAnotherUserRequest.getUri(userId, folderId)), HttpStatus.SC_OK, RestMethod.PUT);
        Assert.assertEquals(Boolean.toString(notify), request.getQueryParams().get("notify"));

        HttpEntity en = request.getRequestEntity();
        Assert.assertTrue(en instanceof StringEntity);
        assertEqualStringEntity(obj, en);
    }
}
