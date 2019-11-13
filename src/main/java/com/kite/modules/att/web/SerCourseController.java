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
import com.kite.modules.att.entity.SerCourse;
import com.kite.modules.att.service.SerCourseService;

/**
 * 课程Controller
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

	/*** 是否导入错误提示*/
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
	 * 课程列表页面
	 */
	@RequiresPermissions("att:serCourse:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerCourse serCourse, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerCourse> page = serCourseService.findPage(new Page<SerCourse>(request, response), serCourse);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serCourseList";
	}


	/**
	 * 查看，增加，编辑课程表单页面
	 */
	@RequiresPermissions(value={"att:serCourse:view","att:serCourse:add","att:serCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerCourse serCourse, Model model) {
		model.addAttribute("serCourse", serCourse);
		if(serCourse.getId()==null){
			// serCourse.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/serCourseForm";
	}

	/**
	 * 查看打印课程表单页面
	 */
	@RequiresPermissions(value={"att:serCourse:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerCourse serCourse, Model model) {
		model.addAttribute("serCourse", serCourse);
		return "modules/att/serCourseView";
	}

	/**
	 * 保存课程
	 */
	@RequiresPermissions(value={"att:serCourse:add","att:serCourse:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerCourse serCourse, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serCourse)){
			return form(serCourse, model);
		}
		if(!serCourse.getIsNewRecord()){//编辑表单保存
			SerCourse t = serCourseService.get(serCourse.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(serCourse, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			serCourseService.save(t);//保存
		}else{//新增表单保存
			serCourseService.save(serCourse);//保存
		}
		addMessage(redirectAttributes, "保存课程成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?menuId="+serCourse.getMenuId();
	}

	/**
	 * 删除课程
	 */
	@RequiresPermissions("att:serCourse:del")
	@RequestMapping(value = "delete")
	public String delete(SerCourse serCourse, RedirectAttributes redirectAttributes) {
		serCourseService.delete(serCourse);
		addMessage(redirectAttributes, "删除课程成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
	}

	/**
	 * 批量删除课程
	 */
	@RequiresPermissions("att:serCourse:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serCourseService.delete(serCourseService.get(id));
		}
		addMessage(redirectAttributes, "删除课程成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:serCourse:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerCourse serCourse, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerCourse> page = serCourseService.findPage(new Page<SerCourse>(request, response, -1), serCourse);
    		new ExportExcel("课程", SerCourse.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出课程记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?menuId="+serCourse.getMenuId();
    }

	/**
	 * 导入Excel数据

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
				ei.write(response, "课程列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
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
					failureMsg.insert(0, "，失败 "+failureNum+" 条课程记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条课程记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入课程失败！失败信息："+e.getMessage());
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
	 * 下载导入课程数据模板
	 */
	@RequiresPermissions("att:serCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "课程数据导入模板.xlsx";
    		List<SerCourse> list = Lists.newArrayList();
    		new ExportExcel("课程数据", SerCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
    }




}