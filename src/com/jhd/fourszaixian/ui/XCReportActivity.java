package com.jhd.fourszaixian.ui;

import java.text.DecimalFormat;
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
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;

import com.fenghuo.jhdwxt.fourszaixian.R;

import com.jhd.fourszaixian.entity.Distances;
import com.jhd.fourszaixian.entity.Record;
import com.jhd.fourszaixian.ui.append.PieView;
import com.jhd.fourszaixian.utils.UserUtils;

public class XCReportActivity extends Activity implements OnClickListener {

	// 2016/5/10 ɾ��������ʻ/����������ݣ���ҳ��ʱ����Ҫ��Щ����

	// private TextView report_type;
	private TextView report_month_all;
	private TextView report_year_all;
	// private TextView report_money;
	List<Record> list;
	// List<Distances> disByList;
	// List<Distances> disXsList;
	String uid;
	Calendar c = Calendar.getInstance();
	// String baoyang = "����";
	// String xingshi = "��ʻ";
	String month = String.valueOf(c.get(Calendar.MONTH) + 1);
	String year = String.valueOf(c.get(Calendar.YEAR));
	// private TextView report_by;
	// private TextView report_xs;

	// --2016/5/11����
	private TextView jiayou;
	private TextView tingche;
	private TextView guolu;
	private TextView weibao;
	private TextView qita;
	private TextView date;

	private PieView pie;// ��״ͼ
	private TextView perjiayou;
	private TextView pertingche;
	private TextView perguolu;
	private TextView perweibao;
	private TextView perqita;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.xingchereport);
		Intent it = getIntent();
		uid = (String) it.getSerializableExtra("uid");
		initViews();
		// report_type = (TextView) findViewById(R.id.report_type);
		report_month_all = (TextView) findViewById(R.id.report_month_all);
		report_year_all = (TextView) findViewById(R.id.report_year_all);
		// report_money = (TextView) findViewById(R.id.report_money);
		// report_by = (TextView) findViewById(R.id.report_yibaoyang);
		// report_xs = (TextView) findViewById(R.id.report_yixingshi);
		recardByMonth(uid, month);

		// disFindByType(uid);

	}

	private void initViews() {
		// TODO Auto-generated method stub
		jiayou = (TextView) findViewById(R.id.jiayou);
		tingche = (TextView) findViewById(R.id.tingche);
		guolu = (TextView) findViewById(R.id.guolu);
		weibao = (TextView) findViewById(R.id.weibao);
		qita = (TextView) findViewById(R.id.qita);
		date = (TextView) findViewById(R.id.date);

		pie = (PieView) findViewById(R.id.pie);
		perjiayou = (TextView) findViewById(R.id.per_jiayou);
		pertingche = (TextView) findViewById(R.id.per_tingche);
		perguolu = (TextView) findViewById(R.id.per_guolu);
		perweibao = (TextView) findViewById(R.id.per_weibao);
		perqita = (TextView) findViewById(R.id.per_qita);
		// float arrper[]=new float [] {10f,10f,20f,10f,10f};
		// pie.setPer(arrper);

		date.setText(year + "-" + month);
	}

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
					// ������Ǯ��
					// int sum;
					report_month_all.setText(sumMoney(list) + "Ԫ");
					// ����ÿ���Ǯ������ʾ�ڱ�״ͼ��
					pie.setPer(setItemMoney(list));

					System.out.println("report_month_all:  "
							+ report_month_all.getText().toString());

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
				break;
			case 2: {
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
					report_year_all.setText(sumMoney(list) + "Ԫ");
					System.out.println("report_year_all:  "
							+ report_year_all.getText().toString());

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
				break;
			case 3: {
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
						r.setXcTime(jsonObject.getString("recard_xctime"));
						r.setXcMoney(jsonObject.getDouble("recard_xcmoney"));
						r.setXcYear(jsonObject.getString("recard_xcyear"));
						list.add(r);
						System.out.println("list " + list.toString());
					}
					// report_money.setText(sumMoney(list));
					// System.out.println("report_money  v :"
					// + report_money.getText().toString());

				} catch (JSONException e) {

					e.printStackTrace();
				}

			}
				break;
			/*
			 * case 4: { try { JSONArray ja = new JSONArray(sr); disByList = new
			 * ArrayList<Distances>(); disXsList = new ArrayList<Distances>();
			 * System.out.println("ja " + ja.length()); for (int i = 0; i <
			 * ja.length(); i++) { JSONObject jsonObject = ja.getJSONObject(i);
			 * Distances dis = new Distances();
			 * dis.setId(jsonObject.getInt("distance_id"));
			 * dis.setUid(jsonObject.getString("distance_uid"));
			 * dis.setType(jsonObject.getString("distance_type"));
			 * dis.setDiatancess(jsonObject.getDouble("distance_dis")); if
			 * (dis.getType().equals(baoyang)) { disByList.add(dis); } else {
			 * disXsList.add(dis); }
			 * 
			 * } //report_by.setText(sumDistance(disByList));
			 * //report_xs.setText(sumDistance(disXsList));
			 * 
			 * } catch (JSONException e) {
			 * 
			 * e.printStackTrace(); }
			 * 
			 * } break;
			 */

			case 5:
				Log.e("qqq", "failed");
				Toast.makeText(getApplicationContext(), "��������ʧ�ܣ�����",
						Toast.LENGTH_SHORT).show();
				break;

			default:
				break;
			}
		}

	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.xcreport_tv_back: {
			finish();
		}
			break;
		/*
		 * case R.id.report_guolu: { // report_type.setText(R.string.guolu); }
		 * break;
		 * 
		 * case R.id.report_jiayou: // report_type.setText(R.string.jiayou);
		 * 
		 * break;
		 * 
		 * case R.id.report_weibao: // report_type.setText(R.string.weibao);
		 * 
		 * break;
		 * 
		 * case R.id.report_qita: // report_type.setText(R.string.others);
		 * 
		 * break;
		 * 
		 * case R.id.report_tingche: // report_type.setText(R.string.tingche);
		 * 
		 * break;
		 */

		/*
		 * case R.id.report_month: { String type =
		 * report_type.getText().toString(); System.out.println("month type:" +
		 * type); recardByMonthType(uid, month, type); }
		 * 
		 * break; case R.id.report_year: { String type =
		 * report_type.getText().toString(); System.out.println("year type:" +
		 * type); recardByYearType(uid, year, type); }
		 * 
		 * break;
		 */

		default:
			break;
		}
	}

	private float[] setItemMoney(List<Record> list) {
		// TODO Auto-generated method stub
		double jiayoufei = 0.0;
		double tingchefei = 0.0;
		double guolufei = 0.0;
		double weibaofei = 0.0;
		double qita = 0.0;
		double sum = 0.0;

		for (Record record : list) {
			switch (record.getXcType()) {
			case "���ͷ�":
				jiayoufei += record.getXcMoney();
				break;
			case "ͣ����":
				tingchefei += record.getXcMoney();
				break;
			case "��·��":
				guolufei += record.getXcMoney();
				break;
			case "ά����":
				weibaofei += record.getXcMoney();
				break;
			case "����":
				qita += record.getXcMoney();
				break;
			}
		}
		sum = jiayoufei + tingchefei + guolufei + weibaofei + qita;
		// ��������ٷֱ�
		double temp = 100.0f / sum;
		jiayou.setText(jiayoufei + "Ԫ");
		guolu.setText(guolufei + "Ԫ");
		tingche.setText(tingchefei + "Ԫ");
		weibao.setText(weibaofei + "Ԫ");
		this.qita.setText(qita + "Ԫ");
		if (sum == 0d) {
			return new float[] { 0f, 0f, 0f, 0f, 0f };
		}
		DecimalFormat df = new DecimalFormat("#0.00");// ���췽�����ַ���ʽ�������С������2λ,����0����.
		perjiayou.setText(df.format(jiayoufei * temp) + "%");
		perguolu.setText(df.format(guolufei * temp) + "%");
		perqita.setText(df.format(qita * temp) + "%");
		perweibao.setText(df.format(weibaofei * temp) + "%");
		pertingche.setText(df.format(tingchefei * temp) + "%");
		return new float[] { (float) (jiayoufei * temp),
				(float) (tingchefei * temp), (float) (guolufei * temp),
				(float) (weibaofei * temp), (float) (qita * temp) };

	}

	private void recardByMonth(final String uid, String month) {
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "2"); // ���·ݲ���
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

				// ������ɺ���ȥ�������Ǯ��
				recardByYear(uid, year);
			}

			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				// TODO Auto-generated method stub
				super.onFailure(t, errorNo, strMsg);
				handler.sendEmptyMessage(5);// failed
			}
		};
		fh.get(UserUtils.RECARDURL, params, callBack);

	}

	private void recardByYear(String uid, String year) {
		FinalHttp fh = new FinalHttp();
		AjaxParams params = new AjaxParams();
		params.put("action", "4"); // �������
		params.put("uid", uid);
		params.put("year", year);
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
				handler.sendEmptyMessage(5);// failed
			}
		};
		fh.get(UserUtils.RECARDURL, params, callBack);
	}

	/*
	 * private void recardByMonthType(String uid, String month, String type) {
	 * FinalHttp fh = new FinalHttp();
	 * 
	 * AjaxParams params = new AjaxParams(); params.put("action", "5"); //
	 * ���·ݺ����Ͳ��ү� params.put("uid", uid); params.put("month", month);
	 * params.put("type", type); AjaxCallBack<String> callBack = new
	 * AjaxCallBack<String>() {
	 * 
	 * @Override public void onSuccess(String s) { // TODO Auto-generated method
	 * stub super.onSuccess(s); Message msg = new Message(); msg.what = 3;
	 * msg.obj = s; handler.sendMessage(msg);// suc }
	 * 
	 * @Override public void onFailure(Throwable t, int errorNo, String strMsg)
	 * { // TODO Auto-generated method stub super.onFailure(t, errorNo, strMsg);
	 * handler.sendEmptyMessage(5);// failed } }; fh.get(UserUtils.RECARDURL,
	 * params, callBack);
	 * 
	 * }
	 */

	/*
	 * private void recardByYearType(String uid, String year, String type) {
	 * FinalHttp fh = new FinalHttp();
	 * 
	 * AjaxParams params = new AjaxParams(); params.put("action", "6"); //
	 * ����ݺ����Ͳ��� params.put("uid", uid); params.put("year", year);
	 * params.put("type", type); AjaxCallBack<String> callBack = new
	 * AjaxCallBack<String>() {
	 * 
	 * @Override public void onSuccess(String s) { // TODO Auto-generated method
	 * stub super.onSuccess(s); Message msg = new Message(); msg.what = 3;
	 * msg.obj = s; handler.sendMessage(msg);// suc }
	 * 
	 * @Override public void onFailure(Throwable t, int errorNo, String strMsg)
	 * { // TODO Auto-generated method stub super.onFailure(t, errorNo, strMsg);
	 * handler.sendEmptyMessage(5);// failed } }; fh.get(UserUtils.RECARDURL,
	 * params, callBack);
	 * 
	 * }
	 */

	/*
	 * private void disFindByType(String uid) { FinalHttp fh = new FinalHttp();
	 * AjaxParams params = new AjaxParams(); params.put("action", "1");
	 * params.put("uid", uid);
	 * 
	 * AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
	 * 
	 * @Override public void onSuccess(String s) { // TODO Auto-generated method
	 * stub super.onSuccess(s); Message msg = new Message(); msg.what = 4;
	 * msg.obj = s; handler.sendMessage(msg);// suc }
	 * 
	 * @Override public void onFailure(Throwable t, int errorNo, String strMsg)
	 * { // TODO Auto-generated method stub super.onFailure(t, errorNo, strMsg);
	 * handler.sendEmptyMessage(5);// failed } }; fh.get(UserUtils.DISTANCEURL,
	 * params, callBack);
	 * 
	 * }
	 */

	private String sumMoney(List<Record> rlist) {
		String s = "0";
		double f = 0;
		for (Record r : rlist) {

			f += r.getXcMoney();

		}
		s = String.valueOf(f);
		return s;
	}

	/*
	 * private String sumDistance(List<Distances> dlist) { String s = "0";
	 * double d = 0; for (Distances dis : dlist) { d += dis.getDiatancess(); } s
	 * = String.valueOf(d); return s; }
	 */

}
