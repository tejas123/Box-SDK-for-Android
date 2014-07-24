package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxFolderTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException, BoxJSONException {
        String emailJson = FileUtils.readFileToString(new File("testdata/email.json"));

        String folderJson = FileUtils.readFileToString(new File("testdata/folder.json"));
        folderJson = folderJson.replace("$folder_upload_email", emailJson);
        BoxFolder folder = (BoxFolder) TestUtils.getFromJSON(folderJson, BoxFolder.class);

        TestParcel parcel = new TestParcel();
        folder.writeToParcel(parcel, 0);
        BoxFolder fromParcel = new BoxFolder(parcel);
        String uploadEmailString = (new BoxJSONParser(new BoxResourceHub()).convertBoxObjectToJSONString(fromParcel.getFolderUploadEmail()));
        String[] parts = uploadEmailString.split(",");
        Assert.assertEquals(2, parts.length);
        Assert.assertTrue(emailJson.contains(parts[0].replace("{", "").replace("}", "")));
        Assert.assertTrue(emailJson.contains(parts[1].replace("{", "").replace("}", "")));
        Assert.assertEquals(false, (boolean) fromParcel.hasCollaborations());
    }
}
