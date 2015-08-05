package com.zb.bittooth.login;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.zb.bittooth.R;
import com.zb.bittooth.Urls;
import com.zb.bittooth.R.drawable;
import com.zb.bittooth.R.id;
import com.zb.bittooth.R.layout;
import com.zb.bittooth.jokes.JokesFragementAdapter;
import com.zb.bittooth.model.Jokes;
import com.zb.bittooth.model.Moble;
import com.zb.bittooth.utils.MobelUtils;
import com.zb.bittooth.utils.T;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class LoginViewPager extends Activity implements OnPageChangeListener,
		OnTouchListener {
	private ViewPager mViewPager;
	private View view1, view2, view3;
	private List<View> list;
	public boolean flag = false;
	private LinearLayout pointLLayout;
	private ImageView[] imgs;
	private int count;
	private int currentItem;
	private int lastX = 0;// 获得当前X坐标
	private HttpUtils httpUtils = null;
	RequestParams params = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_viewpager);
		pointLLayout = (LinearLayout) findViewById(R.id.llayout);
		count = pointLLayout.getChildCount();
		// Log.d("aaaa", "" + count);
		imgs = new ImageView[count];
		for (int i = 0; i < count; i++) {
			imgs[i] = (ImageView) pointLLayout.getChildAt(i);
			// state_enabled 点
			imgs[i].setEnabled(true);
			imgs[i].setTag(i);
		}
		currentItem = 0;
		imgs[currentItem].setEnabled(false);
		mViewPager = (ViewPager) findViewById(R.id.viewPager);
		mViewPager.setOnPageChangeListener(this);
		mViewPager.setOnTouchListener(this);
		LayoutInflater inflater = LayoutInflater.from(LoginViewPager.this);
		list = new ArrayList<View>();
		SharedPreferences sp = getSharedPreferences("loding", 0);
		flag = sp.getBoolean("loding_flag", false);
		// if (true) {
		if (!flag) {
			postMobleInfor();
			initpage(inflater);
		} else {
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					// TODO Auto-generated method stub
					Intent intent = new Intent(LoginViewPager.this,
							LoginSplash.class);
					startActivity(intent);
					finish();
				}
			}, 0);
		}
	}

	/**
	 * 
	 * @Title: initpage
	 * @Description: TODO(用的同一个布局。不同的image)
	 * @param @param flater 设定文件
	 * @return void 返回类型
	 * @throws
	 */
	public void initpage(LayoutInflater flater) {
		view1 = flater.inflate(R.layout.login_loading, null);
		view1.setBackgroundResource(R.drawable.loding_1);
		view2 = flater.inflate(R.layout.login_loading, null);
		view2.setBackgroundResource(R.drawable.loding_2);
		view3 = flater.inflate(R.layout.login_loading, null);
		view3.setBackgroundResource(R.drawable.loding_3);
		/*
		 * View view4 = flater.inflate(R.layout.loading, null);
		 * view4.setBackgroundResource(R.drawable.loding_2);
		 */
		list.add(view1);
		list.add(view2);
		list.add(view3);
		/*
		 * //这里加了第四个页面，但是固定的dot图标没有 list.add(view4);
		 */
		mViewPager.setAdapter(pager);
	}

	private void setcurrentPoint(int position) {
		if (position < 0 || position > count - 1 || currentItem == position) {
			return;
		}
		imgs[currentItem].setEnabled(true);
		imgs[position].setEnabled(false);
		currentItem = position;
	}

	PagerAdapter pager = new PagerAdapter() {

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView(list.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			// TODO Auto-generated method stub
			container.addView(list.get(position));

			return list.get(position);
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.support.v4.view.ViewPager.OnPageChangeListener#
	 * onPageScrollStateChanged(int)
	 */
	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageScrolled
	 * (int, float, int)
	 */
	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.support.v4.view.ViewPager.OnPageChangeListener#onPageSelected
	 * (int)
	 */
	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		setcurrentPoint(arg0);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.View.OnTouchListener#onTouch(android.view.View,
	 * android.view.MotionEvent)
	 */
	@Override
	public boolean onTouch(View arg0, MotionEvent arg1) {
		// TODO Auto-generated method stub
		switch (arg1.getAction()) {
		case MotionEvent.ACTION_DOWN:
			lastX = (int) arg1.getX();
			break;

		case MotionEvent.ACTION_UP:
			if (!flag) {
				if ((lastX - arg1.getX() > 100)
						&& (mViewPager.getCurrentItem() == mViewPager
								.getAdapter().getCount() - 1)) {// 从最后一页向右滑动
					new Handler().postDelayed(new Runnable() {
						public void run() {
							SharedPreferences sp = getSharedPreferences(
									"loding", 0);
							SharedPreferences.Editor et = sp.edit();
							et.putBoolean("loding_flag", true);
							et.commit();
							Intent intent = new Intent(LoginViewPager.this,
									LoginSplash.class);
							startActivity(intent);
							finish();
						};
					}, 0);
				}
			}
		}
		return false;
	}

	// 上传用户手机信息
	public void postMobleInfor() {
		httpUtils = new HttpUtils();
		HttpUtils.sHttpCache.setEnabled(HttpMethod.POST, false);
		params = new RequestParams();
		params.addQueryStringParameter("uuid",
				MobelUtils.getMobelUUid(LoginViewPager.this));
		httpUtils.send(HttpRequest.HttpMethod.POST, Urls.url_post_uuid, params,
				new RequestCallBack<String>() {
					@Override
					public void onStart() {
					}

					@Override
					public void onLoading(long total, long current,
							boolean isUploading) {
						if (isUploading) {
						} else {
						}
					}

					@Override
					public void onSuccess(ResponseInfo<String> responseInfo) {
						T.showShort(LoginViewPager.this, responseInfo.result
								+ "");
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(LoginViewPager.this, error + msg,
								Toast.LENGTH_SHORT).show();
					}
				});
	}
}
