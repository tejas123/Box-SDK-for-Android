package com.box.boxjavalibv2.jsonentities;

import java.util.Date;

import com.box.boxjavalibv2.dao.BoxSharedLink;
import com.box.boxjavalibv2.utils.ISO8601DateParser;

// CHECKSTYLE:OFF
/**
 * Entity for shared link.
 */
public class BoxSharedLinkRequestEntity extends MapJSONStringEntity {

    private static final long serialVersionUID = 1L;

    /**
     * @param accessLevel
     *            access(String can be the strings defined in {@link com.box.boxjavalibv2.dao.BoxSharedLinkAccess}.)
     */
    public BoxSharedLinkRequestEntity(final String accessLevel) {
        setAccess(accessLevel);
    }

    /**
     * Set access String. This defines who has access to the link. String can be the strings defined in {@link com.box.boxjavalibv2.dao.BoxSharedLinkAccess}.
     * 
     * @param accessLevel
     *            access
     * @return
     */
    public BoxSharedLinkRequestEntity setAccess(final String accessLevel) {
        put(BoxSharedLink.FIELD_ACCESS, accessLevel);
        return this;
    }

    /**
     * Set the time to unshare the link. This String is an ISO8601 time String and can be generated from {@link java.util.Date} by
     * {@link com.box.boxjavalibv2.utils.ISO8601DateParser}
     * 
     * @param unsharedAt
     *            time to unshare the link
     * @return
     */
    public void setUnshared_at(final Date unsharedAt) {
        String date = unsharedAt != null ? ISO8601DateParser.toString(unsharedAt) : "null";
        put(BoxSharedLink.FIELD_UNSHARED_AT, date);
    }

    /**
     * Set permissions.
     * 
     * @param permissionsEntity
     *            permissions
     * @return
     */
    private void setPermissions(final BoxSharedLinkPermissionsRequestEntity permissionsEntity) {
        put(BoxSharedLink.FIELD_PERMISSIONS, permissionsEntity);
    }

    /**
     * Set permissions
     * 
     * @param canDownload
     *            Whether shared item can be downloaded.
     * @return
     */
    public void setPermissions(final boolean canDownload) {
        BoxSharedLinkPermissionsRequestEntity perm = new BoxSharedLinkPermissionsRequestEntity();
        perm.setCanDownload(canDownload);
        setPermissions(perm);
    }
}
