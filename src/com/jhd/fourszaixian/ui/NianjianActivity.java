package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.utils.TimeTranslation;
import com.jhd.fourszaixian.utils.UserUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class NianjianActivity extends Activity {
	private Spinner spinner_loc;
	private Spinner spinner_date;
	private EditText current_location;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_nianjian);
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		spinner_loc=(Spinner) findViewById(R.id.nianjian_spinner_loc);
		List<String> list=new ArrayList<String>();
		list.add("�ȷ�");
		list.add("����");
		//list.add("�Ϻ�");
		//list.add("ʯ��ׯ");
		//list.add("����");
		//list.add("���");
		//list.add("....");
		ArrayAdapter<String> adapter=new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list);
		spinner_loc.setAdapter(adapter);
		spinner_loc.setSelection(0,true);
		//int a=android.R.layout.simple_spinner_item;
		spinner_date=(Spinner) findViewById(R.id.nianjian_spinner_date);
		List<String> list_date=new ArrayList<String>();
		TimeTranslation t=new TimeTranslation();
		Calendar c1=Calendar.getInstance();
		//c1.
		//t.CalendarToString2(calendar);
		list_date.add("2015��12��21��");
		list_date.add("2015��12��22��");
		list_date.add("2015��12��23��");
		list_date.add("2015��12��24��");
		list_date.add("2015��12��25��");
		list_date.add("2015��12��26��");
		list_date.add("2015��12��27��");
		ArrayAdapter<String> adapter_date=new ArrayAdapter<String>(getApplicationContext(), R.layout.spinner_item, list_date);
		spinner_date.setAdapter(adapter_date);
		spinner_date.setSelection(3,true);
		
		//���õ�ǰλ��
		current_location=(EditText) findViewById(R.id.current_location);
		current_location.setText(UserUtils.CURRENT_LOACTION);
		
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.nianjian_tv_back:
			finish();
			break;
		case R.id.nianjian_kefu:
			startActivity(new Intent(getApplicationContext(), KefuActivity.class));
			break;

		default:
			break;
		}
	}


}
