package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxGroupTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {
        String groupJson = FileUtils.readFileToString(new File("testdata/group.json"));
        BoxGroup group = (BoxGroup) TestUtils.getFromJSON(groupJson, BoxGroup.class);

        TestParcel parcel = new TestParcel();
        group.writeToParcel(parcel, 0);
        BoxGroup fromParcel = new BoxGroup(parcel);

        Assert.assertEquals(BoxResourceType.GROUP.toString(), fromParcel.getType());
        Assert.assertEquals("testid", fromParcel.getId());
        Assert.assertEquals("testname", fromParcel.getName());
    }

}
