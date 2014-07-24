package com.box.boxjavalibv2.jsonparsing;

import java.util.Collection;

import com.box.boxjavalibv2.dao.BoxObject;
import com.box.boxjavalibv2.dao.IBoxType;

public interface IBoxResourceHub {

    /**
     * Given a {@link IBoxType}, get the corrosponding DAO class.
     * 
     * @param type
     *            resource type
     * @return corresponding resource DAO class
     */
    Class<? extends BoxObject> getClass(IBoxType type);

    /**
     * Get the IBoxType from a lower case string value. For example "file" would return BoxResourceType.FILE
     * 
     * @param type
     * @return
     */
    IBoxType getTypeFromLowercaseString(String type);

    Collection<IBoxType> getAllTypes();
}
