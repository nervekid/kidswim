/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.web;

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
import com.kite.modules.datasources.entity.MultipleDatasourceDesignDetail;
import com.kite.modules.datasources.service.MultipleDatasourceDesignDetailService;

/**
 * 多数据配置设计详情Controller
 * @author cxh
 * @version 2017-08-21
 */
@Controller
@RequestMapping(value = "${adminPath}/datasources/multipleDatasourceDesignDetail")
public class MultipleDatasourceDesignDetailController extends BaseController {

	@Autowired
	private MultipleDatasourceDesignDetailService multipleDatasourceDesignDetailService;
	
	@ModelAttribute
	public MultipleDatasourceDesignDetail get(@RequestParam(required=false) String id) {
		MultipleDatasourceDesignDetail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = multipleDatasourceDesignDetailService.get(id);
		}
		if (entity == null){
			entity = new MultipleDatasourceDesignDetail();
		}
		return entity;
	}
	
	/**
	 * 多数据配置设计详情列表页面
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesignDetail:list")
	@RequestMapping(value = {"list", ""})
	public String list(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MultipleDatasourceDesignDetail> page = multipleDatasourceDesignDetailService.findPage(new Page<MultipleDatasourceDesignDetail>(request, response), multipleDatasourceDesignDetail); 
		model.addAttribute("page", page);
		return "modules/datasources/multipleDatasourceDesignDetailList";
	}

	/**
	 * 查看，增加，编辑多数据配置设计详情表单页面
	 */
	@RequiresPermissions(value={"datasources:multipleDatasourceDesignDetail:view","datasources:multipleDatasourceDesignDetail:add","datasources:multipleDatasourceDesignDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail, Model model) {
		model.addAttribute("multipleDatasourceDesignDetail", multipleDatasourceDesignDetail);
		return "modules/datasources/multipleDatasourceDesignDetailForm";
	}

	/**
	 * 保存多数据配置设计详情
	 */
	@RequiresPermissions(value={"datasources:multipleDatasourceDesignDetail:add","datasources:multipleDatasourceDesignDetail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, multipleDatasourceDesignDetail)){
			return form(multipleDatasourceDesignDetail, model);
		}
		if(!multipleDatasourceDesignDetail.getIsNewRecord()){//编辑表单保存
			MultipleDatasourceDesignDetail t = multipleDatasourceDesignDetailService.get(multipleDatasourceDesignDetail.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(multipleDatasourceDesignDetail, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			multipleDatasourceDesignDetailService.save(t);//保存
		}else{//新增表单保存
			multipleDatasourceDesignDetailService.save(multipleDatasourceDesignDetail);//保存
		}
		addMessage(redirectAttributes, "保存多数据配置设计详情成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesignDetail/?repage";
	}
	
	/**
	 * 删除多数据配置设计详情
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesignDetail:del")
	@RequestMapping(value = "delete")
	public String delete(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail, RedirectAttributes redirectAttributes) {
		multipleDatasourceDesignDetailService.delete(multipleDatasourceDesignDetail);
		addMessage(redirectAttributes, "删除多数据配置设计详情成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesignDetail/?repage";
	}
	
	/**
	 * 批量删除多数据配置设计详情
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesignDetail:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			multipleDatasourceDesignDetailService.delete(multipleDatasourceDesignDetailService.get(id));
		}
		addMessage(redirectAttributes, "删除多数据配置设计详情成功");
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesignDetail/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesignDetail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多数据配置设计详情"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MultipleDatasourceDesignDetail> page = multipleDatasourceDesignDetailService.findPage(new Page<MultipleDatasourceDesignDetail>(request, response, -1), multipleDatasourceDesignDetail);
    		new ExportExcel("多数据配置设计详情", MultipleDatasourceDesignDetail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出多数据配置设计详情记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesignDetail/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("datasources:multipleDatasourceDesignDetail:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<MultipleDatasourceDesignDetail> list = ei.getDataList(MultipleDatasourceDesignDetail.class);
			for (MultipleDatasourceDesignDetail multipleDatasourceDesignDetail : list){
				try{
					multipleDatasourceDesignDetailService.save(multipleDatasourceDesignDetail);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条多数据配置设计详情记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条多数据配置设计详情记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入多数据配置设计详情失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesignDetail/?repage";
    }
	
	/**
	 * 下载导入多数据配置设计详情数据模板
	 */
	@RequiresPermissions("datasources:multipleDatasourceDesignDetail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多数据配置设计详情数据导入模板.xlsx";
    		List<MultipleDatasourceDesignDetail> list = Lists.newArrayList(); 
    		new ExportExcel("多数据配置设计详情数据", MultipleDatasourceDesignDetail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/datasources/multipleDatasourceDesignDetail/?repage";
    }
	
	
	

}