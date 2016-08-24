package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Quan;
import com.jhd.fourszaixian.utils.UserUtils;

public class MyQuanActivity extends Activity{
	private ListView myquan_lv_guoqi;
	List<Quan> qlist;
	private ListView myquan_lv_keyong;
	private TextView myquan_tv;
    List<Quan> glist;
    String uid ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_quan);
		Intent it = getIntent();
		uid = (String) it.getSerializableExtra("uid");
		System.out.println("--------myquan    uid--------:"+uid);
		myquan_tv = (TextView) findViewById(R.id.myquan_tv);
		findViewById(R.id.quan_tv_back).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		myquan_lv_guoqi = (ListView) findViewById(R.id.myquan_lv_guoqi);
		myquan_lv_keyong = (ListView) findViewById(R.id.myquan_lv_keyong);
		
		QuanByState(uid);
		
		
	}
	
	class myAdapterguoqi extends BaseAdapter{

		List<Quan> list;
		Context context;
		LayoutInflater inflater;
		public myAdapterguoqi(Context context,List<Quan> list) {
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.quanitemguoqi, null);
				holder.quan_money = (TextView) convertView.findViewById(R.id.quan_money);
				holder.quan_condition = (TextView) convertView.findViewById(R.id.quan_condition);
				holder.quan_time = (TextView) convertView.findViewById(R.id.quan_time);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.quan_money.setText(list.get(position).getMoney()+"Ԫ");
            holder.quan_condition.setText(list.get(position).getCondition());
            holder.quan_time.setText(list.get(position).getDeadline());
			return convertView;
		}
		
	}
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

	class myAdapterkeyong extends BaseAdapter{

		List<Quan> list;
		Context context;
		LayoutInflater inflater;
		public myAdapterkeyong(Context context,List<Quan> list) {
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {

			ViewHolder holder = null;
			if(convertView == null){
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.quanitemkeyong, null);
				holder.quan_money = (TextView) convertView.findViewById(R.id.quan_money);
				holder.quan_condition = (TextView) convertView.findViewById(R.id.quan_condition);
				holder.quan_time = (TextView) convertView.findViewById(R.id.quan_time);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			holder.quan_money.setText(list.get(position).getMoney()+"Ԫ");
            holder.quan_condition.setText(list.get(position).getCondition());
            holder.quan_time.setText(list.get(position).getDeadline());
			return convertView;
		}
	}
	class ViewHolder{
		TextView quan_money,quan_condition,quan_time;
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
					qlist = new ArrayList<Quan>();
					glist = new ArrayList<Quan>();
					System.out.println("ja "+ja.length());
					for(int i = 0;i<ja.length();i++){
						JSONObject jsonObject = ja.getJSONObject(i);
						Quan q =  new Quan();
						q.setId(jsonObject.getInt("quan_id"));
						q.setUid(jsonObject.getString("quan_uid"));
						
						q.setState(jsonObject.getString("quan_state"));
						q.setMoney(jsonObject.getString("quan_money"));
						q.setCondition(jsonObject.getString("quan_condition"));
						q.setDeadline(jsonObject.getString("quan_deadline"));
						
					    if(q.getState().equals("1") ){
					    	qlist.add(q);
					    }else{
					    	glist.add(q);
					    }
						
					}
					myAdapterkeyong maky = new myAdapterkeyong(getApplicationContext(), qlist);
					myquan_lv_keyong.setAdapter(maky);
					myAdapterguoqi magq = new myAdapterguoqi(getApplicationContext(), glist);
				
				    myquan_lv_guoqi.setAdapter(magq);
					
//					System.out.println(order.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				
			
			}
			break;
			case 2:
				Log.e("qqq", "failed");
				break;

			default:
				break;
			}
		};

	};



	private  void QuanByState(String uid){
		FinalHttp fh=new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("uid", uid);
	
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
		fh.get(UserUtils.QUANURL, params, callBack);
		
	}

	
	
}

