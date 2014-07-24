package com.box.boxjavalibv2.request.requestobjects;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.boxjavalibv2.requests.requestobjects.BoxItemRestoreRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxItemRestoreRequestObjectTest {

    private static final String NAME = "name";
    private static final String PARENT = "parent";

    @Test
    public void testDefaultObjectHasNoField() {
        BoxItemRestoreRequestObject obj = BoxItemRestoreRequestObject.restoreItemRequestObject();
        Assert.assertNull(obj.getFromEntity(NAME));
        Assert.assertNull(obj.getFromEntity(PARENT));
    }

    @Test
    public void testNameInObject() throws BoxRestException, BoxJSONException {
        String name = "testname";
        BoxItemRestoreRequestObject obj = BoxItemRestoreRequestObject.restoreItemRequestObject().setNewName(name);

        Assert.assertEquals(name, obj.getFromEntity(NAME));
    }

    @Test
    public void testParentInObject() throws BoxRestException, BoxJSONException {
        String parentid = "testid";
        BoxItemRestoreRequestObject obj = BoxItemRestoreRequestObject.restoreItemRequestObject().setNewParent(parentid);

        MapJSONStringEntity entity = (MapJSONStringEntity) obj.getFromEntity(PARENT);

        Assert.assertEquals(parentid, entity.get(BoxFolder.FIELD_ID));
    }

    @Test
    public void testBothParentAndNameInObject() throws BoxRestException, BoxJSONException {
        String name = "testname";
        String parentid = "testid";
        BoxItemRestoreRequestObject obj = BoxItemRestoreRequestObject.restoreItemRequestObject().setNewName(name).setNewParent(parentid);

        Assert.assertEquals(name, obj.getFromEntity(NAME));
        MapJSONStringEntity entity = (MapJSONStringEntity) obj.getFromEntity(PARENT);

        Assert.assertEquals(parentid, entity.get(BoxFolder.FIELD_ID));
    }
}
