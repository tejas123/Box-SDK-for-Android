package com.box.boxjavalibv2;

public interface IBoxConfig {

    public String getApiUrlScheme();

    /**
     * Get the API URL Authority.
     * 
     * @return API URL Authority
     */
    public String getApiUrlAuthority();

    /**
     * Get the API URL path.
     * 
     * @return API URL path
     */
    public String getApiUrlPath();

    /**
     * Get the Upload URL scheme.
     * 
     * @return Upload URL scheme
     */
    public String getUploadUrlScheme();

    /**
     * Get the Upload URL Authority.
     * 
     * @return Upload URL Authority
     */
    public String getUploadUrlAuthority();

    /**
     * Get the Download URL scheme.
     * 
     * @return Download URL scheme
     */
    public String getDownloadUrlScheme();

    /**
     * Get the Download URL Authority.
     * 
     * @return Download URL Authority
     */
    public String getDownloadUrlAuthority();

    /**
     * Get the User-Agent String to apply to the HTTP(S) calls.
     * 
     * @return String to use for User-Agent.
     */
    public String getUserAgent();

    /**
     * @return the OAuthUrlScheme
     */
    public String getOAuthUrlScheme();

    /**
     * @return the OAuthUrlAuthority
     */
    public String getOAuthUrlAuthority();

    /**
     * @return the OAuthWebUrlPath
     */
    public String getOAuthWebUrlPath();

    /**
     * @return the OAuth Api Url Path.
     */
    public String getOAuthApiUrlPath();

    /** Get the upload url path. */
    public String getUploadUrlPath();

    /** Get the download url path */
    String getDownloadUrlPath();
}
