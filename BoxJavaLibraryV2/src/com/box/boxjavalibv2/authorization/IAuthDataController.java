package com.box.boxjavalibv2.authorization;

import com.box.boxjavalibv2.dao.IAuthData;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;

/**
 * Interface to wrap IAuthData and handles auth logic like auto-refresh for OAuth.
 */
public interface IAuthDataController {

    IAuthData getAuthData() throws AuthFatalFailureException;

    void refresh() throws AuthFatalFailureException;

}
