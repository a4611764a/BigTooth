package com.zb.bittooth.mine;

import java.util.List;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.zb.bittooth.R;
import com.zb.bittooth.customView.CircleImageView;
import com.zb.bittooth.customView.RoundedImageView;
import com.zb.bittooth.model.Jokes;
import com.zb.bittooth.model.Near;

public class NearFragementAdapter extends BaseAdapter {

	private List<Near> mList;
	private Context mContext;
	private boolean canLoadImage = true;
	// 显示图片的配置
	DisplayImageOptions options = new DisplayImageOptions.Builder()
			.showImageOnLoading(com.zb.bittooth.R.drawable.default_img)
			.showImageOnFail(com.zb.bittooth.R.drawable.default_img)
			.cacheInMemory(true).cacheOnDisk(true)
			.bitmapConfig(Bitmap.Config.RGB_565).build();

	public NearFragementAdapter(Context context, List<Near> list) {
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
					R.layout.mine_frame_near_item, null);
			holder = new ViewHolder();
			holder.img_head = (RoundedImageView) convertView
					.findViewById(R.id.img_head);
			holder.name = (TextView) convertView.findViewById(R.id.name);
			holder.count = (TextView) convertView.findViewById(R.id.count);
			holder.distance = (TextView) convertView
					.findViewById(R.id.distance);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(mList.get(position).getUserName());
		/*
		 * String count =
		 * String.format(mContext.getResources().getString(R.string
		 * .jokes_count), Integer.parseInt(mList.get(position).getCount()));
		 * SpannableStringBuilder style = new SpannableStringBuilder(count);
		 * style.setSpan(new ForegroundColorSpan(Color.RED),
		 * 2,2+mList.get(position).getCount().length()
		 * ,Spannable.SPAN_EXCLUSIVE_INCLUSIVE); holder.count.setText(style);
		 */
		holder.distance.setText(mList.get(position).getDistance() + "m");
		if (canLoadImage) {
			ImageLoader.getInstance().displayImage(
					mList.get(position).getImage_head(), holder.img_head,
					options);
		}
		return convertView;
	}

	static class ViewHolder {
		public RoundedImageView img_head;
		public TextView name, count, distance;
	}
}
