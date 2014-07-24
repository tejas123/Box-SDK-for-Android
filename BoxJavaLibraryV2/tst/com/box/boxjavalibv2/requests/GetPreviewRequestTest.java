package com.box.boxjavalibv2.requests;

import java.util.Map;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.requests.requestobjects.BoxImageRequestObject;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;

public class GetPreviewRequestTest extends RequestTestBase {

    @Test
    public void testUri() {
        Assert.assertEquals("/files/123/preview.png", GetPreviewRequest.getUri("123", "png"));
    }

    @Test
    public void testRequestIsWellFormed() throws BoxRestException, AuthFatalFailureException {
        String id = "testid123";
        String extension = "png";
        int page = 1;
        int minWidth = 2;
        int maxWidth = 4;
        int minHeight = 3;
        int maxHeight = 5;

        BoxImageRequestObject requestObject = BoxImageRequestObject.pagePreviewRequestObject(page, minWidth, maxWidth, minHeight, maxHeight);
        GetPreviewRequest request = new GetPreviewRequest(CONFIG, JSON_PARSER, id, extension, requestObject);
        testRequestIsWellFormed(request, TestUtils.getConfig().getApiUrlAuthority(),
            TestUtils.getConfig().getApiUrlPath().concat(GetPreviewRequest.getUri(id, extension)), HttpStatus.SC_OK, RestMethod.GET);

        Map<String, String> queries = request.getQueryParams();
        Assert.assertEquals(Integer.toString(minWidth), queries.get("min_width"));
        Assert.assertEquals(Integer.toString(maxWidth), queries.get("max_width"));
        Assert.assertEquals(Integer.toString(minHeight), queries.get("min_height"));
        Assert.assertEquals(Integer.toString(maxHeight), queries.get("max_height"));
        Assert.assertEquals(Integer.toString(page), queries.get("page"));

    }
}
