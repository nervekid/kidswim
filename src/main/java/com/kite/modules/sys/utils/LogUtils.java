/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.utils;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kite.common.config.Global;
import com.kite.common.utils.Exceptions;
import com.kite.common.utils.JsonUtils;
import com.kite.common.utils.SpringContextHolder;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.LogDao;
import com.kite.modules.sys.dao.MenuDao;
import com.kite.modules.sys.entity.Log;
import com.kite.modules.sys.entity.LogOfObject;
import com.kite.modules.sys.entity.Menu;
import com.kite.modules.sys.entity.User;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * 字典工具类
 * @author kite
 * @version 2014-11-7
 */
public class LogUtils {

	public static final String CACHE_MENU_NAME_PATH_MAP = "menuNamePathMap";

	private static LogDao logDao = SpringContextHolder.getBean(LogDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static Logger logger = LoggerFactory.getLogger(LogUtils.class);

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, String title){
		saveLog(request, null, null, title);
	}

	/**
	 * 保存日志
	 */
	public static void saveLog(HttpServletRequest request, Object handler, Exception ex, String title){
		User user = UserUtils.getUser();
		if (user.getId() == null){
			user = UserUtils.get("1");
		}
		Log log = new Log();
		log.setTitle(title);
		log.setType(ex == null ? Log.TYPE_ACCESS : Log.TYPE_EXCEPTION);
		log.setRemoteAddr(StringUtils.getRemoteAddr(request));
		log.setUserAgent(request.getHeader("user-agent"));
		log.setRequestUri(request.getRequestURI());
		log.setParams(request.getParameterMap());
		log.setMethod(request.getMethod());
		// 异步保存日志
		new SaveLogThread(log, handler, ex).start();
	}

	/**
	 * 保存日志线程
	 */
	public static class SaveLogThread extends Thread{

		private Log log;
		private Object handler;
		private Exception ex;

		public SaveLogThread(Log log, Object handler, Exception ex){
			super(SaveLogThread.class.getSimpleName());
			this.log = log;
			this.handler = handler;
			this.ex = ex;
		}

		@Override
		public void run() {
			// 获取日志标题
			if (StringUtils.isBlank(log.getTitle())){
				String permission = "";
				if (handler instanceof HandlerMethod){
					Method m = ((HandlerMethod)handler).getMethod();
					RequiresPermissions rp = m.getAnnotation(RequiresPermissions.class);
					permission = (rp != null ? StringUtils.join(rp.value(), ",") : "");
				}
				log.setTitle(getMenuNamePath(log.getRequestUri(), permission));
			}
			// 如果有异常，设置异常信息
			log.setException(Exceptions.getStackTraceAsString(ex));
			// 如果无标题并无异常日志，则不保存信息
			if (StringUtils.isBlank(log.getTitle()) && StringUtils.isBlank(log.getException())){
				logger.info("===================无法进入操作日志保存menuNamePath等于空======================");
				return;
			}
			// 保存日志信息
			logger.info("===================进入日志保存menuNamePath={}======================", log.getTitle());
			log.preInsert();
			logDao.insert(log);
		}
	}

	/**
	 * 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
	 */
	public static String getMenuNamePath(String requestUri, String permission){
		String href = StringUtils.substringAfter(requestUri, Global.getAdminPath());
		if (href.equals("")) {
			href = requestUri;
		}
		@SuppressWarnings("unchecked")
		//Map<String, String> menuMap = (Map<String, String>)CacheUtils.get(CACHE_MENU_NAME_PATH_MAP);
		Map<String, String> menuMap = Maps.newHashMap();
//		if (menuMap == null){
			List<Menu> menuList = menuDao.findAllList(new Menu());
			for (Menu menu : menuList){
				// 获取菜单名称路径（如：系统设置-机构用户-用户管理-编辑）
				String namePath = "";
				if (menu.getParentIds() != null){
					List<String> namePathList = Lists.newArrayList();
					for (String id : StringUtils.split(menu.getParentIds(), ",")){
						if (Menu.getRootId().equals(id)){
							continue; // 过滤跟节点
						}
						for (Menu m : menuList){
							if (m.getId().equals(id)){
								namePathList.add(m.getName());
								break;
							}
						}
					}
					namePathList.add(menu.getName());
					namePath = StringUtils.join(namePathList, "-");
				}
				// 设置菜单名称路径
				if (StringUtils.isNotBlank(menu.getHref())){
					menuMap.put(menu.getHref(), namePath);
				}else if (StringUtils.isNotBlank(menu.getPermission())){
					for (String p : StringUtils.split(menu.getPermission())){
						menuMap.put(p, namePath);
					}
				}

			}
			//CacheUtils.put(CACHE_MENU_NAME_PATH_MAP, menuMap);
//		}
		String menuNamePath = menuMap.get(href);
		if (menuNamePath == null){
			for (String p : StringUtils.split(permission)){
				menuNamePath = menuMap.get(p);
				if (StringUtils.isNotBlank(menuNamePath)){
					break;
				}

			}

			if (href.contains("xw_qyh")) {
				menuNamePath = "价值观飞象卡";
			}
			else if (href.contains("api")) {
				menuNamePath = "API接口调用";
			}
			else if (href.contains("rpc")) {
				menuNamePath = "远程接口调用";
			}
		}
		logger.info("===================进入日志保存menuNamePath={}======================", menuNamePath);
		return menuNamePath;
	}


	/**
	 * @function  保存对象修改前后的 Jon 数据
	 * @param logType	日志类型
	 * @param title		日志标题
	 * @param oldObj	修改前的原对象
	 * @param newObj	修改后的新对象
	 */
	public static void saveObjectLog(String logType , String  title ,Object oldObj ,Object  newObj){
		try{
			LogOfObject logOfObject = new LogOfObject();
			logOfObject.setId(UUID.randomUUID().toString());
			logOfObject.setType(logType);
			logOfObject.setTitle(title);
			User user = UserUtils.getUser();
			logOfObject.setCreateBy(user);
			logOfObject.setCreateDate(new Date());
			JsonUtils jsonUtils = new JsonUtils();
			try{
				String old = jsonUtils.objectToJson(oldObj) ;
				logOfObject.setOldObj(old);
			}catch (Exception ex_1){
				ex_1.printStackTrace();
				logOfObject.setOldObj(ex_1.getMessage());
			}
			try{
				String newO = jsonUtils.objectToJson(newObj) ;
				logOfObject.setNewObj(newO);
			}catch (Exception ex_2){
				ex_2.printStackTrace();
				logOfObject.setNewObj(ex_2.getMessage());
			}
			logDao.saveChangeOfObeject(logOfObject);
		}catch (Exception e){
			e.printStackTrace();
		}
	}




}
