package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Brand;
import com.jhd.fourszaixian.entity.Series;
import com.jhd.fourszaixian.entity.Subs;
import com.jhd.fourszaixian.ui.BrandActivity.BrandListAdapter;
import com.jhd.fourszaixian.ui.BrandActivity.BrandListAdapter.ViewHolder;
import com.jhd.fourszaixian.utils.UserUtils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class SeriesActivity extends Activity implements OnCheckedChangeListener, OnItemClickListener {
	//判断是否来自mycar
	private boolean isFromMycar=false;

	private List<Series> list_series=new ArrayList<Series>();
	private List<Subs> list_subs=new ArrayList<Subs>();
	private List<List<Subs>> subsarray;
	private ListView lv;
	private TextView tv_back;
	private RadioGroup rg;
	private RadioButton rb1;
	private RadioButton rb2;
	private RadioButton rb3;
	private RadioButton rb4;
	private long brand_id=0; 
	private int selectedSubs=0;
	private String brand_name="0";
	private long firstSeriesId=0;
	private long secondSeriesId=0;
	private long thirderiesId=0;
	private long fourthSeriesId=0;
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1://suc
				try {
					JSONArray ja=new JSONArray((String)msg.obj);
					//subsarray=new ArrayList<List<Subs>>();
					//	String [] str=new String[5];
					for(int i=0;i<ja.length();i++){
						JSONObject temp=ja.getJSONObject(i);
						Series s=new Series();
						s.setId(temp.getLong("series_id"));
						s.setName(temp.getString("series_name"));
						list_series.add(s);	
						if(i==0){

							rb1.setText(temp.getString("series_name"));
							firstSeriesId=s.getId();
							rb1.setVisibility(View.VISIBLE);
							//rb1.setSelected(true);
						}else if(i==1){
							rb2.setText(temp.getString("series_name"));
							secondSeriesId=s.getId();
							rb2.setVisibility(View.VISIBLE);
						}
						else if(i==2){
							rb3.setText(temp.getString("series_name"));
							thirderiesId=s.getId();
							rb3.setVisibility(View.VISIBLE);
						}
						else if(i==3){
							rb4.setText(temp.getString("series_name"));
							fourthSeriesId=s.getId();
							rb4.setVisibility(View.VISIBLE);
						}
						Log.e("jhd", "handler:2");
					}

					handler.sendEmptyMessage(3);

					//SeriesListAdapter adapter=new SeriesListAdapter(list, getApplicationContext());
					//lv.setAdapter(adapter);

				} catch (JSONException e) {

					e.printStackTrace();
				}

				break;
			case 2:
				Toast.makeText(getApplicationContext(), "series访问超时请确认网络是否通畅！", Toast.LENGTH_SHORT).show();

				break;
			case 3://去根据series去加载subs
				new Thread(){
					public void run() {
						int i=1;
						for(Series s:list_series){
							try {
								sleep(200*i);
								i++;
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							//	List<Subs> subs_list_temp=new ArrayList<Subs>();
							initSubsList(s.getId());
						}
					};
				}.start();

				break;
			case 4://去根据series去加载subs


				for(int i=0;i<subsarray.size();i++){
					//					for(Subs te:subsarray.get(i)){
					//						Log.d("jhd1", te.toString());
					//					}


					if(subsarray.get(i).size()>0&&subsarray.get(i).get(0).getSeries_id()==firstSeriesId){
						SubsListAdapter subsListAdapter=new SubsListAdapter(subsarray.get(i), getApplicationContext());
						lv.setAdapter(subsListAdapter);
						break;
					}
				}


				break;
			default:
				break;
			}
		}

	};
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_series);
		initView();
		subsarray=new ArrayList<List<Subs>>();
		Intent it=getIntent();
		isFromMycar=it.getBooleanExtra("MyCar", false);
		brand_id = it.getLongExtra("brand_id", 0);
		brand_name=it.getStringExtra("brand_name");
		if(brand_id!=0){
			initSeriesList( brand_id);
		}

	}

	private void initView() {
		// TODO Auto-generated method stub
		lv=(ListView) findViewById(R.id.series_listview);
		lv.setOnItemClickListener(this);
		tv_back=(TextView) findViewById(R.id.series_tv_back);
		rg=(RadioGroup) findViewById(R.id.series_rg);
		rg.setOnCheckedChangeListener(this);
		rb1=(RadioButton) findViewById(R.id.series_rbt1);
		rb2=(RadioButton) findViewById(R.id.series_rbt2);
		rb3=(RadioButton) findViewById(R.id.series_rbt3);
		rb4=(RadioButton) findViewById(R.id.series_rbt4);

	}
	private void initSeriesList(long brand_id)  //本项目中 city 列表存在assets/citys.xml文件中，通过xml解析得到想要的city数据
	{
		Log.w("jhd", "initbrandlist");
		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "2");//series
		params.put("brand_id", ""+brand_id);
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
		fh.get(UserUtils.CARURL, params, callBack);
	}
	private void initSubsList(long series_id) {
		// TODO Auto-generated method stub
		//List<Subs>
		final List<Subs> subs_list_temp=new ArrayList<Subs>();
		Log.w("jhd", "initSubsList");
		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "3");//series
		params.put("series_id", ""+series_id);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);			
				try {
					JSONArray ja=new JSONArray(s);
					for(int i=0;i<ja.length();i++){
						JSONObject temp=ja.getJSONObject(i);
						Subs subs=new Subs();
						subs.setId(temp.getLong("subs_id"));
						subs.setName(temp.getString("subs_name"));
						subs.setSeries_id(temp.getLong("series_id"));
						subs_list_temp.add(subs);				
						Log.e("jhd", "initSubsList:suc");	
					}
					Message msg=new Message();
					msg.what=4;
					//	msg.obj=subs_list_temp;
					subsarray.add(subs_list_temp);

					handler.sendMessage(msg);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					handler.sendEmptyMessage(2);
				}


				//suc				
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);//failed
			}
		};
		fh.get(UserUtils.CARURL, params, callBack);

	};
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.series_tv_back://按了返回键 不用处理直接finish
			
			finish();
			break;

		}
	} 


	//内部类 适配器
	class SubsListAdapter extends BaseAdapter{
		private List<Subs> listdata;
		private LayoutInflater inflater;

		public SubsListAdapter(List<Subs> list,Context context) {

			this.listdata = list;
			inflater=LayoutInflater.from(context);
		}

		@Override
		public int getCount() {return listdata.size();}
		@Override
		public Object getItem(int arg0) {return listdata.get(arg0);}
		@Override
		public long getItemId(int arg0) {return arg0;}

		@Override
		public View getView(int arg0, View converView, ViewGroup arg2) {
			ViewHolder vh=null;
			if(converView==null)
			{
				vh=new ViewHolder();
				converView=inflater.inflate(R.layout.list_item_series, null);
				vh.seriesName=(TextView) converView.findViewById(R.id.list_item_series_tv);				
				converView.setTag(vh);
			}
			else
			{
				vh=(ViewHolder) converView.getTag();
			}
			//数据显示处理
			Subs s=listdata.get(arg0);
			vh.seriesName.setText(s.getName());
			return converView;
		}
		class ViewHolder{		
			TextView seriesName;
		}
	}

	@Override
	public void finish() {
		if(!isFromMycar){
			setResult(1);
		}
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int checkedId) {
		// TODO Auto-generated method stub
		SubsListAdapter subsListAdapter=null;
		switch (checkedId) {
		case R.id.series_rbt1:	
			subsListAdapter=new SubsListAdapter(subsarray.get(0), getApplicationContext());
			selectedSubs=0;
			break;
		case R.id.series_rbt2:
			subsListAdapter=new SubsListAdapter(subsarray.get(1), getApplicationContext());
			selectedSubs=1;
			break;
		case R.id.series_rbt3:
			subsListAdapter=new SubsListAdapter(subsarray.get(2), getApplicationContext());
			selectedSubs=2;
			break;
		case R.id.series_rbt4:
			subsListAdapter=new SubsListAdapter(subsarray.get(3), getApplicationContext());	
			selectedSubs=3;
			break;		
		}
		lv.setAdapter(subsListAdapter);

	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(!isFromMycar){
			Intent it=new Intent(this,ModelAcitvity.class);

			Toast.makeText(this, "贾豪东position:"+position+" id:"+id, 1000).show();
			it.putExtra("subs_id", subsarray.get(selectedSubs).get(position).getId());
			it.putExtra("subs_name", subsarray.get(selectedSubs).get(position).getName());
			it.putExtra("brand_name", brand_name);
			startActivity(it);	
		}
		else{
			Intent it=new Intent(this, MyCarActivity.class);
			it.putExtra("subs_id", subsarray.get(selectedSubs).get(position).getId());
			it.putExtra("subs_name", subsarray.get(selectedSubs).get(position).getName());
			setResult(0, it);
			finish();
		}

	}

}
