package com.box.boxjavalibv2.dao;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.PROPERTY, property = "type", defaultImpl = BoxUserBase.class)
public class BoxUserBase extends BoxTypedObject {

    public static final String FIELD_NAME = "name";

    public BoxUserBase() {
    }

    public BoxUserBase(BoxUserBase obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     * 
     * @param map
     */
    public BoxUserBase(Map<String, Object> map) {
        super(map);
    }

    public BoxUserBase(IBoxParcelWrapper in) {
        super(in);
    }

    /**
     * Get name of this user.
     * 
     * @return name
     */
    @JsonProperty(FIELD_NAME)
    public String getName() {
        return (String) getValue(FIELD_NAME);
    }

    /**
     * Setter. This is only used by {@see <a href="http://jackson.codehaus.org">Jackson JSON processer</a>}
     * 
     * @param name
     *            name
     */
    @JsonProperty(FIELD_NAME)
    private void setName(String name) {
        put(FIELD_NAME, name);
    }
}
