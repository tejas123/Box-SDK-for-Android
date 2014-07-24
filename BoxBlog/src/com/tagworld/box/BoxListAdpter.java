package com.tagworld.box;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class BoxListAdpter extends BaseAdapter {

	private LayoutInflater inflater;
	private ArrayList<BoxModel> networkModelList;

	public BoxListAdpter(Context context, ArrayList<BoxModel> networkModelList) {
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		// this.networkModelList = Constants.networkModelList;
		this.networkModelList = networkModelList;
	}

	@Override
	public int getCount() {
		return networkModelList.size();
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
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		final Holder holder;
		if (view == null) {
			view = inflater.inflate(R.layout.boxinflater, null);
			holder = new Holder();
			holder.tvEmail = (TextView) view.findViewById(R.id.tvEmailAddress);

			view.setTag(holder);
		}

		else {
			holder = (Holder) view.getTag();
		}
		BoxModel networkModel = networkModelList.get(position);
		holder.tvEmail.setText(networkModel.getEmailAddress());
		return view;
	}

	private class Holder {
		TextView tvEmail;

	}

}
