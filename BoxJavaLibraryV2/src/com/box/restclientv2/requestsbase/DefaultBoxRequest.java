package com.box.restclientv2.requestsbase;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpOptions;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxRequestExtras;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.httpclientsupport.HttpClientURIBuilder;

/**
 * Default implementation for IBoxRequest interface. This implementaion utilizes HttpRequestBase as underlying http request.
 */
public class DefaultBoxRequest implements IBoxRequest {

    private static final String FIELDS = "fields";
    private static final String DELIMITER = ",";

    /** Auth. */
    private WeakReference<IBoxRequestAuth> mAuth;
    /** Config */
    private final IBoxConfig mConfig;
    /** Http entity. */
    private HttpEntity mEntity;
    /** Cookie. */
    private ICookie mCookie;
    /** REST method. */
    private final RestMethod mRestMethod;
    /** Endpoint uri. */
    private final String uriPath;
    /** Query parameters. */
    private final HashMap<String, String> queryParams = new HashMap<String, String>();
    /** Headers. */
    private final HashMap<String, String> headers = new HashMap<String, String>();
    /** Raw request. */
    private HttpRequestBase rawRequest;
    /** HttpParams. */
    private final HttpParams httpParams = new BasicHttpParams();
    /** Expected response code. */
    private int expectedResponseCode = HttpStatus.SC_OK;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param uriPath
     *            e.g. /folders/93285/items
     * @param restMethod
     *            REST method
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     * @throws BoxJSONException
     */
    public DefaultBoxRequest(final IBoxConfig config, final IBoxJSONParser parser, final String uriPath, final RestMethod restMethod,
        final BoxDefaultRequestObject requestObject) throws BoxRestException {
        this.mConfig = config;
        this.mRestMethod = restMethod;
        this.uriPath = uriPath;
        getHeaders().put("User-Agent", getConfig().getUserAgent());
        if (requestObject != null) {
            try {
                setEntity(requestObject.getEntity(parser));
            }
            catch (BoxJSONException e) {
                throw new BoxRestException(e, "Cannot parse entity of the request object.");
            }
            catch (UnsupportedEncodingException e) {
                throw new BoxRestException(e, "UnsupportedEncodingException in the request object.");
            }

            BoxRequestExtras mutator = requestObject.getRequestExtras();
            setRequestFields(mutator.getFields());
            getQueryParams().putAll(mutator.getQueryParams());
            getHeaders().putAll(mutator.getHeaders());
        }
    }

    @Override
    public int getExpectedResponseCode() {
        return expectedResponseCode;
    }

    /**
     * Set the expected returned http response status code.
     * 
     * @param code
     *            code
     */
    protected void setExpectedResponseCode(final int code) {
        this.expectedResponseCode = code;
    }

    @Override
    public HttpParams getHttpParams() {
        return httpParams;
    }

    @Override
    public IBoxRequestAuth getAuth() {
        return mAuth != null ? mAuth.get() : null;
    }

    @Override
    public ICookie getCookie() {
        return mCookie;
    }

    @Override
    public HttpEntity getRequestEntity() {
        return mEntity;
    }

    @Override
    public RestMethod getRestMethod() {
        return mRestMethod;
    }

    /**
     * Get raw request underlying this DefaultBoxRequest. Note this object is not constructed until prepareRequest method is called.
     * 
     * @return raw request.
     */
    public HttpRequestBase getRawRequest() {
        return rawRequest;
    }

    @Override
    public void setAuth(final IBoxRequestAuth auth) {
        this.mAuth = new WeakReference<IBoxRequestAuth>(auth);
    }

    @Override
    public void setCookie(final ICookie cookie) {
        this.mCookie = cookie;
    }

    /**
     * Set entity for the request.
     * 
     * @param entity
     *            entity.
     */
    public void setEntity(final HttpEntity entity) {
        this.mEntity = entity;
    }

    @Override
    public void addQueryParam(final String name, final String value) {
        queryParams.put(name, value);
    }

    @Override
    public void addHeader(final String name, final String value) {
        headers.put(name, value);
    }

    /**
     * Set If-Match header.
     * 
     * @param ifMatch
     *            the If-Match header value
     */
    public void setIfMatch(final String ifMatch) {
        addHeader("If-Match", ifMatch);
    }

    /**
     * Set fields on the request. By default, each box object has a complete and a mini format. The complete format is returned when you request a specific
     * object. The mini is returned when the object is part of a collection of items. In either case, you can set the fields here to specify which specific
     * fields you'd like returned. They can be any fields that are a part of the complete object for that particular type.
     * 
     * @param fields
     *            fields
     */
    public void setRequestFields(final List<String> fields) {
        StringBuilder sbr = new StringBuilder();
        if (fields != null && !fields.isEmpty()) {
            int size = fields.size();
            for (int i = 0; i < size - 1; i++) {
                sbr.append(fields.get(i)).append(DELIMITER);
            }
            sbr.append(fields.get(size - 1));
            addQueryParam(FIELDS, sbr.toString());
        }
    }

    /**
     * Get the headers.
     * 
     * @return headers
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * Add http param.
     * 
     * @param name
     *            name
     * @param value
     *            value
     */
    public void addHttpParam(final String name, final String value) {
        httpParams.setParameter(name, value);
    }

    /**
     * Get query parameters.
     * 
     * @return query parameters
     */
    public Map<String, String> getQueryParams() {
        return queryParams;
    }

    @Override
    public HttpRequestBase prepareRequest() throws BoxRestException, AuthFatalFailureException {
        rawRequest = constructHttpUriRequest();
        HttpClientURIBuilder ub;
        try {
            ub = new HttpClientURIBuilder();
            ub.setHost(getAuthority());
            ub.setScheme(getScheme());
            ub.setPath(getApiUrlPath().concat(uriPath).replaceAll("/{2,}", "/"));
            for (Map.Entry<String, String> entry : getQueryParams().entrySet()) {
                ub.addParameter(entry.getKey(), StringUtils.defaultIfEmpty(entry.getValue(), ""));
            }

            rawRequest.setURI(ub.build());
        }
        catch (URISyntaxException e) {
            throw new BoxRestException("URISyntaxException:" + e.getMessage());
        }

        if (getAuth() != null) {
            getAuth().setAuth(this);
        }

        if (getCookie() != null) {
            getCookie().setCookie(this);
        }

        if (getRequestEntity() != null && rawRequest instanceof HttpEntityEnclosingRequestBase) {
            ((HttpEntityEnclosingRequestBase) rawRequest).setEntity(getRequestEntity());
        }

        for (Map.Entry<String, String> entry : getHeaders().entrySet()) {
            rawRequest.addHeader(entry.getKey(), entry.getValue());
        }

        rawRequest.setParams(getHttpParams());

        return rawRequest;
    }

    public IBoxConfig getConfig() {
        return mConfig;
    }

    @Override
    public String getScheme() {
        return mConfig.getApiUrlScheme();
    }

    @Override
    public String getAuthority() {
        return mConfig.getApiUrlAuthority();
    }

    @Override
    public String getApiUrlPath() {
        return mConfig.getApiUrlPath();
    }

    /**
     * Construct raw request. Currently only support GET/PUT/POST/DELETE.
     * 
     * @return raw request
     * @throws BoxRestException
     *             exception
     */
    HttpRequestBase constructHttpUriRequest() throws BoxRestException {
        switch (getRestMethod()) {
            case GET:
                return new HttpGet();
            case PUT:
                return new HttpPut();
            case POST:
                return new HttpPost();
            case DELETE:
                return new HttpDelete();
            case OPTIONS:
                return new HttpOptions();
            default:
                throw new BoxRestException("Method Not Implemented");
        }
    }
}
