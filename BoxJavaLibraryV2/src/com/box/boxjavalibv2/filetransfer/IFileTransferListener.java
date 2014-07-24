package com.box.boxjavalibv2.filetransfer;

import java.io.IOException;

/**
 * Interface definition for a callback to be invoked when file transfer is happening.
 */
public interface IFileTransferListener {

    /**
     * Called when the file has been transferred. Refer to <a
     * href="http://developers.box.net/w/page/12923951/ApiFunction_Upload-and-Download">ApiFunction_Upload-and-Download</a> for details.
     * 
     * @param status
     *            status string indicating completion status(pass, fail...)
     */
    void onComplete(String status);

    /**
     * Called when the file transfer was canceled.
     */
    void onCanceled();

    /**
     * Called periodically during the download. You can use this to monitor transfer progress. Refer to <a
     * href="http://developers.box.net/w/page/12923951/ApiFunction_Upload-and-Download">ApiFunction_Upload-and-Download</a> for details.
     * 
     * @param bytesTransferred
     *            bytes transferred for now
     */
    void onProgress(long bytesTransferred);

    /**
     * Called when IOException is thrown.
     * 
     * @param e
     *            exception
     */
    void onIOException(final IOException e);
}
