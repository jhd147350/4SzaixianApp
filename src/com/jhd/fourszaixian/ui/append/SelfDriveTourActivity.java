package com.jhd.fourszaixian.ui.append;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.ui.append.MySelfDriveRecyclerViewAdapter.OnItemClickListener;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class SelfDriveTourActivity extends Activity {
	// TODO 这个activity 性能不好，但不知道是怎么造成的

	private TextView back;
	private EditText search;
	private ListView listView;
	private RecyclerView recyclerView;
	private List<SelfDriveTourBean> info = new ArrayList<SelfDriveTourBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_self_drive_tour);

		// finalBitmap.configLoadingImage(R.drawable.ad1);
		// finalBitmap.configLoadfailImage(R.drawable.ad1);

		initViews();
		getInfo();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		back = (TextView) findViewById(R.id.back);
		search = (EditText) findViewById(R.id.search);
		listView = (ListView) findViewById(R.id.listview);

		recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		}
	}

	private void getInfo() {
		FinalHttp fh = new FinalHttp();
		/*
		 * AjaxParams params = new AjaxParams(); params.put("action", "1");
		 * params.put("phonenum", phonenum); params.put("password", password);
		 * myphonenum=phonenum; mypassword=password;
		 */
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				try {
					JSONObject str = new JSONObject(s);
					JSONObject data = str.getJSONObject("data");
					JSONArray travelList = data.getJSONArray("travelList");
					for (int i = 0; i < travelList.length(); i++) {
						JSONObject temp = (JSONObject) travelList.get(i);

						SelfDriveTourBean bean = new SelfDriveTourBean();
						bean.setTravelId(temp.getInt("travelId"));
						bean.setTitle(temp.getString("title"));
						bean.setContent(temp.getString("content"));
						bean.setUrl(temp.getString("url"));
						bean.setCityName(temp.getString("cityName"));
						bean.setCreateTime(temp.getLong("createTime"));
						bean.setPicUrl(temp.getString("picUrl"));
						bean.setActivityTime(temp.getString("activityTime"));
						bean.setDepartureLocation(temp
								.getString("departureLocation"));
						bean.setActivityLocation(temp
								.getString("activityLocation"));
						info.add(bean);

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				/*
				 * listView.setAdapter(new MySelfDriveBaseAdapter(
				 * getApplicationContext(), info));
				 * listView.setOnItemClickListener(new OnItemClickListener() {
				 * 
				 * @Override public void onItemClick(AdapterView<?> parent, View
				 * view, int position, long id) { // TODO Auto-generated method
				 * stub // 复用新闻详细页面，传递一个url参数即可
				 * 
				 *  } });
				 */
				MySelfDriveRecyclerViewAdapter adapter=new MySelfDriveRecyclerViewAdapter(
						getApplicationContext(), info);
				adapter.setOnItemClickListener(new OnItemClickListener() {
					
					@Override
					public void onItemClick(View v, SelfDriveTourBean data) {
						// TODO Auto-generated method stub
						 Intent it = new Intent(getApplicationContext(), NewsDetailActivity.class);
						 it.putExtra("url", data.getUrl());
						 startActivity(it);
					}
				});
				recyclerView.setAdapter(adapter);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
			}
		};
		fh.get("http://app.4sline.com:8080/buyer/driveTravel.do", null,
				callBack);
		fh = null;// close

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		// 设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

	/*
	 * class MyRecycleAdapter extends RecyclerView.Adapter<MyViewHolder> {
	 * 
	 * @Override public MyViewHolder onCreateViewHolder(ViewGroup parent, int
	 * viewType) { MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
	 * SelfDriveTourActivity.this).inflate(R.layout.zjyitem, parent, false));
	 * return holder; }
	 * 
	 * @Override public void onBindViewHolder(MyViewHolder holder, int position)
	 * { //holder.tv.setText(info.get(position)); }
	 * 
	 * @Override public int getItemCount() { //return mDatas.size(); }
	 * 
	 * 
	 * } class MyViewHolder extends ViewHolder {
	 * 
	 * TextView tv;
	 * 
	 * public MyViewHolder(View view) { super(view); // tv = (TextView)
	 * view.findViewById(R.id.id_num); } }
	 */

}
