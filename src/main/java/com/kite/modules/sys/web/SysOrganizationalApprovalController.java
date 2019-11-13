/**
 * KITE
 */
package com.kite.modules.sys.web;

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
import com.kite.modules.sys.entity.SysOrganizationalApproval;
import com.kite.modules.sys.service.SysOrganizationalApprovalService;

/**
 * 组织架构职级表Controller
 * @author lyb
 * @version 2019-08-19
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOrganizationalApproval")
public class SysOrganizationalApprovalController extends BaseController implements BasicVerification {

	@Autowired
	private SysOrganizationalApprovalService sysOrganizationalApprovalService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysOrganizationalApproval get(@RequestParam(required=false) String id) {
		SysOrganizationalApproval entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOrganizationalApprovalService.get(id);
		}
		if (entity == null){
			entity = new SysOrganizationalApproval();
		}
		return entity;
	}

	/**
	 * 组织架构职级表列表页面
	 */
	@RequiresPermissions("sys:sysOrganizationalApproval:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysOrganizationalApproval sysOrganizationalApproval, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysOrganizationalApproval> page = sysOrganizationalApprovalService.findPage(new Page<SysOrganizationalApproval>(request, response), sysOrganizationalApproval);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysOrganizationalApprovalList";
	}


	/**
	 * 查看，增加，编辑组织架构职级表表单页面
	 */
	@RequiresPermissions(value={"sys:sysOrganizationalApproval:view","sys:sysOrganizationalApproval:add","sys:sysOrganizationalApproval:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysOrganizationalApproval sysOrganizationalApproval, Model model) {
		model.addAttribute("sysOrganizationalApproval", sysOrganizationalApproval);
		if(sysOrganizationalApproval.getId()==null){
			// sysOrganizationalApproval.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysOrganizationalApprovalForm";
	}

	/**
	 * 查看打印组织架构职级表表单页面
	 */
	@RequiresPermissions(value={"sys:sysOrganizationalApproval:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysOrganizationalApproval sysOrganizationalApproval, Model model) {
		model.addAttribute("sysOrganizationalApproval", sysOrganizationalApproval);
		return "modules/sys/sysOrganizationalApprovalView";
	}

	/**
	 * 保存组织架构职级表
	 */
	@RequiresPermissions(value={"sys:sysOrganizationalApproval:add","sys:sysOrganizationalApproval:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysOrganizationalApproval sysOrganizationalApproval, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysOrganizationalApproval)){
			return form(sysOrganizationalApproval, model);
		}
		if(!sysOrganizationalApproval.getIsNewRecord()){//编辑表单保存
			SysOrganizationalApproval t = sysOrganizationalApprovalService.get(sysOrganizationalApproval.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysOrganizationalApproval, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysOrganizationalApprovalService.save(t);//保存
		}else{//新增表单保存
			sysOrganizationalApprovalService.save(sysOrganizationalApproval);//保存
		}
		addMessage(redirectAttributes, "保存组织架构职级表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizationalApproval/?menuId="+sysOrganizationalApproval.getMenuId();
	}

	/**
	 * 删除组织架构职级表
	 */
	@RequiresPermissions("sys:sysOrganizationalApproval:del")
	@RequestMapping(value = "delete")
	public String delete(SysOrganizationalApproval sysOrganizationalApproval, RedirectAttributes redirectAttributes) {
		sysOrganizationalApprovalService.delete(sysOrganizationalApproval);
		addMessage(redirectAttributes, "删除组织架构职级表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizationalApproval/?repage";
	}

	/**
	 * 批量删除组织架构职级表
	 */
	@RequiresPermissions("sys:sysOrganizationalApproval:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysOrganizationalApprovalService.delete(sysOrganizationalApprovalService.get(id));
		}
		addMessage(redirectAttributes, "删除组织架构职级表成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizationalApproval/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysOrganizationalApproval:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysOrganizationalApproval sysOrganizationalApproval, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织架构职级表"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysOrganizationalApproval> page = sysOrganizationalApprovalService.findPage(new Page<SysOrganizationalApproval>(request, response, -1), sysOrganizationalApproval);
    		new ExportExcel("组织架构职级表", SysOrganizationalApproval.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出组织架构职级表记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizationalApproval/?menuId="+sysOrganizationalApproval.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysOrganizationalApproval:import")
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
				ei.write(response, "组织架构职级表列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysOrganizationalApproval> list = ei.getDataList(SysOrganizationalApproval.class);
				for (SysOrganizationalApproval sysOrganizationalApproval : list){
					try{
						sysOrganizationalApprovalService.save(sysOrganizationalApproval);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条组织架构职级表记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条组织架构职级表记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入组织架构职级表失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizationalApproval/?menuId="+menuId;
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
	 * 下载导入组织架构职级表数据模板
	 */
	@RequiresPermissions("sys:sysOrganizationalApproval:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "组织架构职级表数据导入模板.xlsx";
    		List<SysOrganizationalApproval> list = Lists.newArrayList();
    		new ExportExcel("组织架构职级表数据", SysOrganizationalApproval.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizationalApproval/?repage";
    }




}