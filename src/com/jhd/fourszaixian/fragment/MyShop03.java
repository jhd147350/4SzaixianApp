package com.jhd.fourszaixian.fragment;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.ui.BaiduMapActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MyShop03 extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		System.out.println("----------------½øÈëshop03------------");
		View view = inflater.inflate(R.layout.activity_baidu_map, null);
//		Intent it2=new Intent(getActivity(),BaiduMapActivity.class);
//		startActivity(it2);
		return view;
	}
}
