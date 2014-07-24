package com.box.boxjavalibv2.resourcemanagers;

import com.box.boxjavalibv2.BoxClient;

/**
 * Resource manager plugin interface. Resource manager classes implementing this can be plugged into BoxClient.
 */
public interface IResourceManagerPlugin {

    IBoxResourceManager plugin(BoxClient client);
}
