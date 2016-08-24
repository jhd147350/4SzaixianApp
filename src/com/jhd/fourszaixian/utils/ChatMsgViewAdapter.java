package com.jhd.fourszaixian.utils;

import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.fenghuo.jhdwxt.fourszaixian.R;
import com.jhd.fourszaixian.ui.KefuActivity.ChatMsgEntity;


public class ChatMsgViewAdapter extends BaseAdapter {
	//private Animation slide_bottom_to_top;
	
	//ListView��ͼ��������IMsgViewType����
	public static interface IMsgViewType
	{
		//�Է���������Ϣ
		int IMVT_COM_MSG = 0;
		//�Լ���������Ϣ
		int IMVT_TO_MSG = 1;
	}
	
    private static final String TAG = ChatMsgViewAdapter.class.getSimpleName();
    private List<ChatMsgEntity> data;
    private Context context;  
    private LayoutInflater mInflater;

    public ChatMsgViewAdapter(Context context, List<ChatMsgEntity> data) {
        this.context = context;
        this.data = data;
        //LayoutInflater
        mInflater = LayoutInflater.from(context);
        
      //  slide_bottom_to_top=AnimationUtils.loadAnimation(context, R.anim.slide_bottom_to_top);
      //  slide_bottom_to_top.setDuration(800);
    }

    //��ȡListView�������
    public int getCount() {
        return data.size();
    }

    //��ȡ��
    public Object getItem(int position) {
        return data.get(position);
    }

    //��ȡ���ID
    public long getItemId(int position) {
        return position;
    }

    //��ȡ�������
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
	 	ChatMsgEntity entity = data.get(position);
	 	
	 	if (entity.getMsgType())
	 	{
	 		return IMsgViewType.IMVT_COM_MSG;
	 	}else{
	 		return IMsgViewType.IMVT_TO_MSG;
	 	}
	 	
	}

	//��ȡ���������
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	//��ȡView
    public View getView(int position, View convertView, ViewGroup parent) {
    	
    	ChatMsgEntity entity = data.get(position);
    	boolean isComMsg = entity.getMsgType();
    		
    	ViewHolder viewHolder = null;	
	    if (convertView == null)
	    {
	    	  if (isComMsg)
			  {
	    		  //����ǶԷ���������Ϣ������ʾ����������
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_left, null);
			  }else{
				  //������Լ���������Ϣ������ʾ����������
				  convertView = mInflater.inflate(R.layout.chatting_item_msg_text_right, null);
			  }

	    	  viewHolder = new ViewHolder();
			  viewHolder.tvSendTime = (TextView) convertView.findViewById(R.id.tv_sendtime);
			  viewHolder.tvUserName = (TextView) convertView.findViewById(R.id.tv_username);
			  viewHolder.tvContent = (TextView) convertView.findViewById(R.id.tv_chatcontent);
			  viewHolder.isComMsg = isComMsg;
			  
			 
             // convertView.setAnimation(slide_bottom_to_top);
			//  Log.e("jhd", "��Ӷ���Ч����"+position);
			  convertView.setTag(viewHolder);
			  
			  
	    }else{
	        viewHolder = (ViewHolder) convertView.getTag();
	    }
	    viewHolder.tvSendTime.setText(entity.getDate());
	    viewHolder.tvUserName.setText(entity.getName());
	    viewHolder.tvContent.setText(entity.getText());
	    
	 //   convertView.startAnimation(slide_bottom_to_top);
	    return convertView;
    }
    
    //ͨ��ViewHolder��ʾ�������
    static class ViewHolder { 
        public TextView tvSendTime;
        public TextView tvUserName;
        public TextView tvContent;
        public boolean isComMsg = true;
    }
    
}


