/**
 * KITE
 */
package com.kite.modules.att.web;

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
import com.kite.modules.att.entity.SysCertificatesCoach;
import com.kite.modules.att.service.SysCertificatesCoachService;

/**
 * 教练员资格Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/sysCertificatesCoach")
public class SysCertificatesCoachController extends BaseController implements BasicVerification {

	@Autowired
	private SysCertificatesCoachService sysCertificatesCoachService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysCertificatesCoach get(@RequestParam(required=false) String id) {
		SysCertificatesCoach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysCertificatesCoachService.get(id);
		}
		if (entity == null){
			entity = new SysCertificatesCoach();
		}
		return entity;
	}

	/**
	 * 教练员资格列表页面
	 */
	@RequiresPermissions("att:sysCertificatesCoach:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysCertificatesCoach sysCertificatesCoach, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysCertificatesCoach> page = sysCertificatesCoachService.findPage(new Page<SysCertificatesCoach>(request, response), sysCertificatesCoach);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/sysCertificatesCoachList";
	}


	/**
	 * 查看，增加，编辑教练员资格表单页面
	 */
	@RequiresPermissions(value={"att:sysCertificatesCoach:view","att:sysCertificatesCoach:add","att:sysCertificatesCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysCertificatesCoach sysCertificatesCoach, Model model) {
		model.addAttribute("sysCertificatesCoach", sysCertificatesCoach);
		if(sysCertificatesCoach.getId()==null){
			// sysCertificatesCoach.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/sysCertificatesCoachForm";
	}

	/**
	 * 查看打印教练员资格表单页面
	 */
	@RequiresPermissions(value={"att:sysCertificatesCoach:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysCertificatesCoach sysCertificatesCoach, Model model) {
		model.addAttribute("sysCertificatesCoach", sysCertificatesCoach);
		return "modules/att/sysCertificatesCoachView";
	}

	/**
	 * 保存教练员资格
	 */
	@RequiresPermissions(value={"att:sysCertificatesCoach:add","att:sysCertificatesCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysCertificatesCoach sysCertificatesCoach, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysCertificatesCoach)){
			return form(sysCertificatesCoach, model);
		}
		if(!sysCertificatesCoach.getIsNewRecord()){//编辑表单保存
			SysCertificatesCoach t = sysCertificatesCoachService.get(sysCertificatesCoach.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysCertificatesCoach, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysCertificatesCoachService.save(t);//保存
		}else{//新增表单保存
			sysCertificatesCoachService.save(sysCertificatesCoach);//保存
		}
		addMessage(redirectAttributes, "保存教练员资格成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?menuId="+sysCertificatesCoach.getMenuId();
	}

	/**
	 * 删除教练员资格
	 */
	@RequiresPermissions("att:sysCertificatesCoach:del")
	@RequestMapping(value = "delete")
	public String delete(SysCertificatesCoach sysCertificatesCoach, RedirectAttributes redirectAttributes) {
		sysCertificatesCoachService.delete(sysCertificatesCoach);
		addMessage(redirectAttributes, "删除教练员资格成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?repage";
	}

	/**
	 * 批量删除教练员资格
	 */
	@RequiresPermissions("att:sysCertificatesCoach:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysCertificatesCoachService.delete(sysCertificatesCoachService.get(id));
		}
		addMessage(redirectAttributes, "删除教练员资格成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:sysCertificatesCoach:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysCertificatesCoach sysCertificatesCoach, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教练员资格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysCertificatesCoach> page = sysCertificatesCoachService.findPage(new Page<SysCertificatesCoach>(request, response, -1), sysCertificatesCoach);
    		new ExportExcel("教练员资格", SysCertificatesCoach.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出教练员资格记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?menuId="+sysCertificatesCoach.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:sysCertificatesCoach:import")
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
				ei.write(response, "教练员资格列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysCertificatesCoach> list = ei.getDataList(SysCertificatesCoach.class);
				for (SysCertificatesCoach sysCertificatesCoach : list){
					try{
						sysCertificatesCoachService.save(sysCertificatesCoach);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条教练员资格记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条教练员资格记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入教练员资格失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?menuId="+menuId;
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
	 * 下载导入教练员资格数据模板
	 */
	@RequiresPermissions("att:sysCertificatesCoach:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教练员资格数据导入模板.xlsx";
    		List<SysCertificatesCoach> list = Lists.newArrayList();
    		new ExportExcel("教练员资格数据", SysCertificatesCoach.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?repage";
    }




}