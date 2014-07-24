package com.box.boxjavalibv2.authorization;

import com.box.boxjavalibv2.dao.IAuthData;


/**
 * Listener listening to the event that oauth token is refreshed.
 */
public interface OAuthRefreshListener {

    /**
     * @param newAuthData
     *            the new IAuthData(after refresh).
     */
    void onRefresh(IAuthData newAuthData);
}
