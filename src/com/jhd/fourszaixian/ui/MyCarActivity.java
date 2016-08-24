package com.jhd.fourszaixian.ui;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Car;
import com.jhd.fourszaixian.utils.UserUtils;

public class MyCarActivity extends Activity {

	private EditText chenum;
	private TextView chexi;
	private TextView chexing;
	private TextView pinpai;
	private Button keep;

	// ---------------------
	private long brand_id;
	private String brand_name;
	private long subs_id;
	private String subs_name;
	private String model_sortkey;
	private String model_name;
	private String model_img;
	// -----------------------
	private boolean isBrandNull = true;
	private boolean isSubsNull = true;

	String uid;
	Car car;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mycar);
		Intent it = getIntent();
		uid = (String) it.getSerializableExtra("uid");
		System.out.println("---------uid-------"+uid);
		chenum = (EditText) findViewById(R.id.mycar_chenum);
		chexi = (TextView) findViewById(R.id.mycar_chexi);
		chexing = (TextView) findViewById(R.id.mycar_chexing);
		pinpai = (TextView) findViewById(R.id.mycar_pinpai);
		keep = (Button) findViewById(R.id.mycar_keep);
		car = new Car();

		keep.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				car.setCarBrand(pinpai.getText().toString());
				car.setCarModel(chexing.getText().toString());
				car.setCarSeries(chexi.getText().toString());
				car.setUid(uid);
				System.out.println("--------pimpai-----"+pinpai.getText().toString()+"dfsdfsd");
				if(pinpai.getText().toString().equals("")||chexing.getText().toString().equals("")
						||chexi.getText().toString().equals("")){
					Toast.makeText(getApplicationContext(), "品牌、车型、车系不为空！", Toast.LENGTH_SHORT).show();
				}
				if((!pinpai.getText().toString().equals(""))&&(!chexing.getText().toString().equals(""))
						&&(!chexi.getText().toString().equals(""))){
					findCarByUid(uid);	
				}
				
			}
		});
	}

	// /-------------------------------------
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.mycar_img_pinpai:
			Intent it1 = new Intent(getApplicationContext(),
					BrandActivity.class);
			it1.putExtra("MyCar", true);
			startActivityForResult(it1, UserUtils.MycarToBrandRequestCode);
			break;
		case R.id.mycar_img_chexi:
			if (!isBrandNull) {
				Intent it2 = new Intent(getApplicationContext(),
						SeriesActivity.class);
				it2.putExtra("MyCar", true);
				it2.putExtra("brand_id", brand_id);
				it2.putExtra("brand_name", brand_name);
				startActivityForResult(it2, UserUtils.MycarToSeriesRequestCode);
			} else {
				Toast.makeText(getApplicationContext(), "请先选择品牌！",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.mycar_img_chexing:
			if (!isSubsNull) {
				Intent it3 = new Intent(getApplicationContext(),
						ModelAcitvity.class);
				it3.putExtra("MyCar", true);
				it3.putExtra("subs_id", subs_id);
				startActivityForResult(it3, UserUtils.MycarToModelRequestCode);
			} else {
				Toast.makeText(getApplicationContext(), "请先选择车系！",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case R.id.mycar_tv_back:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("jhd", "req"+requestCode+"-res"+resultCode);
		if (resultCode == 0) {
			switch (requestCode) {
			case UserUtils.MycarToBrandRequestCode:
				brand_id = data.getLongExtra("brand_id", 0);
				brand_name = data.getStringExtra("brand_name");
				Log.e("jhd", brand_name);
				pinpai.setText(brand_name);
				isBrandNull = false;
				break;
			case UserUtils.MycarToSeriesRequestCode:
				if (!isBrandNull) {
					subs_id = data.getLongExtra("subs_id", 0);
					subs_name = data.getStringExtra("subs_name");
					chexi.setText(subs_name);
					isSubsNull = false;
				} else {
					Toast.makeText(getApplicationContext(), "请先选择品牌！",
							Toast.LENGTH_SHORT).show();
				}

				break;
			case UserUtils.MycarToModelRequestCode:
				if (!isSubsNull) {
					model_sortkey = data.getStringExtra("model_sortkey");
					model_name = data.getStringExtra("model_name");
					model_img = data.getStringExtra("model_img");
					chexing.setText(model_sortkey + "款 " + model_name);
				} else {
					Toast.makeText(getApplicationContext(), "请先选择车系！",
							Toast.LENGTH_SHORT).show();
				}

				break;

			default:
				break;
			}
		}
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);

	}

	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String str = (String) msg.obj;
			switch (msg.what) {
			case 1:{
				try {
					JSONObject js = new JSONObject(str);
					boolean flag = js.getBoolean("flag");
					System.out.println("----添加 flag--------"+flag);
					if(flag){
						Toast.makeText(getApplicationContext(), "保存成功！", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "保存失败！", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

				break;
			case 2:
			{
				try {
					JSONObject js = new JSONObject(str);
					System.out.println("--------findcar -- str --"+str.toString());
					int i = js.getInt("car_id");
					if(i==0){
						
							car.setCarBrand(pinpai.getText().toString());
							car.setCarModel(chexing.getText().toString());
							car.setCarSeries(chexi.getText().toString());
					    	insertCar(car);
						    System.out.println("  insertcar car"+car.toString());
						
						
					}else{
						
							car.setCarBrand(pinpai.getText().toString());
							car.setCarModel(chexing.getText().toString());
							car.setCarSeries(chexi.getText().toString());
							updateCar(car);
							System.out.println("-----------update CAr"+ car.toString());

					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}

				break;
			case 3:{
				try {
					JSONObject js = new JSONObject(str);
					boolean flag = js.getBoolean("flag");
					System.out.println("----添加 flag--------"+flag);
					if(flag){
						Toast.makeText(getApplicationContext(), "修改成功！", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "修改失败！", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				break;
			case 4:
				Log.e("qqq", "failed");
				Toast.makeText(getApplicationContext(), "网络请求失败！！！", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
			return false;
		}
	});

	private void insertCar(Car car) {
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "7");// 按uid查询消息
		params.put("uid", car.getUid());
		params.put("brand", car.getCarBrand());
		params.put("series", car.getCarSeries());
		params.put("model", car.getCarModel());
		params.put("img", car.getCarImg());

		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = s;
				handler.sendMessage(msg);
                System.out.println("--------s-------"+s);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(4);
			}
		};
		fh.get(UserUtils.CARURL, params, callBack);
	}
	private void findCarByUid(String uid){
		FinalHttp fh=new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "6");
		params.put("uid", uid);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg=new Message();
				msg.what=2;
				msg.obj=s;
				handler.sendMessage(msg);//suc
				
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(4);//failed
			}
		};
		fh.get(UserUtils.CARURL, params, callBack);
	}
	
	private void updateCar(Car car) {
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "8");// 
		params.put("uid", car.getUid());
		params.put("brand", car.getCarBrand());
		params.put("series", car.getCarSeries());
		params.put("model", car.getCarModel());
		params.put("img", car.getCarImg());

		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 3;
				msg.obj = s;
				handler.sendMessage(msg);

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(4);
			}
		};
		fh.get(UserUtils.CARURL, params, callBack);
	}
}
