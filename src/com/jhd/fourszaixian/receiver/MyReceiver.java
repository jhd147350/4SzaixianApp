package com.jhd.fourszaixian.receiver;


import io.yunba.android.manager.YunBaManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class MyReceiver extends BroadcastReceiver{
	public static ReceiveMsg receiveMsgStaitc;
	public static void setReceiveMsg(ReceiveMsg receiveMsg) {
		Log.e("jhd", "MyReceiver->setReceiveMsg");
		receiveMsgStaitc = receiveMsg;	
	}

	@Override
	public void onReceive(Context context, Intent intent) {
		
		// TODO Auto-generated method stub
		if (YunBaManager.MESSAGE_RECEIVED_ACTION.equals(intent.getAction())) {

	        String topic = intent.getStringExtra(YunBaManager.MQTT_TOPIC);
	        String msg = intent.getStringExtra(YunBaManager.MQTT_MSG);
	        
	        //在这里处理从服务器发布下来的消息， 比如显示通知栏， 打开 Activity 等等
	        StringBuilder showMsg = new StringBuilder();
	        showMsg.append("Received message from server: ")
	                .append(YunBaManager.MQTT_TOPIC)
	                .append(" = ")
	                .append(topic)
	                .append(" ")
	                .append(YunBaManager.MQTT_MSG)
	                .append(" = ")
	                .append(msg);
	        
	      //  DemoUtil.showNotifation(context, topic, msg);
	        Log.e("jhd", showMsg.toString());
	        
	        //跳转
//	        Intent i=new Intent(context,SecActivity.class);
//	        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//	        context.startActivity(i);	 
	        if(receiveMsgStaitc==null){
	        	
	        	Log.e("jhd", "receiveMsg==null");
	        }
	        else{
	        	receiveMsgStaitc.notifyKeFu(msg);
	        }
	    }
	}
	public interface ReceiveMsg{
		public void notifyKeFu(String msg);	
	}
	

}
