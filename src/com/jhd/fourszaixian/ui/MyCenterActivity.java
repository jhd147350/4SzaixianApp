package com.jhd.fourszaixian.ui;

import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.ui.append.SettingActivity;
import com.jhd.fourszaixian.utils.SharedUtils;
import com.jhd.fourszaixian.utils.UserUtils;

public class MyCenterActivity extends Activity{




	//private TextView mycenter_order;
	private TextView mycenter_shop;
	private TextView mycenter_car;
	private TextView mycenter_msg;
	private TextView mycenter_quan;

	//------------------jhd-add------------------------
	private Button btn_state;
	private boolean hasLogin=false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_centre);
		 ShareSDK.initSDK(this);//��ʼ������
		Intent it = getIntent();
	//	mycenter_order = (TextView) findViewById(R.id.mycenter_order);
		mycenter_shop = (TextView) findViewById(R.id.mycenter_shop);
		mycenter_car = (TextView) findViewById(R.id.mycenter_car);
		mycenter_msg = (TextView) findViewById(R.id.mycenter_msg);
		mycenter_quan = (TextView) findViewById(R.id.mycenter_quan);

		//--jhd------------------------------------------
		btn_state=(Button) findViewById(R.id.mc_btn_status);
		if(SharedUtils.hasLogin(getApplicationContext())){
			User user=User.getInstance();
			if(user.getPhonenum()!=null){
				btn_state.setText(user.getPhonenum()+"�ٴε��ע��");
				hasLogin=true;
				ImageView touxiang=(ImageView) MyCenterActivity.this.findViewById(R.id.mc_img_bdwx);
				touxiang.setImageResource(R.drawable.person_default);
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ShareSDK.stopSDK(this);//�ͷ���Դ
	}


	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.mycenter_order:
//		{
//			Intent it = new Intent(MyCenterActivity.this, MyOrderActivity.class);
//			MyCenterActivity.this.startActivity(it);
//		}
//		break;
		case R.id.mycenter_shop:
		{
			User u = User.getInstance();
			Intent it = new Intent(MyCenterActivity.this, MyShopActivity.class);
			it.putExtra("uid", u.getPhonenum());
			MyCenterActivity.this.startActivity(it);
		}
		break;

		case R.id.mycenter_car:
		{
			if(hasLogin){
				User user = User.getInstance();
				Intent it = new Intent(MyCenterActivity.this, MyCarActivity.class);
				it.putExtra("uid",user.getPhonenum() );
				MyCenterActivity.this.startActivity(it);
				
			}else{
				Toast.makeText(getApplicationContext(), "���¼������", 0).show();
				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
						UserUtils.MycenterToLoginRequestCode);
			}
		}
		break;

		case R.id.mycenter_msg:
		{
			if(hasLogin){
				User user = User.getInstance();
				Intent it = new Intent(MyCenterActivity.this, MyMessageActivity.class);
				it.putExtra("uid",user.getPhonenum() );
				MyCenterActivity.this.startActivity(it);
				
			}else{
				Toast.makeText(getApplicationContext(), "���¼������", 0).show();
				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
						UserUtils.MycenterToLoginRequestCode);
			}
		}
		break;

		case R.id.mycenter_quan:
		{
			if(hasLogin){
				User user = User.getInstance();
				Intent it = new Intent(MyCenterActivity.this, MyQuanActivity.class);
				it.putExtra("uid",user.getPhonenum() );
				MyCenterActivity.this.startActivity(it);
				
			}else{
				Toast.makeText(getApplicationContext(), "���¼������", 0).show();
				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
						UserUtils.MycenterToLoginRequestCode);
			}
		}
		break;
		
		
		//--jhd-------------------------------------------
		case R.id.mc_btn_status:
			if(hasLogin){
				showLogout();
			}else{
				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
						UserUtils.MycenterToLoginRequestCode);
			}
			break;
		case R.id.mc_btn_back:
			finish();
			break;
		case R.id.mc_btn_share:
			showShare();
			break;
		case R.id.mycentre_kefu:
			startActivity(new Intent(getApplicationContext(), KefuActivity.class));
			break;
		case R.id.mycentre_xingchejilu:
			if(hasLogin){
				User user = User.getInstance();
				Intent it = new Intent(MyCenterActivity.this, XingCheActivity.class);
				it.putExtra("uid",user.getPhonenum() );
				MyCenterActivity.this.startActivity(it);
				
			}else{
				Toast.makeText(getApplicationContext(), "���¼������", 0).show();
				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
						UserUtils.MycenterToLoginRequestCode);
			}
			break;
		case R.id.mycentre_guanyu:
			Toast.makeText(getApplicationContext(), "���ǹ���...", Toast.LENGTH_SHORT).show();
			break;
			
		case R.id.setting:
			//����
			startActivity(new Intent(getApplicationContext(),SettingActivity.class));
		default:
			break;
		}

	}


	//---jhd--------------------------------------------
	//	private void Logout() {
	//		// TODO Auto-generated method stub
	//		
	//	}
	@Override
	public void finish() {
		super.finish();
		//����activity��ת�Ķ���
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

	//--jhd---------------------------------------------
	private void showLogout() {
		// TODO Auto-generated method stub
		Builder builder=new Builder(this);
		builder.setTitle("ע��");
		builder.setMessage("��ȷ��Ҫע��ô");
		builder.setIcon(R.drawable.ic_launcher);
		// ��һ��������ʾ�� ��ť�е�text�ı�����      �ڶ������� �Ǽ����¼�
		builder.setPositiveButton("ȷ��", new  DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				//				User user=User.getInstance();
				//				user.setPhonenum(null);
				SharedUtils.puthasLogin(getApplicationContext(), false);
				hasLogin=false;
				btn_state.setText("��δ��¼");
				ImageView touxiang=(ImageView) MyCenterActivity.this.findViewById(R.id.mc_img_bdwx);
				touxiang.setImageResource(R.drawable.touxiang);
				dialog.dismiss();				
			}
		});

		builder.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();					
			}
		});		
		builder.show();
	}
	
	//----jhd--------------------------------------------------------
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==UserUtils.MycenterToLoginRequestCode){
			if(resultCode==1001){
				User user=User.getInstance();
				if(user.getPhonenum()!=null){
					btn_state.setText(user.getPhonenum()+"�ٴε��ע��");
					hasLogin=true;
					ImageView touxiang=(ImageView) MyCenterActivity.this.findViewById(R.id.mc_img_bdwx);
					touxiang.setImageResource(R.drawable.person_default);
				}
			}
		}
	}
	///--------------
	private void showShare() {
		
		 OnekeyShare oks = new OnekeyShare();
		 //�ر�sso��Ȩ
		// oks.disableSSOWhenAuthorize(); 

		// ����ʱNotification��ͼ�������  2.5.9�Ժ�İ汾�����ô˷���
		 //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
		 // title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		 oks.setTitle("����΢�ŷ������Title");
		 // titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		// oks.setTitleUrl("http://sharesdk.cn");
		 // text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		 oks.setText("����΢�ŷ������Text.by:jhd");
		 // imagePath��ͼƬ�ı���·����Linked-In�����ƽ̨��֧�ִ˲���
//		 oks.setImagePath("/sdcard/test.jpg");//ȷ��SDcard������ڴ���ͼƬ
		 // url����΢�ţ��������Ѻ�����Ȧ����ʹ��
		 oks.setUrl("http://blog.csdn.net/qq_24505485");
		 oks.setImageUrl("https://mmbiz.qlogo.cn/mmbiz/Q3NicBwNGKkx4mr26q9XTC5QtROhxXibASvDruOgtTzXibuuI20sPSvRp0S946CGfvqZ8rZxFwdnia8M1hGbMVpc1A/0?wx_fmt=png");
		 // comment���Ҷ�������������ۣ�������������QQ�ռ�ʹ��
	//	 oks.setComment("���ǲ��������ı�");
		 // site�Ƿ�������ݵ���վ���ƣ�����QQ�ռ�ʹ��
	//	 oks.setSite(getString(R.string.app_name));
		 // siteUrl�Ƿ�������ݵ���վ��ַ������QQ�ռ�ʹ��
	//	 oks.setSiteUrl("http://sharesdk.cn");
		// https://mmbiz.qlogo.cn/mmbiz/Q3NicBwNGKkx4mr26q9XTC5QtROhxXibASvDruOgtTzXibuuI20sPSvRp0S946CGfvqZ8rZxFwdnia8M1hGbMVpc1A/0?wx_fmt=png
		// ��������GUI
		 oks.show(this);
		 }
}
