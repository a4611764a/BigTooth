package com.zb.bittooth.mine;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.umeng.analytics.MobclickAgent;
import com.zb.bittooth.R;

public class MineFragment extends Fragment {
	private List<Fragment> mFragmentList;
	private MineAdapter adapter;
	DripFragement dripFragement;
	NearFragement nearFragement;
	Context context;
	private int currentIndex;// ViewPager的当前选中页

	@ViewInject(R.id.vp_Mine)
	ViewPager vp_Mine;
	@ViewInject(R.id.btn_near)
	Button btn_near;
	@ViewInject(R.id.btn_dirp)
	Button btn_dirp;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.mine_frame_main, null);
		context = getActivity();
		ViewUtils.inject(this, view); // 注入view和事件
		initView();
		vp_Mine.setOnPageChangeListener(new OnPageChangeListener() {
			@Override
			public void onPageSelected(int position) {
				switch (position) {
				case 0:
					btn_near.setBackgroundResource(R.drawable.mine_title_bg_y_l);
					btn_near.setTextColor(getResources()
							.getColor(R.color.white));
					btn_dirp.setBackgroundResource(R.drawable.mine_title_bg_n_r);
					btn_dirp.setTextColor(getResources().getColor(
							R.color.deep_orange));
					break;
				case 1:
					btn_near.setBackgroundResource(R.drawable.mine_title_bg_n_r);
					btn_near.setTextColor(getResources().getColor(
							R.color.deep_orange));
					btn_dirp.setBackgroundResource(R.drawable.mine_title_bg_y_r);
					btn_dirp.setTextColor(getResources()
							.getColor(R.color.white));
					break;
				}
				currentIndex = position;
			}

			@Override
			public void onPageScrolled(int position, float offset, int arg2) {

				if (currentIndex == 0 && position == 0)// 0->1
				{

				} else if (currentIndex == 1 && position == 0) // 1->0
				{

				}
			}

			/**
			 * state滑动中的状态 有三种状态（0，1，2） 1：正在滑动 2：滑动完毕 0：什么都没做。
			 */
			@Override
			public void onPageScrollStateChanged(int arg0) {

			}

		});
		return view;
	}

	@Override
	public void onResume() {
		super.onResume();
		MobclickAgent.onPageStart("MainScreen");
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPageEnd("MainScreen");
	}

	private void initView() {
		mFragmentList = new ArrayList<Fragment>();
		dripFragement = new DripFragement();
		nearFragement = new NearFragement();
		mFragmentList.add(nearFragement);
		mFragmentList.add(dripFragement);
		adapter = new MineAdapter(this.getFragmentManager(), mFragmentList);
		vp_Mine.setAdapter(adapter);
		vp_Mine.setCurrentItem(0);
	}

	@OnClick(R.id.btn_near)
	public void btn_near(View v) {
		vp_Mine.setCurrentItem(0);
	}

	@OnClick(R.id.btn_dirp)
	public void btn_dirp(View v) {
		vp_Mine.setCurrentItem(1);
	}

	@OnClick(R.id.service)
	public void goToService(View v) {
		startActivity(new Intent(context, Service.class));
	}
}
