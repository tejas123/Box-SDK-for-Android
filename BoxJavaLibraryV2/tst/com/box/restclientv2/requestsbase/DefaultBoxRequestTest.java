package com.box.restclientv2.requestsbase;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.junit.Before;
import org.junit.Test;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class DefaultBoxRequestTest {

    private IBoxConfig config;
    private String host;
    private String scheme;
    private String uri;
    private RestMethod restMethod;
    private HttpEntity requestEntity;

    @Before
    public void setup() {
        config = TestUtils.getConfig();
        host = config.getApiUrlAuthority();
        scheme = config.getApiUrlScheme();
        uri = "/uri";
        restMethod = RestMethod.POST;
        try {
            requestEntity = new StringEntity("str");
        }
        catch (UnsupportedEncodingException e) {
            Assert.fail();
        }
    }

    @Test
    public void prepareRequestTest() throws AuthFatalFailureException {
        try {
            DefaultBoxRequest request = new DefaultBoxRequest(config, new BoxJSONParser(new BoxResourceHub()), uri, restMethod, null);
            request.addQueryParam("a", "b");
            request.setEntity(requestEntity);
            request.prepareRequest();
            HttpRequestBase rawRequest = request.getRawRequest();
            Assert.assertEquals(HttpPost.class, rawRequest.getClass());
            URI url = rawRequest.getURI();
            Assert.assertEquals(scheme, url.getScheme());
            Assert.assertEquals(host, url.getHost());
            Assert.assertEquals(TestUtils.getConfig().getApiUrlPath().concat(uri), url.getPath());
            Assert.assertTrue(url.getQuery().contains("a=b"));
            Assert.assertEquals(requestEntity, ((HttpPost) rawRequest).getEntity());
        }
        catch (BoxRestException e) {
            Assert.fail();
        }
    }

    @Test
    public void ConstructHttpUriRequestTest() {
        BoxJSONParser parser = new BoxJSONParser(new BoxResourceHub());
        try {
            Assert.assertEquals(HttpGet.class, (new DefaultBoxRequest(config, parser, uri, RestMethod.GET, null)).constructHttpUriRequest().getClass());
            Assert.assertEquals(HttpPut.class, (new DefaultBoxRequest(config, parser, uri, RestMethod.PUT, null)).constructHttpUriRequest().getClass());
            Assert.assertEquals(HttpPost.class, (new DefaultBoxRequest(config, parser, uri, RestMethod.POST, null)).constructHttpUriRequest().getClass());
            Assert.assertEquals(HttpDelete.class, (new DefaultBoxRequest(config, parser, uri, RestMethod.DELETE, null)).constructHttpUriRequest().getClass());
        }
        catch (BoxRestException e) {
            Assert.fail(e.getMessage());
        }
    }
}
