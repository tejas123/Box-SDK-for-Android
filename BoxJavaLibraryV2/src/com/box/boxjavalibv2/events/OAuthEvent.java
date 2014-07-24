package com.box.boxjavalibv2.events;

import com.box.boxjavalibv2.authorization.IAuthEvent;

/**
 * OAuth event.
 */
public enum OAuthEvent implements IAuthEvent {
	/** Event for page started loading. */
    PAGE_STARTED,
    /** Event for page finished loading. */
    PAGE_FINISHED,
    /** Webview returns oauth code. */
    OAUTH_CODE_RECEIVED,
    /** Event for auth request received. */
    AUTH_REQUEST_RECEIVED,
    /** Event for oauth created. */
    OAUTH_CREATED,
}
