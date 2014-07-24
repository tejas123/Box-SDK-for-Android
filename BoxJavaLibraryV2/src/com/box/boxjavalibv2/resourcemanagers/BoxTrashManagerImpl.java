package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.DeleteTrashItemRequest;
import com.box.boxjavalibv2.requests.GetFolderTrashItemsRequest;
import com.box.boxjavalibv2.requests.GetTrashItemRequest;
import com.box.boxjavalibv2.requests.RestoreTrashItemRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemRestoreRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxTrashManagerImpl extends AbstractBoxResourceManager implements IBoxTrashManager {

    public BoxTrashManagerImpl(IBoxConfig config, IBoxResourceHub resourceHub, IBoxJSONParser parser, IBoxRequestAuth auth, IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public void deleteTrashFile(final String id, final BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        deleteTrashItem(id, BoxResourceType.FILE, requestObject);
    }

    @Override
    public BoxFile restoreTrashFile(final String id, final BoxItemRestoreRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException {
        return (BoxFile) restoreTrashItem(id, BoxResourceType.FILE, requestObject);
    }

    @Override
    public BoxFile getTrashFile(final String fileId, final BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException {
        return (BoxFile) getTrashItem(fileId, BoxResourceType.FILE, requestObject);
    }

    @Override
    public BoxFolder getTrashFolder(final String folderId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFolder) getTrashItem(folderId, BoxResourceType.FOLDER, requestObject);
    }

    @Override
    public BoxCollection getTrashItems(BoxPagingRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        GetFolderTrashItemsRequest request = new GetFolderTrashItemsRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.ITEMS, getJSONParser());
    }

    @Override
    public void deleteTrashFolder(final String id, final BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        deleteTrashItem(id, BoxResourceType.FOLDER, requestObject);
    }

    @Override
    public BoxFolder restoreTrashFolder(final String id, final BoxItemRestoreRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException {
        return (BoxFolder) restoreTrashItem(id, BoxResourceType.FOLDER, requestObject);
    }

    private BoxItem getTrashItem(final String itemId, final BoxResourceType type, final BoxDefaultRequestObject requestObject) throws BoxRestException,
        AuthFatalFailureException, BoxServerException {
        GetTrashItemRequest request = new GetTrashItemRequest(getConfig(), getJSONParser(), itemId, type, requestObject);
        Object result = getResponseAndParse(request, type, getJSONParser());
        return (BoxItem) tryCastBoxItem(type, result);
    }

    private void deleteTrashItem(final String id, final BoxResourceType type, final BoxDefaultRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        DeleteTrashItemRequest request = new DeleteTrashItemRequest(getConfig(), getJSONParser(), id, type, requestObject);
        executeRequestWithNoResponseBody(request);
    }

    private BoxItem restoreTrashItem(final String id, final BoxResourceType type, final BoxItemRestoreRequestObject requestObject) throws BoxRestException,
        AuthFatalFailureException, BoxServerException {
        RestoreTrashItemRequest request = new RestoreTrashItemRequest(getConfig(), getJSONParser(), id, type, requestObject);
        return (BoxItem) getResponseAndParseAndTryCast(request, type, getJSONParser());
    }
}
