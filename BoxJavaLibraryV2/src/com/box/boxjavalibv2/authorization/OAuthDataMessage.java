package com.box.boxjavalibv2.authorization;

import java.io.IOException;

import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.restclientv2.exceptions.BoxRestException;

/**
 * A message for OAuthData.
 */
public class OAuthDataMessage extends StringMessage {

    public static final String OAUTH_DATA_MESSAGE_KEY = "oauth_data";

    private final IBoxJSONParser mParser;

    private final IBoxResourceHub mHub;

    /**
     * Constructor.
     * 
     * @param oauthData
     *            OAuthData
     * @throws BoxRestException
     *             excetption
     * @throws BoxJSONException
     * @throws IOException
     */
    public OAuthDataMessage(final BoxOAuthToken oauthData, IBoxJSONParser parser, IBoxResourceHub hub) throws BoxRestException, BoxJSONException, IOException {
        super(OAUTH_DATA_MESSAGE_KEY, parser.convertBoxObjectToJSONString(oauthData));
        this.mParser = parser;
        this.mHub = hub;
    }

    /**
     * Get the OAuthData out from the value(String).
     * 
     * @return OAuthData
     */
    @SuppressWarnings("unchecked")
    @Override
    public BoxOAuthToken getData() {
        return mParser.parseIntoBoxObjectQuietly((String) super.getData(), (Class<BoxOAuthToken>) mHub.getClass(BoxResourceType.OAUTH_DATA));
    }
}
