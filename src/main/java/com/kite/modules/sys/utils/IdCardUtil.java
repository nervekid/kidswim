package com.kite.modules.sys.utils;


import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Pattern;

public class IdCardUtil {

	/**
	 * 身份证工具类,由于现在使用的是二代身份证，所以只验证18位<br>
	 * 身份证号码中涉及到行政区划代码，代码不能验证其是否真实存在，所以我们只能验证身份号码逻辑正确性，
	 * 并不能验证身份证号码是否真实存在<br>
	 *
	 *  ①地址码：（前6位）所在县（市、旗、区）的行政区划代码
	 *  ②出生日期码：（第7位到第14位）
	 *  ③顺序码：（第15位到17位）县、区级政府所辖派出所的分配码
	 *  ④校验码：（最后1位）
	 **/
	public static class IDCardUtils {
	    // 正则格式
	    private static Pattern pattern = Pattern.compile("^\\d{17}[\\d|X]$");

	    /**
	     * 省、直辖市代码表，身份证号的前6位为地址信息，我们只验证前两位
	     */
	    private static final String CITY_CODE[] = {
	            "11", "12", "13", "14", "15", "21", "22", "23", "31", "32", "33", "34", "35", "36", "37", "41",
	            "42", "43", "44", "45", "46", "50", "51", "52", "53", "54", "61", "62", "63", "64", "65", "71",
	            "81", "82", "91"
	    };

	    /**
	     * 每位加权因子
	     */
	    private static final int POWER[] = {7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2};

	    /**
	     * 第18位校检码
	     **/
	    private static final String VERIFY_CODE[] = {"1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2"};

	    /**
	     * 验证身份证
	     * @param idno
	     * @return
	     */
	    public static boolean verify(String idno) {
	        //1.格式验证
	        if(idno == null || !pattern.matcher(idno = idno.toUpperCase()).matches()){
	            return false;
	        }

	        //2.验证省、直辖市代码。市、区不作验证，没有规则限制，数字即可
	        if(Arrays.binarySearch(CITY_CODE, idno.substring(0, 2)) == -1){
	            return false;
	        }

	        //3.验证生日,生日可能存在输入20180231这种情况，所以使用Calendar处理校验
	        String birthday = idno.substring(6, 14);
	        // 如果输入的日期为20180231，通过转换的后realBirthday为20180303
	        Date realBirthday = toBirthDay(birthday);
	        // 转换失败或不相等
	        if(realBirthday == null || !birthday.equals(new SimpleDateFormat("yyyyMMdd").format(realBirthday))){
	            return false;
	        }

	        //4.顺序码不作验证，没有规则限制，数字即可
	        //5.验证位验证，计算规则为：身份证前17位数字，对应乘以每位的权重因子，然后相加得到数值X，与11取模获得余数，得到数值Y,通过Y得到校验码。
	        String verifyCode = VERIFY_CODE[getPowerSum(idno) % 11];
	        if(!verifyCode.equals(idno.substring(17, 18))){
	            return false;
	        }
	        return true;
	    }

	    /**
	     * 取得身份证号前17位与对应的权重值相乘的和
	     * @return
	     */
	    private static int getPowerSum(String idno){
	        int sum = 0;
	        // 身份证前17位
	        char[] fix17 = idno.substring(0, 17).toCharArray();
	        for(int i = 0 ; i <= 16 ; i++){
	            sum += (Integer.parseInt(fix17[i] + "") * POWER[i]);
	        }
	        return sum;
	    }

	    /**
	     * 转换成日期
	     * @param birthday
	     * @return
	     */
	    public static Date toBirthDay(String birthday){
	        try{
	            Calendar calendar = Calendar.getInstance();
	            calendar.set(Calendar.YEAR, Integer.parseInt(birthday.substring(0, 4)));
	            calendar.set(Calendar.MONTH, Integer.parseInt(birthday.substring(4, 6)) - 1);//月份从0开始，所以减1
	            calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(birthday.substring(6, 8)));
	            //以下设置意义不大
	            calendar.set(Calendar.HOUR_OF_DAY, 0);
	            calendar.set(Calendar.MINUTE, 0);
	            calendar.set(Calendar.SECOND, 0);
	            calendar.set(Calendar.MILLISECOND, 0);

	            return calendar.getTime();
	        }catch (Exception e){
	            return null;
	        }
	    }
	    /**
	     * 从身份证号码中获取生日
	     * @param idno
	     * @return null表示idno错误，未获取到生日
	     */
	    public static Date getBirthDay(String idno){
	        if(!verify(idno)){
	            return null;
	        }

	        return toBirthDay(idno.substring(6, 14));
	    }

	    /**
	     * 从身份证号中获取性别
	     * @param idno
	     * @return 0:获取失败，1:男，2:女
	     */
	    public static int getGender(String idno){
	        if(!verify(idno)){
	            return 0;
	        }
	        // 奇男，偶女
	        return (Integer.parseInt(idno.substring(16, 17)) % 2) == 0 ? 2 : 1;
	    }
	}
}
