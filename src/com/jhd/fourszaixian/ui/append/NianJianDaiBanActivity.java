package com.jhd.fourszaixian.ui.append;

import com.fenghuo.jhdwxt.fourszaixian.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

public class NianJianDaiBanActivity extends Activity{
private TextView tv_back;
	
	private WebView wv;
	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_weizhang);
		tv_back=(TextView) findViewById(R.id.weizhang_tv_back);
		wv=(WebView) findViewById(R.id.mywebview);
		//wv.loadUrl("http://m.weizhang8.cn");
		//wv.loadUrl("http://m.46644.com/illegal/?tpltype=baidu");
		//ʧЧ��wv.loadUrl("http://m.weizhangwang.com/");
		//wv.loadUrl("http://www.chexianceping.com/");
		wv.loadUrl("http://app.4sline.com/buyer/inspection.do?isPay=1&plat=app&userId=1045319&system=android&appversion=4.0.0&address=�����г����������Ŷ�·10��");
		WebSettings webSettings = wv.getSettings();
		//webview֧��js�ű�
		webSettings.setJavaScriptEnabled(true);
		//���õ���λ  

		webSettings.setGeolocationEnabled(true);  

		//����DomStorage����

		webSettings.setDomStorageEnabled(true);
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.weizhang_tv_back:
			finish();
			break;

		default:
			break;
		}
	}
}
