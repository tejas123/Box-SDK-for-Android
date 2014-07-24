package com.box.restclientv2.requestsbase;

import java.io.UnsupportedEncodingException;

import org.apache.commons.codec.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.entity.StringEntity;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxRequestExtras;
import com.box.restclientv2.exceptions.BoxRestException;

/**
 * A request object with entity and fields.
 */
public class BoxDefaultRequestObject implements IBoxRequestObject {

    private final MapJSONStringEntity jsonEntity = new MapJSONStringEntity();
    private final BoxRequestExtras requestExtras = new BoxRequestExtras();

    public BoxDefaultRequestObject() {
    }

    /**
     * This is package protected, any class overriding this should be in a same package.
     */
    HttpEntity getEntity(IBoxJSONParser parser) throws BoxRestException, BoxJSONException, UnsupportedEncodingException {
        MapJSONStringEntity en = getJSONEntity();
        if (en == null) {
            return null;
        }
        try {
            return new StringEntity(en.toJSONString(parser), CharEncoding.UTF_8);
        }
        catch (UnsupportedEncodingException e) {
            throw new BoxRestException(e);
        }
    }

    protected MapJSONStringEntity getJSONEntity() {
        return jsonEntity;
    }

    /**
     * Add a key value pair to the request body. Not recommended to use. If possible, use provided setters.
     * 
     * @param key
     *            key
     * @param value
     *            value
     */
    public Object put(String key, Object value) {
        return getJSONEntity().put(key, value);
    }

    /**
     * Get value from entity.
     */
    public Object getFromEntity(String key) {
        return getJSONEntity().get(key);
    }

    /**
     * @param limit
     *            the number of items to return. default is 100, max is 1000.
     * @param offset
     *            the item at which to begin the response, default is 0.
     * @return BoxDefaultRequestObject
     */
    public BoxDefaultRequestObject setPage(final int limit, final int offset) {
        getRequestExtras().addQueryParam("limit", Integer.toString(limit));
        getRequestExtras().addQueryParam("offset", Integer.toString(offset));
        return this;
    }

    /**
     * a mutator to the request. This allows you to add any extra header, query params into the request. Use with caution because we don't block anything you
     * set here. e.g., getRequestExtras().addHeader("key", "value");
     */
    public BoxRequestExtras getRequestExtras() {
        return requestExtras;
    }
}
