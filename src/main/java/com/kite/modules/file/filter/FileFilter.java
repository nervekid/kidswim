package com.kite.modules.file.filter;

import com.alibaba.fastjson.JSON;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;

import javax.servlet.*;
import java.io.IOException;
import java.util.List;

/**
 * @author yyw
 * @Description: 文件拦截器
 * @date 2018/8/2411:12
 */
public class FileFilter implements Filter {

    @Autowired
    private SystemService systemService;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        System.out.println("filter初始化");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        System.out.println("filter拦截");

        //做权限
//        User user = UserUtils.getUser();
//
//        String id = user.getId();
//        System.out.println(id);
//
//        User select = new User();
//        select.setId(id);
//
//        XmlWebApplicationContext cxt =(XmlWebApplicationContext) WebApplicationContextUtils.getWebApplicationContext(request.getServletContext());
//
//        if(cxt != null && cxt.getBean("systemService") != null && systemService == null) {
//            systemService = (SystemService) cxt.getBean("systemService");
//        }
//
//
//        List<User> list =  systemService.findUser(select);
//        System.out.println(list.size());


        chain.doFilter(request, response); // 放行
    }

    @Override
    public void destroy() {
        System.out.println("filter摧毁");
    }
}
