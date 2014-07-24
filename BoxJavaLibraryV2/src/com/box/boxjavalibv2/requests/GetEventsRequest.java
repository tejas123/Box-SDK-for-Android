package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxEventRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to get events. http://developers.box.com/docs/#events
 */
public class GetEventsRequest extends DefaultBoxRequest {

    private static final String URI = "/events";

    /**
     * Constructor.
     * 
     * @param config
     * @param parser
     * @param requestObject
     * @throws BoxEventRequestObject
     */
    public GetEventsRequest(final IBoxConfig config, final IBoxJSONParser parser, BoxEventRequestObject requestObject) throws BoxRestException {
        super(config, parser, URI, RestMethod.GET, requestObject);
    }

}
