package com.box.boxjavalibv2.jsonparsing;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import com.box.boxjavalibv2.dao.BoxObject;
import com.box.boxjavalibv2.dao.IBoxType;

public abstract class BaseBoxResourceHub implements IBoxResourceHub {

    // As a performance optimization, set up string values for all types.
    protected final Map<String, IBoxType> lowercaseStringToType = new HashMap<String, IBoxType>();

    public BaseBoxResourceHub() {
        initializeTypes();
    }

    @Override
    @SuppressWarnings({"rawtypes", "unchecked"})
    public Class getClass(IBoxType type) {
        return BoxObject.class;
    }

    protected Map<String, IBoxType> getLowerCaseStringToTypeMap() {
        return lowercaseStringToType;
    }

    /**
     * Get the concrete class for IBoxType
     * 
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected abstract Class getConcreteClassForIBoxType();

    /**
     * Get class for a certain type, assuming the input type is an object of the concrete class of IBoxType defined in this resource hub.
     *
     * @param type
     * @return
     */
    @SuppressWarnings("rawtypes")
    protected abstract Class getObjectClassGivenConcreteIBoxType(IBoxType type);

    /**
     * Do call super.initializeTypes() when overriding this.
     */
    protected void initializeTypes() {
        // Make it non-abstract so children can call super. This way makes it more explicit that super should be called.
    }

    @SuppressWarnings("rawtypes")
    protected synchronized void initializeEnumTypes(Class<? extends Enum> cls) {
        Map<String, IBoxType> map = getLowerCaseStringToTypeMap();
        Enum[] types = cls.getEnumConstants();
        for (Enum type : types) {
            String str = type.name().toLowerCase(Locale.ENGLISH);
            map.put(str, (IBoxType) type);
        }
    }
}
