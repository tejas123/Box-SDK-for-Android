package com.box.boxjavalibv2;

public class BoxConfigBuilder {

    /** Default API url scheme. */
    private static final String API_URL_SCHEME = "https";
    /** Default API url authority. */
    private static final String API_URL_AUTHORITY = "api.box.com";
    /** Default API url path. */
    private static final String API_URL_PATH = "/2.0";

    /** Default Upload url scheme. */
    private static final String UPLOAD_URL_SCHEME = "https";
    /** Default Upload url authority. */
    private static final String UPLOAD_URL_AUTHORITY = "upload.box.com";
    /** Default upload API url path. */
    private static final String UPLOAD_URL_PATH = "/api/2.0";

    /** Default OAuth url scheme. */
    private static final String OAUTH_URL_SCHEME = "https";
    /** Default OAuth url authority. */
    private static final String OAUTH_URL_AUTHORITY = "www.box.com";
    /** Default OAuth API url path. */
    private static final String OAUTH_API_URL_PATH = "/api";
    /** Default OAuth url path. */
    private static final String OAUTH_WEB_URL_PATH = "/api/oauth2/authorize";

    /** Default Download url scheme. */
    private static final String DOWNLOAD_URL_SCHEME = "https";
    /** Default Download url authority. */
    private static final String DOWNLOAD_URL_AUTHORITY = "api.box.com";
    /** Default Download url authority. */
    private static final String DOWNLOAD_URL_PATH = "/2.0";
    /** Default User-Agent String. */
    private static final String USER_AGENT = "BoxJavaLibraryV2";

    private String mOAuthUrlScheme = OAUTH_URL_SCHEME;
    private String mOAuthUrlAuthority = OAUTH_URL_AUTHORITY;
    private String mOAuthApiUrlPath = OAUTH_API_URL_PATH;
    private String mOAuthWebUrlPath = OAUTH_WEB_URL_PATH;
    private String mApiUrlScheme = API_URL_SCHEME;
    private String mApiUrlAuthority = API_URL_AUTHORITY;
    private String mApiUrlPath = API_URL_PATH;
    private String mUploadUrlScheme = UPLOAD_URL_SCHEME;
    private String mUploadUrlAuthority = UPLOAD_URL_AUTHORITY;
    private String mUploadUrlPath = UPLOAD_URL_PATH;
    private String mDownloadUrlScheme = DOWNLOAD_URL_SCHEME;
    private String mDownloadUrlAuthority = DOWNLOAD_URL_AUTHORITY;
    private String mDownloadUrlPath = DOWNLOAD_URL_PATH;
    private String mUserAgent = USER_AGENT;

    public IBoxConfig build() {
        return new BoxConfig(this);
    }

    /**
     * Set a custom API URL scheme.
     * 
     * @param scheme
     *            Custom scheme
     */
    public void apiUrlScheme(final String scheme) {
        mApiUrlScheme = scheme;
    }

    /**
     * Set a custom API URL Authority.
     * 
     * @param authority
     *            Custom Authority
     */
    public void apiUrlAuthority(final String authority) {
        mApiUrlAuthority = authority;
    }

    /**
     * Set a custom Download URL Authority.
     * 
     * @param authority
     *            Custom Authority
     */
    public void setDownloadUrlAuthority(final String authority) {
        mDownloadUrlAuthority = authority;
    }

    /**
     * Set a custom API URL path.
     * 
     * @param path
     *            Custom path
     */
    public void apiUrlPath(final String path) {
        mApiUrlPath = path;
    }

    /**
     * Set a custom Upload URL scheme.
     * 
     * @param scheme
     *            Custom scheme
     */
    public void setUploadUrlScheme(final String scheme) {
        mUploadUrlScheme = scheme;
    }

    /**
     * Set a custom Upload URL Authority.
     * 
     * @param authority
     *            Custom Authority
     */
    public void setUploadUrlAuthority(final String authority) {
        mUploadUrlAuthority = authority;
    }

    /**
     * Set a custom Download URL scheme.
     * 
     * @param scheme
     *            Custom scheme
     */
    public void setDownloadUrlScheme(final String scheme) {
        mDownloadUrlScheme = scheme;
    }

    /**
     * Set the String to use as the User-Agent HTTP header.
     * 
     * @param agent
     *            User-Agent String
     */
    public void setUserAgent(final String agent) {
        mUserAgent = agent;
    }

    /**
     * @param oAuthUrlScheme
     *            the OAuthUrlScheme to set
     */
    public void setAuthUrlScheme(String oAuthUrlScheme) {
        this.mOAuthUrlScheme = oAuthUrlScheme;
    }

    /**
     * @param oAuthUrlAuthority
     *            the OAuthUrlAuthority to set
     */
    public void setOAuthUrlAuthority(String oAuthUrlAuthority) {
        this.mOAuthUrlAuthority = oAuthUrlAuthority;
    }

    /**
     * @param oAuthUrlPath
     *            the OAuthUrlPath to set
     */
    public void setOAuthUrlPath(String oAuthUrlPath) {
        this.mOAuthWebUrlPath = oAuthUrlPath;
    }

    /**
     * @param oAuthApiUrlPath
     *            the mOAuthApiUrlPath to set
     */
    public void setOAuthApiUrlPath(String oAuthApiUrlPath) {
        this.mOAuthApiUrlPath = oAuthApiUrlPath;
    }

    /**
     * @param uploadUrlPath
     *            the mUploadUrlPath to set
     */
    public void setUploadUrlPath(String uploadUrlPath) {
        this.mUploadUrlPath = uploadUrlPath;
    }

    /**
     * @param downloadUrlPath
     *            the downloadUrlPath to set
     */
    public void setDownloadUrlPath(String downloadUrlPath) {
        this.mDownloadUrlPath = downloadUrlPath;
    }

    public class BoxConfig implements IBoxConfig {

        private final String mOAuthUrlScheme;
        private final String mOAuthUrlAuthority;
        private final String mOAuthApiUrlPath;
        private final String mOAuthWebUrlPath;
        private final String mApiUrlScheme;
        private final String mApiUrlAuthority;
        private final String mApiUrlPath;
        private final String mUploadUrlScheme;
        private final String mUploadUrlAuthority;
        private final String mUploadUrlPath;
        private final String mDownloadUrlScheme;
        private final String mDownloadUrlAuthority;
        private final String mDownloadUrlPath;
        private final String mUserAgent;

        private BoxConfig(BoxConfigBuilder builder) {
            this.mApiUrlAuthority = builder.mApiUrlAuthority;
            this.mApiUrlPath = builder.mApiUrlPath;
            this.mApiUrlScheme = builder.mApiUrlScheme;
            this.mDownloadUrlAuthority = builder.mDownloadUrlAuthority;
            this.mDownloadUrlPath = builder.mDownloadUrlPath;
            this.mDownloadUrlScheme = builder.mDownloadUrlScheme;
            this.mOAuthApiUrlPath = builder.mOAuthApiUrlPath;
            this.mOAuthUrlAuthority = builder.mOAuthUrlAuthority;
            this.mOAuthUrlScheme = builder.mOAuthUrlScheme;
            this.mOAuthWebUrlPath = builder.mOAuthWebUrlPath;
            this.mUploadUrlAuthority = builder.mUploadUrlAuthority;
            this.mUploadUrlPath = builder.mUploadUrlPath;
            this.mUploadUrlScheme = builder.mUploadUrlScheme;
            this.mUserAgent = builder.mUserAgent;
        }

        /**
         * Get the Download URL Authority.
         * 
         * @return Download URL Authority
         */
        @Override
        public String getDownloadUrlAuthority() {
            return mDownloadUrlAuthority;
        }

        /**
         * Get the API URL scheme.
         * 
         * @return API URL scheme
         */
        @Override
        public String getApiUrlScheme() {
            return mApiUrlScheme;
        }

        /**
         * Get the API URL Authority.
         * 
         * @return API URL Authority
         */
        @Override
        public String getApiUrlAuthority() {
            return mApiUrlAuthority;
        }

        /**
         * Get the API URL path.
         * 
         * @return API URL path
         */
        @Override
        public String getApiUrlPath() {
            return mApiUrlPath;
        }

        /**
         * Get the Upload URL scheme.
         * 
         * @return Upload URL scheme
         */
        @Override
        public String getUploadUrlScheme() {
            return mUploadUrlScheme;
        }

        /**
         * Get the Upload URL Authority.
         * 
         * @return Upload URL Authority
         */
        @Override
        public String getUploadUrlAuthority() {
            return mUploadUrlAuthority;
        }

        /**
         * Get the Download URL scheme.
         * 
         * @return Download URL scheme
         */
        @Override
        public String getDownloadUrlScheme() {
            return mDownloadUrlScheme;
        }

        /**
         * Get the User-Agent String to apply to the HTTP(S) calls.
         * 
         * @return String to use for User-Agent.
         */
        @Override
        public String getUserAgent() {
            return mUserAgent;
        }

        /**
         * @return the OAuthUrlScheme
         */
        @Override
        public String getOAuthUrlScheme() {
            return mOAuthUrlScheme;
        }

        /**
         * @return the OAuthUrlAuthority
         */
        @Override
        public String getOAuthUrlAuthority() {
            return mOAuthUrlAuthority;
        }

        /**
         * @return the OAuthUrlPath
         */
        @Override
        public String getOAuthWebUrlPath() {
            return mOAuthWebUrlPath;
        }

        /**
         * @return the mOAuthApiUrlPath
         */
        @Override
        public String getOAuthApiUrlPath() {
            return mOAuthApiUrlPath;
        }

        @Override
        public String getUploadUrlPath() {
            return mUploadUrlPath;
        }

        @Override
        public String getDownloadUrlPath() {
            return mDownloadUrlPath;
        }

    }
}
