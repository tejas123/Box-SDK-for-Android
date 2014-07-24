package com.box.boxjavalibv2.jsonentities;

import java.util.LinkedHashMap;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;

/**
 * Implemenation of {@link IBoxJSONStringEntity} based on LinkedHashMap.
 */
public class MapJSONStringEntity extends LinkedHashMap<String, Object> implements IBoxJSONStringEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public String toJSONString(IBoxJSONParser parser) throws BoxJSONException {
        return parser.convertBoxObjectToJSONStringQuietly(this);
    }

}
