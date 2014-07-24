package com.box.boxjavalibv2.dao;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class BoxHashMap<K, V> extends HashMap<K, V> {

    private static final long serialVersionUID = 1L;

    @SuppressWarnings("unchecked")
    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }

        if (!(obj instanceof HashMap)) {
            return false;
        }

        HashMap<K, Object> mapObj = (HashMap<K, Object>) obj;

        if (mapObj.size() != this.size()) {
            return false;
        }

        try {
            Iterator<Entry<K, Object>> i = mapObj.entrySet().iterator();
            while (i.hasNext()) {
                Entry<K, Object> e = i.next();
                Object key = e.getKey();
                Object value = e.getValue();
                if (value == null) {
                    if (!(get(key) == null && containsKey(key))) {
                        return false;
                    }
                }
                else {
                    if (value instanceof Object[]) {
                        if (!Arrays.equals((Object[]) value, (Object[]) (get(key)))) {
                            return false;
                        }
                    }
                    else if (!value.equals(get(key))) {
                        return false;
                    }

                }

            }
        }
        catch (ClassCastException e) {
            return false;
        }
        catch (NullPointerException e) {
            return false;
        }
        return true;

    }

    @Override
    public int hashCode() {
        int hashCode = 0;
        Iterator<java.util.Map.Entry<K, V>> i = entrySet().iterator();
        while (i.hasNext()) {
            Entry<K, V> entry = i.next();
            // ignore primitive arrays in our calculation of hashCode.
            if (!(entry.getValue() instanceof Object[])) {
                hashCode += entry.hashCode();
            }
        }
        return hashCode;
    }

}
