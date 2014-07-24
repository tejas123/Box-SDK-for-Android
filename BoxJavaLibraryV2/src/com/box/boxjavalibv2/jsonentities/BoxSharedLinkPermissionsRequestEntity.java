package com.box.boxjavalibv2.jsonentities;



public class BoxSharedLinkPermissionsRequestEntity extends MapJSONStringEntity {

    private static final long serialVersionUID = 1L;
    public static final String FIELD_CAN_DOWNLOAD = "can_download";

    /**
     * whether can_download is true.
     * 
     * @return can_download
     */
    public Boolean canDownload() {
        return (Boolean) get(FIELD_CAN_DOWNLOAD);
    }

    /**
     * Setter.
     * 
     * @param canDownload
     */
    protected void setCanDownload(final Boolean canDownload) {
        put(FIELD_CAN_DOWNLOAD, canDownload);
    }

}
