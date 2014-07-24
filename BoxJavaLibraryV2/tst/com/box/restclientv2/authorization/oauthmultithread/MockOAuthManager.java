package com.box.restclientv2.authorization.oauthmultithread;

import java.util.HashMap;
import java.util.Map;

import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.resourcemanagers.BoxOAuthManagerImpl;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxOAuthRequestObject;

public class MockOAuthManager extends BoxOAuthManagerImpl {

    public static volatile boolean refreshShouldFail;

    public MockOAuthManager() {
        super(null, null, null, null);
    }

    @Override
    public BoxOAuthToken refreshOAuth(final BoxOAuthRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        if (refreshShouldFail) {
            throw new AuthFatalFailureException();
        }
        // Let's say refreshing a token takes 1 second.
        try {
            Thread.sleep(1000);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        return createMockToken(MockRestClient.accessToken);
    }

    @Override
    public BoxOAuthToken createOAuth(BoxOAuthRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        return createMockToken(MockRestClient.accessToken);
    }

    private BoxOAuthToken createMockToken(String accessToken) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("access_token", accessToken);
        return new BoxOAuthToken(map);
    }
}
