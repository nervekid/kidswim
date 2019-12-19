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
import com.kite.modules.att.entity.SerGroup;
import com.kite.modules.att.service.SerGroupService;

/**
 * 分组Controller
 * @author lyb
 * @version 2019-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serGroup")
public class SerGroupController extends BaseController implements BasicVerification {

	@Autowired
	private SerGroupService serGroupService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerGroup get(@RequestParam(required=false) String id) {
		SerGroup entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serGroupService.get(id);
		}
		if (entity == null){
			entity = new SerGroup();
		}
		return entity;
	}

	/**
	 * 分组列表页面
	 */
	@RequiresPermissions("att:serGroup:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerGroup serGroup, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerGroup> page = serGroupService.findPage(new Page<SerGroup>(request, response), serGroup);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serGroupList";
	}


	/**
	 * 查看，增加，编辑分组表单页面
	 */
	@RequiresPermissions(value={"att:serGroup:view","att:serGroup:add","att:serGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerGroup serGroup, Model model) {
		model.addAttribute("serGroup", serGroup);
		if(serGroup.getId()==null){
			// serGroup.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/serGroupForm";
	}

	/**
	 * 查看打印分组表单页面
	 */
	@RequiresPermissions(value={"att:serGroup:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerGroup serGroup, Model model) {
		model.addAttribute("serGroup", serGroup);
		return "modules/att/serGroupView";
	}

	/**
	 * 保存分组
	 */
	@RequiresPermissions(value={"att:serGroup:add","att:serGroup:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerGroup serGroup, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serGroup)){
			return form(serGroup, model);
		}
		if(!serGroup.getIsNewRecord()){//编辑表单保存
			SerGroup t = serGroupService.get(serGroup.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(serGroup, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			serGroupService.save(t);//保存
		}else{//新增表单保存
			serGroupService.save(serGroup);//保存
		}
		addMessage(redirectAttributes, "保存分组成功");
		return "redirect:"+Global.getAdminPath()+"/att/serGroup/?menuId="+serGroup.getMenuId();
	}

	/**
	 * 删除分组
	 */
	@RequiresPermissions("att:serGroup:del")
	@RequestMapping(value = "delete")
	public String delete(SerGroup serGroup, RedirectAttributes redirectAttributes) {
		serGroupService.delete(serGroup);
		addMessage(redirectAttributes, "删除分组成功");
		return "redirect:"+Global.getAdminPath()+"/att/serGroup/?repage";
	}

	/**
	 * 批量删除分组
	 */
	@RequiresPermissions("att:serGroup:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serGroupService.delete(serGroupService.get(id));
		}
		addMessage(redirectAttributes, "删除分组成功");
		return "redirect:"+Global.getAdminPath()+"/att/serGroup/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:serGroup:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerGroup serGroup, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerGroup> page = serGroupService.findPage(new Page<SerGroup>(request, response, -1), serGroup);
    		new ExportExcel("分组", SerGroup.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出分组记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serGroup/?menuId="+serGroup.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:serGroup:import")
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
				ei.write(response, "分组列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerGroup> list = ei.getDataList(SerGroup.class);
				for (SerGroup serGroup : list){
					try{
						serGroupService.save(serGroup);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条分组记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分组记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分组失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serGroup/?menuId="+menuId;
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
	 * 下载导入分组数据模板
	 */
	@RequiresPermissions("att:serGroup:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分组数据导入模板.xlsx";
    		List<SerGroup> list = Lists.newArrayList();
    		new ExportExcel("分组数据", SerGroup.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serGroup/?repage";
    }




}