package com.zb.bittooth.login;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.umeng.analytics.AnalyticsConfig;
import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.zb.bittooth.MainActivity;
import com.zb.bittooth.R;
import com.zb.bittooth.model.Moble;

import de.greenrobot.event.EventBus;

public class LoginSplash extends Activity {
	private LocationClient locationClient = null;
	private Moble moble = new Moble();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		AnalyticsConfig.enableEncrypt(true);// 日志加密
		final View view = View.inflate(this, R.layout.login_splash, null);
		setContentView(view);
		PushAgent mPushAgent = PushAgent.getInstance(this);
		mPushAgent.enable(); // 友盟推送
		PushAgent.getInstance(this).onAppStart();
		locationClient = new LocationClient(getApplicationContext());
		// 设置定位条件
		LocationClientOption option = new LocationClientOption();
		option.setOpenGps(true);// 打开gps
		option.setCoorType("bd09ll"); // 设置坐标类型
		option.setScanSpan(1000);
		option.setAddrType("all");
		locationClient.setLocOption(option);
		locationClient.start();
		// 地理定位
		locationClient.requestLocation();
		// 注册位置监听器
		locationClient.registerLocationListener(new BDLocationListener() {
			@Override
			public void onReceiveLocation(BDLocation location) {
				// TODO Auto-generated method stub
				if (location == null) {
					return;
				}
				moble.setLongitude(location.getLongitude() + "");
				moble.setLatitude(location.getLatitude() + "");
				moble.setCity(location.getCity());
				moble.setAddress(location.getDistrict()+location.getStreet()+ "");
				moble.setLocation(locationClient);
				if (moble.getCity() != null) {
					EventBus.getDefault().post(moble);
				}
			}
		});
		// 、、、、、、、、、、、、、、、、、、、

		AlphaAnimation aa = new AlphaAnimation(0.3f, 1.0f);
		aa.setDuration(2500);
		view.startAnimation(aa);
		aa.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationEnd(Animation arg0) {

				/* if(isNetworkAvailable(Login.this)) */
				redirectTo();
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}

			@Override
			public void onAnimationStart(Animation animation) {
			}

		});
	}

	public static boolean isNetworkAvailable(Context ctx) {
		boolean bl = false;
		ConnectivityManager cm = (ConnectivityManager) ctx
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info == null || !info.isAvailable()) {
			bl = false;
		} else {
			bl = true;
		}
		return bl;
	}

	//
	private void redirectTo() {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
		finish();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		finish();
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("SplashScreen");
		MobclickAgent.onResume(this);
	}

	@Override
	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("SplashScreen");
		MobclickAgent.onPause(this);
	}
}
