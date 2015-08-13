package com.zb.bittooth.jokes;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.umeng.analytics.MobclickAgent;
import com.zb.bittooth.MyEvent;
import com.zb.bittooth.NetReceiver;
import com.zb.bittooth.R;
import com.zb.bittooth.Urls;
import com.zb.bittooth.customView.AutoListView;
import com.zb.bittooth.customView.AutoListView.OnLoadListener;
import com.zb.bittooth.customView.CustomProgressDialog;
import com.zb.bittooth.customView.JokesxuehuaView;
import com.zb.bittooth.customView.PullToRefreshView;
import com.zb.bittooth.customView.PullToRefreshView.PullToRefreshListener;
import com.zb.bittooth.model.Jokes;
import com.zb.bittooth.model.Moble;
import com.zb.bittooth.utils.MobelUtils;
import com.zb.bittooth.utils.SPUtils;
import com.zb.bittooth.utils.T;

import de.greenrobot.event.EventBus;

public class JokesFragment extends Fragment implements OnLoadListener {
	NetReceiver mReceiver = new NetReceiver();
	IntentFilter mFilter = new IntentFilter();
	//
	Gson gson = new Gson();
	private HttpUtils httpUtils = null;
	RequestParams params = null;
	CustomProgressDialog dialog;
	private int page = 1;// 页码
	Context context;
	List<Jokes> list = new ArrayList<Jokes>();
	List<Jokes> list_show = new ArrayList<Jokes>();
	JokesFragementAdapter adapter;
	@ViewInject(R.id.pull)
	PullToRefreshView pullToRefreshView;
	@ViewInject(R.id.listView)
	AutoListView listView;
	@ViewInject(R.id.ll_netState)
	LinearLayout ll_netState;
	@ViewInject(R.id.city)
	TextView city;
	@ViewInject(R.id.goto_top)
	ImageView goto_top;
	//
	/** Called when the activity is first created. */
	private JokesxuehuaView mFlowerView;
	// 屏幕宽度
	public static int screenWidth;
	// 屏幕高度
	public static int screenHeight;
	Timer myTimer = null;
	TimerTask mTask = null;
	private static final int SNOW_BLOCK = 1;
	private Handler mHandler = new Handler() {
		public void dispatchMessage(Message msg) {
			mFlowerView.inva();
		};
	};
	//
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.jokes_frame_main, null);
		context = getActivity();
		EventBus.getDefault().register(this);
		ViewUtils.inject(this, view); // 注入view和事件
		mFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		context.registerReceiver(mReceiver, mFilter);
		dialog = new CustomProgressDialog(context, "正在加载中", R.anim.load_frame);
		httpUtils = new HttpUtils();
		HttpUtils.sHttpCache.setEnabled(HttpMethod.GET, false);
		pullToRefreshView.setOnRefreshListener(new PullToRefreshListener() {
			@Override
			public void onRefresh() {
				try {
					Thread.sleep(3000);
					page = 1;
					// list_show.removeAll(list_show);
					getNetDate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}

				pullToRefreshView.finishRefreshing();
			}
		}, 0);
		// 雪花漂浮
		mFlowerView = (JokesxuehuaView) view.findViewById(R.id.flowerview);
		screenWidth = ((Activity) context).getWindow().getWindowManager()
				.getDefaultDisplay().getWidth();
		screenHeight = ((Activity) context).getWindow().getWindowManager()
				.getDefaultDisplay().getHeight();

		DisplayMetrics dis = new DisplayMetrics();
		((Activity) context).getWindowManager().getDefaultDisplay()
				.getMetrics(dis);
		float de = dis.density;
		mFlowerView.setWH(screenWidth, screenHeight, de);
		mFlowerView.loadFlower();
		mFlowerView.addRect();

		myTimer = new Timer();
		mTask = new TimerTask() {
			@Override
			public void run() {
				Message msg = new Message();
				msg.what = SNOW_BLOCK;
				mHandler.sendMessage(msg);
			}
		};
		myTimer.schedule(mTask, 3000, 10);
		// 雪花漂浮
		getNetDate();
		listView.setOnLoadListener(this);
		adapter = new JokesFragementAdapter(context, list_show);
		listView.setAdapter(adapter);
	//	listView.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(), true, false));
    //    gridView.setOnScrollListener(new PauseOnScrollListener(imageLoader, pauseOnScroll, pauseOnFling));

		return view;
	}

	private void getNetDate() {
		httpUtils.send(HttpRequest.HttpMethod.GET, Urls.url_get_jokes
				+ "?page=" + page, new RequestCallBack<String>() {
			@Override
			public void onStart() {
				if(page==1)
				dialog.show();
			}

			@Override
			public void onLoading(long total, long current, boolean isUploading) {
				if (isUploading) {
				} else {
				}
			}

			@Override
			public void onSuccess(ResponseInfo<String> responseInfo) {
				listView.onLoadComplete();
				list = gson.fromJson(responseInfo.result,
						new TypeToken<List<Jokes>>() {
						}.getType());
				if (page == 1) {
					list_show.removeAll(list_show);
				}
				list_show.addAll(list);
				listView.setResultSize(list.size());
				adapter.notifyDataSetChanged();
				dialog.dismiss();
			}

			@Override
			public void onFailure(HttpException error, String msg) {
				Toast.makeText(context, error + msg, Toast.LENGTH_SHORT).show();
				dialog.dismiss();
			}
		});
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

	@Override
	public void onStart() {
		super.onStart();
	}

	@Override
	public void onDestroyView() {
		// TODO 自动生成的方法存根
		super.onDestroyView();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		context.unregisterReceiver(mReceiver);
		EventBus.getDefault().unregister(this);
		dialog.cancel();
	}

	public void onEventMainThread(MyEvent myevent) {
		FragmentTransaction transaction = ((Activity) context)
				.getFragmentManager().beginTransaction();
		if (myevent.getITEMS() != null && myevent.getITEMS().equals("0")) {
			ll_netState.setVisibility(View.VISIBLE);
			transaction.commit();
		} else if (myevent.getITEMS() != null && myevent.getITEMS().equals("1")) {
			ll_netState.setVisibility(View.GONE);
			if (list_show.size() <= 0) {
				page = 1;
				getNetDate();
			}
		}
		if (myevent.getIsShow() != null && myevent.getIsShow().equals("1")) {
			goto_top.setVisibility(View.VISIBLE);
		} else if (myevent.getIsShow() != null
				&& myevent.getIsShow().equals("0")) {
			goto_top.setVisibility(View.GONE);
		}
		if(myevent.getShareResult()!=null&&myevent.getShareResult().equals("1")){
			T.showShort(context, "分享成功");
		}
		else if(myevent.getShareResult()!=null&&myevent.getShareResult().equals("0")){
			T.showShort(context, "分享失败");
		}
	}

	public void onEventMainThread(Moble moble) {
		if (moble != null) {
			city.setText(moble.getCity());
			postMobleInfor(moble);
			moble.getLocation().stop();
			SPUtils.put(context, "address", moble.getAddress());
		}
	}

	// 上传用户手机信息
	public void postMobleInfor(Moble moble) {
		HttpUtils.sHttpCache.setEnabled(HttpMethod.POST, false);
		params = new RequestParams();
		params.addQueryStringParameter("longitude", moble.getLongitude());
		params.addQueryStringParameter("latitude", moble.getLatitude());
		params.addQueryStringParameter("uuid", MobelUtils.getMobelUUid(context));
		if (moble.getCity() != null)
			params.addQueryStringParameter("city", moble.getCity());
		httpUtils.send(HttpRequest.HttpMethod.POST, Urls.url_post_userInfo,
				params, new RequestCallBack<String>() {
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
						// T.showShort(context, responseInfo.result+"");
						if (responseInfo.result.equals("0")) {
							T.showShort(context, "系统错误");
						} else {
						}

					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(context, error + msg, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	@OnClick(R.id.goto_top)
	public void gotoTop(View v) {
		v.startAnimation(AnimationUtils.loadAnimation(context, R.anim.goto_top));
		v.setVisibility(View.GONE);
		listView.setSelection(0);
	}

	@OnClick(R.id.jokes_img)
	public void toImg(View v) {
		startActivity(new Intent(context, JokesActivity_img.class));
	}

	@Override
	public void onLoad() {
		page++;
		getNetDate();
	}
}
