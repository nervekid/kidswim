package com.kite.common.web;

import com.kite.modules.sys.security.SystemAuthorizingRealm;
import com.kite.modules.sys.utils.UserUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by chx on 2019/4/25.
 */

public class Servlets extends ServletsBase{
    /**
     * 是否是Ajax异步请求
     * @param request
     */
    public static boolean isAjaxRequest(HttpServletRequest request){

        String accept = request.getHeader("accept");
        String xRequestedWith = request.getHeader("X-Requested-With");
        SystemAuthorizingRealm.Principal principal = UserUtils.getPrincipal();

        // 如果是异步请求或是手机端，则直接返回信息
        return ((accept != null && accept.indexOf("application/json") != -1
                || (xRequestedWith != null && xRequestedWith.indexOf("XMLHttpRequest") != -1)
                || (principal != null && principal.isMobileLogin())));
    }

}