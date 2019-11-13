/**
 * MouTai
 */
package com.kite.modules.sys.web;

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
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.entity.XuanwuCheck;
import com.kite.modules.sys.service.XuanwuCheckService;

/**
 * xuanwuController
 * @author cxh
 * @version 2017-12-27
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/xuanwuCheck")
public class XuanwuCheckController extends BaseController {

	@Autowired
	private XuanwuCheckService xuanwuCheckService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	
	@ModelAttribute
	public XuanwuCheck get(@RequestParam(required=false) String id) {
		XuanwuCheck entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xuanwuCheckService.get(id);
		}
		if (entity == null){
			entity = new XuanwuCheck();
		}
		return entity;
	}
	
	/**
	 * xuanwu列表页面
	 */
	@RequiresPermissions("sys:xuanwuCheck:list")
	@RequestMapping(value = {"list", ""})
	public String list(XuanwuCheck xuanwuCheck, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<XuanwuCheck> page = xuanwuCheckService.findPage(new Page<XuanwuCheck>(request, response), xuanwuCheck); 
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/xuanwuCheckList";
	}


	/**
	 * 查看，增加，编辑xuanwu表单页面
	 */
	@RequiresPermissions(value={"sys:xuanwuCheck:view","sys:xuanwuCheck:add","sys:xuanwuCheck:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(XuanwuCheck xuanwuCheck, Model model) {
		model.addAttribute("xuanwuCheck", xuanwuCheck);
		if(xuanwuCheck.getId()==null){
			// xuanwuCheck.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/xuanwuCheckForm";
	}

	/**
	 * 查看打印xuanwu表单页面
	 */
	@RequiresPermissions(value={"sys:xuanwuCheck:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(XuanwuCheck xuanwuCheck, Model model) {
		model.addAttribute("xuanwuCheck", xuanwuCheck);
		return "modules/sys/xuanwuCheckView";
	}

	/**
	 * 保存xuanwu
	 */
	@RequiresPermissions(value={"sys:xuanwuCheck:add","sys:xuanwuCheck:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(XuanwuCheck xuanwuCheck, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, xuanwuCheck)){
			return form(xuanwuCheck, model);
		}
		if(!xuanwuCheck.getIsNewRecord()){//编辑表单保存
			XuanwuCheck t = xuanwuCheckService.get(xuanwuCheck.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(xuanwuCheck, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			xuanwuCheckService.save(t);//保存
		}else{//新增表单保存
			xuanwuCheckService.save(xuanwuCheck);//保存
		}
		addMessage(redirectAttributes, "保存xuanwu成功");
		return "redirect:"+Global.getAdminPath()+"/sys/xuanwuCheck/?menuId="+xuanwuCheck.getMenuId();
	}
	
	/**
	 * 删除xuanwu
	 */
	@RequiresPermissions("sys:xuanwuCheck:del")
	@RequestMapping(value = "delete")
	public String delete(XuanwuCheck xuanwuCheck, RedirectAttributes redirectAttributes) {
		xuanwuCheckService.delete(xuanwuCheck);
		addMessage(redirectAttributes, "删除xuanwu成功");
		return "redirect:"+Global.getAdminPath()+"/sys/xuanwuCheck/?repage";
	}
	
	/**
	 * 批量删除xuanwu
	 */
	@RequiresPermissions("sys:xuanwuCheck:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			xuanwuCheckService.delete(xuanwuCheckService.get(id));
		}
		addMessage(redirectAttributes, "删除xuanwu成功");
		return "redirect:"+Global.getAdminPath()+"/sys/xuanwuCheck/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:xuanwuCheck:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(XuanwuCheck xuanwuCheck, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "xuanwu"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<XuanwuCheck> page = xuanwuCheckService.findPage(new Page<XuanwuCheck>(request, response, -1), xuanwuCheck);
    		new ExportExcel("xuanwu", XuanwuCheck.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出xuanwu记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/xuanwuCheck/?menuId="+xuanwuCheck.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:xuanwuCheck:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file,String menuId, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<XuanwuCheck> list = ei.getDataList(XuanwuCheck.class);
			for (XuanwuCheck xuanwuCheck : list){
				try{
					xuanwuCheckService.save(xuanwuCheck);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条xuanwu记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条xuanwu记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入xuanwu失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/xuanwuCheck/?menuId="+menuId;
    }
	
	/**
	 * 下载导入xuanwu数据模板
	 */
	@RequiresPermissions("sys:xuanwuCheck:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "xuanwu数据导入模板.xlsx";
    		List<XuanwuCheck> list = Lists.newArrayList(); 
    		new ExportExcel("xuanwu数据", XuanwuCheck.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/xuanwuCheck/?repage";
    }
	
	
	

}