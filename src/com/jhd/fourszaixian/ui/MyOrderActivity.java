package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.View;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;
//import android.app.Fragment;
import com.jhd.fourszaixian.fragment.BXorder;
import com.jhd.fourszaixian.fragment.BYorder;
import com.jhd.fourszaixian.fragment.DBorder;

public class MyOrderActivity extends FragmentActivity implements OnPageChangeListener {


	private ViewPager viewpager;
	private TextView order1_tv;
	private TextView order2_tv;
	private TextView order3_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_order);
		viewpager = (ViewPager) findViewById(R.id.order_vp);
		Intent it = getIntent();
		List<Fragment> list = new ArrayList<Fragment>();
		order1_tv = (TextView) findViewById(R.id.order1_tv);
		order2_tv = (TextView) findViewById(R.id.order2_tv);
		order3_tv = (TextView) findViewById(R.id.order3_tv);
		BYorder by = new BYorder();
		BXorder bx = new BXorder();
		DBorder db = new DBorder();
		list.add(by);
		list.add(bx);
		list.add(db);
		
		
		
		Myadapter myadapter = new Myadapter(getSupportFragmentManager(), list);
	
		viewpager.setAdapter(myadapter);
		
		viewpager.setOnPageChangeListener(this);
	}

	public void click(View view){
		switch (view.getId()) {
		case R.id.order1_tv:
			  viewpager.setCurrentItem(0);
			  setSelecte(order1_tv);
			break;
		case R.id.order2_tv:
			  viewpager.setCurrentItem(1);
			  setSelecte(order2_tv);
			break;
		case R.id.order3_tv:
			  viewpager.setCurrentItem(2);
			  setSelecte(order3_tv);
			break;

		default:
			break;
		}
	}
	//-----add-------
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.my_order_tv_back:
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
		order1_tv.setSelected(tv.getId() == R.id.order1_tv);
		order2_tv.setSelected(tv.getId() == R.id.order2_tv);
		order3_tv.setSelected(tv.getId() == R.id.order3_tv);
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
		
		order1_tv.setSelected(tv == 0);
		order2_tv.setSelected(tv == 1);
		order3_tv.setSelected(tv == 2);

	}
}
