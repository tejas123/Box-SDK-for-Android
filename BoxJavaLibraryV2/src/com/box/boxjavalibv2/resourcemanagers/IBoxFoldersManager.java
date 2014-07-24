package com.box.boxjavalibv2.resourcemanagers;

import java.io.UnsupportedEncodingException;
import java.util.List;

import com.box.boxjavalibv2.dao.BoxCollaboration;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.GetFolderItemsRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxFolderDeleteRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxFolderRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemCopyRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxFoldersManager extends IBoxResourceManager {

    /**
     * Get folder given a folder id.
     * 
     * @param folderId
     *            id of the folder
     * @param requestObject
     *            object that goes into request.
     * @return requested box folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder getFolder(String folderId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Create a folder.
     * 
     * @param requestObject
     *            request object
     * @return created folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder createFolder(BoxFolderRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Delete a folder.
     * 
     * @param folderId
     *            id of the folder
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteFolder(String folderId, BoxFolderDeleteRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Copy a folder.
     * 
     * @param folderId
     *            id of the folder
     * @param requestObject
     *            request object
     * @return copied folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder copyFolder(String folderId, BoxItemCopyRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Get the items(subfolders, files, weblinks...) under a folder. By default, returning maximum of {@link GetFolderItemsRequest#DEFAULT_FOLDER_ITEMS_LIMIT}
     * items, at an offset of {@link GetFolderItemsRequest#DEFAULT_FOLDER_ITEMS_OFFSET}
     * 
     * @param folderId
     *            id of the folder.
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
    public BoxCollection getFolderItems(String folderId, BoxPagingRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Update info for a folder.
     * 
     * @param folderId
     *            id of the folder
     * @param requestObject
     *            request object
     * @return updated folder
     * @throws UnsupportedEncodingException
     *             exception
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder updateFolderInfo(String folderId, BoxFolderRequestObject requestObject) throws UnsupportedEncodingException, BoxRestException,
        BoxServerException, AuthFatalFailureException;

    /**
     * Create a shared link for a folder, given the id of the file/folder.
     * 
     * @param folderId
     *            id of the folder
     * @param requestObject
     *            request object
     * @return the folder, with shared link related fields filled in.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder createSharedLink(String folderId, BoxSharedLinkRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Get collaborations of a folder.
     * 
     * @param folderId
     *            id of the folder
     * @return collaborations
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public List<BoxCollaboration> getFolderCollaborations(String folderId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

}