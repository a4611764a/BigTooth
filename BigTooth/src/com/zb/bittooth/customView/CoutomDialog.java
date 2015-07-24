package com.zb.bittooth.customView;

import com.zb.bittooth.R;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;

public class CoutomDialog extends Dialog{
	ScrollView sv_my;
    Context context;
    public CoutomDialog(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        this.context = context;
    }
    public CoutomDialog(Context context, int theme){
        super(context, theme);
        this.context = context;
        this.setContentView(R.layout.my_share_dialog);
    }
    @SuppressLint("NewApi")
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
     /*   sv_my=(ScrollView) findViewById(R.id.sv_my);
        sv_my.setOverScrollMode(View.OVER_SCROLL_NEVER);*/
    }

}
