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

		assertEquals("201911", DateUtils.transformDateToYYYYMM(date1));
		assertEquals("000001", DateUtils.transformNumString(1));
		assertEquals("000010", DateUtils.transformNumString(10));
		assertEquals("000100", DateUtils.transformNumString(100));
		assertEquals("001000", DateUtils.transformNumString(1000));
		assertEquals("010000", DateUtils.transformNumString(10000));
		assertEquals("100000", DateUtils.transformNumString(100000));

		assertEquals(dateBegin, DateUtils.getTimesmorning(DateUtils.firstDateInMonth(date2)));
		assertEquals(dateEnd, DateUtils.getTimesevening(DateUtils.lastDateInMonth(date2)));
	}
}
