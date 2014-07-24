package com.box.boxjavalibv2.requests;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetFolderItemsRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/folders/123/items", GetFolderItemsRequest.getUri("123"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        String id = "testid123";
        int limit = 5;
        int offset = 2;
        String fieldA = "fieldA";
        String fieldB = "fieldB";
        List<String> fields = new ArrayList<String>();
        fields.add(fieldA);
        fields.add(fieldB);

        BoxPagingRequestObject obj = BoxPagingRequestObject.pagingRequestObject(limit, offset);
        obj.getRequestExtras().addFields(fields);
        GetFolderItemsRequest request = new GetFolderItemsRequest(CONFIG, JSON_PARSER, id, obj);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetFolderItemsRequest.getUri(id)), HttpStatus.SC_OK, RestMethod.GET);

        Map<String, String> queries = request.getQueryParams();
        Assert.assertEquals(Integer.toString(limit), queries.get("limit"));
        Assert.assertEquals(Integer.toString(offset), queries.get("offset"));
        Assert.assertEquals(fieldA + "," + fieldB, queries.get("fields"));
    }
}
