package com.box.restclientv2.interfaces;

import java.io.UnsupportedEncodingException;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.junit.Before;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.IBoxRequest;
import com.box.restclientv2.requestsbase.ICookie;
import com.box.restclientv2.responseparsers.IBoxResponseParser;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * This is a unit test class to test and guarantee interfaces of the REST client. No concrete implemented classes are tested.
 */
public class InterfacesTest {

    private IBoxRequestAuth auth;
    private IBoxRESTClient client;
    private IBoxRequest request;
    private IBoxResponse response;
    private IBoxResponseParser responseParser;
    private final String parsedResponse = "parsed response";
    private ICookie cookie;

    @Before
    public void setUp() {
        auth = new IBoxRequestAuth() {

            @Override
            public void setAuth(IBoxRequest request) throws BoxRestException {
            }
        };
        cookie = new ICookie() {

            @Override
            public void setCookie(IBoxRequest request) {
            }

        };
        client = new IBoxRESTClient() {

            @Override
            public IBoxResponse execute(IBoxRequest boxRequest) throws BoxRestException {
                return response;
            }

        };
        request = new IBoxRequest() {

            @Override
            public IBoxRequestAuth getAuth() {
                return auth;
            }

            @Override
            public ICookie getCookie() {
                return cookie;
            }

            @Override
            public HttpEntity getRequestEntity() {
                try {
                    return new StringEntity("");
                }
                catch (UnsupportedEncodingException e) {
                    Assert.fail();
                    return null;
                }
            }

            @Override
            public HttpParams getHttpParams() {
                return new BasicHttpParams();
            }

            @Override
            public HttpUriRequest prepareRequest() throws BoxRestException {
                return new HttpGet();
            }

            @Override
            public RestMethod getRestMethod() {
                return RestMethod.GET;
            }

            @Override
            public int getExpectedResponseCode() {
                return HttpStatus.SC_OK;
            }

            @Override
            public String getScheme() {
                return "https:";
            }

            @Override
            public String getAuthority() {
                return "api.box.com";
            }

            @Override
            public String getApiUrlPath() {
                return "/2.0/";
            }

            @Override
            public void addQueryParam(String name, String value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void addHeader(String name, String value) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setAuth(IBoxRequestAuth auth) {
                // TODO Auto-generated method stub

            }

            @Override
            public void setCookie(ICookie cookie) {
                // TODO Auto-generated method stub

            }

        };
        response = new IBoxResponse() {

            @Override
            public Object parseResponse(IBoxResponseParser responseParser, IBoxResponseParser errorParser) throws BoxRestException {
                return parsedResponse;
            }

            @Override
            public double getContentLength() {
                // TODO Auto-generated method stub
                return 0;
            }

        };
        responseParser = new IBoxResponseParser() {

            @Override
            public Object parse(IBoxResponse response) throws BoxRestException {
                return parsedResponse;
            }

        };
    }

    @Test
    public void testInterfacesE2E() throws AuthFatalFailureException {
        IBoxResponse theResponse = null;
        try {
            theResponse = client.execute(request);
        }
        catch (BoxRestException e) {
            Assert.fail();
        }

        Assert.assertEquals(response, theResponse);
        Object result = null;
        try {
            result = response.parseResponse(responseParser, null);
        }
        catch (BoxRestException e) {
            Assert.fail();
        }
        Assert.assertEquals(parsedResponse, result);
    }
}
