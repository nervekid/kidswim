/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.web;

import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.Log;
import com.kite.modules.sys.service.LogService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 日志Controller
 * @author kite
 * @version 2013-6-2
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/log")
public class LogController extends BaseController {

	@Autowired
	private LogService logService;
	
	@RequiresPermissions("sys:log:list")
	@RequestMapping(value = {"list", ""})
	public String list(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
        Page<Log> page = logService.findPage(new Page<Log>(request, response), log); 
        model.addAttribute("page", page);
		return "modules/sys/logList";
	}

	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("sys:log:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			logService.delete(logService.get(id));
		}
		addMessage(redirectAttributes, "删除日志成功");
		return "redirect:"+Global.getAdminPath()+"/sys/log/?repage";
	}
	
	/**
	 * 批量删除
	 */
	@RequiresPermissions("sys:log:del")
	@RequestMapping(value = "empty")
	public String empty(RedirectAttributes redirectAttributes) {
		logService.empty();
		addMessage(redirectAttributes, "清空日志成功");
		return "redirect:"+Global.getAdminPath()+"/sys/log/?repage";
	}


	@RequestMapping(value = {"listNew"})
	public String listNew(Log log, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<Log> page = logService.findPage(new Page<Log>(request, response), log);
		model.addAttribute("page", page);
		return "modules/sys/logList";
	}
}
