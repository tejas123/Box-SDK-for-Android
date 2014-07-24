package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxFileTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {
        String fileJson = FileUtils.readFileToString(new File("testdata/file.json"));
        BoxFile filev2 = (BoxFile) TestUtils.getFromJSON(fileJson, BoxFile.class);

        TestParcel parcel = new TestParcel();
        filev2.writeToParcel(parcel, 0);
        BoxFile fromParcel = new BoxFile(parcel);

        Assert.assertEquals("file", fromParcel.getType());
        Assert.assertEquals("testfileid", fromParcel.getId());
        Assert.assertEquals("testsha1", fromParcel.getSha1());
        Assert.assertEquals("2", fromParcel.getVersionNumber());
        Assert.assertEquals(2, (int) fromParcel.getCommentCount());

        String[] tags = fromParcel.getTags();
        Assert.assertEquals(2, tags.length);
        Assert.assertEquals("taga", tags[0]);
        Assert.assertEquals("tagb", tags[1]);

    }
}
