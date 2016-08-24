package com.jhd.fourszaixian.fragment;

import java.util.List;

import net.tsz.afinal.FinalBitmap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.entity.Shop;

public class shopAdapter extends BaseAdapter{

	Context context;
	List<Shop> list;
	LayoutInflater inflater;
    FinalBitmap bitmap;
	public shopAdapter(Context context,List<Shop> list) {
		this.context = context;
		this.list = list;
		inflater = LayoutInflater.from(context);
		bitmap = FinalBitmap.create(context);
		bitmap.configLoadfailImage(R.drawable.ic_launcher);
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
			convertView = inflater.inflate(R.layout.shopitem, null);
			holder.shopName = (TextView) convertView.findViewById(R.id.shopitem_shopname);
			holder.shopYuyue = (TextView) convertView.findViewById(R.id.shopitem_yuyue);
			holder.shop4S = (TextView) convertView.findViewById(R.id.shopitem_4Sjia);
			holder.shopDistance = (TextView) convertView.findViewById(R.id.shopitem_distance);
			holder.shopAddress = (TextView) convertView.findViewById(R.id.shopitem_address);
			holder.shopImg = (ImageView) convertView.findViewById(R.id.shopitem_img);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.shop4S.setText(list.get(position).getShop4S());
		holder.shopAddress.setText(list.get(position).getShopAddress());
		holder.shopDistance.setText(list.get(position).getShopDistance());
		holder.shopDistance.append("km");
		holder.shopName.setText(list.get(position).getShopName());
		holder.shopYuyue.setText(list.get(position).getShopYuyue());

		String imgUrl = list.get(position % list.size()).getShopImg();
		if(imgUrl.equals(holder.shopImg.getTag())){
			bitmap.display(holder.shopImg, imgUrl);
		}else{
			bitmap.display(holder.shopImg, imgUrl);
		}
		holder.shopImg.setTag(imgUrl);
		return convertView;
	}

	class ViewHolder{
		TextView shopName,shopYuyue,shopDistance,shopAddress,shop4S;
		ImageView shopImg;
	}
}
