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
import com.box.boxjavalibv2.requests.requestobjects.BoxEmailAliasRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class AddEmailAliasRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/users/123/email_aliases", CreateEmailAliasRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException, BoxJSONException {
        String userId = "testuserid";
        String email = "testeamail@box.com";

        BoxEmailAliasRequestObject obj = BoxEmailAliasRequestObject.addEmailAliasRequestObject(email);

        CreateEmailAliasRequest request = new CreateEmailAliasRequest(CONFIG, JSON_PARSER, userId, obj);

        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(CreateEmailAliasRequest.getUri(userId)), HttpStatus.SC_CREATED, RestMethod.POST);
        HttpEntity en = request.getRequestEntity();
        Assert.assertTrue(en instanceof StringEntity);
        MapJSONStringEntity mEntity = new MapJSONStringEntity();
        mEntity.put("email", email);
        assertEqualStringEntity(mEntity, en);

    }
}
