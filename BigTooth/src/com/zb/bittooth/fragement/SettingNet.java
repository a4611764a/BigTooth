package com.zb.bittooth.fragement;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.lidroid.xutils.ViewUtils;
import com.zb.bittooth.MyEvent;
import com.zb.bittooth.R;

public class SettingNet extends Fragment {
	View rootView;
	private Context mContext;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.fragement_net, null);
		ViewUtils.inject(this, rootView); // 注入view和事件
		mContext = getActivity();
		rootView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				addShicai();
			}
		});
		return rootView;
	}

	public void addShicai() {
		// 跳转到系统的网络设置界面
		Intent intent = null;
		// 先判断当前系统版本
		if (android.os.Build.VERSION.SDK_INT > 10) { // 3.0以上
			intent = new Intent(android.provider.Settings.ACTION_SETTINGS);
		} else {
			intent = new Intent();
			intent.setClassName("com.android.settings", "com.android.settings");
		}
		mContext.startActivity(intent);
	}
}
