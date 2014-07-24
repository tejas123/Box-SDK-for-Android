package com.box.restclientv2.authorization.oauthmultithread;

import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class MockRequest extends DefaultBoxRequest {

    public MockRequest() throws BoxRestException {
        super(TestUtils.getConfig(), new BoxJSONParser(new BoxResourceHub()), "uri", RestMethod.GET, null);
    }

}
