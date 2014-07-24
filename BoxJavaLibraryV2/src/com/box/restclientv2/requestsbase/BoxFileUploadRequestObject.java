package com.box.restclientv2.requestsbase;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.codec.CharEncoding;
import org.apache.http.HttpEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.InputStreamBody;
import org.apache.http.entity.mime.content.StringBody;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.filetransfer.IFileTransferListener;
import com.box.boxjavalibv2.httpentities.MultipartEntityWithProgressListener;
import com.box.boxjavalibv2.jsonentities.IBoxJSONStringEntity;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.utils.Constants;
import com.box.boxjavalibv2.utils.ISO8601DateParser;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxFileUploadRequestObject extends BoxDefaultRequestObject {

    private static final String METADATA = "metadata";
    private MultipartEntityWithProgressListener entity = null;

    private static final String KEY_PARENT = "parent";
    private static final String KEY_NAME = "name";
    private static final String KEY_FILE_NAME = "filename";
    private static final String KEY_CONTENT_CREATED_AT = "content_created_at";
    private static final String KEY_CONTENT_MODIFIED_AT = "content_modified_at";

    private BoxFileUploadRequestObject() {
        super();
    }

    /**
     * BoxFileUploadRequestObject for upload file request. Note: for uploading a new version of the file, please use uploadNewVersionRequestObject().
     * 
     * @param parentId
     *            id of the parent folder
     * @param fileName
     *            name of the file
     * @param file
     *            file to be uploaded
     * @return BoxFileUploadRequestObject
     * @throws BoxRestException
     * @throws BoxJSONException
     */
    public static BoxFileUploadRequestObject uploadFileRequestObject(final String parentId, final String fileName, final File file) throws BoxRestException,
        BoxJSONException {
        try {
            BoxFileUploadRequestObject requestObject = new BoxFileUploadRequestObject();
            return requestObject.setMultipartMIME(getNewFileMultipartEntity(parentId, fileName, file));
        }
        catch (UnsupportedEncodingException e) {
            throw new BoxRestException(e);
        }
    }

    /**
     * BoxFileUploadRequestObject for upload file request. Note: for uploading a new version of the file, please use uploadNewVersionRequestObject().
     * 
     * @param parentId
     *            id of the parent folder
     * @param fileName
     *            name of the file
     * @param inputStream
     *            InputStream of the file to be uploaded
     * @return BoxFileUploadRequestObject
     * @throws BoxRestException
     * @throws BoxJSONException
     */
    public static BoxFileUploadRequestObject uploadFileRequestObject(final String parentId, final String fileName, final InputStream inputStream)
        throws BoxRestException, BoxJSONException {
        try {
            return (new BoxFileUploadRequestObject()).setMultipartMIME(getNewFileMultipartEntity(parentId, inputStream, fileName));
        }
        catch (UnsupportedEncodingException e) {
            throw new BoxRestException(e);
        }
    }

    /**
     * BoxFileUploadRequestObject for upload a new version of a file.
     * 
     * @param name
     *            name of the file
     * @param file
     *            file to be uploaded
     * @return BoxFileUploadRequestObject
     * @throws BoxRestException
     *             exception
     */
    public static BoxFileUploadRequestObject uploadNewVersionRequestObject(final String name, final File file) throws BoxRestException {
        try {
            return (new BoxFileUploadRequestObject()).setMultipartMIME(getNewVersionMultipartEntity(name, file));
        }
        catch (UnsupportedEncodingException e) {
            throw new BoxRestException(e);
        }
    }

    /**
     * BoxFileUploadRequestObject for upload a new version of a file.
     * 
     * @param name
     *            name of the file
     * @param inputStream
     *            input stream of the file uploaded.
     * @return BoxFileUploadRequestObject
     * @throws BoxRestException
     *             exception
     */
    public static BoxFileUploadRequestObject uploadNewVersionRequestObject(final String name, final InputStream inputStream) throws BoxRestException {
        try {
            return (new BoxFileUploadRequestObject()).setMultipartMIME(getNewVersionMultipartEntity(name, inputStream));
        }
        catch (UnsupportedEncodingException e) {
            throw new BoxRestException(e);
        }
    }

    public BoxFileUploadRequestObject setMultipartMIME(final MultipartEntityWithProgressListener mime) throws BoxRestException {
        entity = mime;
        return this;
    }

    /**
     * Set upload listener.
     * 
     * @param listener
     *            upload listener
     */
    public BoxFileUploadRequestObject setListener(IFileTransferListener listener) {
        entity.setListener(listener);
        return this;
    }

    /**
     * Set the content MD5 in the request. This is used in upload file request, it can make sure that the file is not corrupted in transit. In case of the sha1
     * is different than the sha1 calculated on server, request is going to fail.
     * 
     * @param sha1
     *            sha1
     */
    public BoxFileUploadRequestObject setContentMD5(String sha1) {
        getRequestExtras().addHeader(Constants.CONTENT_MD5, sha1);
        return this;
    }

    /**
     * Set the time that the file was locally created.
     * 
     * @param createdAt
     * @return
     * @throws UnsupportedEncodingException
     */
    public BoxFileUploadRequestObject setLocalFileCreatedAt(Date createdAt) throws UnsupportedEncodingException {
        entity.addContentBodyPart(KEY_CONTENT_CREATED_AT, new StringBody(ISO8601DateParser.toString(createdAt)));
        return this;
    }

    /**
     * Set the time that the file was locally last modified.
     * 
     * @param createdAt
     * @return
     * @throws UnsupportedEncodingException
     */
    public BoxFileUploadRequestObject setLocalFileLastModifiedAt(Date modifiedAt) throws UnsupportedEncodingException {
        entity.addContentBodyPart(KEY_CONTENT_MODIFIED_AT, new StringBody(ISO8601DateParser.toString(modifiedAt)));
        return this;
    }

    @Override
    public HttpEntity getEntity(IBoxJSONParser parser) throws UnsupportedEncodingException, BoxJSONException {
        entity.prepareParts(parser);
        return entity;
    }

    // In order to handle foreign characters, the file name is handled in metadata part instead of the InputStreamBody part.
    private static MultipartEntityWithProgressListener getNewFileMultipartEntity(final String parentId, final InputStream inputStream, final String fileName)
        throws BoxRestException, UnsupportedEncodingException, BoxJSONException {
        MultipartEntityWithProgressListener me = new MultipartEntityWithProgressListener(HttpMultipartMode.BROWSER_COMPATIBLE);
        me.addContentBodyPart(Constants.FOLDER_ID, new StringBody(parentId));
        me.addBoxJSONStringEntityPart(METADATA, getMetadataBody(parentId, fileName));

        me.addContentBodyPart(fileName, new InputStreamBody(inputStream, KEY_FILE_NAME));

        return me;
    }

    // In order to handle foreign characters, the file name is handled in metadata part instead of the FileBody part.
    private static MultipartEntityWithProgressListener getNewFileMultipartEntity(final String parentId, final String name, final File file)
        throws BoxRestException, UnsupportedEncodingException, BoxJSONException {
        MultipartEntityWithProgressListener me = new MultipartEntityWithProgressListener(HttpMultipartMode.BROWSER_COMPATIBLE);
        me.addContentBodyPart(Constants.FOLDER_ID, new StringBody(parentId));
        me.addContentBodyPart(KEY_FILE_NAME, new FileBody(file, KEY_FILE_NAME, "", CharEncoding.UTF_8));
        me.addBoxJSONStringEntityPart(METADATA, getMetadataBody(parentId, name));
        if (me.getContentBodyPart(KEY_CONTENT_MODIFIED_AT) == null) {
            String date = ISO8601DateParser.toString(new Date(file.lastModified()));
            me.addContentBodyPart(KEY_CONTENT_MODIFIED_AT, new StringBody(date));
        }

        return me;
    }

    private static IBoxJSONStringEntity getMetadataBody(String parentId, String name) throws UnsupportedEncodingException, BoxRestException, BoxJSONException {
        MapJSONStringEntity parentEntity = new MapJSONStringEntity();
        parentEntity.put(Constants.ID, parentId);

        MapJSONStringEntity entity = new MapJSONStringEntity();
        entity.put(KEY_PARENT, parentEntity);
        entity.put(KEY_NAME, name);
        return entity;
    }

    private static MultipartEntityWithProgressListener getNewVersionMultipartEntity(final String name, final File file) throws UnsupportedEncodingException {
        MultipartEntityWithProgressListener me = new MultipartEntityWithProgressListener(HttpMultipartMode.BROWSER_COMPATIBLE);
        me.addContentBodyPart(name, new FileBody(file, name, "", CharEncoding.UTF_8));

        if (me.getContentBodyPart(KEY_CONTENT_MODIFIED_AT) == null) {
            me.addContentBodyPart(KEY_CONTENT_MODIFIED_AT, new StringBody(ISO8601DateParser.toString(new Date(file.lastModified()))));
        }
        return me;
    }

    private static MultipartEntityWithProgressListener getNewVersionMultipartEntity(final String name, final InputStream inputStream)
        throws UnsupportedEncodingException {
        MultipartEntityWithProgressListener me = new MultipartEntityWithProgressListener(HttpMultipartMode.BROWSER_COMPATIBLE);
        me.addContentBodyPart(name, new InputStreamBody(inputStream, name));

        return me;
    }
}
