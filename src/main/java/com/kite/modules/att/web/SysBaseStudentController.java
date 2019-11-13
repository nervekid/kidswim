/**
 * KITE
 */
package com.kite.modules.att.web;

import java.util.List;
import java.util.HashMap;
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
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.att.entity.SysBaseStudent;
import com.kite.modules.att.service.SysBaseStudentService;

/**
 * 学员Controller
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

	/*** 是否导入错误提示*/
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
	 * 学员列表页面
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
	 * 查看，增加，编辑学员表单页面
	 */
	@RequiresPermissions(value={"att:sysBaseStudent:view","att:sysBaseStudent:add","att:sysBaseStudent:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysBaseStudent sysBaseStudent, Model model) {
		model.addAttribute("sysBaseStudent", sysBaseStudent);
		if(sysBaseStudent.getId()==null){
			// sysBaseStudent.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/sysBaseStudentForm";
	}

	/**
	 * 查看打印学员表单页面
	 */
	@RequiresPermissions(value={"att:sysBaseStudent:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysBaseStudent sysBaseStudent, Model model) {
		model.addAttribute("sysBaseStudent", sysBaseStudent);
		return "modules/att/sysBaseStudentView";
	}

	/**
	 * 保存学员
	 */
	@RequiresPermissions(value={"att:sysBaseStudent:add","att:sysBaseStudent:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysBaseStudent sysBaseStudent, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysBaseStudent)){
			return form(sysBaseStudent, model);
		}
		if(!sysBaseStudent.getIsNewRecord()){//编辑表单保存
			SysBaseStudent t = sysBaseStudentService.get(sysBaseStudent.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysBaseStudent, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysBaseStudentService.save(t);//保存
		}else{//新增表单保存
			sysBaseStudentService.save(sysBaseStudent);//保存
		}
		addMessage(redirectAttributes, "保存学员成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?menuId="+sysBaseStudent.getMenuId();
	}

	/**
	 * 删除学员
	 */
	@RequiresPermissions("att:sysBaseStudent:del")
	@RequestMapping(value = "delete")
	public String delete(SysBaseStudent sysBaseStudent, RedirectAttributes redirectAttributes) {
		sysBaseStudentService.delete(sysBaseStudent);
		addMessage(redirectAttributes, "删除学员成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?repage";
	}

	/**
	 * 批量删除学员
	 */
	@RequiresPermissions("att:sysBaseStudent:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysBaseStudentService.delete(sysBaseStudentService.get(id));
		}
		addMessage(redirectAttributes, "删除学员成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:sysBaseStudent:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysBaseStudent sysBaseStudent, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学员"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysBaseStudent> page = sysBaseStudentService.findPage(new Page<SysBaseStudent>(request, response, -1), sysBaseStudent);
    		new ExportExcel("学员", SysBaseStudent.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出学员记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?menuId="+sysBaseStudent.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:sysBaseStudent:import")
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
				ei.write(response, "学员列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysBaseStudent> list = ei.getDataList(SysBaseStudent.class);
				for (SysBaseStudent sysBaseStudent : list){
					try{
						sysBaseStudentService.save(sysBaseStudent);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条学员记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条学员记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入学员失败！失败信息："+e.getMessage());
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
	 * 下载导入学员数据模板
	 */
	@RequiresPermissions("att:sysBaseStudent:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "学员数据导入模板.xlsx";
    		List<SysBaseStudent> list = Lists.newArrayList();
    		new ExportExcel("学员数据", SysBaseStudent.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseStudent/?repage";
    }




}