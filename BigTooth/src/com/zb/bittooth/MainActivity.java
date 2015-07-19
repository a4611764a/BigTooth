package com.zb.bittooth;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TextView;

import com.umeng.analytics.MobclickAgent;
import com.umeng.message.PushAgent;
import com.umeng.update.UmengUpdateAgent;
import com.zb.bittooth.customView.NoSlideViewPager;
import com.zb.bittooth.jokes.JokesFragment;
import com.zb.bittooth.mine.MineFragment;
import com.zb.bittooth.tooth.ToothFragment;

public class MainActivity extends FragmentActivity {
	private TabHost mTabHost;
	private NoSlideViewPager mViewPager;
	private List<Fragment> mFragments = new ArrayList<Fragment>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		UmengUpdateAgent.setUpdateOnlyWifi(false);
		UmengUpdateAgent.update(this);// 友盟自动更新
		mFragments.add(new JokesFragment());
		mFragments.add(new MineFragment());
		mFragments.add(new ToothFragment());

		mViewPager = (NoSlideViewPager) findViewById(R.id.content);
		// mViewPager.setOffscreenPageLimit(mFragments.size());
		mViewPager.setOffscreenPageLimit(3);
		mViewPager.setAdapter(new ViewAdapter(getSupportFragmentManager()));
		mViewPager.setScanScroll(false);

		mTabHost = (TabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup();

		mTabHost.addTab(mTabHost.newTabSpec("0").setIndicator(createView(0))
				.setContent(android.R.id.tabcontent));
		mTabHost.addTab(mTabHost.newTabSpec("1").setIndicator(createView(1))
				.setContent(android.R.id.tabcontent));
		mTabHost.addTab(mTabHost.newTabSpec("2").setIndicator(createView(2))
				.setContent(android.R.id.tabcontent));
		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {

			@Override
			public void onTabChanged(String tabId) {
				int index = Integer.parseInt(tabId);
				mViewPager.setCurrentItem(index, false);
				mTabHost.getTabContentView().setVisibility(View.GONE);
			}
		});
		mTabHost.setCurrentTab(0);
	}

	@SuppressLint("NewApi")
	private View createView(int tabIndex) {
		View view = LayoutInflater.from(this).inflate(R.layout.tabwidget_view,
				null);
		TextView tv = (TextView) view.findViewById(R.id.tv);
		ImageView img = (ImageView) view.findViewById(R.id.img);
		switch (tabIndex) {
		case 0:
			tv.setText("笑料");
			img.setImageResource(R.drawable.main_icon_jokes);
			break;
		case 1:
			tv.setText("我的");
			img.setImageResource(R.drawable.main_icon_mine);
			break;
		case 2:
			tv.setText("大牙");
			img.setImageResource(R.drawable.main_icon_tooth);
			break;
		}
		return view;

	}

	private class ViewAdapter extends FragmentPagerAdapter {

		public ViewAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			return mFragments.get(position);
		}

		@Override
		public int getCount() {
			return mFragments.size();
		}

	}

	@Override
	protected void onResume() {
		super.onResume();
		MobclickAgent.onResume(this);
	}

	public void onPause() {
		super.onPause();
		MobclickAgent.onPause(this);
	}
}