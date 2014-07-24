package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.GetFolderItemsRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemRestoreRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxTrashManager extends IBoxResourceManager {

    /**
     * Get trashed file given a file id.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            object that goes into request.
     * @return requested box file
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFile getTrashFile(String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Permanently delete a trashed file.
     * 
     * @param id
     *            id of the file
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteTrashFile(String id, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Restore a trashed file.
     * 
     * @param id
     *            id of the trashed file.
     * @param requestObject
     * @return the file
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFile restoreTrashFile(String id, BoxItemRestoreRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;

    /**
     * Get trash folder given a folder id.
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
    public BoxFolder getTrashFolder(String folderId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Get all the trashed items(subfolders, files, weblinks...). By default, returning maximum of {@link GetFolderItemsRequest#DEFAULT_FOLDER_ITEMS_LIMIT}
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
    public BoxCollection getTrashItems(BoxPagingRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Permanently delete a trashed folder.
     * 
     * @param id
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
    public void deleteTrashFolder(String id, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Restore a trashed folder.
     * 
     * @param id
     *            id of the trashed folder.
     * @param requestObject
     * @return the folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder restoreTrashFolder(String id, BoxItemRestoreRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;
}
