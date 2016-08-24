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


public class DBorder extends Fragment {

	private ListView dborder_lv;
	private List<Order> list;
	private TextView bdorder_tv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.orderp3, null);
		dborder_lv = (ListView) view.findViewById(R.id.dborder_lv);
		bdorder_tv = (TextView) view.findViewById(R.id.dborder_tv);
		String state = "未完�?";
		String uid = (String) getActivity().getIntent().getSerializableExtra("uid");
		orderByState(uid, state);
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
					dborder_lv.setAdapter(adapter);
				}
				else{
					bdorder_tv.setText("暂时没有订单！！�?");
					
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



	private  void orderByState(String uid,String state){
		FinalHttp fh=new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "2");
		params.put("uid", uid);
		params.put("state", state);
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
