package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.dao.BoxComment;
import com.box.boxjavalibv2.dao.IBoxType;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.requestobjects.BoxCommentRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public interface IBoxCommentsManager extends IBoxResourceManager {

    /**
     * Add a comment to an item.
     * 
     * @param requestObject
     *            comment request object.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxComment addComment(BoxCommentRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;

    public BoxComment addComment(String commentedItemId, IBoxType commentedItemType, String message) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Get a comment, given a comment id.
     * 
     * @param commentId
     *            id of the comment
     * @param requestObject
     *            object that goes into request.
     * @return comment
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxComment getComment(String commentId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Update a comment.
     * 
     * @param commentId
     *            id of the comment
     * @param requestObject
     *            comment request object.s
     * @return comment
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public BoxComment updateComment(String commentId, BoxCommentRequestObject requestObject) throws BoxRestException, BoxServerException,
        AuthFatalFailureException;

    /**
     * Delete a comment.
     * 
     * @param commentId
     *            id of the comment
     * @param requestObject
     *            object that goes into request.
     * @throws BoxRestException
     *             See {@link com.box.restclientv2.exceptions.BoxRestException} for more info.
     * @throws BoxServerException
     *             See {@link com.box.restclientv2.exceptions.BoxServerException} for more info.
     * @throws AuthFatalFailureException
     *             See {@link com.box.restclientv2.exceptions.AuthFatalFailureException} for more info.
     */
    public void deleteComment(String commentId, BoxDefaultRequestObject requestObject) throws BoxRestException, BoxServerException, AuthFatalFailureException;
}