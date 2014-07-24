package com.box.boxjavalibv2.requests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.junit.Assert;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxSharedLinkAccess;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonentities.BoxSharedLinkRequestEntity;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

/**
 * Test CreateSharedLinkRequest.
 */
public class CreateSharedLinkRequestTest extends RequestTestBase {

    // CHECKSTYLE:OFF
    @Test
    public void testUri() {
        Assert.assertEquals("/folders/123", CreateSharedLinkRequest.getUri("123", BoxResourceType.FOLDER));
        Assert.assertEquals("/files/123", CreateSharedLinkRequest.getUri("123", BoxResourceType.FILE));
    }

    @Test
    public void testFileRequestWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException, BoxJSONException,
        NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        testRequestIsWellFormed(BoxResourceType.FILE);
    }

    @Test
    public void testFolderRequestWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException, BoxJSONException,
        NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        testRequestIsWellFormed(BoxResourceType.FOLDER);
    }

    private void testRequestIsWellFormed(final BoxResourceType type) throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException,
        BoxJSONException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String id = "testid123";
        String access = BoxSharedLinkAccess.COLLABORATORS;
        Date unsharedAt = new Date();

        BoxSharedLinkRequestEntity sEntity = new BoxSharedLinkRequestEntity(access);
        sEntity.setPermissions(true);
        sEntity.setUnshared_at(unsharedAt);
        BoxSharedLinkRequestObject obj = BoxSharedLinkRequestObject.createSharedLinkRequestObject(sEntity);

        CreateSharedLinkRequest request = new CreateSharedLinkRequest(CONFIG, JSON_PARSER, id, obj, type);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(CreateSharedLinkRequest.getUri(id, type)), HttpStatus.SC_OK, RestMethod.PUT);
        HttpEntity en = request.getRequestEntity();
        Assert.assertTrue(en instanceof StringEntity);

        super.assertEqualStringEntity(obj, en);
    }
}
