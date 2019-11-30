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
import com.kite.modules.att.entity.SysBaseCoach;
import com.kite.modules.att.service.SysBaseCoachService;

/**
 * 教练员Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/sysBaseCoach")
public class SysBaseCoachController extends BaseController implements BasicVerification {

	@Autowired
	private SysBaseCoachService sysBaseCoachService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysBaseCoach get(@RequestParam(required=false) String id) {
		SysBaseCoach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBaseCoachService.get(id);
		}
		if (entity == null){
			entity = new SysBaseCoach();
		}
		return entity;
	}

	/**
	 * 教练员列表页面
	 */
	@RequiresPermissions("att:sysBaseCoach:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysBaseCoach sysBaseCoach, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (sysBaseCoach.getEntryYear() != null && !sysBaseCoach.getEntryYear().equals("")) {
			String yearMonth = sysBaseCoach.getEntryYear();
			yearMonth = yearMonth.replace("-", "");
			sysBaseCoach.setEntryYear(yearMonth);
		}
		Page<SysBaseCoach> page = sysBaseCoachService.findPage(new Page<SysBaseCoach>(request, response), sysBaseCoach);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/sysBaseCoachList";
	}


	/**
	 * 查看，增加，编辑教练员表单页面
	 */
	@RequiresPermissions(value={"att:sysBaseCoach:view","att:sysBaseCoach:add","att:sysBaseCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysBaseCoach sysBaseCoach, Model model) {
		model.addAttribute("sysBaseCoach", sysBaseCoach);
		if(sysBaseCoach.getId()==null){
			// sysBaseCoach.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/sysBaseCoachForm";
	}

	/**
	 * 查看打印教练员表单页面
	 */
	@RequiresPermissions(value={"att:sysBaseCoach:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysBaseCoach sysBaseCoach, Model model) {
		model.addAttribute("sysBaseCoach", sysBaseCoach);
		return "modules/att/sysBaseCoachView";
	}

	/**
	 * 保存教练员
	 */
	@RequiresPermissions(value={"att:sysBaseCoach:add","att:sysBaseCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysBaseCoach sysBaseCoach, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysBaseCoach)){
			return form(sysBaseCoach, model);
		}
		if(!sysBaseCoach.getIsNewRecord()){//编辑表单保存
			SysBaseCoach t = sysBaseCoachService.get(sysBaseCoach.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysBaseCoach, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysBaseCoachService.save(t);//保存
		}else{//新增表单保存
			sysBaseCoachService.save(sysBaseCoach);//保存
		}
		addMessage(redirectAttributes, "保存教练员成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?menuId="+sysBaseCoach.getMenuId();
	}

	/**
	 * 删除教练员
	 */
	@RequiresPermissions("att:sysBaseCoach:del")
	@RequestMapping(value = "delete")
	public String delete(SysBaseCoach sysBaseCoach, RedirectAttributes redirectAttributes) {
		sysBaseCoachService.delete(sysBaseCoach);
		addMessage(redirectAttributes, "删除教练员成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?repage";
	}

	/**
	 * 批量删除教练员
	 */
	@RequiresPermissions("att:sysBaseCoach:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysBaseCoachService.delete(sysBaseCoachService.get(id));
		}
		addMessage(redirectAttributes, "删除教练员成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:sysBaseCoach:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysBaseCoach sysBaseCoach, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教练员"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysBaseCoach> page = sysBaseCoachService.findPage(new Page<SysBaseCoach>(request, response, -1), sysBaseCoach);
    		new ExportExcel("教练员", SysBaseCoach.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出教练员记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?menuId="+sysBaseCoach.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:sysBaseCoach:import")
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
				ei.write(response, "教练员列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysBaseCoach> list = ei.getDataList(SysBaseCoach.class);
				for (SysBaseCoach sysBaseCoach : list){
					try{
						sysBaseCoachService.save(sysBaseCoach);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条教练员记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条教练员记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入教练员失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?menuId="+menuId;
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
	 * 下载导入教练员数据模板
	 */
	@RequiresPermissions("att:sysBaseCoach:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教练员数据导入模板.xlsx";
    		List<SysBaseCoach> list = Lists.newArrayList();
    		new ExportExcel("教练员数据", SysBaseCoach.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?repage";
    }




}