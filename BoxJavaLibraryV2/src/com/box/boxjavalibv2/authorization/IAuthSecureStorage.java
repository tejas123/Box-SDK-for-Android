package com.box.boxjavalibv2.authorization;

import com.box.boxjavalibv2.dao.IAuthData;

/**
 * Interface for storage to save authentication(OAuth) objects in a secure way.
 */
public interface IAuthSecureStorage {

    /**
     * Save auth in a secure way.
     * 
     * @param auth
     */
    void saveAuth(IAuthData auth);

    /**
     * Get auth.
     * 
     * @return
     */
    IAuthData getAuth();
}
