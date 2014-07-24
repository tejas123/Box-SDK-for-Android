package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxServerError;
import com.box.boxjavalibv2.dao.IBoxType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.exceptions.BoxUnexpectedHttpStatusException;
import com.box.boxjavalibv2.exceptions.BoxUnexpectedStatus;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.responseparsers.BoxObjectResponseParser;
import com.box.boxjavalibv2.responseparsers.ErrorResponseParser;
import com.box.boxjavalibv2.utils.Utils;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;
import com.box.restclientv2.responses.DefaultBoxResponse;

/**
 * Base class for BoxAPI classes.
 */
public abstract class AbstractBoxResourceManager implements IBoxResourceManager {

    /** BoxConfig. */
    private final IBoxConfig mConfig;
    private final IBoxResourceHub mResourceHub;
    private final IBoxJSONParser mParser;

    private final IBoxRequestAuth mAuth;

    private final IBoxRESTClient mRestClient;

    /**
     * private constructor.
     * 
     * @param config
     *            Config
     * @param resourceHub
     *            IResourceHub
     * @param parser
     *            json parser
     * @param auth
     *            auth for api calls
     * @param restClient
     *            REST client to make api calls.
     */
    public AbstractBoxResourceManager(final IBoxConfig config, final IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        this.mConfig = config;
        this.mResourceHub = resourceHub;
        this.mParser = parser;
        this.mAuth = auth;
        this.mRestClient = restClient;
    }

    public IBoxRequestAuth getAuth() {
        return mAuth;
    }

    protected IBoxRESTClient getRestClient() {
        return this.mRestClient;
    }

    public IBoxResourceHub getResourceHub() {
        return mResourceHub;
    }

    public IBoxJSONParser getJSONParser() {
        return mParser;
    }

    public IBoxConfig getConfig() {
        return mConfig;
    }

    /**
     * Execute a request and expect no response body.
     */
    protected void executeRequestWithNoResponseBody(final DefaultBoxRequest request) throws BoxServerException, BoxRestException, AuthFatalFailureException {
        request.setAuth(getAuth());
        DefaultBoxResponse response = (DefaultBoxResponse) getRestClient().execute(request);
        try {
            if (response.getExpectedResponseCode() != response.getResponseStatusCode()) {
                ErrorResponseParser errorParser = new ErrorResponseParser(getJSONParser());
                BoxServerError error = (BoxServerError) errorParser.parse(response);
                if (error == null) {
                    throw new BoxServerException("Unexpected response code:" + response.getResponseStatusCode() + ", expecting:"
                                                 + response.getExpectedResponseCode(), response.getResponseStatusCode());
                }
                else {
                    throw new BoxServerException(error);
                }
            }
        }
        finally {
            Utils.consumeHttpEntityQuietly(response.getHttpResponse().getEntity());
        }
    }

    /**
     * Make a rest api request, get response, parse the response, and try to cast parsed out object into expected object.
     */
    public Object getResponseAndParseAndTryCast(final DefaultBoxRequest request, final IBoxType type, final IBoxJSONParser parser) throws BoxRestException,
        AuthFatalFailureException, BoxServerException {
        Object obj = getResponseAndParse(request, type, parser);
        return tryCastObject(type, obj);
    }

    /**
     * Make a rest api request, get response, and then parse the response.
     */
    public Object getResponseAndParse(final DefaultBoxRequest request, final IBoxType type, final IBoxJSONParser parser) throws BoxRestException,
        AuthFatalFailureException {
        request.setAuth(getAuth());
        DefaultBoxResponse response = (DefaultBoxResponse) getRestClient().execute(request);
        BoxObjectResponseParser responseParser = new BoxObjectResponseParser(getClassFromType(type), parser);
        ErrorResponseParser errorParser = new ErrorResponseParser(getJSONParser());
        return response.parseResponse(responseParser, errorParser);
    }

    /**
     * Try to cast an object into a specific class.
     */
    @SuppressWarnings("rawtypes")
    public Object tryCastObject(final IBoxType expectedType, final Object obj) throws BoxServerException, BoxRestException {
        if (obj instanceof BoxServerError) {
            throw new BoxServerException((BoxServerError) obj);
        }
        else if (obj instanceof BoxUnexpectedStatus) {
            throw new BoxUnexpectedHttpStatusException((BoxUnexpectedStatus) obj);
        }
        else {
            Class expectedClass = getClassFromType(expectedType);
            if (expectedClass.isInstance(obj)) {
                return obj;
            }
            else {
                if (obj == null) {
                    throw new BoxRestException("Invalid class, expected:" + expectedClass.getCanonicalName());
                }
                throw new BoxRestException("Invalid class, expected:" + expectedClass.getCanonicalName() + ";current:" + obj.getClass().getCanonicalName());
            }
        }
    }

    @SuppressWarnings("rawtypes")
    protected Class getClassFromType(final IBoxType type) {
        return getResourceHub().getClass(type);
    }

    // TODO: support web links
    /**
     * Try to cast a box item into a concrete class(i.e. file or folder)
     */
    protected Object tryCastBoxItem(final BoxResourceType type, final Object item) throws BoxServerException, BoxRestException {
        return tryCastObject(type, item);
    }
}
