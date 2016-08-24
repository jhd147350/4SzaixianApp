package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
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
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Zjyitem;
import com.jhd.fourszaixian.utils.UserUtils;

public class ZJYActivity extends Activity {

	private ListView zjy_lv;
	private List<Zjyitem> list ;
    private FinalBitmap bitmap;
	private EditText search_ed;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.zjymain);
		Intent it = getIntent();
		zjy_lv = (ListView) findViewById(R.id.zjy_listview);
		search_ed = (EditText) findViewById(R.id.zjy_search);
		findByAll();
		search_ed.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String content = search_ed.getText().toString();
				findByAddress(content);
			}
		});
		
	
	}
	
	//----jhd-add-----------------------
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.zijiayou_tv_back:
			finish();
			break;
		default:
			break;
		}
	}
	//--------------------finish----
	@Override
	public void finish() {
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}
	
	class myAdapter extends BaseAdapter{

		List<Zjyitem> zlist = new ArrayList<Zjyitem>();
		Context context;
		LayoutInflater inflater;
		
		public myAdapter(Context context,List<Zjyitem> zlist) {
			this.context = context;
			this.zlist = zlist;
			bitmap = FinalBitmap.create(context);
			bitmap.configLoadfailImage(R.drawable.ic_launcher);
			inflater = LayoutInflater.from(context);
		}
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return zlist.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return zlist.get(position);
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
				convertView = inflater.inflate(R.layout.zjyitem, null);
				holder.title = (TextView) convertView.findViewById(R.id.zjy_title);
				//holder.content = (TextView) convertView.findViewById(R.id.zjy_content);
				//holder.cfd = (TextView) convertView.findViewById(R.id.zjy_cfd);
				//holder.time = (TextView) convertView.findViewById(R.id.zjy_activityTime);
				//holder.address = (TextView) convertView.findViewById(R.id.zjy_activityAddress);
				//holder.img = (ImageView) convertView.findViewById(R.id.zjy_img);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			
			holder.title .setText(zlist.get(position).getZjyTitle());
			holder.content.setText(zlist.get(position).getZjyContent());
			holder.cfd.setText(zlist.get(position).getZjyCfd());
			holder.time.setText(zlist.get(position).getZjytime());
			holder.address.setText(zlist.get(position).getZjyAddress());
			
			
			String imgUrl = zlist.get(position % zlist.size()).getZjyImg();
			if(imgUrl.equals(holder.img.getTag())){
				bitmap.display(holder.img, imgUrl);
			}else{
				bitmap.display(holder.img, imgUrl);
			}
			holder.img.setTag(imgUrl);
			return convertView;
		}
		
	}
	class ViewHolder{
		TextView title,content,cfd,time,address;
		ImageView img;
	}

	Handler handler = new Handler(new Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String str = (String) msg.obj;
			switch (msg.what) {
			case 1:{
				list = new ArrayList<Zjyitem>();
				try {
					JSONArray ja = new JSONArray(str);					
					for (int i = 0; i < ja.length(); i++) {
						JSONObject js = ja.getJSONObject(i);
						Zjyitem z = new Zjyitem();
						z.setId(js.getInt("zjy_id"));
						z.setZjyTitle(js.getString("zjy_title"));
						z.setZjyContent(js.getString("zjy_content"));
						z.setZjyCfd(js.getString("zjy_cfd"));
						z.setZjytime(js.getString("zjy_time"));
						z.setZjyAddress(js.getString("zjy_address"));
						z.setZjyImg(js.getString("zjy_img"));
						z.setZjyHttp(js.getString("zjy_http"));
						list.add(z);
					}
					myAdapter adapter = new myAdapter(ZJYActivity.this, list);
					zjy_lv.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
				
				break;
			case 2:
				Log.e("qqq", "failed");
				Toast.makeText(getApplicationContext(), "网络请求失败！", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			return false;
		}
	});
	private void findByAddress(String address){
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("address", address);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = s;
				handler.sendMessage(msg);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);
			}
		};
		fh.get(UserUtils.ZJYURL, params, callBack);
	}
	private void findByAll(){
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "2"); //全部
		
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = s;
				handler.sendMessage(msg);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);
			}
		};
		fh.get(UserUtils.ZJYURL, params, callBack);
	}
}
