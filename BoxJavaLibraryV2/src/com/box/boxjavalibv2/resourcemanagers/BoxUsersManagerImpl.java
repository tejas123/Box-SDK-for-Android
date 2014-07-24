package com.box.boxjavalibv2.resourcemanagers;

import java.util.ArrayList;
import java.util.List;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxEmailAlias;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.requests.CreateEmailAliasRequest;
import com.box.boxjavalibv2.requests.CreateEnterpriseUserRequest;
import com.box.boxjavalibv2.requests.DeleteEmailAliasRequest;
import com.box.boxjavalibv2.requests.DeleteUserRequest;
import com.box.boxjavalibv2.requests.GetAllUsersInEnterpriseRequest;
import com.box.boxjavalibv2.requests.GetCurrentUserRequest;
import com.box.boxjavalibv2.requests.GetEmailAliasesRequest;
import com.box.boxjavalibv2.requests.MoveFolderToAnotherUserRequest;
import com.box.boxjavalibv2.requests.UpdateUserLoginRequest;
import com.box.boxjavalibv2.requests.UpdateUserRequest;
import com.box.boxjavalibv2.requests.requestobjects.BoxEmailAliasRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSimpleUserRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserDeleteRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserUpdateLoginRequestObject;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

/**
 * Use this class to execute requests <b>synchronously</b> against the Box REST API(V2), users endpints. Full details about the Box API can be found at <a
 * href="http://developers.box.com/docs">http://developers.box.com/docs</a> . You must have an OpenBox application with a valid API key to use the Box API. All
 * methods in this class are executed in the invoking thread, and therefore are NOT safe to execute in the UI thread of your application. You should only use
 * this class if you already have worker threads or AsyncTasks that you want to incorporate the Box API into.
 */
public final class BoxUsersManagerImpl extends AbstractBoxResourceManager implements IBoxUsersManager {

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
    public BoxUsersManagerImpl(IBoxConfig config, final IBoxResourceHub resourceHub, final IBoxJSONParser parser, final IBoxRequestAuth auth,
        final IBoxRESTClient restClient) {
        super(config, resourceHub, parser, auth, restClient);
    }

    @Override
    public BoxUser getCurrentUser(BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        GetCurrentUserRequest request = new GetCurrentUserRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxUser) getResponseAndParseAndTryCast(request, BoxResourceType.USER, getJSONParser());
    }

    @Override
    public List<BoxUser> getAllEnterpriseUser(final BoxDefaultRequestObject requestObject, final String filterTerm) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        GetAllUsersInEnterpriseRequest request = new GetAllUsersInEnterpriseRequest(getConfig(), getJSONParser(), requestObject, filterTerm);
        BoxCollection collection = (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.USERS, getJSONParser());
        return getUsers(collection);
    }

    @Override
    public BoxFolder moveFolderToAnotherUser(final String userId, final String folderId, final BoxSimpleUserRequestObject requestObject)
        throws BoxRestException, BoxServerException, AuthFatalFailureException {
        MoveFolderToAnotherUserRequest request = new MoveFolderToAnotherUserRequest(getConfig(), getJSONParser(), userId, folderId, requestObject);
        return (BoxFolder) getResponseAndParseAndTryCast(request, BoxResourceType.FOLDER, getJSONParser());
    }

    @Override
    public BoxUser createEnterpriseUser(BoxUserRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        CreateEnterpriseUserRequest request = new CreateEnterpriseUserRequest(getConfig(), getJSONParser(), requestObject);
        return (BoxUser) getResponseAndParseAndTryCast(request, BoxResourceType.USER, getJSONParser());
    }

    @Override
    public BoxUser updateUserInformaiton(final String userId, BoxUserRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        UpdateUserRequest request = new UpdateUserRequest(getConfig(), getJSONParser(), userId, requestObject);
        return (BoxUser) getResponseAndParseAndTryCast(request, BoxResourceType.USER, getJSONParser());
    }

    @Override
    public void deleteEnterpriseUser(String userId, BoxUserDeleteRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException {
        DeleteUserRequest request = new DeleteUserRequest(getConfig(), getJSONParser(), userId, requestObject);
        this.executeRequestWithNoResponseBody(request);
    }

    @Override
    public List<BoxEmailAlias> getEmailAliases(final String userId, final BoxDefaultRequestObject requestObject) throws BoxServerException, BoxRestException,
        AuthFatalFailureException {
        GetEmailAliasesRequest request = new GetEmailAliasesRequest(getConfig(), getJSONParser(), userId, requestObject);
        BoxCollection collection = (BoxCollection) getResponseAndParseAndTryCast(request, BoxResourceType.EMAIL_ALIASES, getJSONParser());
        return getEmailAliases(collection);
    }

    @Override
    public BoxEmailAlias addEmailAlias(final String userId, BoxEmailAliasRequestObject requestObject) throws BoxServerException, BoxRestException,
        AuthFatalFailureException {
        CreateEmailAliasRequest request = new CreateEmailAliasRequest(getConfig(), getJSONParser(), userId, requestObject);
        return (BoxEmailAlias) getResponseAndParseAndTryCast(request, BoxResourceType.EMAIL_ALIAS, getJSONParser());
    }

    @Override
    public void deleteEmailAlias(final String userId, final String emailId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException {
        DeleteEmailAliasRequest request = new DeleteEmailAliasRequest(getConfig(), getJSONParser(), userId, emailId, requestObject);
        executeRequestWithNoResponseBody(request);
    }

    @Override
    public BoxUser updateUserPrimaryLogin(final String userId, final BoxUserUpdateLoginRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        UpdateUserLoginRequest request = new UpdateUserLoginRequest(getConfig(), getJSONParser(), userId, requestObject);
        return (BoxUser) getResponseAndParseAndTryCast(request, BoxResourceType.USER, getJSONParser());
    }

    /**
     * Get users from a collection.Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return users
     */
    @Deprecated
    public static List<BoxUser> getUsers(BoxCollection collection) {
        List<BoxUser> users = new ArrayList<BoxUser>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxUser) {
                users.add((BoxUser) object);
            }
        }
        return users;
    }

    /**
     * Get email aliases from a collection.Deprecated, use Utils.getTypedObjects instead.
     * 
     * @param collection
     *            collection
     * @return email aliases
     */
    @Deprecated
    public static List<BoxEmailAlias> getEmailAliases(BoxCollection collection) {
        List<BoxEmailAlias> aliases = new ArrayList<BoxEmailAlias>();
        List<BoxTypedObject> list = collection.getEntries();
        for (BoxTypedObject object : list) {
            if (object instanceof BoxEmailAlias) {
                aliases.add((BoxEmailAlias) object);
            }
        }
        return aliases;
    }
}
