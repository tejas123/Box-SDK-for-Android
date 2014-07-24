package com.box.boxjavalibv2.exceptions;

import com.box.boxjavalibv2.dao.BoxServerError;
import com.box.restclientv2.exceptions.BoxSDKException;

/**
 * Exception wrapping error responses received from api calls. This indicates api response is received so network level is good, however server returns error
 * information.
 */
public class BoxServerException extends BoxSDKException {

    /**
     * Default serial version UID.
     */
    private static final long serialVersionUID = 1L;

    private BoxServerError error;
    private String customMessage;
    private int statusCode;

    /**
     * For jackson parser use only.
     */
    protected BoxServerException() {
    }

    /**
     * Constructor.
     * 
     * @param customMessage
     *            message
     * @param statusCode
     *            http status code
     */
    public BoxServerException(String customMessage, int statusCode) {
        this.customMessage = customMessage;
        this.statusCode = statusCode;
    }

    /**
     * Constructor.
     * 
     * @param error
     *            API response error.
     */
    public BoxServerException(BoxServerError error) {
        this.error = error;
        Integer status = error.getStatus();
        if (status != null) {
            this.statusCode = status;
        }
    }

    /**
     * Get the API response error.
     * 
     * @return API response error.
     */
    public BoxServerError getError() {
        return error;
    }

    /**
     * Get the custom error message.
     * 
     * @return custom error message
     */
    public String getCustomMessage() {
        return customMessage;
    }

    @Override
    public int getStatusCode() {
        return statusCode;
    }

    @Override
    public String getMessage() {
        if (customMessage != null) {
            return String.format("%s:%s", statusCode, customMessage);
        }
        else if (error != null) {
            return error.getMessage();
        }
        else {
            return null;
        }
    }

}
