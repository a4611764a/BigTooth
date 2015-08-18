package com.zb.bittooth.jokes;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zb.bittooth.R;
import com.zb.bittooth.customView.CustomProgressDialog;
import com.zb.bittooth.utils.T;

public class JokesActivity_img extends Activity {
	//fffffffffffff
	Gson gson = new Gson();
	private HttpUtils httpUtils = null;
	CustomProgressDialog dialog;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jokes_activity_img);
		ViewUtils.inject(this);
		dialog = new CustomProgressDialog(this, "正在加载中", R.anim.load_frame);
	}

	@OnClick(R.id.top1)
	public void goTop(View v) {
		Intent intent=new Intent();
		intent.setClass(this,ViewPagerActivity.class );
		startActivity(intent);
	//	intent.putExtra("key", );
	}
}
