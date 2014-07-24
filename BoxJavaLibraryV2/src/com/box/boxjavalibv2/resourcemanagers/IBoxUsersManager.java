package com.box.boxjavalibv2.resourcemanagers;

import java.util.List;

import com.box.boxjavalibv2.dao.BoxEmailAlias;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.requestobjects.BoxEmailAliasRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxSimpleUserRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserDeleteRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxUserUpdateLoginRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxUsersManager extends IBoxResourceManager {

    /**
     * Get the current user's information.
     * 
     * @param requestObject
     *            request object
     * @return current user
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxUser getCurrentUser(BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Get the list of all users for the Enterprise with their user_id, public_name, and login if the user is an enterprise admin. If the user is not an admin,
     * this request returns the current user's user_id, public_name, and login.
     * 
     * @param requestObject
     *            request object
     * @param filterTerm
     *            A string used to filter the results to only users starting with the filter_term in either the name or the login. Use null if don't want
     *            filter.
     * @return collection of users
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public List<BoxUser> getAllEnterpriseUser(BoxDefaultRequestObject requestObject, String filterTerm) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Moves all of the content from within one user's folder into a new folder in another user's account. You can move folders across users as long as the you
     * have administrative permissions. To move everything from the root folder, use 0 which always represents the root folder of a Box account
     * 
     * @param userId
     *            id of the user
     * @param folderId
     *            id of the folder to be removed
     * @param requestObject
     *            request object
     * @return the newly created destination folder
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxFolder moveFolderToAnotherUser(String userId, String folderId, BoxSimpleUserRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException;

    /**
     * Used to provision a new user in an enterprise. This method only works for enterprise admins.
     * 
     * 
     * @param requestObject
     *            request object
     * @return newly created user
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxUser createEnterpriseUser(BoxUserRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    /**
     * Used to edit the settings and information about a user. This method only works for enterprise admins.
     * 
     * @param userId
     *            id of the user.
     * @param requestObject
     *            request object
     * @return the updated user
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxUser updateUserInformaiton(String userId, BoxUserRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Used to delete a user in an enterprise. This method only works for enterprise admins.
     *
     * @param userId
     *            id of the user.
     * @param requestObject
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteEnterpriseUser(String userId, BoxUserDeleteRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Retrieves all email aliases for this user. The collection of email aliases does not include the primary login for the user
     * 
     * @param userId
     *            id of user
     * @param requestObject
     *            request object
     * @return collection of email aliases
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public List<BoxEmailAlias> getEmailAliases(String userId, BoxDefaultRequestObject requestObject) throws BoxServerException, BoxRestException,
        AuthFatalFailureException;

    /**
     * Adds a new email alias to the given user's account. This feature is currently only available to enterprise admins and the new email must be in a domain
     * associated with the enterprise and can not be a publicly atainable domain (e.g. gmail.com).
     * 
     * @param userId
     *            id of user
     * @param requestObject
     *            request object
     * @return the newly added email alias
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxEmailAlias addEmailAlias(String userId, BoxEmailAliasRequestObject requestObject) throws BoxServerException, BoxRestException,
        AuthFatalFailureException;

    /**
     * Removes an email alias from a user.
     * 
     * @param userId
     *            id of the user
     * @param emailId
     *            id of the email alias to be removed
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteEmailAlias(String userId, String emailId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Used to convert one of the user's confirmed email aliases into the user's primary login.
     * 
     * @param userId
     *            id of the user
     * @param requestObject
     *            request object
     * @return the updated user object
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.boxjavalibv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.boxjavalibv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxUser updateUserPrimaryLogin(String userId, BoxUserUpdateLoginRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

}