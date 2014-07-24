package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;
import org.junit.Assert;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class UpdateGroupMembershipRequestTest extends RequestTestBase {

    @Test
    public void uriTest() {
        Assert.assertEquals("/group_memberships/123", UpdateGroupMembershipRequest.getUri("123"));
    }

    @Test
    public void testRequestWellFormed() throws BoxRestException, AuthFatalFailureException {
        String id = "1234";
        UpdateGroupMembershipRequest request = new UpdateGroupMembershipRequest(CONFIG, JSON_PARSER, id, null);
        this.testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(UpdateGroupMembershipRequest.getUri(id)), HttpStatus.SC_OK, RestMethod.PUT);
    }

}
