package test01;

import java.text.ParseException;
import java.util.Date;

import org.joda.time.DateTime;

import com.mmall.util.DateTimeUtil;

public class Test01 {
	
	public static void main(String[] args) throws ParseException {
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		System.out.println(sdf.format(new Date()));
//		Date date = sdf.parse("2018-09-07 12:12:12");
//		long time = date.getTime();
//		System.out.println(time);
//		System.out.println(sdf.parse("2018-09-07 12:12:12"));
		
		Date date = new Date();
		System.out.println(date);
		DateTime datetime = new DateTime(date);
		System.out.println(datetime);
		System.out.println(datetime.toString(DateTimeUtil.STANDARD_FORMAT));
	}
}
