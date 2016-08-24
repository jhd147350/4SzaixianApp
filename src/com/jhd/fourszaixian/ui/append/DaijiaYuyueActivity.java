package com.jhd.fourszaixian.ui.append;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.utils.UserUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class DaijiaYuyueActivity extends Activity {
	private TextView phonenum;
	private TextView address;
	private Button confirm;
	private String loc;

	private String name;
	private String phone;
	private String time;
	private String state;// 订单状态
	private String id;// 订单号

	// 用以前的订单表，将features变为phone字段
	// 将money变成 地址
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daijia_yuyue);
		initViews();
		Intent it = getIntent();
		name = it.getStringExtra("name");
		phone = it.getStringExtra("phone");
		initData();
	}

	private void initData() {
		// TODO Auto-generated method stub
		loc = UserUtils.CURRENT_LOACTION;
		address.setText(loc);

	}

	private void initViews() {
		// TODO Auto-generated method stub
		phonenum = (TextView) findViewById(R.id.phonenum);
		address = (TextView) findViewById(R.id.address);
		confirm = (Button) findViewById(R.id.confirm);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.confirm:
			if (User.getInstance().getPhonenum() == null) {
				Toast.makeText(getApplicationContext(), "请先登录！",
						Toast.LENGTH_SHORT).show();
			} else {
				createOrder(User.getInstance().getPhonenum(), name, phone,
						address.getText().toString());
				// action=3,插入代驾订单
			}
			break;

		default:
			break;
		}
	}

	void createOrder(String uid, String name, String phone, String address) {
		// uid 是用户手机号，即账号
		// name 是司机name
		// phone 是司机号码
		// 地址是当前地址
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "3");
		params.put("uid", uid);
		params.put("name", name);
		params.put("phone", phone);
		params.put("address", address);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
				Toast.makeText(getApplicationContext(), "预约成功",
						Toast.LENGTH_SHORT).show();
				finish();
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				Toast.makeText(getApplicationContext(), "预约失败",
						Toast.LENGTH_SHORT).show();
			}
		};
		fh.get(UserUtils.ORDERURL, params, callBack);
	}

}
