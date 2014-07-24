package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.jsonentities.BoxSharedLinkRequestEntity;

public class BoxFileRequestObject extends BoxItemRequestObject {

    public BoxFileRequestObject() {
        super();
    }

    public BoxFileRequestObject(BoxSharedLinkRequestEntity sharedLink) {
        super(sharedLink);
    }

    public static BoxFileRequestObject getRequestObject() {
        return new BoxFileRequestObject();
    }

    public static BoxFileRequestObject deleteSharedLinkRequestObject() {
        return new BoxFileRequestObject(null);
    }

    public static BoxFileRequestObject createSharedLinkRequestObject(BoxSharedLinkRequestEntity sharedLink) {
        return new BoxFileRequestObject(sharedLink);
    }
}
