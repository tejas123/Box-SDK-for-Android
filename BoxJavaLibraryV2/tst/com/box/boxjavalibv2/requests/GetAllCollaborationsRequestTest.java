package com.box.boxjavalibv2.requests;

import java.io.IOException;
import java.util.Map;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.requests.requestobjects.BoxGetAllCollabsRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetAllCollaborationsRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/collaborations", GetAllCollaborationsRequest.getUri());
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, IllegalStateException, IOException, AuthFatalFailureException {
        String status = "teststatus789";

        GetAllCollaborationsRequest request = new GetAllCollaborationsRequest(CONFIG, JSON_PARSER,
            BoxGetAllCollabsRequestObject.getAllCollaborationsRequestObject(status));
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetAllCollaborationsRequest.getUri()), HttpStatus.SC_OK, RestMethod.GET);

        Map<String, String> queries = request.getQueryParams();
        Assert.assertEquals(status, queries.get("status"));
    }
}
