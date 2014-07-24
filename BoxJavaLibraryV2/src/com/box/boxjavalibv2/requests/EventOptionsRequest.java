package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get the options for the Events api. Among other things, you can use this to get the RealTime server for long polling.
 * http://developers.box.com/docs/#events-long-polling
 */
public class EventOptionsRequest extends DefaultBoxRequest {

    private static final String URI = "/events";

    /**
     * Constructor.
     * 
     * @param config
     * @param parser
     * @param requestObject
     * @throws BoxRestException
     */
    public EventOptionsRequest(final IBoxConfig config, final IBoxJSONParser parser, BoxDefaultRequestObject requestObject) throws BoxRestException {
        super(config, parser, URI, RestMethod.OPTIONS, requestObject);
    }

}
