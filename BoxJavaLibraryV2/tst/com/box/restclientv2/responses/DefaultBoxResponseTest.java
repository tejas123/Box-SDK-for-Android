package com.box.restclientv2.responses;

import junit.framework.Assert;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.message.BasicHttpResponse;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responseparsers.DefaultBoxJSONResponseParser;
import com.box.restclientv2.responseparsers.IBoxResponseParser;

public class DefaultBoxResponseTest {

    private DefaultBoxResponse response;
    private HttpResponse rawResponse;
    private StatusLine statusLine;
    int statusCode = HttpStatus.SC_OK;

    private IBoxResponseParser parser;
    private IBoxResponseParser errorParser;
    private final Object parsedObject = new String("pass");
    private final Object parsedErrorObject = new String("error");

    @Before
    public void setup() {
        parser = EasyMock.createMock(DefaultBoxJSONResponseParser.class);
        errorParser = EasyMock.createMock(DefaultBoxJSONResponseParser.class);
        rawResponse = EasyMock.createMock(BasicHttpResponse.class);
        statusLine = EasyMock.createMock(StatusLine.class);
    }

    @Test
    public void parseExpectedResponseTest() {
        parseResponseTest(true);
    }

    @Test
    public void parseUnexpectedResponseTest() {
        parseResponseTest(false);
    }

    private void parseResponseTest(boolean isExpectedResponse) {
        EasyMock.reset(parser, errorParser, rawResponse, statusLine);
        try {
            response = new DefaultBoxResponse(rawResponse);
            EasyMock.expect(parser.parse(response)).andReturn(parsedObject);
            EasyMock.expect(errorParser.parse(response)).andReturn(parsedErrorObject);
            EasyMock.expect(rawResponse.getStatusLine()).andReturn(statusLine);
            EasyMock.expect(statusLine.getStatusCode()).andReturn(statusCode);
            EasyMock.replay(parser, errorParser, rawResponse, statusLine);
            response.setExpectedResponseCode(isExpectedResponse ? statusCode : HttpStatus.SC_INTERNAL_SERVER_ERROR);
            Object x = response.parseResponse(parser, errorParser);
            Assert.assertEquals(isExpectedResponse ? parsedObject : parsedErrorObject, x);
        }
        catch (BoxRestException e) {
            Assert.fail(e.getMessage());
        }
    }
}
