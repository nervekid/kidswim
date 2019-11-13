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
import com.kite.modules.sys.entity.SysTDataaccessuserentity;
import com.kite.modules.sys.service.SysTDataaccessuserentityService;
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
 * 多组织架构用户对应数据权限组分录Controller
 * @author lyb
 * @version 2018-11-12
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTDataaccessuserentity")
public class SysTDataaccessuserentityController extends BaseController implements BasicVerification {

	@Autowired
	private SysTDataaccessuserentityService sysTDataaccessuserentityService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysTDataaccessuserentity get(@RequestParam(required=false) String id) {
		SysTDataaccessuserentity entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysTDataaccessuserentityService.get(id);
		}
		if (entity == null){
			entity = new SysTDataaccessuserentity();
		}
		return entity;
	}

	/**
	 * 多组织架构用户对应数据权限组分录列表页面
	 */
	@RequiresPermissions("sys:sysTDataaccessuserentity:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysTDataaccessuserentity sysTDataaccessuserentity, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysTDataaccessuserentity> page = sysTDataaccessuserentityService.findPage(new Page<SysTDataaccessuserentity>(request, response), sysTDataaccessuserentity);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysTDataaccessuserentityList";
	}


	/**
	 * 查看，增加，编辑多组织架构用户对应数据权限组分录表单页面
	 */
	@RequiresPermissions(value={"sys:sysTDataaccessuserentity:view","sys:sysTDataaccessuserentity:add","sys:sysTDataaccessuserentity:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysTDataaccessuserentity sysTDataaccessuserentity, Model model) {
		model.addAttribute("sysTDataaccessuserentity", sysTDataaccessuserentity);
		if(sysTDataaccessuserentity.getId()==null){
			// sysTDataaccessuserentity.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysTDataaccessuserentityForm";
	}

	/**
	 * 查看打印多组织架构用户对应数据权限组分录表单页面
	 */
	@RequiresPermissions(value={"sys:sysTDataaccessuserentity:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysTDataaccessuserentity sysTDataaccessuserentity, Model model) {
		model.addAttribute("sysTDataaccessuserentity", sysTDataaccessuserentity);
		return "modules/sys/sysTDataaccessuserentityView";
	}

	/**
	 * 保存多组织架构用户对应数据权限组分录
	 */
	@RequiresPermissions(value={"sys:sysTDataaccessuserentity:add","sys:sysTDataaccessuserentity:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysTDataaccessuserentity sysTDataaccessuserentity, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysTDataaccessuserentity)){
			return form(sysTDataaccessuserentity, model);
		}
		if(!sysTDataaccessuserentity.getIsNewRecord()){//编辑表单保存
			SysTDataaccessuserentity t = sysTDataaccessuserentityService.get(sysTDataaccessuserentity.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysTDataaccessuserentity, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysTDataaccessuserentityService.save(t);//保存
		}else{//新增表单保存
			sysTDataaccessuserentityService.save(sysTDataaccessuserentity);//保存
		}
		addMessage(redirectAttributes, "保存多组织架构用户对应数据权限组分录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuserentity/?menuId="+sysTDataaccessuserentity.getMenuId();
	}

	/**
	 * 删除多组织架构用户对应数据权限组分录
	 */
	@RequiresPermissions("sys:sysTDataaccessuserentity:del")
	@RequestMapping(value = "delete")
	public String delete(SysTDataaccessuserentity sysTDataaccessuserentity, RedirectAttributes redirectAttributes) {
		sysTDataaccessuserentityService.delete(sysTDataaccessuserentity);
		addMessage(redirectAttributes, "删除多组织架构用户对应数据权限组分录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuserentity/?repage";
	}

	/**
	 * 批量删除多组织架构用户对应数据权限组分录
	 */
	@RequiresPermissions("sys:sysTDataaccessuserentity:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysTDataaccessuserentityService.delete(sysTDataaccessuserentityService.get(id));
		}
		addMessage(redirectAttributes, "删除多组织架构用户对应数据权限组分录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuserentity/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysTDataaccessuserentity:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysTDataaccessuserentity sysTDataaccessuserentity, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多组织架构用户对应数据权限组分录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysTDataaccessuserentity> page = sysTDataaccessuserentityService.findPage(new Page<SysTDataaccessuserentity>(request, response, -1), sysTDataaccessuserentity);
    		new ExportExcel("多组织架构用户对应数据权限组分录", SysTDataaccessuserentity.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出多组织架构用户对应数据权限组分录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuserentity/?menuId="+sysTDataaccessuserentity.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysTDataaccessuserentity:import")
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
				ei.write(response, "多组织架构用户对应数据权限组分录列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysTDataaccessuserentity> list = ei.getDataList(SysTDataaccessuserentity.class);
				for (SysTDataaccessuserentity sysTDataaccessuserentity : list){
					try{
						sysTDataaccessuserentityService.save(sysTDataaccessuserentity);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条多组织架构用户对应数据权限组分录记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条多组织架构用户对应数据权限组分录记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入多组织架构用户对应数据权限组分录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuserentity/?menuId="+menuId;
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
	 * 下载导入多组织架构用户对应数据权限组分录数据模板
	 */
	@RequiresPermissions("sys:sysTDataaccessuserentity:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多组织架构用户对应数据权限组分录数据导入模板.xlsx";
    		List<SysTDataaccessuserentity> list = Lists.newArrayList();
    		new ExportExcel("多组织架构用户对应数据权限组分录数据", SysTDataaccessuserentity.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuserentity/?repage";
    }




}