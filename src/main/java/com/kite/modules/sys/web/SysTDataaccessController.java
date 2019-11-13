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
import com.kite.modules.sys.entity.SysTDataaccess;
import com.kite.modules.sys.entity.SysTEntityuseorgan;
import com.kite.modules.sys.service.SysTDataaccessService;
import com.kite.modules.sys.service.SysTEntityuseorganService;
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
 * 多组织架构数据权限Controller
 * @author lyb
 * @version 2018-10-30
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTDataaccess")
public class SysTDataaccessController extends BaseController implements BasicVerification {

	@Autowired
	private SysTDataaccessService sysTDataaccessService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SysTEntityuseorganService sysTEntityuseorganService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysTDataaccess get(@RequestParam(required=false) String id) {
		SysTDataaccess entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysTDataaccessService.get(id);
		}
		if (entity == null){
			entity = new SysTDataaccess();
		}
		return entity;
	}

	/**
	 * 多组织架构数据权限列表页面
	 */
	@RequiresPermissions("sys:sysTDataaccess:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysTDataaccess sysTDataaccess, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysTDataaccess> page = sysTDataaccessService.findPage(new Page<SysTDataaccess>(request, response), sysTDataaccess);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysTDataaccessList";
	}


	/**
	 * 查看，增加，编辑多组织架构数据权限表单页面
	 */
	@RequiresPermissions(value={"sys:sysTDataaccess:view","sys:sysTDataaccess:add","sys:sysTDataaccess:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysTDataaccess sysTDataaccess, Model model) {
		List<SysTEntityuseorgan> orgEntityList = this.sysTEntityuseorganService.findList(new SysTEntityuseorgan());
        model.addAttribute("orgEntityList", orgEntityList);
		model.addAttribute("sysTDataaccess", sysTDataaccess);
		if(sysTDataaccess.getId()==null){
			// sysTDataaccess.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysTDataaccessForm";
	}

	/**
	 * 查看打印多组织架构数据权限表单页面
	 */
	@RequiresPermissions(value={"sys:sysTDataaccess:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysTDataaccess sysTDataaccess, Model model) {
		model.addAttribute("sysTDataaccess", sysTDataaccess);
		return "modules/sys/sysTDataaccessView";
	}

	/**
	 * 保存多组织架构数据权限
	 */
	@RequiresPermissions(value={"sys:sysTDataaccess:add","sys:sysTDataaccess:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysTDataaccess sysTDataaccess, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysTDataaccess)){
			return form(sysTDataaccess, model);
		}
		if(!sysTDataaccess.getIsNewRecord()){//编辑表单保存
			SysTDataaccess t = sysTDataaccessService.get(sysTDataaccess.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysTDataaccess, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysTDataaccessService.save(t);//保存
		}else{//新增表单保存
			sysTDataaccessService.save(sysTDataaccess);//保存
		}
		addMessage(redirectAttributes, "保存多组织架构数据权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccess/?menuId="+sysTDataaccess.getMenuId();
	}

	/**
	 * 删除多组织架构数据权限
	 */
	@RequiresPermissions("sys:sysTDataaccess:del")
	@RequestMapping(value = "delete")
	public String delete(SysTDataaccess sysTDataaccess, RedirectAttributes redirectAttributes) {
		sysTDataaccessService.delete(sysTDataaccess);
		addMessage(redirectAttributes, "删除多组织架构数据权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccess/?repage";
	}

	/**
	 * 批量删除多组织架构数据权限
	 */
	@RequiresPermissions("sys:sysTDataaccess:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysTDataaccessService.delete(sysTDataaccessService.get(id));
		}
		addMessage(redirectAttributes, "删除多组织架构数据权限成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccess/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysTDataaccess:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysTDataaccess sysTDataaccess, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多组织架构数据权限"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysTDataaccess> page = sysTDataaccessService.findPage(new Page<SysTDataaccess>(request, response, -1), sysTDataaccess);
    		new ExportExcel("多组织架构数据权限", SysTDataaccess.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出多组织架构数据权限记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccess/?menuId="+sysTDataaccess.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysTDataaccess:import")
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
				ei.write(response, "多组织架构数据权限列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysTDataaccess> list = ei.getDataList(SysTDataaccess.class);
				for (SysTDataaccess sysTDataaccess : list){
					try{
						sysTDataaccessService.save(sysTDataaccess);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条多组织架构数据权限记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条多组织架构数据权限记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入多组织架构数据权限失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccess/?menuId="+menuId;
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
	 * 下载导入多组织架构数据权限数据模板
	 */
	@RequiresPermissions("sys:sysTDataaccess:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多组织架构数据权限数据导入模板.xlsx";
    		List<SysTDataaccess> list = Lists.newArrayList();
    		new ExportExcel("多组织架构数据权限数据", SysTDataaccess.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccess/?repage";
    }




}