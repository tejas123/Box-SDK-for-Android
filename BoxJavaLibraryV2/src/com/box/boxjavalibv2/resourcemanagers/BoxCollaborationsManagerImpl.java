package com.box.boxjavalibv2.resourcemanagers;

import java.util.ArrayList;
import java.util.List;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollaboration;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.CreateCollaborationRequest;
import com.box.boxjavalibv2.requests.DeleteCollaborationRequest;
import com.box.boxjavalibv2.requests.GetAllCollaborationsRequest;
import com.box.boxjavalibv2.requests.GetCollaborationRequest;
import com.box.boxjavalibv2.requests.UpdateCollaborationRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxCollabRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxGetAllCollabsRequestObject;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

/**
 * Use this class to execute requests <b>synchronously</b> against the Box REST API(V2), collaborations endpints. Full details about the Box API can be found at
 * <a href="http://developers.box.com/docs">http://developers.box.com/docs</a> . You must have an OpenBox application with a valid API key to use the Box API.
 * All methods in this class are executed in the invoking thread, and therefore are NOT safe to execute in the UI thread of your application. You should only
 * use this class if you already have worker threads or AsyncTasks that you want to incorporate the Box API into.
 */
public final class BoxCollaborationsManagerImpl extends AbstractBoxResourceManager implements IBoxCollaborationsManager {

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
    public BoxCollaborationsManagerImpl(IBoxConfig config, final IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public BoxCollaboration getCollaboration(final String collabId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        GetCollaborationRequest request = new GetCollaborationRequest(getConfig(), getJSONParser(), collabId, requestObject);

        return (BoxCollaboration) getResponseAndParseAndTryCast(request, BoxResourceType.COLLABORATION, getJSONParser());
    }

    @Override
    public BoxCollaboration createCollaboration(final String folderId, final BoxCollabRequestObject collabObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        CreateCollaborationRequest request = new CreateCollaborationRequest(getConfig(), getJSONParser(), folderId, collabObject);

        return (BoxCollaboration) getResponseAndParseAndTryCast(request, BoxResourceType.COLLABORATION, getJSONParser());
    }

    @Override
    public List<BoxCollaboration> getAllCollaborations(final BoxGetAllCollabsRequestObject collabObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        GetAllCollaborationsRequest request = new GetAllCollaborationsRequest(getConfig(), getJSONParser(), collabObject);

        BoxCollection collection = (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.COLLABORATIONS, getJSONParser());
        return getCollaborations(collection);
    }

    @Override
    public void deleteCollaboration(final String collabId, BoxDefaultRequestObject requestObject) throws BoxServerException, BoxRestException,
        AuthFatalFailureException {
        DeleteCollaborationRequest request = new DeleteCollaborationRequest(getConfig(), getJSONParser(), collabId, requestObject);
        executeRequestWithNoResponseBody(request);
    }

    @Override
    public BoxCollaboration updateCollaboration(final String collabId, final BoxCollabRequestObject requestObject) throws BoxRestException,
        AuthFatalFailureException, BoxServerException {
        UpdateCollaborationRequest request = new UpdateCollaborationRequest(getConfig(), getJSONParser(), collabId, requestObject);
        return (BoxCollaboration) super.getResponseAndParseAndTryCast(request, BoxResourceType.COLLABORATION, getJSONParser());
    }

    /**
     * Get collaborations from a collection.Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return collaborations
     */
    @Deprecated
    public static List<BoxCollaboration> getCollaborations(BoxCollection collection) {
        List<BoxCollaboration> collabs = new ArrayList<BoxCollaboration>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxCollaboration) {
                collabs.add((BoxCollaboration) object);
            }
        }
        return collabs;
    }
}
