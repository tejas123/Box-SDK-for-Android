package com.box.boxjavalibv2.requests;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.http.HttpStatus;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxFileUploadRequestObject;

/**
 * Test UploadFileRequest.
 */
public class UploadFileRequestTest extends RequestTestBase {

    /**
     * Test uri.
     */
    @Test
    public void testUri() {
        Assert.assertEquals("/files/content", UploadFileRequest.getUri());
    }

    /**
     * Test reqeust is well formed.
     * 
     * @throws IOException
     *             exception
     * @throws BoxRestException
     *             exception
     * @throws AuthFatalFailureException
     * @throws BoxJSONException
     */
    @Test
    public void testRequstIsWellFormed() throws IOException, BoxRestException, AuthFatalFailureException, BoxJSONException {
        String parentId = "testid123";
        String content = "testcontent456";
        File f = null;
        f = TestUtils.createTempFile(content);

        String fileName = "testfilename998";

        UploadFileRequest request = new UploadFileRequest(CONFIG, JSON_PARSER, BoxFileUploadRequestObject.uploadFileRequestObject(parentId, fileName, f));
        testRequestIsWellFormed(request, TestUtils.getConfig().getUploadUrlAuthority(),
            TestUtils.getConfig().getUploadUrlPath().concat(UploadFileRequest.getUri()), HttpStatus.SC_CREATED, RestMethod.POST);
    }
}
