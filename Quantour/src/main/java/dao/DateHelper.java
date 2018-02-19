package dao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;

public class DateHelper {

	public static Date stringToDate(String time) {
	
		SimpleDateFormat myFmt1=new SimpleDateFormat("MM/dd/yy"); 
		try {
			return myFmt1.parse(time);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}
	
	public static LocalDate dateToLocalDate(Date date) {
		
		LocalDate localDate = ZonedDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault()).toLocalDate();  

		return localDate;
	}

	
}
