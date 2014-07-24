package com.box.boxjavalibv2.dao;

import java.util.Map;


/**
 * Box Group.
 */
public class BoxGroup extends BoxUserBase {

    /**
     * Constructor.
     */
    public BoxGroup() {
        super();
        setType(BoxResourceType.GROUP.toString());
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     * 
     * @param obj
     */
    public BoxGroup(BoxGroup obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     * 
     * @param map
     */
    public BoxGroup(Map<String, Object> map) {
        super(map);
    }

    public BoxGroup(IBoxParcelWrapper in) {
        super(in);
    }
}
