package com.box.boxjavalibv2.requests.requestobjects;

import org.apache.commons.lang.StringUtils;

import com.box.boxjavalibv2.dao.BoxCollaboration;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxCollabRequestObject extends BoxDefaultRequestObject {

    private BoxCollabRequestObject() {
    }

    /**
     * create a collaboration.
     * 
     * @param folderId
     *            id of the folder
     * @param userId
     *            id of the user to collaborate, this is optional, if you don't want to supply a user id, use null.
     * @param login
     *            login email of the collaborator(Can be non-box email.)
     * @param role
     *            role/access level of this collaboration(This is a String defined in {@link com.box.boxjavalibv2.dao.CollaborationRole}
     * @return BoxCollabRequestEntity
     */
    public static BoxCollabRequestObject createCollabObject(final String folderId, final String userId, final String login, final String role) {
        BoxCollabRequestObject entity = new BoxCollabRequestObject();
        MapJSONStringEntity item = getItemEntity(folderId);
        entity.setAccessibleBy(userId, login);
        entity.setItem(item);
        entity.setRole(role);
        return entity;
    }

    /**
     * @param userId
     *            id of the user to collaborate, this is optional, if you don't want to supply a user id, use null.
     * @param login
     *            login email of the collaborator(Can be non-box email.)
     */
    public BoxCollabRequestObject setAccessibleBy(final String userId, final String login) {
        MapJSONStringEntity accessibleBy = getAccessibilityEntity(userId, login);
        put(BoxCollaboration.FIELD_ACCESSIBLE_BY, accessibleBy);
        return this;
    }

    /**
     * update a collaboration.
     * 
     * @param role
     *            role/access level of this collaboration(This is a String defined in {@link com.box.boxjavalibv2.dao.CollaborationRole}.
     * @return
     */
    public static BoxCollabRequestObject updateCollabObjects(final String role) {
        BoxCollabRequestObject obj = new BoxCollabRequestObject();
        return obj.setRole(role);
    }

    /** Set the item. */
    private BoxCollabRequestObject setItem(MapJSONStringEntity item) {
        put(BoxCollaboration.FIELD_FOLDER, item);
        return this;
    }

    /** Set the role. */
    public BoxCollabRequestObject setRole(String role) {
        put(BoxCollaboration.FIELD_ROLE, role);
        return this;
    }

    public BoxCollabRequestObject setStatus(String status) {
        getRequestExtras().addQueryParam(BoxCollaboration.FIELD_STATUS, status);
        return this;
    }

    private static MapJSONStringEntity getItemEntity(String folderId) {
        MapJSONStringEntity entity = new MapJSONStringEntity();
        entity.put("id", folderId);
        entity.put("type", BoxResourceType.FOLDER.toString());
        return entity;
    }

    private static MapJSONStringEntity getAccessibilityEntity(final String userId, final String login) {
        MapJSONStringEntity entity = new MapJSONStringEntity();
        if (StringUtils.isNotEmpty(userId)) {
            entity.put("id", userId);
        }
        entity.put("login", login);
        return entity;
    }
}
