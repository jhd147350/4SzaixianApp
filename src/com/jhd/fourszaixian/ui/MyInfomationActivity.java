package com.jhd.fourszaixian.ui;


import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Handler.Callback;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Info;
import com.jhd.fourszaixian.fragment.infoAdapter;
import com.jhd.fourszaixian.utils.UserUtils;

public class MyInfomationActivity extends Activity implements OnClickListener{

	private ListView info_lv;
	private List<Info> list;
	//private TextView info_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.infomain);
		info_lv = (ListView) findViewById(R.id.info_lv);
		//info_tv = (TextView) findViewById(R.id.info_tv);
		findAllInfo();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.info_title_back:
			finish();
			
			break;
		case R.id.zixun_all:
			findAllInfo();
			System.out.println("全部资讯");
			break;
		case R.id.zixun_bx:
			findInfoByType("保险");
			System.out.println("保险");
			break;
		case R.id.zixun_cars:
			findInfoByType("爱车");
			System.out.println("爱车");
			
			break;
		case R.id.zixun_hangye:
			findInfoByType("行业");
			System.out.println("行业");
			break;
		case R.id.zixun_news:
			findInfoByType("新闻");
			System.out.println("新闻");
			break;
            
		default:
			break;
		}
		
	}
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}
	 Handler handler = new Handler(new Callback() {
			
			@Override
			public boolean handleMessage(Message msg) {
				// TODO Auto-generated method stub
				String str = (String) msg.obj;
				System.out.println("--------InfoAllActicleActicity str"+str);
				switch (msg.what) {
				case 1:{
					
					list = new ArrayList<Info>();
				//	info_tv.setText(null);
					infoAdapter adapter1 = new infoAdapter(MyInfomationActivity.this, list);
					info_lv.setAdapter(adapter1);
					try {
						JSONArray ja = new JSONArray(str);
						
						for (int i = 0; i < ja.length(); i++) {
							JSONObject js = ja.getJSONObject(i);
							Info  info = new Info();
							info.setId(js.getInt("info_id"));
							info.setInfoContent(js.getString("info_content"));
							info.setInfoImg(js.getString("info_img"));
							info.setInfoTime(js.getString("info_time"));
							info.setInfoTitle(js.getString("info_title"));
							info.setInfoType(js.getString("info_type"));
							list.add(info);
						}
						if(list.size()==0){
						//	info_tv.setText("暂时没有相关资讯！！！");
						}else{
							infoAdapter adapter = new infoAdapter(MyInfomationActivity.this, list);
							info_lv.setAdapter(adapter);
						}
						
						System.out.println("----InfoActicleActicity list"+list.toString());
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
					
					break;
				case 2:{
					Log.e("qqq", "failed");
					Toast.makeText(getApplicationContext(), "网络请求失败！！！", Toast.LENGTH_SHORT).show();
				}
					
					break;
				default:
					break;
				}
				return false;
			}
		});
	private void findAllInfo(){
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");//表示查询全部资讯
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = t;
				handler.sendMessage(msg);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);
			}
		};
		fh.get(UserUtils.INFOURL,params, callBack);
	}
	
	private void findInfoByType(String type){
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "2");//根据类型查找info
		params.put("type", type);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String t) {
				// TODO Auto-generated method stub
				super.onSuccess(t);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = t;
				handler.sendMessage(msg);
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);
			}
		};
		fh.get(UserUtils.INFOURL,params, callBack);
	}
	
}
