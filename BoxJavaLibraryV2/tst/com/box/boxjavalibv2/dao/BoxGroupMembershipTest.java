package com.box.boxjavalibv2.dao;

import java.io.File;
import java.io.IOException;

import junit.framework.Assert;

import org.apache.commons.io.FileUtils;
import org.junit.Test;

import com.box.boxjavalibv2.testutils.TestUtils;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxGroupMembershipTest {

    @Test
    public void testParcelRoundTrip() throws BoxRestException, IOException {
        String membershipJson = FileUtils.readFileToString(new File("testdata/groupmembership.json"));
        String userJson = FileUtils.readFileToString(new File("testdata/user.json"));
        String groupJson = FileUtils.readFileToString(new File("testdata/group.json"));
        membershipJson = String.format(membershipJson, userJson, groupJson);

        BoxGroupMembership membership = (BoxGroupMembership) TestUtils.getFromJSON(membershipJson, BoxGroupMembership.class);

        TestParcel parcel = new TestParcel();
        membership.writeToParcel(parcel, 0);
        BoxGroupMembership fromParcel = new BoxGroupMembership(parcel);

        Assert.assertEquals(BoxResourceType.GROUP_MEMBERSHIP.toString(), fromParcel.getType());
        Assert.assertEquals("testid", fromParcel.getId());
        Assert.assertEquals("testrole", fromParcel.getRole());
        Assert.assertNotNull(fromParcel.getUser());
        Assert.assertNotNull(fromParcel.getGroup());
    }

}
