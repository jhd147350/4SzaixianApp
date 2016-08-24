package com.jhd.fourszaixian.ui;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Shop;
import com.jhd.fourszaixian.fragment.MyShop01;
import com.jhd.fourszaixian.fragment.MyShop02;
import com.jhd.fourszaixian.fragment.MyShop03;
import com.jhd.fourszaixian.fragment.shopAdapter;
import com.jhd.fourszaixian.utils.UserUtils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

@SuppressLint("NewApi") 
public class CarActivity extends FragmentActivity {
	private TextView tv_brandname;
	private TextView tv_subsname;
	private TextView tv_modelname;
	private TextView tv_modelsortkey;
	private ImageView img_model_img;
	//private FrameLayout content;
	//private MyShop01 fg1 = new MyShop01();
	//private MyShop02 fg2 = new MyShop02();
	//private MyShop03 fg3 = new MyShop03();
	private FinalBitmap finalBitmap;
	private RadioGroup rg;
	
	private ListView listview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_car);
		finalBitmap = FinalBitmap.create(this);
		initView();
		Intent it = getIntent();
		String brand_name = it.getStringExtra("brand_name");
		String subs_name = it.getStringExtra("subs_name");
		String model_name = it.getStringExtra("model_name");
		String model_sortkey = it.getStringExtra("model_sortkey");
		String model_img = it.getStringExtra("model_img");
		finalBitmap.display(img_model_img, model_img);
		if (it != null) {
			tv_brandname.setText(brand_name);
			tv_subsname.setText(subs_name);
			tv_modelname.setText(model_name);
			tv_modelsortkey.setText(model_sortkey + " 款");
		}
		//1994款 1.8L
		String str=model_sortkey+"款 "+model_name;
		shopByType(brand_name, subs_name, str, "基础保养");

	}

	private void initView() {
		// TODO Auto-generated method stub
		tv_brandname = (TextView) findViewById(R.id.car_tv_brand_name);
		tv_subsname = (TextView) findViewById(R.id.car_tv_subs_name);
		tv_modelname = (TextView) findViewById(R.id.car_tv_model_name);
		tv_modelsortkey = (TextView) findViewById(R.id.car_tv_model_sortkey);
		img_model_img = (ImageView) findViewById(R.id.car_model_img);
		
		//content = (FrameLayout) findViewById(R.id.content);
		listview=(ListView) findViewById(R.id.listview);

		rg = (RadioGroup) findViewById(R.id.car_rg);
	
		rg.setVisibility(View.GONE);

	}

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.car_tv_back:
			finish();

			break;

		default:
			break;
		}

	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

	
	private void shopByType(String brand, String series, String model,
			String type) {
		FinalHttp fh = new FinalHttp();
		//1994款 1.8L

		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("brand", brand);
		params.put("series", series);
		params.put("model", model);
		params.put("type", type);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String ss) {
				// TODO Auto-generated method stub
				super.onSuccess(ss);
				ArrayList<Shop> list = new ArrayList<Shop>();
				try {

					JSONArray ja = new JSONArray(ss);
					
					System.out.println("ja " + ja.length());
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jsonObject = ja.getJSONObject(i);
						Shop s = new Shop();
						s.setId(jsonObject.getInt("shop_id"));
						s.setBrand(jsonObject.getString("shop_brand"));
						s.setSeries(jsonObject.getString("shop_series"));
						s.setModel(jsonObject.getString("shop_model"));
						s.setType(jsonObject.getString("shop_type"));
						s.setShopName(jsonObject.getString("shop_name"));
						s.setShopYuyue(jsonObject.getString("shop_yuyue"));
						s.setShop4S(jsonObject.getString("shop_4s"));
						s.setShopDistance(jsonObject.getString("shop_distance"));
						s.setShopAddress(jsonObject.getString("shop_address"));
						s.setShopImg(jsonObject.getString("shop_img"));
						list.add(s);
					}

					// System.out.println(order.toString());
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (list.size() != 0) {
					shopAdapter adapter = new shopAdapter(getApplicationContext(), list);
					listview.setAdapter(adapter);
				} else {

					
				}
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
		};
		fh.get(UserUtils.SHOPURL, params, callBack);

	}
}
