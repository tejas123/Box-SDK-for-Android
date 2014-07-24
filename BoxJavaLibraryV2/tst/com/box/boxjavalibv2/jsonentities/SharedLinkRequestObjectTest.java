package com.box.boxjavalibv2.jsonentities;

import java.util.Date;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxSharedLinkAccess;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.utils.ISO8601DateParser;

public class SharedLinkRequestObjectTest {

    private final static String PERMISSIONS_STR = "\"permissions\":%s";
    private final static String ACCESS_STR = "\"access\":\"%s\"";
    private final static String UNSHARED_STR = "\"unshared_at\":\"%s\"";

    @Test
    public void testFull() throws BoxJSONException {
        Date date = new Date();
        String access = BoxSharedLinkAccess.OPEN;
        BoxSharedLinkRequestEntity entity = new BoxSharedLinkRequestEntity(access);
        entity.setPermissions(true);
        entity.setUnshared_at(date);

        String accessStr = String.format(ACCESS_STR, access);
        String dateStr = String.format(UNSHARED_STR, ISO8601DateParser.toString(date));

        String entityStr = entity.toJSONString(new BoxJSONParser(new BoxResourceHub()));
        Assert.assertFalse(entityStr.contains(PERMISSIONS_STR));
        Assert.assertTrue(entityStr.contains(accessStr));
        Assert.assertTrue(entityStr.contains(dateStr));
    }

    @Test
    public void testNoUnsharedAt() throws BoxJSONException {
        String access = BoxSharedLinkAccess.OPEN;
        BoxSharedLinkRequestEntity entity = new BoxSharedLinkRequestEntity(access);
        entity.setPermissions(true);
        String accessStr = String.format(ACCESS_STR, access);

        String entityStr = entity.toJSONString(new BoxJSONParser(new BoxResourceHub()));
        Assert.assertFalse(entityStr.contains(PERMISSIONS_STR));
        Assert.assertTrue(entityStr.contains(accessStr));
        Assert.assertFalse(entityStr.contains("\"unshared_at\":"));

    }
}
