package com.box.boxjavalibv2.requests.requestobjects;

public class BoxUserDeleteRequestObject extends BoxSimpleUserRequestObject {

    private final static String FORCE = "force";

    /**
     * Request entity to delete an enterprise user.
     * 
     * @param notify
     *            whether to notify user if user is rolled out of enterprise
     * @param force
     *            whether or not the user should be deleted even if this user
     *            still own files
     * @return
     */
    public static BoxUserDeleteRequestObject deleteEnterpriseUserRequestObject(boolean notify,
            boolean force) {
        BoxUserDeleteRequestObject obj = new BoxUserDeleteRequestObject().setForceDelete(force);
        obj.setNotifyUser(notify);
        return obj;
    }

    /**
     * Set whether or not the user should be deleted even if this user still own
     * files.
     * 
     * @param force
     *            whether or not the user should be deleted even if this user
     *            still own files
     * @return
     */
    public BoxUserDeleteRequestObject setForceDelete(final boolean force) {
        getRequestExtras().addQueryParam(FORCE, Boolean.toString(force));
        return this;
    }
}
