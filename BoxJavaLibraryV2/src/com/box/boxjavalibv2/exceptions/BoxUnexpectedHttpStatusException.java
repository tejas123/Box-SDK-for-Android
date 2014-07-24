package com.box.boxjavalibv2.exceptions;

import com.box.boxjavalibv2.dao.BoxServerError;

/**
 * Exception for unexpected http status code(not error status code).
 */
public class BoxUnexpectedHttpStatusException extends BoxServerException {

    private static final long serialVersionUID = 1L;
    private final BoxUnexpectedStatus unexpectedStatus;
    private Object context;

    public BoxUnexpectedHttpStatusException(BoxUnexpectedStatus unexpectedStatus) {
        super(unexpectedStatus.getMessage(), unexpectedStatus.getStatus());
        this.unexpectedStatus = unexpectedStatus;
    }

    /**
     * @return the {@link #BoxUnexpectedStatus}
     */
    public BoxUnexpectedStatus getUnexpectedStatus() {
        return unexpectedStatus;
    }

    /**
     * @return the context
     */
    public Object getContext() {
        return context;
    }

    /**
     * @param context
     *            the context to set
     */
    public void setContext(Object context) {
        this.context = context;
    }

    @Override
    public BoxServerError getError() {
        return unexpectedStatus;
    }
}
