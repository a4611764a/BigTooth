/**
 * 
 */
package com.zb.bittooth.jokes;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.bittooth.App;
import com.zb.bittooth.R;
import com.zb.bittooth.customView.CircleImageView;
import com.zb.bittooth.customView.MyWind8ImageView;
import com.zb.bittooth.model.Jokes;
import com.zb.bittooth.utils.T;

public class JokesFragementAdapter extends BaseAdapter {

	private List<Jokes> mList;
	private Context mContext;
	private boolean canLoadImage = true;

	public JokesFragementAdapter(Context context, List<Jokes> list) {
		this.mContext = context;
		this.mList = list;
	}

	@Override
	public int getCount() {
		return mList.size();
	}

	@Override
	public Object getItem(int position) {
		return mList.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		final ViewHolder holder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.jokes_frame_main_item, null);
			holder = new ViewHolder();
			holder.img_head = (CircleImageView) convertView
					.findViewById(R.id.img_head);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.content = (TextView) convertView.findViewById(R.id.content);
			holder.tag1 = (TextView) convertView.findViewById(R.id.tag1);
			holder.shoucangl=(MyWind8ImageView) convertView.findViewById(R.id.shoucang);
			// holder.tag2 = (TextView) convertView.findViewById(R.id.tag2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(mList.get(position).getUserName());
		holder.content.setText(mList.get(position).getContent());
		holder.tag1.setText(mList.get(position).getTag());
		if (canLoadImage) {
			ImageLoader.getInstance().displayImage(
					mList.get(position).getImgHead(), holder.img_head, App.options,App.mImageLoadingListenerImpl);
		}
		holder.shoucangl.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				T.showShort(mContext, "dd");
			}
		});
		return convertView;
	}

	static class ViewHolder {
		public CircleImageView img_head;
		public TextView name, content, tag1;
		public MyWind8ImageView shoucangl;
	}
}
