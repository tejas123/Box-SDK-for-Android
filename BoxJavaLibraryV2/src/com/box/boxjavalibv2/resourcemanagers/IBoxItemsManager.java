package com.box.boxjavalibv2.resourcemanagers;

import java.io.UnsupportedEncodingException;

import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemCopyRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxItemsManager extends IBoxResourceManager {

    /**
     * Get item given an item id.
     * 
     * @param id
     *            id of the item
     * @param requestObject
     *            requestObject
     * @param type
     *            resource type
     * @return requested box file/folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxItem getItem(String id, BoxDefaultRequestObject requestObject, BoxResourceType type) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Copy an item.
     * 
     * @param id
     *            id of the item
     * @param requestObject
     *            request object
     * @param type
     *            resource type of the item
     * @return copied file/folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxItem copyItem(String id, BoxItemCopyRequestObject requestObject, BoxResourceType type) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Update info for an item
     * 
     * @param id
     *            id of the item
     * @param requestObject
     *            request object
     * @param type
     *            resource type of the item
     * @return updated file/folder
     * @throws UnsupportedEncodingException
     *             exception
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxItem updateItemInfo(String id, BoxItemRequestObject requestObject, BoxResourceType type) throws UnsupportedEncodingException, BoxRestException,
        BoxServerException, AuthFatalFailureException;

    /**
     * Create a shared link for an item, given the id .
     *
     * @param id
     *            id of the item
     * @param requestObject
     *            request object
     * @param type
     *            resource type of this item
     * @return the file/folder, with shared link related fields filled in.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxItem createSharedLink(String id, BoxSharedLinkRequestObject requestObject, BoxResourceType type) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;
}