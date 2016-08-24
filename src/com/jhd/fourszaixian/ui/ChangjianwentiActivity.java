package com.jhd.fourszaixian.ui;

import com.fenghuo.jhdwxt.fourszaixian.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

public class ChangjianwentiActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_changjianwenti);
	}
	
	@Override
	public void finish() {
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.changjianwenti_tv_back:
			finish();
			
			break;
	

		default:
			break;
		}
		
	}

}
