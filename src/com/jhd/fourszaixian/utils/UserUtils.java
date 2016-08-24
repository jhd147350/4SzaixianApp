package com.jhd.fourszaixian.utils;

import io.yunba.android.manager.YunBaManager;

import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import com.jhd.fourszaixian.entity.User;

import android.R.string;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

public class UserUtils {

	// url
	// 外网服务器地址
	// public static final String
	// URL="http://flowergoserver.duapp.com/4Szaixian/";

	// 内网服务器地址
	public static String IP = "192.168.56.1";
	// public static String URL="http://192.168.56.1:8080/4Szaixian/";
	public static String URL = "http://" + IP + ":8080/4Szaixian/";

	public static String CARURL = URL + "CarServlet";

	public static String LOGINURL = URL + "UserServlet";

	public static final int MycenterToLoginRequestCode = 5;
	public static final int MycarToLoginRequestCode = 6;
	public static final int MycarToBrandRequestCode = 11;
	public static final int MycarToSeriesRequestCode = 12;
	public static final int MycarToModelRequestCode = 13;
	public static final int BrandToMycarResultCode = 14;
	public static final int SeriesToMycarResultCode = 15;
	public static final int ModelToMycarResultCode = 16;

	public static String IMGURLFOLDER = URL + "image/";

	public static String ORDERURL = URL + "OrderServlet";

	public static String QUANURL = URL + "QuanServlet";

	public static String RECARDURL = URL + "RecordServlet";

	public static String MESSAGEURL = URL + "MessageServlet";

	public static String SHOPURL = URL + "ShopServlet";

	public static String DISTANCEURL = URL + "DistancesServlet";

	public static String BAOYANGURL = URL + "BaoyangServlet";

	public static String CURRENT_LOACTION = "河北省廊坊市爱民东道133号";

	public static void updateIP() {
		// URL
		URL = "http://" + IP + ":8080/4Szaixian/";
		CARURL = URL + "CarServlet";

		LOGINURL = URL + "UserServlet";
		IMGURLFOLDER = URL + "image/";
		ORDERURL = URL + "OrderServlet";
		QUANURL = URL + "QuanServlet";
		RECARDURL = URL + "RecordServlet";
		MESSAGEURL = URL + "MessageServlet";
		SHOPURL = URL + "ShopServlet";
		DISTANCEURL = URL + "DistancesServlet";
		BAOYANGURL = URL + "BaoyangServlet";
	}

	public static void setAlias(final String alias, Context context) {
		// final String alias = alias_of_getset.getText().toString().trim();
		if (TextUtils.isEmpty(alias)) {
			Log.e("jhd", "alias==null");
			return;
		}
		// setCostomMsg("set alias = " + alias);
		YunBaManager.setAlias(context, alias, new IMqttActionListener() {

			@Override
			public void onSuccess(IMqttToken arg) {

				StringBuilder showMsg = new StringBuilder();
				showMsg.append("[Demo] setAlias alias ").append(" = ")
						.append(alias).append(" succeed");
				Log.e("jhd", "suc" + showMsg.toString());
				// setCostomMsg(showMsg.toString());
			}

			@Override
			public void onFailure(IMqttToken arg0, Throwable arg) {
				StringBuilder showMsg = new StringBuilder();
				showMsg.append("[Demo] setAlias alias ").append(" = ")
						.append(alias).append(" failed");
				Log.e("jhd", "failed" + showMsg.toString());
				// setCostomMsg(showMsg.toString());
			}
		});
	}

	public static void handlePublishAlias(final String msg, Context context) {
		// final String alias = alias_txt.getText().toString().trim();
		// final String msg = alias_msg.getText().toString().trim();
		// 暂时只是实现了2个人的通信，没有实现多人加好友的功能
		String alias;
		if (User.getInstance().getPhonenum() != null
				&& User.getInstance().getPhonenum().equals("18333606137")) {
			alias = "18333606136";
		} else {
			alias = "18333606137";
		}
		// 服务端指定了是18333606137.
		//String Alias = "18333606137";
		//if (Tophone != null) {
		//	Alias = Tophone;
		//}

		//final String alias = "18333606137";
		if (TextUtils.isEmpty(alias) || TextUtils.isEmpty(msg)) {
			Log.e("jhd", "Alias and msg should not be null");
			return;
		}

		// setCostomMsg("Publish msg = " + msg + " to alias = " + alias);
		YunBaManager.publishToAlias(context, alias, msg,
				new IMqttActionListener() {
					public void onSuccess(IMqttToken asyncActionToken) {

						//String msgLog = "Publish alias succeed : " + alias;
						//StringBuilder showMsg = new StringBuilder();
						//showMsg.append("[Demo] publish alias msg")
						//		.append(" = ").append(msg).append(" to ")
						//		.append("alias").append(" = ").append(alias)
						//		.append(" succeed");
						// setCostomMsg(showMsg.toString());
						// DemoUtil.showToast(msgLog, getApplicationContext());
						//Log.e("jhd", "suc" + showMsg.toString());
					}

					@Override
					public void onFailure(IMqttToken asyncActionToken,
							Throwable exception) {
						//String msg = "[Demo] Publish alias = " + alias
						//		+ " failed : " + exception.getMessage();
						// setCostomMsg(msg);
						// DemoUtil.showToast(msg, getApplicationContext());
						//Log.e("jhd", "failed" + msg);

					}
				});
	}

	public static final String ZJYURL = URL + "ZjyServlet";

	public static final String INFOURL = URL + "InfoServlet";
}
