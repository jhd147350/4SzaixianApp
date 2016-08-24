package com.jhd.fourszaixian.ui;


import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.ui.append.DaijiaActivity;
import com.jhd.fourszaixian.ui.append.MyOrderActivity;
import com.jhd.fourszaixian.ui.append.NewsActivity;
import com.jhd.fourszaixian.ui.append.NianJianDaiBanActivity;
import com.jhd.fourszaixian.ui.append.SelfDriveTourActivity;
import com.jhd.fourszaixian.utils.CustomLayoutAnimationController;
import com.jhd.fourszaixian.utils.SharedUtils;
import com.jhd.fourszaixian.utils.UserUtils;
import com.jhd.fourszaixian.utils.CustomLayoutAnimationController.Callback;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable; 
import android.support.v4.view.PagerAdapter; 
import android.support.v4.view.ViewPager; 
import android.support.v4.view.ViewPager.OnPageChangeListener; 
import android.util.Log;
import android.view.MotionEvent; 
import android.view.View; 
import android.view.View.OnTouchListener; 
import android.view.ViewGroup; 
import android.view.ViewGroup.LayoutParams; 
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("HandlerLeak") 
public class MainActivity extends Activity { 
	//-΢�Žӿ�
	//private IWXAPI api;
	//������viewpager�й�
	private ImageView[] imageViews = null; 
	private ImageView imageView = null; 
	private ViewPager advPager = null; 
	private AtomicInteger what = new AtomicInteger(0); 
	private boolean isContinue = true; 

	private TextView tv_state;
	//private TextView tv_xianxingweihao;
	
	private RelativeLayout ll_main;
	
	private long exitTime=0;//���ΰ������˳�

	@Override 
	public void onCreate(Bundle savedInstanceState) { 
		super.onCreate(savedInstanceState); 
		setContentView(R.layout.activity_main); 
		tv_state=(TextView) findViewById(R.id.main_user_state);
		//TODO �����ȷ���ʱû�����ݣ�����Ҫ��
		//tv_xianxingweihao=(TextView) findViewById(R.id.main_xianxingweihao);
		//tv_xianxingweihao.setText(new Weihaoxianxing().getXianXingWeiHao());//�õ����е�β��
		//
		initViewPager(); 
		//ע��Ӧ�õ�΢��
//		regToWx();
		if(SharedUtils.hasLogin(getApplicationContext())){
			String myphonenum=SharedUtils.getPhonenum(getApplicationContext());
			tv_state.setText(myphonenum);
			User user=User.getInstance();
			user.setPhonenum(myphonenum);
			Log.e("jhd", "user:"+user.getPhonenum()+"��½,���������˱���");
			UserUtils.setAlias(myphonenum, getApplicationContext());
		}
		in();
	} 

	//���������еĵ���¼���������
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.main_jiayouzhan:
			Intent it1=new Intent(this, BaiduMapActivity.class);
			it1.putExtra("target", "����վ");
			startActivity(it1);
			break;
		case R.id.main_xichedian:
			//	Log.e("jhd", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			Intent it2=new Intent(this, BaiduMapActivity.class);
			it2.putExtra("target", "ϴ����");
			startActivity(it2);
			break;
		case R.id.main_tingchechang:
			//Log.e("jhd", "xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx");
			Intent it3=new Intent(this, BaiduMapActivity.class);
			it3.putExtra("target", "ͣ����");
			startActivity(it3);
			break;
		case R.id.main_weizhangchaxun:
			startActivity(new Intent(getApplicationContext(), WeizhangActivity.class));
			break;
		case R.id.main_nianjiandaiban:
			//ʹ��webview����
			startActivity(new Intent(getApplicationContext(), NianJianDaiBanActivity.class));
			break;
		case R.id.main_38yuandaijia:
			startActivity(new Intent(this, DaijiaActivity.class));
			//Toast.makeText(this, "���ݷ�����δ�����������ڴ���", Toast.LENGTH_SHORT).show();
			break;
		case R.id.main_baoyangbijia:
			startActivity(new Intent(this, BrandActivity.class));
			break;
		case R.id.main_chexianbijia:
			startActivity(new Intent(this, ChexianbijiaActivity.class));
			break;
		case R.id.main_quangoumianfeijiuyuan:
			Intent it5=new Intent(this, BaiduMapActivity.class);
			it5.putExtra("target", "����վ");
			startActivity(it5);
			break;
		case R.id.main_wodezhongxin:
			startActivity(new Intent(getApplicationContext(), MyCenterActivity.class));
			break;
		case R.id.main_zijiayou:
			//��һ��ʵ��
			//startActivity(new Intent(getApplicationContext(), ZJYActivity.class));
			startActivity(new Intent(getApplicationContext(), SelfDriveTourActivity.class));
			break;
		case R.id.main_zixun:
			//��һ��ʵ��
			//startActivity(new Intent(getApplicationContext(), MyInfomationActivity.class));
			startActivity(new Intent(getApplicationContext(), NewsActivity.class));
			break;
		case R.id.main_user_state:

			String state = tv_state.getText().toString();
			if(state.equals("δ��¼")){
				startActivityForResult(new Intent(this,LoginActivity.class),1);
			}
			break;
		case R.id.main_wodedingdan:
			String states = tv_state.getText().toString();
			if(states.equals("��¼�ɹ�")){
			/*	User user = User.getInstance();
				Intent it = new Intent(MainActivity.this, MyOrderActivity.class);
				it.putExtra("uid",user.getPhonenum() );
				MainActivity.this.startActivity(it);*/
				startActivity(new Intent(this,MyOrderActivity.class));
				
			}else{
				Toast.makeText(getApplicationContext(), "���ȵ�¼������", Toast.LENGTH_SHORT).show();
				startActivityForResult(new Intent(this,LoginActivity.class),1);
			}
//		{
//			if(hasLogin){
//				User user = User.getInstance();
//				Intent it = new Intent(MainActivity.this, MyOrderActivity.class);
//				it.putExtra("uid",user.getPhonenum() );
//				MainActivity.this.startActivity(it);
//				
//			}else{
//				Toast.makeText(getApplicationContext(), "���¼������", 0).show();
//				startActivityForResult(new Intent(getApplicationContext(), LoginActivity.class),
//						UserUtils.MycenterToLoginRequestCode);
//			}
//		}
			break;
		case R.id.main_xingchejilu:
			//�г���¼
//			startActivity(new Intent(getApplicationContext(), XingCheActivity.class));
			String statess = tv_state.getText().toString();
			if(statess.equals("��¼�ɹ�")){
				User user = User.getInstance();
				Intent it = new Intent(MainActivity.this, XingCheActivity.class);
				it.putExtra("uid",user.getPhonenum() );
				MainActivity.this.startActivity(it);
			}else{
				Toast.makeText(getApplicationContext(), "���ȵ�¼������", Toast.LENGTH_SHORT).show();
				startActivityForResult(new Intent(this,LoginActivity.class),1);
			}
			break;

		default:
			break;
		}
	}
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub

		super.onActivityResult(requestCode, resultCode, data);

		switch (resultCode) {
		case 1001://��ʾ�ӵ�¼���淵��
			boolean b = data.getBooleanExtra("isSuc", false);
			if(b){
				Toast.makeText(getApplicationContext(), "Result", 100).show();
				tv_state.setText("��¼�ɹ�");
			}


			break;

		default:
			break;
		}
	}


	private void initViewPager() { 
		advPager = (ViewPager) findViewById(R.id.adv_pager); 
		ViewGroup group = (ViewGroup) findViewById(R.id.viewGroup); 

		//      �����ŵ������Ź�汳�� 
		List<View> advPics = new ArrayList<View>(); 

		ImageView img1 = new ImageView(this); 
		img1.setBackgroundResource(R.drawable.ad_mianfeijiuyuan_1);
		img1.setAdjustViewBounds(true);
		advPics.add(img1); 

		ImageView img2 = new ImageView(this); 
		img2.setBackgroundResource(R.drawable.ad2); 
		img2.setAdjustViewBounds(true);
		advPics.add(img2); 

		ImageView img3 = new ImageView(this); 
		img3.setBackgroundResource(R.drawable.ad_mianfeijiuyuan_1); 
		img3.setAdjustViewBounds(true);
		advPics.add(img3); 

		ImageView img4 = new ImageView(this); 
		img4.setBackgroundResource(R.drawable.ad1); 
		img4.setAdjustViewBounds(true);
		advPics.add(img4); 

		//      ��imageviews������� 
		imageViews = new ImageView[advPics.size()]; 
		//Сͼ�� 
		for (int i = 0; i < advPics.size(); i++) { 
			imageView = new ImageView(this); 
			imageView.setLayoutParams(new LayoutParams(20, 20)); 
			imageView.setPadding(3, 3, 3, 3); 
			imageViews[i] = imageView; 
			if (i == 0) { 
				imageViews[i] 
						.setImageResource(R.drawable.home_serve_dot_pressed); 
			} else { 
				imageViews[i] 
						.setImageResource(R.drawable.home_serve_dot); 
			} 
			group.addView(imageViews[i]); 
		} 

		advPager.setAdapter(new AdvAdapter(advPics)); 
		advPager.setOnPageChangeListener(new GuidePageChangeListener()); 
		advPager.setOnTouchListener(new OnTouchListener() { 

			@Override 
			public boolean onTouch(View v, MotionEvent event) { 
				switch (event.getAction()) { 
				case MotionEvent.ACTION_DOWN: 
				case MotionEvent.ACTION_MOVE: 
					isContinue = false; 
					break; 
				case MotionEvent.ACTION_UP: 
					isContinue = true; 
					break; 
				default: 
					isContinue = true; 
					break; 
				} 
				return false; 
			} 
		}); 
		new Thread(new Runnable() { 

			@Override 
			public void run() { 
				while (true) { 
					if (isContinue) { 
						viewHandler.sendEmptyMessage(what.get()); 
						whatOption(); 
					} 
				} 
			} 

		}).start(); 
	} 


	private void whatOption() { 
		what.incrementAndGet(); 
		if (what.get() > imageViews.length - 1) { 
			what.getAndAdd(-4); 
		} 
		try { 
			Thread.sleep(1500); //1.5�����һ��
		} catch (InterruptedException e) { 

		} 
	} 

	private final Handler viewHandler = new Handler() { 

		@Override 
		public void handleMessage(Message msg) { 
			advPager.setCurrentItem(msg.what); 
			super.handleMessage(msg); 
		} 

	}; 

	private final class GuidePageChangeListener implements OnPageChangeListener { 

		@Override 
		public void onPageScrollStateChanged(int arg0) { 

		} 

		@Override 
		public void onPageScrolled(int arg0, float arg1, int arg2) { 

		} 

		@Override 
		public void onPageSelected(int arg0) { 
			what.getAndSet(arg0); 
			for (int i = 0; i < imageViews.length; i++) { 
				imageViews[arg0] 
						.setImageResource(R.drawable.home_serve_dot_pressed); //banner_dian_focus
						if (arg0 != i) { 
							imageViews[i] 
									.setImageResource(R.drawable.home_serve_dot); //banner_dian_blur
						} 
			} 

		} 

	} 

	private final class AdvAdapter extends PagerAdapter { 
		private List<View> views = null; 

		public AdvAdapter(List<View> views) { 
			this.views = views; 
		} 

		@Override 
		public void destroyItem(View arg0, int arg1, Object arg2) { 
			((ViewPager) arg0).removeView(views.get(arg1)); 
		} 

		@Override 
		public void finishUpdate(View arg0) { 

		} 

		@Override 
		public int getCount() { 
			return views.size(); 
		} 

		@Override 
		public Object instantiateItem(View arg0, int arg1) { 
			((ViewPager) arg0).addView(views.get(arg1), 0); 
			return views.get(arg1); 
		} 

		@Override 
		public boolean isViewFromObject(View arg0, Object arg1) { 
			return arg0 == arg1; 
		} 

		@Override 
		public void restoreState(Parcelable arg0, ClassLoader arg1) { 

		} 

		@Override 
		public Parcelable saveState() { 
			return null; 
		} 

		@Override 
		public void startUpdate(View arg0) { 

		} 
	}
	
	
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(!SharedUtils.hasLogin(getApplicationContext())){
			tv_state.setText("δ��¼");
		}
		else{
			tv_state.setText("��¼�ɹ�");
		}	
		
	}
	
	@Override
	public void onBackPressed() {    
	
		
			exit();  ///�˳�Ӧ��
			
	}
	
	public void exit() {   //�˳�Ӧ��
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "�ٰ�һ���˳�����",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
            //System.exit(0);
        }
    }
	
//	private void regToWx(){
//		String APP_ID="wxeeab7b670aae59dc";
//		//�ڶ���������appid��ͨ�������õ�ʵ��
//		api=WXAPIFactory.createWXAPI(this, APP_ID, true);
//		api.registerApp(APP_ID);//��Ӧ��ע�ᵽ΢��
//	}
	private void in(){
		ll_main=(RelativeLayout) findViewById(R.id.main_main);
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.fangda);//�Ŵ󶯻�
		

		//�õ�һ��LayoutAnimationController����
		CustomLayoutAnimationController controller = new CustomLayoutAnimationController(animation);

		//���ÿؼ���ʾ��˳��
		controller.setOrder(CustomLayoutAnimationController.ORDER_CUSTOM);
		controller.setOnIndexListener(new Callback() {

			@Override
			public int onIndex(CustomLayoutAnimationController controller, int count,
					int index) {
				// TODO Auto-generated method stub
				//0,1,2,3
				//4,5,6,7
				//---��Ӧ----
				//0,1,2,3
				//1,2,3,4
				Log.e("jhd", "count:"+count+"  index:"+index);
				int colCount=6;//Ĭ��Ϊ6��

				return  index%colCount+(int)Math.ceil(index/colCount);
			}
		});

		//���ÿؼ���ʾ���ʱ�䣻
		controller.setDelay(0.16f);


		//ΪListView����LayoutAnimationController���ԣ�
		ll_main.setLayoutAnimation(controller);
		
		ll_main.startLayoutAnimation();
	}
	
	

}