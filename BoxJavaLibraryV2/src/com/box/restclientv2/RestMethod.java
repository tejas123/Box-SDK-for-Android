package com.box.restclientv2;

/**
 * REST Method.
 */
public enum RestMethod {
    GET, PUT, POST, DELETE, OPTIONS, OTHERS;

    private static final String METHOD_GET = "get";
    private static final String METHOD_PUT = "put";
    private static final String METHOD_POST = "post";
    private static final String METHOD_DELETE = "delete";
    private static final String METHOD_OPTIONS = "options";

    /**
     * Get String
     * 
     * @return string.
     */
    public String getMethodString() {
        switch (this) {
            case GET:
                return METHOD_GET;
            case PUT:
                return METHOD_PUT;
            case POST:
                return METHOD_POST;
            case DELETE:
                return METHOD_DELETE;
            case OPTIONS:
                return METHOD_OPTIONS;
            default:
                break;
        }
        return null;
    }
}
