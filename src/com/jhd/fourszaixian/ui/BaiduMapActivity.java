package com.jhd.fourszaixian.ui;

import java.util.List;



import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BaiduMap.OnMarkerClickListener;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.InfoWindow;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationConfiguration;
import com.baidu.mapapi.map.MyLocationData;
import com.baidu.mapapi.map.MarkerOptions.MarkerAnimateType;
import com.baidu.mapapi.map.TextOptions;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.navi.BaiduMapAppNotSupportNaviException;
import com.baidu.mapapi.navi.BaiduMapNavigation;
import com.baidu.mapapi.navi.NaviParaOption;
import com.baidu.mapapi.search.core.PoiInfo;
import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiNearbySearchOption;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.utils.OpenClientUtil;
import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.utils.MyOrientationListener;
import com.jhd.fourszaixian.utils.UserUtils;
import com.jhd.fourszaixian.utils.MyOrientationListener.OnOrientationListener;

import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class BaiduMapActivity extends Activity implements OnGetPoiSearchResultListener {
	private Handler handler=new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case 1://poisearch
				PoiNearbySearchOption option=new PoiNearbySearchOption().keyword(destinationTarget).location(localposition).radius(5000).pageCapacity(9);//5000m
				poiSearch.searchNearby(option);
				break;

			default:
				break;
			}
		};
	};


	//btn����mode
	private Button btn_mode;

	private String destinationTarget="����վ";
	private TextView currentloc;
	private TextView currentfun;
	//�������
	MyOrientationListener myOrientationListener;
	private float mXDirection=0;//����Ƕ�,һ��ʼ��Ϊ0

	private MapView mMapView = null; 
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	//ʵ�ֶ�λ
	private BaiduMap mBaiduMap;
	private BitmapDescriptor mCurrentMarker;
	private MyLocationConfiguration config;
	private com.baidu.mapapi.map.MyLocationConfiguration.LocationMode mCurrentMode;
	private boolean isFirstLoc=true;
	private LatLng localposition;//��¼��ǰλ��
	//��ͼģʽ
	private int mode=1;//1.��ͨ��2�����棬3���̡�Ĭ����1��

	//ʵ��poi��������վ����Ϣ
	private PoiSearch poiSearch;
	//private Marker mMarker;//Ŀ�ĵ�
	private BitmapDescriptor bd = BitmapDescriptorFactory.fromResource(R.drawable.icon_gcoding);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		SDKInitializer.initialize(getApplicationContext());
		setContentView(R.layout.activity_baidu_map);
		mMapView=(MapView) findViewById(R.id.bmapView);
		currentloc=(TextView) findViewById(R.id.baidumapcurrentloc);
		currentfun=(TextView) findViewById(R.id.baidumapcurrentfun);
		btn_mode=(Button) findViewById(R.id.baidumap_mode);
		btn_mode.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				switch (mode) {
				case 1:
					mode=2;
					btn_mode.setText("����");
					mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING;
					break;
				case 2:
					mode=3;
					btn_mode.setText("����");
					mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.COMPASS;
					break;
				case 3:
					mode=1;
					btn_mode.setText("��ͨ");
					mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL;
					break;
				}
				config= new MyLocationConfiguration(mCurrentMode, true, null); //���һ��������mCurrentMaker��null��λϵͳĬ�� 
				mBaiduMap.setMyLocationConfigeration(config);

			}
		});
		currentfun.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//�õ�֮ǰҳ���intent
		Intent it=getIntent();
		if(it.getStringExtra("target")!=null){
			destinationTarget=it.getStringExtra("target");
			currentfun.setText(destinationTarget);
		}

		//��λ�õ���ǰ��ַ
		mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
		mLocationClient.registerLocationListener( myListener );    //ע���������
		initLocation();
		mLocationClient.start();

		mBaiduMap = mMapView.getMap();
		//	mBaiduMap.setBuildingsEnabled(true);

		//mBaiduMap.setMaxAndMinZoomLevel(20, 18);
		MapStatusUpdate zoomTo = MapStatusUpdateFactory.zoomTo(15);//�������ż���
		mBaiduMap.animateMapStatus(zoomTo);
		mBaiduMap.hideInfoWindow();

		mBaiduMap.setMyLocationEnabled(true);
		mCurrentMarker = BitmapDescriptorFactory  
				.fromResource(R.drawable.ic_launcher);  
		mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL;
		config= new MyLocationConfiguration(mCurrentMode, true, null); //���һ��������mCurrentMaker��null��λϵͳĬ�� 
		mBaiduMap.setMyLocationConfigeration(config);
		//���õ��marker����
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				//	Log.e("jhd", "ccccccccccccccccccccc");
				//marker.getPosition();
				startNavi(localposition, marker.getPosition());
				return false;
			}
		});


		initOritationListener();//�õ�localpositon���ʼ���������
		initPoiSearch();//��ʼ��poisearch
	}
	private void initPoiSearch() {
		// TODO Auto-generated method stub
		//��һ��������POI����ʵ��
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(this);
	}
	/**
	 * �����ٶȵ�ͼ����(Native)
	 */
	public void startNavi(LatLng pt1,LatLng pt2) {
		// ���� ��������
		NaviParaOption para = new NaviParaOption()
		.startPoint(pt1).endPoint(pt2)
		.startName("��ǰλ��").endName("Ŀ�ĵ�");
		try {
			BaiduMapNavigation.openBaiduMapNavi(para, this);
		} catch (BaiduMapAppNotSupportNaviException e) {
			e.printStackTrace();
			showDialog();
		}
	}

	/**
	 * ��ʾδ��װ�ٶȵ�ͼapp��app�汾����
	 */
	public void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("����δ��װ�ٶȵ�ͼapp��app�汾���ͣ����ȷ�ϰ�װ��");
		builder.setTitle("��ʾ");
		builder.setPositiveButton("ȷ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				OpenClientUtil.getLatestBaiduMapApp(BaiduMapActivity.this);
			}
		});
		builder.setNegativeButton("ȡ��", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override  
	protected void onDestroy() {  //�ٶȵ�ͼ������Դ�Ļ���
		super.onDestroy();  
		poiSearch.destroy();
		mLocationClient.stop();
		// �رն�λͼ��
		mBaiduMap.setMyLocationEnabled(false);
		//��activityִ��onDestroyʱִ��mMapView.onDestroy()��ʵ�ֵ�ͼ�������ڹ���  
		mMapView.onDestroy();  

		bd.recycle();
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//��activityִ��onResumeʱִ��mMapView. onResume ()��ʵ�ֵ�ͼ�������ڹ���  
		mMapView.onResume(); 
		if(!mLocationClient.isStarted()){//������λ
			mLocationClient.start();
		}
		// �������򴫸���  
		myOrientationListener.start();  
	}  
	@Override  
	protected void onPause() {  //��ͣʱ�رգ���ʡ��Դ
		super.onPause();  
		//��activityִ��onPauseʱִ��mMapView. onPause ()��ʵ�ֵ�ͼ�������ڹ���  
		mMapView.onPause();  
		mLocationClient.stop();
		// �رշ��򴫸���  
		myOrientationListener.stop();  
	}  

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy
				);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span=2000;//30�붨λһ��
		option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(false);//��ѡ��Ĭ��false����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ��ɱ��
		option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		mLocationClient.setLocOption(option);
	}

	//ʵ�ֶ�λ�ӿڣ�BDLocationListener�ӿ���1��������Ҫʵ�֣� 1.�����첽���صĶ�λ�����������BDLocation���Ͳ�����
	public class MyLocationListener implements BDLocationListener {
		MyLocationData locData;

		@Override
		public void onReceiveLocation(BDLocation location) {

			//Receive Location
			if (location == null || mMapView == null) {
				return;
			}
			LatLng ll = new LatLng(location.getLatitude(),
					location.getLongitude());
			if (isFirstLoc) {

				isFirstLoc = false;

				MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
				mBaiduMap.animateMapStatus(u);//��һ����ʾ��ͼ�ϵĵ�ǰλ��

				/*
				 * *
				 * showInfoWindow���÷�
				 * *				
				mBaiduMap.showMapPoi(true);
				TextView tv=new TextView(getApplicationContext());
				tv.setText("��ǰλ��");
				tv.setBackgroundColor(Color.BLUE);
				mBaiduMap.showInfoWindow(new InfoWindow(tv, ll, -20));	
				 */

				localposition=ll;
				handler.sendEmptyMessage(1);
			}		

			locData = new MyLocationData.Builder()
			.accuracy(10)//location.getRadius()
			// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360
			.direction(mXDirection)
			.latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			Log.e("jhd", "direction:"+location.getDirection());
			Log.e("jhd", "AddrStr:"+location.getAddrStr());
			localposition=ll;
			//���õ�ǰλ��
			currentloc.setText(location.getAddrStr());
			UserUtils.CURRENT_LOACTION=location.getAddrStr();
		}
	}
	@Override
	public void finish() {
		super.finish();
		//����activity��ת�Ķ���
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}


	/** 
	 * ��ʼ�����򴫸��� 
	 */  
	private void initOritationListener()  
	{  
		myOrientationListener = new MyOrientationListener(  
				getApplicationContext());  
		myOrientationListener  
		.setOnOrientationListener(new OnOrientationListener()  
		{  
			@Override  
			public void onOrientationChanged(float x)  
			{  
				mXDirection = x;  
				Log.e("jhd", "����"+x);
				if(localposition!=null){

					// ���춨λ����  
					MyLocationData locData = new MyLocationData.Builder()  
					.accuracy(10)  
					// �˴����ÿ����߻�ȡ���ķ�����Ϣ��˳ʱ��0-360  
					.direction(x)  
					.latitude(localposition.latitude)  
					.longitude(localposition.longitude).build();  
					// ���ö�λ����  
					mBaiduMap.setMyLocationData(locData);  
					// �����Զ���ͼ��  
					//				BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory  
					//						.fromResource(R.drawable.navi_map_gps_locked);  
					//				MyLocationConfigeration config = new MyLocationConfigeration(  
					//						mCurrentMode, true, null);  
					//				mBaiduMap.setMyLocationConfigeration(config);  
				}else{
					Log.e("jhd", "localpositionΪ��--����"+x);
				}

			}  
		});  
	}
	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		Log.e("jhd", "onGetPoiDetailResult");
		//	result
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "��Ǹ��δ�ҵ����", Toast.LENGTH_SHORT)
			.show();
		} else {
			Toast.makeText(this, result.getName() + ": " + result.getAddress(), Toast.LENGTH_SHORT)
			.show();
		}
	}
	@Override
	public void onGetPoiResult(PoiResult result) {
		Log.e("jhd1", "onGetPoiResult");
		if (result == null|| result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND) {		
			Toast.makeText(getApplicationContext(), "δ�ҵ����", Toast.LENGTH_LONG).show();
			Log.e("jhd1", "result == null || result.error == SearchResult.ERRORNO.RESULT_NOT_FOUND");		
		}else{
			Log.e("jhd1", "result != null && result.error != SearchResult.ERRORNO.RESULT_NOT_FOUND");
		}
		Log.e("jhd1", "onGetPoiResult1");
		if (result.error == SearchResult.ERRORNO.NO_ERROR) {
			Log.e("jhd1", "result.error == SearchResult.ERRORNO.NO_ERROR");
			List<PoiInfo> allPoi = result.getAllPoi();
			if(allPoi==null){
				Log.e("jhd1", "allPoi==null");
				Toast.makeText(getApplicationContext(), "�����쳣", Toast.LENGTH_SHORT).show();
			}
			else{
				Log.e("jhd1", "allPoi!=null");
				BitmapDescriptor bd_temp ;
				View v_temp=LayoutInflater.from(getApplicationContext()).inflate(R.layout.text_up_img_down, null);
				TextView tv_temp = (TextView) v_temp.findViewById(R.id.baidumap_custom_text);
				ImageView img_temp = (ImageView) v_temp.findViewById(R.id.baidumap_custom_img);
				int [] imgIds={R.drawable.icon_marka,R.drawable.icon_markb,R.drawable.icon_markc,
						R.drawable.icon_markd,R.drawable.icon_marke,R.drawable.icon_markf,
						R.drawable.icon_markg,R.drawable.icon_markh,R.drawable.icon_marki,R.drawable.icon_markj,
						R.drawable.icon_gcoding,R.drawable.icon_gcoding,R.drawable.icon_gcoding,
						R.drawable.icon_gcoding,R.drawable.icon_gcoding,R.drawable.icon_gcoding,
						R.drawable.icon_gcoding,R.drawable.icon_gcoding,R.drawable.icon_gcoding,R.drawable.icon_gcoding};
				for(int i=0;i<allPoi.size();i++){

					PoiInfo poi = allPoi.get(i);
					String address = poi.address;
					String name = poi.name;
					LatLng ll1 = poi.location;
					tv_temp.setText(name);
					img_temp.setImageResource(imgIds[i]);
					bd_temp=BitmapDescriptorFactory.fromView(v_temp);
					MarkerOptions oo = new MarkerOptions().position(ll1).icon(bd_temp)
							.anchor(0.5f, 1.0f).zIndex(7);

					oo.animateType(MarkerAnimateType.grow);
					mBaiduMap.addOverlay(oo);
				}	
			}
		}else{
			Log.e("jhd1", "result.error != SearchResult.ERRORNO.NO_ERROR");
			Log.e("jhd1", "result.error=="+result.error);
		}
		
		Log.e("jhd1", "onGetPoiResult2");
	}  
}
