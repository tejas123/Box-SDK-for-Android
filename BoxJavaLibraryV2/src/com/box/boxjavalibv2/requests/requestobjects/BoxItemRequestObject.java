package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.jsonentities.BoxSharedLinkRequestEntity;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;

public class BoxItemRequestObject extends BoxSharedLinkRequestObject {

    public BoxItemRequestObject() {
        super();
    }

    public BoxItemRequestObject(BoxSharedLinkRequestEntity sharedLink) {
        super(sharedLink);
    }

    public static BoxItemRequestObject getRequestObject() {
        return new BoxItemRequestObject();
    }

    public static BoxItemRequestObject deleteSharedLinkRequestObject() {
        return new BoxItemRequestObject(null);
    }

    public static BoxItemRequestObject createSharedLinkRequestObject(BoxSharedLinkRequestEntity sharedLink) {
        return new BoxItemRequestObject(sharedLink);
    }

    /**
     * Set parent folder of the file.
     * 
     * @param parentId
     *            id of parent
     * @return
     */
    public BoxItemRequestObject setParent(String parentId) {
        MapJSONStringEntity entity = new MapJSONStringEntity();
        entity.put(BoxFolder.FIELD_ID, parentId);
        put(BoxItem.FIELD_PARENT, entity);
        return this;
    }

    /**
     * Set name of the file.
     * 
     * @param name
     *            name
     * @return
     */
    public BoxItemRequestObject setName(String name) {
        put(BoxFile.FIELD_NAME, name);
        return this;
    }

    /**
     * Set description of the file
     * 
     * @param description
     *            description
     * @return
     */
    public BoxItemRequestObject setDescription(String description) {
        put(BoxFile.FIELD_DESCRIPTION, description);
        return this;
    }

    public BoxItemRequestObject setTags(String[] tags) {
        put(BoxFile.FIELD_TAGS, tags);
        return this;
    }
}
