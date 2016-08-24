package com.jhd.fourszaixian.ui;



import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONStringer;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.receiver.MyReceiver;
import com.jhd.fourszaixian.receiver.MyReceiver.ReceiveMsg;
import com.jhd.fourszaixian.utils.ChatMsgViewAdapter;
import com.jhd.fourszaixian.utils.UserUtils;

import android.animation.LayoutTransition;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
//实现参考http://blog.csdn.net/pocoyoshamoo/article/details/9674385
public class KefuActivity extends Activity implements OnClickListener {
	
	
	
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	//聊天内容的适配器
	private ChatMsgViewAdapter mAdapter;
	private ListView mListView;
	//聊天的内容
	public static List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	public static boolean isFirst=true;
	
	//private String ToPhone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		setContentView(R.layout.activity_kefu);
		
		MyReceiver.setReceiveMsg(new ReceiveMsg() {

			@Override
			public void notifyKeFu(String msg) {
				// TODO Auto-generated method stub
				Log.e("jhd", "notifyKeFu");
				send(msg);
			}
		});
		initView();
		if(isFirst){
			initData();
			isFirst=false;
		}
		mAdapter = new ChatMsgViewAdapter(this, mDataArrays);
		mListView.setAdapter(mAdapter);	
		
	}

	//初始化视图
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	/*	//---
		//通过加载XML动画设置文件来创建一个Animation对象；

	       LayoutAnimationController animation=AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_layout);

	       //得到一个LayoutAnimationController对象；

	      // LayoutAnimationController lac=new LayoutAnimationController(animation);

	       //设置控件显示的顺序；

	       animation.setOrder(LayoutAnimationController.ORDER_REVERSE);

	       //设置控件显示间隔时间；

	       animation.setDelay(1);

	       //为ListView设置LayoutAnimationController属性；

	       mListView.setLayoutAnimation(animation);
	       //mListView.startLayoutAnimation();*/
	       
	}

	//private String[] msgArray = new String[]{"  您来了！\n有什么能帮到您的？\n-------------------\n故障咨询|配件咨询|二手车\n4S店比价预约|保险比价\n违章查询代办|喝酒代驾\n全国24小时免费救援"};

	//private String[]dataArray = new String[]{"2015-11-29 18:00"};
	private List<String> dateArray=new ArrayList<String>();

	private List<String> msgArray=new ArrayList<String>();

	private final static int COUNT = 1;

	//初始化要显示的数据
	private void initData() {
		msgArray.add("  您来了！\n有什么能帮到您的？\n-----------------------\n故障咨询|配件咨询|二手车\n4S店比价预约|保险比价\n违章查询代办|喝酒代驾\n全国24小时免费救援");
		dateArray.add(getDate());
		for(int i = 0; i < msgArray.size(); i++) {
			ChatMsgEntity entity = new ChatMsgEntity();



			entity.setDate(dateArray.get(i));
			//	entity.setDate(dataArray[i]);
			if (i % 2 == 0)
			{
				entity.setName("4S在线客服");
				entity.setMsgType(true);
			}else{
				entity.setName("Shamoo");
				entity.setMsgType(false);
			}

			entity.setText(msgArray.get(i));
			mDataArrays.add(entity);
		}
		
		
	}

	public void onClick(View view) {
		// TODO Auto-generated method stub
		switch(view.getId()) {
		case R.id.btn_back:
			back();
			break;
		case R.id.btn_send:
			send();
			break;
		}
	}

	private void send()
	{
		String contString = mEditTextContent.getText().toString();
		String phonenum=User.getInstance().getPhonenum();
		if(phonenum==null){
			Toast.makeText(getApplicationContext(), "login please！", Toast.LENGTH_SHORT).show();
			return;
		}
		if (contString.length() > 0)
		{
			ChatMsgEntity entity = new ChatMsgEntity();
			entity.setDate(getDate());
			entity.setName("");
			entity.setMsgType(false);
			entity.setText(contString);
			mDataArrays.add(entity);
			mAdapter.notifyDataSetChanged();
			mEditTextContent.setText("");
			mListView.setSelection(mListView.getCount() - 1);
			//mListView.addView();
			//通过网络发送出去
			JSONObject jo=new JSONObject();
			try {
				jo.put("phonenum", phonenum);
				jo.put("msg", contString);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			UserUtils.handlePublishAlias(jo.toString(), getApplicationContext());
		}
	}
	private void send(String contString)
	{
		Log.e("jhd", "send");
		//String contString = mEditTextContent.getText().toString();
		if (contString.length() > 0)
		{
			//phonenum
			//msg
			//解析这两个参数
			try {
				JSONObject jo=new JSONObject(contString);
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setName("4S在线客服");
				entity.setMsgType(true);
				entity.setText(jo.getString("msg"));
				mDataArrays.add(entity);
				mAdapter.notifyDataSetChanged();
				mEditTextContent.setText("");
				//ToPhone=jo.getString(jo.getString("phonenum"));
				mListView.setSelection(mListView.getCount() - 1);
//				TextView tv=new TextView(getApplicationContext());
//				tv.setText("asdasd");
//				mListView.addView(tv);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}


	//获取日期
	private String getDate() {
		Calendar c = Calendar.getInstance();
		String year = String.valueOf(c.get(Calendar.YEAR));
		String month = String.valueOf(c.get(Calendar.MONTH));
		String day = String.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);
		String hour = String.valueOf(c.get(Calendar.HOUR_OF_DAY));
		String mins = String.valueOf(c.get(Calendar.MINUTE));
		StringBuffer sbBuffer = new StringBuffer();
		sbBuffer.append(year + "-" + month + "-" + day + " " + hour + ":" + mins); 
		return sbBuffer.toString();
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		back();
		return true;
	}

	private void back() {
		finish();
	}
	@Override
	public void finish() {
		super.finish();
		//设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

	//消息内容
	public class ChatMsgEntity {
		private final String TAG = ChatMsgEntity.class.getSimpleName();
		//名字
		private String name;
		//日期
		private String date;
		//聊天内容
		private String text;
		//是否为对方发来的信息
		private boolean isComMeg = true;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getDate() {
			return date;
		}

		public void setDate(String date) {
			this.date = date;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public boolean getMsgType() {
			return isComMeg;
		}

		public void setMsgType(boolean isComMsg) {
			isComMeg = isComMsg;
		}

		public ChatMsgEntity() {
		}

		public ChatMsgEntity(String name, String date, String text, boolean isComMsg) {
			this.name = name;
			this.date = date;
			this.text = text;
			this.isComMeg = isComMsg;
		}
	}	
	
}
