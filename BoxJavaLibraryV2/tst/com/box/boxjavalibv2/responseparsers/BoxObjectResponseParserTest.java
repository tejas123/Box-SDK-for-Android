package com.box.boxjavalibv2.responseparsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responseparsers.DefaultBoxJSONResponseParser;
import com.box.restclientv2.responses.DefaultBoxResponse;

public class BoxObjectResponseParserTest {

    private BoxFile file;
    private DefaultBoxResponse boxResponse;
    private HttpResponse response;
    private HttpEntity entity;
    private InputStream inputStream;

    @Before
    public void setUp() {
        file = new BoxFile();
        boxResponse = EasyMock.createMock(DefaultBoxResponse.class);
        response = EasyMock.createMock(BasicHttpResponse.class);
        entity = EasyMock.createMock(StringEntity.class);
    }

    @Test
    public void testCanParseBoxObject() throws IllegalStateException, IOException, BoxRestException, BoxJSONException {
        BoxResourceHub hub = new BoxResourceHub();
        EasyMock.reset(boxResponse, response, entity);
        inputStream = new ByteArrayInputStream((new BoxJSONParser(hub)).convertBoxObjectToJSONString(file).getBytes());
        EasyMock.expect(boxResponse.getHttpResponse()).andReturn(response);
        EasyMock.expect(response.getEntity()).andReturn(entity);
        EasyMock.expect(entity.getContent()).andReturn(inputStream);
        EasyMock.replay(boxResponse, response, entity);
        DefaultBoxJSONResponseParser parser = new DefaultBoxJSONResponseParser(BoxFile.class, new BoxJSONParser(hub));
        Object object = parser.parse(boxResponse);
        Assert.assertEquals(BoxFile.class, object.getClass());
        Assert.assertEquals((new BoxJSONParser(hub)).convertBoxObjectToJSONString(file), (new BoxJSONParser(hub)).convertBoxObjectToJSONString(object));
        EasyMock.verify(boxResponse, response, entity);
    }
}
