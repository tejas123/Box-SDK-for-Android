package com.box.boxjavalibv2.authorization;

import com.box.boxjavalibv2.BoxClient;
import com.box.boxjavalibv2.dao.BoxOAuthToken;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.restclientv2.exceptions.BoxRestException;

/**
 * This is the data controller for OAuth, it handles token auto refresh.
 */
public class OAuthDataController implements IAuthDataController {

    public static enum OAuthTokenState {
        PRE_CREATION, AVAILABLE, REFRESHING, FAIL,
    }

    /**
     * Time to wait for lock.
     */
    private static final int WAIT = 200;
    /**
     * Default timeout waiting for lock.
     */
    private static final int WAIT_TIME_OUT = 60000;

    private final BoxClient mClient;
    private final String mClientId;
    private final String mClientSecret;
    private String mDeviceId = null;
    private String mDeviceName = null;
    private volatile BoxOAuthToken mOAuthToken;
    private volatile OAuthTokenState mTokenState = OAuthTokenState.PRE_CREATION;
    private boolean mAutoRefresh;
    private int mWaitTimeOut = WAIT_TIME_OUT;
    private Exception refreshFailException;
    private volatile boolean locked = false;

    private OAuthRefreshListener refreshListener;

  public OAuthDataController(BoxClient boxClient, final String clientId, final String clientSecret, final boolean autoRefresh) {
        this.mClient = boxClient;
        this.mClientId = clientId;
        this.mClientSecret = clientSecret;
        this.mAutoRefresh = autoRefresh;
    }

    /**
     * Makes OAuth auto refresh itself when token expires. Note if autorefresh fails, it's not going to try refresh again.
     * 
     * @param autoRefresh
     */
    public void setAutoRefreshOAuth(boolean autoRefresh) {
        mAutoRefresh = autoRefresh;
    }

    /**
     * Set the timeout for threads waiting for OAuth token refresh.
     * 
     * @param timeout
     */
    public void setWaitTimeOut(int timeout) {
        this.mWaitTimeOut = timeout;
    }

    /**
     * @return the mScheme
     */
    public String getScheme() {
        return mClient.getConfig().getOAuthUrlScheme();
    }

    /**
     * @return the mAuthority
     */
    public String getAuthority() {
        return mClient.getConfig().getOAuthUrlAuthority();
    }

    /**
     * @return the OAuth url path.
     */
    public String getUrlPath() {
        return mClient.getConfig().getOAuthWebUrlPath();
    }

    /**
     * @return the mClientId
     */
    public String getClientId() {
        return mClientId;
    }

    /**
     * @return the mClientSecret
     */
    public String getClientSecret() {
        return mClientSecret;
    }

    public void setOAuthData(BoxOAuthToken token) {
        this.mOAuthToken = token;
    }

    /**
     * Set device id. This is optional.
     * 
     * @param deviceId
     *            device id
     */
    public void setDeviceId(final String deviceId) {
        this.mDeviceId = deviceId;
    }

    /**
     * Set device name. Optional.
     * 
     * @param deviceName
     *            device name
     */
    public void setDeviceName(final String deviceName) {
        this.mDeviceName = deviceName;
    }

    /**
     * @return the TokenState
     */
    public OAuthTokenState getTokenState() {
        return mTokenState;
    }

    /**
     * @param tokenState
     *            the mTokenState to set
     */
    public void setTokenState(OAuthTokenState tokenState) {
        this.mTokenState = tokenState;
    }

    /**
     * @return the refreshFailException
     */
    public Exception getRefreshFailException() {
        return refreshFailException;
    }

    /**
     * @param refreshFailException
     *            the refreshFailException to set
     */
    public void setRefreshFail(Exception refreshFailException) {
        this.refreshFailException = refreshFailException;
        if (refreshFailException != null) {
            setTokenState(OAuthTokenState.FAIL);
        }
    }

    /**
     * Initialize the controller.
     */
    public void initialize() {
        setTokenState(OAuthTokenState.AVAILABLE);
        setRefreshFail(null);
        unlock();
    }

    /**
     * Get OAuthData, counting number of retries, in case of too many retries, throw.
     * 
     * @return OAuthData
     * @throws AuthFatalFailureException
     */
    @Override
    public BoxOAuthToken getAuthData() throws AuthFatalFailureException {
        long num = 0;
        while (num * WAIT <= mWaitTimeOut) {
            if (getAndSetLock(false)) {
                return mOAuthToken;
            }
            else {
                doWait();
                num++;
            }
        }
        throw new AuthFatalFailureException(getRefreshFailException());
    }

    /**
     * Refresh the OAuth.
     * 
     * @throws AuthFatalFailureException
     *             exception
     */
    @Override
    public void refresh() throws AuthFatalFailureException {
        if (!getAndSetLock(true)) {
            getAuthData();
        }
        else {
            try {
                if (getTokenState() == OAuthTokenState.FAIL || !mAutoRefresh) {
                    setTokenState(OAuthTokenState.FAIL);
                    throw new AuthFatalFailureException(getRefreshFailException());
                }
                else {
                    doRefresh();
                }
            }
            finally {
                unlock();
            }
        }
    }

    /**
     * Get the lock, optionally lock the lock after getting the lock.
     * 
     * @param doLock
     *            whether want to lock after getting the lock.
     * @return
     */
    synchronized public boolean getAndSetLock(boolean doLock) {
        boolean lockRetrieved = false;
        if (doLock) {
            if (locked) {
                lockRetrieved = false;
            }
            else {
                locked = true;
                lockRetrieved = true;
            }
        }
        else {
            lockRetrieved = !locked;
        }
        return lockRetrieved;
    }

    /**
     * Unlock the OAuth lock.
     */
    private void unlock() {
        locked = false;
    }

    /**
     * Refresh the OAuth.
     * 
     * @throws BoxRestException
     *             exception
     * @throws BoxServerException
     *             exception
     * @throws AuthFatalFailureException
     *             exception
     */
    private void doRefresh() throws AuthFatalFailureException {
        setTokenState(OAuthTokenState.REFRESHING);
        try {
            mOAuthToken = mClient.getOAuthManager().refreshOAuth(mOAuthToken.getRefreshToken(), mClientId, mClientSecret, mDeviceId, mDeviceName);
            setTokenState(OAuthTokenState.AVAILABLE);
            setRefreshFail(null);
            if (refreshListener != null) {
                refreshListener.onRefresh(mOAuthToken);
            }
        }
        catch (BoxRestException e) {
            setRefreshFail(e);
            throw new AuthFatalFailureException(getRefreshFailException());
        }
        catch (BoxServerException e) {
            setRefreshFail(e);
            throw new AuthFatalFailureException(getRefreshFailException());
        }
    }

    /**
     * Convenient method for wait.
     */
    private void doWait() {
        try {
            Thread.sleep(WAIT);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void addOAuthRefreshListener(OAuthRefreshListener listener) {
        this.refreshListener = listener;
    }

}
