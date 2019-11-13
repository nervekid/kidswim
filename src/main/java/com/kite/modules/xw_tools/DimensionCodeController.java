package com.kite.modules.xw_tools;

/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */


import com.kite.common.web.BaseController;
import com.kite.modules.sys.service.SystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 * 二维码Controller
 * @author kite
 * @version 2015-11-30
 */
@Controller
@RequestMapping(value = "/tools/DimensionCodeController")
public class DimensionCodeController extends BaseController {

	@Autowired
	private SystemService systemService;
	

	
	/**
	 * 回调链接
	 */
	@RequestMapping(value = "mobile")
	public String mobile(HttpServletRequest request, @RequestParam String mobile,Model model) throws Exception{
		String fullUrl=fullUrl(mobile);
		//model.addAttribute("filePath", createTwoDimensionCode(fullUrl,mobile));
		return "modules/xw_tools/DimensionCode";
	}
	
	public String fullUrl(String mobile){
		Long tsLong = System.currentTimeMillis();
        String ts = tsLong.toString();
        String STR = ts.substring(0, ts.length() - 3) + "000";
		return "9027|" + mobile +"|" + STR + "|" + "440106B008";
		
	}
	
	

	
	
	



}