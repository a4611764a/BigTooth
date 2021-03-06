package com.zb.bittooth;

import java.io.File;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.mechat.mechatlibrary.MCClient;
import com.mechat.mechatlibrary.callback.OnInitCallback;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.nostra13.universalimageloader.utils.StorageUtils;

public class App extends Application {
	//33
	public static App app;
	public static Typeface TEXT_TYPE;
	public static DisplayImageOptions options;
	public static ImageLoadingListenerImpl mImageLoadingListenerImpl;
	@Override
	public void onCreate() {
		File cacheDir = StorageUtils.getOwnCacheDirectory(
				getApplicationContext(), "/bigtooth/image/");
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				this)
				.memoryCacheExtraOptions(480, 800)
				// max width, max height，即保存的每个缓存文件的最大长宽
				.discCacheExtraOptions(480, 800, null)
				// Can slow ImageLoader, use it carefully (Better don't use
				// it)/设置缓存的详细信息，最好不要设置这个
				.threadPoolSize(3)
				// 线程池内加载的数量
				.threadPriority(Thread.NORM_PRIORITY - 2)
				.denyCacheImageMultipleSizesInMemory()
				.memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
				// You can pass your own memory cache
				// implementation/你可以通过自己的内存缓存实现
				.memoryCacheSize(2 * 1024 * 1024)
				.discCacheSize(50 * 1024 * 1024)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				// 将保存的时候的URI名称用MD5 加密
				.tasksProcessingOrder(QueueProcessingType.LIFO)
				.discCacheFileCount(100)
				// 缓存的文件数量
				.discCache(new UnlimitedDiscCache(cacheDir))
				// 自定义缓存路径
				.defaultDisplayImageOptions(DisplayImageOptions.createSimple())
				.imageDownloader(
						new BaseImageDownloader(this, 5 * 1000, 30 * 1000)) // connectTimeout
																			// (5
																			// s),
																			// readTimeout
																			// (30
																			// s)超时时间
				// .writeDebugLogs() // Remove for release app
				.build();// 开始构建
		// Initialize ImageLoader with configuration.
		ImageLoader.getInstance().init(config);// 全局初始化此配置
		// 显示图片的配置
		 options = new DisplayImageOptions.Builder()
				.showImageOnLoading(com.zb.bittooth.R.drawable.default_img)
				.showImageOnFail(com.zb.bittooth.R.drawable.default_img)
				.cacheInMemory(true).cacheOnDisk(true)
				.bitmapConfig(Bitmap.Config.RGB_565).build()
				;
		 mImageLoadingListenerImpl=new ImageLoadingListenerImpl();
		
		// 初始化美洽SDK
		MCClient.init(this, "557fb69c4eae356204000008", new OnInitCallback() {

			@Override
			public void onSuccess(String response) {
				// TODO Auto-generated method stub
				// Success
				Toast.makeText(getApplicationContext(), "init MCSDK success",
						Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onFailed(String response) {
				// TODO Auto-generated method stub
				// Failed
				Toast.makeText(getApplicationContext(),
						"init MCSDK failed " + response, Toast.LENGTH_SHORT)
						.show();
			}
		});
		//载入字体样式
		// 加载自定义字体
				try {
					TEXT_TYPE = Typeface.createFromAsset(getAssets(),
							"front/huakangshaonvti.ttf");
				} catch (Exception e) {
					Log.i("MyApp", "加载第三方字体失败。");
					TEXT_TYPE = null;
				}
	}
	
}
//监听图片异步加载,图片淡入效果
 class ImageLoadingListenerImpl extends SimpleImageLoadingListener {

  public static final List<String> displayedImages = 
        Collections.synchronizedList(new LinkedList<String>());

  @Override
  public void onLoadingComplete(String imageUri, View view,Bitmap bitmap) {
    if (bitmap != null) {
      ImageView imageView = (ImageView) view;
      boolean isFirstDisplay = !displayedImages.contains(imageUri);
   //   if (isFirstDisplay) {
        //图片的淡入效果
        FadeInBitmapDisplayer.animate(imageView, 2000);
        displayedImages.add(imageUri);
  //    }
    }
  }
  }
