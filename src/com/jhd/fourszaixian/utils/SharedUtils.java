package com.jhd.fourszaixian.utils;

import android.content.Context;
import android.content.SharedPreferences.Editor;
import android.util.Log;

//实现保存当前用户到文件
public class SharedUtils {
	private static final String FILE_NAME="4szaixian";
	private static final String MODE_NAME="welcome";
	private static final String HASLOGIN="hasLogin";
	
	private static final String MYCARNUM="mycarnum";
	private static final String MYCARBRAND="mycarbrand";
	private static final String MYCARSUBS="mycarsubs";
	private static final String MYCARMODEL="mycarmodel";
	//获取boolean值
//	public static boolean isFirstStart(Context context)
//	{
//		Log.e("jhd", "SharedUtils-isFirstStart   :  "+context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, true));
//		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, true);
//	}
//	//写入
//	public static void putIsFirstStart(Context context,boolean isFirst){
//		Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
//		editor.putBoolean(MODE_NAME, isFirst);
//		editor.commit();
//		Log.e("jhd", "SharedUtils-put    :  "+isFirst);
//	}


	//写入一个String类型的数据phonenum
	public static void putPhonenum(Context context,String phonenum)
	{
		Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
		editor.putString("phonenum", phonenum);
		editor.commit();
	}
	//获取String类型的数据
	public static String getPhonenum(Context context)
	{	
		return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString("phonenum", "获取为空");	
	}
	
	
	
	//获取boolean值
		public static boolean hasLogin(Context context)
		{

			Log.e("jhd", "SharedUtils-isFirstStart   :  "+context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(MODE_NAME, true));
			return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(HASLOGIN, false);

		}
		//写入
		public static void puthasLogin(Context context,boolean hasLogin){
			Editor editor = context.getSharedPreferences(FILE_NAME, Context.MODE_APPEND).edit();
			editor.putBoolean(HASLOGIN, hasLogin);
			editor.commit();
			Log.e("jhd", "SharedUtils-put    :  "+hasLogin);
		}
}
