package com.kite.modules.att.rpc;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kite.common.utils.DateUtlis;
import com.kite.modules.att.command.RpcAllCourseBeginTimeCommand;
import com.kite.modules.att.command.RpcRollCallCommand;
import com.kite.modules.att.command.RpcRollCallShowCommand;
import com.kite.modules.att.service.SerCourseService;
import com.kite.modules.att.service.SerRollCallService;
import com.kite.modules.att.service.SerSaleService;

/**
 * 泳班点名远程接口类
 * @author lyb
 * @version 2019-12-20
 */
@Controller
@RequestMapping(value = "/att/rollcall")
public class RpcAttRollCallController {

	private static Logger logger = LoggerFactory.getLogger(RpcAttRollCallController.class);

	@Autowired private SerSaleService serSaleService;
	@Autowired private SerRollCallService serRollCallService;
	@Autowired private SerCourseService serCourseService;

	/**
	 * 根据条件(课程地址，日期，上课开始时间，上课结束时间)查找点名单
	 * @throws ParseException
	 */
	@RequestMapping(value = "findAllBeginTimeByAddressAndDate")
	@ResponseBody
	public Map<String, Object> findAllBeginTimeByAddressAndDate(
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("dateStr") String dateStr,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws ParseException {
		logger.info("进入根据条件(课程地址，日期)查找课堂明细开始时间的接口"
				+ "courseAddress={}, dateStr={}",
				courseAddress, dateStr);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = DateUtlis.getFistTimeDate(sdf.parse(dateStr));
        Date endDate = DateUtlis.getLastTimeDate(beginDate);
        List<RpcAllCourseBeginTimeCommand> beginTimeList = this.serCourseService.findAllCourseBeginTimeByAddressAndBeginTime(
        		courseAddress,
        		beginDate,
        		endDate);
        data.put("size", beginTimeList.size());
        if (beginTimeList.isEmpty()) {
        	logger.info("查询根据条件(课程地址，日期)查找课堂明细开始时间失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询根据条件(课程地址，日期)查找课堂明细开始时间成功,status={}", 1);
        	data.put("status", "1");
        	data.put("beginTimeList", beginTimeList);
        }
        return data;
	}

	/**
	 * 根据条件(课程地址，日期，上课开始时间，上课结束时间)查找点名单
	 * @throws ParseException
	 */
	@RequestMapping(value = "findRollCallSaleStudentList")
	@ResponseBody
	public Map<String, Object> findRollCallSaleStudentList(
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("dateStr") String dateStr,
			@RequestParam("beginTimeStr")String beginTimeStr,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws ParseException {
		logger.info("进入根据条件(课程地址，日期，上课开始时间，上课结束时间)查找点名单的接口"
				+ "courseAddress={}, dateStr={},beginTimeStr={}",
				courseAddress, dateStr, beginTimeStr);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date beginDate = DateUtlis.getFistTimeDate(sdf.parse(dateStr));
        Date endDate = DateUtlis.getLastTimeDate(beginDate);

        String beginTimeStrHm = beginTimeStr.substring(0, 2) + ":" + beginTimeStr.substring(2, 4);
        String beginTimeHoleStr = dateStr + " " + beginTimeStrHm + ":" + "00";
        Date beginTime = sdd.parse(beginTimeHoleStr);

        List<RpcRollCallShowCommand> rollCallShowList = this.serSaleService.findRpcRollCallShowCommandByCondition(
        		courseAddress,
        		beginDate,
        		endDate,
        		beginTime);
        data.put("size", rollCallShowList.size());
        if (rollCallShowList.isEmpty()) {
        	logger.info("查询根据条件(课程地址，日期，上课开始时间，上课结束时间)查找点名单失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询根据条件(课程地址，日期，上课开始时间，上课结束时间)查找点名单成功,status={}", 1);
        	data.put("status", "1");
        	data.put("rollCallShowList", rollCallShowList);
        }
        return data;
	}

	/**
	 * 点名接口
	 * @throws ParseException
	 */
	@RequestMapping(value = "rollCall")
	@ResponseBody
	public Map<String, Object> rollCall(
			@RequestBody RpcRollCallCommand command,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws ParseException {
		logger.info("进入点名接口,当前点名人数={}", command.getRollCallCommandList().size());
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        try {
        	this.serRollCallService.rollCallByList(command);
        	logger.info("点名成功,当前点名={}", command.getRollCallCommandList().size());
        	data.put("status", "1");
        	data.put("rollCallNum", command.getRollCallCommandList().size());
        }
        catch(Exception E) {
        	logger.info("点名失败");
        	data.put("status", "0");
        }
        return data;
	}

}
