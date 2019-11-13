package com.kite.modules.sys.utils;

import java.security.MessageDigest;


public class CryptoUtil {

    /**
     * 根据ID获取用户R
     * @param strSrc，strSrc是EAS的newbosid(BOSTYPE)+明文密码
     * @return 加密密码
     */
	public static String encrypt(String strSrc)
  {
    byte[] btKey = new byte[strSrc.getBytes().length + 1];
    System.arraycopy(strSrc.getBytes(), 0, btKey, 0, strSrc.getBytes().length);
    String strEncode = "";
    try
    {
      MessageDigest md = MessageDigest.getInstance("MD5");
      md.update(btKey);
      byte[] btDigest = md.digest();

      strEncode = Base64Encoder.byteArrayToBase64(btDigest);
    }
    catch (Exception e)
    {
    }
    return strEncode;
  }

}
