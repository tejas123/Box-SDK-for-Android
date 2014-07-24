package com.box.boxjavalibv2.responseparsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxServerError;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responses.DefaultBoxResponse;

public class ErrorResponseParserTest {

    private BoxServerError error;
    private DefaultBoxResponse boxResponse;
    private HttpResponse response;
    private HttpEntity entity;
    private InputStream inputStream;
    private StatusLine statusLine;
    private final int statusCode = HttpStatus.SC_FORBIDDEN;

    @Before
    public void setUp() {
        error = new BoxServerError();
        error.setStatus(HttpStatus.SC_FORBIDDEN);
        boxResponse = EasyMock.createMock(DefaultBoxResponse.class);
        response = EasyMock.createMock(BasicHttpResponse.class);
        entity = EasyMock.createMock(StringEntity.class);
        statusLine = EasyMock.createMock(StatusLine.class);
    }

    @Test
    public void testCanParseBoxServerError() throws BoxRestException, IllegalStateException, IOException, BoxJSONException {
        BoxJSONParser jsonParser = new BoxJSONParser(new BoxResourceHub());
        EasyMock.reset(boxResponse, response, entity);
        inputStream = new ByteArrayInputStream(jsonParser.convertBoxObjectToJSONString(error).getBytes());
        EasyMock.expect(boxResponse.getHttpResponse()).andStubReturn(response);
        EasyMock.expect(response.getEntity()).andStubReturn(entity);
        EasyMock.expect(entity.getContent()).andStubReturn(inputStream);
        EasyMock.expect(entity.isStreaming()).andStubReturn(false);

        EasyMock.expect(boxResponse.getHttpResponse()).andStubReturn(response);
        EasyMock.expect(response.getStatusLine()).andStubReturn(statusLine);
        EasyMock.expect(statusLine.getStatusCode()).andStubReturn(statusCode);

        EasyMock.replay(boxResponse, response, entity, statusLine);
        ErrorResponseParser parser = new ErrorResponseParser(jsonParser);
        Object object = parser.parse(boxResponse);
        Assert.assertEquals(BoxServerError.class, object.getClass());

        Assert.assertEquals(jsonParser.convertBoxObjectToJSONString(error), jsonParser.convertBoxObjectToJSONString(object));
        EasyMock.verify(boxResponse, response, entity, statusLine);
    }
}
