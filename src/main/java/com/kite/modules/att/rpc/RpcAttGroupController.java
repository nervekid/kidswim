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
import com.kite.modules.att.command.RpcCourseCorrespondSaleSituationCommand;
import com.kite.modules.att.command.RpcCreateGroupCommand;
import com.kite.modules.att.command.RpcSaleStudentCommand;
import com.kite.modules.att.command.UnGroupLevelCorrespondCountCommand;
import com.kite.modules.att.entity.SerGroup;
import com.kite.modules.att.entity.SerGroupDetails;
import com.kite.modules.att.service.SerCourseService;
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
	@Autowired private SerCourseService serCourseService;
	@Autowired private SerGroupService serGroupService;
	@Autowired private SerGroupDetailsService serGroupDetailsService;
	@Autowired private SystemService systemService;

	/**
	 * 根据条件查找课程对应销售单情况
	 * @param courseAddress
	 * @param learnBeginTime
	 * @param beginDateStr
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "findCourseCorrespondSaleSituation")
	@ResponseBody
	public Map<String, Object> findCourseCorrespondSaleSituation(
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("learnBeginTime") String learnBeginTime,
			@RequestParam("beginDateStr")String beginDateStr,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws ParseException {
		logger.info("进入根据条件查找课程对应销售单情况的接口courseAddress={}, learnBeginTime={}", courseAddress, learnBeginTime);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = DateUtlis.getFistTimeDate(sdf.parse(beginDateStr));
        Date queryEndDate = DateUtlis.getLastTimeDate(beginDate);
        try {
        	RpcCourseCorrespondSaleSituationCommand command = new RpcCourseCorrespondSaleSituationCommand();
            //1.查找课程级别对应销售单人数情况(未分组销售单)
            List<UnGroupLevelCorrespondCountCommand> cList = this.serCourseService.findUnGroupLevelCorrespondCount(courseAddress,
            		learnBeginTime, beginDate, queryEndDate);
            //2.查询课程编号列表
            List<String> codeList = this.serGroupService.findCodeStrListByCondition(courseAddress,
            		learnBeginTime, beginDate, queryEndDate);
            command.setUnGroupLevelCorrespondCountCommandList(cList);
        	command.setCodes(codeList);
        	logger.info("查询根据条件查找课程对应销售单情况成功,status={}", 1);
        	data.put("status", "1");
        	data.put("command", command);

            return data;
        }
        catch(Exception e) {
        	e.printStackTrace();
        	logger.info("查询根据条件查找课程对应销售单情况失败,status={}", 0);
        	data.put("status", "0");
        	return data;
        }

	}


	/**
	 * 根据泳池，时间段开始时间查找销售单学员信息列表
	 * (符合条件的未分组销售单)
	 * @throws ParseException
	 */
	@RequestMapping(value = "findSaleStudentList")
	@ResponseBody
	public Map<String, Object> findSaleStudentList(
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("learnBeginTime") String learnBeginTime,
			@RequestParam("beginDateStr")String beginDateStr,
			@RequestParam("courseLevel")String courseLevel,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) throws ParseException {
		logger.info("进入根据泳池，时间段开始时间查找销售单学员信息(符合条件的未分组销售单)的接口courseAddress={}, learnBeginTime={}", courseAddress, learnBeginTime);
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date beginDate = DateUtlis.getFistTimeDate(sdf.parse(beginDateStr));
        Date queryEndDate = DateUtlis.getLastTimeDate(beginDate);
        List<RpcSaleStudentCommand> tpcSaleStudentCommandList = this.serSaleService.findRpcSaleStudentCommandByAddressAndBeginTime(
        		courseAddress,
        		courseLevel,
        		learnBeginTime,
        		beginDate,
        		queryEndDate);
        data.put("size", tpcSaleStudentCommandList.size());
        if (tpcSaleStudentCommandList.isEmpty()) {
        	logger.info("查询销售单学员信息列表(符合条件的未分组销售单)失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询销售单学员信息列表(符合条件的未分组销售单)成功,status={}", 1);
        	data.put("status", "1");
        	data.put("tpcSaleStudentCommandList", tpcSaleStudentCommandList);
        }
        return data;
	}

	/**
	 * 查找所有泳池
	 */
	@RequestMapping(value = "findGroupByAll")
	@ResponseBody
	public Map<String, Object> findGroupByAll(HttpServletRequest request, HttpServletResponse response, Model model) {
		logger.info("进入查找所有分组的接口");
		Map<String,Object> data =  new HashMap<>();
        data.put("msg", "");
        List<SerGroup> groupList = this.serGroupService.findList(new SerGroup());
        if (groupList.isEmpty()) {
        	logger.info("查询所有分组列表列表失败,status={}", 0);
        	data.put("status", "0");
        }
        else {
        	logger.info("查询所有分组成功,status={}", 1);
        	data.put("status", "1");
        	data.put("groupList", groupList);
        }
        return data;
	}

	/**
	 * 根据泳池，时间段查询分组的接口
	 */
	@RequestMapping(value = "findGroupByAddressAndBeginTime")
	@ResponseBody
	public Map<String, Object> findGroupByAddressAndBeginTime(
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("learnBeginTime") String learnBeginTime,
			HttpServletRequest request,
			HttpServletResponse response,
			Model model) {
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
        	serGroup.setGroupLearnBeginTime(command.getLearnBeginStr());
        	serGroup.setCourseAddress(command.getCourseAddress());
        	serGroup.setCreateBy(this.systemService.getUser(command.getUserId()));
        	if (command.getSaleIds() != null && !command.getSaleIds().isEmpty()) {
        		serGroup.setSaleIds(command.getSaleIds());
        	}
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
