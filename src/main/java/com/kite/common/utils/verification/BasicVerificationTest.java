package com.kite.common.utils.verification;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class BasicVerificationTest {

	@Test
    public void execute() {

		//验证年月
		assertEquals(true, BasicVerificationUtil.yearMonthChek("201808"));

		//比率校验
		assertEquals(true, BasicVerificationUtil.rateCheck("20.00"));
		assertEquals(true, BasicVerificationUtil.rateCheck("20"));
		assertEquals(true, BasicVerificationUtil.rateCheck("20.01"));
		assertEquals(true, BasicVerificationUtil.rateCheck("20.10"));
		assertEquals(true, BasicVerificationUtil.rateCheck("2.3"));
		assertEquals(true, BasicVerificationUtil.rateCheck("02.03"));
		assertEquals(true, BasicVerificationUtil.rateCheck("02.30"));
		assertEquals(true, BasicVerificationUtil.rateCheck("02.30"));
		
		//数字或带小数点数值校验
		assertEquals(true, BasicVerificationUtil.DigitCheck("444"));
		assertEquals(true, BasicVerificationUtil.DigitCheck("111 "));
		assertEquals(true, BasicVerificationUtil.DigitCheck(" 111 "));
		assertEquals(true, BasicVerificationUtil.DigitCheck(" 111"));
		assertEquals(true, BasicVerificationUtil.DigitCheck("111.00"));
		
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("0"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("0.1"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("11.111"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("0.405"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("0.10"));
		//assertEquals(true, BasicVerificationUtil.DigitCheckV2("-111.00"));
		//assertEquals(true, BasicVerificationUtil.DigitCheckV2("-111.01"));
		//assertEquals(true, BasicVerificationUtil.DigitCheckV2("111.00"));

		
		assertEquals(true, BasicVerificationUtil.DigitCheckV3("0"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV3("-10"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV3("-0.1"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV3("-1.0"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV3("-1.405"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV3("-111.00"));

		assertEquals(true, BasicVerificationUtil.isInteger("0"));
		assertEquals(true, BasicVerificationUtil.isInteger("-10"));
		assertEquals(true, BasicVerificationUtil.isInteger("-1"));
		assertEquals(true, BasicVerificationUtil.isInteger("-1"));
		assertEquals(true, BasicVerificationUtil.isInteger("12"));
		assertEquals(true, BasicVerificationUtil.isInteger("12"));


		assertEquals(true, BasicVerificationUtil.DigitCheckV4("0"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("-10"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("-0.1"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("-1.0"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("-1.405"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("-111.00"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("111"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("111 "));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4(" 111 "));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4(" 111"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("111.00"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("0"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("0.1"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("11.111"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV4("0.405"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("0.10"));
		assertEquals(true, BasicVerificationUtil.DigitCheckV2("111.00"));
		
		//身份证校验,15位或者18位
		assertEquals(true, BasicVerificationUtil.identityCheck("441502199308102630"));

		//启用与禁用字段校验
		assertEquals(true, BasicVerificationUtil.enableCheck("启用 "));
		assertEquals(true, BasicVerificationUtil.enableCheck(" 启用  "));
		assertEquals(true, BasicVerificationUtil.enableCheck("启用  "));
		assertEquals(true, BasicVerificationUtil.enableCheck(" 启用 "));
		assertEquals(true, BasicVerificationUtil.enableCheck("禁用 "));
		assertEquals(true, BasicVerificationUtil.enableCheck(" 禁用  "));
		assertEquals(true, BasicVerificationUtil.enableCheck("禁用  "));
		assertEquals(true, BasicVerificationUtil.enableCheck(" 禁用 "));

		//11位手机号码校验
		assertEquals(true, BasicVerificationUtil.phoneElevenCheck(" 15999947554 "));
		assertEquals(true, BasicVerificationUtil.phoneElevenCheck("15999947554 "));
		assertEquals(true, BasicVerificationUtil.phoneElevenCheck(" 15999947554"));
		assertEquals(true, BasicVerificationUtil.phoneElevenCheck("15999947554"));
		assertEquals(true, BasicVerificationUtil.phoneElevenCheck("13899947554"));
		assertEquals(true, BasicVerificationUtil.phoneElevenCheck("18999947554"));

		//邮箱格式校验
		assertEquals(true, BasicVerificationUtil.emailCheck("493388964@qq.com"));
		assertEquals(true, BasicVerificationUtil.emailCheck(" 493388964@qq.com "));
		assertEquals(true, BasicVerificationUtil.emailCheck("493388964@qq.com "));
		assertEquals(true, BasicVerificationUtil.emailCheck(" 493388964@qq.com"));
		assertEquals(true, BasicVerificationUtil.emailCheck("liuyanbing@wxchina.com"));

		//布尔数值校验
		assertEquals(true, BasicVerificationUtil.booleanCheck("1"));
		assertEquals(true, BasicVerificationUtil.booleanCheck("0"));
		assertEquals(true, BasicVerificationUtil.booleanCheck("1 "));
		assertEquals(true, BasicVerificationUtil.booleanCheck(" 1"));
		assertEquals(true, BasicVerificationUtil.booleanCheck(" 1 "));

		//日期校验
		assertEquals(true, BasicVerificationUtil.isValidDateA("20180322"));
		assertEquals(true, BasicVerificationUtil.isValidDateB("20180322 15:43"));
		assertEquals(true, BasicVerificationUtil.isValidDateC("20180322 15:43:22"));
		assertEquals(true, BasicVerificationUtil.isValidDateD("2018-03-22"));
		assertEquals(true, BasicVerificationUtil.isValidDateE("2018-03-22 15:43"));
		assertEquals(true, BasicVerificationUtil.isValidDateF("2018-03-22 15:43:22"));
		assertEquals(true, BasicVerificationUtil.isValidDateG("2018/03/22"));
		assertEquals(true, BasicVerificationUtil.isValidDateH("2018/03/22 15:32"));
		assertEquals(true, BasicVerificationUtil.isValidDateI("2018/03/22 12:22:32"));
	}
}
