package com.jhd.fourszaixian.ui;
import org.json.JSONException;
import org.json.JSONObject;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.utils.SharedUtils;
import com.jhd.fourszaixian.utils.UserUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.lidroid.xutils.view.annotation.event.OnClick;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.animation.BounceInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends Activity {

	@ViewInject(R.id.login_qq)
	TextView tv_login_qq;
	@ViewInject(R.id.login_weixin)
	TextView tv_login_weixin;
	@ViewInject(R.id.login_weibo)
	TextView tv_login_weibo;
	@ViewInject(R.id.login_zhifubao)
	TextView tv_login_zhifubao;

	
	@ViewInject(R.id.login_container)
	LinearLayout container;
	@ViewInject(R.id.needmiss)
	LinearLayout needmiss;
	@ViewInject(R.id.tanchu)
	LinearLayout tanchu;
	@ViewInject(R.id.login_click_up_click_down)
	ImageView login_click_up_click_down;
	private boolean isUp=false;

	private String myphonenum;
	private String mypassword;


	private EditText et_phonenum;
	private EditText et_password;
	private Button bt_login;
	private TextView bt_signup;
	private boolean isPhjonenumRight=false;
	private boolean isPasswordRight=false;

	private Handler handler=new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 1:
				String s=(String) msg.obj;
				try {
					JSONObject jo=new JSONObject(s);
					boolean isScu = jo.getBoolean("isSuc");

					if(isScu){
						Intent it = new Intent(getApplicationContext(),MainActivity.class);
						it.putExtra("isSuc", true);
						User user=User.getInstance();
						user.setPhonenum(myphonenum);
						user.setPassword(mypassword);

						SharedUtils.puthasLogin(getApplicationContext(), true);
						SharedUtils.putPhonenum(getApplicationContext(), myphonenum);
						UserUtils.setAlias(myphonenum, getApplicationContext());
						Log.e("jhd", "登录成功，设置了别名");

						setResult(1001, it);
						finish();
					}else{
						//it.putExtra("isSuc", false);
						bt_login.setEnabled(true);


					}


				} catch (JSONException e) {
					//  Auto-generated catch block
					e.printStackTrace();
				}

				//	Toast.makeText(getApplicationContext(), "suc--"+s, 100).show();
				break;
			case 2://
				Toast.makeText(getApplicationContext(), "网络连接失败", 100).show();
				bt_login.setEnabled(true);
				bt_login.setText("重新登录");
				break;

			default:
				break;
			}
		};
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		ViewUtils.inject(this);
		initView();
		//
	
		ViewTreeObserver vto = container.getViewTreeObserver();
		vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener()
		{
			public boolean onPreDraw()
			{
				int H = needmiss.getMeasuredHeight();
				FrameLayout.LayoutParams lp1=(android.widget.FrameLayout.LayoutParams) tanchu.getLayoutParams();
				lp1.bottomMargin=-H;
				return true;
			}
		});
	}

	private void initView() {
		// TODO Auto-generated method stub
		et_password=(EditText) findViewById(R.id.login_password);
		et_phonenum=(EditText) findViewById(R.id.login_phonenum);
		bt_login=(Button) findViewById(R.id.login_login);
		bt_signup=(TextView) findViewById(R.id.login_signup);

		bt_login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String phonenum=et_phonenum.getText().toString();
				String password=et_password.getText().toString();
				Login(phonenum, password);
				bt_login.setEnabled(false);
				bt_login.setText("登录中�?��?��??");

			}
		});
		bt_signup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				startActivity(new Intent(getApplicationContext(), SignupActivity.class));
			}
		});

		//
		et_phonenum.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence c, int arg1, int arg2, int arg3) {
				//  Auto-generated method stub
				//Log.e("jhd", "on"+arg0);
				if(c.length()==11)
					isPhjonenumRight=true;					
				else
					isPhjonenumRight=false;				
				if(isPhjonenumRight&&isPasswordRight)
					bt_login.setEnabled(true);
				else
					bt_login.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}

			@Override
			public void afterTextChanged(Editable arg0) {}
		});
		//
		et_password.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence c, int arg1, int arg2, int arg3) {
				//  Auto-generated method stub
				if(c.length()>=6)
					isPasswordRight=true;									
				else
					isPasswordRight=false;				
				if(isPhjonenumRight&&isPasswordRight)
					bt_login.setEnabled(true);
				else
					bt_login.setEnabled(false);
			}

			@Override
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {}

			@Override
			public void afterTextChanged(Editable arg0) {}
		});
	}
	//ʵ�ֵ�¼
	private  void Login(String phonenum,String password){
		FinalHttp fh=new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("phonenum", phonenum);
		params.put("password", password);
		myphonenum=phonenum;
		mypassword=password;
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
				Message msg=new Message();
				msg.what=1;
				msg.obj=s;
				handler.sendMessage(msg);//suc
			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);//failed
			}
		};
		fh.get(UserUtils.LOGINURL, params, callBack);
	}

	public void onClick(View v){
		switch (v.getId()) {
		case R.id.login_tv_back:
			finish();

			break;

		default:
			break;
		}
	}
	@Override
	public void finish() {
		super.finish();
		
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}
	
	
	///-------------------
	@OnClick({R.id.login_click_up_click_down,
		R.id.login_qq,R.id.login_weibo,R.id.login_weixin,R.id.login_zhifubao,})
	public void onClick2(View v){
		if(v.getId()==R.id.login_click_up_click_down){
			if(isUp){
				translationYAnimRunDown(tanchu);
				login_click_up_click_down.setImageResource(R.drawable.navibar_arrow_up);

			}else{
				translationYAnimRunUp(tanchu);
				login_click_up_click_down.setImageResource(R.drawable.navibar_arrow_down);
			}
			isUp=!isUp;
		}else if(v.getId()==R.id.login_qq||v.getId()==R.id.login_weibo
				||v.getId()==R.id.login_weixin||v.getId()==R.id.login_zhifubao){
			Toast.makeText(this, "第三方登录暂未实�?", Toast.LENGTH_SHORT).show();
		}
	}
	
	@SuppressLint("NewApi")
	public void translationYAnimRunUp(View view) 
	{  
		int h = needmiss.getHeight();    
		ObjectAnimator anim=ObjectAnimator.ofFloat(view, "translationY",0.0F , -h);               
		anim.setDuration(1000);
		anim.setInterpolator(new BounceInterpolator());    
		anim.start();  
	}
	@SuppressLint("NewApi")
	public void translationYAnimRunDown(View view)  
	{ 
		int h = needmiss.getHeight();     
		ObjectAnimator anim=ObjectAnimator.ofFloat(view, "translationY",-h , 0.0f);
		anim.setDuration(1000);
		anim.setInterpolator(new BounceInterpolator());
		anim.start(); 
	}


}
