/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.web;

import com.google.common.collect.Maps;
import com.kite.common.config.Global;
import com.kite.common.json.AjaxJson;
import com.kite.common.persistence.Page;
import com.kite.common.result.ResultSupport;
import com.kite.common.security.shiro.session.SessionDAO;
import com.kite.common.servlet.ValidateCodeServlet;
import com.kite.common.utils.CookieUtils;
import com.kite.common.utils.IdGen;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.SessionSituation;
import com.kite.modules.sys.entity.SysUserCollectionMenu;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.security.FormAuthenticationFilter;
import com.kite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.kite.modules.sys.security.UsernamePasswordToken;
import com.kite.modules.sys.service.SessionSituationService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.XuanwuCheckService;
import com.kite.modules.sys.utils.UserUtils;
import com.kite.modules.xw_tools.entity.XwPushmessage;
import com.kite.modules.xw_tools.service.XwPushmessageService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.SavedRequest;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 登录Controller
 * @author kite
 * @version 2013-5-31
 */
@Controller
public class LoginController extends BaseController{

	@Autowired
	private SessionDAO sessionDAO;
	@Autowired
	private XwPushmessageService xwPushmessageService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SessionSituationService sessionSituationService;
	@Autowired
	private XuanwuCheckService xuanwuCheckService;

	/**
	 * 管理登录
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/login")
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

	/**
	 * 登录失败，真正登录的POST请求由Filter完成
	 */
	@RequestMapping(value = "${adminPath}/login", method = RequestMethod.POST)
	public String loginFail(HttpServletRequest request, HttpServletResponse response, Model model) {
		Principal principal = UserUtils.getPrincipal();

		// 如果已经登录，则跳转到管理首页
		if(principal != null){
			return "redirect:" + adminPath;
		}

		String username = WebUtils.getCleanParam(request, FormAuthenticationFilter.DEFAULT_USERNAME_PARAM);
		boolean rememberMe = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM);
		boolean mobile = WebUtils.isTrue(request, FormAuthenticationFilter.DEFAULT_MOBILE_PARAM);
		String exception = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME);
		String message = (String)request.getAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM);

		if (StringUtils.isBlank(message) || StringUtils.equals(message, "null")){
			message = "用户或密码错误, 请重试.";
		}

		model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM, username);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_REMEMBER_ME_PARAM, rememberMe);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MOBILE_PARAM, mobile);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_ERROR_KEY_ATTRIBUTE_NAME, exception);
		model.addAttribute(FormAuthenticationFilter.DEFAULT_MESSAGE_PARAM, message);

		if (logger.isDebugEnabled()){
			logger.debug("login fail, active session size: {}, message: {}, exception: {}",
					sessionDAO.getActiveSessions(false).size(), message, exception);
		}

		// 非授权异常，登录失败，验证码加1。
		if (!UnauthorizedException.class.getName().equals(exception)){
			boolean validateCodeLogin = isValidateCodeLogin(username, true, false);
			model.addAttribute("isValidateCodeLogin", validateCodeLogin);
			/*if(!validateCodeLogin){
				Session session = UserUtils.getSession();
				session.removeAttribute(ValidateCodeServlet.VALIDATE_CODE);
			}*/
		}

		// 验证失败清空验证码
		request.getSession().setAttribute(ValidateCodeServlet.VALIDATE_CODE, IdGen.uuid());

		// 如果是手机登录，则返回JSON字符串
		if (mobile){
			AjaxJson j = new AjaxJson();
			j.setSuccess(false);
			j.setMsg(message);
			j.put("username", username);
			j.put("name","");
			j.put("mobileLogin", mobile);
			j.put("JSESSIONID", "");
	        return renderString(response, j.getJsonStr());
		}

		return "modules/sys/sysLogin";
	}

	/**
	 * 管理登录
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/logout", method = RequestMethod.GET)
	public String logout(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
		try {
			Principal principal = UserUtils.getPrincipal();
			Session session = UserUtils.getSession();
			String sessionId = String.valueOf(session.getId());
			SessionSituation sessionSituation = this.sessionSituationService.findBySessionId(sessionId);
			if (sessionSituation != null && principal!=null) {
                Date date = new Date();
                long begin = session.getLastAccessTime().getTime();
                long end = date.getTime();
                long difference = end - begin;
                SessionSituation entity = new SessionSituation();
                entity.setActualExitTime(Integer.parseInt(String.valueOf(difference)));
                entity.setStopTime(date);
                entity.setUserName(principal.getName());
                try {
                    MyBeanUtils.copyBeanNotNull2Bean(sessionSituation, entity);
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
                this.sessionSituationService.save(entity);
            }
			// 如果已经登录，则跳转到管理首页
			if(principal != null){
                if(session!=null){
                    sessionDAO.delete(session);
    //				UserUtils.clearCache(UserUtils.getUser());
                //	CacheUtils.remove(UserUtils.USER_CACHE,UserUtils.USER_CACHE_LOGIN_NAME_+);
                }
                UserUtils.getSubject().logout();
            }
			// 如果是手机客户端退出跳转到login，则返回JSON字符串
			String ajax = request.getParameter("__ajax");
			if(	ajax!=null){
				model.addAttribute("success", "1");
				model.addAttribute("msg", "退出成功");
				return renderString(response, model);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + adminPath+"/login";
	}

	/**
	 * 登录成功，进入管理首页
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "${adminPath}")
	public String index(HttpServletRequest request, HttpServletResponse response) {
		Principal principal = UserUtils.getPrincipal();
		// 登录成功后，验证码计算器清零
		isValidateCodeLogin(principal.getLoginName(), false, true);

		if (logger.isDebugEnabled()){
			logger.debug("show index, active session size: {}", sessionDAO.getActiveSessions(false).size());
		}

		// 如果已登录，再次访问主页，则退出原账号。
		if (Global.TRUE.equals(Global.getConfig("notAllowRefreshIndex"))){
			String logined = CookieUtils.getCookie(request, "LOGINED");
			if (StringUtils.isBlank(logined) || "false".equals(logined)){
				CookieUtils.setCookie(response, "LOGINED", "true");
			}else if (StringUtils.equals(logined, "true")){
				UserUtils.getSubject().logout();
				return "redirect:" + adminPath + "/login";
			}
		}

		// 如果是手机登录，则返回JSON字符串
		if (principal.isMobileLogin()){
			if (request.getParameter("login") != null){
				return renderString(response, principal);
			}
			if (request.getParameter("index") != null){
				return "modules/sys/sysIndex";
			}
			return "redirect:" + adminPath + "/login";
		}

//		// 登录成功后，获取上次登录的当前站点ID
//		UserUtils.putCache("siteId", StringUtils.toLong(CookieUtils.getCookie(request, "siteId")));

//		System.out.println("==========================a");
//		try {
//			byte[] bytes = com.kite.common.utils.FileUtils.readFileToByteArray(
//					com.kite.common.utils.FileUtils.getFile("c:\\sxt.dmp"));
//			UserUtils.getSession().setAttribute("kkk", bytes);
//			UserUtils.getSession().setAttribute("kkk2", bytes);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
////		for (int i=0; i<1000000; i++){
////			//UserUtils.getSession().setAttribute("a", "a");
////			request.getSession().setAttribute("aaa", "aa");
////		}
//		System.out.println("==========================b");
		//
		/*OaNotify oaNotify = new OaNotify();
		oaNotify.setSelf(true);
		oaNotify.setReadFlag("0");
		Page<OaNotify> page = oaNotifyService.find(new Page<OaNotify>(request, response), oaNotify);
		request.setAttribute("page", page);
		request.setAttribute("count", page.getList().size());//未读通知条数*/

		XwPushmessage xwPushmessage = new XwPushmessage();
		xwPushmessage.setReadFlag("0");
		xwPushmessage.setSelf(true);
		Page<XwPushmessage> page =  xwPushmessageService.find(new Page<XwPushmessage>(request, response), xwPushmessage);
		request.setAttribute("page", page);
		request.setAttribute("count", page.getList().size());//未读通知条数
		//
	/*	MailBox mailBox = new MailBox();
		mailBox.setReceiver(UserUtils.getUser());
		mailBox.setReadstatus("0");//筛选未读
		Page<MailBox> mailPage = mailBoxService.findPage(new MailPage<MailBox>(request, response), mailBox);
		request.setAttribute("noReadCount", mailBoxService.getCount(mailBox));
		request.setAttribute("mailPage", mailPage);*/
		// 默认风格
		String indexStyle = "default";
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			if (cookie == null || StringUtils.isEmpty(cookie.getName())) {
				continue;
			}
			if (cookie.getName().equalsIgnoreCase("theme")) {
				indexStyle = cookie.getValue();
			}
		}
		// 要添加自己的风格，复制下面三行即可
		if (StringUtils.isNotEmpty(indexStyle)
				&& indexStyle.equalsIgnoreCase("ace")) {
			return "modules/sys/sysIndex-ace";
		}

		return "modules/sys/sysIndex";
	}

	/**
	 * 获取主题方案
	 */
	@RequestMapping(value = "/theme/{theme}")
	public String getThemeInCookie(@PathVariable String theme, HttpServletRequest request, HttpServletResponse response){
		if (StringUtils.isNotBlank(theme)){
			CookieUtils.setCookie(response, "theme", theme);
		}else{
			theme = CookieUtils.getCookie(request, "theme");
		}
		return "redirect:"+request.getParameter("url");
	}

	/**
	 * 是否是验证码登录
	 * @param useruame 用户名
	 * @param isFail 计数加1
	 * @param clean 计数清零
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static boolean isValidateCodeLogin(String useruame, boolean isFail, boolean clean){
//		Map<String, Integer> loginFailMap = (Map<String, Integer>)CacheUtils.get("loginFailMap");
//		if (loginFailMap==null){
//			loginFailMap = Maps.newHashMap();
//			CacheUtils.put("loginFailMap", loginFailMap);
//		}

		Map<String, Integer> loginFailMap = Maps.newHashMap();

		Integer loginFailNum = loginFailMap.get(useruame);
		if (loginFailNum==null){
			loginFailNum = 0;
		}
		if (isFail){
			loginFailNum++;
			loginFailMap.put(useruame, loginFailNum);
		}
		if (clean){
			loginFailMap.remove(useruame);
		}
		return loginFailNum >= 3;
	}


	/**
	 * 首页
	 * @throws IOException
	 */
	@RequestMapping(value = "${adminPath}/home")
	public String home(HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {

		List<SysUserCollectionMenu> list = sysUserCollectionMenuService.findList(new SysUserCollectionMenu(UserUtils.getUser()));
		model.addAttribute("list",list);
		return "modules/sys/sysHome";

	}

	/*@RequestMapping(value = "simulationLogin")
	public String simulationLogin(){
		String resultUrl = "";
		Subject currentUser = SecurityUtils.getSubject();
		List<XuanwuCheck> list = xuanwuCheckService.findList(new XuanwuCheck());
		XuanwuCheck xuanwuCheck = null;
		if(list!=null && list.size()>0){
			xuanwuCheck = list.get(0);
		}
		if(xuanwuCheck!=null){
			String decrypt = DesUtil.decrypt(xuanwuCheck.getXuanwuu(), Global.scanCodeExpress);
			UsernamePasswordToken token = new UsernamePasswordToken("13543452355",decrypt.toCharArray(),false,null,"null",false,true,xuanwuCheck.getXuanwup());
			currentUser.login(token);
			resultUrl="redirect:" + adminPath ;
		}else{
			resultUrl="redirect:" + adminPath+"/login";
		}
		return resultUrl;
	}*/

	@RequestMapping(value = "/loginApi/login")
	@ResponseBody
	public ResultSupport<User> loginApi(String userName, String password){
			ResultSupport<User> result = ResultSupport.createMisResp();
		try {
			Subject currentUser = SecurityUtils.getSubject();
			UsernamePasswordToken token = new UsernamePasswordToken(userName,password.toCharArray(),false,null,"null",false);
			currentUser.login(token);
			User user = UserUtils.getUser();
			ResultSupportUtilsV2.fillResultSupport(result, "返回成功", user);
		} catch (AuthenticationException e) {
			e.printStackTrace();
		}
		return result;
	}
}
