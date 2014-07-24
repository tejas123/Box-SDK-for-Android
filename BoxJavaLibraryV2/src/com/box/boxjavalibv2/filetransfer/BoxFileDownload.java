package com.box.boxjavalibv2.filetransfer;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

import org.apache.commons.io.IOUtils;

import com.box.boxjavalibv2.IBoxConfig;
import com.box.boxjavalibv2.dao.BoxServerError;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.jsonparsing.IBoxJSONParser;
import com.box.boxjavalibv2.requests.DownloadFileRequest;
import com.box.boxjavalibv2.responseparsers.ErrorResponseParser;
import com.box.restclientv2.IBoxRESTClient;
import com.box.restclientv2.authorization.IBoxRequestAuth;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;
import com.box.restclientv2.responseparsers.DefaultFileResponseParser;
import com.box.restclientv2.responses.DefaultBoxResponse;

/**
 * Contains logic for downloading a user's file from Box API and supports using {@link IFileTransferListener} to monitor downloading progress.
 */
public class BoxFileDownload {

    /**
     * size of buffer used when reading from download input stream.
     */
    private static final int DOWNLOAD_BUFFER_SIZE = 4096;
    /**
     * The minimum time in milliseconds that must pass between each call to FileDownloadListener.onProgress. This is to avoid excessive calls which may lock up
     * the device.
     */
    private int progressUpdateInterval = 300;
    /** config. */
    private final IBoxConfig mConfig;
    /** File id. */
    private final String mFileId;
    /**
     * Used to track how many bytes have been transferred so far.
     */
    private long mBytesTransferred = 0;
    /** Listener to monitor downloading progress. */
    private IFileTransferListener mListener = null;
    /** REST client to make api calls. */
    private final IBoxRESTClient mRestClient;

    /**
     * Constructor.
     * 
     * @param config
     *            config
     * @param restClient
     *            REST client to make api calls.
     * @param fileId
     *            Id of the file to be downloaded.
     */
    public BoxFileDownload(final IBoxConfig config, final IBoxRESTClient restClient, final String fileId) {
        this.mConfig = config;
        this.mFileId = fileId;
        this.mRestClient = restClient;
    }

    /**
     * Set the listener listening to download progress.
     * 
     * @param listener
     *            listener
     */
    public void setProgressListener(final IFileTransferListener listener) {
        this.mListener = listener;
    }

    /**
     * Set the interval time you want the progress update to be reported.
     * 
     * @param time
     *            interval time
     */
    public void setProgressUpdateInterval(final int time) {
        progressUpdateInterval = time;
    }

    /**
     * Execute a download.
     * 
     * @param auth
     *            auth
     * @param outputStreams
     *            OutputStream's the file is going to be downloaded into.
     * @param parser
     *            json parser
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     * @throws IOException
     *             exception
     * @throws BoxServerException
     *             exception
     * @throws InterruptedException
     *             exception
     * @throws AuthFatalFailureException
     *             exception indicating authentication totally failed
     */
    public void execute(final IBoxRequestAuth auth, final OutputStream[] outputStreams, IBoxJSONParser parser, BoxDefaultRequestObject requestObject)
        throws BoxRestException, IOException, BoxServerException, InterruptedException, AuthFatalFailureException {
        InputStream result = execute(auth, parser, requestObject);
        copyOut(result, outputStreams);
    }

    /**
     * Execute a download.
     * 
     * @param auth
     *            auth
     * @param destination
     *            destination file
     * @param parser
     *            json parser
     * @param
     * @param requestObject
     *            request object
     * @throws BoxRestException
     *             exception
     * @throws IOException
     *             exception
     * @throws BoxServerException
     *             exception
     * @throws InterruptedException
     *             exception
     * @throws AuthFatalFailureException
     *             exception indicating authentication totally failed
     */
    public void execute(final IBoxRequestAuth auth, final File destination, IBoxJSONParser parser, BoxDefaultRequestObject requestObject)
        throws BoxRestException, IOException, BoxServerException, InterruptedException, AuthFatalFailureException {
        OutputStream[] streams = new OutputStream[1];
        streams[0] = new FileOutputStream(destination);
        execute(auth, streams, parser, requestObject);
    }

    /**
     * Execute the download and return the raw InputStream. This method is not involved with download listeners and will not publish anything through download
     * listeners. Instead caller handles the InputStream as she/he wishes.
     * 
     * @param auth
     *            auth
     * @param parser
     *            json parser
     * @param requestObject
     *            request object
     * @return InputStream
     * @throws BoxRestException
     *             exception
     * @throws BoxServerException
     *             exception
     * @throws AuthFatalFailureException
     *             exception indicating authentication totally failed
     */
    public InputStream execute(final IBoxRequestAuth auth, IBoxJSONParser parser, BoxDefaultRequestObject requestObject) throws BoxRestException,
        BoxServerException, AuthFatalFailureException {
        DownloadFileRequest request = new DownloadFileRequest(mConfig, parser, mFileId, requestObject);
        request.setAuth(auth);
        DefaultBoxResponse response = (DefaultBoxResponse) mRestClient.execute(request);
        DefaultFileResponseParser responseParser = new DefaultFileResponseParser();
        ErrorResponseParser errorParser = new ErrorResponseParser(parser);
        Object result = response.parseResponse(responseParser, errorParser);
        if (result instanceof BoxServerError) {
            throw new BoxServerException((BoxServerError) result);
        }
        return (InputStream) result;
    }

    /**
     * Get bytes transferred.
     * 
     * @return the mBytesTransferred
     */
    public long getBytesTransferred() {
        return mBytesTransferred;
    }

    /**
     * Copy the InputStream into OutputStream's, also notify listener about the progress.
     * 
     * @param inputStream
     *            InputStream
     * @param outputStreams
     *            OutputStream's
     * @throws IOException
     *             exception
     * @throws InterruptedException
     *             exception
     */
    private void copyOut(final InputStream inputStream, final OutputStream[] outputStreams) throws IOException, InterruptedException {
        // Read the rest of the stream and write to the destination OutputStream.
        final byte[] buffer = new byte[DOWNLOAD_BUFFER_SIZE];
        int bufferLength = 0;
        long lastOnProgressPost = 0;
        try {
            while ((bufferLength = inputStream.read(buffer)) > 0) {
                if (Thread.currentThread().isInterrupted()) {
                    throw new InterruptedException();
                }
                for (int i = 0; i < outputStreams.length; i++) {
                    outputStreams[i].write(buffer, 0, bufferLength);
                }
                mBytesTransferred += bufferLength;
                long currTime = (new Date()).getTime();
                if (mListener != null && currTime - lastOnProgressPost > progressUpdateInterval) {
                    lastOnProgressPost = currTime;
                    mListener.onProgress(mBytesTransferred);
                }
            }
        }
        finally {
            // Try to flush and close all the OutputStreams and close InputStream.
            IOException exception = null;
            for (int i = 0; i < outputStreams.length; i++) {
                try {
                    outputStreams[i].flush();
                    outputStreams[i].close();
                }
                catch (IOException e) {
                    exception = e;
                }
            }
            IOUtils.closeQuietly(inputStream);
            if (exception != null) {
                throw exception;
            }
        }
    }
}
