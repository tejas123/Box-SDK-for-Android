package com.box.boxjavalibv2.requests;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.entity.StringEntity;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class CreateEnterpriseUserRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/users", CreateEnterpriseUserRequest.getUri());
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException, BoxJSONException,
        NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        String name = "testname";
        String login = "testlogin";
        String role = "testrole";
        String language = "tetstlan";
        String title = "testtitle";
        String phone = "testphone";
        String address = "testaddress";
        String status = "teststatus";
        boolean sync = true;
        int space = 123;
        boolean seeManaged = true;
        boolean exemptLimit = true;
        boolean exemptLogin = true;
        String key1 = "testkey1";
        String value1 = "testvalue1";
        String key2 = "testkey2";
        String value2 = "testvalue2";
        LinkedHashMap<String, String> codes = new LinkedHashMap<String, String>();
        codes.put(key1, value1);
        codes.put(key2, value2);
        BoxUserRequestObject obj = getUserRequestObject(login, name, role, language, sync, title, phone, address, space, codes, seeManaged, status,
            exemptLimit, exemptLogin);
        CreateEnterpriseUserRequest request = new CreateEnterpriseUserRequest(CONFIG, JSON_PARSER, obj);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(CreateEnterpriseUserRequest.getUri()), HttpStatus.SC_CREATED, RestMethod.POST);

        HttpEntity entity = request.getRequestEntity();
        Assert.assertTrue(entity instanceof StringEntity);

        assertEqualStringEntity(obj, entity);
    }

    private BoxUserRequestObject getUserRequestObject(String login, String name, String role, String language, boolean sync, String title, String phone,
        String address, double space, LinkedHashMap<String, String> codes, boolean seeManaged, String status, boolean exemptLimit, boolean exemptLogin) {
        BoxUserRequestObject obj = BoxUserRequestObject.createEnterpriseUserRequestObject(login, name);
        obj.setRole(role);
        obj.setLanguage(language);
        obj.setSyncEnabled(sync);
        obj.setJobTitle(title);
        obj.setPhone(phone);
        obj.setAddress(address);
        obj.setSpaceAmount(space);
        obj.setTrackingCodes(codes);
        obj.setCanSeeManagedUsers(seeManaged);
        obj.setStatus(status);
        obj.setExemptFromDeviceLimits(exemptLimit);
        obj.setExemptFromLoginVerification(exemptLogin);
        return obj;
    }
}
