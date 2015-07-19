package com.zb.bittooth.mine;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
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
import com.zb.bittooth.MyEvent;
import com.zb.bittooth.NetReceiver;
import com.zb.bittooth.R;
import com.zb.bittooth.Urls;
import com.zb.bittooth.customView.AutoListView;
import com.zb.bittooth.customView.AutoListView.OnLoadListener;
import com.zb.bittooth.customView.CustomProgressDialog;
import com.zb.bittooth.customView.PullToRefreshView;
import com.zb.bittooth.customView.PullToRefreshView.PullToRefreshListener;
import com.zb.bittooth.fragement.SettingNet;
import com.zb.bittooth.model.Jokes;
import com.zb.bittooth.model.Near;

import de.greenrobot.event.EventBus;

public class NearFragement extends Fragment implements OnLoadListener {
	NetReceiver mReceiver = new NetReceiver();
	IntentFilter mFilter = new IntentFilter();
	Gson gson = new Gson();
	private HttpUtils httpUtils = null;
	RequestParams params = null;
	private int page = 1;// 页码
	CustomProgressDialog dialog;
	Context context;
	List<Near> list = new ArrayList<Near>();
	List<Near> list_show = new ArrayList<Near>();
	NearFragementAdapter adapter;
	@ViewInject(R.id.pull)
	PullToRefreshView pullToRefreshView;
	@ViewInject(R.id.listView)
	AutoListView listView;
	@ViewInject(R.id.ll_main_netState)
	LinearLayout ll_main_netState;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.mine_frame_near, null);
		context = getActivity();
		ViewUtils.inject(this, view); // 注入view和事件
		EventBus.getDefault().register(this);
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
					// list_show.removeAll(list_show);
					// list_show=new ArrayList<Jokes>();
					page = 1;
					getNetDate();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				pullToRefreshView.finishRefreshing();
			}
		}, 1);
		listView.setOnLoadListener(this);
		adapter = new NearFragementAdapter(context, list_show);
		listView.setAdapter(adapter);
		return view;
	}

	private void getNetDate() {
		httpUtils.send(HttpRequest.HttpMethod.GET, Urls.url_get_jokes
				+ "?page=" + page, new RequestCallBack<String>() {
			@Override
			public void onStart() {
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
						new TypeToken<List<Near>>() {
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
	public void onLoad() {
		page++;
		getNetDate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		context.unregisterReceiver(mReceiver);
		EventBus.getDefault().unregister(this);
		dialog.cancel();
	}

	public void onEventMainThread(MyEvent myEvent) {
		FragmentTransaction transaction = ((Activity) context)
				.getFragmentManager().beginTransaction();
		SettingNet netFragement = new SettingNet();
		if (myEvent.getITEMS()!=null&&myEvent.getITEMS().equals("0")) {
			ll_main_netState.setVisibility(View.VISIBLE);
			transaction.replace(R.id.ll_main_netState, netFragement);
		} else if (myEvent.getITEMS()!=null&&myEvent.getITEMS().equals("1")){
			ll_main_netState.setVisibility(View.GONE);
			if (list_show.size() <= 0) {
				page = 1;
				getNetDate();
			}
		}
		transaction.commit();
	}
}
