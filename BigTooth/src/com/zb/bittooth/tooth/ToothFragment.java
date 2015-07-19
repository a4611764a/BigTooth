package com.zb.bittooth.tooth;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
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
import com.umeng.analytics.MobclickAgent;
import com.zb.bittooth.R;
import com.zb.bittooth.Urls;
import com.zb.bittooth.customView.FlowLayout;
import com.zb.bittooth.customView.RoundedImageView;
import com.zb.bittooth.model.Moble;
import com.zb.bittooth.model.Tag;
import com.zb.bittooth.utils.MobelUtils;
import com.zb.bittooth.utils.T;

import de.greenrobot.event.EventBus;

public class ToothFragment extends Fragment {
	@ViewInject(R.id.imageView1)
	RoundedImageView imageView1;
	@ViewInject(R.id.name)
	EditText name;
	@ViewInject(R.id.jokes)
	EditText jokes;
	@ViewInject(R.id.ll_mo)
	LinearLayout ll_mo;
	@ViewInject(R.id.title2)
	TextView title2;
	@ViewInject(R.id.title3)
	TextView title3;
	@ViewInject(R.id.post)
	Button post;
	@ViewInject(R.id.location)
	TextView location;
	private Context context;
	private HttpUtils httpUtils = null;
	Gson gson;
	RequestParams params = null;
	private List<Tag> listTag;
	private static boolean isPostHead = false;
	static String filePath = "";// 上传图片路径地址
	private int position = -1;
	private int old_position = -1;
	/**
	 * 自定义流式布局
	 */
	private FlowLayout mFlowlayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = LayoutInflater.from(getActivity()).inflate(
				R.layout.tooth_frame_main, null);
		context = getActivity();
		ViewUtils.inject(this, view); // 注入view和事件
		EventBus.getDefault().register(this);
		httpUtils = new HttpUtils();
		gson = new Gson();
		HttpUtils.sHttpCache.setEnabled(HttpMethod.POST, false);
		HttpUtils.sHttpCache.setEnabled(HttpMethod.GET, false);
		mFlowlayout = (FlowLayout) view.findViewById(R.id.flowlay);
		listTag = new ArrayList<Tag>();
		getTags();
		imageView1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				// 使用startActivityForResult启动SelectPicPopupWindow当返回到此Activity的时候就会调用onActivityResult函数
				startActivityForResult(new Intent(context,
						SelectPicPopupWindow.class), 1);
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

	private void getTags() {
		httpUtils.send(HttpRequest.HttpMethod.GET, Urls.url_get_tagList,
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
						listTag = gson.fromJson(responseInfo.result,
								new TypeToken<List<Tag>>() {
								}.getType());
						initData(listTag);
					}

					@Override
					public void onFailure(HttpException error, String msg) {
						Toast.makeText(context, error + msg, Toast.LENGTH_SHORT)
								.show();
					}
				});
	}

	private void initData(List<Tag> listTag) {
		LayoutInflater inflater = LayoutInflater.from(context);
		for (int i = 0; i < listTag.size(); i++) {
			TextView tv = (TextView) inflater.inflate(R.layout.tooth_tv,
					mFlowlayout, false);
			tv.setText(listTag.get(i).getTags());
			tv.setTag(listTag.get(i).getId());
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View tv) {
					for (int i = 0; i < mFlowlayout.getChildCount(); i++) {
						mFlowlayout.getChildAt(i).setBackgroundResource(
								R.drawable.tooth_tv_bg);
					}
					position = Integer.parseInt(tv.getTag() + "");
					if (old_position != position) {
						tv.setBackgroundResource(R.drawable.tooth_tv_bg_un);
						old_position = position;
					} else {
						tv.setBackgroundResource(R.drawable.tooth_tv_bg);
						old_position = -1;
					}
				}
			});
			mFlowlayout.addView(tv);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (resultCode) {
		case 1:
			if (data != null) {
				// 取得返回的Uri,基本上选择照片的时候返回的是以Uri形式，但是在拍照中有得机子呢Uri是空的，所以要特别注意
				Uri mImageCaptureUri = data.getData();
				// 返回的Uri不为空时，那么图片信息数据都会在Uri中获得。如果为空，那么我们就进行下面的方式获取
				if (mImageCaptureUri != null) {
					Bitmap image;
					try {
						// 这个方法是根据Uri获取Bitmap图片的静态方法
						image = MediaStore.Images.Media.getBitmap(
								context.getContentResolver(), mImageCaptureUri);
						if (image != null) {
							savePicToSdcard(image, Urls.imageCachePath,
									"image_head");
							imageView1.setImageBitmap(image);
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					Bundle extras = data.getExtras();
					if (extras != null) {
						// 这里是有些拍照后的图片是直接存放到Bundle中的所以我们可以从这里面获取Bitmap图片
						Bitmap image = extras.getParcelable("data");
						if (image != null) {
							File file = savePicToSdcard(image,
									Urls.imageCachePath, "image_head");
							imageView1.setImageBitmap(image);
							Intent intent = new Intent(
									Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
							Uri uri = Uri.fromFile(file);
							intent.setData(uri);
							context.sendBroadcast(intent);
						}
					}
				}
			}
			break;
		default:
			break;

		}
	}

	// 头像图片路径
	public static File savePicToSdcard(Bitmap bitmap, String path,
			String fileName) {
		File destFile = null;
		if (bitmap == null) {
			return destFile;
		} else {
			filePath = path + fileName + ".png";
			destFile = new File(filePath);
			try {
				BufferedOutputStream bos = new BufferedOutputStream(
						new FileOutputStream(destFile));
				bitmap.compress(Bitmap.CompressFormat.PNG, 80, bos);
				bos.flush();
				bos.close();
			} catch (IOException e) {
				filePath = "";
			}
			isPostHead = true;
		}
		return destFile;
	}

	@OnClick(R.id.regist)
	public void regist(final Button v) {
		if (name.getText().toString().trim().length() <= 0) {
			T.showShort(context, "请输入您喜欢的昵称");
		} else if (!isPostHead) {
			T.showShort(context, "请上传您要使用的头像");
		} else {
			params = new RequestParams();
			params.addQueryStringParameter("uuid",
					MobelUtils.getMobelUUid(context));
			params.addQueryStringParameter("userName", name.getText()
					.toString());
			params.addBodyParameter("file", new File(filePath));
			httpUtils.send(HttpRequest.HttpMethod.POST,
					Urls.url_post_userRegister, params,
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
							T.showShort(context, responseInfo.result + "");
							ll_mo.startAnimation(AnimationUtils.loadAnimation(
									context, R.anim.tooth_out));
							v.setClickable(false);
							ll_mo.setVisibility(View.GONE);
							title2.setTextColor(getResources().getColor(
									R.color.deep_orange));
							title3.setTextColor(getResources().getColor(
									R.color.deep_orange));
							post.setBackgroundResource(R.drawable.tooth_tv_bg_un);
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							Toast.makeText(context, error + msg,
									Toast.LENGTH_SHORT).show();
							/*
							 * ll_mo.startAnimation(AnimationUtils.loadAnimation(
							 * context, R.anim.tooth_translate));
							 * ll_mo.setVisibility(View.GONE);
							 * v.setClickable(false);
							 */
						}
					});
		}
	}

	@OnClick(R.id.post)
	public void postJokes(View v) {
		if (jokes.getText().toString().trim().length() <= 0) {
			T.showShort(context, "请输入您笑话的内容");
		} else if (old_position != -1) {
			T.showShort(context, "请为您的笑话点击一个标签");
		} else {
			params = new RequestParams();
			params.addQueryStringParameter("uuid",
					MobelUtils.getMobelUUid(context));
			 params.addQueryStringParameter("jokes", jokes.getText().toString());
			 params.addQueryStringParameter("tag", listTag.get(old_position)
			.toString());
			httpUtils.send(HttpRequest.HttpMethod.POST,
					Urls.url_post_userRegister, params,
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
							T.showShort(context, responseInfo.result + "");
						}

						@Override
						public void onFailure(HttpException error, String msg) {
							Toast.makeText(context, error + msg,
									Toast.LENGTH_SHORT).show();
						}
					});
		}
	}

	@OnClick(R.id.ll_mo)
	public void ll_mo(View v) {
		T.showShort(context, "请先加入后，才能发表笑话");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}

	public void onEventMainThread(Moble moble) {
		if (moble != null) {
			location.setText(moble.getAddress());
		}
	}
}