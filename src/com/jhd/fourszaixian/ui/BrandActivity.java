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

	//�ж��Ƿ��mycarҳ����ת������Ĭ��false
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
					//				handler.sendEmptyMessage(3);//�ȼ�����������֮�����ͼƬ

				} catch (JSONException e) {

					e.printStackTrace();
				}

				break;
			case 2:
				Toast.makeText(getApplicationContext(), "���ʳ�ʱ��ȷ�������Ƿ�ͨ����", Toast.LENGTH_SHORT).show();
				break;
				//			case 3:
				//				new Thread(){ //�����ͼƬ
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
		isFromMyCar=it.getBooleanExtra("MyCar", false);//�ж��Ƿ�����mycar


		//����listview header
		View view=LayoutInflater.from(this).inflate(R.layout.list_item_brand_header, null);	

		listView.addHeaderView(view);  //���listheader

		initBrandList();//�����ݼ��ص�list��

		BrandListAdapter adapter=new BrandListAdapter(list, this,false);//�½�������
		listView.setAdapter(adapter);//��������

		sidebar.setTextView(dialog);

		//sidebar���ü���
		sidebar.setOnTouchingLetterChangedListener(new OnTouchingLetterChangedListener() {

			@Override
			public void onTouchingLetterChanged(String sortKey) {
				// TODO Auto-generated method stub
				listView.setSelection(findIndexBySortKey(list, sortKey)+1); //��Ϊ��header ����+1
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
			if(position!=0){   //position==0 ֤���ǵ���� listview��header ��������ʱ����������header�����
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

	//	private Bitmap getBitmapFromUrl(String imgUrl) {//�õ�ͼƬ��д���ڴ濨//����id�����ļ���������Ψһ
	//		URL url;
	//		Bitmap bitmap = null;
	//		try {
	//			Log.e("jhd", "getBitmapFromUrl--img:"+imgUrl);
	//			url = new URL(imgUrl);
	//			InputStream is = url.openStream();
	//			bitmap = BitmapFactory.decodeStream(is);
	//			is.close();//�ر�
	//
	//		} catch (MalformedURLException e) {
	//			e.printStackTrace();
	//			Log.e("jhd", "getBitmapFromUrl -�쳣");
	//		} catch (IOException e) {
	//			e.printStackTrace();
	//			Log.e("jhd", "getBitmapFromUrl -�쳣");
	//		}
	//		return bitmap;
	//	}


	public void onClick(View v){
		switch (v.getId()) {
		case R.id.brand_tv_back://���˷��ؼ� ���ô���ֱ��finish
			
			finish();
			break;
		default:
			break;
		}
	} 

	

	private StringBuffer buffer=new StringBuffer();//y������һ�α�������ĸ����
	private List<String> firstList=new ArrayList<String>();//��������ֵ����ĳ�����
	//�ڲ��� ������
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
			//������ʾ����
			Log.i("jhd", "getView->��ʼ�����ݣ�"+arg0);
			Brand b=listdata.get(arg0);
			String sort=b.getSortkey();
			String name=b.getName();

			if(buffer.indexOf(sort)==-1){   //��������if ������ʾ����ĸ����
				buffer.append(sort);
				firstList.add(name);
			}
			if(firstList.contains(name)){
				vh.sortKey.setText(sort);
				vh.sortKey.setVisibility(View.VISIBLE);//��ʾ
			}else {
				vh.sortKey.setVisibility(View.GONE);//����ʾ
			}
			vh.brandName.setText(name);
			//����ʹ�ÿ�ܼ���ͼƬֻ��һ��
			finalBitmap.display(vh.img, UserUtils.IMGURLFOLDER+list.get(arg0).getImg());

			//����isloadimg ������ͼƬ
			//			if(isLoadImg){
			//				if(list_bm.size()>=(arg0+1) && list_bm.get(arg0)!=null){
			//					Log.w("jhd", "getView->"+arg0+"������һ��ͼ");
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

	private void initBrandList()  //����Ŀ�� city �б����assets/citys.xml�ļ��У�ͨ��xml�����õ���Ҫ��city����
	{

		Log.i("jhd", "initbrandlist");
		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");//1��ʾ��¼����
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


	//����sortkey�ҵ�����λ��
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
			Log.e("jhd", "û�и��ݴ�����sortKey�ҵ���Ӧ������ֵ");
		}
		Log.e("jhd", "findIndexBySortKey��return -2");
		return -2; ///������header ���Բ��ܷ���-1������-2  �������ò���listviewѡ������ĸ�
	}
	@Override
	public void finish() {
		if(!isFromMyCar){
			setResult(1);
		}
		super.finish();
		//����activity��ת�Ķ���
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

}