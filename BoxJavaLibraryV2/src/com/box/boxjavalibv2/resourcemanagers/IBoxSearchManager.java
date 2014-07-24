package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxSearchManager extends IBoxResourceManager {

    /**
     * Perform a search against the user's account.
     * 
     * @param searchQuery
     *            search query string
     * @param requestObject
     *            request object
     * @return Items(subfolders, files, weblinks...) under the folder.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxCollection search(String searchQuery, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

}