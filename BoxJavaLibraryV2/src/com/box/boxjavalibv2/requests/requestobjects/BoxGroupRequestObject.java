package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.dao.BoxGroup;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxGroupRequestObject extends BoxDefaultRequestObject {

    private BoxGroupRequestObject() {
        super();
    }

    public static BoxGroupRequestObject createGroupRequestObject(final String name) {
        return updateGroupRequestObject(name);
    }

    public static BoxGroupRequestObject updateGroupRequestObject(final String name) {
        BoxGroupRequestObject obj = new BoxGroupRequestObject();
        obj.put(BoxGroup.FIELD_NAME, name);
        return obj;
    }

}
