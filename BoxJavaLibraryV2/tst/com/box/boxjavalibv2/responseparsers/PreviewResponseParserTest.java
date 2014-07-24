package com.box.boxjavalibv2.responseparsers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import junit.framework.Assert;

import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import com.box.boxjavalibv2.dao.BoxPreview;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responses.DefaultBoxResponse;

public class PreviewResponseParserTest {

    private final static String PREVIEW_MOCK_CONTENT = "arbitrary string";
    private final static String LINK_VALUE = "<https://api.box.com/2.0/files/5000369410/preview.png?page=%d>; rel=\"first\", <https://api.box.com/2.0/files/5000369410/preview.png?page=%d>; rel=\"last\"";
    private final static String LINK_NAME = "Link";
    private final static int firstPage = 1;
    private final static int lastPage = 2;
    private final static double length = 213;

    private BoxPreview preview;
    private DefaultBoxResponse boxResponse;
    private HttpResponse response;
    private HttpEntity entity;
    private InputStream inputStream;
    private Header header;

    @Before
    public void setUp() {
        preview = new BoxPreview();
        preview.setFirstPage(firstPage);
        preview.setLastPage(lastPage);
        boxResponse = EasyMock.createMock(DefaultBoxResponse.class);
        response = EasyMock.createMock(BasicHttpResponse.class);
        entity = EasyMock.createMock(StringEntity.class);
        header = new BasicHeader("Link", String.format(LINK_VALUE, firstPage, lastPage));
    }

    @Test
    public void testCanParsePreview() throws IllegalStateException, IOException, BoxRestException {
        EasyMock.reset(boxResponse, response, entity);
        inputStream = new ByteArrayInputStream(PREVIEW_MOCK_CONTENT.getBytes());
        EasyMock.expect(boxResponse.getHttpResponse()).andReturn(response);
        EasyMock.expect(boxResponse.getContentLength()).andReturn(length);
        EasyMock.expect(response.getEntity()).andReturn(entity);
        EasyMock.expect(entity.getContent()).andReturn(inputStream);

        EasyMock.expect(boxResponse.getHttpResponse()).andReturn(response);
        EasyMock.expect(response.getFirstHeader("Link")).andReturn(header);

        EasyMock.replay(boxResponse, response, entity);
        PreviewResponseParser parser = new PreviewResponseParser();
        Object object = parser.parse(boxResponse);
        Assert.assertEquals(BoxPreview.class, object.getClass());

        BoxPreview parsed = (BoxPreview) object;
        Assert.assertEquals(length, parsed.getContentLength());
        Assert.assertEquals(firstPage, parsed.getFirstPage().intValue());
        Assert.assertEquals(lastPage, parsed.getLastPage().intValue());
        Assert.assertEquals(PREVIEW_MOCK_CONTENT, IOUtils.toString(parsed.getContent()));
        EasyMock.verify(boxResponse, response, entity);

    }
}
