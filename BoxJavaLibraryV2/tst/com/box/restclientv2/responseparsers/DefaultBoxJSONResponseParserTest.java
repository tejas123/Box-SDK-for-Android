package com.box.restclientv2.responseparsers;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHttpResponse;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.restclientv2.responses.DefaultBoxResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DefaultBoxJSONResponseParserTest {

    private TestObject testObject;
    private final String fieldA = "fa";
    private final String fieldB = "fb";

    private DefaultBoxResponse boxResponse;
    private HttpResponse response;
    private HttpEntity entity;
    private InputStream inputStream;

    @Before
    public void setUp() {
        testObject = new TestObject(fieldA, fieldB);
        boxResponse = EasyMock.createMock(DefaultBoxResponse.class);
        response = EasyMock.createMock(BasicHttpResponse.class);
        entity = EasyMock.createMock(StringEntity.class);
    }

    @Test
    public void testParse() {
        try {
            EasyMock.reset(boxResponse, response, entity);
            inputStream = new ByteArrayInputStream(testObject.toString().getBytes());
            EasyMock.expect(boxResponse.getHttpResponse()).andReturn(response);
            EasyMock.expect(response.getEntity()).andReturn(entity);
            EasyMock.expect(entity.getContent()).andReturn(inputStream);
            EasyMock.replay(boxResponse, response, entity);
            DefaultBoxJSONResponseParser parser = new DefaultBoxJSONResponseParser(TestObject.class, new BoxJSONParser(new BoxResourceHub()));
            Object object = parser.parse(boxResponse);
            Assert.assertEquals(TestObject.class, object.getClass());
            TestObject tObject = (TestObject) object;
            Assert.assertEquals(fieldA, tObject.getFieldAWithAnnotation());
            Assert.assertEquals(fieldB, tObject.getFieldB());
        }
        catch (Exception e) {
            Assert.fail();
        }
    }

    private static class TestObject {

        private String fieldA;
        private String fieldB;

        TestObject() {
        }

        TestObject(String fieldA, String fieldB) {
            this.fieldA = fieldA;
            this.fieldB = fieldB;
        }

        @JsonProperty("fieldA")
        String getFieldAWithAnnotation() {
            return fieldA;
        }

        @JsonProperty("fieldA")
        void setFieldAWithAnnotation(String fieldA) {
            this.fieldA = fieldA;
        }

        String getFieldB() {
            return fieldB;
        }

        void setFieldB(String fieldB) {
            this.fieldB = fieldB;
        }

        @Override
        public String toString() {
            return "{\"fieldA\":\"" + fieldA + "\",\"fieldB\":\"" + fieldB + "\"}";
        }
    }
}
