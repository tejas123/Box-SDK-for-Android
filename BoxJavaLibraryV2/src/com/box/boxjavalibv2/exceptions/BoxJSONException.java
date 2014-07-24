package com.box.boxjavalibv2.exceptions;

import com.box.restclientv2.exceptions.BoxSDKException;

public class BoxJSONException extends BoxSDKException {

    public BoxJSONException(Exception e) {
        super(e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

}
