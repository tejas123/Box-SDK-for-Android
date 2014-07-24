package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxItemTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {
        String fileJson = FileUtils.readFileToString(new File("testdata/file.json"));
        BoxItem originalItem = (BoxItem) TestUtils.getFromJSON(fileJson, BoxItem.class);

        TestParcel parcel = new TestParcel();
        originalItem.writeToParcel(parcel, 0);
        BoxItem item = new BoxFile(parcel);

        Assert.assertTrue(item instanceof BoxFile);
        Assert.assertEquals("file", item.getType());
        Assert.assertEquals("testfileid", item.getId());
        Assert.assertEquals("testcreatedat", item.getCreatedAt());
        Assert.assertEquals("testmodifiedat", item.getModifiedAt());
        Assert.assertEquals("testetag", item.getEtag());
        Assert.assertEquals("testsequenceid", item.getSequenceId());
        Assert.assertEquals("testname", item.getName());
        Assert.assertEquals("testdescription", item.getDescription());
        Assert.assertEquals(1.0, item.getSize());
        Assert.assertEquals("testitemstatus", item.getItemStatus());
    }
}
