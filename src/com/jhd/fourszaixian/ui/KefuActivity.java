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
//ʵ�ֲο�http://blog.csdn.net/pocoyoshamoo/article/details/9674385
public class KefuActivity extends Activity implements OnClickListener {
	
	
	
	private Button mBtnSend;
	private Button mBtnBack;
	private EditText mEditTextContent;
	//�������ݵ�������
	private ChatMsgViewAdapter mAdapter;
	private ListView mListView;
	//���������
	public static List<ChatMsgEntity> mDataArrays = new ArrayList<ChatMsgEntity>();
	public static boolean isFirst=true;
	
	//private String ToPhone;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);// ȥ��������
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

	//��ʼ����ͼ
	private void initView() {
		mListView = (ListView) findViewById(R.id.listview);
		mBtnBack = (Button) findViewById(R.id.btn_back);
		mBtnBack.setOnClickListener(this);
		mBtnSend = (Button) findViewById(R.id.btn_send);
		mBtnSend.setOnClickListener(this);
		mEditTextContent = (EditText) findViewById(R.id.et_sendmessage);
	/*	//---
		//ͨ������XML���������ļ�������һ��Animation����

	       LayoutAnimationController animation=AnimationUtils.loadLayoutAnimation(this, R.anim.list_anim_layout);

	       //�õ�һ��LayoutAnimationController����

	      // LayoutAnimationController lac=new LayoutAnimationController(animation);

	       //���ÿؼ���ʾ��˳��

	       animation.setOrder(LayoutAnimationController.ORDER_REVERSE);

	       //���ÿؼ���ʾ���ʱ�䣻

	       animation.setDelay(1);

	       //ΪListView����LayoutAnimationController���ԣ�

	       mListView.setLayoutAnimation(animation);
	       //mListView.startLayoutAnimation();*/
	       
	}

	//private String[] msgArray = new String[]{"  �����ˣ�\n��ʲô�ܰﵽ���ģ�\n-------------------\n������ѯ|�����ѯ|���ֳ�\n4S��ȼ�ԤԼ|���ձȼ�\nΥ�²�ѯ����|�Ⱦƴ���\nȫ��24Сʱ��Ѿ�Ԯ"};

	//private String[]dataArray = new String[]{"2015-11-29 18:00"};
	private List<String> dateArray=new ArrayList<String>();

	private List<String> msgArray=new ArrayList<String>();

	private final static int COUNT = 1;

	//��ʼ��Ҫ��ʾ������
	private void initData() {
		msgArray.add("  �����ˣ�\n��ʲô�ܰﵽ���ģ�\n-----------------------\n������ѯ|�����ѯ|���ֳ�\n4S��ȼ�ԤԼ|���ձȼ�\nΥ�²�ѯ����|�Ⱦƴ���\nȫ��24Сʱ��Ѿ�Ԯ");
		dateArray.add(getDate());
		for(int i = 0; i < msgArray.size(); i++) {
			ChatMsgEntity entity = new ChatMsgEntity();



			entity.setDate(dateArray.get(i));
			//	entity.setDate(dataArray[i]);
			if (i % 2 == 0)
			{
				entity.setName("4S���߿ͷ�");
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
			Toast.makeText(getApplicationContext(), "login please��", Toast.LENGTH_SHORT).show();
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
			//ͨ�����緢�ͳ�ȥ
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
			//��������������
			try {
				JSONObject jo=new JSONObject(contString);
				ChatMsgEntity entity = new ChatMsgEntity();
				entity.setDate(getDate());
				entity.setName("4S���߿ͷ�");
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


	//��ȡ����
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
		//����activity��ת�Ķ���
		this.overridePendingTransition(R.anim.activity_slide_in_right,R.anim.activity_slide_out_left);
	}

	//��Ϣ����
	public class ChatMsgEntity {
		private final String TAG = ChatMsgEntity.class.getSimpleName();
		//����
		private String name;
		//����
		private String date;
		//��������
		private String text;
		//�Ƿ�Ϊ�Է���������Ϣ
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
