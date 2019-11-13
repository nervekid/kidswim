package com.kite.modules.sys.web;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.kite.common.config.Global;
import com.kite.common.security.shiro.session.CacheSessionDAO;
import com.kite.common.utils.StringUtils;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.entity.UserOnline;
import com.kite.modules.sys.service.SystemService;

/**
 * 在线用户管理controller类
 * Created by cxh on 2017/12/29.
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/online")
public class UserOnLineController  extends BaseController{
    @Autowired
        private CacheSessionDAO cacheSessionDAO;
    @Autowired
    private SystemService systemService;


    @RequestMapping(value = "list")
    public String list(Model model, HttpServletRequest request, HttpServletResponse response) {
        List<UserOnline> onlineSessionList=new ArrayList<UserOnline>();
        Collection<Session> activeSessions = cacheSessionDAO.getActiveSessions(false);
        Iterator it = activeSessions.iterator();
        UserOnline online = null;
        while(it.hasNext()){
            Session session = (Session) it.next();
            Object attribute = session.getAttribute("principalId");
            if(attribute!=null){
                online = new UserOnline();
                String id = attribute.toString();
                if(StringUtils.isNotEmpty(id)){
                    User user = systemService.getUser(id);
                    if(user!=null){
                        online.setId(session.getId());
                        online.setUsername(user.getName());
                        online.setStartTimestamp(session.getStartTimestamp());
                        online.setLastAccessTime(session.getLastAccessTime());
                        online.setHost(session.getHost());
                        online.setUserAgent(request.getHeader("User-Agent"));
                        onlineSessionList.add(online);
                    }
                }
            }
        }
        model.addAttribute("onlineSessionList",onlineSessionList);
        return "modules/sys/onlineSessionList";
    }


    /**
     * 删除设备分类
     */
    @RequestMapping(value = "forceLogout")
    public String forceLogout(String  id, RedirectAttributes redirectAttributes) {
        try {
            Session session = cacheSessionDAO.readSession(id);
            if(session!=null){
            	cacheSessionDAO.delete(session);
                addMessage(redirectAttributes, "踢出成功");
            }
        } catch (UnknownSessionException e) {
            addMessage(redirectAttributes, "操作失败");
            e.printStackTrace();
        }
        return "redirect:"+ Global.getAdminPath()+"/sys/online/list?repage";
    }


}
