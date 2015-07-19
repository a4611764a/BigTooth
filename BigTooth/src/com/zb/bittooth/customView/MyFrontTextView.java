package com.zb.bittooth.customView;

import com.zb.bittooth.App;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyFrontTextView extends TextView {
	public MyFrontTextView(Context context) {
		super(context);
		setTypeface();
	}

	public MyFrontTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		setTypeface();
	}

	public MyFrontTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		setTypeface();
	}

	private void setTypeface() {
		// 如果自定义typeface初始化失败，就用原生的typeface
		if (App.TEXT_TYPE == null) {
			setTypeface(getTypeface());
		} else {
			setTypeface(App.TEXT_TYPE );
		}
	}
}
