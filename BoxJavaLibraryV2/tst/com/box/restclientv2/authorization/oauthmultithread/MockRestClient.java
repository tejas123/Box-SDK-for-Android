package com.box.restclientv2.authorization.oauthmultithread;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.ProtocolVersion;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;

import com.box.boxjavalibv2.BoxRESTClient;

public class MockRestClient extends BoxRESTClient {

    public static final String INCOMING_AUTH_HEADER = "incoming_oauth";

    public static volatile String accessToken;

    @Override
    protected HttpResponse getResponse(HttpUriRequest request) throws ClientProtocolException, IOException {
        HttpResponse response;
        if (checkOAuth(request)) {
            response = new BasicHttpResponse(createStatusLine(HttpStatus.SC_OK));
        }
        else {
            response = new BasicHttpResponse(createStatusLine(HttpStatus.SC_UNAUTHORIZED));
            response.addHeader(WWW_AUTHENTICATE, OAUTH_ERROR_HEADER + "=" + OAUTH_INVALID_TOKEN);
            response.addHeader(request.getHeaders("Authorization")[0]);
        }
        return response;
    }

    private StatusLine createStatusLine(int statusCode) {
        ProtocolVersion pv = new ProtocolVersion("tst", 0, 1);
        return new BasicStatusLine(pv, statusCode, "");
    }

    private boolean checkOAuth(HttpUriRequest request) {
        if (!request.containsHeader("Authorization")) {
            System.out.println("no auth:" + request.getURI());
            return true;
        }
        String header = request.getHeaders("Authorization")[0].getValue();
        return header.contains(accessToken);
    }
}
