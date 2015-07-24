/**
 * 
 */
package com.zb.bittooth.jokes;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zb.bittooth.App;
import com.zb.bittooth.MainActivity;
import com.zb.bittooth.R;
import com.zb.bittooth.Urls;
import com.zb.bittooth.customView.CircleImageView;
import com.zb.bittooth.customView.CoutomDialog;
import com.zb.bittooth.customView.MyWind8ImageView;
import com.zb.bittooth.model.Jokes;
import com.zb.bittooth.utils.T;
import com.zb.bittooth.utils.WeixinShare;

public class JokesFragementAdapter extends BaseAdapter {

	private List<Jokes> mList;
	private Context mContext;
	WeixinShare share;
	private boolean canLoadImage = true;
	private  IWXAPI wxApi;
	public JokesFragementAdapter(Context context, List<Jokes> list) {
		this.mContext = context;
		this.mList = list;
		wxApi = WXAPIFactory.createWXAPI(context, Urls.WX_APP_ID);  
		wxApi.registerApp(Urls.WX_APP_ID);  
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

	@SuppressLint("NewApi") @Override
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
			holder.shoucangl = (MyWind8ImageView) convertView
					.findViewById(R.id.shoucang);
			holder.share = (MyWind8ImageView) convertView
					.findViewById(R.id.share);
			// holder.tag2 = (TextView) convertView.findViewById(R.id.tag2);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.name.setText(mList.get(position).getUserName());
		holder.content.setText(mList.get(position).getContent());
		holder.tag1.setText(mList.get(position).getTag());
		if(mList.get(position).getIsShouCang()==null){
			holder.shoucangl.setBackground(mContext.getResources().getDrawable(R.drawable.radar_card_around_collect_highlighted));
		}
		else {
		if(mList.get(position).getIsShouCang().equals("1")){
			holder.shoucangl.setBackground(mContext.getResources().getDrawable(R.drawable.radar_card_around_collect_lighted));
		}
		else{
			holder.shoucangl.setBackground(mContext.getResources().getDrawable(R.drawable.radar_card_around_collect_highlighted));
		}
		}
		if (canLoadImage) {
			if (holder.img_head.getTag() == null|| !holder.img_head.getTag().toString().equals(mList.get(position).getImgHead())) {
				ImageLoader.getInstance().displayImage(
						mList.get(position).getImgHead(), holder.img_head,
						App.options, App.mImageLoadingListenerImpl);
				holder.img_head.setTag(mList.get(position).getImgHead());
			}
			
		}
		holder.shoucangl.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi") @Override
			public void onClick(View arg0) {
				if(mList.get(position).getIsShouCang()==null||mList.get(position).getIsShouCang().equals("0")){
				holder.shoucangl.setBackground(mContext.getResources().getDrawable(R.drawable.radar_card_around_collect_lighted));
				mList.get(position).setIsShouCang("1");
				}
				else{
					holder.shoucangl.setBackground(mContext.getResources().getDrawable(R.drawable.radar_card_around_collect_highlighted));
					mList.get(position).setIsShouCang("0");
				}
				notifyDataSetChanged();
			}
		});
		holder.share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				/*wechatShare(1, "", mList.get(position).getTag(), mList
						.get(position).getContent(), mList.get(position)
						.getImgHead());*/
			//	wechatShare(1);//分享到微信朋友圈
				showShareDialog();
			}
		});
		return convertView;
	}

	static class ViewHolder {
		public CircleImageView img_head;
		public TextView name, content, tag1;
		public MyWind8ImageView shoucangl, share;
	}
	/** 
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码） 
	 * @param flag(0:分享到微信好友，1：分享到微信朋友圈) 
	 */  
	public  void wechatShare(int flag){
	    WXWebpageObject webpage = new WXWebpageObject();  
	    webpage.webpageUrl = "http://www.bigTooth.com";  
	    WXMediaMessage msg = new WXMediaMessage(webpage);  
	    msg.title = "好人";  
	    msg.description = "好好好好好好好好好好好好好好好好好好好好好好好好好好好";
	    Bitmap thumb = null;
	    //这里替换一张自己工程里的图片资源
	 //   if(image_url==null)
	    thumb = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_launcher); 
	 //   else{
	//    thumb=getImageUrl(image_url);
	 //   }
	    msg.setThumbImage(thumb);  
	    SendMessageToWX.Req req = new SendMessageToWX.Req();  
	    req.transaction = String.valueOf(System.currentTimeMillis());  
	    req.message = msg;  
	    req.scene = flag==0?SendMessageToWX.Req.WXSceneSession:SendMessageToWX.Req.WXSceneTimeline;  
	    wxApi.sendReq(req);  
	}  
	//根据一个url得到图片的BitMap。
	public Bitmap getImageUrl(String image_url){
		ImageSize targetSize = new ImageSize(80, 50); // result Bitmap will be fit to this size
		Bitmap bmp = ImageLoader.getInstance().loadImageSync(image_url, targetSize, App.options);
		return bmp;
	}
	public boolean showShareDialog(){
		final CoutomDialog dialog=new CoutomDialog(mContext,R.style.MyDialog);
		MyWind8ImageView friends=(MyWind8ImageView) dialog.findViewById(R.id.share_friends);
		friends.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wechatShare(1);
				dialog.dismiss();
			}
		});
		MyWind8ImageView share_friend_private=(MyWind8ImageView) dialog.findViewById(R.id.share_friend_private);
		share_friend_private.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				wechatShare(0);
				dialog.dismiss();
			}
		});
		dialog.show();
		return true;
		
	}
}
