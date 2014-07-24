package com.box.boxjavalibv2.request.requestobjects;

import junit.framework.Assert;

import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxResourceType;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.exceptions.BoxJSONException;
import com.box.boxjavalibv2.jsonentities.MapJSONStringEntity;
import com.box.boxjavalibv2.requests.requestobjects.BoxCollabRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;

public class BoxCollaborationRequestObjectTest {

    private static final String ITEM_STR = "item";
    private static final String ACCESSIBLE_STR = "accessible_by";
    private static final String ROLE_STR = "role";

    @Test
    public void testJSONHasAllFields() throws BoxRestException, BoxJSONException {
        String folderId = "testfolderid123";
        String userId = "testuserid456";
        String login = "abc@box.com";
        String role = "testrole789";

        BoxCollabRequestObject obj = BoxCollabRequestObject.createCollabObject(folderId, userId, login, role);

        // String jsonStr = obj.getJSONEntity().toJSONString(new BoxJSONParser(new BoxResourceHub()));
        MapJSONStringEntity item = (MapJSONStringEntity) obj.getFromEntity(ITEM_STR);
        Assert.assertEquals(folderId, item.get(BoxFolder.FIELD_ID));
        Assert.assertEquals(BoxResourceType.FOLDER.toString(), item.get(BoxFolder.FIELD_TYPE));

        MapJSONStringEntity accessible = (MapJSONStringEntity) obj.getFromEntity(ACCESSIBLE_STR);
        Assert.assertEquals(userId, accessible.get(BoxUser.FIELD_ID));
        Assert.assertEquals(login, accessible.get(BoxUser.FIELD_LOGIN));

        Assert.assertEquals(role, obj.getFromEntity(ROLE_STR));
    }
}
