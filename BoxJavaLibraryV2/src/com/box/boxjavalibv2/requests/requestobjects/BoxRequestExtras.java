package com.box.boxjavalibv2.requests.requestobjects;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BoxRequestExtras {

    private final List<String> fields = new ArrayList<String>();
    private final Map<String, String> queryParams = new HashMap<String, String>();
    private final Map<String, String> headers = new HashMap<String, String>();

    public List<String> getFields() {
        return fields;
    }

    /**
     * Add a field in the request, these fields (Please check "Fields" part in <a href="http://developers.box.com/docs/">developer doc</a> will end up as fields
     * query parameter in the url.
     * 
     * @param field
     *            field to add. Currently supported fields are the Strings defined in {@link com.box.boxjavalibv2.dao#BoxCollaboration}, for example:
     *            {@link com.box.boxjavalibv2.dao.BoxCollaboration#FIELD_ROLE}, {@link com.box.boxjavalibv2.dao.BoxCollaboration#FIELD_CREATED_BY}...
     */
    public BoxRequestExtras addField(String field) {
        getFields().add(field);
        return this;
    }

    /**
     * Add fields in the request, these fields (Please check "Fields" part in <a href="http://developers.box.com/docs/">developer doc</a> will end up as fields
     * query parameter in the url.
     * 
     * @param fields
     *            fields to add. Currently supported fields are the Strings defined in {@link com.box.boxjavalibv2.dao#BoxCollaboration}, for example:
     *            {@link com.box.boxjavalibv2.dao.BoxCollaboration#FIELD_ROLE}, {@link com.box.boxjavalibv2.dao.BoxCollaboration#FIELD_CREATED_BY}...
     */
    public BoxRequestExtras addFields(List<String> fields) {
        getFields().addAll(fields);
        return this;
    }

    /**
     * Add a query parameter. Which eventually will go into url.
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public BoxRequestExtras addQueryParam(String key, String value) {
        queryParams.put(key, value);
        return this;
    }

    /**
     * Add a header.
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public BoxRequestExtras addHeader(String key, String value) {
        headers.put(key, value);
        return this;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    /**
     * Set etag.
     * 
     * @param etag
     *            etag
     * @return BoxFileRequestObject
     */
    public BoxRequestExtras setIfMatch(String etag) {
        addHeader("If-Match", etag);
        return this;
    }
}
