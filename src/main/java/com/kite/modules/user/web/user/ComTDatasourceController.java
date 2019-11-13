/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.user.web.user;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


import com.google.common.collect.Lists;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.web.BaseController;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.modules.user.entity.user.ComTDatasource;
import com.kite.modules.user.service.user.ComTDatasourceService;

/**
 * 数据源Controller
 * @author czh
 * @version 2017-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/user/user/comTDatasource")
public class ComTDatasourceController extends BaseController {

	@Autowired
	private ComTDatasourceService comTDatasourceService;
	
	@ModelAttribute
	public ComTDatasource get(@RequestParam(required=false) String id) {
		ComTDatasource entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comTDatasourceService.get(id);
		}
		if (entity == null){
			entity = new ComTDatasource();
		}
		return entity;
	}
	
	/**
	 * 数据源列表页面
	 */
	@RequiresPermissions("user:user:comTDatasource:list")
	@RequestMapping(value = {"list", ""})
	public String list(ComTDatasource comTDatasource, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<ComTDatasource> page = comTDatasourceService.findPage(new Page<ComTDatasource>(request, response), comTDatasource); 
		model.addAttribute("page", page);
		return "modules/user/user/comTDatasourceList";
	}

	/**
	 * 查看，增加，编辑数据源表单页面
	 */
	@RequiresPermissions(value={"user:user:comTDatasource:view","user:user:comTDatasource:add","user:user:comTDatasource:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComTDatasource comTDatasource, Model model) {
		model.addAttribute("comTDatasource", comTDatasource);
		return "modules/user/user/comTDatasourceForm";
	}

	/**
	 * 保存数据源
	 */
	@RequiresPermissions(value={"user:user:comTDatasource:add","user:user:comTDatasource:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComTDatasource comTDatasource, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comTDatasource)){
			return form(comTDatasource, model);
		}
		if(!comTDatasource.getIsNewRecord()){//编辑表单保存
			ComTDatasource t = comTDatasourceService.get(comTDatasource.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(comTDatasource, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			comTDatasourceService.save(t);//保存
		}else{//新增表单保存
			comTDatasourceService.save(comTDatasource);//保存
		}
		addMessage(redirectAttributes, "保存数据源成功");
		return "redirect:"+Global.getAdminPath()+"/user/user/comTDatasource/?repage";
	}
	
	/**
	 * 删除数据源
	 */
	@RequiresPermissions("user:user:comTDatasource:del")
	@RequestMapping(value = "delete")
	public String delete(ComTDatasource comTDatasource, RedirectAttributes redirectAttributes) {
		comTDatasourceService.delete(comTDatasource);
		addMessage(redirectAttributes, "删除数据源成功");
		return "redirect:"+Global.getAdminPath()+"/user/user/comTDatasource/?repage";
	}
	
	/**
	 * 批量删除数据源
	 */
	@RequiresPermissions("user:user:comTDatasource:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comTDatasourceService.delete(comTDatasourceService.get(id));
		}
		addMessage(redirectAttributes, "删除数据源成功");
		return "redirect:"+Global.getAdminPath()+"/user/user/comTDatasource/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:user:comTDatasource:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ComTDatasource comTDatasource, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "数据源"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComTDatasource> page = comTDatasourceService.findPage(new Page<ComTDatasource>(request, response, -1), comTDatasource);
    		new ExportExcel("数据源", ComTDatasource.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出数据源记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/user/comTDatasource/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:user:comTDatasource:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComTDatasource> list = ei.getDataList(ComTDatasource.class);
			for (ComTDatasource comTDatasource : list){
				try{
					comTDatasourceService.save(comTDatasource);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条数据源记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条数据源记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入数据源失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/user/comTDatasource/?repage";
    }
	
	/**
	 * 下载导入数据源数据模板
	 */
	@RequiresPermissions("user:user:comTDatasource:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "数据源数据导入模板.xlsx";
    		List<ComTDatasource> list = Lists.newArrayList(); 
    		new ExportExcel("数据源数据", ComTDatasource.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/user/comTDatasource/?repage";
    }
	
	
	

}