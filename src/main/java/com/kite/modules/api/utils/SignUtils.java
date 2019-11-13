package com.kite.modules.api.utils;

import com.kite.common.utils.MD5Utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * 签名工具类
 * @author wsd
 * @version 2019-11-07
 */
public class SignUtils {

    /**
     * md5加密算法
     * @param appSecret
     * @param data
     * @return
     */
    public static String calSign(String appSecret, Map<String,Object> data){
        data.put("appkey", appSecret);

        List<String> keys = new ArrayList<>(data.keySet());
        Collections.sort(keys);
        StringBuilder txt = new StringBuilder();
        for(String key : keys){
            if("sign".equalsIgnoreCase(key)) {
                continue;
            }
            Object val = data.get(key);
            txt.append(key).append("=").append(val==null?"":val.toString().trim()).append("&");
        }
        if(txt.length() > 0) {
            txt.deleteCharAt(txt.length()-1);
        }
        data.remove("appkey");
        System.out.println("cal sign txt [" + txt + "],appSecret [" + appSecret + "].");

        return MD5Utils.encode(txt.toString());
    }
}
