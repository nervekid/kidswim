/**
 * KITE
 */
package com.kite.modules.sys.web;

import com.google.common.collect.Lists;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.SysModuleConfig;
import com.kite.modules.sys.service.SysModuleConfigService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 系统模块配置Controller
 * @author cxh
 * @version 2019-04-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysModuleConfig")
public class SysModuleConfigController extends BaseController implements BasicVerification {

	@Autowired
	private SysModuleConfigService sysModuleConfigService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysModuleConfig get(@RequestParam(required=false) String id) {
		SysModuleConfig entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysModuleConfigService.get(id);
		}
		if (entity == null){
			entity = new SysModuleConfig();
		}
		return entity;
	}

	/**
	 * 系统模块配置列表页面
	 */
	@RequiresPermissions("sys:sysModuleConfig:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysModuleConfig sysModuleConfig, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysModuleConfig> page = sysModuleConfigService.findPage(new Page<SysModuleConfig>(request, response), sysModuleConfig);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysModuleConfigList";
	}


	/**
	 * 查看，增加，编辑系统模块配置表单页面
	 */
	@RequiresPermissions(value={"sys:sysModuleConfig:view","sys:sysModuleConfig:add","sys:sysModuleConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysModuleConfig sysModuleConfig, Model model) {
		model.addAttribute("sysModuleConfig", sysModuleConfig);
		if(sysModuleConfig.getId()==null){
			// sysModuleConfig.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysModuleConfigForm";
	}

	/**
	 * 查看打印系统模块配置表单页面
	 */
	@RequiresPermissions(value={"sys:sysModuleConfig:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysModuleConfig sysModuleConfig, Model model) {
		model.addAttribute("sysModuleConfig", sysModuleConfig);
		return "modules/sys/sysModuleConfigView";
	}

	/**
	 * 保存系统模块配置
	 */
	@RequiresPermissions(value={"sys:sysModuleConfig:add","sys:sysModuleConfig:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysModuleConfig sysModuleConfig, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysModuleConfig)){
			return form(sysModuleConfig, model);
		}
		if(!sysModuleConfig.getIsNewRecord()){//编辑表单保存
			SysModuleConfig t = sysModuleConfigService.get(sysModuleConfig.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysModuleConfig, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysModuleConfigService.save(t);//保存
		}else{//新增表单保存
			sysModuleConfigService.save(sysModuleConfig);//保存
		}
		addMessage(redirectAttributes, "保存系统模块配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysModuleConfig/?menuId="+sysModuleConfig.getMenuId();
	}

	/**
	 * 删除系统模块配置
	 */
	@RequiresPermissions("sys:sysModuleConfig:del")
	@RequestMapping(value = "delete")
	public String delete(SysModuleConfig sysModuleConfig, RedirectAttributes redirectAttributes) {
		sysModuleConfigService.delete(sysModuleConfig);
		addMessage(redirectAttributes, "删除系统模块配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysModuleConfig/?repage";
	}

	/**
	 * 批量删除系统模块配置
	 */
	@RequiresPermissions("sys:sysModuleConfig:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysModuleConfigService.delete(sysModuleConfigService.get(id));
		}
		addMessage(redirectAttributes, "删除系统模块配置成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysModuleConfig/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysModuleConfig:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysModuleConfig sysModuleConfig, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统模块配置"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysModuleConfig> page = sysModuleConfigService.findPage(new Page<SysModuleConfig>(request, response, -1), sysModuleConfig);
    		new ExportExcel("系统模块配置", SysModuleConfig.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统模块配置记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysModuleConfig/?menuId="+sysModuleConfig.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysModuleConfig:import")
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
				ei.write(response, "系统模块配置列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysModuleConfig> list = ei.getDataList(SysModuleConfig.class);
				for (SysModuleConfig sysModuleConfig : list){
					try{
						sysModuleConfigService.save(sysModuleConfig);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条系统模块配置记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统模块配置记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统模块配置失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysModuleConfig/?menuId="+menuId;
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
	 * 下载导入系统模块配置数据模板
	 */
	@RequiresPermissions("sys:sysModuleConfig:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统模块配置数据导入模板.xlsx";
    		List<SysModuleConfig> list = Lists.newArrayList();
    		new ExportExcel("系统模块配置数据", SysModuleConfig.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysModuleConfig/?repage";
    }




}