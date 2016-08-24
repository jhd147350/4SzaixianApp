package com.jhd.fourszaixian.fragment;


import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Order;
import com.jhd.fourszaixian.utils.UserUtils;

public class BXorder extends Fragment {

	private ListView bx_lv;
	private List<Order> list;
	private TextView bx_tv;
	String type = "保险";
	String uid;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.orderp2, null);
		bx_lv = (ListView) view.findViewById(R.id.bxorder_lv);
		bx_tv = (TextView) view.findViewById(R.id.bxorder_tv);
	
		uid = (String) getActivity().getIntent().getSerializableExtra("uid");
		orderByType(uid, type);
		System.out.println("uid   "+uid);
		return view;
	}
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			String sr=(String) msg.obj;
            System.out.println("sr   "+sr);
			switch (msg.what) {
			case 1:
			{
				try {

					JSONArray ja = new JSONArray(sr);
					list = new ArrayList<Order>();
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
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if(list.size()!=0){
					orderAdapter adapter = new orderAdapter(getActivity(), list);
					bx_lv.setAdapter(adapter);
				}
				else{
					bx_tv.setText("暂时没有订单！！�?");
					
				}
			
			
			
			}
			break;
			case 2:
				Log.e("qqq", "failed");
				Toast.makeText(getActivity(), "网络请求失败！！�?", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};

	};



	private  void orderByType(String uid,String type){
		FinalHttp fh=new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "1");//1��ʾ��¼����
		params.put("uid", uid);
		params.put("type", type);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg=new Message();
				msg.what=1;
				msg.obj=s;
				handler.sendMessage(msg);//suc
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);//failed
			}
		};
		fh.get(UserUtils.ORDERURL, params, callBack);
		
	}

	
	
}
