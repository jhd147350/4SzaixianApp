package com.jhd.fourszaixian.ui;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Distances;
import com.jhd.fourszaixian.entity.Record;
import com.jhd.fourszaixian.entity.User;
import com.jhd.fourszaixian.utils.TimeTranslation;
import com.jhd.fourszaixian.utils.UserUtils;

public class XingCheActivity extends Activity {

	private TextView yxs;
	private TextView baoyang;
	private ListView listview;
	private List<Record> list;
	String uid;
	private TimeTranslation tt = new TimeTranslation();
	private TextView xc_month;
	String xsStr = "行驶";
	String byStr = "保养";
	private TextView xingche_money;

	// Calendar c = Calendar.getInstance();
	// String month = String.valueOf(c.get(Calendar.MONTH) + 1);
	// String year = String.valueOf(c.get(Calendar.YEAR));
	private String xsTemp;// 保存对话框设置后的数�?
	private String byTemp;

	List<Distances> disByList;
	List<Distances> disXsList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xingche);
		yxs = (TextView) findViewById(R.id.xingche_yixingshi);
		baoyang = (TextView) findViewById(R.id.xingche_baoyang);
		list = new ArrayList<Record>();

		listview = (ListView) findViewById(R.id.xingche_lv);

		xc_month = (TextView) findViewById(R.id.xingche_month);
		xingche_money = (TextView) findViewById(R.id.xingche_money);

		Calendar ca = Calendar.getInstance();
		System.out.println(ca.toString());
		int monthInt = ca.get(Calendar.MONTH) + 1;
		String str = String.valueOf(monthInt);
		xc_month.setText(str);
		uid = (String) getIntent().getSerializableExtra("uid");
		System.out.println("------------行车记录 �?�? uid " + uid);

		// -------初始化就去查询行驶记�?
		disFindByType(uid);
		//recardByMonth(uid, str);

	}
	
	protected void onResume() {
		super.onResume();
		Calendar ca = Calendar.getInstance();
		System.out.println("onResume"+ca.toString());
		int monthInt = ca.get(Calendar.MONTH) + 1;
		String str = String.valueOf(monthInt);
		if(str.equals(xc_month.getText().toString())){
			recardByMonth(uid, str);
		}
		
	};

	Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			String sr = (String) msg.obj;
			// System.out.println("sr   "+sr);
			switch (msg.what) {
			case 1: {
				try {

					JSONArray ja = new JSONArray(sr);
					list = new ArrayList<Record>();
					System.out.println("ja " + ja.length());
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jsonObject = ja.getJSONObject(i);
						Record r = new Record();
						r.setId(jsonObject.getInt("recard_id"));
						r.setUid(jsonObject.getString("recard_uid"));
						r.setXcType(jsonObject.getString("recard_xctype"));
						r.setXcMonth(jsonObject.getString("recard_xcmonth"));
						// String time = jsonObject.getString("recard_xctime");
						r.setXcTime(jsonObject.getString("recard_xctime"));
						r.setXcMoney(jsonObject.getDouble("recard_xcmoney"));
						r.setXcYear(jsonObject.getString("recard_xcyear"));
						list.add(r);
						System.out.println("list " + list.toString());
					}

					listAdapter adapter = new listAdapter(
							getApplicationContext(), list);
					listview.setAdapter(adapter);
					listview.setSelection(list.size()-1);
					xingche_money.setText(sumMoney(list));

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
				break;
			case 2: {// 更新行驶保养公里数成�?
				String s = (String) msg.obj;
				try {
					JSONObject jo = new JSONObject(s);
					boolean isScu = jo.getBoolean("isSuc");
					String typeTemp=jo.getString("type");
					if (isScu) {
						if(typeTemp.equals("保养")){
							baoyang.setText(byTemp);
						}else{
							yxs.setText(xsTemp);
						}
						Toast.makeText(getApplicationContext(), "更新成功",
								Toast.LENGTH_SHORT).show();
					} else {
						Toast.makeText(getApplicationContext(), "更新失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
				break;
			case 3:
				Log.e("qqq", "failed");
				Toast.makeText(getApplicationContext(), "网络请求失败！！�?",
						Toast.LENGTH_SHORT).show();
				break;
			case 4: {
				//查询到结果显示到界面
				try {
					JSONArray ja = new JSONArray(sr);
					disByList = new ArrayList<Distances>();
					disXsList = new ArrayList<Distances>();
					System.out.println("ja " + ja.length());
					for (int i = 0; i < ja.length(); i++) {
						JSONObject jsonObject = ja.getJSONObject(i);
						Distances dis = new Distances();
						dis.setId(jsonObject.getInt("distance_id"));
						dis.setUid(jsonObject.getString("distance_uid"));
						dis.setType(jsonObject.getString("distance_type"));
						dis.setDiatancess(jsonObject.getDouble("distance_dis"));
						if (dis.getType().equals(byStr)) {
							disByList.add(dis);
						} else {
							disXsList.add(dis);
						}

					}
					baoyang.setText(sumDistance(disByList));
					yxs.setText(sumDistance(disXsList));

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
				break;

			default:
				break;
			}
		};

	};

	private void disFindByType(String uid) {
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "1");
		params.put("uid", uid);

		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 4;
				msg.obj = s;
				handler.sendMessage(msg);// suc
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(5);// failed
			}
		};
		fh.get(UserUtils.DISTANCEURL, params, callBack);

	}

	private String sumDistance(List<Distances> dlist) {
		String s = "0";
		double d = 0;
		for (Distances dis : dlist) {
			d += dis.getDiatancess();
		}
		s = String.valueOf(d);
		return s;
	}

	@Override
	public void finish() {
		super.finish();
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);
	}

	class listAdapter extends BaseAdapter {

		Context context;
		List<Record> list;
		LayoutInflater inflater;

		public listAdapter(Context context, List<Record> list) {
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
		public View getView(int position, View view, ViewGroup parent) {
			ViewHolder holder = null;
			if (view == null) {
				holder = new ViewHolder();
				view = inflater.inflate(R.layout.xingcheitem, null);
				holder.type = (TextView) view.findViewById(R.id.xcitem_type);
				holder.money = (TextView) view.findViewById(R.id.xcitem_money);
				holder.time = (TextView) view.findViewById(R.id.xcitem_time);
				view.setTag(holder);
			} else {
				holder = (ViewHolder) view.getTag();
			}
			holder.time.setText(list.get(position).getXcTime());
			holder.money.setText(list.get(position).getXcMoney() + "");
			holder.type.setText(list.get(position).getXcType());

			return view;
		}
	}

	class ViewHolder {
		TextView type, money, time;
	}

	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {

		case R.id.xingche_yixingshi:
			showMyDialog("亲，请输入您的�?�行驶公里数", ((TextView) v).getText().toString(),
					true);
			break;
		case R.id.xingche_baoyang:
			showMyDialog("亲，请输入您的上次保养公里数", ((TextView) v).getText().toString(),
					false);
			break;

		case R.id.xingche_month: {

			String month = xc_month.getText().toString();
			recardByMonth(uid, month);
			System.out.println("month");

		}
			break;
		case R.id.xingche_month_left: {
			String month_now = xc_month.getText().toString();
			int m = Integer.parseInt(month_now);
			String month = "1";
			if (m > 1) {
				month = String.valueOf(m - 1);

			}
			xc_month.setText(month);
			recardByMonth(uid, month);

		}
			break;
		case R.id.xingche_month_right: {
			String month_now = xc_month.getText().toString();

			int m = Integer.parseInt(month_now);
			String month = "12";
			if (m < 12) {
				month = String.valueOf(m + 1);

			}
			xc_month.setText(month);
			recardByMonth(uid, month);

		}
			break;
		case R.id.xingche_report:

		{
			User user = User.getInstance();
			Intent it = new Intent(XingCheActivity.this, XCReportActivity.class);
			it.putExtra("uid", user.getPhonenum());
			XingCheActivity.this.startActivity(it);
		}
			break;
		case R.id.xingche_recard:

		{
			User user = User.getInstance();
			Intent it = new Intent(XingCheActivity.this, XCRecardActivity.class);
			it.putExtra("uid", user.getPhonenum());
			XingCheActivity.this.startActivity(it);
		}
			break;
		// ------------add-------------------
		case R.id.xingche_tv_back:
			finish();
			break;
		/*
		 * case R.id.xingche_cancel:{ //取消 yxs.setText(null);
		 * baoyang.setText(null); }
		 */
		/*
		 * case R.id.xingche_sure:{ //确定
		 * 
		 * String xsreport = yxs.getText().toString(); String byreport =
		 * baoyang.getText().toString();
		 * 
		 * double xsDouble = Double.parseDouble(xsreport); double byDouble =
		 * Double.parseDouble(byreport);
		 * 
		 * Distances dis = new Distances(); dis.setUid(uid);
		 * System.out.println("---------行车记录 确认添加 uid--------"+uid);
		 * dis.setType(xsStr); dis.setDiatancess(xsDouble);
		 * System.out.println("xsDouble : "+xsDouble); distanceInsert(dis);
		 * 
		 * 
		 * Distances dis1 = new Distances(); dis1.setUid(uid);
		 * dis1.setType(byStr); dis1.setDiatancess(byDouble);
		 * System.out.println("byDouble : "+byDouble); distanceInsert(dis1);
		 * 
		 * 
		 * } break;
		 */
		default:
			break;
		}
	}

	private void showMyDialog(String title, String data, final boolean isXingshi) {
		// TODO Auto-generated method stub
		AlertDialog.Builder dialog = new Builder(this);
		dialog.setTitle(title);
		final EditText et = new EditText(this);
		et.setText(data);
		dialog.setView(et);
		dialog.setNegativeButton("取消", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub

			}
		});
		dialog.setPositiveButton("完成", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

				double distance = Double.parseDouble(et.getText().toString());
				Distances dis = new Distances();
				dis.setUid(uid);
				System.out.println("---------行车记录 确认添加或更�? uid:" + uid);
				dis.setDiatancess(distance);
				if (isXingshi) {
					dis.setType(xsStr);
					xsTemp=et.getText().toString();
				} else {
					dis.setType(byStr);
					byTemp=et.getText().toString();
				}
				distanceInsert(dis);
			}
		});
		dialog.show();
	}

	private void recardByMonth(String uid, String month) {
		FinalHttp fh = new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "2");
		params.put("uid", uid);
		params.put("month", month);
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
				handler.sendEmptyMessage(3);// failed
			}
		};
		fh.get(UserUtils.RECARDURL, params, callBack);

	}

	private void distanceInsert(Distances dis) {
		FinalHttp fh = new FinalHttp();

		AjaxParams params = new AjaxParams();
		params.put("action", "2");
		params.put("uid", dis.getUid());
		params.put("type", dis.getType());
		String diss = String.valueOf(dis.getDiatancess());
		params.put("distancess", diss);

		// System.out.println();
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				// TODO Auto-generated method stub
				super.onSuccess(s);
				Message msg = new Message();
				msg.what = 2;
				msg.obj = s;
				handler.sendMessage(msg);// suc
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(3);// failed
			}
		};
		fh.get(UserUtils.DISTANCEURL, params, callBack);

	}

	private String sumMoney(List<Record> rlist) {
		String s = "0";
		double f = 0;
		for (Record r : rlist) {

			f += r.getXcMoney();

		}
		System.out.println("sumMoney");
		s = String.valueOf(f);
		return s;
	}
}
