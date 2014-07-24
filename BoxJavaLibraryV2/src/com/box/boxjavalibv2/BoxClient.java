package com.box.boxjavalibv2;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.NotImplementedException;

import com.box.boxjavalibv2.BoxConnectionManagerBuilder.BoxConnectionManager;
import com.box.boxjavalibv2.authorization.IAuthDataController;
import com.box.boxjavalibv2.authorization.IAuthEvent;
import com.box.boxjavalibv2.authorization.IAuthFlowListener;
import com.box.boxjavalibv2.authorization.IAuthFlowMessage;
import com.box.boxjavalibv2.authorization.IAuthFlowUI;
import com.box.boxjavalibv2.authorization.IAuthSecureStorage;
import com.box.boxjavalibv2.authorization.OAuthAuthorization;
import com.box.boxjavalibv2.authorization.OAuthDataController;
import com.box.boxjavalibv2.authorization.OAuthDataController.OAuthTokenState;
import com.box.boxjavalibv2.authorization.OAuthRefreshListener;
import com.box.boxjavalibv2.authorization.SharedLinkAuthorization;
import com.box.boxjavalibv2.dao.BoxBase;
import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.IAuthData;
import com.box.boxjavalibv2.events.OAuthEvent;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.jsonparsing.BoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.BoxResourceHub;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.jsonparsing.IBoxResourceHub;
import com.box.boxjavalibv2.resourcemanagers.BoxCollaborationsManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxCommentsManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxEventsManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxFilesManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxFoldersManageImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxGroupsManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxItemsManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxOAuthManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxSearchManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxSharedItemsManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxTrashManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.BoxUsersManagerImpl;
import com.box.boxjavalibv2.resourcemanagers.IBoxCollaborationsManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxCommentsManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxEventsManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxFilesManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxFoldersManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxGroupsManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxItemsManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxOAuthManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxResourceManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxSearchManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxSharedItemsManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxTrashManager;
import com.box.boxjavalibv2.resourcemanagers.IBoxUsersManager;
import com.box.boxjavalibv2.resourcemanagers.IPluginResourceManagerBuilder;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;

/**
 * This is the main entrance of the sdk. The client contains all resource managers and also handles authentication. Make sure you call authenticate method
 * before making any api calls. you can use the resource managers to execute requests <b>synchronously</b> against the Box REST API(V2). Full details about the
 * Box API can be found at {@see <a href="http://developers.box.com/docs">http://developers.box.com/docs</a>} . You must have an OpenBox application with a
 * valid API key to use the Box API. All methods in this class are executed in the invoking thread, and therefore are NOT safe to execute in the UI thread of
 * your application. You should only use this class if you already have worker threads or AsyncTasks that you want to incorporate the Box API into.
 */
public class BoxClient extends BoxBase implements IAuthFlowListener {

    private final static boolean DEFAULT_AUTO_REFRESH = true;

    private final IAuthDataController authController;
    private final IBoxRequestAuth auth;

    private final IBoxResourceHub resourceHub;
    private final IBoxJSONParser jsonParser;
    private final IBoxRESTClient restClient;
    private final IBoxConfig config;

    private final IBoxFilesManager filesManager;
    private final IBoxFoldersManager foldersManager;
    private final IBoxItemsManager boxItemsManager;
    private final IBoxSearchManager searchManager;
    private final IBoxEventsManager eventsManager;
    private final IBoxCollaborationsManager collaborationsManager;
    private final IBoxCommentsManager commentsManager;
    private final IBoxUsersManager usersManager;
    private final IBoxOAuthManager oauthManager;
    private final IBoxGroupsManager groupsManager;
    private final IBoxTrashManager trashManager;
    private final Map<String, IBoxResourceManager> pluginResourceManagers = new HashMap<String, IBoxResourceManager>();

    /**
     * This constructor has some connection parameters. They are used to periodically close idle connections that HttpClient opens.
     * 
     * @param clientId
     *            client id, you can get it from dev console.
     * @param clientSecret
     *            client secret, you can get it from dev console.
     * @param hub
     *            resource hub. use null if you want to use default one.
     * @param parser
     *            json parser, use null if you want to use default one(Jackson).
     * @param config
     *            BoxConfig. User BoxConfigBuilder to build. Normally you only need default config: (new BoxConfigBuilder()).build()
     * @param connectionManager
     *            BoxConnectionManager. Normally you only need default connection manager: (new BoxConnectionManagerBuilder()).build()
     */
    public BoxClient(final String clientId, final String clientSecret, final IBoxResourceHub hub, final IBoxJSONParser parser, final IBoxConfig config,
        final BoxConnectionManager connectionManager) {
        this(clientId, clientSecret, hub, parser, createMonitoredRestClient(connectionManager), config);
    }

    /**
     * @param clientId
     *            client id
     * @param clientSecret
     *            client secret
     * @param hub
     *            resource hub, use null for default resource hub.
     * @param parser
     *            json parser, use null for default parser.
     * @param config
     *            BoxConfig. User BoxConfigBuilder to build. Normally you only need default config: (new BoxConfigBuilder()).build()
     */
    public BoxClient(final String clientId, final String clientSecret, final IBoxResourceHub hub, final IBoxJSONParser parser, final IBoxConfig config) {
        this(clientId, clientSecret, hub, parser, createRestClient(), config);
    }

    /**
     * @param clientId
     *            client id
     * @param clientSecret
     *            client secret
     * @param hub
     *            resource hub, use null for default resource hub.
     * @param parser
     *            json parser, use null for default parser.
     * @param restClient
     *            rest client
     * @param config
     *            BoxConfig. User BoxConfigBuilder to build. Normally you only need default config: (new BoxConfigBuilder()).build()
     */
    public BoxClient(final String clientId, final String clientSecret, final IBoxResourceHub hub, final IBoxJSONParser parser, final IBoxRESTClient restClient,
        final IBoxConfig config) {
        this.resourceHub = hub == null ? createResourceHub() : hub;
        this.jsonParser = parser == null ? createJSONParser(resourceHub) : parser;
        this.restClient = restClient;
        this.config = config == null ? (new BoxConfigBuilder()).build() : config;
        authController = createAuthDataController(clientId, clientSecret);
        auth = createAuthorization(authController);

        boxItemsManager = new BoxItemsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        filesManager = new BoxFilesManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        foldersManager = new BoxFoldersManageImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        searchManager = new BoxSearchManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        eventsManager = new BoxEventsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        collaborationsManager = new BoxCollaborationsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        commentsManager = new BoxCommentsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        usersManager = new BoxUsersManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        oauthManager = new BoxOAuthManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getRestClient());
        groupsManager = new BoxGroupsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        trashManager = new BoxTrashManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
    }

    @Deprecated
    public BoxClient(final String clientId, final String clientSecret, final IBoxConfig config) {
        this(clientId, clientSecret, null, null, config);
    }

    /**
     * Plug in a resource manager into sdk client. The plugged in resource manager can be retrieved by getPluginManager(key).
     * 
     * @param key
     *            key of the resource manager. The key is used to retrieve the plugged in resource manager.
     * @param builder
     *            builder of the resource manager, the builder interface confines the base building blocks of the plugged in resource manager.
     * @return The plugged in resource manager.
     */
    public IBoxResourceManager pluginResourceManager(String key, IPluginResourceManagerBuilder builder) {
        IBoxResourceManager manager = builder.build(getConfig(), getResourceHub(), getJSONParser(), getAuth(), getRestClient());
        pluginResourceManagers.put(key, manager);
        return manager;
    }

    /**
     * Whether this client is authenticated.
     * 
     * @return
     */
    public boolean isAuthenticated() {
        try {
            return getOAuthDataController().getTokenState() != OAuthTokenState.FAIL && getAuthData() != null;
        }
        catch (AuthFatalFailureException e) {
            return false;
        }
    }

    /**
     * Makes OAuth auto refresh itself when token expires. By default, this is set to true. Note if autorefresh fails, it's not going to try refresh again.
     * 
     * @param autoRefresh
     */
    public void setAutoRefreshOAuth(boolean autoRefresh) {
        getOAuthDataController().setAutoRefreshOAuth(autoRefresh);
    }

    /**
     * Set whether we want the connection to keep open (and reused) after an api call.
     * 
     * @param connectionOpen
     *            true if we want the connection to remain open and reused for another api call.
     */
    public void setConnectionOpen(final boolean connectionOpen) {
        ((BoxRESTClient) getRestClient()).setConnectionOpen(connectionOpen);
    }

    /**
     * Set connection time out.
     * 
     * @param timeOut
     */
    public void setConnectionTimeOut(final int timeOut) {
        ((BoxRESTClient) getRestClient()).setConnectionTimeOut(timeOut);
    }

    /**
     * Get the OAuthDataController that controls OAuth data.
     */
    public OAuthDataController getOAuthDataController() {
        return (OAuthDataController) authController;
    }

    /**
     * Add a listener to listen to OAuth refresh events. This is important because every time OAuth token refreshes, a new refresh/access token pair will be
     * generated. If your app is storing the token pairs, it needs to get rid of the stale pair store the updated pair.
     * 
     * @param listener
     */
    public void addOAuthRefreshListener(OAuthRefreshListener listener) {
        getOAuthDataController().addOAuthRefreshListener(listener);
    }

    /**
     * Get the OAuth data.
     * 
     * @return
     * @throws AuthFatalFailureException
     */
    public BoxOAuthToken getAuthData() throws AuthFatalFailureException {
        return getOAuthDataController().getAuthData();
    }

    /**
     * Save auth in a customized secure storage.
     * 
     * @param storage
     * @throws AuthFatalFailureException
     */
    public void saveAuth(IAuthSecureStorage storage) throws AuthFatalFailureException {
        storage.saveAuth(getAuthData());
    }

    /**
     * Authenticate from the auth object stored in the secure storage.
     * 
     * @param storage
     */
    public void authenticateFromSecureStorage(IAuthSecureStorage storage) {
        authenticate(storage.getAuth());
    }

    /**
     * Get the BoxFilesManager, which can be used to make API calls on files endpoints. Note this files manager only work on the folders you own. if you are
     * trying to make api calls on a shared file (file shared to you via shared link), please use getSharedFilesManager().
     * 
     * @return the filesManager
     */
    public IBoxFilesManager getFilesManager() {
        return filesManager;
    }

    /**
     * Get the BoxItemsManager, which can be used to make API calls on files/folders endpoints. Note this files manager only work on the files/folders you own.
     * if you are trying to make api calls on a shared file (file shared to you via shared link), please use getSharedBoxItemsManager(). In general this is a
     * convenient resource manager when you make api calls for a BoxItem without knowing whether it's a BoxFile or BoxFolder.
     * 
     * @return the boxItemsManager
     */
    public IBoxItemsManager getBoxItemsManager() {
        return boxItemsManager;
    }

    /**
     * Get the BoxItemsManager for items(files/folders) shared to you, this can be used to make API calls on files/folders endpoints. Note this is different
     * from getSharedItemsManager(), getSharedItemsManager is used for api calls on sharedItems endpoints.
     * 
     * @param sharedLink
     *            shared link
     * @param password
     *            use null if no password required.
     * @return
     */
    public IBoxItemsManager getSharedBoxItemsManager(String sharedLink, String password) {
        return new BoxItemsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getSharedItemAuth(sharedLink, password), getRestClient());
    }

    /**
     * Get the BoxItemsManager, which can be used to make API calls on files/folders endpoints for trashed files/folders.
     * 
     * @return the boxItemsManager
     */
    public IBoxTrashManager getTrashManager() {
        return trashManager;
    }

    /**
     * Get the OAuthManager, which can be used to make OAuth related api calls.
     * 
     * @return
     */
    public IBoxOAuthManager getOAuthManager() {
        return oauthManager;
    }

    /**
     * Get the BoxGroupsManager, which can be used to make API calls on groups endpoints.
     */
    public IBoxGroupsManager getGroupsManager() {
        return groupsManager;
    }

    /**
     * Get Shared Items manager, which can be used to make API calls on shared item endpoints.
     * 
     * @param sharedLink
     *            shared link
     * @param password
     *            password
     * @return BoxSharedItemsManager
     */
    public IBoxSharedItemsManager getSharedItemsManager(String sharedLink, String password) {
        return new BoxSharedItemsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getSharedItemAuth(sharedLink, password), getRestClient());
    }

    /**
     * Get the BoxFilesManager for shared items, which can be used to make API calls on files endpoints for a shared item.
     * 
     * @param sharedLink
     *            shared link.
     * @param password
     *            password of the shared link, use null if there is no password
     * @return BoxFilesManager
     */
    public IBoxFilesManager getSharedFilesManager(String sharedLink, String password) {
        return new BoxFilesManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getSharedItemAuth(sharedLink, password), getRestClient());
    }

    /**
     * Get the BoxFoldersManager for shared items, which can be used to make API calls on folders endpoints for a shared item.
     * 
     * @param sharedLink
     *            shared link.
     * @param password
     *            password of the shared link, use null if there is no password
     * @return BoxFoldersManager
     */
    public IBoxFoldersManager getSharedFoldersManager(String sharedLink, String password) {
        return new BoxFoldersManageImpl(getConfig(), getResourceHub(), getJSONParser(), getSharedItemAuth(sharedLink, password), getRestClient());
    }

    /**
     * Get the BoxCommentsManager for shared items, which can be used to make API calls on comments endpoints for a shared item.
     * 
     * @param sharedLink
     *            shared link.
     * @param password
     *            password of the shared link, use null if there is no password
     * @return BoxFoldersManager
     */
    public IBoxCommentsManager getSharedCommentsManager(String sharedLink, String password) {
        return new BoxCommentsManagerImpl(getConfig(), getResourceHub(), getJSONParser(), getSharedItemAuth(sharedLink, password), getRestClient());
    }

    /**
     * A generic way to get a resourceManager with shared link auth. Currently only supports file, folder and comment endpoints.
     * 
     * @param type
     * @param sharedLink
     * @param password
     * @return
     */
    public IBoxResourceManager getResourceManagerWithSharedLinkAuth(BoxResourceType type, String sharedLink, String password) {
        switch (type) {
            case FILE:
                return getSharedFilesManager(sharedLink, password);
            case FOLDER:
                return getSharedFoldersManager(sharedLink, password);
            case COMMENT:
                return getSharedCommentsManager(sharedLink, password);
            default:
                throw new NotImplementedException();
        }
    }

    /**
     * Get a resource manager plugged in based on the key.
     */
    public IBoxResourceManager getPluginManager(String pluginManagerKey) {
        return this.pluginResourceManagers.get(pluginManagerKey);
    }

    /**
     * @return the BoxFoldersManager, which can be used to make API calls on folders endpoints. Note this folders manager only work on the folders you own. if
     *         you are trying to make api calls on a shared folder (folder shared to you via shared link), please use getSharedFoldersManager().
     */
    public IBoxFoldersManager getFoldersManager() {
        return foldersManager;
    }

    /**
     * @return BoxSearchManager through which searches can be performed.
     */
    public IBoxSearchManager getSearchManager() {
        return searchManager;
    }

    /**
     * 
     * @return BoxEventsManager through which the Box Events API can be queried.
     */
    public IBoxEventsManager getEventsManager() {
        return eventsManager;
    }

    /**
     * @return the collaborationsManager
     */
    public IBoxCollaborationsManager getCollaborationsManager() {
        return collaborationsManager;
    }

    /**
     * @return the commentsManager
     */
    public IBoxCommentsManager getCommentsManager() {
        return commentsManager;
    }

    /**
     * @return the usersManager
     */
    public IBoxUsersManager getUsersManager() {
        return usersManager;
    }

    /**
     * Get authenticated using a Auth object, this could be a previously stored data.
     * 
     * @param authData
     */
    public synchronized void authenticate(IAuthData authData) {
        OAuthDataController oauthController = getOAuthDataController();
        oauthController.setOAuthData((BoxOAuthToken) authData);
        if (authData != null) {
            oauthController.setTokenState(OAuthTokenState.AVAILABLE);
        }
        else {
            oauthController.setTokenState(OAuthTokenState.PRE_CREATION);
        }
    }

    /**
     * Get authenticated. Note authentication is done asynchronously and may not finish right after this method. The authentication result should be notified to
     * the IAuthFlowListener parameter.
     * 
     * @param authFlowUI
     *            UI for the auth(OAuth) flow.
     * @param autoRefreshOAuth
     *            whether the OAuth token should be auto refreshed when it expires.
     * @param listener
     *            listener listening to the auth flow events.
     */
    public void authenticate(IAuthFlowUI authFlowUI, boolean autoRefreshOAuth, IAuthFlowListener listener) {
        authFlowUI.addAuthFlowListener(listener);
        authFlowUI.authenticate(this);
    }

    @Override
    public void onAuthFlowEvent(IAuthEvent event, IAuthFlowMessage message) {
        OAuthEvent oe = (OAuthEvent) event;
        if (oe == OAuthEvent.OAUTH_CREATED) {
            ((OAuthAuthorization) getAuth()).setOAuthData(getOAuthTokenFromMessage(message));
        }
    }

    /**
     * Check authentication state.
     * 
     * @return authentication state
     */
    public OAuthTokenState getAuthState() {
        return getOAuthDataController().getTokenState();
    }

    /**
     * Get config.
     * 
     * @return config
     */
    public IBoxConfig getConfig() {
        return config;
    }

    /**
     * Create a resource hub
     * 
     * @return IBoxResourceHub
     */
    protected IBoxResourceHub createResourceHub() {
        return new BoxResourceHub();
    }

    /**
     * Create a json parser.
     * 
     * @param resourceHub
     * @return
     */
    protected IBoxJSONParser createJSONParser(IBoxResourceHub resourceHub) {
        return new BoxJSONParser(resourceHub);
    }

    /**
     * Get resource hub.
     * 
     * @return Resource hub
     */
    public IBoxResourceHub getResourceHub() {
        return this.resourceHub;
    }

    public IBoxJSONParser getJSONParser() {
        return this.jsonParser;
    }

    /**
     * Get rest client.
     * 
     * @return
     */
    protected IBoxRESTClient getRestClient() {
        return this.restClient;
    }

    /**
     * Create a REST client to make api calls.
     * 
     * @return IBoxRESTClient
     */
    protected static IBoxRESTClient createRestClient() {
        return new BoxRESTClient();
    }

    protected static IBoxRESTClient createMonitoredRestClient(final BoxConnectionManager connectionManager) {
        return new BoxRESTClient(connectionManager);
    }

    /**
     * Get the authorization needed for shared items.
     * 
     * @param sharedLink
     *            shared link
     * @param password
     *            password(use null if no password at all)
     * @return IBoxRequestAuth
     */
    public IBoxRequestAuth getSharedItemAuth(String sharedLink, String password) {
        return new SharedLinkAuthorization((OAuthAuthorization) getAuth(), sharedLink, password);
    }

    /**
     * Create auth data controller, which maintains auth data and handles auth refresh.
     */
    protected IAuthDataController createAuthDataController(final String clientId, final String clientSecret) {
        return new OAuthDataController(this, clientId, clientSecret, DEFAULT_AUTO_REFRESH);
    }

    /**
     * From auth data controller, generate IBoxRequestAuth, which can be applied to api requests.
     */
    protected IBoxRequestAuth createAuthorization(IAuthDataController controller) {
        return new OAuthAuthorization((OAuthDataController) authController);
    }

    /**
     * Get the auth object used to make api calls.
     * 
     * @return
     */
    public IBoxRequestAuth getAuth() {
        return auth;
    }

    @Override
    public void onAuthFlowMessage(IAuthFlowMessage message) {
    }

    @Override
    public void onAuthFlowException(Exception e) {
    }

    protected BoxOAuthToken getOAuthTokenFromMessage(IAuthFlowMessage message) {
        return (BoxOAuthToken) message.getData();
    }
}
