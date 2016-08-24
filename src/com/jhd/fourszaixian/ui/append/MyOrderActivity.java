package com.jhd.fourszaixian.ui.append;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Order;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.utils.UserUtils;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

public class MyOrderActivity extends Activity{
	//主要是那个代驾的订单
	//TODO 可以考虑，加入其他订单，统一写一个接口订单接口或订单父类抽象类
	
	private ListView listView;
	private TextView nothing;
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_myorder);
		listView=(ListView) findViewById(R.id.listview);
		nothing=(TextView) findViewById(R.id.nothing);
		getOrder(User.getInstance().getPhonenum());
	}
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
	}
	public void onClick(View v){
		if(v.getId()==R.id.back){
			finish();
		}
	}

	void getOrder(String uid) {
		// uid 是用户手机号，即账号
		// name 是司机name
		// phone 是司机号码
		// 地址是当前地址
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("uid", uid);
		params.put("type", "代驾");//type=代驾
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
				List<Order> list = new ArrayList<Order>();
				try {
					JSONArray ja = new JSONArray(s);
					
					System.out.println("ja "+ja.length());
					for(int i = 0;i<ja.length();i++){
						JSONObject jsonObject = ja.getJSONObject(i);
						Order order = new Order();
						
						order.setId(jsonObject.getInt("order_id"));
						order.setUid(jsonObject.getString("order_uid"));
						order.setState(jsonObject.getString("order_state"));
						order.setFeatures(jsonObject.getString("order_features"));
						order.setMoney(jsonObject.getString("order_money"));
						order.setName(jsonObject.getString("order_name"));
						order.setType(jsonObject.getString("order_type"));
						order.setTime( jsonObject.getString("order_time"));
						System.out.println("order "+order);
						list.add(order);
					}
//					System.out.println(order.toString());
				} catch (JSONException e) {
					e.printStackTrace();
				}

				if(list.size()!=0){
					DaijiaOrderAdapter adapter = new DaijiaOrderAdapter(getApplicationContext(), list);
					listView.setAdapter(adapter);
					nothing.setVisibility(View.GONE);
				}	
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
		};
		fh.get(UserUtils.ORDERURL, params, callBack);
	}
}
