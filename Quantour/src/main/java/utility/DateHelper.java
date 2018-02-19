package utility;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;


public class DateHelper 
{
		public static String dateToString(Date date)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			if(date != null)
			{
				return dateFormat.format(date);
			}
			else 
			{
				return "yyyy-MM-dd HH:mm:ss";
			}
		}
		
		public static Date localDateToDate(LocalDate localDate) {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try {
				date = dateFormat.parse(localDate.toString());
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return date;
		}
		
		public static LocalDate dateToLocalDate(Date date)
		{
			String dateStr = dateToString(date);
			int year = Integer.parseInt(dateStr.substring(0, 4));
			int month = Integer.parseInt(dateStr.substring(5, 7));
			int day = Integer.parseInt(dateStr.substring(8, 10));
			LocalDate localDate = LocalDate.of(year, month, day);
			return localDate;
		}

		public static Date stringToDate(String dateStr)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			Date date = null;
			try
			{
				date = dateFormat.parse(dateStr);
			}catch(ParseException e){
				e.printStackTrace();
			}
			return date;
		}

		public static Date rollBackOneDay(Date date) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int day = calendar.get(Calendar.DATE);
			calendar.set(Calendar.DATE, day - 1);

			Date date2 = calendar.getTime();

			if (isWeekends(date2)) {
				return rollBackOneDay(date2);
			}
			return date2;
		}
		
		public static boolean isWeekends(Date date) {
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY)
				return true;
			else
				return false;
		}
		
		public static Date add(Date date, int count) {
			Date newDate = date;
			Calendar calendar = Calendar.getInstance();
			if (count > 0) {
				for (int i = 0; i < Math.abs(count); i++) {
					calendar.setTime(newDate);
					calendar.add(Calendar.DATE, 1);
					newDate = calendar.getTime();
					while (isWeekends(newDate)) {
						calendar.add(Calendar.DATE, 2);
						newDate = calendar.getTime();
					}
				}
			} else {
				for (int i = 0; i < Math.abs(count); i++) {
					calendar.setTime(date);
					calendar.add(Calendar.DATE, -1);
					newDate = calendar.getTime();
					while (isWeekends(newDate)) {
						calendar.add(Calendar.DATE, -2);
						newDate = calendar.getTime();
					}
				}
			}
			return newDate;
		}

}
