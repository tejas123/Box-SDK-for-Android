package com.box.boxjavalibv2.resourcemanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.List;

import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFileVersion;
import com.box.boxjavalibv2.dao.BoxPreview;
import com.box.boxjavalibv2.dao.BoxThumbnail;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.filetransfer.IFileTransferListener;
import com.box.boxjavalibv2.requests.requestobjects.BoxFileRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxImageRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemCopyRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.BoxFileUploadRequestObject;

public interface IBoxFilesManager extends IBoxResourceManager {

    /**
     * Get file given a file id.
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
    public BoxFile getFile(String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Delete a file.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            object that goes into request.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteFile(String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Get preview of a file.
     * 
     * @param fileId
     *            id of the file
     * @param extension
     *            requested of the preview image file extension
     * @param requestObject
     *            request object
     * @return preview
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxPreview getPreview(String fileId, String extension, BoxImageRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Get thumbnail of a file. Deprecated, use getThumbnail instead.s
     */
    @Deprecated
    public InputStream downloadThumbnail(String fileId, String extension, BoxImageRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Get thumbnail of a file.
     * 
     * @param fileId
     *            id of the file
     * @param extension
     *            file extension of requested thumbnail
     * @param requestObject
     *            request object
     * @return InputStream
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxThumbnail getThumbnail(final String fileId, final String extension, final BoxImageRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException;

    /**
     * Upload file/files.
     * 
     * @param requestObject
     *            reqeust object
     * @return newly created box file object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     * @throws InterruptedException
     */

    public BoxFile uploadFile(BoxFileUploadRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException,
        InterruptedException;

    /**
     * Copy a file.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            request object
     * @return copied file
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFile copyFile(String fileId, BoxItemCopyRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Download a file.
     * 
     * @param fileId
     *            id of the file
     * @param destination
     *            destination of the downloaded file
     * @param listener
     *            listener to monitor the download progress
     * @param requestObject
     *            extra request object going into api request
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     * @throws IllegalStateException
     *             IllegalStateException
     * @throws IOException
     *             IOException
     * @throws InterruptedException
     *             InterruptedException
     */
    public void downloadFile(String fileId, File destination, IFileTransferListener listener, BoxDefaultRequestObject requestObject) throws BoxRestException,
        BoxServerException, IllegalStateException, IOException, InterruptedException, AuthFatalFailureException;

    /**
     * Execute the download and return the raw InputStream. This method is not involved with download listeners and will not publish anything through download
     * listeners. Instead caller handles the InputStream as she/he wishes.
     * 
     * @param fileId
     *            id of the file to be downloaded
     * @param requestObject
     *            request object
     * @return InputStream
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public InputStream downloadFile(String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Download a file.
     * 
     * @param fileId
     *            id of the file
     * @param outputStreams
     *            OutputStream's the file will be downloaded into
     * @param listener
     *            listener to monitor the download progress
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     * @throws IOException
     *             exception
     * @throws InterruptedException
     *             exception
     */
    public void downloadFile(String fileId, OutputStream[] outputStreams, IFileTransferListener listener, BoxDefaultRequestObject requestObject)
        throws BoxRestException, IOException, BoxServerException, InterruptedException, AuthFatalFailureException;

    /**
     * Upload a new version of a file.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            requestObject
     * @return a FileVersion object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     * @throws InterruptedException
     */
    public BoxFile uploadNewVersion(String fileId, BoxFileUploadRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException, InterruptedException;

    /**
     * Get file versions(Note: Versions are only tracked for Box users with premium accounts.).
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            request object
     * @return FileVersions
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public List<BoxFileVersion> getFileVersions(String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Update info for a file.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            request object
     * @return updated file
     * @throws UnsupportedEncodingException
     *             exception
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFile updateFileInfo(String fileId, BoxFileRequestObject requestObject) throws UnsupportedEncodingException, BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Create a shared link for a file, given the id of the file/folder.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            request object
     * @return the file, with shared link related fields filled in.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFile createSharedLink(String fileId, BoxSharedLinkRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Get comments on a file.
     * 
     * @param fileId
     *            id of the file
     * @param requestObject
     *            object that goes into request.
     * @return collection of comments
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxCollection getFileComments(String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

}