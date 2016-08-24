package com.jhd.fourszaixian.ui;


import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttToken;

import io.yunba.android.manager.YunBaManager;

import android.app.Application;
import android.util.Log;

import com.baidu.mapapi.SDKInitializer;

public class MyApplication extends Application {
	private String TAG="jhd";
	//String TAG = "qqq";
	@Override
	public void onCreate() {
		super.onCreate();
		
		// ��ʹ�� SDK �����֮ǰ��ʼ�� context ��Ϣ������ ApplicationContext
		SDKInitializer.initialize(this);
		
		
		//��ʼ���ư�����	
		YunBaManager.start(getApplicationContext());
        YunBaManager.subscribe(getApplicationContext(), new String[]{"jhd"}, new IMqttActionListener() {

            @Override
            public void onSuccess(IMqttToken arg0) {
                Log.e("jhd", "Subscribe topic succeed");
            }

            @Override
            public void onFailure(IMqttToken arg0, Throwable arg1) {
                Log.e("jhd", "Subscribe topic failed") ;
            }
        });
        
        
        Log.e(TAG, "MyApplication onCreate") ;
        
//        
//		//�ư�
//		  YunBaManager.start(getApplicationContext());
//
//	        YunBaManager.subscribe(getApplicationContext(), new String[]{"aa"}, new IMqttActionListener() {
//
//	            @Override
//	            public void onSuccess(IMqttToken arg0) {
//	                Log.d(TAG, "Subscribe topic succeed");
//	            }
//
//	            @Override
//	            public void onFailure(IMqttToken arg0, Throwable arg1) {
//	                Log.d(TAG, "Subscribe topic failed" );
//	            }
//	        });

	}

}