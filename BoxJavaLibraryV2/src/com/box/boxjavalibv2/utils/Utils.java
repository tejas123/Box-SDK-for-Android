package com.box.boxjavalibv2.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;

import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxTypedObject;

/**
 * Utils class.
 */
public final class Utils {

    /**
     * Private constructor so the class cannot be instantiated.
     */
    private Utils() {
    }

    /**
     * Given a resource type, get the string for it's REST API container. For example, given a {@link BoxResourceType#FILE}, it it's container would be "files".
     * 
     * @param type
     *            type
     * @return container string
     */
    public static String getContainerString(final BoxResourceType type) {
        switch (type) {
            case FILE_VERSION:
                return "versions";
            default:
                return type.toPluralString();
        }
    }

    /**
     * Filter out a list of specified type BoxTypedObject's from a Collection
     * 
     * @param collection
     *            collection to be filtered.
     * @param cls
     *            class of the type to be filtered. e.g. BoxFile.class.
     * @return
     */
    @SuppressWarnings("unchecked")
    public static <T extends BoxTypedObject> List<T> getTypedObjects(BoxCollection collection, Class<T> cls) {
        List<T> objects = new ArrayList<T>();

        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (cls.isInstance(object)) {
                objects.add((T) object);
            }
        }

        return objects;
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists, is closed. The process is done, <i>quietly</i> , without throwing
     * any IOException.
     * 
     * @param entity
     *            the entity to consume.
     * 
     * 
     *            Copied from org.apache.http.util.EntityUtils
     */
    public static void consumeHttpEntityQuietly(final HttpEntity entity) {
        try {
            consumeHttpEntity(entity);
        }
        catch (final IOException ignore) {
        }
    }

    /**
     * Ensures that the entity content is fully consumed and the content stream, if exists, is closed.
     * 
     * @param entity
     *            the entity to consume.
     * @throws IOException
     *             if an error occurs reading the input stream
     * 
     *             Copied from org.apache.http.util.EntityUtils
     */
    public static void consumeHttpEntity(final HttpEntity entity) throws IOException {
        if (entity == null) {
            return;
        }
        if (entity.isStreaming()) {
            final InputStream instream = entity.getContent();
            if (instream != null) {
                instream.close();
            }
        }
    }
}
