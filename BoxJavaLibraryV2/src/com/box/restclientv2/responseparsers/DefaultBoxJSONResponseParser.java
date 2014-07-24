package com.box.restclientv2.responseparsers;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responses.DefaultBoxResponse;
import com.box.restclientv2.responses.IBoxResponse;

/**
 * This class is a wrapper class in order for <a href="http://jackson.codehaus.org/">Jackson JSON processor</a> to parse response JSON into the wrapped objects.
 */
@SuppressWarnings("rawtypes")
public class DefaultBoxJSONResponseParser implements IBoxResponseParser {

    private final Class objectClass;

    private final IBoxJSONParser mParser;

    /**
     * Constructor.
     * 
     * @param objectClass
     *            class of the wrapped object. Response from API will be parsed into this object, caller needs to make sure this class has fields with same
     *            names as the JSON part of API response. If class contains more fields than the JSON, those fields will be left null. If the class contains
     *            less fields than the JSON, the parsing will error out.
     * @param parser
     *            IBoxJSONParser
     */
    public DefaultBoxJSONResponseParser(Class objectClass, IBoxJSONParser parser) {
        this.objectClass = objectClass;
        this.mParser = parser;
    }

    public IBoxJSONParser getParser() {
        return mParser;
    }

    /**
     * Class of the wrapped object.
     * 
     * @return class of the wrapped object.
     */
    public Class getObjectClass() {
        return objectClass;
    }

    /**
     * By default, this only parses the JSON part into object. In case caller needs more information(i.e. information from headers) into this object, caller can
     * extend this class and implement the extraParses(HttpResponse httpResponse) method
     * 
     * @throws BoxRestException
     */
    @Override
    public Object parse(IBoxResponse response) throws BoxRestException {
        if (!(response instanceof DefaultBoxResponse)) {
            throw new BoxRestException("class mismatch, expected:" + DefaultBoxResponse.class.getName() + ";current:" + response.getClass().getName());
        }
        HttpResponse httpResponse = ((DefaultBoxResponse) response).getHttpResponse();

        InputStream in = null;
        try {
            in = httpResponse.getEntity().getContent();
            if (in == null) {
                return null;
            }
            else {
                return parseInputStream(in);
            }
        }
        catch (Exception e) {
            throw new BoxRestException(e, "Failed to parse response.");
        }
        finally {
            IOUtils.closeQuietly(in);
        }
    }

    /**
     * Parse input stream.
     * 
     * @param in
     *            input stream.
     * @throws BoxRestException
     * @throws IOException
     */
    @SuppressWarnings("unchecked")
    protected Object parseInputStream(InputStream in) throws BoxRestException, BoxJSONException, IOException {
        return mParser.parseIntoBoxObject(in, objectClass);
    }
}
