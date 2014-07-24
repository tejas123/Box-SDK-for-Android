package com.box.boxjavalibv2.resourcemanagers;

import java.util.ArrayList;
import java.util.List;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxComment;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.dao.IBoxType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.CreateCommentRequest;
import com.box.boxjavalibv2.requests.DeleteCommentRequest;
import com.box.boxjavalibv2.requests.GetCommentRequest;
import com.box.boxjavalibv2.requests.UpdateCommentRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxCommentRequestObject;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

/**
 * Use this class to execute requests <b>synchronously</b> against the Box REST API(V2), comments endpints. Full details about the Box API can be found at <a
 * href="http://developers.box.com/docs">http://developers.box.com/docs</a> . You must have an OpenBox application with a valid API key to use the Box API. All
 * methods in this class are executed in the invoking thread, and therefore are NOT safe to execute in the UI thread of your application. You should only use
 * this class if you already have worker threads or AsyncTasks that you want to incorporate the Box API into.
 */
public final class BoxCommentsManagerImpl extends AbstractBoxResourceManager implements IBoxCommentsManager {

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
    public BoxCommentsManagerImpl(IBoxConfig config, final IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public BoxComment addComment(final BoxCommentRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        CreateCommentRequest request = new CreateCommentRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxComment) getResponseAndParseAndTryCast(request, BoxResourceType.COMMENT, getJSONParser());
    }

    @Override
    public BoxComment addComment(String commentedItemId, IBoxType commentedItemType, String message) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        BoxCommentRequestObject obj = BoxCommentRequestObject.addCommentRequestObject(commentedItemType, commentedItemId, message);
        return addComment(obj);
    }

    @Override
    public BoxComment getComment(final String commentId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        GetCommentRequest request = new GetCommentRequest(getConfig(), getJSONParser(), commentId, requestObject);
        return (BoxComment) getResponseAndParseAndTryCast(request, BoxResourceType.COMMENT, getJSONParser());
    }

    @Override
    public BoxComment updateComment(final String commentId, final BoxCommentRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        UpdateCommentRequest request = new UpdateCommentRequest(getConfig(), getJSONParser(), commentId, requestObject);
        return (BoxComment) getResponseAndParseAndTryCast(request, BoxResourceType.COMMENT, getJSONParser());
    }

    @Override
    public void deleteComment(final String commentId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        DeleteCommentRequest request = new DeleteCommentRequest(getConfig(), getJSONParser(), commentId, requestObject);
        executeRequestWithNoResponseBody(request);
    }

    /**
     * Get comments from a collection. Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return comments
     */
    @Deprecated
    public static List<BoxComment> getComments(BoxCollection collection) {
        List<BoxComment> comments = new ArrayList<BoxComment>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxComment) {
                comments.add((BoxComment) object);
            }
        }
        return comments;
    }
}
