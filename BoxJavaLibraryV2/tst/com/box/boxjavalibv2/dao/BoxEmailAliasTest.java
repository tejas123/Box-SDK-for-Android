package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxEmailAliasTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {
        String emailJson = FileUtils.readFileToString(new File("testdata/emailalias.json"));
        BoxEmailAlias originalEmail = (BoxEmailAlias) TestUtils.getFromJSON(emailJson, BoxEmailAlias.class);

        TestParcel parcel = new TestParcel();
        originalEmail.writeToParcel(parcel, 0);
        BoxEmailAlias email = new BoxEmailAlias(parcel);

        Assert.assertEquals(true, email.isConfirmed().booleanValue());
        Assert.assertEquals("testemail@testemail.com", email.getEmail());
    }
}
