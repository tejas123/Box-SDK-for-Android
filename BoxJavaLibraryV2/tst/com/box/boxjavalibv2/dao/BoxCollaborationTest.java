package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxCollaborationTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {

        String userJson = FileUtils.readFileToString(new File("testdata/user.json"));

        String emailJson = FileUtils.readFileToString(new File("testdata/email.json"));
        String folderJson = FileUtils.readFileToString(new File("testdata/folder.json"));
        folderJson = folderJson.replace("$folder_upload_email", emailJson);

        String collabJson = FileUtils.readFileToString(new File("testdata/collaboration.json"));
        collabJson = collabJson.replace("$created_by", userJson).replace("$item", folderJson);

        BoxCollaboration originalCollab = (BoxCollaboration) TestUtils.getFromJSON(collabJson, BoxCollaboration.class);
        TestParcel parcel = new TestParcel();
        originalCollab.writeToParcel(parcel, 0);
        BoxCollaboration collab = new BoxCollaboration(parcel);

        Assert.assertEquals("collaboration", collab.getType());
        Assert.assertEquals("testexpiresat", collab.getExpiresAt());
        Assert.assertEquals("teststatus", collab.getStatus());
        Assert.assertEquals("testrole", collab.getRole());
        Assert.assertEquals("testacknowledgedat", collab.getAcknowledgedAt());
    }
}
