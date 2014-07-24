package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.dao.BoxPreview;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxImageRequestObject extends BoxDefaultRequestObject {

    private BoxImageRequestObject() {
        super();
    }

    /**
     * Get BoxImageRequestObject for a preview request with pages.
     * 
     * @param page
     *            requested preview page number(For example, 2 if you want to get the 2nd page of the preview if the preview has multiple pages.)
     * @param minWidth
     *            minimum width of the preview image requested
     * @param maxWidth
     *            maximum width of the preview image requested
     * @param minHeight
     *            minimum height of the preview image requested
     * @param maxHeight
     *            maximum height of the preview image requested
     * @return BoxPreviewRequestObject
     */
    public static BoxImageRequestObject pagePreviewRequestObject(final int page, final int minWidth, final int maxWidth, final int minHeight,
        final int maxHeight) {
        return (new BoxImageRequestObject()).setPage(page).setMinHeight(minHeight).setMaxHeight(maxHeight).setMinWidth(minWidth).setMaxWidth(maxWidth);
    }

    /**
     * Get BoxImageRequestObject for an image or preview request without pages.
     * 
     * @return BoxPreviewRequestObject
     */
    public static BoxImageRequestObject previewRequestObject() {
        return new BoxImageRequestObject();
    }

    public BoxImageRequestObject setMinWidth(int minWidth) {
        getRequestExtras().addQueryParam(BoxPreview.MIN_WIDTH, Integer.toString(minWidth));
        return this;
    }

    public BoxImageRequestObject setMaxWidth(int maxWidth) {
        getRequestExtras().addQueryParam(BoxPreview.MAX_WIDTH, Integer.toString(maxWidth));
        return this;
    }

    public BoxImageRequestObject setMinHeight(int minHeight) {
        getRequestExtras().addQueryParam(BoxPreview.MIN_HEIGHT, Integer.toString(minHeight));
        return this;
    }

    public BoxImageRequestObject setMaxHeight(int maxHeight) {
        getRequestExtras().addQueryParam(BoxPreview.MAX_HEIGHT, Integer.toString(maxHeight));
        return this;
    }

    public BoxImageRequestObject setPage(int page) {
        getRequestExtras().addQueryParam(BoxPreview.PAGE, Integer.toString(page));
        return this;
    }
}
