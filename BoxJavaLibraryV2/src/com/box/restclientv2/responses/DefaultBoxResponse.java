package com.box.restclientv2.responses;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responseparsers.IBoxResponseParser;

/**
 * Default implementation for BOX JSON response. This implementation uses <a href="http://jackson.codehaus.org/">Jackson JSON processor</a> to parse response
 * JSON into objects.
 */
public class DefaultBoxResponse implements IBoxResponse {

    private int expectedResponseCode;

    private final HttpResponse httpResponse;

    /**
     * Constructor.
     * 
     * @param httpResponse
     *            raw http response.
     */
    public DefaultBoxResponse(HttpResponse httpResponse) {
        this.httpResponse = httpResponse;
    }

    /**
     * Get the raw HttpResponse.
     * 
     * @return HttpResponse.
     */
    public HttpResponse getHttpResponse() {
        return httpResponse;
    }

    /**
     * Get the response status code.
     * 
     * @return resposne status code.
     */
    public int getResponseStatusCode() {
        return httpResponse.getStatusLine().getStatusCode();
    }

    @Override
    public Object parseResponse(IBoxResponseParser responseParser, IBoxResponseParser errorParser) throws BoxRestException {
        int statusCode = getHttpResponse().getStatusLine().getStatusCode();
        if (statusCode != getExpectedResponseCode()) {
            return errorParser.parse(this);
        }
        else {
            return responseParser.parse(this);
        }
    }

    /**
     * Set expected response http status code, in case the response have different response code, the response will be treated as error.
     * 
     * @param code
     *            expected response http status code.
     */
    public void setExpectedResponseCode(int code) {
        this.expectedResponseCode = code;
    }

    /**
     * Get the expected http response status code.
     * 
     * @return expected http response status code.
     */
    public int getExpectedResponseCode() {
        return this.expectedResponseCode;
    }

    @Override
    public double getContentLength() {
        Header header = getHttpResponse().getFirstHeader("Content-Length");
        if (header != null) {

            try {
                return Double.parseDouble(header.getValue());
            }
            catch (NumberFormatException e) {
                // Something wrong with response, content length is unknown in this case.
            }
        }
        return 0;
    }
}
