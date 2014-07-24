package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class CreateGroupMembershipTest extends RequestTestBase {

    @Test
    public void uriTest() {
        Assert.assertEquals("/group_memberships", CreateGroupMembershipRequest.getUri());
    }

    @Test
    public void testRequestWellFormed() throws BoxRestException, AuthFatalFailureException {
        CreateGroupMembershipRequest request = new CreateGroupMembershipRequest(CONFIG, JSON_PARSER, null);
        this.testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(CreateGroupMembershipRequest.getUri()), HttpStatus.SC_CREATED, RestMethod.POST);
    }

}
