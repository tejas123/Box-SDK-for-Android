package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxSharedItemsManager extends IBoxResourceManager {

    /**
     * Get the shared item given a SharedItemAuth.
     *
     * @param requestObject
     *            request object
     * @return the shared object referred to by the shared link.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxItem getSharedItem(BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

}