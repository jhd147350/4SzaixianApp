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


	//btn——mode
	private Button btn_mode;

	private String destinationTarget="加油站";
	private TextView currentloc;
	private TextView currentfun;
	//方向监听
	MyOrientationListener myOrientationListener;
	private float mXDirection=0;//方向角度,一开始归为0

	private MapView mMapView = null; 
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	//实现定位
	private BaiduMap mBaiduMap;
	private BitmapDescriptor mCurrentMarker;
	private MyLocationConfiguration config;
	private com.baidu.mapapi.map.MyLocationConfiguration.LocationMode mCurrentMode;
	private boolean isFirstLoc=true;
	private LatLng localposition;//记录当前位置
	//地图模式
	private int mode=1;//1.普通，2，跟随，3罗盘。默认是1；

	//实现poi检索加油站等信息
	private PoiSearch poiSearch;
	//private Marker mMarker;//目的地
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
					btn_mode.setText("跟随");
					mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.FOLLOWING;
					break;
				case 2:
					mode=3;
					btn_mode.setText("罗盘");
					mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.COMPASS;
					break;
				case 3:
					mode=1;
					btn_mode.setText("普通");
					mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL;
					break;
				}
				config= new MyLocationConfiguration(mCurrentMode, true, null); //最后一个参数填mCurrentMaker，null则位系统默认 
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
		//得到之前页面的intent
		Intent it=getIntent();
		if(it.getStringExtra("target")!=null){
			destinationTarget=it.getStringExtra("target");
			currentfun.setText(destinationTarget);
		}

		//定位得到当前地址
		mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
		mLocationClient.registerLocationListener( myListener );    //注册监听函数
		initLocation();
		mLocationClient.start();

		mBaiduMap = mMapView.getMap();
		//	mBaiduMap.setBuildingsEnabled(true);

		//mBaiduMap.setMaxAndMinZoomLevel(20, 18);
		MapStatusUpdate zoomTo = MapStatusUpdateFactory.zoomTo(15);//设置缩放级别
		mBaiduMap.animateMapStatus(zoomTo);
		mBaiduMap.hideInfoWindow();

		mBaiduMap.setMyLocationEnabled(true);
		mCurrentMarker = BitmapDescriptorFactory  
				.fromResource(R.drawable.ic_launcher);  
		mCurrentMode=com.baidu.mapapi.map.MyLocationConfiguration.LocationMode.NORMAL;
		config= new MyLocationConfiguration(mCurrentMode, true, null); //最后一个参数填mCurrentMaker，null则位系统默认 
		mBaiduMap.setMyLocationConfigeration(config);
		//设置点击marker监听
		mBaiduMap.setOnMarkerClickListener(new OnMarkerClickListener() {

			@Override
			public boolean onMarkerClick(Marker marker) {
				//	Log.e("jhd", "ccccccccccccccccccccc");
				//marker.getPosition();
				startNavi(localposition, marker.getPosition());
				return false;
			}
		});


		initOritationListener();//得到localpositon后初始化方向监听
		initPoiSearch();//初始化poisearch
	}
	private void initPoiSearch() {
		// TODO Auto-generated method stub
		//第一步，创建POI检索实例
		poiSearch = PoiSearch.newInstance();
		poiSearch.setOnGetPoiSearchResultListener(this);
	}
	/**
	 * 启动百度地图导航(Native)
	 */
	public void startNavi(LatLng pt1,LatLng pt2) {
		// 构建 导航参数
		NaviParaOption para = new NaviParaOption()
		.startPoint(pt1).endPoint(pt2)
		.startName("当前位置").endName("目的地");
		try {
			BaiduMapNavigation.openBaiduMapNavi(para, this);
		} catch (BaiduMapAppNotSupportNaviException e) {
			e.printStackTrace();
			showDialog();
		}
	}

	/**
	 * 提示未安装百度地图app或app版本过低
	 */
	public void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setMessage("您尚未安装百度地图app或app版本过低，点击确认安装？");
		builder.setTitle("提示");
		builder.setPositiveButton("确认", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				OpenClientUtil.getLatestBaiduMapApp(BaiduMapActivity.this);
			}
		});
		builder.setNegativeButton("取消", new OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.create().show();
	}

	@Override  
	protected void onDestroy() {  //百度地图各种资源的回收
		super.onDestroy();  
		poiSearch.destroy();
		mLocationClient.stop();
		// 关闭定位图层
		mBaiduMap.setMyLocationEnabled(false);
		//在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理  
		mMapView.onDestroy();  

		bd.recycle();
	}  
	@Override  
	protected void onResume() {  
		super.onResume();  
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
		mMapView.onResume(); 
		if(!mLocationClient.isStarted()){//开启定位
			mLocationClient.start();
		}
		// 开启方向传感器  
		myOrientationListener.start();  
	}  
	@Override  
	protected void onPause() {  //暂停时关闭，节省资源
		super.onPause();  
		//在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理  
		mMapView.onPause();  
		mLocationClient.stop();
		// 关闭方向传感器  
		myOrientationListener.stop();  
	}  

	private void initLocation(){
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy
				);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
		option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系
		int span=2000;//30秒定位一次
		option.setScanSpan(span);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		option.setOpenGps(true);//可选，默认false,设置是否使用gps
		option.setLocationNotify(true);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		option.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		option.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		option.setIgnoreKillProcess(false);//可选，默认false，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认杀死
		option.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
		mLocationClient.setLocOption(option);
	}

	//实现定位接口，BDLocationListener接口有1个方法需要实现： 1.接收异步返回的定位结果，参数是BDLocation类型参数。
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
				mBaiduMap.animateMapStatus(u);//第一次显示地图上的当前位置

				/*
				 * *
				 * showInfoWindow的用法
				 * *				
				mBaiduMap.showMapPoi(true);
				TextView tv=new TextView(getApplicationContext());
				tv.setText("当前位置");
				tv.setBackgroundColor(Color.BLUE);
				mBaiduMap.showInfoWindow(new InfoWindow(tv, ll, -20));	
				 */

				localposition=ll;
				handler.sendEmptyMessage(1);
			}		

			locData = new MyLocationData.Builder()
			.accuracy(10)//location.getRadius()
			// 此处设置开发者获取到的方向信息，顺时针0-360
			.direction(mXDirection)
			.latitude(location.getLatitude())
			.longitude(location.getLongitude()).build();
			mBaiduMap.setMyLocationData(locData);
			Log.e("jhd", "direction:"+location.getDirection());
			Log.e("jhd", "AddrStr:"+location.getAddrStr());
			localposition=ll;
			//设置当前位置
			currentloc.setText(location.getAddrStr());
			UserUtils.CURRENT_LOACTION=location.getAddrStr();
		}
	}
	@Override
	public void finish() {
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}


	/** 
	 * 初始化方向传感器 
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
				Log.e("jhd", "方向："+x);
				if(localposition!=null){

					// 构造定位数据  
					MyLocationData locData = new MyLocationData.Builder()  
					.accuracy(10)  
					// 此处设置开发者获取到的方向信息，顺时针0-360  
					.direction(x)  
					.latitude(localposition.latitude)  
					.longitude(localposition.longitude).build();  
					// 设置定位数据  
					mBaiduMap.setMyLocationData(locData);  
					// 设置自定义图标  
					//				BitmapDescriptor mCurrentMarker = BitmapDescriptorFactory  
					//						.fromResource(R.drawable.navi_map_gps_locked);  
					//				MyLocationConfigeration config = new MyLocationConfigeration(  
					//						mCurrentMode, true, null);  
					//				mBaiduMap.setMyLocationConfigeration(config);  
				}else{
					Log.e("jhd", "localposition为空--方向："+x);
				}

			}  
		});  
	}
	@Override
	public void onGetPoiDetailResult(PoiDetailResult result) {
		Log.e("jhd", "onGetPoiDetailResult");
		//	result
		if (result.error != SearchResult.ERRORNO.NO_ERROR) {
			Toast.makeText(this, "抱歉，未找到结果", Toast.LENGTH_SHORT)
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
			Toast.makeText(getApplicationContext(), "未找到结果", Toast.LENGTH_LONG).show();
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
				Toast.makeText(getApplicationContext(), "检索异常", Toast.LENGTH_SHORT).show();
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
