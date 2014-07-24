package com.tagworld.box;

import java.util.ArrayList;

import android.app.Application;

import com.box.boxandroidlibv2.BoxAndroidClient;
import com.box.boxjavalibv2.dao.BoxUser;

/**
 * The application class which contains a singleton instance of
 * BoxAndroidClient.
 */
public class BoxSDKSampleApplication extends Application {

	// TODO: use your own client settings
	public static final String CLIENT_ID = "//add your client id;
	public static final String CLIENT_SECRET = "//add your client secret";

	private ArrayList<BoxAndroidClient> list = new ArrayList<BoxAndroidClient>();

	public void setClient(BoxAndroidClient client) {
		list.add(client);

	}

	/**
	 * Gets the BoxAndroidClient for this app.
	 * 
	 * @return a singleton instance of BoxAndroidClient.
	 */
	public ArrayList<BoxAndroidClient> getClient() {
		return list;
	}

}
