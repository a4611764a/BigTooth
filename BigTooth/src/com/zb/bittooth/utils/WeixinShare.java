package com.zb.bittooth.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zb.bittooth.App;
import com.zb.bittooth.R;
import com.zb.bittooth.Urls;

public class WeixinShare {
	private  IWXAPI wxApi;
	private   Context context;
		public WeixinShare(Context context){
			this.context=context;
			//实例化  
			wxApi = WXAPIFactory.createWXAPI(context, Urls.WX_APP_ID);  
			wxApi.registerApp(Urls.WX_APP_ID);  
		}

	/** 
	 * 微信分享 （这里仅提供一个分享网页的示例，其它请参看官网示例代码） 
	 * @param flag(0:分享到微信好友，1：分享到微信朋友圈) 
	 */  
		//在需要分享的地方添加代码：  
		//wechatShare(0);//分享到微信好友  
		//wechatShare(1);//分享到微信朋友圈  
	public  void wechatShare(int flag,String url,String title,String description, String image_url ){  
	    WXWebpageObject webpage = new WXWebpageObject();  
	    webpage.webpageUrl = url;  
	    WXMediaMessage msg = new WXMediaMessage(webpage);  
	    msg.title = title;  
	    msg.description = description;
	    Bitmap thumb = null;
	    //这里替换一张自己工程里的图片资源
	    if(image_url==null)
	    thumb = BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher); 
	    else{
	    thumb=getImageUrl(image_url);
	    }
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
} 
