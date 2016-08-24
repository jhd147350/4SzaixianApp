package com.jhd.fourszaixian.ui.append;

import com.fenghuo.jhdwxt.fourszaixian.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class NewsDetailActivity extends Activity {

	private TextView back;
	private WebView webView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news_detail);
		initViews();
		webView.loadUrl(getIntent().getStringExtra("url"));
		webView.setWebViewClient(new WebViewClient() {
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // ��д�˷������������ҳ��������ӻ����ڵ�ǰ��webview����ת��������������Ǳ�
				view.loadUrl(url);
				return true;
			}
		});
		WebSettings webSettings = webView.getSettings();
		// webview֧��js�ű�
		webSettings.setJavaScriptEnabled(true);
	}

	private void initViews() {
		// TODO Auto-generated method stub
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		webView = (WebView) findViewById(R.id.webview);

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		// ����activity��ת�Ķ���
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

}
