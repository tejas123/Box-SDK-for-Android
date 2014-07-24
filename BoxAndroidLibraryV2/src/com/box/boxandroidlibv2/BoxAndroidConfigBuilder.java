package com.box.boxandroidlibv2;

import com.box.boxjavalibv2.BoxConfigBuilder;

public class BoxAndroidConfigBuilder extends BoxConfigBuilder {

    /** Default User-Agent String. */
    private static final String USER_AGENT = "BoxAndroidLibraryV2";

    public BoxAndroidConfigBuilder() {
        super();
        this.setUserAgent(USER_AGENT);
    }
}
