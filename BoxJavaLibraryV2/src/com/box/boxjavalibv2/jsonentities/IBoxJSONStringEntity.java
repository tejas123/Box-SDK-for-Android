package com.box.boxjavalibv2.jsonentities;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;

/**
 * Interface for classes that can be converted to JSON Strings.
 */
public interface IBoxJSONStringEntity {

    /**
     * Convert to JSON String.
     * 
     * @param parser
     *            json parser
     * @return JSON String
     * @throws BoxJSONException
     */
    String toJSONString(IBoxJSONParser parser) throws BoxJSONException;
}
