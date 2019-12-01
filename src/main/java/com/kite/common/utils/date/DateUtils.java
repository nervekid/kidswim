package com.kite.common.utils.date;

import java.util.Calendar;
import java.util.Date;

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
	     String ym = y + m;
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

}
