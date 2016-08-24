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
		// webview֧��js�ű�
		webSettings.setJavaScriptEnabled(true);
		// ���õ���λ

		webSettings.setGeolocationEnabled(true);

		// ����DomStorage����

		webSettings.setDomStorageEnabled(true);

	}

	@Override
	public void finish() {
		super.finish();
		// ����activity��ת�Ķ���
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
