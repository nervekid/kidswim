package com.kite.common.utils.date;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import com.kite.common.utils.DateUtlis;
import com.kite.common.utils.StringUtils;

public class DateUtils {


	/**
	 * 将日期转换为YYYYMM
	 * @param date
	 * @return
	 */
	public static String transformDateToYYYYMM(Date date) {
		 Calendar calendar=Calendar.getInstance();
	     calendar.setTime(date);
	     String y = String.valueOf(calendar.get(Calendar.YEAR));
	     String m = String.valueOf(calendar.get(Calendar.MONTH) + 1);
	     String ym = "";
	     if (Integer.parseInt(m) < 10) {
	    	 m = "0" + m;
	     }
	     ym = y + m;
	     return ym;
	}

	/**
	 * 将对当前整形转换为十万位，带零
	 * @param i
	 * @return
	 */
	public static String transformNumString(int i) {
		String iStr = String.valueOf(i);
		if(iStr.length() == 1) {
			return "000000" + iStr;
		}
		else if(iStr.length() == 2) {
			return "00000" + iStr;
		}
		else if(iStr.length() == 3) {
			return "0000" + iStr;
		}
		else if(iStr.length() == 4) {
			return "000" + iStr;
		}
		else if(iStr.length() == 5) {
			return "00" + iStr;
		}
		else if(iStr.length() == 6) {
			return "0" + iStr;
		}
		else if(iStr.length() == 7) {
			return iStr;
		}
		else {
			return iStr;
		}
	}


	/**
	 * 将对当前整形转换为千位，带零
	 * @param i
	 * @return
	 */
	public static String transformThousandBitNumString(int i) {
		String iStr = String.valueOf(i);
		if(iStr.length() == 1) {
			return "000" + iStr;
		}
		else if(iStr.length() == 2) {
			return "00" + iStr;
		}
		else if(iStr.length() == 3) {
			return "0" + iStr;
		}
		else if(iStr.length() == 4) {
			return iStr;
		}
		else {
			return iStr;
		}
	}

	/**
	 * 将对当前整形转换为百位，带零
	 * @param i
	 * @return
	 */
	public static String transformHundredBitNumString(int i) {
		String iStr = String.valueOf(i);
		if(iStr.length() == 1) {
			return "00" + iStr;
		}
		else if(iStr.length() == 2) {
			return "0" + iStr;
		}
		else if(iStr.length() == 3) {
			return iStr;
		}
		else {
			return iStr;
		}
	}

	/**
	 * 获取指定日期当天开始时间
	 * @param date
	 * @return
	 */
    public static Date getTimesmorning(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
	 * 获取指定日期当天结束时间
	 * @param date
	 * @return
	 */
    public static Date getTimesevening(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, 23);
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }

    /**
     * 指定日期当月第一天
     * @param date
     * @return
     */
    public static Date firstDateInMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 0);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        Date firstday = cal.getTime();
        return firstday;

    }

    /**
     * 指定日期当月最后一天
     * @param date
     * @return
     */
    public static Date lastDateInMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal = Calendar.getInstance();
        cal.add(Calendar.MONTH, 1);
        cal.set(Calendar.DAY_OF_MONTH, 0);
        Date lastday = cal.getTime();
        return lastday;
    }

    /**
     *  获取当月的第一天
     * @return
     * @throws ParseException
     */
    public static Date getCurrentMonthFirstDate() throws ParseException {
        Calendar cale =Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        cale = Calendar.getInstance();
        cale.add(Calendar.MONTH, 0);
        cale.set(Calendar.DAY_OF_MONTH, 1);
        String  firstday = format.format(cale.getTime());
        return format.parse(firstday);
    }

    /**
     * 获取指定日期当天最后时刻
     * @param thatDay
     * @return
     */
    public static Date getLastTimeDate(Date thatDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        return c.getTime();
    }

    /**
     * 获取指定日期当天开始时刻
     * @param thatDay
     * @return
     */
    public static Date getFistTimeDate(Date thatDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.set(Calendar.HOUR_OF_DAY, 00);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取指定日期中午十二点时刻
     * @param thatDay
     * @return
     */
    public static Date getNoon12OclockTimeDate(Date thatDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.set(Calendar.HOUR_OF_DAY, 12);
        c.set(Calendar.MINUTE, 00);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取指定日期中午十二点时刻
     * @param thatDay
     * @return
     */
    public static Date getNoon1330OclockTimeDate(Date thatDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.set(Calendar.HOUR_OF_DAY, 13);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取上班打卡时间 早上八点30分
     * @param thatDay
     * @return
     */
    public static Date getCheckInTimeDate(Date thatDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.set(Calendar.HOUR_OF_DAY, 8);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取下班打卡时间 下午五点30分
     * @param thatDay
     * @return
     */
    public static Date getCheckOutTimeDate(Date thatDay) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.set(Calendar.HOUR_OF_DAY, 17);
        c.set(Calendar.MINUTE, 30);
        c.set(Calendar.SECOND, 00);
        return c.getTime();
    }

    /**
     * 获取某一天所在月的最后一天日期
     * @param thatDay 制定日期
     * @return
     */
    public static Date getLastDayInMonth(Date thatDay) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(thatDay);
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.roll(Calendar.DAY_OF_MONTH, -1);
        return cal.getTime();
    }

    /**
     *  获取下个月的第一天
     * @return
     * @throws ParseException
     */
    public static Date getNextMonthFirstDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        System.out.println(c.getTime());
        c.set(Calendar.MONTH, c.get(Calendar.MONTH)+1);
        c.set(Calendar.DAY_OF_MONTH, 1);
        String  firstday = format.format(c.getTime());
        return format.parse(firstday);
    }

    /**
     * 获取上个月的第一天
     * @return
     * @throws ParseException
     */
    public static Date getPreviousMonthFirstDate() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar   cal_1=Calendar.getInstance();//获取当前日期
        cal_1.add(Calendar.MONTH, -1);
        cal_1.set(Calendar.DAY_OF_MONTH,1);//设置为1号,当前日期既为本月第一天
        String  firstDay = format.format(cal_1.getTime());
        return format.parse(firstDay);
    }
	/**
	 * 获取上个月的第一天
	 * @return
	 * @throws ParseException
	 */
	public static String  getPreviousMonthFirstDateStr() throws ParseException {
		LocalDate now = LocalDate.now();
		LocalDate lastLocalDate = now.minusMonths(1);
		LocalDate with = lastLocalDate.with(TemporalAdjusters.firstDayOfMonth());
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = df.format(with);
		return format;
	}

	/**
	 * 获取当前月的最后一天
	 * @return
	 * @throws ParseException
	 */
	public static String  getNowMonthlastDateStr() throws ParseException {
		LocalDate now = LocalDate.now();
		LocalDate with = now.with(TemporalAdjusters.lastDayOfMonth());
		DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String format = df.format(with);
		return format;
	}

    /**
     * 获取提前多少个月
     * @param monty
     * @return
     */
	public static Date getBeforeMonth(int monty) throws ParseException{
		Calendar c = Calendar.getInstance();
		c.add(Calendar.MONTH, -monty);
		return c.getTime();
	}

	/**
	 * 比较2个日期的大小
	 * @param dateA
	 * @param dateB
	 * @return
	 */
	public static int compare(Date dateA,Date dateB)  throws ParseException{
		Calendar calendarA  = Calendar.getInstance();
		calendarA.setTime(dateA);
		Calendar calendarB  = Calendar.getInstance();
		calendarB.setTime(dateB);
		return calendarA.compareTo(calendarB);
	}

	/**
	 * 日期格式化
	 *
	 * @param date
	 * @param
	 * @return
	 */
	public static String getDateFormat(Date date, String formatStr) {
		if (date == null || StringUtils.isEmpty(formatStr)) {
			return null;
		}
		if (StringUtils.isNotBlank(formatStr)) {
			return new SimpleDateFormat(formatStr).format(date);
		}
		return null;
	}

	/**
	 * 日期格式化yyyy-MM-dd
	 * @param date
	 * @return
	 */
	public static Date formatDate(String date, String format) {
		if (StringUtils.isEmpty(date) || StringUtils.isEmpty(format)) {
			return null;
		}

		try {
			return new SimpleDateFormat(format).parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}


	/**
	 * 2018-01 - 2018-06
	 */
	public static Map<String,String> getBeginAndEndTimeType1 (String str) {

		Map<String,String> map = new HashMap<String,String>();

		String[] arr = str.split(" - ");
		map.put("begin", arr[0].replace("-", ""));
		map.put("end", arr[1].replace("-", ""));

		return map;
	}

	/**
     * 将日期转为YYYYmm格式，如201709
     * @return
     */
    public static String changeDateToYYYYmm(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH) + 1;
        String yearStr = String.valueOf(year);
        String monthStr;
        if (month < 10) {
            monthStr = String.valueOf("0" + month);
        }
        else {
            monthStr = String.valueOf(month);
        }
        return yearStr + monthStr;
    }

    /**
     * 将日期转为YYYY格式，如2019
     * @return
     */
    public static String changeDateToYYYY(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int year = c.get(Calendar.YEAR);
        String yearStr = String.valueOf(year);
        return yearStr;
    }

    /**
     * 将YYYYmm形式转化为日期
     * @param planMonth
     * @return
     */
    public static Date changeYYYYmmToDate(String planMonth) {
        SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
        String yeah = planMonth.substring(0, 4);
        String month = planMonth.substring(4, planMonth.length());
        String str = yeah + "-" + month + "-" + "01";
        Date date = new Date();
        try {
            date = spd.parse(str);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将YYYY形式转化为当前指定年份的第一个月第一天
     * @param planMonth
     * @return
     */
    public static Date changeYYYYToFirstDate(String planMonth) {
        SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
        String yeah = planMonth.substring(0, 4);
        String str = yeah + "-" + "01" + "-" + "01";
        Date date = new Date();
        try {
            date = spd.parse(str);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 将YYYY形式转化为当前指定年份的最后一个月月最后一天
     * @param planMonth
     * @return
     */
    public static Date changeYYYYToLastDate(String planMonth) {
        SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd");
        String yeah = planMonth.substring(0, 4);
        String str = yeah + "-" + "12" + "-" + "31";
        Date date = new Date();
        try {
            date = spd.parse(str);
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    /**
     * 获取某一天所在月的第一天日期
     * @param thatDay 指定日期
     * @return
     */
    public static Date getFirstDayInMonth(Date thatDay) {
        GregorianCalendar gc = (GregorianCalendar) Calendar.getInstance();
        gc.setTime(thatDay);
        gc.set(Calendar.DAY_OF_MONTH, 1);
        return gc.getTime();
    }

    /**
     * 获取指定日期过去指定天数的日期
     * @param thatDay 指定日期
     * @param num 指定天数
     * @return
     */
    public static Date getLastNumDate(Date thatDay, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.add(Calendar.DATE, -num);
        Date d = c.getTime();
        return d;
    }

    /**
     * 获取指定日期未来指定天数的日期
     * @param thatDay 指定日期
     * @param num 指定天数
     * @return
     */
    public static Date getPreNumDate(Date thatDay, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.add(Calendar.DATE, +num);
        Date d = c.getTime();
        return d;
    }

    /**
     * 10位时间戳转Date
     * @param time
     * @return
     */
    public static Date timestampToDate(String time) {
        long temp = Long.valueOf(time) * 1000;
        return new Date(temp);
    }

    public static void main(String[] args) {
        Date a = new Date();timestampToDate("1536222378");
        System.out.println(a);
    }

    public static String dateToStamp(Date date) {
        String res;
        long ts = date.getTime() / 1000 ;
        res = String.valueOf(ts);
        return res;
    }

    /**
     * 计算两个日期之间相差的月数
     * @param date1 <String>日期一
     * @param date2 <String>日期2
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2) throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));

        result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);

        return result == 0 ? 1 : Math.abs(result);

    }

    /**
     * 计算两个日期之间相差的天数
    * @param date1 <String>
    * @param date2 <String>
    * @return int
    * @throws ParseException
    */
   public static int getDateSpace(String date1, String date2) throws ParseException {

       Calendar calst = Calendar.getInstance();;
       Calendar caled = Calendar.getInstance();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

       calst.setTime(sdf.parse(date1));
       caled.setTime(sdf.parse(date2));

        //设置时间为0时
        calst.set(Calendar.HOUR_OF_DAY, 0);
        calst.set(Calendar.MINUTE, 0);
        calst.set(Calendar.SECOND, 0);
        caled.set(Calendar.HOUR_OF_DAY, 0);
        caled.set(Calendar.MINUTE, 0);
        caled.set(Calendar.SECOND, 0);
       //得到两个日期相差的天数
       int days = ((int)(caled.getTime().getTime()/1000)-(int)(calst.getTime().getTime()/1000))/3600/24;

       return days + 1;
   }

   /**
    * 判断是否小于13:30
    */
   @SuppressWarnings("deprecation")
   public static boolean judgeDateTimeLessThan1330oClock(Date date) throws ParseException {
	   boolean flag = false;
	   int hours = date.getHours();
	   int mins = date.getMinutes();
	   String str = String.valueOf(hours) + String.valueOf(mins);
	   int strInt = Integer.parseInt(str);
	   if (strInt < 1330) {
		   flag = true;
	   }
	   return flag;
   }

   /**
    * 判断是否是上班卡
    * 返回真：上班卡，返回否：下班卡
    */
   @SuppressWarnings("deprecation")
   public static boolean judgeCheckInCard(Date date) throws ParseException {
	   boolean flag = false;
	   int hours = date.getHours();
	   int mins = date.getMinutes();
	   String minsStr = "";
	   if (0<= mins && mins <= 9) {
		   minsStr = "0" + String.valueOf(mins);
	   }
	   else {
		   minsStr = String.valueOf(mins);
	   }
	   String str = String.valueOf(hours) + minsStr;
	   int strInt = Integer.parseInt(str);
	   //在00:00至06:00或者13:31至23:59属于下班卡
	   if ((0 <= strInt && strInt <= 6) || (1331 <= strInt && strInt <= 2359)) {
		   flag = false;
	   }
	   //否则属于下班卡
	   else {
		   flag = true;
	   }

	   return flag;
   }

   /**
    * 判断是否是异常卡
    * 返回真：异常卡，返回否：正常卡
    */
   @SuppressWarnings("deprecation")
   public static boolean judgeAbnormalCheckCard(Date date) throws ParseException {
	   boolean flag = false;
	   int hours = date.getHours();
	   int mins = date.getMinutes();
	   String str = String.valueOf(hours) + String.valueOf(mins);
	   int strInt = Integer.parseInt(str);
	   //在00:00至06:00或者13:31至23:59属于下班卡
	   if (830 <= strInt && strInt <= 1730) {
		   flag = false;
	   }
	   //否则属于下班卡
	   else {
		   flag = true;
	   }

	   return flag;
   }

   /**
    * 获取指定日期当月天数
    * @param date 指定日期
    * @return
    */
   @SuppressWarnings("static-access")
   public static int getDaysOfMonth(Date date) {
	   Calendar cal = Calendar.getInstance();
	   cal.setTime(date);
	   return cal.getActualMaximum(cal.DAY_OF_MONTH);
   }

   /**
    * 判断两个日期是否相同
    * @param date1
    * @param date2
    * @return
    */
   public static boolean isSameDate(Date date1, Date date2) {
	   try {
	   	   Calendar cal1 = Calendar.getInstance();
           cal1.setTime(date1);
           Calendar cal2 = Calendar.getInstance();
           cal2.setTime(date2);
           boolean isSameYear = cal1.get(Calendar.YEAR) == cal2.get(Calendar.YEAR);
           boolean isSameMonth = isSameYear && cal1.get(Calendar.MONTH) == cal2.get(Calendar.MONTH);
           boolean isSameDate = isSameMonth && cal1.get(Calendar.DAY_OF_MONTH) == cal2.get(Calendar.DAY_OF_MONTH);
           return isSameDate;
   	   }
       catch (Exception e) {
       }

	   return false;
   	}

   /**
    * 两个时间相差距离多少天多少小时多少分多少秒
    * @param str1 时间参数 1 格式：1990-01-01 12:00:00
    * @param str2 时间参数 2 格式：2009-01-01 12:00:00
    * @return long[] 返回值为：{天, 时, 分, 秒}
    */
   public static double getDistanceTimes(Date starttime, Date endtime) throws ParseException {
	   long day = 0;
	   long hour = 0;
	   long time1 = starttime.getTime();
	   long time2 = endtime.getTime();
	   long diff ;
	   if(time1<time2) {
	       diff = time2 - time1;
	   } else {
	       diff = time1 - time2;
	   }
	   day = diff / (24 * 60 * 60 * 1000);
	   hour = (diff / (60 * 60 * 1000) - day * 24);
	   String mStr = String.valueOf(((diff / (60 * 1000)) - day * 24 * 60 - hour * 60));
	   String m = Chufa(Integer.parseInt(mStr), 60);
	   float mm = Float.parseFloat(m);
	   float hourmin = hour + mm;
       return hourmin;
   }

   public static String Chufa(int a,int b) {
		DecimalFormat dF = new DecimalFormat("0.00");
		return dF.format((float)a/b);
	}

   /**
    * 计算一段时间段内有多少个周几
    * @param start 开始时间
    * @param end 结束时间
    * @param a 星期几
    * @return
    */
   public static int calculateTheNumberOfTimesFfTheWeek(Date start, Date end, int a){
       long sunDay = 0;//计数
       try{
           Calendar startDate = Calendar.getInstance(); //开始时间
           startDate.setTime(start);

           Calendar endDate = Calendar.getInstance();//结束时间
           endDate.setTime(end);

           int SW = startDate.get(Calendar.DAY_OF_WEEK)-1;//开始日期是星期几
           int EW = endDate.get(Calendar.DAY_OF_WEEK)-1;//结束日期是星期几

           long diff = endDate.getTimeInMillis()-startDate.getTimeInMillis();
           long days = diff / (1000 * 60 * 60 * 24);//给定时间段内一共有多少天
           long w = Math.round(Math.ceil(((days+SW+(7-EW))/7.0)));//给定时间内，共有多少个星期
           sunDay = w;//总的星期几统计数
           if(a<SW)//给定的星期几小于起始日期的星期几，需要减少一天
               sunDay--;
           if(a>EW)//给定的星期几大于结束日期的星期几，需要减少一天
               sunDay--;
       }catch(Exception se){
           se.printStackTrace();
       }
       int i = Integer.parseInt(String.valueOf(sunDay));
       return i;
   }

   /**
    * 将日期转化为星期几
    * @param date
    * @return
    */
   public static int getWeekByDate(Date date) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       int dayForWeek = 0;
       if (c.get(Calendar.DAY_OF_WEEK) == 1) {
           dayForWeek = 7;
       }
       else {
           dayForWeek = c.get(Calendar.DAY_OF_WEEK) - 1;
       }
       if (dayForWeek == 7) {
    	   return 0;
       }
       return dayForWeek;
   }

   /**
    * 选择指定日期的下一个星期几
    * 0代表星期日 1-6代表星期一至星期六
    * @param date
    * @param week
    * @return
    */
   public static Date getNextWekkByDateAndWeek(Date date, int week) {
	   for (int i = 0; i < 7; i++) {
		   Date tarDate = DateUtils.getPreNumDate(date, i);
		   if (DateUtils.getWeekByDate(tarDate) == week) {
			   return tarDate;
		   }
	   }
	   return date;
   }

   /**
    * 获取指定日期的上一个星期几
    * 0代表星期日 1-6代表星期一至星期六
    * @param date
    * @param week
    * @return
    */
   public static Date getPreWekkByDateAndWeek(Date date, int week) {
	   for (int i = 0; i < 7; i++) {
		   Date tarDate = DateUtils.getLastNumDate(date, i);
		   if (DateUtils.getWeekByDate(tarDate) == week) {
			   return tarDate;
		   }
	   }
	   return date;
   }

   /**
    * 将日期转为HHmm格式，如1208
    * @return
    */
   public static String changeDateToHHmm(Date date) {
       Calendar c = Calendar.getInstance();
       c.setTime(date);
       int year = c.get(Calendar.HOUR_OF_DAY);
       int month = c.get(Calendar.MINUTE);
       String yearStr = String.valueOf(year);
       String monthStr;
       if (month < 10) {
           monthStr = String.valueOf("0" + month);
       }
       else {
           monthStr = String.valueOf(month);
       }
       return yearStr + monthStr;
   }

}
