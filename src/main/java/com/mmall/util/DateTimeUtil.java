package com.mmall.util;

import java.util.Date;

import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

public class DateTimeUtil {
	
	public static final String STANDARD_FORMAT="yyyy-MM-dd HH:mm:ss";
	
	public static Date strToDate(String dateTimeStr,String formatStr){
		DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(formatStr);
		DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
		return dateTime.toDate();
	}
	public static String dateToString(Date date,String formatStr){
		if(date==null){
			return StringUtils.EMPTY;
		}
		DateTime dateTime=new DateTime(date);
		return dateTime.toString(formatStr);
	}
	public static Date strToDate(String dateTimeStr){
		DateTimeFormatter dateTimeFormatter=DateTimeFormat.forPattern(STANDARD_FORMAT);
		DateTime dateTime=dateTimeFormatter.parseDateTime(dateTimeStr);
		return dateTime.toDate();
	}
	public static String dateToString(Date date){
		if(date==null){
			return StringUtils.EMPTY;
		}
		DateTime dateTime=new DateTime(date);
		return dateTime.toString(STANDARD_FORMAT);
	}
	public static void main(String[] args) {
//		System.out.println(dateToString(new Date()));
//		System.out.println(strToDate("2018-09-02 21:57:25"));
		LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter format = DateTimeFormat.forPattern("yyyy年MM月dd日 hh:mm a");
        String nowStr = now.toString(format);
        System.out.println(nowStr);

	}
}
