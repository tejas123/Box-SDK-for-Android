package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.utils.Constants;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxFolderDeleteRequestObject extends BoxDefaultRequestObject {

    private BoxFolderDeleteRequestObject() {
        super();
    }

    public static BoxFolderDeleteRequestObject deleteFolderRequestObject(boolean recursive) {
        return (new BoxFolderDeleteRequestObject()).setRecursive(recursive);
    }

    /**
     * Set whether operation is done recursively. (For example deleting a folder)
     * 
     * @param recursive
     * @return
     */
    private BoxFolderDeleteRequestObject setRecursive(final boolean recursive) {
        getRequestExtras().addQueryParam(Constants.RECURSIVE, Boolean.toString(recursive));
        return this;
    }
}
