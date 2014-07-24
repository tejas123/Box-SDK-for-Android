package com.box.boxjavalibv2.requests.requestobjects;

import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxPagingRequestObject extends BoxDefaultRequestObject {

    public BoxPagingRequestObject() {
        super();
    }

    /**
     * BoxPagingRequestObject for get a paged list.
     * 
     * @param limit
     *            the number of items to return. default is 100, max is 1000.
     * @param offset
     *            the item at which to begin the response, default is 0.
     * @return BoxFolderRequestObject
     */
    public static BoxPagingRequestObject pagingRequestObject(final int limit, final int offset) {
        return (BoxPagingRequestObject) (new BoxPagingRequestObject()).setPage(limit, offset);
    }
}
