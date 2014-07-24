package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxItemCopyRequestObject extends BoxDefaultRequestObject {

    private BoxItemCopyRequestObject() {
    }

    public static BoxItemCopyRequestObject copyItemRequestObject(String parentId) {
        BoxItemCopyRequestObject entity = new BoxItemCopyRequestObject();
        entity.setParent(parentId);
        return entity;
    }

    /**
     * Set parent folder of the file.
     * 
     * @param parentId
     *            id of parent
     * @return
     */
    private BoxItemCopyRequestObject setParent(String parentId) {
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
    public BoxItemCopyRequestObject setName(String name) {
        put(BoxFile.FIELD_NAME, name);
        return this;
    }
}
