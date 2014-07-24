package com.box.boxjavalibv2.responseparsers;

import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.responseparsers.DefaultBoxJSONResponseParser;

/**
 * Parser to parse {@link com.box.restclientv2.responses.DefaultBoxResponse} into Box DAO objects. It analyse the response JSON String and uses <a
 * href="http://jackson.codehaus.org/">Jackson JSON processor</a> to generate Box DAO objects.
 */
@SuppressWarnings("rawtypes")
public class BoxObjectResponseParser extends DefaultBoxJSONResponseParser {

    /**
     * Constructor.
     * 
     * @param cls
     *            Object class.
     */
    public BoxObjectResponseParser(Class cls, IBoxJSONParser parser) {
        super(cls, parser);
    }
}
