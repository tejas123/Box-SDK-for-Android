package com.tagworld.box;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.format.Formatter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.box.boxandroidlibv2.dao.BoxAndroidFile;
import com.box.boxandroidlibv2.dao.BoxAndroidFolder;
import com.box.boxjavalibv2.dao.BoxItem;
import com.box.boxjavalibv2.dao.BoxTypedObject;

public class DirBoxListAdapter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<BoxTypedObject> filesList;
	private SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(
			"dd-M-yyyy kk:mm");
	private BoxFileManager boxFileManagerAct;
	private Handler handler;

	public DirBoxListAdapter(BoxFileManager boxFileManagerAct,
			ArrayList<BoxTypedObject> filesList, Handler handler) {
		this.boxFileManagerAct = boxFileManagerAct;
		this.filesList = filesList;
		this.handler = handler;
		inflater = (LayoutInflater) this.boxFileManagerAct
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		return filesList.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {

		View view = convertView;
		final Holder holder;
		// view = convertView;
		if (view == null) {
			holder = new Holder();
			// view = lInflater.inflate(R.layout.directory_list_inflater, null);
			view = (LinearLayout) inflater.inflate(
					R.layout.directory_list_inflater, parent, false);

			holder.ivDirectoryListInflaterType = (ImageView) view
					.findViewById(R.id.ivDirectoryListInflaterType);
			holder.tvDirectoryListInflaterName = (TextView) view
					.findViewById(R.id.tvDirectoryListInflaterName);
			holder.tvDirectoryListInflaterSize = (TextView) view
					.findViewById(R.id.tvDirectoryListInflaterSize);
			holder.tvDirectoryListInflaterDate = (TextView) view
					.findViewById(R.id.tvDirectoryListInflaterDate);
//			holder.ivBoxDownload = (ImageView) view
//					.findViewById(R.id.ivBoxDownload);
			holder.llMain = (LinearLayout) view.findViewById(R.id.llMain);

			view.setTag(holder);
		} else
			holder = (Holder) view.getTag();

		BoxTypedObject item = filesList.get(position);

		BoxItem boxItem = (BoxItem) item;

		holder.tvDirectoryListInflaterDate.setText(DATE_FORMAT.format(boxItem
				.dateModifiedAt()));

		holder.tvDirectoryListInflaterSize.setText(Formatter.formatFileSize(
				boxFileManagerAct, boxItem.getSize().longValue()));
		holder.tvDirectoryListInflaterName.setText(boxItem.getName());
		if (item instanceof BoxAndroidFolder) {
			// BoxAndroidFolder folder = (BoxAndroidFolder) item;
			holder.ivDirectoryListInflaterType
					.setImageResource(R.drawable.folder);
			

		} else if (item instanceof BoxAndroidFile) {
			// BoxAndroidFile file = (BoxAndroidFile) item;
			holder.ivDirectoryListInflaterType
					.setImageResource(R.drawable.defaultfile);
		}
		holder.llMain.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Message message=new Message();
				message.what=0;
				message.arg1=position;
				handler.sendMessage(message);
			}
		});
//		holder.ivBoxDownload.setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Message message=new Message();
//				message.what=1;
//				message.arg1=position;
//				handler.sendMessage(message);
//			}
//		});
		return view;
	}

	private class Holder {
		ImageView ivDirectoryListInflaterType;//, ivBoxDownload;
		TextView tvDirectoryListInflaterName, tvDirectoryListInflaterDate,
				tvDirectoryListInflaterSize;
		LinearLayout llMain;
	}

}
