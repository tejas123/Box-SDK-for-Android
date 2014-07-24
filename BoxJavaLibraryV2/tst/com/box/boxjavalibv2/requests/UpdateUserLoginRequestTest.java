package com.box.boxjavalibv2.requests;

import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserUpdateLoginRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class UpdateUserLoginRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/users/123", UpdateUserLoginRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException, BoxJSONException {
        String userId = "testuserid";
        String newLogin = "testnewlogin";
        UpdateUserLoginRequest request = new UpdateUserLoginRequest(CONFIG, JSON_PARSER, userId,
            BoxUserUpdateLoginRequestObject.updateUserPrimaryLoginRequestObject(newLogin));

        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(UpdateUserLoginRequest.getUri(userId)), HttpStatus.SC_OK, RestMethod.PUT);

        HttpEntity entity = request.getRequestEntity();
        Assert.assertTrue(entity instanceof StringEntity);

        MapJSONStringEntity mEntity = new MapJSONStringEntity();
        mEntity.put("login", newLogin);
        assertEqualStringEntity(mEntity, entity);
    }
}
