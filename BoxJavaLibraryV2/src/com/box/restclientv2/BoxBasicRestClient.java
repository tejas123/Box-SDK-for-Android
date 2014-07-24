package com.box.restclientv2;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.DefaultHttpClient;

import com.box.boxjavalibv2.BoxConnectionManagerBuilder.BoxConnectionManager;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.IBoxRequest;
import com.box.restclientv2.responses.DefaultBoxResponse;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * Basic implementation of the {@link IBoxRESTClient}.
 */
public class BoxBasicRestClient implements IBoxRESTClient {

    private final DefaultHttpClient mHttpClient;

    public HttpClient getRawHttpClient() {
        return mHttpClient;
    }

    /**
     * Create a DefaultHttpClient, monitored by the connectionManager.
     * 
     * @param connectionManager
     */
    public BoxBasicRestClient(final BoxConnectionManager connectionManager) {
        mHttpClient = connectionManager.getMonitoredRestClient();
    }

    /**
     * Constructor.
     */
    public BoxBasicRestClient() {
        mHttpClient = new DefaultHttpClient();
    }

    @Override
    public IBoxResponse execute(final IBoxRequest boxRequest) throws BoxRestException, AuthFatalFailureException {
        HttpUriRequest httpRequest = boxRequest.prepareRequest();
        HttpResponse response;

        try {
            response = getRawHttpClient().execute(httpRequest);
        }
        catch (IOException e) {
            throw new BoxRestException(e);
        }
        DefaultBoxResponse boxResponse = new DefaultBoxResponse(response);
        boxResponse.setExpectedResponseCode(boxRequest.getExpectedResponseCode());
        return boxResponse;
    }

}
