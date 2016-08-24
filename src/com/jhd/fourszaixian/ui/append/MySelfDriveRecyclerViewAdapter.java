package com.jhd.fourszaixian.ui.append;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;



public class MySelfDriveRecyclerViewAdapter extends Adapter<MySelfDriveRecyclerViewAdapter.MyViewHolder> implements OnClickListener{

	private Context context;
	private List<SelfDriveTourBean> data;
	private FinalBitmap finalBitmap;
	
	private OnItemClickListener onItemClickListener=null;
	
	

	public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
		this.onItemClickListener = onItemClickListener;
	}

	public MySelfDriveRecyclerViewAdapter(Context context,
			List<SelfDriveTourBean> data) {
		super();
		this.context = context;
		this.data = data;
		finalBitmap = FinalBitmap.create(context);
	}

	@Override
	public int getItemCount() {
		// TODO Auto-generated method stub
		return data.size();
	}

	@Override
	public void onBindViewHolder(MyViewHolder holder, int position) {
		// TODO Auto-generated method stub
		SelfDriveTourBean bean = data.get(position);
		holder.zjy_activityAddress.setText(bean.getActivityLocation());
		holder.zjy_activityTime.setText(bean.getActivityTime());
		holder.zjy_cfd.setText(bean.getDepartureLocation());
		holder.zjy_content.setText(bean.getContent());
		holder.zjy_title.setText(bean.getTitle());
		
		holder.itemView.setTag(data.get(position));
		
		finalBitmap.display(holder.zjy_img, bean.getPicUrl());
	}

	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int arg1) {
		// TODO Auto-generated method stub
		View v=LayoutInflater.from(context)
		.inflate(R.layout.zjyitem, parent, false);
		MyViewHolder holder = new MyViewHolder(v);
		v.setOnClickListener(this);
		return holder;
	}
	public class MyViewHolder extends ViewHolder{

		TextView zjy_title;
		ImageView zjy_img;
		TextView zjy_content;
		TextView zjy_cfd;
		TextView zjy_activityAddress;
		TextView zjy_activityTime;

		public MyViewHolder(View v) {
			super(v);
			zjy_activityAddress = (TextView) v
					.findViewById(R.id.zjy_activityAddress);
			zjy_activityTime = (TextView) v.findViewById(R.id.zjy_activityTime);
			zjy_cfd = (TextView) v.findViewById(R.id.zjy_cfd);
			zjy_content = (TextView) v.findViewById(R.id.zjy_content);
			zjy_img = (ImageView) v.findViewById(R.id.zjy_img);
			zjy_title = (TextView) v.findViewById(R.id.zjy_title);
			
		
			//getPosition();
		}

		
	}
	public interface OnItemClickListener{
		void onItemClick(View v,SelfDriveTourBean data);
	}
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (onItemClickListener!=null) {
			onItemClickListener.onItemClick(v, (SelfDriveTourBean) v.getTag());
		}
		
	}

}



