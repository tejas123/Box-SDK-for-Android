package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxGroup;
import com.box.boxjavalibv2.dao.BoxGroupMembership;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.requestobjects.BoxGroupMembershipRequestObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxGroupRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxGroupsManager extends IBoxResourceManager {

    /**
     * Get all groups.
     * 
     * @param requestObject
     * @return All groups.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxCollection getAllGroups(BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Create a group.
     * 
     * @param requestObject
     * @return the group created.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxGroup createGroup(BoxGroupRequestObject requestObject) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Create a group.
     * 
     * @param name
     *            name of the group.
     * @return The group created.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxGroup createGroup(String name) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Update group information.
     * 
     * @param groupId
     *            id of the group.
     * @param requestObject
     * @return the updated group.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxGroup updateGroup(String groupId, BoxGroupRequestObject requestObject) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Delete a group.
     * 
     * @param groupId
     *            id of the group.
     * @param requestObject
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteGroup(String groupId, BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Get memberships of a group.
     * 
     * @param groupId
     *            id of the group.
     * @param requestObject
     * @return memberships.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxCollection getMemberships(String groupId, BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;

    public BoxGroupMembership getMembership(String membershipId, BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;

    public BoxGroupMembership createMembership(BoxGroupMembershipRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;

    /**
     * @param groupId
     *            id of the group
     * @param userId
     *            id of the user to be added.
     * @param role
     *            role of the user. e.g. BoxGroupMembership.ROLE_ADMIN
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxGroupMembership createMembership(String groupId, String userId, String role) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;

    /**
     * update a membership.
     * 
     * @param membershipId
     *            id of the membership to be updated.
     * @param requestObject
     * @return Updated membership.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxGroupMembership updateMembership(String membershipId, BoxGroupMembershipRequestObject requestObject) throws BoxRestException,
        AuthFatalFailureException, BoxServerException;

    /**
     * Update role of a membership.
     * 
     * @param membershipId
     *            id of the membership.
     * @param role
     *            new role.
     * @return updated membership.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxGroupMembership updateMembership(String membershipId, String role) throws BoxRestException, AuthFatalFailureException, BoxServerException;

    /**
     * Delete a membership.
     * 
     * @param membershipId
     *            id of the membership.
     * @param requestObject
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteMembership(String membershipId, BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;

    /**
     * Get all collaborations of this group.
     * 
     * @param groupId
     *            id of the group.
     * @param requestObject
     * @return collection of all the collaboration.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxCollection getAllCollaborations(String groupId, BoxDefaultRequestObject requestObject) throws BoxRestException, AuthFatalFailureException,
        BoxServerException;
}