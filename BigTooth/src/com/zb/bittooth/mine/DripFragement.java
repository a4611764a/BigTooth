package com.zb.bittooth.mine;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.lidroid.xutils.ViewUtils;
import com.zb.bittooth.R;
import com.zb.bittooth.customView.ExtendedListView;
import com.zb.bittooth.customView.ExtendedListView.OnPositionChangedListener;

public class DripFragement extends Fragment implements OnPositionChangedListener {
	private ExtendedListView mListView;
	Context context;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mine_frame_drip, null);
		context = getActivity();
		ViewUtils.inject(this, view); // 注入view和事件
		mListView = (ExtendedListView) view.findViewById(android.R.id.list);
		mListView.setAdapter(new DummyAdapter());
		mListView.setCacheColorHint(Color.TRANSPARENT);
		mListView.setOnPositionChangedListener(this);
		return view;
	}
	private class DummyAdapter extends BaseAdapter {

		private int mNumDummies = 100;

		@Override
		public int getCount() {
			return mNumDummies;
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
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(R.layout.mine_frame_drip__item, parent,
						false);
			}

			TextView textView = (TextView) convertView;
			textView.setText("" + position);

			return convertView;
		}
	}

	@Override
	public void onPositionChanged(ExtendedListView listView, int firstVisiblePosition, View scrollBarPanel) {
		((TextView) scrollBarPanel).setText("Position " + firstVisiblePosition+"dfasdfad");
	}
}
