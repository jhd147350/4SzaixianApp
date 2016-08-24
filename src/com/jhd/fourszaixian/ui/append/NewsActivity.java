package com.jhd.fourszaixian.ui.append;

import java.util.ArrayList;
import java.util.List;

import javax.security.auth.PrivateCredentialPermission;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import net.tsz.afinal.FinalBitmap;
import net.tsz.afinal.FinalHttp;
import net.tsz.afinal.http.AjaxCallBack;
import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.ui.append.NewsBean.Lists;
import com.jhd.fourszaixian.utils.UserUtils;

import android.R.integer;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class NewsActivity extends Activity implements OnClickListener {

	private TextView back;
	private ListView listView;
	private List<NewsBean> allnews;
	private FinalBitmap finalBitmap;
	private ProgressBar pb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_news);
		initViews();
		allnews = new ArrayList<NewsBean>();
		finalBitmap = FinalBitmap.create(this);
		finalBitmap.configLoadingImage(R.drawable.ad1);
		finalBitmap.configLoadfailImage(R.drawable.ad1);
		getNews();

	}

	private void initViews() {
		// TODO Auto-generated method stub
		back = (TextView) findViewById(R.id.back);
		back.setOnClickListener(this);
		listView = (ListView) findViewById(R.id.list_view);
		pb = (ProgressBar) findViewById(R.id.pb);
	}

	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		// 设置activity跳转的动画
		this.overridePendingTransition(R.anim.activity_slide_in_right,
				R.anim.activity_slide_out_left);

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;

		default:
			break;
		}
	}

	private void getNews() {
		FinalHttp fh = new FinalHttp();
		/*
		 * AjaxParams params = new AjaxParams(); params.put("action", "1");
		 * params.put("phonenum", phonenum); params.put("password", password);
		 * myphonenum=phonenum; mypassword=password;
		 */
		AjaxCallBack<String> callBack = new AjaxCallBack<String>() {
			@Override
			public void onSuccess(String s) {
				super.onSuccess(s);
				try {
					JSONObject str = new JSONObject(s);
					JSONObject data = str.getJSONObject("data");
					JSONArray articleList = data.getJSONArray("articleList");
					for (int i = 0; i < articleList.length(); i++) {
						JSONObject temp = (JSONObject) articleList.get(i);
						JSONArray list = temp.getJSONArray("list");
						NewsBean newsBean = new NewsBean();
						String date = temp.getString("date");
						newsBean.setDate(date);
						List<Lists> lists = new ArrayList<Lists>();
						for (int j = 0; j < list.length(); j++) {
							JSONObject zixun = (JSONObject) list.get(j);
							Lists tempj = new NewsBean().new Lists();
							tempj.setDate(zixun.getString("date"));
							tempj.setTitle(zixun.getString("title"));
							tempj.setUrl(zixun.getString("url"));
							tempj.setImageurl(zixun.getString("imageurl"));
							tempj.setType(zixun.getInt("type"));
							tempj.setContent(zixun.getString("content"));
							lists.add(tempj);
						}
						newsBean.setList(lists);
						allnews.add(newsBean);
					}
				} catch (JSONException e) {e.printStackTrace();}
				listView.setAdapter(new MyBaseAdapter(getApplicationContext(),
						allnews));
				listView.setSelection(allnews.size() - 1);
				pb.setVisibility(View.GONE);

			}
			@Override
			public void onFailure(Throwable t, int errorNo, String strMsg) {
				super.onFailure(t, errorNo, strMsg);
			}
			@Override
			public void onLoading(long count, long current) {
				super.onLoading(count, current);
			}
		};
		fh.get("http://app.4sline.com:8080/buyer/articleGet.do", null, callBack);
	}

	private class MyBaseAdapter extends BaseAdapter {

		private Context context;
		private List<NewsBean> data;

		// 页面各项的点击事件

		MyBaseAdapter(Context context, List<NewsBean> data) {
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
		public View getView(final int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			MyViewHolder holder = null;
			// MyItemViewHolder itemHolder=null;
			// List<MyItemViewHolder> itemList=null;
			// MyTwo myTwo=null;
			// View view=null;
			if (convertView == null) {
				convertView = View
						.inflate(context, R.layout.item_day_new, null);
				holder = new MyViewHolder();
				holder.title = (TextView) convertView.findViewById(R.id.title);
				holder.image = (ImageView) convertView.findViewById(R.id.image);
				holder.date = (TextView) convertView.findViewById(R.id.date);
				holder.title1 = (TextView) convertView
						.findViewById(R.id.title1);
				holder.image1 = (ImageView) convertView
						.findViewById(R.id.image1);
				holder.title2 = (TextView) convertView
						.findViewById(R.id.title2);
				holder.image2 = (ImageView) convertView
						.findViewById(R.id.image2);
				holder.isShow1 = (LinearLayout) convertView
						.findViewById(R.id.isShow1);
				holder.isShow2 = (LinearLayout) convertView
						.findViewById(R.id.isShow2);

				/*
				 * itemHolder=new MyItemViewHolder();
				 * itemHolder.view=View.inflate(context,
				 * R.layout.item_item_day_new, null);
				 * itemHolder.image=(ImageView)
				 * itemHolder.view.findViewById(R.id.image);
				 * itemHolder.title=(TextView)
				 * itemHolder.view.findViewById(R.id.title);
				 * 
				 * myTwo=new MyTwo(); myTwo.myViewHolder=holder;
				 * myTwo.myItemViewHolder=itemHolder;
				 */
				convertView.setTag(holder);
				// convertView.sett
			} else {
				// myTwo=(MyTwo) convertView.getTag();
				holder = (MyViewHolder) convertView.getTag();
				// itemHolder=myTwo.myItemViewHolder;
			}
			holder.date.setText(data.get(position).getDate());
			// TODO image
			holder.title
					.setText(data.get(position).getList().get(0).getTitle());
			// holder.title1.setText(data.get(position).getList().get(1).getTitle());
			// 使用框架加载网路图片
			finalBitmap.display(holder.image,
					data.get(position).getList().get(0).getImageurl());

			// 动态添加每一天的资讯

			// 这里暂时这么处理

			int length = data.get(position).getList().size();
			if (length > 1) {
				holder.isShow1.setVisibility(View.VISIBLE);
				holder.title1.setText(data.get(position).getList().get(1)
						.getTitle());
				finalBitmap.display(holder.image1, data.get(position).getList()
						.get(1).getImageurl());
			} else {
				holder.isShow1.setVisibility(View.GONE);
			}
			if (length > 2) {
				holder.isShow2.setVisibility(View.VISIBLE);
				holder.title2.setText(data.get(position).getList().get(2)
						.getTitle());
				finalBitmap.display(holder.image2, data.get(position).getList()
						.get(2).getImageurl());
			} else {
				holder.isShow2.setVisibility(View.GONE);
			}
			// 点击事件
			OnClickListener listener = new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent it=new Intent(getApplicationContext(),NewsDetailActivity.class);
					switch (v.getId()) {
					case R.id.image:
						it.putExtra("url", data.get(position).getList().get(0).getUrl());
						break;
					case R.id.isShow1:
						it.putExtra("url", data.get(position).getList().get(1).getUrl());
						break;
					case R.id.isShow2:
						it.putExtra("url", data.get(position).getList().get(2).getUrl());
						break;
					}
					startActivity(it);

				}
			};
			holder.image.setOnClickListener(listener);
			holder.isShow1.setOnClickListener(listener);
			holder.isShow2.setOnClickListener(listener);

			/*
			 * for(int i=1;i<length;i++){ /*View item=View.inflate(context,
			 * R.layout.item_item_day_new, null); TextView title=(TextView)
			 * item.findViewById(R.id.title); ImageView image=(ImageView)
			 * item.findViewById(R.id.image);
			 * title.setText(data.get(position).getList().get(i).getTitle());
			 */
			// TODO image
			/*
			 * itemHolder.title.setText(data.get(position).getList().get(i).getTitle
			 * ()); holder.ll.addView(itemHolder.view); }
			 */

			// ll.addView(View.inflate(context, R.layout.item_item_day_new,
			// null));
			return convertView;
		}
	}

	private class MyViewHolder {
		TextView title;
		ImageView image;
		TextView date;

		// 写死处理，多余2项 则不会显示多余的
		// TODO 动态添加有问题，上下拉动时 会加载很多重复的项
		TextView title1;
		ImageView image1;
		LinearLayout isShow1;
		LinearLayout isShow2;
		TextView title2;
		ImageView image2;

	}
	/*
	 * private class MyItemViewHolder{ TextView title; ImageView image; View
	 * view; } private class MyTwo{ MyViewHolder myViewHolder; MyItemViewHolder
	 * myItemViewHolder; }
	 */
}
