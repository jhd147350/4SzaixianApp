package com.jhd.fourszaixian.ui;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;



import com.baidu.mapapi.BMapManager;
import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Brand;
import com.jhd.fourszaixian.myview.SideBar;
import com.jhd.fourszaixian.myview.SideBar.OnTouchingLetterChangedListener;
import com.jhd.fourszaixian.utils.UserUtils;



import android.R.integer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.KeyEvent;
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

public class BrandActivity extends Activity implements OnItemClickListener {

	//判断是否从mycar页面跳转而来，默认false
	private boolean isFromMyCar=false;


	private FinalBitmap finalBitmap;
	private ListView listView;	
	private SideBar sidebar;
	private TextView dialog;

	private List<Brand> list=new ArrayList<Brand>();
	private List<Bitmap> list_bm=new ArrayList<Bitmap>();
	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1://suc
				try {
					JSONArray ja=new JSONArray((String)msg.obj);
					for(int i=0;i<ja.length();i++){
						JSONObject temp=ja.getJSONObject(i);
						Brand b=new Brand();
						b.setId(temp.getLong("brand_id"));
						b.setName(temp.getString("brand_name"));
						Log.e("jhd2", temp.getString("brand_img"));
						b.setImg(temp.getString("brand_img"));
						b.setSortkey(temp.getString("brand_sortkey"));
						list.add(b);						
					}
					BrandListAdapter adapter=new BrandListAdapter(list, getApplicationContext(),false);
					listView.setAdapter(adapter);
					//-------------------------------------------------------------------				
					//				handler.sendEmptyMessage(3);//先加载文字完事之后加载图片

				} catch (JSONException e) {

					e.printStackTrace();
				}

				break;
			case 2:
				Toast.makeText(getApplicationContext(), "访问超时请确认网络是否通畅！", Toast.LENGTH_SHORT).show();
				break;
				//			case 3:
				//				new Thread(){ //后加载图片
				//					public void run() {
				//						for(int i=0;i<list.size();i++){
				//							String imgurl=list.get(i).getImg();
				//							if(!imgurl.equals("1")){
				//								Message msg=new Message();
				//								Bitmap bitmap = getBitmapFromUrl(imgurl);
				//								list_bm.add(bitmap);
				//								msg.obj=bitmap;
				//								msg.what=4;
				//								msg.arg1=i;
				//								handler.sendMessage(msg);
				//								Log.e("jhd", "i:"+i+"img:"+imgurl);
				//							}	
				//							else{
				//								list_bm.add(null);
				//							}
				//						}				
				//					};
				//				}.start();
				//				
				//				break;
				//			case 4:
				//
				//				Log.w("jhd", "handler:4");
				//				BrandListAdapter adapter=new BrandListAdapter(list, getApplicationContext(),true);
				//				listView.setAdapter(adapter);
				//				break;

			default:

				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_brand);
		initView();
		finalBitmap = FinalBitmap.create(this);
		finalBitmap.configLoadingImage(R.drawable.wutu);
		finalBitmap.configLoadfailImage(R.drawable.wutu);
		Intent it=getIntent();
		isFromMyCar=it.getBooleanExtra("MyCar", false);//判断是否来自mycar


		//出事listview header
		View view=LayoutInflater.from(this).inflate(R.layout.list_item_brand_header, null);	

		listView.addHeaderView(view);  //添加listheader

		initBrandList();//将数据加载到list中

		BrandListAdapter adapter=new BrandListAdapter(list, this,false);//新建适配器
		listView.setAdapter(adapter);//绑定适配器

		sidebar.setTextView(dialog);

		//sidebar设置监听
		sidebar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String sortKey) {
				// TODO Auto-generated method stub
				listView.setSelection(findIndexBySortKey(list, sortKey)+1); //因为有header 所以+1
			}
		});
	}

	private void initView() {

		listView=(ListView) findViewById(R.id.brand_listview);
		sidebar=(SideBar) findViewById(R.id.sidebar);
		dialog=(TextView) findViewById(R.id.dialog);
		listView.setOnItemClickListener(this);
	}

	public void onItemClick(AdapterView<?> parent,View view,int position,long id)
	{
		if(!isFromMyCar){
			Intent it=new Intent(this,SeriesActivity.class);
			Toast.makeText(this, "position:"+position+" id:"+id, 1000).show();
			if(position!=0){   //position==0 证明是点击了 listview的header ，这里暂时不处理点击了header的情况
				it.putExtra("brand_id", list.get(position-1).getId());
				it.putExtra("brand_name", list.get(position-1).getName());
				startActivity(it);				
			}
		}
		else{
			Intent it=new Intent(this,MyCarActivity.class);
			it.putExtra("brand_id", list.get(position-1).getId());
			it.putExtra("brand_name", list.get(position-1).getName());
			setResult(0, it);	
			
			Log.e("jhd", "fanhui0"+list.get(position-1).getName());
			finish();

		}
	}

	//	private Bitmap getBitmapFromUrl(String imgUrl) {//得到图片并写入内存卡//根据id生成文件名，这样唯一
	//		URL url;
	//		Bitmap bitmap = null;
	//		try {
	//			Log.e("jhd", "getBitmapFromUrl--img:"+imgUrl);
	//			url = new URL(imgUrl);
	//			InputStream is = url.openStream();
	//			bitmap = BitmapFactory.decodeStream(is);
	//			is.close();//关闭
	//
	//		} catch (MalformedURLException e) {
	//			e.printStackTrace();
	//			Log.e("jhd", "getBitmapFromUrl -异常");
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//			Log.e("jhd", "getBitmapFromUrl -异常");
	//		}
	//		return bitmap;
	//	}


	public void onClick(View v){
		switch (v.getId()) {
		case R.id.brand_tv_back://按了返回键 不用处理直接finish
			
			finish();
			break;
		default:
			break;
		}
	} 

	

	private StringBuffer buffer=new StringBuffer();//y用来第一次保存首字母索引
	private List<String> firstList=new ArrayList<String>();//保存索引值对面的城市名
	//内部类 适配器
	class BrandListAdapter extends BaseAdapter{
		private List<Brand> listdata;
		private LayoutInflater inflater;
		private boolean isLoadImg=false;


		public BrandListAdapter(List<Brand> list,Context context,boolean isLoadImg) {

			this.listdata = list;
			inflater=LayoutInflater.from(context);
			this.isLoadImg=isLoadImg;
		}

		@Override
		public int getCount() {

			return listdata.size();
		}

		@Override
		public Object getItem(int arg0) {

			return listdata.get(arg0);
		}

		@Override
		public long getItemId(int arg0) {

			return arg0;
		}

		@Override
		public View getView(int arg0, View converView, ViewGroup arg2) {

			ViewHolder vh=null;
			if(converView==null)
			{
				Log.i("jhd", "getView->-converView==null");
				vh=new ViewHolder();
				converView=inflater.inflate(R.layout.list_item_brand, null);
				vh.brandName=(TextView) converView.findViewById(R.id.list_item_brand_tv_brandName);
				vh.sortKey=(TextView) converView.findViewById(R.id.list_item_brand_tv_sortKey);
				vh.img=(ImageView) converView.findViewById(R.id.list_item_brand_img);
				converView.setTag(vh);
			}
			else
			{
				Log.i("jhd", "getView->-converView!=null");
				vh=(ViewHolder) converView.getTag();
			}
			//数据显示处理
			Log.i("jhd", "getView->初始化数据："+arg0);
			Brand b=listdata.get(arg0);
			String sort=b.getSortkey();
			String name=b.getName();

			if(buffer.indexOf(sort)==-1){   //以下俩个if 设置显示首字母索引
				buffer.append(sort);
				firstList.add(name);
			}
			if(firstList.contains(name)){
				vh.sortKey.setText(sort);
				vh.sortKey.setVisibility(View.VISIBLE);//显示
			}else {
				vh.sortKey.setVisibility(View.GONE);//不显示
			}
			vh.brandName.setText(name);
			//这里使用框架加载图片只需一步
			finalBitmap.display(vh.img, UserUtils.IMGURLFOLDER+list.get(arg0).getImg());

			//根据isloadimg 来加载图片
			//			if(isLoadImg){
			//				if(list_bm.size()>=(arg0+1) && list_bm.get(arg0)!=null){
			//					Log.w("jhd", "getView->"+arg0+"加载了一张图");
			//					vh.img.setImageBitmap(list_bm.get(arg0));
			//				}
			//				
			//			}

			return converView;
		}

		class ViewHolder{
			ImageView img;			
			TextView sortKey;		
			TextView brandName;
		}

	}

	private void initBrandList()  //本项目中 city 列表存在assets/citys.xml文件中，通过xml解析得到想要的city数据
	{

		Log.i("jhd", "initbrandlist");
		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");//1表示登录操作
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


	//根据sortkey找到索引位置
	private int findIndexBySortKey(List<Brand> list,String sortKey)
	{

		if(list!=null)
		{
			for(int i=0;i<list.size();i++){
				Brand b=list.get(i);
				if(sortKey.equals(b.getSortkey())){
					return i;
				}
			}
		}
		else{
			Log.e("jhd", "没有根据传来的sortKey找到对应的索引值");
		}
		Log.e("jhd", "findIndexBySortKey：return -2");
		return -2; ///由于有header 所以不能返回-1，返回-2  这样设置不上listview选择的是哪个
	}
	@Override
	public void finish() {
		if(!isFromMyCar){
			setResult(1);
		}
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

}