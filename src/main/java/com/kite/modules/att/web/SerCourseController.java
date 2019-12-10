/**
 * KITE
 */
package com.kite.modules.att.web;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kite.common.config.Global;
import com.kite.common.json.AjaxJson;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.att.entity.SerCourse;
import com.kite.modules.att.entity.SerCourseDetails;
import com.kite.modules.att.entity.SysBaseStudent;
import com.kite.modules.att.enums.KidSwimDictEnum;
import com.kite.modules.att.service.SerCourseDetailsService;
import com.kite.modules.att.service.SerCourseLevelCostService;
import com.kite.modules.att.service.SerCourseService;
import com.kite.modules.sys.entity.Dict;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.DictUtils;

/**
 * 課程Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serCourse")
public class SerCourseController extends BaseController implements BasicVerification {

	@Autowired
	private SerCourseService serCourseService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private SerCourseDetailsService serCourseDetailsService;
	@Autowired
	private SerCourseLevelCostService serCourseLevelCostService;

	/*** 是否導入錯誤提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerCourse get(@RequestParam(required=false) String id) {
		SerCourse entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serCourseService.get(id);
		}
		if (entity == null){
			entity = new SerCourse();
		}
		return entity;
	}

	/**
	 * 課程列表頁面
	 * @throws ParseException
	 */
	@RequiresPermissions("att:serCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerCourse serCourse, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		Page<SerCourse> page = serCourseService.findPage(new Page<SerCourse>(request, response), serCourse);

		//給出泳池地址字典列表
		Dict courseAddressDict = new Dict();
		courseAddressDict.setType("course_addrese_flag");
		List<Dict> courseAddressDictList = this.systemService.listDict(courseAddressDict);

		//給出课程级别字典列表
		Dict courseLevelDict = new Dict();
		courseLevelDict.setType("course_level");
		List<Dict> courseLevelDictList = this.systemService.listDict(courseLevelDict);

		//給出星期幾字典列表
		Dict weekNumDict = new Dict();
		weekNumDict.setType("week_flag");
		List<Dict> weekNumDictList = this.systemService.listDict(weekNumDict);

		if (serCourse.getDateRange() != null && !serCourse.getDateRange().equals("")) {
    		String [] attr = serCourse.getDateRange().split(" 至 ");
    		String beginStr = attr[0];
    		String endStr = attr[1];
    		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    		Date begin = com.kite.common.utils.date.DateUtils.getFistTimeDate(sdf.parse(beginStr));
    		Date end = com.kite.common.utils.date.DateUtils.getLastTimeDate(sdf.parse(endStr));
    		serCourse.setBeginTime(begin);
    		serCourse.setEndTime(end);
    	}

		model.addAttribute("page", page);
		model.addAttribute("courseAddressDictList", courseAddressDictList);
		model.addAttribute("courseLevelDictList", courseLevelDictList);
		model.addAttribute("weekNumDictList", weekNumDictList);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serCourseList";
	}


	/**
	 * 查看，增加，編輯課程表單頁面
	 */
	@RequiresPermissions(value={"att:serCourse:view","att:serCourse:add","att:serCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerCourse serCourse, Model model) {
		model.addAttribute("serCourse", serCourse);
		if(serCourse.getId()==null){
			// serCourse.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//設置編碼
		}
		return "modules/att/serCourseForm";
	}

	/**
	 * 查看打印課程表單頁面
	 */
	@RequiresPermissions(value={"att:serCourse:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerCourse serCourse, Model model) {
		List<SerCourseDetails> serCourseDetailsList = this.serCourseService.findSerCourseDetailsByCourseId(serCourse.getId());
		for (int i = 0; i < serCourseDetailsList.size(); i++) {
			if (serCourseDetailsList.get(i).getCoathId() == null || serCourseDetailsList.get(i).getCoathId().equals("")) {
				serCourseDetailsList.get(i).setCoathName("暂缺");
			}
		}
		serCourse.setSerCourseDetailsList(serCourseDetailsList);
		model.addAttribute("yesNoList", DictUtils.getDictList("yes_no"));
		model.addAttribute("serCourse", serCourse);
		return "modules/att/serCourseView";
	}

	/**
	 * 保存課程
	 */
	@RequiresPermissions(value={"att:serCourse:add","att:serCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerCourse serCourse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serCourse)){
			return form(serCourse, model);
		}
		if(!serCourse.getIsNewRecord()){//編輯表單保存
			SerCourse t = serCourseService.get(serCourse.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(serCourse, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			serCourseService.save(t);//保存
		}else{//新增表單保存
			serCourseService.save(serCourse);//保存
		}
		addMessage(redirectAttributes, "保存課程成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?menuId="+serCourse.getMenuId();
	}

	/**
	 * 刪除課程
	 */
	@RequiresPermissions("att:serCourse:del")
	@RequestMapping(value = "delete")
	public String delete(SerCourse serCourse, RedirectAttributes redirectAttributes) {
		serCourseService.delete(serCourse);
		addMessage(redirectAttributes, "刪除課程成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
	}

	/**
	 * 批量刪除課程
	 */
	@RequiresPermissions("att:serCourse:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serCourseService.delete(serCourseService.get(id));
		}
		addMessage(redirectAttributes, "刪除課程成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:serCourse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerCourse serCourse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "課程"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerCourse> page = serCourseService.findPage(new Page<SerCourse>(request, response, -1), serCourse);
    		new ExportExcel("課程", SerCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出課程記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?menuId="+serCourse.getMenuId();
    }

	/**
	 * 導入Excel數據

	 */
	@RequiresPermissions("att:serCourse:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, HttpServletResponse response, String menuId, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			this.check(ei);
			if (!ei.isCheckOk) {
				this.isTip = true;
				ei.write(response, "課程列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerCourse> list = ei.getDataList(SerCourse.class);
				for (SerCourse serCourse : list){
					try{
						serCourseService.save(serCourse);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失敗 "+failureNum+" 條課程記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條課程記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入課程失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?menuId="+menuId;
    }

	@Override
	public void check(ImportExcel ei) {

	}

	@ResponseBody
	@RequestMapping(value = "import/tip")
	public Map<String, String> tip(RedirectAttributes redirectAttributes, HttpServletResponse response) {
		Map<String, String> map = new HashMap<String, String>();
		if (this.isTip) {
			map.put("isAlert", "1");
			return map;
		}
		map.put("isAlert", "0");
		return map;
	}

	/**
	 * 下載導入課程數據模板
	 */
	@RequiresPermissions("att:serCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "課程數據導入模板.xlsx";
    		List<SerCourse> list = Lists.newArrayList();
    		new ExportExcel("課程數據", SerCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
    }

	/**
	 * 功能：兌換用戶收到且未兌換打的飛象卡，更新飛象卡的狀態，更新錢包的金額
	 * @param userwallet
	 * @param request
	 * @param response
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping(value = "/generateCourseScheduling")
	@ResponseBody
	public AjaxJson generateCourseScheduling(
			@RequestParam("courseLevel") String courseLevel,
			@RequestParam("beginLearn") String beginLearn,
			@RequestParam("endLearn") String endLearn,
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("weekNum") String weekNum,
			@RequestParam("beginTimeStr") String beginTimeStr,
			@RequestParam("endTimeStr") String endTimeStr,
			@RequestParam("assessmentDateStr") String assessmentDateStr,
			HttpServletRequest request,
			HttpServletResponse  response) throws ParseException {

		AjaxJson ajaxJson = new AjaxJson();
		LinkedHashMap<String,Object>  map = new LinkedHashMap<String, Object>();

		//1.獲取開始時間與結束時間以及评估日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date beginTime = com.kite.common.utils.date.DateUtils.getTimesmorning(sdf.parse(beginTimeStr));
		Date endTime = com.kite.common.utils.date.DateUtils.getTimesevening(sdf.parse(endTimeStr));
		Date assessmentDate = com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(sdf.parse(assessmentDateStr));

		int days = com.kite.common.utils.date.DateUtils.getDateSpace(sdf.format(beginTime), sdf.format(endTime));
		if (days < 7) {
			map.put("msg","生成課程失敗,由於生成課程天數小於七天, 無法生成課程！");
			return  ajaxJson;
		}

		//2.获取课程堂数
		int weekenNum = 0;
		Date first = null;
		weekenNum = com.kite.common.utils.date.DateUtils.calculateTheNumberOfTimesFfTheWeek(beginTime, endTime, Integer.parseInt(weekNum));

		//3.后去课程编号
		String yearStr = com.kite.common.utils.date.DateUtils.changeDateToYYYY(beginTime);
		int count = this.serCourseService.findCountByLevelAndAddress(courseLevel, courseAddress) + 1;
		String countStr = com.kite.common.utils.date.DateUtils.transformHundredBitNumString(count);
		String code = yearStr + courseAddress + "-" + courseLevel + countStr; //年份+地点编号+ - +课程对应等级+百位流水号 例如:2019MS-CCOO1 按照规则编码

		//3.获取上课的开始时间与结束时间HH:mm形式
		String learnBeginTimeStr = beginLearn.replace(":", "").substring(0, beginLearn.replace(":", "").length() - 2);
		String learnEndTimeTimeStr = endLearn.replace(":", "").substring(0, endLearn.replace(":", "").length() - 2);

		//4.计算费用
		BigDecimal costAmount = this.serCourseLevelCostService.findCostAmountByCourseAddressAndCourseLevelFlag(courseLevel, courseAddress);

		//5.写入课程表
		SerCourse serCourse = new SerCourse();
		serCourse.setCode(code);
		serCourse.setCourseLevel(courseLevel);
		serCourse.setCourseBeginTime(beginTime);
		serCourse.setCourseEndTimeTime(endTime);
		serCourse.setLearnBeginTime(learnBeginTimeStr);
		serCourse.setLearnEndTimeTime(learnEndTimeTimeStr);
		serCourse.setLearnNum(weekenNum);
		serCourse.setCourseAddress(courseAddress);
		serCourse.setStrInWeek(weekNum);
		serCourse.setAssessmentDate(assessmentDate);
		serCourse.setCourseFee(costAmount);
		this.serCourseService.save(serCourse);

		String courseId = this.serCourseService.findCourseIdByCode(code);

		//5.計算壹段時間段內有多少天周幾
		for (int i = 0; i < weekenNum; i++) {
			SerCourseDetails serCourseDetails = new SerCourseDetails();
			serCourseDetails.setCourseId(courseId);
			serCourseDetails.setCoathId(null);
			serCourseDetails.setRollCallStatusFlag(KidSwimDictEnum.yesNo.否.getName());
			//核算日期
			if (i == 0) {
				for (int j = 0; j < days; j++) {
					first = com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(com.kite.common.utils.date.DateUtils.getPreNumDate(beginTime, j));
					if (String.valueOf(com.kite.common.utils.date.DateUtils.getWeekByDate(first)).equals(weekNum)) {
						//時間段內第壹個符合指定周幾的日期
						serCourseDetails.setLearnDate(first);
						String beginlearnTimeStr = sdf.format(first) + " " + beginLearn;
						String endLearnTimeStr = sdf.format(first) + " " + endLearn;
						Date beginlearnTime = sdfTime.parse(beginlearnTimeStr);
						Date endLearnTime = sdfTime.parse(endLearnTimeStr);
						serCourseDetails.setLearnBeginDate(beginlearnTime);
						serCourseDetails.setLearnEndDate(endLearnTime);
						break;
					}
				}
			}
			else {
				first = com.kite.common.utils.date.DateUtils.getPreNumDate(first, 7);
				serCourseDetails.setLearnDate(first);
				String beginlearnTimeStr = sdf.format(first) + " " + beginLearn;
				String endLearnTimeStr = sdf.format(first) + " " + endLearn;
				Date beginlearnTime = sdfTime.parse(beginlearnTimeStr);
				Date endLearnTime = sdfTime.parse(endLearnTimeStr);
				serCourseDetails.setLearnBeginDate(beginlearnTime);
				serCourseDetails.setLearnEndDate(endLearnTime);
			}

			//繼續添加
			this.serCourseDetailsService.save(serCourseDetails);
		}
		map.put("msg","成功生成課程！");
		ajaxJson.setBody(map);
		return  ajaxJson;
	}


	/**
	 * 功能：查询课程预生成结果
	 */
	@RequestMapping(value = "preGeneration")
	@ResponseBody
	public AjaxJson preGeneration(
			@RequestParam("courseLevel") String courseLevel,
			@RequestParam("beginLearn") String beginLearn,
			@RequestParam("endLearn") String endLearn,
			@RequestParam("courseAddress") String courseAddress,
			@RequestParam("weekNum") String weekNum,
			@RequestParam("beginTimeStr") String beginTimeStr,
			@RequestParam("endTimeStr") String endTimeStr,
			@RequestParam("assessmentDateStr") String assessmentDateStr,
			HttpServletRequest request,
			HttpServletResponse  response){
		AjaxJson ajaxJson = new AjaxJson();
		LinkedHashMap<String,Object>  map = new LinkedHashMap<String, Object>();
		try {
			//1.獲取開始時間與結束時間以及评估日期
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			SimpleDateFormat sdfTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date beginTime = com.kite.common.utils.date.DateUtils.getTimesmorning(sdf.parse(beginTimeStr));
			Date endTime = com.kite.common.utils.date.DateUtils.getTimesevening(sdf.parse(endTimeStr));
			Date assessmentDate = com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(sdf.parse(assessmentDateStr));

			int days = com.kite.common.utils.date.DateUtils.getDateSpace(sdf.format(beginTime), sdf.format(endTime));
			if (days < 7) {
				map.put("msg","生成課程失敗,由於生成課程天數小於七天, 無法生成課程！");
				return  ajaxJson;
			}

			//2.获取课程堂数
			int weekenNum = 0;
			Date first = null;
			weekenNum = com.kite.common.utils.date.DateUtils.calculateTheNumberOfTimesFfTheWeek(beginTime, endTime, Integer.parseInt(weekNum));

			//3.后去课程编号
			String yearStr = com.kite.common.utils.date.DateUtils.changeDateToYYYY(beginTime);
			int count = this.serCourseService.findCountByLevelAndAddress(courseLevel, courseAddress) + 1;
			String countStr = com.kite.common.utils.date.DateUtils.transformHundredBitNumString(count);
			String code = yearStr + courseAddress + "-" + courseLevel + countStr; //年份+地点编号+ - +课程对应等级+百位流水号 例如:2019MS-CCOO1 按照规则编码

			//3.获取上课的开始时间与结束时间HH:mm形式
			String learnBeginTimeStr = beginLearn.replace(":", "").substring(0, beginLearn.replace(":", "").length() - 2);
			String learnEndTimeTimeStr = endLearn.replace(":", "").substring(0, endLearn.replace(":", "").length() - 2);

			//4.计算费用
			BigDecimal costAmount = this.serCourseLevelCostService.findCostAmountByCourseAddressAndCourseLevelFlag(courseLevel, courseAddress);

			//5.获取星期几
			String weekNumStr = DictUtils.getDictLabel(weekNum, "week_flag", null);

			//5.計算壹段時間段內有多少天周幾
			SimpleDateFormat ss = new SimpleDateFormat("M月d日");
			StringBuilder strBulider = new StringBuilder();
			for (int i = 0; i < weekenNum; i++) {
				//核算日期
				if (i == 0) {
					for (int j = 0; j < days; j++) {
						first = com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(com.kite.common.utils.date.DateUtils.getPreNumDate(beginTime, j));
						if (String.valueOf(com.kite.common.utils.date.DateUtils.getWeekByDate(first)).equals(weekNum)) {
							//時間段內第壹個符合指定周幾的日期
							strBulider.append(ss.format(first));
							strBulider.append(",");
							break;
						}
					}
				}
				else {
					first = com.kite.common.utils.date.DateUtils.getPreNumDate(first, 7);
					strBulider.append(ss.format(first));
					strBulider.append(",");
				}
			}

			map.put("code", code);
			map.put("weekenNum", weekenNum);
			map.put("dates", strBulider.toString());
			map.put("weekNumStr",weekNumStr);
			map.put("costAmount",costAmount);

			ajaxJson.setBody(map);
		}
		catch (Exception e){
			e.printStackTrace();
		}
		return  ajaxJson;
	}


	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String code, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SerCourse> list = this.serCourseService.findLikeByCode(code);
		for (int i=0; i<list.size(); i++){
			SerCourse e = list.get(i);

			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getCode());
			map.put("name", e.getCode());

			mapList.add(map);
		}
		return mapList;
	}

}