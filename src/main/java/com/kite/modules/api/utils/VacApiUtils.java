package com.kite.modules.api.utils;

import cn.hutool.http.HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

/**
 * 飞象卡接口调用工具类
 * @author wsd
 * @version 2019-11-07
 */
public class VacApiUtils {

    private static final Logger logger = LoggerFactory.getLogger(VacApiUtils.class);

    /** 接口密钥 */
    private static final String SECRET = "6cXslS47df5Yaswast";
    /** 创建飞象卡用户 */
    private static final String CREATE_USER_URL = "https://kite.wxchina.com/xw_qyh/value_card/userManager/createVacUser";
    /** 删除飞象卡用户 */
    private static final String DELETE_USER_URL = "https://kite.wxchina.com/xw_qyh/value_card/userManager/deleteVacUser";

    /**
     * 创建飞象卡用户
     * @param userId
     * @param phone
     */
    public static void createVacUser(String userId, String phone) {
        String result = reqVacApi(CREATE_USER_URL, userId, phone);
        System.err.println(result);
        logger.info("飞象卡用户创建接口回调信息: {}", result);
    }

    /**
     * 删除飞象卡用户
     * @param userId
     * @param phone
     */
    public static void deleteVacUser(String userId, String phone) {
        String result = reqVacApi(DELETE_USER_URL, userId, phone);
        System.err.println(result);
        logger.info("飞象卡用户删除接口回调信息: {}", result);
    }

    /**
     * 封装飞象卡接口地址、请求参数
     * @param url
     * @param userId
     * @param phone
     * @return
     */
    public static String reqVacApi(String url, String userId, String phone) {
        Map<String, Object> params = new HashMap<>(8);
        params.put("secret", SECRET);
        params.put("timestamp", Instant.now().toEpochMilli());
        params.put("usermobile", phone);
        String signature = SignUtils.calSign(SECRET, params);
        params.put("signature", signature);
        params.put("userId", userId);
        logger.info("请求接口: {} , 请求参数: {}", url, params.toString());
        String result = HttpUtil.post(url, params);
        return result;
    }
}
