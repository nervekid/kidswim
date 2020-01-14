package com.kite.common.utils.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class DateUtilsTest {

	@Test
    public void execute() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String dateString1 = "2019-11-30 00:00:00";
		Date date1 = new Date();
		try {
			date1 = sdf.parse(dateString1);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		String dateString2 = "2019-11-15 12:30:00";
		Date date2 = new Date();
		try {
			date2 = sdf.parse(dateString2);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		String dateBeginStr = "2019-11-01 00:00:00";
		Date dateBegin = new Date();
		try {
			dateBegin = sdf.parse(dateBeginStr);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

		String dateEndStr = "2019-11-30 23:59:59";
		Date dateEnd = new Date();
		try {
			dateEnd = sdf.parse(dateEndStr);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}


		String weekBeginStr1 = "2019-11-01 00:00:00";
		Date weekBegin1 = new Date();
		try {
			weekBegin1 = sdf.parse(weekBeginStr1);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}


		String weekendStr1 = "2019-11-30 23:59:59";
		Date weekend1 = new Date();
		try {
			weekend1 = sdf.parse(weekendStr1);
		}
		catch (ParseException e) {
			e.printStackTrace();
		}

//		assertEquals("201911", DateUtils.transformDateToYYYYMM(date1));
//		assertEquals("000001", DateUtils.transformNumString(1));
//		assertEquals("000010", DateUtils.transformNumString(10));
//		assertEquals("000100", DateUtils.transformNumString(100));
//		assertEquals("001000", DateUtils.transformNumString(1000));
//		assertEquals("010000", DateUtils.transformNumString(10000));
//		assertEquals("100000", DateUtils.transformNumString(100000));

//		assertEquals(dateBegin, DateUtils.getTimesmorning(DateUtils.firstDateInMonth(date2)));
//		assertEquals(dateEnd, DateUtils.getTimesevening(DateUtils.lastDateInMonth(date2)));
		SimpleDateFormat spd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date time11 = new Date();
        try {
        	time11 = spd.parse("2019-12-01 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time12 = new Date();
        try {
        	time12 = spd.parse("2019-12-31 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time13 = new Date();
        try {
        	time13 = spd.parse("2019-11-15 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time14 = new Date();
        try {
        	time14 = spd.parse("2019-12-31 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time15 = new Date();
        try {
        	time15 = spd.parse("2020-01-01 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time16 = new Date();
        try {
        	time16 = spd.parse("2020-02-29 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(5, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 1));
        assertEquals(5, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 2));
        assertEquals(4, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 3));
        assertEquals(4, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 4));
        assertEquals(4, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 5));
        assertEquals(4, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 6));
        assertEquals(5, DateUtils.calculateTheNumberOfTimesFfTheWeek(time11, time12, 0));

        assertEquals(7, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 1));
        assertEquals(7, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 2));
        assertEquals(6, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 3));
        assertEquals(6, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 4));
        assertEquals(7, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 5));
        assertEquals(7, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 6));
        assertEquals(7, DateUtils.calculateTheNumberOfTimesFfTheWeek(time13, time14, 0));

        assertEquals(8, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 1));
        assertEquals(8, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 2));
        assertEquals(9, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 3));
        assertEquals(9, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 4));
        assertEquals(9, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 5));
        assertEquals(9, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 6));
        assertEquals(8, DateUtils.calculateTheNumberOfTimesFfTheWeek(time15, time16, 0));


        Date time1 = new Date();
        try {
        	time1 = spd.parse("2019-12-02 00:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time2 = new Date();
        try {
        	time2 = spd.parse("2019-12-03 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time3 = new Date();
        try {
        	time3 = spd.parse("2019-12-04 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time4 = new Date();
        try {
        	time4 = spd.parse("2019-12-05 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time5 = new Date();
        try {
        	time5 = spd.parse("2019-12-06 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time6 = new Date();
        try {
        	time6 = spd.parse("2019-12-07 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time7 = new Date();
        try {
        	time7 = spd.parse("2019-12-08 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(1, com.kite.common.utils.date.DateUtils.getWeekByDate(time1));
        assertEquals(2, com.kite.common.utils.date.DateUtils.getWeekByDate(time2));
        assertEquals(3, com.kite.common.utils.date.DateUtils.getWeekByDate(time3));
        assertEquals(4, com.kite.common.utils.date.DateUtils.getWeekByDate(time4));
        assertEquals(5, com.kite.common.utils.date.DateUtils.getWeekByDate(time5));
        assertEquals(6, com.kite.common.utils.date.DateUtils.getWeekByDate(time6));
        assertEquals(0, com.kite.common.utils.date.DateUtils.getWeekByDate(time7));


        Date time8 = new Date();
        try {
        	time8 = spd.parse("2019-12-03 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }


        Date time9 = new Date();
        try {
        	time9 = spd.parse("2019-12-10 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time10 = new Date();
        try {
        	time10 = spd.parse("2019-12-17 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date time111 = new Date();
        try {
        	time111 = spd.parse("2019-12-24 12:00:00");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        assertEquals(time9, com.kite.common.utils.date.DateUtils.getPreNumDate(time8, 7));
        assertEquals(time10, com.kite.common.utils.date.DateUtils.getPreNumDate(time9, 7));
        assertEquals(time111, com.kite.common.utils.date.DateUtils.getPreNumDate(time10, 7));




        Date pre1 = new Date();
        try {
        	pre1 = spd.parse("2020-01-01 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date pre1Taget = new Date();
        try {
        	pre1Taget = spd.parse("2020-01-06 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }

        Date last1 = new Date();
        try {
        	last1 = spd.parse("2020-02-29 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        Date last1Taget = new Date();
        try {
        	last1Taget = spd.parse("2020-02-24 15:25:33");
        }
        catch (ParseException e) {
            e.printStackTrace();
        }
        assertEquals(pre1Taget, com.kite.common.utils.date.DateUtils.getNextWekkByDateAndWeek(pre1, 1));
        assertEquals(last1Taget, com.kite.common.utils.date.DateUtils.getPreWekkByDateAndWeek(last1, 1));




	}
}
