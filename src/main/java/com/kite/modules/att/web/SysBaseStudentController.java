/**
 * KITE
 */
package com.kite.modules.att.web;

import java.util.Date;
import java.util.HashMap;
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
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.att.entity.SysBaseStudent;
import com.kite.modules.att.entity.SysBaseStudentImport;
import com.kite.modules.att.service.SysBaseStudentService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.SystemService;

/**
 * 學員Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/sysBaseStudent")
public class SysBaseStudentController extends BaseController implements BasicVerification {

	@Autowired
	private SysBaseStudentService sysBaseStudentService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SystemService systemService;

	/*** 是否導入錯誤提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysBaseStudent get(@RequestParam(required=false) String id) {
		SysBaseStudent entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBaseStudentService.get(id);
		}
		if (entity == null){
			entity = new SysBaseStudent();
		}
		return entity;
	}

	/**
	 * 學員列表頁面
	 */
	@RequiresPermissions("att:sysBaseStudent:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysBaseStudent sysBaseStudent, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysBaseStudent> page = sysBaseStudentService.findPage(new Page<SysBaseStudent>(request, response), sysBaseStudent);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/sysBaseStudentList";
	}

	/**
	 * 查看，增加，編輯學員表單頁面
	 */
	@RequiresPermissions(value={"att:sysBaseStudent:view","att:sysBaseStudent:add","att:sysBaseStudent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysBaseStudent sysBaseStudent, Model model) {
		model.addAttribute("sysBaseStudent", sysBaseStudent);
		if(sysBaseStudent.getId()==null){
			// sysBaseStudent.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//設置編碼
		}
		return "modules/att/sysBaseStudentForm";
	}

	/**
	 * 查看打印學員表單頁面
	 */
	@RequiresPermissions(value={"att:sysBaseStudent:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysBaseStudent sysBaseStudent, Model model) {
		model.addAttribute("sysBaseStudent", sysBaseStudent);
		return "modules/att/sysBaseStudentView";
	}

	/**
	 * 保存學員
	 */
	@RequiresPermissions(value={"att:sysBaseStudent:add","att:sysBaseStudent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysBaseStudent sysBaseStudent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysBaseStudent)){
			return form(sysBaseStudent, model);
		}
		if(!sysBaseStudent.getIsNewRecord()){//編輯表單保存
			SysBaseStudent t = sysBaseStudentService.get(sysBaseStudent.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(sysBaseStudent, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			sysBaseStudentService.save(t);//保存
		}else{//新增表單保存
			//1.計算學員編號 入學年份+月份+流水碼 如:201901000001
			Date nowDate = new Date();
			Date beginTime = com.kite.common.utils.date.DateUtils.getTimesmorning(com.kite.common.utils.date.DateUtils.firstDateInMonth(nowDate));
			Date endTime = com.kite.common.utils.date.DateUtils.getTimesevening(com.kite.common.utils.date.DateUtils.lastDateInMonth(nowDate));
			String yearMonth = com.kite.common.utils.date.DateUtils.transformDateToYYYYMM(new Date());
			int nowStudents = this.sysBaseStudentService.findCountOfStudents(beginTime, endTime) + 1;
			String code = yearMonth + com.kite.common.utils.date.DateUtils.transformNumString(nowStudents);
			sysBaseStudent.setCode(code);
			sysBaseStudentService.save(sysBaseStudent);//保存
		}
		addMessage(redirectAttributes, "保存學員成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?menuId="+sysBaseStudent.getMenuId();
	}

	/**
	 * 刪除學員
	 */
	@RequiresPermissions("att:sysBaseStudent:del")
	@RequestMapping(value = "delete")
	public String delete(SysBaseStudent sysBaseStudent, RedirectAttributes redirectAttributes) {
		sysBaseStudentService.delete(sysBaseStudent);
		addMessage(redirectAttributes, "刪除學員成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?repage";
	}

	/**
	 * 批量刪除學員
	 */
	@RequiresPermissions("att:sysBaseStudent:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysBaseStudentService.delete(sysBaseStudentService.get(id));
		}
		addMessage(redirectAttributes, "刪除學員成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:sysBaseStudent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysBaseStudent sysBaseStudent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "學員"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysBaseStudent> page = sysBaseStudentService.findPage(new Page<SysBaseStudent>(request, response, -1), sysBaseStudent);
    		new ExportExcel("學員", SysBaseStudent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出學員記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?menuId="+sysBaseStudent.getMenuId();
    }

	/**
	 * 導入Excel數據

	 */
	@RequiresPermissions("att:sysBaseStudent:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, HttpServletResponse response, String menuId, RedirectAttributes redirectAttributes) {
		try {
			Date nowDate = new Date();
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			this.check(ei);
			if (!ei.isCheckOk) {
				this.isTip = true;
				ei.write(response, "學員列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysBaseStudentImport> list = ei.getDataList(SysBaseStudentImport.class);
				for (SysBaseStudentImport sysBaseStudentImport : list){
					try{
						//1.計算學員編號 入學年份+月份+流水碼 如:201901000001
						Date beginTime = com.kite.common.utils.date.DateUtils.getTimesmorning(com.kite.common.utils.date.DateUtils.firstDateInMonth(nowDate));
						Date endTime = com.kite.common.utils.date.DateUtils.getTimesevening(com.kite.common.utils.date.DateUtils.lastDateInMonth(nowDate));
						String yearMonth = com.kite.common.utils.date.DateUtils.transformDateToYYYYMM(new Date());
						int nowStudents = this.sysBaseStudentService.findCountOfStudents(beginTime, endTime) + 1;
						String code = yearMonth + com.kite.common.utils.date.DateUtils.transformNumString(nowStudents);

						//字典轉值
						String sexValue = this.systemService.findDictValueByTypeAndLabel("sex_flag", sysBaseStudentImport.getSex());
						String studiedSwimValue = this.systemService.findDictValueByTypeAndLabel("yes_no", sysBaseStudentImport.getStudiedSwimFlag());
						String drownedValue = this.systemService.findDictValueByTypeAndLabel("yes_no", sysBaseStudentImport.getDrownedFlag());
						String courseLevelValue = this.systemService.findDictValueByTypeAndLabel("courseLevel_flag", sysBaseStudentImport.getCourseLevelFlag());
						String drownedAddressValue = this.systemService.findDictValueByTypeAndLabel("drowned_address_flag", sysBaseStudentImport.getDrownedAddressFlag());
						SysBaseStudent sysBaseStudent = new SysBaseStudent();
						sysBaseStudent.setCode(code);
						sysBaseStudent.setNameCn(sysBaseStudentImport.getNameCn());
						sysBaseStudent.setNameEn(sysBaseStudentImport.getNameEn());
						sysBaseStudent.setIdNo(sysBaseStudentImport.getIdNo());
						sysBaseStudent.setEmail(sysBaseStudentImport.getEmail());
						sysBaseStudent.setPhone(sysBaseStudentImport.getPhone());
						sysBaseStudent.setBirthday(sysBaseStudentImport.getBirthday());
						sysBaseStudent.setContactAddress(sysBaseStudentImport.getContactAddress());
						sysBaseStudent.setAttendingSchool(sysBaseStudentImport.getAttendingSchool());
						sysBaseStudent.setGrade(sysBaseStudentImport.getGrade());
						sysBaseStudent.setStudySwimmingOrgan(sysBaseStudentImport.getStudySwimmingOrgan());
						sysBaseStudent.setStudiedSwimmingStyle(sysBaseStudentImport.getStudiedSwimmingStyle());
						sysBaseStudent.setDrownedAge(sysBaseStudentImport.getDrownedAge());
						sysBaseStudent.setLongTermDisease(sysBaseStudentImport.getLongTermDisease());
						sysBaseStudent.setLongTermMedicine(sysBaseStudentImport.getLongTermMedicine());
						sysBaseStudent.setContactPhone(sysBaseStudentImport.getContactPhone());
						sysBaseStudent.setContactRelationship(sysBaseStudentImport.getContactRelationship());
						sysBaseStudent.setUrgentRelationship(sysBaseStudentImport.getUrgentRelationship());
						sysBaseStudent.setUrgentPhone(sysBaseStudentImport.getUrgentPhone());
						sysBaseStudent.setGuardianName(sysBaseStudentImport.getGuardianName());
						sysBaseStudent.setGuardianPhone(sysBaseStudentImport.getGuardianPhone());
						sysBaseStudent.setGuardianIdNo(sysBaseStudentImport.getGuardianIdNo());
						sysBaseStudent.setGuardianRelationship(sysBaseStudentImport.getGuardianRelationship());
						sysBaseStudent.setFacebook(sysBaseStudentImport.getFacebook());

						sysBaseStudent.setSex(sexValue);
						sysBaseStudent.setStudiedSwimFlag(studiedSwimValue);
						sysBaseStudent.setDrownedFlag(drownedValue);
						sysBaseStudent.setCourseLevelFlag(courseLevelValue);
						sysBaseStudent.setDrownedAddressFlag(drownedAddressValue);
						sysBaseStudentService.save(sysBaseStudent);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失敗 "+failureNum+" 條學員記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條學員記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入學員失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?menuId="+menuId;
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
	 * 下載導入學員數據模板
	 */
	@RequiresPermissions("att:sysBaseStudent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "學員數據導入模板.xlsx";
    		List<SysBaseStudent> list = Lists.newArrayList();
    		new ExportExcel("學員數據", SysBaseStudentImport.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?repage";
    }



	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String subName, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<SysBaseStudent> list = sysBaseStudentService.findByName(subName);
		for (int i=0; i<list.size(); i++){
			SysBaseStudent e = list.get(i);

			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getCode());
			map.put("name", e.getNameCn() + "(" +e.getNameEn()+ ")");

			mapList.add(map);
		}
		return mapList;
	}
}