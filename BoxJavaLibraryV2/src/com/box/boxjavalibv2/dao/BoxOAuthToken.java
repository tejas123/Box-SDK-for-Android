package com.box.boxjavalibv2.dao;

import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

// CHECKSTYLE:OFF
/**
 * OAuth data.
 */
public class BoxOAuthToken extends BoxObject implements IAuthData {

    public static final String FIELD_ACCESS_TOKEN = "access_token";
    public static final String FIELD_EXPIRES_IN = "expires_in";
    public static final String FIELD_TOKEN_TYPE = "token_type";
    public static final String FIELD_REFRESH_TOKEN = "refresh_token";

    public BoxOAuthToken() {
    }

    /**
     * Copy constructor, this does deep copy for all the fields.
     * 
     * @param obj
     */
    public BoxOAuthToken(BoxOAuthToken obj) {
        super(obj);
    }

    /**
     * Instantiate the object from a map. Each entry in the map reflects to a field.
     * 
     * @param map
     */
    public BoxOAuthToken(Map<String, Object> map) {
        super(map);
    }

    /**
     * @return the access token
     */
    @JsonProperty(FIELD_ACCESS_TOKEN)
    public String getAccessToken() {
        return (String) getValue(FIELD_ACCESS_TOKEN);
    }

    /**
     * @param accessToken
     *            the access_token to set
     */
    @JsonProperty(FIELD_ACCESS_TOKEN)
    private void setAccessToken(final String accessToken) {
        put(FIELD_ACCESS_TOKEN, accessToken);
    }

    /**
     * @return the expires_in
     */
    @JsonProperty(FIELD_EXPIRES_IN)
    public Integer getExpiresIn() {
        return (Integer) getValue(FIELD_EXPIRES_IN);
    }

    /**
     * @param expiresIn
     *            the expires_in to set
     */
    @JsonProperty(FIELD_EXPIRES_IN)
    private void setExpiresIn(final Integer expiresIn) {
        put(FIELD_EXPIRES_IN, expiresIn);
    }

    /**
     * @return the token_type
     */
    @JsonProperty(FIELD_TOKEN_TYPE)
    public String getTokenType() {
        return (String) getValue(FIELD_TOKEN_TYPE);
    }

    /**
     * @param tokenType
     *            the token_type to set
     */
    @JsonProperty(FIELD_TOKEN_TYPE)
    private void setTokenType(final String tokenType) {
        put(FIELD_TOKEN_TYPE, tokenType);
    }

    /**
     * @return the refresh_token
     */
    @JsonProperty(FIELD_REFRESH_TOKEN)
    public String getRefreshToken() {
        return (String) getValue(FIELD_REFRESH_TOKEN);
    }

    /**
     * @param refreshToken
     *            the refresh_token to set
     */
    @JsonProperty(FIELD_REFRESH_TOKEN)
    private void setRefreshToken(final String refreshToken) {
        put(FIELD_REFRESH_TOKEN, refreshToken);
    }

    public BoxOAuthToken(IBoxParcelWrapper in) {
        super(in);
    }
}
