package com.jhd.fourszaixian.ui.append;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Order;

public class DaijiaOrderAdapter extends BaseAdapter{
	Context context;
	List<Order> list;
	LayoutInflater inflater;
	public DaijiaOrderAdapter(Context context,List<Order> list) {
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
			convertView = inflater.inflate(R.layout.item_daijia_order, null);
			holder.time = (TextView) convertView.findViewById(R.id.time);
			holder.address = (TextView) convertView.findViewById(R.id.address);
			holder.order_num = (TextView) convertView.findViewById(R.id.order_num);
			holder.driver_name = (TextView) convertView.findViewById(R.id.driver_name);
			holder.driver_phone = (TextView) convertView.findViewById(R.id.driver_phone);
			holder.order_state = (TextView) convertView.findViewById(R.id.order_state);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		Order temp = list.get(position);
		System.out.println("jhd-"+temp.toString());
		holder.time.setText(temp.getTime());
		holder.address.setText("地址:"+temp.getMoney());
		holder.order_num.setText("订单号:"+temp.getId());
		holder.driver_name.setText("姓名:"+temp.getName());
		
		holder.driver_phone.setText("电话:"+temp.getFeatures());
		holder.order_state.setText("状态:"+temp.getState());
		return convertView;
	}

	class ViewHolder{
		TextView time,address,order_num,driver_name,driver_phone,order_state;
	}
}
