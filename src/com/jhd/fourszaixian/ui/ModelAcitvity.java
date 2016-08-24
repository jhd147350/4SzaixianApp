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
import com.jhd.fourszaixian.entity.Model;
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
import android.widget.TextView;
import android.widget.Toast;

public class ModelAcitvity extends Activity implements OnItemClickListener {


	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1://suc
				try {
					JSONArray ja=new JSONArray((String)msg.obj);
					Log.d("jhd", "ja"+ja.toString());
					for(int i=0;i<ja.length();i++){
						JSONObject temp=ja.getJSONObject(i);
						Model m=new Model();
						m.setId(temp.getLong("model_id"));
						m.setName(temp.getString("model_name"));
						m.setImg(temp.getString("model_img"));
						m.setSortkey(temp.getString("model_sortkey"));
						m.setSubs_id(temp.getLong("subs_id"));
						list.add(m);						
					}
					ModelListAdapter adapter=new ModelListAdapter(list, getApplicationContext());
					lv.setAdapter(adapter);
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					Log.e("jhd", "ModelActivity handler：what=1 崩了！！");
				}
				break;
			case 2://failed
				Toast.makeText(getApplicationContext(), "model访问超时请确认网络是否通畅！", Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		};
	};

	
	//判断是否来自mycar
	private boolean isFromMycar=false;
	
	
	
	private String brand_name="1";
	private String subs_name="1";
	private long subs_id;
	private List<Model> list=new ArrayList<Model>();
	
	private ListView lv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_model);
		inintView();
		Intent it=getIntent();
		brand_name = it.getStringExtra("brand_name");
		subs_name = it.getStringExtra("subs_name");
		subs_id=it.getLongExtra("subs_id", 0);
		isFromMycar=it.getBooleanExtra("MyCar", false);
		if(subs_id!=0){
			initModel(subs_id);
		}else{
			Log.e("jhd", "ModelActivity->onCreate->subs_id=="+subs_id);
			finish();
		}
	}
	private void inintView() {
		// TODO Auto-generated method stub
		lv=(ListView) findViewById(R.id.model_listview);
		lv.setOnItemClickListener(this);
		
	}
	private void initModel(long subs_id) {
		// TODO Auto-generated method stub
		Log.w("jhd", "initModel");
		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "4");//model
		params.put("subs_id", ""+subs_id);
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
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		if(!isFromMycar){
			setResult(1);
		}
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}
	
	
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.model_tv_back:
			
			finish();
			break;

		default:
			break;
		}
		
	}
	@Override
	public void onItemClick(AdapterView<?> arg0, View view, int position, long id) {
		// TODO Auto-generated method stub
		if(!isFromMycar){
		Intent it=new Intent(this,CarActivity.class);
		it.putExtra("model_name", list.get(position).getName());
		it.putExtra("model_sortkey", list.get(position).getSortkey());
		it.putExtra("model_img", list.get(position).getImg());
		it.putExtra("subs_name", subs_name);
		it.putExtra("brand_name", brand_name);
		startActivity(it);
		}else{
			Intent it=new Intent(this,MyCarActivity.class);
			it.putExtra("model_name", list.get(position).getName());
			it.putExtra("model_sortkey", list.get(position).getSortkey());
			it.putExtra("model_img", list.get(position).getImg());
			setResult(0, it);
			finish();
		}
		
	}
	
	private StringBuffer buffer=new StringBuffer();//y用来第一次保存首字母索引
	private List<String> firstList=new ArrayList<String>();//保存索引值对面的城市名
	//内部类 适配器
	class ModelListAdapter extends BaseAdapter{
		private List<Model> listdata;
		private LayoutInflater inflater;


		public ModelListAdapter(List<Model> list,Context context) {

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
				Log.i("jhd", "getView->-converView==null");
				vh=new ViewHolder();
				converView=inflater.inflate(R.layout.list_item_model, null);
				vh.modelName=(TextView) converView.findViewById(R.id.list_item_model_tv_modelName);
				vh.sortKey=(TextView) converView.findViewById(R.id.list_item_model_tv_sortKey);		
				converView.setTag(vh);
			}
			else
			{
				Log.i("jhd", "getView->-converView!=null");
				vh=(ViewHolder) converView.getTag();
			}
			//数据显示处理
			Log.i("jhd", "getView->初始化数据："+arg0);
			Model m=listdata.get(arg0);
			String sort=m.getSortkey();
			String name=m.getName();

			if(buffer.indexOf(sort)==-1){   //以下俩个if 设置显示首字母索引
				buffer.append(sort);
				firstList.add(name);
			}
			if(firstList.contains(name)){
				vh.sortKey.setText(sort+" 款");
				vh.sortKey.setVisibility(View.VISIBLE);//显示
			}else {
				vh.sortKey.setVisibility(View.GONE);//不显示
			}
			vh.modelName.setText(name);
			return converView;
		}

		class ViewHolder{						
			TextView sortKey;		
			TextView modelName;
		}
	}


}
