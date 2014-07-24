package com.box.boxjavalibv2.resourcemanagers;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFileVersion;
import com.box.boxjavalibv2.dao.BoxPreview;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxServerError;
import com.box.boxjavalibv2.dao.BoxThumbnail;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.exceptions.BoxUnexpectedHttpStatusException;
import com.box.boxjavalibv2.exceptions.BoxUnexpectedStatus;
import com.box.boxjavalibv2.filetransfer.BoxFileDownload;
import com.box.boxjavalibv2.filetransfer.BoxFileUpload;
import com.box.boxjavalibv2.filetransfer.IFileTransferListener;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.DeleteFileRequest;
import com.box.boxjavalibv2.requests.GetFileCommentsRequest;
import com.box.boxjavalibv2.requests.GetFileVersionsRequest;
import com.box.boxjavalibv2.requests.GetPreviewRequest;
import com.box.boxjavalibv2.requests.ThumbnailRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxFileRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxImageRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemCopyRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.boxjavalibv2.responseparsers.ErrorResponseParser;
import com.box.boxjavalibv2.responseparsers.PreviewResponseParser;
import com.box.boxjavalibv2.responseparsers.ThumbnailResponseParser;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.requestsbase.BoxFileUploadRequestObject;
import com.box.restclientv2.responseparsers.DefaultFileResponseParser;
import com.box.restclientv2.responses.DefaultBoxResponse;

public class BoxFilesManagerImpl extends BoxItemsManagerImpl implements IBoxFilesManager {

    /**
     * Constructor.
     * 
     * @param config
     *            BoxConfig
     * @param resourceHub
     *            resource hub
     * @param parser
     *            json parser
     * @param auth
     *            auth for api calls
     * @param restClient
     *            REST client to make api calls.
     */
    public BoxFilesManagerImpl(IBoxConfig config, final IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public BoxFile getFile(final String fileId, final BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFile) super.getItem(fileId, requestObject, BoxResourceType.FILE);
    }

    @Override
    public void deleteFile(final String fileId, final BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        DeleteFileRequest request = new DeleteFileRequest(getConfig(), getJSONParser(), fileId, requestObject);
        executeRequestWithNoResponseBody(request);
    }

    @Override
    public BoxPreview getPreview(final String fileId, final String extension, final BoxImageRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        GetPreviewRequest request = new GetPreviewRequest(getConfig(), getJSONParser(), fileId, extension, requestObject);
        request.setAuth(getAuth());
        DefaultBoxResponse response = (DefaultBoxResponse) getRestClient().execute(request);
        PreviewResponseParser parser = new PreviewResponseParser();
        ErrorResponseParser errorParser = new ErrorResponseParser(getJSONParser());
        Object result = response.parseResponse(parser, errorParser);

        return (BoxPreview) tryCastObject(BoxResourceType.PREVIEW, result);
    }

    @Override
    public BoxThumbnail getThumbnail(final String fileId, final String extension, final BoxImageRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        ThumbnailRequest request = new ThumbnailRequest(getConfig(), getJSONParser(), fileId, extension, requestObject);
        request.setAuth(getAuth());
        DefaultBoxResponse response = (DefaultBoxResponse) getRestClient().execute(request);
        ThumbnailResponseParser parser = new ThumbnailResponseParser();
        ErrorResponseParser errorParser = new ErrorResponseParser(getJSONParser());
        Object result = response.parseResponse(parser, errorParser);
        return (BoxThumbnail) tryCastObject(BoxResourceType.THUMBNAIL, result);
    }

    @Override
    @Deprecated
    public InputStream downloadThumbnail(final String fileId, final String extension, final BoxImageRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        ThumbnailRequest request = new ThumbnailRequest(getConfig(), getJSONParser(), fileId, extension, requestObject);
        request.setAuth(getAuth());
        DefaultBoxResponse response = (DefaultBoxResponse) getRestClient().execute(request);
        if (response.getResponseStatusCode() != request.getExpectedResponseCode()) {
            ErrorResponseParser errorParser = new ErrorResponseParser(getJSONParser());
            Object o = errorParser.parse(response);
            if (o instanceof BoxServerError) {
                if (o instanceof BoxUnexpectedStatus) {
                    throw new BoxUnexpectedHttpStatusException((BoxUnexpectedStatus) o);
                }
                else {
                    throw new BoxServerException((BoxServerError) o);
                }
            }
        }
        return (InputStream) (new DefaultFileResponseParser()).parse(response);
    }

    @Override
    public BoxFile uploadFile(final BoxFileUploadRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException,
        InterruptedException {
        BoxFileUpload upload = new BoxFileUpload(getConfig());
        return upload.execute(this, requestObject);
    }

    @Override
    public BoxFile copyFile(final String fileId, final BoxItemCopyRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFile) super.copyItem(fileId, requestObject, BoxResourceType.FILE);
    }

    @Override
    public void downloadFile(final String fileId, final File destination, final IFileTransferListener listener, BoxDefaultRequestObject requestObject)
        throws BoxRestException, BoxServerException, IllegalStateException, IOException, InterruptedException, AuthFatalFailureException {
        BoxFileDownload download = new BoxFileDownload(getConfig(), getRestClient(), fileId);
        download.setProgressListener(listener);
        download.execute(getAuth(), destination, getJSONParser(), requestObject);
    }

    @Override
    public InputStream downloadFile(final String fileId, final BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        BoxFileDownload download = new BoxFileDownload(getConfig(), getRestClient(), fileId);
        return download.execute(getAuth(), getJSONParser(), requestObject);
    }

    @Override
    public void downloadFile(final String fileId, final OutputStream[] outputStreams, final IFileTransferListener listener,
        final BoxDefaultRequestObject requestObject) throws BoxRestException, IOException, BoxServerException, InterruptedException, AuthFatalFailureException {
        BoxFileDownload download = new BoxFileDownload(getConfig(), getRestClient(), fileId);
        download.setProgressListener(listener);
        download.execute(getAuth(), outputStreams, getJSONParser(), requestObject);
    }

    @Override
    public BoxFile uploadNewVersion(final String fileId, final BoxFileUploadRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException, InterruptedException {
        BoxFileUpload upload = new BoxFileUpload(getConfig());
        return upload.execute(fileId, this, requestObject);
    }

    @Override
    public List<BoxFileVersion> getFileVersions(final String fileId, final BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        GetFileVersionsRequest request = new GetFileVersionsRequest(getConfig(), getJSONParser(), fileId, requestObject);
        BoxCollection collection = (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.FILE_VERSIONS, getJSONParser());
        return getFileVersions(collection);
    }

    @Override
    public BoxFile updateFileInfo(final String fileId, final BoxFileRequestObject requestObject) throws UnsupportedEncodingException, BoxRestException,
        BoxServerException, AuthFatalFailureException {
        return (BoxFile) super.updateItemInfo(fileId, requestObject, BoxResourceType.FILE);
    }

    @Override
    public BoxFile createSharedLink(final String fileId, BoxSharedLinkRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFile) super.createSharedLink(fileId, requestObject, BoxResourceType.FILE);
    }

    @Override
    public BoxCollection getFileComments(final String fileId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        GetFileCommentsRequest request = new GetFileCommentsRequest(getConfig(), getJSONParser(), fileId, requestObject);
        return (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.COMMENTS, getJSONParser());
    }

    /**
     * Get files in a collection.Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return list of files
     */
    @Deprecated
    public static List<BoxFile> getFiles(BoxCollection collection) {
        List<BoxFile> files = new ArrayList<BoxFile>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxFile) {
                files.add((BoxFile) object);
            }
        }
        return files;
    }

    /**
     * Get file versions in a collection. Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return list of file versions
     */
    @Deprecated
    public static List<BoxFileVersion> getFileVersions(BoxCollection collection) {
        List<BoxFileVersion> files = new ArrayList<BoxFileVersion>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxFileVersion) {
                files.add((BoxFileVersion) object);
            }
        }
        return files;
    }
}
