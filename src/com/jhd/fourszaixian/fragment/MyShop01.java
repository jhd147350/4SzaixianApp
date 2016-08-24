package com.jhd.fourszaixian.fragment;

import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Car;
import com.jhd.fourszaixian.entity.Order;
import com.jhd.fourszaixian.entity.Shop;
import com.jhd.fourszaixian.ui.MyCarActivity;
import com.jhd.fourszaixian.ui.MyShopActivity;
import com.jhd.fourszaixian.utils.UserUtils;

public class MyShop01 extends Fragment {
	private ListView shop01_lv;

	private List<Shop> list;

	private TextView shop_tv;
	String type = "基础保养";

	private TextView head_brand, head_series, head_model, head_time;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.myshop01, null);
		shop01_lv = (ListView) view.findViewById(R.id.shop01_listview);
		shop_tv = (TextView) view.findViewById(R.id.shop1_tv);
		head_brand = (TextView) view.findViewById(R.id.shop01_head)
				.findViewById(R.id.shop_brand);
		head_series = (TextView) view.findViewById(R.id.shop01_head)
				.findViewById(R.id.shop_series);
		head_model = (TextView) view.findViewById(R.id.shop01_head)
				.findViewById(R.id.shop_models);
		head_time = (TextView) view.findViewById(R.id.shop01_head)
				.findViewById(R.id.shop_time);
		String uid = (String) getActivity().getIntent().getSerializableExtra(
				"uid");
		System.out.println("----------shop01    uid-------" + uid);

		findCarByUid(uid);

		return view;
	}

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String sr = (String) msg.obj;
			System.out.println("sr   " + sr);
			switch (msg.what) {
			case 1: {
				try {

					JSONArray ja = new JSONArray(sr);
					list = new ArrayList<Shop>();
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
					shopAdapter adapter = new shopAdapter(getActivity(), list);
					shop01_lv.setAdapter(adapter);
				} else {

					shop_tv.setText("服务提供请等待");
				}

			}
				break;
			case 2: {
				try {
					JSONObject js = new JSONObject(sr);
					Car c = new Car();
					c.setUid(js.getString("car_uid"));
					c.setCarBrand(js.getString("car_brand"));
					c.setCarSeries(js.getString("car_series"));
					c.setCarModel(js.getString("car_model"));
					// c.setCarImg(js.getString("car_img"));
					System.out.println("----------car_uid---"
							+ js.getString("car_uid"));

					head_brand.setText(c.getCarBrand().toString());
					head_model.setText(c.getCarModel().toString());
					head_series.setText(c.getCarSeries().toString());
					shopByType(c.getCarBrand(), c.getCarSeries(),
							c.getCarModel(), type);

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
				break;
			case 3:
				Log.e("qqq", "failed");
				Toast.makeText(getActivity(), "网络请求失败！！！", Toast.LENGTH_SHORT)
						.show();
				break;

			default:
				break;
			}
		};

	};

	private void shopByType(String brand, String series, String model,
			String type) {
		FinalHttp fh = new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("brand", brand);
		params.put("series", series);
		params.put("model", model);
		params.put("type", type);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = s;
				handler.sendMessage(msg);// suc
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(3);// failed
			}
		};
		fh.get(UserUtils.SHOPURL, params, callBack);

	}

	private void findCarByUid(String uid) {
		FinalHttp fh = new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "6");
		params.put("uid", uid);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 2;
				msg.obj = s;
				handler.sendMessage(msg);// suc
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(3);// failed
			}
		};
		fh.get(UserUtils.CARURL, params, callBack);
	}

}
