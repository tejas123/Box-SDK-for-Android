package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.dao.BoxGroup;
import com.box.boxjavalibv2.dao.BoxGroupMembership;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxGroupMembershipRequestObject extends BoxDefaultRequestObject {

    private BoxGroupMembershipRequestObject() {
    }

    /**
     * 
     * @param groupId
     *            id of the group
     * @param userId
     *            id of the user to be added.
     * @param role
     *            role of the user.
     * @return
     */
    public static BoxGroupMembershipRequestObject addMembershipRequestObject(final String groupId, final String userId, final String role) {
        BoxGroupMembershipRequestObject obj = new BoxGroupMembershipRequestObject();
        return obj.setGroup(groupId).setUser(userId).setRole(role);
    }

    public static BoxGroupMembershipRequestObject updateMembershipRequestObject(final String role) {
        BoxGroupMembershipRequestObject obj = new BoxGroupMembershipRequestObject();
        return obj.setRole(role);
    }

    public BoxGroupMembershipRequestObject setGroup(final String groupId) {
        MapJSONStringEntity groupEntity = new MapJSONStringEntity();
        groupEntity.put(BoxGroup.FIELD_ID, groupId);
        put(BoxGroupMembership.FIELD_GROUP, groupEntity);
        return this;
    }

    /**
     * @param role
     *            role of the user.
     */
    public BoxGroupMembershipRequestObject setRole(final String role) {
        put(BoxGroupMembership.FIELD_ROLE, role);
        return this;
    }

    public BoxGroupMembershipRequestObject setUser(final String userId) {
        MapJSONStringEntity userEntity = new MapJSONStringEntity();
        userEntity.put(BoxUser.FIELD_ID, userId);
        put(BoxGroupMembership.FIELD_USER, userEntity);
        return this;
    }
}
