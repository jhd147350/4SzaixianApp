package com.jhd.fourszaixian.utils;

import java.util.Calendar;

public class Weihaoxianxing {
	//http://www.tjits.cn/lh/index.html�������β�ŷ���
	//20151011-20160109����β�ţ�
	//�����������ӷ�������ȡ����Ϊ���β�Ų��ǹ̶������
	String data[]=new String[]{"4/9","5/0","1/6","2/7","3/8","������","������"};

	public String getXianXingWeiHao(){
		Calendar c = Calendar.getInstance();
	//	int year = Integer.valueOf(c.get(Calendar.YEAR));
	//	int month = Integer.valueOf(c.get(Calendar.MONTH));
	//	int day = Integer.valueOf(c.get(Calendar.DAY_OF_MONTH) + 1);//�·���Ҫ+1
		int dayOfWeek=Integer.valueOf(c.get(Calendar.DAY_OF_WEEK) -1);//������Ҫ-1
	//	return data[dayOfWeek-1];
		//�������ĩ�ͻ���������±�Խ�磬���³������
		//TODO �Ժ���ʱ������
		return data[1];
	}
}
