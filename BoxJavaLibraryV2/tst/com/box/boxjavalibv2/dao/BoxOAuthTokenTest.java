package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxOAuthTokenTest {

    @Test
    public void testParcelRoundTrip() throws IOException, BoxRestException {
        String oauthdataJson = FileUtils.readFileToString(new File("testdata/oauthdata.json"));
        BoxOAuthToken original = (BoxOAuthToken) TestUtils.getFromJSON(oauthdataJson, BoxOAuthToken.class);

        TestParcel parcel = new TestParcel();
        original.writeToParcel(parcel, 0);
        BoxOAuthToken oauthdata = new BoxOAuthToken(parcel);

        Assert.assertEquals("testaccesstoken", oauthdata.getAccessToken());
        Assert.assertEquals(10, oauthdata.getExpiresIn().intValue());
        Assert.assertEquals("testtokentype", oauthdata.getTokenType());
        Assert.assertEquals("testrefreshtoken", oauthdata.getRefreshToken());
    }
}
