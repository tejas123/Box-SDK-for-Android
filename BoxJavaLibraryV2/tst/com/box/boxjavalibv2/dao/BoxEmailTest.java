package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxEmailTest {

    @Test
    public void testParcelRoundTrip() throws IOException, BoxRestException {
        String emailJson = FileUtils.readFileToString(new File("testdata/email.json"));
        BoxEmail email = (BoxEmail) TestUtils.getFromJSON(emailJson, BoxEmail.class);

        TestParcel parcel = new TestParcel();
        email.writeToParcel(parcel, 0);
        BoxEmail fromParcel = new BoxEmail(parcel);
        Assert.assertEquals("testaccess", fromParcel.getAccess());
        Assert.assertEquals("testemail@box.com", fromParcel.getEmail());
    }
}
