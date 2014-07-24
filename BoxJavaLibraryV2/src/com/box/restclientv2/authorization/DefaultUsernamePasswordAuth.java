package com.box.restclientv2.authorization;

import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.DefaultBoxRequest;
import com.box.restclientv2.requestsbase.IBoxRequest;

/**
 * Auth for username/password.
 */
public class DefaultUsernamePasswordAuth extends DefaultRequestAuth {

    static final String PASSWORD = "password";
    static final String LOGIN = "login";
    static final String ACTION = "action";
    static final String AUTH_ACTION = "authorization";

    private final String userName;
    private final String password;

    /**
     * Constructor
     * 
     * @param userName
     *            user name.
     * @param password
     *            password
     */
    public DefaultUsernamePasswordAuth(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    /**
     * get user name.
     * 
     * @return user name.
     */
    public String getUserName() {
        return userName;
    }

    /**
     * get password.
     * 
     * @return password
     */
    public String getPassword() {
        return password;
    }

    @Override
    public void setAuth(IBoxRequest request) throws BoxRestException, AuthFatalFailureException {
        super.setAuth(request);

        DefaultBoxRequest defaultRequest = ((DefaultBoxRequest) request);
        defaultRequest.addQueryParam(PASSWORD, getPassword());
        defaultRequest.addQueryParam(LOGIN, getUserName());
        defaultRequest.addQueryParam(ACTION, AUTH_ACTION);
    }
}
