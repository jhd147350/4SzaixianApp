package com.jhd.fourszaixian.ui;

import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.utils.UserUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends Activity {

	Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String s=(String) msg.obj;				
				try {
					JSONObject jo=new JSONObject(s);
					boolean isScu = jo.getBoolean("isSuc");
					if(isScu){
						Toast.makeText(getApplicationContext(), "注册成功！", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						Toast.makeText(getApplicationContext(), "该手机号已注册", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}			
				break;
			case 2:
				Toast.makeText(getApplicationContext(), "注册访问超时请确认网络是否通畅！", Toast.LENGTH_SHORT).show();		
				break;


			default:
				break;
			}
		};
	};
	private EditText et_phonenum;
	private EditText et_password;
	private EditText et_confirmpassword;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_signup);
		initView();
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_phonenum=(EditText) findViewById(R.id.signup_phonenum);

		et_password=(EditText) findViewById(R.id.signup_password);
		et_confirmpassword=(EditText) findViewById(R.id.signup_confirmpassword);

	}


	public void onClick(View v){
		switch (v.getId()) {
		case R.id.signup_tv_back:
		case R.id.signup_cancel:
			finish();

			break;
		case R.id.signup_signup:
			String phonenum=et_phonenum.getText().toString();
			String password=et_password.getText().toString();
			String confirmpassword=et_confirmpassword.getText().toString();
			if(phonenum.length()==11&&password.length()>=6&&confirmpassword.equals(password)){
				Signup(phonenum,password);
				Log.e("jhd", "signup");
			}
			else{
				Toast.makeText(getApplicationContext(), "请输入符合要求的数据", Toast.LENGTH_SHORT).show();
			}


			break;

		default:
			break;
		}
	}


	private void Signup(String phonenum, String password) {
		// TODO Auto-generated method stub

		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "2");//2表示注册操作
		params.put("phonenum", phonenum);
		params.put("password", password);
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg=new Message();
				msg.what=1;
				msg.obj=s;
				handler.sendMessage(msg);//suc
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);//failed
			}
		};
		fh.get(UserUtils.LOGINURL, params, callBack);
	}


	@Override
	public void finish() {
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}


}
