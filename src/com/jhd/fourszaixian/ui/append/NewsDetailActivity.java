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
			public boolean shouldOverrideUrlLoading(WebView view, String url) { // 重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
				view.loadUrl(url);
				return true;
			}
		});
		WebSettings webSettings = webView.getSettings();
		// webview支持js脚本
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
		// 设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

}
