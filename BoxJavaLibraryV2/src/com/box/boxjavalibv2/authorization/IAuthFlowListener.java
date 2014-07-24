package com.box.boxjavalibv2.authorization;

import java.io.Serializable;


public interface IAuthFlowListener extends Serializable{

    /**
     * On a message.
     * 
     * @param message
     *            message
     */
    void onAuthFlowMessage(IAuthFlowMessage message);

    /**
     * On an exception.
     * 
     * @param e
     *            exception
     */
    void onAuthFlowException(Exception e);

    /**
     * On an event.
     * 
     * @param state
     *            state
     * @param message
     *            context message of this event
     */
    void onAuthFlowEvent(IAuthEvent state, IAuthFlowMessage message);
}
