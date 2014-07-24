package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxUserTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {
        String userJson = FileUtils.readFileToString(new File("testdata/user.json"));
        BoxUser original = (BoxUser) TestUtils.getFromJSON(userJson, BoxUser.class);

        TestParcel parcel = new TestParcel();
        original.writeToParcel(parcel, 0);
        BoxUser fromParcel = new BoxUser(parcel);

        Assert.assertEquals("user", fromParcel.getType());
        Assert.assertEquals("testuserid", fromParcel.getId());
        Assert.assertEquals("testname", fromParcel.getName());
        Assert.assertEquals("testrole", fromParcel.getRole());
        Assert.assertEquals("testlanguage", fromParcel.getLanguage());
        Assert.assertEquals(999, fromParcel.getSpaceAmount().intValue());
        Assert.assertEquals(555, fromParcel.getSpaceUsed().intValue());
        Assert.assertEquals(1000, fromParcel.getMaxUploadSize().intValue());
        Assert.assertEquals(true, fromParcel.isSyncEnabled().booleanValue());
        Assert.assertEquals(false, fromParcel.canSeeManagedUsers().booleanValue());
        Assert.assertEquals("teststatus", fromParcel.getStatus());
        Assert.assertEquals("testjobtitle", fromParcel.getJobTitle());
        Assert.assertEquals("123", fromParcel.getPhone());
        Assert.assertEquals("testaddress", fromParcel.getAddress());
        Assert.assertEquals("http://testboxavatarurl.com", fromParcel.getAvatarUrl());
        Assert.assertEquals(true, fromParcel.isExemptFromDeviceLimits().booleanValue());
        Assert.assertEquals(false, fromParcel.isExemptFromLoginVerification().booleanValue());

        String[] tags = fromParcel.getMyTags();
        Assert.assertEquals(2, tags.length);
        Assert.assertEquals("mytaga", tags[0]);
        Assert.assertEquals("mytagb", tags[1]);
    }
}
