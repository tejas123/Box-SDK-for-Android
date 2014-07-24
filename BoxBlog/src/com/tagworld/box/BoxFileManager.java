package com.tagworld.box;

import java.io.File;
import java.util.ArrayList;

import org.apache.commons.lang.ObjectUtils.Null;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.widget.ListView;
import android.widget.Toast;

import com.box.boxandroidlibv2.BoxAndroidClient;
import com.box.boxandroidlibv2.dao.BoxAndroidCollection;
import com.box.boxandroidlibv2.dao.BoxAndroidFile;
import com.box.boxandroidlibv2.dao.BoxAndroidFolder;
import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxFolder;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.restclientv2.exceptions.BoxSDKException;

public class BoxFileManager extends Activity {
	private ListView lvBoxFileList;
	private BoxModel selectedBoxMode;
	private BoxAndroidClient boxAndroidClient;
	private String currentFolderId = "0";
	private File currentFile;
	private ProgressDialog pd;
	private DirBoxListAdapter boxListAdapter;
	private ArrayList<BoxTypedObject> currentBoxObjects;
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			if (msg.what == 0) {
				BoxTypedObject object = (BoxTypedObject) currentBoxObjects.get(msg.arg1);
				// .getItem(msg.arg1);
				if (object instanceof BoxAndroidFile) {
					downloadFile((BoxAndroidFile) object);
				} else if (object instanceof BoxAndroidFolder) {
					navigateToFolder(((BoxAndroidFolder) object).getId());
				}
				// pd = ProgressDialog.show(BoxFileManager.this, "",
				// "Loading...");
				// downloadFile(object);
			}
		}
	};

	public void downloadFile(final BoxAndroidFile file) {
		Toast.makeText(BoxFileManager.this,
				"Starting download..." + file.getName(), Toast.LENGTH_LONG)
				.show();
		pd = ProgressDialog.show(BoxFileManager.this, "", "Downloading...");
		new AsyncTask<BoxAndroidFile, Void, Boolean>() {

			@Override
			protected Boolean doInBackground(BoxAndroidFile... arg0) {
				try {
					File f = new File(currentFile, file.getName());
					boxAndroidClient.getFilesManager().downloadFile(
							file.getId(), f, null, null);
				} catch (Exception e) {
					return false;
				}

				return true;
			}

			@Override
			protected void onPostExecute(Boolean result) {
				if (result) {
					Toast.makeText(BoxFileManager.this,
							"Successfully downloaded " + file.getName() + ".",
							Toast.LENGTH_LONG).show();
				} else {
					Toast.makeText(BoxFileManager.this,
							"An error occurred when downloading.",
							Toast.LENGTH_LONG).show();
				}
				pd.dismiss();
			}

		}.execute(file);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boxfilemanager);
		currentFile = new File(getPath() + "/theAppGuruz");
		if (!currentFile.exists())
			currentFile.mkdirs();
		selectedBoxMode = (BoxModel) getIntent().getSerializableExtra(
				BoxMain.SELECTED_BOXMODEL);
		boxAndroidClient = getClient().get(
				(getIntent().getIntExtra(BoxMain.SELECTED_CLIENT, 0)));
		lvBoxFileList = (ListView) findViewById(R.id.lvBoxFilesFoolderList);
		setFileListAdapter();

	}

	private ArrayList<BoxAndroidClient> getClient() {
		return ((BoxSDKSampleApplication) getApplication()).getClient();
	}

	private void setFileListAdapter() {
		currentBoxObjects=selectedBoxMode.getBoxObjects();
		boxListAdapter = new DirBoxListAdapter(BoxFileManager.this,
				currentBoxObjects, handler);
		lvBoxFileList.setAdapter(boxListAdapter);
	}

	public void onBackPressed() {
		if (currentFolderId == "0") {
			super.onBackPressed();
		}
		Thread t = new Thread() {

			@Override
			public void run() {
				String parentFolderId;
				try {
					BoxFolder parentFolder = boxAndroidClient
							.getFoldersManager()
							.getFolder(currentFolderId, null).getParent();
					if (parentFolder == null) {
						parentFolderId = "0";
					} else {
						parentFolderId = parentFolder.getId();
					}
				} catch (BoxSDKException e) {

					return;
				}

				navigateToFolder(parentFolderId);
			}
		};
		t.start();

	}

	public void navigateToFolder(final String folderId) {
		new AsyncTask<Null, Void, BoxAndroidCollection>() {

			@Override
			protected BoxAndroidCollection doInBackground(Null... params) {
				try {
					BoxPagingRequestObject requestObj = new BoxPagingRequestObject();
					requestObj.getRequestExtras().addField(BoxFile.FIELD_NAME);
					requestObj.getRequestExtras().addField(
							BoxFile.FIELD_MODIFIED_AT);
					requestObj.getRequestExtras().addField(
							BoxFile.FIELD_SHARED_LINK);
					requestObj.getRequestExtras().addField(BoxFile.FIELD_SIZE);
					requestObj.getRequestExtras().addField(
							BoxFile.FIELD_OWNED_BY);

					return (BoxAndroidCollection) boxAndroidClient
							.getFoldersManager().getFolderItems(folderId,
									requestObj);
				} catch (BoxSDKException e) {

					return new BoxAndroidCollection();
				}
			}

			@Override
			protected void onPostExecute(BoxAndroidCollection result) {
				currentFolderId = folderId;
				currentBoxObjects = result.getEntries();
				lvBoxFileList.setAdapter(new DirBoxListAdapter(
						BoxFileManager.this, currentBoxObjects, handler));
			}
		}.execute();
	}

	private String getPath() {
		String path = "";
		if (Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED)) {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
		} else if ((new File("/mnt/emmc")).exists()) {
			path = "/mnt/emmc";
		} else {
			path = Environment.getExternalStorageDirectory().getAbsolutePath();
		}
		return path;
	}
}
