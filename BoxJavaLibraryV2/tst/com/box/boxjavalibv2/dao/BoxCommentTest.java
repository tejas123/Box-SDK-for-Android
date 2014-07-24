package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxCommentTest {

    @Test
    public void testParcelRoundTrip() throws IOException, BoxRestException {
    	
        String userJson = FileUtils.readFileToString(new File("testdata/user.json"));
        String fileJson = FileUtils.readFileToString(new File("testdata/file.json"));
        String commentJson = FileUtils.readFileToString(new File("testdata/comment.json"));
        commentJson = commentJson.replace("$created_by", userJson).replace("$item", fileJson);
        BoxComment originalComment = (BoxComment) TestUtils.getFromJSON(commentJson, BoxComment.class);

        TestParcel parcel = new TestParcel();
        originalComment.writeToParcel(parcel, 0);
        BoxComment comment = new BoxComment(parcel);

        Assert.assertEquals("testmessage", comment.getMessage());
        Assert.assertEquals(true, comment.isReplyComment().booleanValue());
    }
}
