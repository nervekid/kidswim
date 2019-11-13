package com.kite.common.utils.verification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


import org.springframework.stereotype.Component;


/**
 * 校验工具类
 * @author lyb
 *
 */
@Component
public class BasicVerificationUtil {

	private static Pattern pattern = Pattern.compile("^[-\\+]?[\\d]*$");
	private static Pattern pattern1 = Pattern.compile("^(\\d{6})(19|20)(\\d{2})(1[0-2]|0[1-9])(0[1-9]|[1-2][0-9]|3[0-1])(\\d{3})(\\d|X|x)?$");
	/**
	 * 比率校验
	 * @param tar
	 * @return
	 */
	public static boolean rateCheck(String tar) {
		String regEx = "^\\d{1,2}(\\.\\d{1,2})?$";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}

	/**
	 * 验证年月
	 * @param tar
	 * @return
	 */
	public static boolean yearMonthChek(String tar) {
		boolean result = false;
		String regEx = "^\\d{6}$";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    result = matcher.matches();

	    if (result) {
	    	String year = tar.substring(0, 4);
	    	String month = tar.substring(4, 6);
	    	if (Integer.parseInt(month) > 12 || Integer.parseInt(month) == 0) {
	    		result = false;
	    	}
	    	if (Integer.parseInt(year) > 2100 || Integer.parseInt(year) < 1999) {
	    		result = false;
	    	}
	    }
    	return result;
	}

	/**
	 * 校验导入数据
	 * 纯数字或者带小数点数字
	 * @param tar
	 * @return
	 */
	public static boolean DigitCheck(String tar) {
	    String regEx = "^[0-9]\\d*\\.\\d*|[0-9]\\d*$";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}
	
	/**
	 * 校验导入数据
	 * 纯数字或者带小数点数字   可以以0.开头
	 * @param tar
	 * @return
	 */
	public static boolean DigitCheckV2(String tar) {
	    String regEx = "([1-9]\\d*\\.?\\d*)|(0\\.\\d*[0-9])|0";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}

	public static boolean isInteger(String str) {
		return pattern.matcher(str).matches();
	}

	/**
	 * 校验导入数据
	 * 负数 纯数字或者带小数点数字   可以以0.开头
	 * @param tar
	 * @return
	 */
	public static boolean DigitCheckV3(String tar) {
	    String regEx = "^-([1-9]\\d*\\.?\\d*)|^-(0\\.\\d*[0-9])|0";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}
	
	public static boolean DigitCheckV4(String tar) {
		String regEx = "^-([1-9]\\d*\\.?\\d*)|^-(0\\.\\d*[0-9])|0|([1-9]\\d*\\.?\\d*)|(0\\.\\d*[0-9])";
		Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}
	
	
	/**
	 * 启用与禁用字段校验
	 * @param tar
	 * @return
	 */
	public static boolean enableCheck(String tar) {
		if (tar.trim().equals("启用") || tar.trim().equals("禁用")) {
			return true;
		}
		return false;
	}

	/**
	 * 身份证校验，18位
	 * @param str
	 * @return
	 */
	 public static boolean identityCheck(String idCard){
	        Matcher matcher = pattern1.matcher(idCard);
	        int[] prefix = new int[]{7,9,10,5,8,4,2,1,6,3,7,9,10,5,8,4,2};
	        int[] suffix = new int[]{ 1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2 };
	        if(matcher.matches()){
	            Map<String, String> cityMap = initCityMap();
	            if(cityMap.get(idCard.substring(0,2)) == null ){
	                return false;
	            }
	            int idCardWiSum=0;
	            for(int i=0;i<17;i++){
	                idCardWiSum+=Integer.valueOf(idCard.substring(i,i+1))*prefix[i];
	            }

	            int idCardMod=idCardWiSum%11;
	            String idCardLast=idCard.substring(17);

	            if(idCardMod==2){
	                if(idCardLast.equalsIgnoreCase("x")){
	                    return true;
	                }else{
	                    return false;
	                }
	            }else{
	                if(idCardLast.equals(suffix[idCardMod]+"")){
	                    return true;
	                }else{
	                    return false;
	                }
	           }
	        }
	        return false;
	    }


	/**
	 * 十一位手机号码校验
	 * 已13, 15, 18开头
	 * @param tar
	 * @return
	 */
	public static boolean phoneElevenCheck(String tar) {
	    String regEx = "^1[358]\\d{9}$";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}

	/**
	 * 邮箱格式校验
	 * @param tar
	 * @return
	 */
	public static boolean emailCheck(String tar) {
	    String regEx = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
	    Pattern pattern = Pattern.compile(regEx);
	    Matcher matcher = pattern.matcher(tar.trim());
	    boolean result = matcher.matches();
	    return result;
	}

	/**
	 * 布尔数值校验
	 * @param tar
	 * @return
	 */
	public static boolean booleanCheck(String tar) {
		if (tar.trim().length() == 1 && (tar.trim().equals("1") || tar.trim().equals("0"))) {
			return true;
		}
		return false;
	}

	/**
	 * 校验导入数据
	 * yyyyMMdd 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateA(String str) {
		boolean convertSuccess=true;
	      SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyyMMdd HH:mm 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateB(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyyMMdd HH:mm:ss 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateC(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HH:mm:ss");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyy-MM-dd 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateD(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}
	
	/**
	 * 校验导入数据
	 * yyyy-MM-dd HH:mm 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateE(String str) {
		boolean convertSuccess=true;
		 SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyy-MM-dd HH:mm:ss 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateF(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyy/MM/dd 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateG(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyy/MM/dd HH:mm 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateH(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	/**
	 * 校验导入数据
	 * yyyy/MM/dd HH:mm:ss 格式
	 * @param str
	 * @return
	 */
	public static boolean isValidDateI(String str) {
		boolean convertSuccess=true;
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	      try {
	          format.setLenient(false);
	          format.parse(str);
	       }
	       catch (ParseException e) {
	           convertSuccess=false;
	       }
	       return convertSuccess;
	}

	private static Map<String, String> initCityMap(){
        Map<String, String> cityMap = new HashMap<String, String>();
            cityMap.put("11", "北京");
            cityMap.put("12", "天津");
            cityMap.put("13", "河北");
            cityMap.put("14", "山西");
            cityMap.put("15", "内蒙古");

            cityMap.put("21", "辽宁");
            cityMap.put("22", "吉林");
            cityMap.put("23", "黑龙江");

            cityMap.put("31", "上海");
            cityMap.put("32", "江苏");
            cityMap.put("33", "浙江");
            cityMap.put("34", "安徽");
            cityMap.put("35", "福建");
            cityMap.put("36", "江西");
            cityMap.put("37", "山东");

            cityMap.put("41", "河南");
            cityMap.put("42", "湖北");
            cityMap.put("43", "湖南");
            cityMap.put("44", "广东");
            cityMap.put("45", "广西");
            cityMap.put("46", "海南");

            cityMap.put("50", "重庆");
            cityMap.put("51", "四川");
            cityMap.put("52", "贵州");
            cityMap.put("53", "云南");
            cityMap.put("54", "西藏");

            cityMap.put("61", "陕西");
            cityMap.put("62", "甘肃");
            cityMap.put("63", "青海");
            cityMap.put("64", "宁夏");
            cityMap.put("65", "新疆");

//          cityMap.put("71", "台湾");
//          cityMap.put("81", "香港");
//          cityMap.put("82", "澳门");
//          cityMap.put("91", "国外");
            return cityMap;
        }

}
