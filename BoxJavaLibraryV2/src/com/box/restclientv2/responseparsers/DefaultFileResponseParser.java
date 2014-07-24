package com.box.restclientv2.responseparsers;

import org.apache.http.HttpResponse;

import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responses.DefaultBoxResponse;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * Default implementation for response parser to parse a response with a file InputStream.
 */
public class DefaultFileResponseParser implements IBoxResponseParser {

    @Override
    public Object parse(IBoxResponse response) throws BoxRestException {
        if (!(response instanceof DefaultBoxResponse)) {
            throw new BoxRestException("class mismatch, expected:" + DefaultBoxResponse.class.getName() + ";current:" + response.getClass().getCanonicalName());
        }
        HttpResponse httpResponse = ((DefaultBoxResponse) response).getHttpResponse();
        try {
            return httpResponse.getEntity().getContent();
        }
        catch (Exception e) {
            throw new BoxRestException(e, "Failed to parse response.");
        }
    }
}
