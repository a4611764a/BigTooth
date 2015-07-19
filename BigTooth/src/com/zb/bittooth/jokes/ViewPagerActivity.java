/*******************************************************************************
 * Copyright 2011, 2012 Chris Banes.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.zb.bittooth.jokes;

import uk.co.senab.photoview.PhotoView;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.TextView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import com.zb.bittooth.MyEvent;
import com.zb.bittooth.R;
import com.zb.bittooth.customView.LazyViewPager;

import de.greenrobot.event.EventBus;

public class ViewPagerActivity extends Activity {
	@ViewInject(R.id.viewPager)
	LazyViewPager mViewPager;
	@ViewInject(R.id.content)
	TextView content;
	@ViewInject(R.id.title)
	TextView title;
	@ViewInject(R.id.pageCount)
	TextView pageCount;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.jokes_img_viewpager_layout);
		ViewUtils.inject(this);
		EventBus.getDefault().register(this);
		mViewPager = (LazyViewPager) findViewById(R.id.viewPager);
		mViewPager.setAdapter(new SamplePagerAdapter());
	}

	@OnClick(R.id.back)
	public void back(View v) {
		finish();
	}

	public void onEventMainThread(MyEvent event) {
		if (event.getCoutent() != null) {
			content.setText(event.getCoutent());
		}
		if(event.getTitle()!=null){
			title.setText(event.getTitle());
		}
		if(event.getPage()!=null){
			pageCount.setText(event.getPage());
		}
	}

	static class SamplePagerAdapter extends PagerAdapter {
		static MyEvent event = new MyEvent();
		private static int[] sDrawables = { R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper, R.drawable.wallpaper,
				R.drawable.wallpaper };

		@Override
		public int getCount() {
			return sDrawables.length;
		}

		@Override
		public View instantiateItem(ViewGroup container, int position) {
			PhotoView photoView = new PhotoView(container.getContext());

			photoView.setImageResource(sDrawables[position]);
			// Now just add PhotoView to ViewPager and return it
			container.addView(photoView, LayoutParams.MATCH_PARENT,
					LayoutParams.MATCH_PARENT);
			event.setCoutent("dd");
			event.setPage(0+"/"+this.getCount());
			event.setTitle("henhao");
			EventBus.getDefault().post(event);
			return photoView;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		EventBus.getDefault().unregister(this);
	}
}
