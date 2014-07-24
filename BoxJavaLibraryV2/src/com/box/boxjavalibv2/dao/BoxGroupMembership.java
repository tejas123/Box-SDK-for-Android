package com.box.boxjavalibv2.dao;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BoxGroupMembership extends BoxTypedObject {

    public final static String FIELD_USER = "user";
    public final static String FIELD_GROUP = "group";
    public final static String FIELD_ROLE = "role";

    public final static String ROLE_ADMIN = "admin";
    public final static String ROLE_MEMBER = "member";

    public BoxGroupMembership() {
        setType(BoxResourceType.GROUP_MEMBERSHIP.toString());
    }

    public BoxGroupMembership(BoxGroupMembership obj) {
        super(obj);
    }

    public BoxGroupMembership(Map<String, Object> map) {
        super(map);
    }

    public BoxGroupMembership(IBoxParcelWrapper in) {
        super(in);
    }

    @JsonProperty(FIELD_ROLE)
    public String getRole() {
        return (String) getValue(FIELD_ROLE);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus.org">Jackson JSON processer</a>}.
     */
    @JsonProperty(FIELD_ROLE)
    private void setRole(String role) {
        put(FIELD_ROLE, role);
    }

    @JsonProperty(FIELD_USER)
    public BoxUser getUser() {
        return (BoxUser) getValue(FIELD_USER);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus.org">Jackson JSON processer</a>}.
     * 
     * @param user
     *            the user to set
     */
    @JsonProperty(FIELD_USER)
    private void setUser(BoxUser user) {
        put(FIELD_USER, user);
    }

    @JsonProperty(FIELD_GROUP)
    public BoxGroup getGroup() {
        return (BoxGroup) getValue(FIELD_GROUP);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus.org">Jackson JSON processer</a>}.
     * 
     * @param group
     *            the group to set
     */
    @JsonProperty(FIELD_GROUP)
    private void setGroup(BoxGroup group) {
        put(FIELD_GROUP, group);
    }

}
