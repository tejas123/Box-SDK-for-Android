package com.box.boxjavalibv2.dao;

import java.util.Map;


// CHECKSTYLE:OFF
/**
 * Permission for shared links.
 * 
 */
public class BoxSharedLinkPermissions extends BoxObject {

    public static final String FIELD_CAN_DOWNLOAD = "can_download";

    /**
     * Default constructor.
     */
    public BoxSharedLinkPermissions() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     * 
     * @param obj
     */
    public BoxSharedLinkPermissions(BoxSharedLinkPermissions obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     * 
     * @param map
     */
    public BoxSharedLinkPermissions(Map<String, Object> map) {
        super(map);
    }

    /**
     * Constructor.
     * 
     * @param canDownload
     *            can be downloaded
     */
    public BoxSharedLinkPermissions(final boolean canDownload) {
        setCan_download(canDownload);
    }

    /**
     * whether can_download is true.
     * 
     * @return can_download
     */
    public Boolean isCan_download() {
        return (Boolean) getValue(FIELD_CAN_DOWNLOAD);
    }

    /**
     * Setter.
     * 
     * @param canDownload
     */
    protected void setCan_download(final Boolean canDownload) {
        put(FIELD_CAN_DOWNLOAD, canDownload);
    }

    protected BoxSharedLinkPermissions(IBoxParcelWrapper parcel) {
        super(parcel);
    }
}
