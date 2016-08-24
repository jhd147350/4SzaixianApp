package com.jhd.fourszaixian.fragment;

import java.util.List;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Order;
import com.jhd.fourszaixian.utils.TimeTranslation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class orderAdapter extends BaseAdapter {

	Context context;
	List<Order> list;
	LayoutInflater inflater;
	TimeTranslation t;
	public orderAdapter(Context context,List<Order> list) {
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
		if(convertView == null){
			holder = new ViewHolder();
			convertView = inflater.inflate(R.layout.orderitem, null);
			holder.order_type = (TextView) convertView.findViewById(R.id.order_type);
			holder.order_state = (TextView) convertView.findViewById(R.id.order_state);
			holder.order_name = (TextView) convertView.findViewById(R.id.order_name);
			holder.order_feature = (TextView) convertView.findViewById(R.id.order_features);
			holder.order_money = (TextView) convertView.findViewById(R.id.order_money);
			holder.order_time = (TextView) convertView.findViewById(R.id.order_time);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.order_feature.setText(list.get(position).getFeatures());
		holder.order_money.setText(list.get(position).getMoney());
		holder.order_name.setText(list.get(position).getName());
		holder.order_state.setText(list.get(position).getState());
		
		holder.order_time.setText(list.get(position).getTime());
		holder.order_type.setText(list.get(position).getType());
		return convertView;
	}

	class ViewHolder{
		TextView order_type,order_state,order_name,order_feature,order_money,order_time;
	}
}
