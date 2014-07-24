package com.box.restclientv2.authorization.oauthmultithread;

import com.box.boxjavalibv2.BoxClient;
import com.box.boxjavalibv2.resourcemanagers.IBoxOAuthManager;
import com.box.boxjavalibv2.testutils.TestUtils;

public class MockBoxClient extends BoxClient {

    public MockBoxClient() {
        super("", "", null, null, new MockRestClient(), TestUtils.getConfig());
    }

    @Override
    public IBoxOAuthManager getOAuthManager() {
        return new MockOAuthManager();
    }

}
