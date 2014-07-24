package com.box.boxjavalibv2.responseparsers;

import java.io.InputStream;

import com.box.boxjavalibv2.dao.BoxThumbnail;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.responseparsers.DefaultFileResponseParser;
import com.box.restclientv2.responses.IBoxResponse;

public class ThumbnailResponseParser extends DefaultFileResponseParser {

    @Override
    public BoxThumbnail parse(IBoxResponse response) throws BoxRestException {
        BoxThumbnail obj = new BoxThumbnail();
        obj.setContent((InputStream) super.parse(response));
        obj.setContentLength(response.getContentLength());
        return obj;
    }
}
