/**
 * KITE
 */
package com.kite.modules.att.web;

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
import com.kite.modules.att.service.SerCourseService;
import com.kite.modules.att.service.SysBaseCoachService;
import com.kite.modules.sys.entity.Dict;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.SystemService;

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
	private SysBaseCoachService sysBaseCoachService;
	@Autowired
	private SystemService systemService;

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

		//給出星期幾字典列表
		Dict weekNumDict = new Dict();
		weekNumDict.setType("week_flag");
		List<Dict> weekNumDictList = this.systemService.listDict(weekNumDict);

		if (serCourse.getDateRange() != null && !serCourse.getDateRange().equals("")) {
    		String [] attr = serCourse.getDateRange().split(" ~ ");
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
	public AjaxJson generateCourseScheduling(@RequestParam("courseAddressFlag") String courseAddressFlag, @RequestParam("coachId") String coachId,
			@RequestParam("beginTimeStr") String beginTimeStr, @RequestParam("endTimeStr") String endTimeStr, @RequestParam("weekNum") String weekNum,
			HttpServletRequest request, HttpServletResponse  response) throws ParseException {
		AjaxJson ajaxJson = new AjaxJson();
		LinkedHashMap<String,Object>  map = new LinkedHashMap<String, Object>();

		//1.獲取開始時間與結束時間
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date beginTime = com.kite.common.utils.date.DateUtils.getTimesmorning(com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(sdf.parse(beginTimeStr)));
		Date endTime = com.kite.common.utils.date.DateUtils.getTimesevening(com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(sdf.parse(endTimeStr)));
		String beginYearMonth =  com.kite.common.utils.date.DateUtils.transformDateToYYYYMM(beginTime);
		String endYearMonth = com.kite.common.utils.date.DateUtils.transformDateToYYYYMM(endTime);
		SimpleDateFormat sdfD = new SimpleDateFormat("yyyy-MM-dd");
		int days = com.kite.common.utils.date.DateUtils.getDateSpace(sdfD.format(beginTime), sdfD.format(endTime));
		if (days < 7) {
			map.put("msg","生成課程失敗,由於生成課程天數小於七天, 無法生成課程！");
			return  ajaxJson;
		}

		//2.獲取年份
		String yearStr = com.kite.common.utils.date.DateUtils.changeDateToYYYY(beginTime);

		//3.根據教練員ID獲取教練員編號
		String coachCode = this.sysBaseCoachService.findCoachCodeByCoachId(coachId);
		if (coachCode == null || coachCode.equals("")) {
			map.put("msg","生成課程失敗,無法找到教練員！");
			return  ajaxJson;
		}

		//4計算壹段時間段內有多少天周幾
		int weekenNum = 0;
		Date first = null;
		weekenNum = com.kite.common.utils.date.DateUtils.calculateTheNumberOfTimesFfTheWeek(beginTime, endTime, Integer.parseInt(weekNum));
		for (int i = 0; i < weekenNum; i++) {
			SerCourse serCourse = new SerCourse();
			serCourse.setCode(yearStr + courseAddressFlag + coachCode);
			serCourse.setCoathId(coachId);
			serCourse.setBeginYearMonth(beginYearMonth);
			serCourse.setEndYearMonth(endYearMonth);

			//核算日期
			if (i == 0) {
				for (int j = 0; j < days; j++) {
					first = com.kite.common.utils.date.DateUtils.getNoon12OclockTimeDate(com.kite.common.utils.date.DateUtils.getPreNumDate(beginTime, j));
					if (String.valueOf(com.kite.common.utils.date.DateUtils.getWeekByDate(first)).equals(weekNum)) {
						//時間段內第壹個符合指定周幾的日期
						serCourse.setCourseDate(first);
						break;
					}
				}
			}
			else {
				first = com.kite.common.utils.date.DateUtils.getPreNumDate(first, 7);
				serCourse.setCourseDate(first);
			}

			//繼續添加
			serCourse.setCourseNum(i + 1);
			serCourse.setCourseAddress(courseAddressFlag);
			serCourse.setStrInWeek(weekNum);
			serCourse.setBeginDate(beginTime);
			serCourse.setEndDate(endTime);

			this.serCourseService.save(serCourse);
		}
		map.put("msg","成功生成課程！");
		ajaxJson.setBody(map);
		return  ajaxJson;
	}

}