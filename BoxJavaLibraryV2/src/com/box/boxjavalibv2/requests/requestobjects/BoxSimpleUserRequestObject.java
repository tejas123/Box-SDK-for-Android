package com.box.boxjavalibv2.requests.requestobjects;

import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxSimpleUserRequestObject extends BoxDefaultRequestObject {

    private final static String NOTIFY = "notify";

    protected BoxSimpleUserRequestObject() {
    }

    /**
     * move a folder to another user.
     * 
     * @param destinationUserId
     *            the ID of the user who the folder will be transferred to
     * @param notify
     *            whether destination user should receive email notification of the transfer
     * @return
     */
    public static BoxSimpleUserRequestObject moveFolderToAnotherUserRequestEntity(final String destinationUserId, final boolean notify) {
        BoxSimpleUserRequestObject obj = new BoxSimpleUserRequestObject();
        return obj.setNotifyUser(notify).setDestinationUser(destinationUserId);
    }

    /**
     * Set destination user, this is only used in request to move a folder to another user's account.
     * 
     * @param destinationUserId
     *            the ID of the user who the folder will be transferred to
     * @return
     */
    private BoxSimpleUserRequestObject setDestinationUser(final String destinationUserId) {
        MapJSONStringEntity id = new MapJSONStringEntity();
        id.put(BoxUser.FIELD_ID, destinationUserId);
        put(BoxItem.FIELD_OWNED_BY, id);
        return this;
    }

    /**
     * Set whether the user should receive an email notification. This applies to the case when they are rolled out of an enterprise or when somebody moves a
     * folder into the user's account.
     * 
     * @param notify
     * @return
     */
    public BoxSimpleUserRequestObject setNotifyUser(final boolean notify) {
        getRequestExtras().addQueryParam(NOTIFY, Boolean.toString(notify));
        return this;
    }
}
