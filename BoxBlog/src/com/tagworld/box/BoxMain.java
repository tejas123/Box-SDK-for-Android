package com.tagworld.box;

import java.util.ArrayList;

import org.apache.commons.lang.ObjectUtils.Null;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.box.boxandroidlibv2.BoxAndroidClient;
import com.box.boxandroidlibv2.activities.OAuthActivity;
import com.box.boxandroidlibv2.dao.BoxAndroidOAuthData;
import com.box.boxjavalibv2.authorization.OAuthRefreshListener;
import com.box.boxjavalibv2.dao.BoxCollection;
import com.box.boxjavalibv2.dao.BoxFile;
import com.box.boxjavalibv2.dao.BoxTypedObject;
import com.box.boxjavalibv2.dao.BoxUser;
import com.box.boxjavalibv2.dao.IAuthData;
import com.box.boxjavalibv2.exceptions.AuthFatalFailureException;
import com.box.boxjavalibv2.exceptions.BoxServerException;
import com.box.boxjavalibv2.requests.requestobjects.BoxPagingRequestObject;
import com.box.restclientv2.exceptions.BoxRestException;
import com.box.restclientv2.exceptions.BoxSDKException;
import com.box.restclientv2.requestsbase.BoxDefaultRequestObject;

public class BoxMain extends Activity {
	private final static int AUTHENTICATE_REQUEST = 0;
	public final static String SELECTED_BOXMODEL = "selectedBoxModel",SELECTED_CLIENT="selected_client";
	private ListView lvBoxList;
	private BoxListAdpter boxListAdpter;
	private ArrayList<BoxModel> boxModelModelList = new ArrayList<BoxModel>();
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			if (msg.what == 1) {
				setBoxListAdapter();
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.boxmain);
		lvBoxList = (ListView) findViewById(R.id.lvBoxList);
		setBoxListAdapter();
		onClientAuthenticated();
		Button btnCreate = (Button) findViewById(R.id.btnLogin);
		btnCreate.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startAuth();
			}
		});

		lvBoxList.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				Intent intent=new Intent(BoxMain.this, BoxFileManager.class)
				.putExtra(SELECTED_BOXMODEL,
						boxModelModelList.get(position));
				intent.putExtra(SELECTED_CLIENT, position);
				startActivity(intent);

			}
		});
	}

	private void setBoxListAdapter() {
		boxListAdpter = new BoxListAdpter(BoxMain.this, boxModelModelList);
		lvBoxList.setAdapter(boxListAdpter);
	}

	private void startAuth() {
		boxModelModelList=new ArrayList<BoxModel>();
		Intent intent = OAuthActivity.createOAuthActivityIntent(this,
				BoxSDKSampleApplication.CLIENT_ID,
				BoxSDKSampleApplication.CLIENT_SECRET, false);
		startActivityForResult(intent, AUTHENTICATE_REQUEST);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case AUTHENTICATE_REQUEST:
			if (resultCode == Activity.RESULT_CANCELED) {
				if (data != null) {
					String failMessage = data
							.getStringExtra(OAuthActivity.ERROR_MESSAGE);
					Toast.makeText(this, "Auth fail:" + failMessage,
							Toast.LENGTH_LONG).show();
					finish();
				}
			} else {
				BoxAndroidOAuthData oauth = data
						.getParcelableExtra(OAuthActivity.BOX_CLIENT_OAUTH);
				BoxAndroidClient client = new BoxAndroidClient(
						BoxSDKSampleApplication.CLIENT_ID,
						BoxSDKSampleApplication.CLIENT_SECRET, null, null, null);
				client.authenticate(oauth);
				BoxSDKSampleApplication app = (BoxSDKSampleApplication) getApplication();
				client.addOAuthRefreshListener(new OAuthRefreshListener() {

					@Override
					public void onRefresh(IAuthData newAuthData) {

						Log.d("OAuth",
								"oauth refreshed, new oauth access token is:"
										+ newAuthData.getAccessToken());
					}

				});
				app.setClient(client);
				onClientAuthenticated();
			}
			break;
		}

	}

	private void onClientAuthenticated() {
		final ProgressDialog pd = ProgressDialog.show(BoxMain.this, "",
				"Loading...");
		AsyncTask<Null, Void, ArrayList<BoxCollection>> task = new AsyncTask<Null, Void, ArrayList<BoxCollection>>() {

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				Toast.makeText(BoxMain.this, "Making api call.",
						Toast.LENGTH_LONG).show();
			}

			@Override
			protected ArrayList<BoxCollection> doInBackground(Null... params) {

				ArrayList<BoxCollection> boxCollectionItemsList = new ArrayList<BoxCollection>();
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

					for (int i = 0; i < getClient().size(); i++) {
						boxCollectionItemsList.add(getClient().get(i)
								.getFoldersManager()
								.getFolderItems("0", requestObj));
					}
					return boxCollectionItemsList;
				} catch (BoxSDKException e) {

					return boxCollectionItemsList;
				}
			}

			@Override
			protected void onPostExecute(
					final ArrayList<BoxCollection> boxCollectionItemsList) {

				super.onPostExecute(boxCollectionItemsList);

				new Thread(new Runnable() {

					@Override
					public void run() {
//						
						for (int i = 0; i < boxCollectionItemsList.size(); i++) {
							ArrayList<BoxTypedObject> boxObjects = boxCollectionItemsList
									.get(i).getEntries();

							String login = "";
							BoxDefaultRequestObject requestObj = new BoxDefaultRequestObject();

							requestObj.getRequestExtras().addField(
									BoxUser.FIELD_LOGIN);
							try {
								login = getClient().get(i).getUsersManager()
										.getCurrentUser(requestObj).getLogin();

								BoxModel boxModel = new BoxModel();
								boxModel.setId(R.drawable.ic_launcher);
								boxModel.setEmailAddress(login);
								boxModel.setBoxObjects(boxObjects);
								boxModelModelList.add(boxModel);

							} catch (BoxRestException e) {
								pd.dismiss();
								e.printStackTrace();
							} catch (BoxServerException e) {
								pd.dismiss();
								e.printStackTrace();
							} catch (AuthFatalFailureException e) {
								pd.dismiss();
								e.printStackTrace();
							}
						}
						pd.dismiss();
						handler.sendEmptyMessage(1);

					}
				}).start();

			}

		};
		task.execute();
	}

	private ArrayList<BoxAndroidClient> getClient() {
		return ((BoxSDKSampleApplication) getApplication()).getClient();
	}
}
