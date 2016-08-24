package com.jhd.fourszaixian.ui.append;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;

public class MySelfDriveBaseAdapter extends BaseAdapter {
	private FinalBitmap finalBitmap;
			
	private Context context;
	private List<SelfDriveTourBean> data;
	MySelfDriveBaseAdapter(Context context, List<SelfDriveTourBean> data) {
		this.context = context;
		this.data = data;
		finalBitmap = FinalBitmap.create(context);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		System.out.println("jhd-count:----------------------------------------");
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
		long time1 = System.currentTimeMillis();
		MyBaseViewHolder holder = null;
		if (convertView == null) {
			convertView = View.inflate(context, R.layout.zjyitem, null);
			holder = new MyBaseViewHolder();
			holder.zjy_activityAddress = (TextView) convertView
					.findViewById(R.id.zjy_activityAddress);
			holder.zjy_activityTime = (TextView) convertView
					.findViewById(R.id.zjy_activityTime);
			holder.zjy_cfd = (TextView) convertView.findViewById(R.id.zjy_cfd);
			holder.zjy_content = (TextView) convertView
					.findViewById(R.id.zjy_content);
			holder.zjy_img = (ImageView) convertView.findViewById(R.id.zjy_img);
			holder.zjy_title = (TextView) convertView
					.findViewById(R.id.zjy_title);
			convertView.setTag(holder);
		} else {
			holder = (MyBaseViewHolder) convertView.getTag();
		}
		SelfDriveTourBean temp = data.get(position);
		holder.zjy_activityAddress.setText(temp.getActivityLocation());
		holder.zjy_activityTime.setText(temp.getActivityTime());
		holder.zjy_cfd.setText(temp.getDepartureLocation());
		holder.zjy_content.setText(temp.getContent());
		holder.zjy_title.setText(temp.getTitle());
		// 使用框架加载网路图片
		 finalBitmap.display(holder.zjy_img,temp.getPicUrl());
		long time2 = System.currentTimeMillis();
		// System.out.println("jhd-"+time1);
		// System.out.println("jhd-"+time2);
		System.out.println("jhd-pos:" + position + "-time:"
				+ (long) (time2 - time1));
		return convertView;
	}
}

class MyBaseViewHolder {
	TextView zjy_title;
	ImageView zjy_img;
	TextView zjy_content;
	TextView zjy_cfd;
	TextView zjy_activityAddress;
	TextView zjy_activityTime;
}