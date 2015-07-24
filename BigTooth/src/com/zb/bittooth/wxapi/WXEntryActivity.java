package com.zb.bittooth.wxapi;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;

import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;
import com.zb.bittooth.MyEvent;
import com.zb.bittooth.Urls;

import de.greenrobot.event.EventBus;

/** 微信客户端回调activity示例 */  
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {  
    // IWXAPI 是第三方app和微信通信的openapi接口  
    private IWXAPI api;  
    private MyEvent myEvent;
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        api = WXAPIFactory.createWXAPI(this, Urls.WX_APP_ID, false);  
        api.handleIntent(getIntent(), this);  
      
        super.onCreate(savedInstanceState);  
    }  
    @Override  
    public void onReq(BaseReq arg0) { }  
  
    @Override  
    public void onResp(BaseResp resp) {  
        Log.v("weixin", "resp.errCode:" + resp.errCode + ",resp.errStr:"  
                + resp.errStr);  
        myEvent=new MyEvent();
        switch (resp.errCode) {  
        case BaseResp.ErrCode.ERR_OK:  
            //分享成功
        	myEvent.setShareResult("1");
        	EventBus.getDefault().post(myEvent);
        	finish();
        	
            break;  
        case BaseResp.ErrCode.ERR_USER_CANCEL:  
            //分享取消  
        	myEvent.setShareResult("0");
        	EventBus.getDefault().post(myEvent);
        	finish();
            break;  
        case BaseResp.ErrCode.ERR_AUTH_DENIED:  
            //分享拒绝  
        	myEvent.setShareResult("0");
        	EventBus.getDefault().post(myEvent);
        	finish();
            break;  
        }  
        
    }  
}  