package com.box.boxjavalibv2.jsonentities;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;

public class MapJSONStringEntityTest {

    @Test
    public void testJson() throws BoxJSONException {
        String json = "{\"%s\":\"%s\"}";
        String name = "testname";
        String value = "testvalue";
        MapJSONStringEntity entity = new MapJSONStringEntity();

        entity.put(name, value);
        Assert.assertEquals(String.format(json, name, value), entity.toJSONString(new BoxJSONParser(new BoxResourceHub())));
    }
}
