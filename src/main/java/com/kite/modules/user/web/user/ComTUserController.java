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
import com.kite.modules.user.entity.user.ComTUser;
import com.kite.modules.user.service.user.ComTUserService;

/**
 * userController
 * @author czh
 * @version 2017-08-24
 */
@Controller
@RequestMapping(value = "${adminPath}/user/user/comTUser")
public class ComTUserController extends BaseController {

	@Autowired
	private ComTUserService comTUserService;
	public static String DATASOURCE="";
	
	@ModelAttribute
	public ComTUser get(@RequestParam(required=false) String id) {
		ComTUser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = comTUserService.get(DATASOURCE,id);
		}
		if (entity == null){
			entity = new ComTUser();
		}
		return entity;
	}
	
	/**
	 * 人员测试列表页面
	 */
	@RequiresPermissions("user:user:comTUser:list")
	@RequestMapping(value = {"list", ""})
	public String list(@RequestParam String datasource,ComTUser comTUser, HttpServletRequest request, HttpServletResponse response, Model model) {
		System.out.println(datasource+"========================================datasource");
		DATASOURCE=datasource;
		Page<ComTUser> page = comTUserService.findPage(DATASOURCE,new Page<ComTUser>(request, response), comTUser); 
		model.addAttribute("page", page);
		return "modules/user/user/comTUserList";
	}

	/**
	 * 查看，增加，编辑人员测试表单页面
	 */
	@RequiresPermissions(value={"user:user:comTUser:view","user:user:comTUser:add","user:user:comTUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(ComTUser comTUser, Model model) {
		model.addAttribute("comTUser", comTUser);
		return "modules/user/user/comTUserForm";
	}

	/**
	 * 保存人员测试
	 */
	@RequiresPermissions(value={"user:user:comTUser:add","user:user:comTUser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(ComTUser comTUser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, comTUser)){
			return form(comTUser, model);
		}
		if(!comTUser.getIsNewRecord()){//编辑表单保存
			ComTUser t = comTUserService.get(DATASOURCE,comTUser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(comTUser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			comTUserService.save(DATASOURCE,t);//保存
		}else{//新增表单保存
			comTUserService.save(DATASOURCE,comTUser);//保存
		}
		addMessage(redirectAttributes, "保存人员测试成功");
		return "redirect:"+Global.getAdminPath()+"/user/user/comTUser/?repage&datasource="+DATASOURCE;
	}
	
	/**
	 * 删除人员测试
	 */
	@RequiresPermissions("user:user:comTUser:del")
	@RequestMapping(value = "delete")
	public String delete(ComTUser comTUser, RedirectAttributes redirectAttributes) {
		comTUserService.delete(DATASOURCE,comTUser);
		addMessage(redirectAttributes, "删除人员测试成功");
		return "redirect:"+Global.getAdminPath()+"/user/user/comTUser/?repage&datasource="+DATASOURCE;
	}
	
	/**
	 * 批量删除人员测试
	 */
	@RequiresPermissions("user:user:comTUser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			comTUserService.delete(DATASOURCE,comTUserService.get(id));
		}
		addMessage(redirectAttributes, "删除人员测试成功");
		return "redirect:"+Global.getAdminPath()+"/user/user/comTUser/?repage&datasource="+DATASOURCE;
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("user:user:comTUser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(ComTUser comTUser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "人员测试"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<ComTUser> page = comTUserService.findPage(DATASOURCE,new Page<ComTUser>(request, response, -1), comTUser);
    		new ExportExcel("人员测试", ComTUser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出人员测试记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/user/comTUser/?repage&datasource="+DATASOURCE;
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("user:user:comTUser:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<ComTUser> list = ei.getDataList(ComTUser.class);
			for (ComTUser comTUser : list){
				try{
					comTUserService.save(DATASOURCE,comTUser);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条人员测试记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条人员测试记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入人员测试失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/user/comTUser/?repage&datasource="+DATASOURCE;
    }
	
	/**
	 * 下载导入人员测试数据模板
	 */
	@RequiresPermissions("user:user:comTUser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "人员测试数据导入模板.xlsx";
    		List<ComTUser> list = Lists.newArrayList(); 
    		new ExportExcel("人员测试数据", ComTUser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/user/user/comTUser/?repage&datasource="+DATASOURCE;
    }
	
	
	

}