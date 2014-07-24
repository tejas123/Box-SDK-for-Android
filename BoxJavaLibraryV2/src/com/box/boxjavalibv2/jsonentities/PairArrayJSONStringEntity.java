package com.box.boxjavalibv2.jsonentities;

import java.util.ArrayList;
import java.util.Map;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;

/**
 * A special MapJSONStringEntity, when serializing into JSON, it's serialized into array of pairs.
 */
public class PairArrayJSONStringEntity extends MapJSONStringEntity {

    private static final long serialVersionUID = 1L;

    @Override
    public String toJSONString(IBoxJSONParser parser) throws BoxJSONException {
        ArrayList<MapJSONStringEntity> list = new ArrayList<MapJSONStringEntity>();
        for (Map.Entry<String, Object> entry : this.entrySet()) {
            MapJSONStringEntity entity = new MapJSONStringEntity();
            entity.put(entry.getKey(), entry.getValue());
            list.add(entity);
        }
        return parser.convertBoxObjectToJSONStringQuietly(list);
    }
}
