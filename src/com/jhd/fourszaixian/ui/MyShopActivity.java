package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.LinearGradient;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;

import com.jhd.fourszaixian.fragment.MyShop01;
import com.jhd.fourszaixian.fragment.MyShop02;
import com.jhd.fourszaixian.fragment.MyShop03;

@SuppressLint("NewApi") 
public class MyShopActivity extends FragmentActivity implements OnPageChangeListener {


	private ViewPager viewpager;
	private TextView myshop01_tv;
	private TextView myshop02_tv;
	private TextView myshop03_tv;
	
	//private LinearLayout  root;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_shop);
		/*root=(LinearLayout) findViewById(R.id.root);
		root.setVisibility(View.GONE);*/
		viewpager = (ViewPager) findViewById(R.id.myshop_vp);
		Intent it = getIntent();
		List<Fragment> list = new ArrayList<Fragment>();
		myshop01_tv = (TextView) findViewById(R.id.shop_01);
		myshop02_tv = (TextView) findViewById(R.id.shop_02);
		myshop03_tv = (TextView) findViewById(R.id.shop_03);
		MyShop01 shop1 = new MyShop01();
		//MyShop02 shop2 = new MyShop02();
		//MyShop03 shop3 = new MyShop03();
		list.add(shop1);
		//list.add(shop2);
		//list.add(shop3);
		
		
		
		Myadapter myadapter = new Myadapter(getSupportFragmentManager(), list);
	
		viewpager.setAdapter(myadapter);
		
		viewpager.setOnPageChangeListener(this);
	}

	public void click(View view){
		switch (view.getId()) {
		case R.id.shop_01:
			  viewpager.setCurrentItem(0);
			  setSelecte(myshop01_tv);
			break;
		case R.id.shop_02:
			  viewpager.setCurrentItem(1);
			  setSelecte(myshop02_tv);
			break;
		case R.id.shop_03:
			  viewpager.setCurrentItem(2);
			  setSelecte(myshop03_tv);
			break;

		default:
			break;
		}
	}
	//-----add-------
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.my_shop_tv_back:
			finish();
			break;
		default:
			break;
		}
	}
	@Override
	public void finish() {
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

	
	
	
	///------有问题，，，你这设置的一点击全部都是选中状态
//	public void setSelecte(TextView tv){
//		order1_tv.setSelected(order1_tv.getId() == R.id.order1_tv);
//		order2_tv.setSelected(order2_tv.getId() == R.id.order2_tv);
//		order3_tv.setSelected(order3_tv.getId() == R.id.order3_tv);
//	}
	

	//-----------------add---------
	private void setSelecte(TextView tv){
		myshop01_tv.setSelected(tv.getId() == R.id.shop_01);
		myshop02_tv.setSelected(tv.getId() == R.id.shop_02);
		myshop03_tv.setSelected(tv.getId() == R.id.shop_03);
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int arg0) {
		// TODO Auto-generated method stub
		
		setSelect(arg0);
		
	}

	class Myadapter extends FragmentPagerAdapter{

		List<Fragment> list;
		public Myadapter(FragmentManager fm,List<Fragment> list) {
			super(fm);
			this.list = list;
		}

		@Override
		public Fragment getItem(int arg0) {
			// TODO Auto-generated method stub
			return list.get(arg0);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		
	}
	private void setSelect(int tv) {
		
		myshop01_tv.setSelected(tv == 0);
		myshop02_tv.setSelected(tv == 1);
		myshop03_tv.setSelected(tv == 2);

	}
}
