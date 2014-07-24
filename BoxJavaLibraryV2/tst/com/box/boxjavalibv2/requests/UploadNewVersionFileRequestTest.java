package com.box.boxjavalibv2.requests;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.Header;
import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.boxjavalibv2.utils.Constants;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxFileUploadRequestObject;

/**
 * Test UploadNewVersionFileRequest.
 */
public class UploadNewVersionFileRequestTest extends RequestTestBase {

    /**
     * Test uri.
     */
    @Test
    public void testUri() {
        Assert.assertEquals("/files/123/content", UploadNewVersionFileRequest.getUri("123"));
    }

    /**
     * Test request is well formed.
     * 
     * @throws IOException
     *             exception
     * @throws BoxRestException
     *             exception
     * @throws AuthFatalFailureException
     */
    @Test
    public void testRequstIsWellFormed() throws IOException, BoxRestException, AuthFatalFailureException {
        String fileId = "testid123";
        String sha1 = "testsha1456";
        String content = "testcontent456";
        File f = null;

        f = TestUtils.createTempFile(content);

        String fileName = "testfilename998";
        BoxFileUploadRequestObject obj = BoxFileUploadRequestObject.uploadNewVersionRequestObject(fileName, f);
        obj.getRequestExtras().setIfMatch(sha1);
        UploadNewVersionFileRequest request = new UploadNewVersionFileRequest(CONFIG, JSON_PARSER, fileId, obj);
        testRequestIsWellFormed(request, TestUtils.getConfig().getUploadUrlAuthority(),
            TestUtils.getConfig().getUploadUrlPath().concat(UploadNewVersionFileRequest.getUri(fileId)), HttpStatus.SC_CREATED, RestMethod.POST);

        Header header = request.getRawRequest().getFirstHeader(Constants.IF_MATCH);
        Assert.assertNotNull(header);
        Assert.assertEquals(sha1, header.getValue());

    }
}
