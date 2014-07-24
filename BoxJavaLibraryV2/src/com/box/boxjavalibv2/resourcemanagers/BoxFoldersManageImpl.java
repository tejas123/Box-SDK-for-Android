package com.box.boxjavalibv2.resourcemanagers;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollaboration;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.CreateNewFolderRequest;
import com.box.boxjavalibv2.requests.DeleteFolderRequest;
import com.box.boxjavalibv2.requests.GetFolderCollaborationsRequest;
import com.box.boxjavalibv2.requests.GetFolderItemsRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxFolderDeleteRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxFolderRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemCopyRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSharedLinkRequestObject;
import com.box.boxjavalibv2.utils.Utils;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxFoldersManageImpl extends BoxItemsManagerImpl implements IBoxFoldersManager {

    /**
     * Constructor.
     * 
     * @param config
     *            Config
     * @param resourceHub
     *            IResourceHub
     * @param parser
     *            json parser
     * @param auth
     *            auth for api calls
     * @param restClient
     *            REST client to make api calls.
     */
    public BoxFoldersManageImpl(IBoxConfig config, final IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public BoxFolder getFolder(final String folderId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFolder) super.getItem(folderId, requestObject, BoxResourceType.FOLDER);
    }

    @Override
    public BoxFolder createFolder(BoxFolderRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        CreateNewFolderRequest request = new CreateNewFolderRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxFolder) getResponseAndParseAndTryCast(request, BoxResourceType.FOLDER, getJSONParser());
    }

    @Override
    public void deleteFolder(final String folderId, BoxFolderDeleteRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        DeleteFolderRequest request = new DeleteFolderRequest(getConfig(), getJSONParser(), folderId, requestObject);
        executeRequestWithNoResponseBody(request);
    }

    @Override
    public BoxFolder copyFolder(final String folderId, BoxItemCopyRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFolder) super.copyItem(folderId, requestObject, BoxResourceType.FOLDER);
    }

    @Override
    public BoxCollection getFolderItems(final String folderId, BoxPagingRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        GetFolderItemsRequest request = new GetFolderItemsRequest(getConfig(), getJSONParser(), folderId, requestObject);
        return (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.ITEMS, getJSONParser());
    }

    @Override
    public BoxFolder updateFolderInfo(final String folderId, final BoxFolderRequestObject requestObject) throws UnsupportedEncodingException, BoxRestException,
        BoxServerException, AuthFatalFailureException {
        return (BoxFolder) super.updateItemInfo(folderId, requestObject, BoxResourceType.FOLDER);
    }

    @Override
    public BoxFolder createSharedLink(final String folderId, BoxSharedLinkRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        return (BoxFolder) super.createSharedLink(folderId, requestObject, BoxResourceType.FOLDER);
    }

    @Override
    public List<BoxCollaboration> getFolderCollaborations(final String folderId, BoxDefaultRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        GetFolderCollaborationsRequest request = new GetFolderCollaborationsRequest(getConfig(), getJSONParser(), folderId, requestObject);

        BoxCollection collection = (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.COLLABORATIONS, getJSONParser());
        return Utils.getTypedObjects(collection, BoxCollaboration.class);
    }

    /**
     * Get folders in a collection.Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return list of folders
     */
    @Deprecated
    public static List<BoxFolder> getFolders(BoxCollection collection) {
        List<BoxFolder> folders = new ArrayList<BoxFolder>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxFolder) {
                folders.add((BoxFolder) object);
            }
        }
        return folders;
    }
}
