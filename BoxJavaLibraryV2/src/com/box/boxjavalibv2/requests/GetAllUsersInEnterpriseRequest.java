package com.box.boxjavalibv2.requests;

import org.apache.commons.lang.StringUtils;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Request to return Returns the list of all users for the Enterprise with their user_id, public_name, and login if the user is an enterprise admin. If the user
 * is not an admin, this request returns the current user's user_id, public_name, and login.
 */
public class GetAllUsersInEnterpriseRequest extends DefaultBoxRequest {

    /** Default(max) value of maximum number of items returned. */
    public static final int DEFAULT_ITEMS_MAX = 100;
    /** Default value of offset. */
    public static final int DEFAULT_ITEMS_OFFSET = 0;

    private static final String URI = "/users";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param requestObject
     *            request object
     * @param filterTerm
     *            A string used to filter the results to only users starting with the filter_term in either the name or the login. Use null if don't want
     *            filter.
     * @throws BoxRestException
     */
    public GetAllUsersInEnterpriseRequest(final IBoxConfig config, final IBoxJSONParser parser, BoxDefaultRequestObject requestObject, final String filterTerm)
        throws BoxRestException {
        super(config, parser, getUri(), RestMethod.GET, requestObject);

        if (StringUtils.isNotEmpty(filterTerm)) {
            addQueryParam("filter_term", filterTerm);
        }
    }

    /**
     * Get uri.
     * 
     * @return uri
     */
    public static String getUri() {
        return URI;
    }
}
