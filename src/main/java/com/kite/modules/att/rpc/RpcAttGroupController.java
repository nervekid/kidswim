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

import com.kite.modules.att.command.RpcSaleStudentCommand;
import com.kite.modules.att.service.SerSaleService;
import com.kite.modules.sys.entity.Dict;

/**
 * 泳班分组远程接口类
 * @author lyb
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "/att/group")
public class RpcAttGroupController {

	private static Logger logger = LoggerFactory.getLogger(RpcAttGroupController.class);

	@Autowired private SerSaleService serSaleService;

	/**
	 * 根据泳池，时间段开始时间查找销售单学员信息列表
	 */
	@RequestMapping(value = "findSaleStudentList")
	@ResponseBody
	public Map<String, Object> findSaleStudentList(@RequestParam("courseAddress") String courseAddress,@RequestParam("learnBeginTime") String learnBeginTime,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入根据泳池，时间段开始时间查找销售单学员信息的接口courseAddress={}, learnBeginTime={}", courseAddress, learnBeginTime);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");

        List<RpcSaleStudentCommand> tpcSaleStudentCommandList = this.serSaleService.findRpcSaleStudentCommandByAddressAndBeginTime(courseAddress, learnBeginTime);

        if (tpcSaleStudentCommandList.isEmpty()) {
        	logger.info("查询销售单学员信息列表失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询销售单学员信息列表成功,status={}", 1);
        	data.put("status", "1");
        	data.put("tpcSaleStudentCommandList", tpcSaleStudentCommandList);
        }
        return data;
	}

	/**
	 * 根据泳池，时间段查询分组的接口
	 */

	/**
	 * 创建分组几插入分组明细的接口
	 */
}
