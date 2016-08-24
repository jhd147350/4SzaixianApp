package com.jhd.fourszaixian.ui.append;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.utils.UserUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

public class SettingActivity extends Activity {
	private EditText ip;
	private TextView back;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_setting);
		ip = (EditText) findViewById(R.id.ip);
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				UserUtils.IP=ip.getText().toString();
				UserUtils.updateIP();//也同时将其他URl更新
				finish();
			}
		});
		ip.setText(UserUtils.IP);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		// 设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);

	}
}
