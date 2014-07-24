package com.box.restclientv2.requestsbase;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.params.HttpParams;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;

/**
 * Interface for the request to Box API.
 */
public interface IBoxRequest {

    /**
     * Auth.
     * 
     * @return auth.
     */
    IBoxRequestAuth getAuth();

    /**
     * Cookie.
     * 
     * @return cookie
     */
    ICookie getCookie();

    /**
     * Request entity.
     * 
     * @return request entity.
     */
    HttpEntity getRequestEntity();

    /**
     * HttpParams
     * 
     * @return http params
     */
    HttpParams getHttpParams();

    /**
     * Add query parameters into request url.
     * 
     * @param name
     *            name of the parameter
     * @param value
     *            value of the parameter
     */
    void addQueryParam(final String name, final String value);

    /**
     * Add header into request.
     * 
     * @param name
     *            name of header
     * @param value
     *            value of header
     */
    void addHeader(final String name, final String value);

    /**
     * Set authorization.
     * 
     * @param auth
     *            auth
     */
    void setAuth(final IBoxRequestAuth auth);

    /**
     * Set cookie.
     * 
     * @param cookie
     *            cookie.
     */
    void setCookie(final ICookie cookie);

    /**
     * Prepare the request by setting all headers, query params, entities...
     * 
     * @return raw request.
     * @throws BoxRestException
     * @throws AuthFatalFailureException
     */
    HttpUriRequest prepareRequest() throws BoxRestException, AuthFatalFailureException;

    /**
     * REST method, GET/PUT/POST/DELETE/...
     * 
     * @return REST method.
     */
    RestMethod getRestMethod();

    /** Get scheme for the api call. */
    String getScheme();

    /** Get authority for the api call. */
    String getAuthority();

    /** Get api path for the api call. */
    public String getApiUrlPath();

    /** Get expected response code. */
    int getExpectedResponseCode();
}
