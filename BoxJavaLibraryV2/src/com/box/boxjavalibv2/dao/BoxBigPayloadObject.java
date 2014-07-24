package com.box.boxjavalibv2.dao;

import java.io.InputStream;

import com.box.restclientv2.exceptions.BoxRestException;

/**
 * Representing an object of a big payload from server response. Normally this would be a file.
 */
public class BoxBigPayloadObject extends BoxObject {

    private InputStream content;
    private double contentLength;

    /**
     * Set content.
     * 
     * @param content
     *            content
     */
    public void setContent(InputStream content) {
        this.content = content;
    }

    /**
     * Get content of the preview. Caller is responsible for closing the InputStream.
     * 
     * @return preview input stream.
     * @throws BoxRestException
     */
    public InputStream getContent() throws BoxRestException {
        return content;
    }

    public double getContentLength() {
        return contentLength;
    }

    public void setContentLength(double contentLength) {
        this.contentLength = contentLength;
    }

    @Override
    public void writeToParcel(IBoxParcelWrapper parcelWrapper, int flags) {
        throw new UnsupportedOperationException("Writing to parcel is not supported!");
    }
}
