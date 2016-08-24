package com.jhd.fourszaixian.ui;

import com.fenghuo.jhdwxt.fourszaixian.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class ChexianbijiaActivity extends Activity {
	WebView wv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chexianbijia);
		wv = (WebView) findViewById(R.id.webview);
		wv.loadUrl("http://app.4sline.com/buyer/insuranceParity.do?plat=app&userId=1045319&system=android&appversion=4.0.0");
		WebSettings webSettings = wv.getSettings();
		// webview支持js脚本
		webSettings.setJavaScriptEnabled(true);
		// 启用地理定位

		webSettings.setGeolocationEnabled(true);

		// 开启DomStorage缓存

		webSettings.setDomStorageEnabled(true);

	}

	@Override
	public void finish() {
		super.finish();
		// 设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.chexianbijia_tv_back:
			finish();

			break;
		case R.id.chexianbijia_changjianwenti:
			startActivity(new Intent(this, ChangjianwentiActivity.class));
			break;

		default:
			break;
		}

	}

}
