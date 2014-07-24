package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxFileVersionTest {

    @Test
    public void testParcelRoundTrip() throws IOException, BoxRestException {
        String userJson = FileUtils.readFileToString(new File("testdata/user.json"));
        String fileJson = FileUtils.readFileToString(new File("testdata/fileversion.json"));
        fileJson = fileJson.replace("$modified_by", userJson);

        BoxFileVersion original = (BoxFileVersion) TestUtils.getFromJSON(fileJson, BoxFileVersion.class);

        TestParcel parcel = new TestParcel();
        original.writeToParcel(parcel, 0);
        BoxFileVersion version = new BoxFileVersion(parcel);

        Assert.assertEquals("testname", version.getName());
    }
}
