package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Messages;
import com.jhd.fourszaixian.utils.UserUtils;

public class MyMessageActivity extends Activity implements OnClickListener {
	private ListView mymsg_lv;
	private List<Messages> list = new ArrayList<Messages>();
    String uid;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.my_message);
		Intent it = getIntent();
		mymsg_lv = (ListView) findViewById(R.id.mymessage_lv);
		uid = (String) it.getSerializableExtra("uid");
		System.out.println("---------mymessage uid-----"+uid);
		findByUid(uid);
	}

	class myAdapter extends BaseAdapter {

		List<Messages> list;
		Context context;
		LayoutInflater inflater;

		public myAdapter(Context context, List<Messages> list) {
			// TODO Auto-generated constructor stub
			this.context = context;
			this.list = list;
			inflater = LayoutInflater.from(context);
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = inflater.inflate(R.layout.messageitem, null);
				holder.msgitem_content = (TextView) convertView
						.findViewById(R.id.msgitem_content);
				holder.msgitem_time = (TextView) convertView
						.findViewById(R.id.msgitem_time);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.msgitem_content.setText(list.get(position).getContent());
			holder.msgitem_time.setText(list.get(position).getTime());
			return convertView;
		}

		class ViewHolder {
			TextView msgitem_content, msgitem_time;
		}
	}

	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.message_tv_back: {
			finish();
		}

			break;

		default:
			break;
		}
	}

	Handler handler = new Handler(new Callback() {

		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			String str = (String) msg.obj;
			switch (msg.what) {
			case 1:{
				try {
					JSONArray ja = new JSONArray(str);
					for (int i = 0; i < ja.length(); i++) {
						JSONObject js = ja.getJSONObject(i);
						Messages m = new Messages();
						m.setId(js.getInt("msg_id"));
						m.setUid(js.getString("msg_uid"));
						m.setTime(js.getString("msg_time"));
						m.setContent(js.getString("msg_content"));
						list.add(m);
						myAdapter adapter = new myAdapter(MyMessageActivity.this, list);
						mymsg_lv.setAdapter(adapter);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

				break;
			case 2:

				break;
			default:
				break;
			}
			return false;
		}
	});

	private void findByUid(String uid) {
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");// 按uid查询消息
		params.put("uid", uid);

		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 1;
				msg.obj = s;
				handler.sendMessage(msg);

			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(2);
			}
		};
		fh.get(UserUtils.MESSAGEURL, params, callBack);
	}
}
