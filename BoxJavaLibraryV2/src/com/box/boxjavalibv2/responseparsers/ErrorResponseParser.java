package com.box.boxjavalibv2.responseparsers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;

import com.box.boxjavalibv2.dao.BoxGenericServerError;
import com.box.boxjavalibv2.dao.BoxServerError;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.exceptions.BoxUnexpectedStatus;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.utils.Utils;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responseparsers.DefaultBoxJSONResponseParser;
import com.box.restclientv2.responses.DefaultBoxResponse;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * Parser to parse {@link com.box.restclientv2.responses.DefaultBoxResponse} into {@link com.box.boxjavalibv2.dao.BoxServerError} objects. It analyse the
 * response JSON String and uses <a href="http://jackson.codehaus.org/">Jackson JSON processor</a> to generate {@link com.box.boxjavalibv2.dao.BoxServerError}
 * objects.
 */
public class ErrorResponseParser extends DefaultBoxJSONResponseParser {

    private static final String RETRY_AFTER = "Retry-After";

    public ErrorResponseParser(final IBoxJSONParser parser) {
        super(BoxServerError.class, parser);
    }

    @Override
    public Object parse(IBoxResponse response) throws BoxRestException {
        if (!(response instanceof DefaultBoxResponse)) {
            throw new BoxRestException("class mismatch, expected:" + DefaultBoxResponse.class.getName() + ";current:" + response.getClass().getName());
        }

        HttpResponse httpResponse = ((DefaultBoxResponse) response).getHttpResponse();
        try {
            int statusCode = httpResponse.getStatusLine().getStatusCode();
            BoxServerError error = null;
            if (isErrorResponse(statusCode)) {
                error = (BoxServerError) super.parse(response);
            }
            else {
                error = new BoxUnexpectedStatus(statusCode);
                if (isRetryAccepted(statusCode)) {
                    Header header = ((DefaultBoxResponse) response).getHttpResponse().getFirstHeader(RETRY_AFTER);
                    if (header != null) {
                        String value = header.getValue();
                        ((BoxUnexpectedStatus) error).setRetryAfter(Integer.valueOf(value));
                    }
                }
            }
            error.setStatus(statusCode);
            return error;
        }
        finally {
            Utils.consumeHttpEntityQuietly(httpResponse.getEntity());
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    protected Object parseInputStream(InputStream in) {
        String errorStr = null;
        try {
            errorStr = IOUtils.toString(in);
            Object obj = getParser().parseIntoBoxObject(errorStr, getObjectClass());
            // JSON parser falls back to BoxTypedObject when there is no "type" in error String. In this case, this "BoxTypedObject" does not really make sense
            // and should not be used.
            if (obj instanceof BoxServerError) {
                return obj;
            }
        }
        catch (BoxJSONException e) {
            if (StringUtils.isEmpty(errorStr)) {
                errorStr = e.getMessage();
            }
        }
        catch (IOException e) {
            errorStr = "Fail to read response.";
        }

        BoxGenericServerError genericE = new BoxGenericServerError();
        genericE.setMessage(errorStr);
        return genericE;
    }

    private boolean isErrorResponse(int statusCode) {
        return statusCode >= 400 && statusCode < 600;
    }

    private boolean isRetryAccepted(int statusCode) {
        return statusCode == HttpStatus.SC_ACCEPTED;
    }

}
