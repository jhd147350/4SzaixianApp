package com.jhd.fourszaixian.utils;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class TimeTranslation {

	public TimeTranslation() {
		
		// TODO Auto-generated constructor stub
	}
	public Date StringToDate(String s){
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
		Date date = null;
		try {
			date = formatter.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}
	
	
	public String DateToString(Date date){
		String s = null;
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd ");
		s = formatter.format(date);
		return s;
	}
	
	public String CalendarToString(Calendar calendar){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String dateStr = df.format(calendar.getTime());
		return dateStr;
	}
	public String CalendarToString2(Calendar calendar){
		SimpleDateFormat df = new SimpleDateFormat("yyyyƒÍMM‘¬dd»’");
		String dateStr = df.format(calendar.getTime());
		return dateStr;
	}
	public Calendar stringToCalendar(String str){
		
		Date date=StringToDate(str);
		Calendar cal=Calendar.getInstance();
		cal.setTime(date);
		return cal;
	}
}
