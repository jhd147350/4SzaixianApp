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
import com.jhd.fourszaixian.entity.Info;


public class infoAdapter extends BaseAdapter{
    
	private List<Info> infoList;
	private Context context;
	private LayoutInflater inflater;
	 private FinalBitmap bitmap;
	public infoAdapter(Context context,List<Info> infoList) {
		this.context = context;
		this.infoList = infoList;
		inflater = LayoutInflater.from(context);
		bitmap = FinalBitmap.create(context);
		bitmap.configLoadfailImage(R.drawable.ic_launcher);
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return infoList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return infoList.get(position);
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
		    convertView = inflater.inflate(R.layout.zixun_acticle_item, null);
		    holder.content = (TextView) convertView.findViewById(R.id.zixun_allacticle_content);
		    holder.time = (TextView) convertView.findViewById(R.id.zixun_allacticle_time);
		    holder.title = (TextView) convertView.findViewById(R.id.zixun_allacticle_title);
		    holder.img = (ImageView) convertView.findViewById(R.id.zixun_allacticle_img);
		    convertView.setTag(holder);
		}else{
			
			holder = (ViewHolder) convertView.getTag();
		}
		holder.content .setText(infoList.get(position).getInfoContent());
		holder.time.setText(infoList.get(position).getInfoTime());
		holder.title.setText(infoList.get(position).getInfoTitle());
		String imgUrl = infoList.get(position%infoList.size()).getInfoImg();
		if(imgUrl.equals(holder.img.getTag())){
			bitmap.display(holder.img, imgUrl);
		}else{
			bitmap.display(holder.img, imgUrl);
		}
		holder.img.setTag(imgUrl);
		return convertView;
	}
}
class ViewHolder{
	TextView title,content,time;
	ImageView img;
}