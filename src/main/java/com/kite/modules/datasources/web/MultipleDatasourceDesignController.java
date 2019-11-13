/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.kite.modules.datasources.entity.DataSourceConfig;
import com.kite.modules.datasources.entity.MultipleDatasourceDesignDetail;
import com.kite.modules.datasources.enums.MultipDatasourceStatus;
import com.kite.modules.datasources.service.DataSourceConfigService;
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
import com.kite.modules.datasources.entity.MultipleDatasourceDesign;
import com.kite.modules.datasources.service.MultipleDatasourceDesignService;

/**
 * 多数据配置设计Controller
 * @author cxh
 * @version 2017-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/datasources/multipleDatasourceDesign")
public class MultipleDatasourceDesignController extends BaseController {

	@Autowired
	private MultipleDatasourceDesignService multipleDatasourceDesignService;
	@Autowired
	private DataSourceConfigService dataSourceConfigService;
	
	@ModelAttribute
	public MultipleDatasourceDesign get(@RequestParam(required=false) String id) {
		MultipleDatasourceDesign entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = multipleDatasourceDesignService.get(id);
		}
		if (entity == null){
			entity = new MultipleDatasourceDesign();
		}
		return entity;
	}
	
	/**
	 * 多数据配置设计列表页面
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesign:list")
	@RequestMapping(value = {"list", ""})
	public String list(MultipleDatasourceDesign multipleDatasourceDesign, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MultipleDatasourceDesign> page = multipleDatasourceDesignService.findPage(new Page<MultipleDatasourceDesign>(request, response), multipleDatasourceDesign); 
		model.addAttribute("page", page);
		return "modules/datasources/multipleDatasourceDesignList";
	}

	/**
	 * 查看，增加，编辑多数据配置设计表单页面
	 */
	@RequiresPermissions(value={"datasources:multipleDatasourceDesign:view","datasources:multipleDatasourceDesign:add","datasources:multipleDatasourceDesign:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MultipleDatasourceDesign multipleDatasourceDesign, Model model) {
		DataSourceConfig config = new DataSourceConfig();
		config.setStatus(MultipDatasourceStatus.OPEN.getValue());
		List<DataSourceConfig> dataSourceConfigList = dataSourceConfigService.findList(config);
		model.addAttribute("dataSourceConfigList", dataSourceConfigList);
		model.addAttribute("multipleDatasourceDesign", multipleDatasourceDesign);
		return "modules/datasources/multipleDatasourceDesignForm";
	}

	/**
	 * 保存多数据配置设计
	 */
	@RequiresPermissions(value={"datasources:multipleDatasourceDesign:add","datasources:multipleDatasourceDesign:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MultipleDatasourceDesign multipleDatasourceDesign, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, multipleDatasourceDesign)){
			return form(multipleDatasourceDesign, model);
		}
		if(!multipleDatasourceDesign.getIsNewRecord()){//编辑表单保存
			MultipleDatasourceDesign t = multipleDatasourceDesignService.get(multipleDatasourceDesign.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(multipleDatasourceDesign, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			multipleDatasourceDesignService.save(t);//保存
		}else{//新增表单保存
			multipleDatasourceDesignService.save(multipleDatasourceDesign);//保存
		}
		addMessage(redirectAttributes, "保存多数据配置设计成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesign/?repage";
	}
	
	/**
	 * 删除多数据配置设计
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesign:del")
	@RequestMapping(value = "delete")
	public String delete(MultipleDatasourceDesign multipleDatasourceDesign, RedirectAttributes redirectAttributes) {
		multipleDatasourceDesignService.delete(multipleDatasourceDesign);
		addMessage(redirectAttributes, "删除多数据配置设计成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesign/?repage";
	}
	
	/**
	 * 批量删除多数据配置设计
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesign:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			multipleDatasourceDesignService.delete(multipleDatasourceDesignService.get(id));
		}
		addMessage(redirectAttributes, "删除多数据配置设计成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesign/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesign:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MultipleDatasourceDesign multipleDatasourceDesign, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多数据配置设计"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MultipleDatasourceDesign> page = multipleDatasourceDesignService.findPage(new Page<MultipleDatasourceDesign>(request, response, -1), multipleDatasourceDesign);
    		new ExportExcel("多数据配置设计", MultipleDatasourceDesign.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出多数据配置设计记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesign/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("datasources:multipleDatasourceDesign:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MultipleDatasourceDesign> list = ei.getDataList(MultipleDatasourceDesign.class);
			for (MultipleDatasourceDesign multipleDatasourceDesign : list){
				try{
					multipleDatasourceDesignService.save(multipleDatasourceDesign);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条多数据配置设计记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条多数据配置设计记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入多数据配置设计失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesign/?repage";
    }
	
	/**
	 * 下载导入多数据配置设计数据模板
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesign:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多数据配置设计数据导入模板.xlsx";
    		List<MultipleDatasourceDesign> list = Lists.newArrayList(); 
    		new ExportExcel("多数据配置设计数据", MultipleDatasourceDesign.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesign/?repage";
    }
	


    @RequestMapping("loadSqlColumns")
	@ResponseBody
    public String loadSqlColumns(Model model,String sqlText,String dataSourceConfigId){
		List<MultipleDatasourceDesignDetail> multipleDatasourceDesignDetailList = multipleDatasourceDesignService.getReportMetaDataColumns(dataSourceConfigId, sqlText);
		model.addAttribute("multipleDatasourceDesignDetailList",multipleDatasourceDesignDetailList);
		return "modules/datasources/multipleDatasourceDesignDetailList";
	}
	

}