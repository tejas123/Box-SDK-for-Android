package com.box.boxjavalibv2.requests;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.requestobjects.BoxSimpleUserRequestObject;
import com.box.restclientv2.RestMethod;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;

/**
 * Moves all of the content from within one user's folder into a new folder in another user's account. You can move folders across users as long as the you have
 * administrative permissions. To move everything from the root folder, use 0 which always represents the root folder of a Box account
 */
public class MoveFolderToAnotherUserRequest extends DefaultBoxRequest {

    private static final String URI = "/users/%s/folders/%s";

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param parser
     *            json parser
     * @param userId
     *            id of the signed in user
     * @param folderId
     *            id of the folder to be moved
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     */
    public MoveFolderToAnotherUserRequest(final IBoxConfig config, final IBoxJSONParser parser, final String userId, final String folderId,
        final BoxSimpleUserRequestObject requestObject) throws BoxRestException {
        super(config, parser, getUri(userId, folderId), RestMethod.PUT, requestObject);
    }

    /**
     * Get uri.
     * 
     * @param userId
     *            id of the user
     * @param folderId
     *            id of the folder
     * @return
     */
    public static String getUri(final String userId, final String folderId) {
        return String.format(URI, userId, folderId);
    }
}
