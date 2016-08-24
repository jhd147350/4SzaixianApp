package com.jhd.fourszaixian.ui.append;

import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import net.tsz.afinal.http.AjaxParams;

import org.apache.http.entity.StringEntity;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.ui.append.NewsBean.Lists;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Picture;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class DaijiaActivity extends Activity {
	ListView listView;
	List<DaijiaBean> daijiaBeans = new ArrayList<DaijiaBean>();
	FinalBitmap finalBitmap;

	private Button book_now;
	private Button my_order;
	private OnClickListener listener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.book_now:
				startActivity(new Intent(DaijiaActivity.this,DaijiaYuyueActivity.class));

				break;
			case R.id.myorder:
				startActivity(new Intent(DaijiaActivity.this,MyOrderActivity.class));
				break;

			}

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_daijia);
		finalBitmap = FinalBitmap.create(this);
		initViews();
		getDaijia();
	}

	private void initViews() {
		// TODO Auto-generated method stub
		listView = (ListView) findViewById(R.id.listview);
		book_now = (Button) findViewById(R.id.book_now);
		my_order = (Button) findViewById(R.id.myorder);

		my_order.setOnClickListener(listener);
		book_now.setOnClickListener(listener);

	}

	public void onClick(View v) {
		if (v.getId() == R.id.back) {
			finish();
		}

	}

	void getDaijia() {
		HttpUtils httpUtils = new HttpUtils();
		RequestParams params = new RequestParams();
		// http://p.aidaijia.com/pservice.aspx
		// params.setBodyEntity(new
		// BodyParamsEntity("1201|39.985044|116.504741|32d374227e5b70ab6cc55f1b994f0e7c|869334020938075|8ce32395964b4731b1432d0f35b37bf4"));
		try {
			// TODO 真坑 String 可以放任意格式的数据，为了给body值 ，调试一下午
			params.setBodyEntity(new StringEntity(
					"1201|39.985044|116.504741|32d374227e5b70ab6cc55f1b994f0e7c|869334020938075|8ce32395964b4731b1432d0f35b37bf4"));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("jhdpost-eeee");
		}
		// params.addBodyParameter("qwee",
		// "1201|39.985044|116.504741|32d374227e5b70ab6cc55f1b994f0e7c|869334020938075|8ce32395964b4731b1432d0f35b37bf4");
		httpUtils.send(HttpMethod.POST, "http://p.aidaijia.com/pservice.aspx",
				params, new RequestCallBack<String>() {

					@Override
					public void onFailure(HttpException arg0, String arg1) {
						// TODO Auto-generated method stub
						System.out.println("jhdpost-ffff" + arg0.toString());
						System.out.println("jhdpost-ffff" + arg1);
					}

					@Override
					public void onSuccess(ResponseInfo<String> arg0) {
						// TODO Auto-generated method stub
						// System.out.println("jhdpost-s"+arg0.result+"\n"+arg0.toString());
						try {
							JSONArray ja = new JSONArray(arg0.result);
							for (int i = 0; i < ja.length(); i++) {
								JSONObject jo = (JSONObject) ja.get(i);
								DaijiaBean daijiaBean = new DaijiaBean();
								daijiaBean.setName(jo.getString("name"));
								daijiaBean.setCishu(jo.getString("cishu"));
								daijiaBean.setGoodrate(jo.getString("goodrate"));
								daijiaBean.setJialin(jo.getString("jialin"));
								daijiaBean.setJuli(jo.getDouble("juli"));
								daijiaBean.setPic(jo.getString("pic"));
								daijiaBean.phone=jo.getString("phone");
								daijiaBeans.add(daijiaBean);

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						listView.setAdapter(new MyBaseAdapter(
								getApplicationContext(), daijiaBeans));
					}
				});
	}

	
	private class MyBaseAdapter extends BaseAdapter {

		private Context context;
		private List<DaijiaBean> data;

		// 页面各项的点击事件

		MyBaseAdapter(Context context, List<DaijiaBean> data) {
			this.context = context;
			this.data = data;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			MyViewHolder holder = null;
			if (convertView == null) {
				convertView = View.inflate(context, R.layout.item_daijia, null);
				holder = new MyViewHolder();
				holder.name = (TextView) convertView.findViewById(R.id.name);
				holder.cishu = (TextView) convertView.findViewById(R.id.cishu);
				holder.goodrate = (TextView) convertView
						.findViewById(R.id.goodrate);
				holder.pic = (ImageView) convertView.findViewById(R.id.pic);
				holder.juli = (TextView) convertView.findViewById(R.id.juli);
				holder.yuyue = (Button) convertView.findViewById(R.id.yuyue);
				holder.jialin = (TextView) convertView
						.findViewById(R.id.jialin);
				convertView.setTag(holder);
			} else {
				holder = (MyViewHolder) convertView.getTag();
			}
			final DaijiaBean temp = data.get(position);
			holder.name.setText(temp.getName());
			holder.cishu.setText("代驾:" + temp.getCishu() + "次");
			holder.goodrate.setText("好评率:" + temp.getGoodrate() + "%");
			holder.jialin.setText("驾龄:" + temp.getJialin() + "年");
			DecimalFormat df = new DecimalFormat("#0.0");// 构造方法的字符格式这里如果小数不足2位,会以0补足.
			holder.juli.setText("距离:" + df.format(temp.getJuli()) + "公里");

			finalBitmap.display(holder.pic, temp.getPic());
			
			holder.yuyue.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO 还要传递一些相关数据去记录订单，以及状态
					temp.getName();
					Intent it=new Intent(DaijiaActivity.this,DaijiaYuyueActivity.class);
					it.putExtra("name", temp.getName());
					it.putExtra("phone", temp.phone);
					startActivity(it);
				}
			});

			return convertView;
		}
	}

	private class MyViewHolder {
		TextView name;
		ImageView pic;
		TextView jialin;
		TextView cishu;
		TextView goodrate;
		TextView juli;
		Button yuyue;
	}
}
