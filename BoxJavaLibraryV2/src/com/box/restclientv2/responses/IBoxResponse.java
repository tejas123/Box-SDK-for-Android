package com.box.restclientv2.responses;

import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responseparsers.IBoxResponseParser;

/**
 * Interface for API response.
 */
public interface IBoxResponse {

    /**
     * Parese HttpResponse into IResponseObject.
     * 
     * @param boxResponseObject
     * @return parsed object
     * @throws BoxRestException
     */
    public Object parseResponse(IBoxResponseParser responseParser, IBoxResponseParser errorParser) throws BoxRestException;

    /**
     * Get content length of the response.
     */
    public double getContentLength();

}
