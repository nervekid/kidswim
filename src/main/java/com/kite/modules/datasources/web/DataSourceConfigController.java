/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.web;

import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.kite.modules.datasources.entity.JsonResult;
import com.kite.modules.datasources.enums.MultipDatasourceStatus;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
import com.kite.modules.datasources.entity.DataSourceConfig;
import com.kite.modules.datasources.service.DataSourceConfigService;

/**
 * 多数据配置Controller
 * @author cxh
 * @version 2017-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/datasources/dataSourceConfig")
public class DataSourceConfigController extends BaseController {

	@Autowired
	private DataSourceConfigService dataSourceConfigService;
	
	@ModelAttribute
	public DataSourceConfig get(@RequestParam(required=false) String id) {
		DataSourceConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = dataSourceConfigService.get(id);
		}
		if (entity == null){
			entity = new DataSourceConfig();
		}
		return entity;
	}
	
	/**
	 * 多数据配置列表页面
	 */
	@RequiresPermissions("datasources:dataSourceConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(DataSourceConfig dataSourceConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<DataSourceConfig> page = dataSourceConfigService.findPage(new Page<DataSourceConfig>(request, response), dataSourceConfig); 
		model.addAttribute("page", page);
		return "modules/datasources/dataSourceConfigList";
	}

	/**
	 * 查看，增加，编辑多数据配置表单页面
	 */
	@RequiresPermissions(value={"datasources:dataSourceConfig:view","datasources:dataSourceConfig:add","datasources:dataSourceConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(DataSourceConfig dataSourceConfig, Model model) {
		if(dataSourceConfig!=null && StringUtils.isEmpty(dataSourceConfig.getStatus())){
			dataSourceConfig.setStatus(MultipDatasourceStatus.STOP.getValue());
		}
		model.addAttribute("dataSourceConfig", dataSourceConfig);
		return "modules/datasources/dataSourceConfigForm";
	}

	/**
	 * 保存多数据配置
	 */
	@RequiresPermissions(value={"datasources:dataSourceConfig:add","datasources:dataSourceConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(DataSourceConfig dataSourceConfig, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, dataSourceConfig)){
			return form(dataSourceConfig, model);
		}
		if(!dataSourceConfig.getIsNewRecord()){//编辑表单保存
			DataSourceConfig t = dataSourceConfigService.get(dataSourceConfig.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(dataSourceConfig, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			dataSourceConfigService.save(t);//保存
		}else{//新增表单保存

			dataSourceConfig.setUid(UUID.randomUUID().toString());
			dataSourceConfigService.save(dataSourceConfig);//保存
		}
		addMessage(redirectAttributes, "保存多数据配置成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/dataSourceConfig/?repage";
	}
	
	/**
	 * 删除多数据配置
	 */
	@RequiresPermissions("datasources:dataSourceConfig:del")
	@RequestMapping(value = "delete")
	public String delete(DataSourceConfig dataSourceConfig, RedirectAttributes redirectAttributes) {
		dataSourceConfigService.delete(dataSourceConfig);
		addMessage(redirectAttributes, "删除多数据配置成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/dataSourceConfig/?repage";
	}
	
	/**
	 * 批量删除多数据配置
	 */
	@RequiresPermissions("datasources:dataSourceConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			dataSourceConfigService.delete(dataSourceConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除多数据配置成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/dataSourceConfig/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("datasources:dataSourceConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(DataSourceConfig dataSourceConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多数据配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<DataSourceConfig> page = dataSourceConfigService.findPage(new Page<DataSourceConfig>(request, response, -1), dataSourceConfig);
    		new ExportExcel("多数据配置", DataSourceConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出多数据配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/dataSourceConfig/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("datasources:dataSourceConfig:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<DataSourceConfig> list = ei.getDataList(DataSourceConfig.class);
			for (DataSourceConfig dataSourceConfig : list){
				try{
					dataSourceConfigService.save(dataSourceConfig);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条多数据配置记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条多数据配置记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入多数据配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/dataSourceConfig/?repage";
    }
	
	/**
	 * 下载导入多数据配置数据模板
	 */
	@RequiresPermissions("datasources:dataSourceConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多数据配置数据导入模板.xlsx";
    		List<DataSourceConfig> list = Lists.newArrayList(); 
    		new ExportExcel("多数据配置数据", DataSourceConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/dataSourceConfig/?repage";
    }

	@RequestMapping(value = "/testConnection")
	@ResponseBody
	public JsonResult testConnection(String id) {
		JsonResult result = new JsonResult(false, "");
		try {
			DataSourceConfig datasource = dataSourceConfigService.get(id);
			if(datasource!=null){
				result.setSuccess(this.dataSourceConfigService.testConnection(datasource.getJdbcUrl(), datasource.getUser(), datasource.getPassword()));
			}else {
				result.setSuccess(false);
			}
		} catch (Exception ex) {
			this.setExceptionResult(result, ex);
		}
		return result;
	}

	protected void setExceptionResult(JsonResult result, Exception ex) {
		result.setSuccess(false);
		result.setMsg(String.format("系统异常, 原因:%s", ex.getMessage()));
	}
	
	

}