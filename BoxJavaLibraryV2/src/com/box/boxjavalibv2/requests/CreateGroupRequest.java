package com.box.boxjavalibv2.requests;

import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxGroupRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

public class CreateGroupRequest extends DefaultBoxRequest {

    private static final String URI = "/groups";

    public CreateGroupRequest(final IBoxConfig config, final IBoxJSONParser parser, final BoxGroupRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(), RestMethod.POST, requestObject);
        setExpectedResponseCode(HttpStatus.SC_CREATED);
    }

    /**
     * Get uri.
     * 
     * @return uri
     */
    public static String getUri() {
        return URI;
    }
}
