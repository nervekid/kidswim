package com.kite.modules.att.rpc;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kite.modules.att.command.RpcUserCommand;
import com.kite.modules.sys.service.SystemService;


/**
 * 泳班登录大厅远程接口类
 * @author lyb
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "/att/hall")
public class RpcAttHallController {
	private static Logger logger = LoggerFactory.getLogger(RpcAttHallController.class);

	@Autowired private SystemService systemService;

	/**
	 * 泳班登录接口
	 */
	@SuppressWarnings("static-access")
	@RequestMapping(value = "login")
	@ResponseBody
	public Map<String, Object> login(@RequestParam("userLoginName") String userLoginName, @RequestParam("userPassword") String userPassword,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入泳班系统远程登录接口userLoginName={}", userLoginName);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");

        //根据用户名找到用户
        RpcUserCommand user = this.systemService.findByUserLoginNameAndPassword(userLoginName, this.systemService.entryptPasswordByMD5(userPassword));
        if (user == null) {
        	logger.info("登录失败，状态码为={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("登录成功，状态码为={}", 1);
        	data.put("status", "1");
        	data.put("user", user);
        }
        return data;
	}

}
