package com.jhd.fourszaixian.utils;

import java.util.Calendar;

public class Weihaoxianxing {
	//http://www.tjits.cn/lh/index.html天津限行尾号方案
	//20151011-20160109限行尾号：
	//建议这个数组从服务器获取，因为这个尾号不是固定不变的
	String data[]=new String[]{"4/9","5/0","1/6","2/7","3/8","不限行","不限行"};

	public String getXianXingWeiHao(){
		Calendar c = Calendar.getInstance();
	//	int year = Integer.valueOf(c.get(Calendar.YEAR));
	//	int month = Integer.valueOf(c.get(Calendar.MONTH));
	//	int day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);//月份需要+1
		int dayOfWeek=Integer.valueOf(c.get(Calendar.DAY_OF_WEEK) -1);//星期需要-1
	//	return data[dayOfWeek-1];
		//这个到周末就会出现数组下标越界，导致程序崩溃
		//TODO 以后有时间解决！
		return data[1];
	}
}
