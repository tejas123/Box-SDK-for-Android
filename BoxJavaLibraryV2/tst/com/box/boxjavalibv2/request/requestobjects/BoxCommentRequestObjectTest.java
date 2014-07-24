package com.box.boxjavalibv2.request.requestobjects;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.requests.requestobjects.BoxCommentRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxCommentRequestObjectTest {

    private static final String MESSAGE = "message";

    @Test
    public void testJSONHasAllFields() throws BoxRestException, BoxJSONException {
        String message = "testmessage123";
        BoxCommentRequestObject obj = BoxCommentRequestObject.updateCommentRequestObject(message);

        Assert.assertEquals(message, obj.getFromEntity(MESSAGE));
    }
}
