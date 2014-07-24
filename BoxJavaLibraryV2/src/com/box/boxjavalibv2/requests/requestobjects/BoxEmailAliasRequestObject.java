package com.box.boxjavalibv2.requests.requestobjects;

import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxEmailAliasRequestObject extends BoxDefaultRequestObject {

    private BoxEmailAliasRequestObject() {
    }

    /**
     * add an email alias.
     * 
     * @param email
     * @return
     */
    public static BoxEmailAliasRequestObject addEmailAliasRequestObject(final String email) {
        BoxEmailAliasRequestObject entity = new BoxEmailAliasRequestObject();
        return entity.setEmailAlias(email);
    }

    /**
     * Set email alias.
     * 
     * @param email
     * @return
     */
    private BoxEmailAliasRequestObject setEmailAlias(final String email) {
        put("email", email);
        return this;
    }
}
