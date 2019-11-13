/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.web;

import com.kite.common.config.Global;
import com.kite.common.json.AjaxJson;
import com.kite.common.security.shiro.session.SessionDAO;
import com.kite.common.servlet.ValidateCodeServlet;
import com.kite.common.utils.CookieUtils;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.kite.modules.sys.service.SessionSituationService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.XuanwuCheckService;
import com.kite.modules.sys.utils.UserUtils;
import com.kite.modules.xw_tools.service.XwPushmessageService;
import org.apache.shiro.session.Session;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

/**
 * 登录Controller
 * @author kite
 * @version 2013-5-31
 */
@Controller
public class MobileLoginController extends BaseController{

	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private XwPushmessageService xwPushmessageService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SessionSituationService sessionSituationService;

	/*@Autowired
	private MailBoxService mailBoxService;*/

	@Autowired
	private XuanwuCheckService xuanwuCheckService;

	/**
	 * 手机管理登录
	 * @throws IOException
	 */
	@RequestMapping(value = "/mobile/modules/sys/sysLogin")
	public String login(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {


		Principal principal = UserUtils.getPrincipal();

//		// 默认页签模式
//		String tabmode = CookieUtils.getCookie(request, "tabmode");
//		if (tabmode == null){
//			CookieUtils.setCookie(response, "tabmode", "1");
//		}

		if (logger.isDebugEnabled()){
			logger.debug("login, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			CookieUtils.setCookie(response, "LOGINED", "false");
		}

		// 如果已经登录，则跳转到管理首页
		if(principal != null && !principal.isMobileLogin()){
			return "redirect:" + adminPath;
		}
		Session session = UserUtils.getSession();
		if(session!=null){
			Object attribute = session.getAttribute(ValidateCodeServlet.VALIDATE_CODE);
			if(attribute!=null){
				model.addAttribute("isValidateCodeLogin",true);
			}

		}
		 SavedRequest savedRequest = WebUtils.getSavedRequest(request);//获取跳转到login之前的URL
		// 如果是手机没有登录跳转到到login，则返回JSON字符串
		 if(savedRequest != null){
			 String queryStr = savedRequest.getQueryString();
			if(	queryStr!=null &&( queryStr.contains("__ajax") || queryStr.contains("mobileLogin"))){
				AjaxJson j = new AjaxJson();
				j.setSuccess(false);
				j.setErrorCode("0");
				j.setMsg("没有登录!");
				return renderString(response, j);
			}
		 }
		return "modules/sys/sysLogin";
	}


	@RequestMapping(value = "/mobile/modules/sys/sysLogin/getCheckCode")
	@ResponseBody
	public void getCharNumCheckCode(HttpServletRequest request, HttpServletResponse response) throws IOException{
		//定义BufferedImage（图像数据缓冲区）对象
		BufferedImage bi = new BufferedImage(68,22,BufferedImage.TYPE_INT_RGB);
		//绘制图片
		Graphics g = bi.getGraphics();
		//背景色
		Color c = new Color(200,150,255);
		g.setColor(c);
		//图片坐标
		g.fillRect(0, 0, 68, 22);
		//验证码选取
		char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789".toCharArray();
		Random r = new Random();
		int len=ch.length,index;
		StringBuffer sb = new StringBuffer();
		for(int i=0; i<4; i++){
			index = r.nextInt(len);
			g.setColor(new Color(r.nextInt(88),r.nextInt(188),r.nextInt(255)));
			Font ft = new Font(Font.SANS_SERIF, Font.BOLD, 16);
			g.setFont(ft);
			g.drawString(ch[index]+"", (i*15)+3, 18);
			sb.append(ch[index]);
		}
		//打印验证码，项目中用日志
		System.out.println(sb.toString());
		//验证码写到session
		request.getSession().setAttribute("checkCode", sb.toString());
		//ImageIO写出图片
		ImageIO.write(bi, "JPG", response.getOutputStream());
	}


}
