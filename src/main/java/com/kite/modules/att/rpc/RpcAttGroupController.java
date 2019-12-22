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
import com.kite.modules.att.command.RpcCreateGroupCommand;
import com.kite.modules.att.command.RpcSaleStudentCommand;
import com.kite.modules.att.entity.SerGroup;
import com.kite.modules.att.entity.SerGroupDetails;
import com.kite.modules.att.service.SerGroupDetailsService;
import com.kite.modules.att.service.SerGroupService;
import com.kite.modules.att.service.SerSaleService;
import com.kite.modules.sys.service.SystemService;

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
	@Autowired private SerGroupService serGroupService;
	@Autowired private SerGroupDetailsService serGroupDetailsService;
	@Autowired private SystemService systemService;
	/**
	 * 根据泳池，时间段开始时间查找销售单学员信息列表
	 * @throws ParseException
	 */
	@RequestMapping(value = "findSaleStudentList")
	@ResponseBody
	public Map<String, Object> findSaleStudentList(@RequestParam("courseAddress") String courseAddress,@RequestParam("learnBeginTime") String learnBeginTime,
			@RequestParam("beginDateStr")String beginDateStr, @RequestParam("endDateStr")String endDateStr, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		logger.info("进入根据泳池，时间段开始时间查找销售单学员信息的接口courseAddress={}, learnBeginTime={}", courseAddress, learnBeginTime);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = DateUtlis.getFistTimeDate(sdf.parse(beginDateStr));
        Date endDate = DateUtlis.getLastTimeDate(sdf.parse(endDateStr));
        List<RpcSaleStudentCommand> tpcSaleStudentCommandList = this.serSaleService.findRpcSaleStudentCommandByAddressAndBeginTime(courseAddress,
        		learnBeginTime, beginDate, endDate);
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
	@RequestMapping(value = "findGroupByAddressAndBeginTime")
	@ResponseBody
	public Map<String, Object> findGroupByAddressAndBeginTime(@RequestParam("courseAddress") String courseAddress,@RequestParam("learnBeginTime") String learnBeginTime,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入根据泳池，时间段查询分组的接口");
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        List<SerGroup> groupList = this.serGroupService.findSerGroupListByAddressAndBeginTime(courseAddress, learnBeginTime);
        if (groupList.isEmpty()) {
        	logger.info("查询分组列表列表失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询分组列表成功,status={}", 1);
        	data.put("status", "1");
        	data.put("groupList", groupList);
        }
        return data;
	}

	/**
	 * 创建分组的接口
	 */
	@RequestMapping(value = "createSerGroup")
	@ResponseBody
	public Map<String, Object> createSerGroup(@RequestBody RpcCreateGroupCommand command,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入创建分组接口");
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        try {
        	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        	//生成分组编号 G+年份+泳池編號+課程等級+四位數流水碼 例如 G2020HH-CA0023
        	StringBuffer codeStr = new StringBuffer();
        	String year = DateUtlis.changeDateToYYYY(new Date());
        	codeStr.append("G");
        	codeStr.append(year);
        	codeStr.append(command.getCourseAddress());
        	codeStr.append("-");
        	codeStr.append(command.getCourseLeavel());
        	int num = this.serGroupService.findSerGroupByAddressNum(command.getCourseAddress()) + 1;
        	String numStr = com.kite.common.utils.date.DateUtils.transformThousandBitNumString(num);
        	codeStr.append(numStr);
        	String code = codeStr.toString();
        	SerGroup serGroup = new SerGroup();
        	serGroup.setCode(code);
        	serGroup.setCoathId(command.getCoathId());
        	serGroup.setGroupBeginTime(sdf.parse(command.getBeginDate()));
        	serGroup.setGroupEndTimeTime(sdf.parse(command.getEndDate()));
        	serGroup.setGroupLearnBeginTime(command.getLearnBeginStr());
        	serGroup.setCourseAddress(command.getCourseAddress());
        	serGroup.setCreateBy(this.systemService.getUser(command.getUserId()));
        	this.serGroupService.save(serGroup);
        	logger.info("创建分组成功，编号为={}", code);
        	data.put("status", "1");
        	data.put("code", code);
        }
        catch(Exception E) {
        	logger.info("创建分组失败");
        	data.put("status", "0");
        }


        return data;
	}

	/**
	 * 插入分组的接口
	 */
	@RequestMapping(value = "inertSerGroupDetails")
	@ResponseBody
	public Map<String, Object> inertSerGroupDetails(@RequestBody RpcAttGroupDetailsCommand command,
			HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入插入分组的接口");
		Map<String,Object> data =  new HashMap<>();
		try {
			SerGroup serGroup = this.serGroupService.findSerGroupByCode(command.getGroupCode());
			if (serGroup != null) {
				SerGroupDetails serGroupDetails = new SerGroupDetails();
				serGroupDetails.setSaleId(command.getSaleId());
				serGroupDetails.setGroupId(serGroup.getId());
				serGroupDetails.setCreateBy(this.systemService.getUser(command.getUserId()));
				this.serGroupDetailsService.save(serGroupDetails);
				logger.info("加入分组明细成功");
				data.put("status", "1");
			}
			else {
				logger.info("无法根据编号找到分组,分组编号code=", command.getGroupCode());
				data.put("status", "0");
			}
		}
		catch(Exception e) {
			logger.info("加入分组明细失败");
			data.put("status", "0");
		}
        data.put("msg", "");
        return data;
	}


}
