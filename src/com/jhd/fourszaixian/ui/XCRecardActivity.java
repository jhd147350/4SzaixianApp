package com.jhd.fourszaixian.ui;

import java.util.Calendar;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Record;
import com.jhd.fourszaixian.utils.TimeTranslation;
import com.jhd.fourszaixian.utils.UserUtils;

public class XCRecardActivity extends Activity implements OnClickListener {

	
   
	private EditText recard_edit;
	private TextView text_flag;
    TimeTranslation tt = new TimeTranslation();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.xcrecard);
		Intent it = getIntent();
	    
		recard_edit = (EditText) findViewById(R.id.recard_edit);
		text_flag = (TextView) findViewById(R.id.recard_type);
		text_flag.setText(R.string.weibao);

	}

	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String s=(String) msg.obj;				
				try {
					JSONObject jo=new JSONObject(s);
					boolean isScu = jo.getBoolean("isSuc");
					if(isScu){
						Toast.makeText(getApplicationContext(), "添加成功", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(getApplicationContext(), "添加失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "网络请求失败！！�?", Toast.LENGTH_SHORT).show();		
				break;


			default:
				break;
			}
		};
	};
//	@Override
	public void click(View v) {
		switch (v.getId()) {
		case R.id.xcrecard_weibao:{
			text_flag.setText(R.string.weibao);
			System.out.println(text_flag.getText().toString());
		}

			break;

		case R.id.xcrecard_guolu:
		{
			text_flag.setText(R.string.guolu);
			System.out.println(text_flag.getText().toString());
		}

			break;

		case R.id.xcrecard_qita:{
			text_flag.setText(R.string.others);
			System.out.println(text_flag.getText().toString());
		}

			break;

		case R.id.xcrecard_stoping:
		{
			text_flag.setText(R.string.tingche);
			System.out.println(text_flag.getText().toString());
		}

			break;

		case R.id.xcrecard_jiayou:
		{
			text_flag.setText(R.string.jiayou);
			System.out.println(text_flag.getText().toString());
		}
		break;
		case R.id.recard_output_sure:
		{
			Record re = new Record();
		    Calendar calendar = Calendar.getInstance();
			String uid = (String) getIntent().getSerializableExtra("uid");
			re.setUid(uid);
			double money = Double.parseDouble(recard_edit.getText().toString());
			re.setXcMoney(money);
			
			
		    String month = String.valueOf(calendar.get(Calendar.MONTH)+1);
		    String year = String.valueOf(calendar.get(Calendar.YEAR));
			re.setXcMonth(month);
			re.setXcTime(tt.CalendarToString(calendar));
			re.setXcType(text_flag.getText().toString());
			re.setXcYear(year);
			recardInsert(re);
			System.out.println("recard :"+re);
			System.out.println("month = "+month);
		}
			break;
		

		default:
			break;
		}
       
	}
	private void recardInsert(Record record) {
		FinalHttp fh = new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "3");
		params.put("uid", record.getUid());
		params.put("xctype", record.getXcType());
		String money = String.valueOf(record.getXcMoney());
		params.put("xcmoney", money);
		params.put("xctime", record.getXcTime());
		params.put("xcmonth", record.getXcMonth());
		params.put("xcyear", record.getXcYear());
		System.out.println("----"+record.getXcYear());
//		System.out.println();
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
				handler.sendEmptyMessage(2);// failed
			}
		};
		fh.get(UserUtils.RECARDURL, params, callBack);

	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.xcrecard_tv_back:
		{
			finish();
		}
			break;

		default:
			break;
		}
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		
	}
}
