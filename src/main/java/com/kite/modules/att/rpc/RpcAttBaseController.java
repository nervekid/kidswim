package com.kite.modules.att.rpc;

import java.util.HashMap;
import java.util.List;
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

import com.kite.modules.sys.entity.Dict;
import com.kite.modules.sys.service.SystemService;

/**
 * 泳班基础远程接口类
 * @author lyb
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "/att/base")
public class RpcAttBaseController {

	private static Logger logger = LoggerFactory.getLogger(RpcAttBaseController.class);

	@Autowired private SystemService systemService;


	/**
	 * 根据类型查询字典列表接口
	 */
	@RequestMapping(value = "findDictListByType")
	@ResponseBody
	public Map<String, Object> findDictListByType(@RequestParam("type") String type,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入根据类型查询字典列表接口type={}", type);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");

        List<Dict> dictList = this.systemService.findDictListByType(type);

        if (dictList.isEmpty()) {
        	logger.info("查询字典列表失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询字典列表成功,status={}", 1);
        	data.put("status", "1");
        	data.put("dictList", dictList);
        }
        return data;
	}
}
