package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxSharedLinkTest {

    @Test
    public void testParcelRoundTrip() throws IOException, BoxRestException {
        String sharedJson = FileUtils.readFileToString(new File("testdata/sharedlink.json"));
        BoxSharedLink original = (BoxSharedLink) TestUtils.getFromJSON(sharedJson, BoxSharedLink.class);

        TestParcel parcel = new TestParcel();
        original.writeToParcel(parcel, 0);
        BoxSharedLink fromParcel = new BoxSharedLink(parcel);

        Assert.assertEquals("testurl", fromParcel.getUrl());
        Assert.assertEquals("downloadurl", fromParcel.getDownloadUrl());
        Assert.assertEquals(true, (boolean)fromParcel.isPasswordEnabled());
        Assert.assertEquals("testunsharedat", fromParcel.getUnsharedAt());
        Assert.assertEquals(99, (int)fromParcel.getDownloadCount());
        Assert.assertEquals("testaccess", fromParcel.getAccess());
    }
}
