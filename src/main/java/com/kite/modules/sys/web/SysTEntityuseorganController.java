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
import com.kite.modules.gen.entity.GenTable;
import com.kite.modules.gen.service.GenTableService;
import com.kite.modules.sys.entity.SysTEntityuseorgan;
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
 * 实体功能对应组织架构Controller
 * @author lyb
 * @version 2018-10-26
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTEntityuseorgan")
public class SysTEntityuseorganController extends BaseController implements BasicVerification {

	@Autowired
	private SysTEntityuseorganService sysTEntityuseorganService;
	@Autowired
	private GenTableService genTableService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysTEntityuseorgan get(@RequestParam(required=false) String id) {
		SysTEntityuseorgan entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysTEntityuseorganService.get(id);
		}
		if (entity == null){
			entity = new SysTEntityuseorgan();
		}
		return entity;
	}

	/**
	 * 实体功能对应组织架构列表页面
	 */
	@RequiresPermissions("sys:sysTEntityuseorgan:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysTEntityuseorgan sysTEntityuseorgan, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysTEntityuseorgan> page = sysTEntityuseorganService.findPage(new Page<SysTEntityuseorgan>(request, response), sysTEntityuseorgan);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysTEntityuseorganList";
	}


	/**
	 * 查看，增加，编辑实体功能对应组织架构表单页面
	 */
	@RequiresPermissions(value={"sys:sysTEntityuseorgan:view","sys:sysTEntityuseorgan:add","sys:sysTEntityuseorgan:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysTEntityuseorgan sysTEntityuseorgan, Model model) {
		model.addAttribute("sysTEntityuseorgan", sysTEntityuseorgan);
		List<GenTable> tableList = this.genTableService.findTableListFormDb(new GenTable());
        model.addAttribute("tableList", tableList);
		if(sysTEntityuseorgan.getId()==null){
			// sysTEntityuseorgan.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		if (sysTEntityuseorgan.getId() == null) {
			model.addAttribute("isAdd", true);
		}
		else {
			model.addAttribute("isAdd", false);
		}
		return "modules/sys/sysTEntityuseorganForm";
	}

	/**
	 * 查看打印实体功能对应组织架构表单页面
	 */
	@RequiresPermissions(value={"sys:sysTEntityuseorgan:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysTEntityuseorgan sysTEntityuseorgan, Model model) {
		model.addAttribute("sysTEntityuseorgan", sysTEntityuseorgan);
		return "modules/sys/sysTEntityuseorganView";
	}

	/**
	 * 保存实体功能对应组织架构
	 */
	@RequiresPermissions(value={"sys:sysTEntityuseorgan:add","sys:sysTEntityuseorgan:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysTEntityuseorgan sysTEntityuseorgan, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysTEntityuseorgan)){
			return form(sysTEntityuseorgan, model);
		}
		if(sysTEntityuseorgan.getDataTableName() == null || sysTEntityuseorgan.getDataTableName().equals("") ||
				sysTEntityuseorgan.getOrganTag() ==null || sysTEntityuseorgan.getOrganTag().equals("")) {
			addMessage(redirectAttributes, "保存失败,发生异常,请选择数据库表与组织架构");
			return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?menuId="+sysTEntityuseorgan.getMenuId();
		}
		String comments = this.genTableService.findCommentsByName(sysTEntityuseorgan.getDataTableName());
		sysTEntityuseorgan.setDataTableNameCN(comments);
		if(!sysTEntityuseorgan.getIsNewRecord()){//编辑表单保存
			SysTEntityuseorgan t = sysTEntityuseorganService.get(sysTEntityuseorgan.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysTEntityuseorgan, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysTEntityuseorganService.save(t);//保存
		}else{//新增表单保存
			if (this.sysTEntityuseorganService.isEntityOrganRepeat(sysTEntityuseorgan.getDataTableName())) {
				addMessage(redirectAttributes, "保存失败,发生异常,数据库表实体已经被其他组织架构关联");
				return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?menuId="+sysTEntityuseorgan.getMenuId();
			}
			sysTEntityuseorganService.save(sysTEntityuseorgan);//保存
		}
		addMessage(redirectAttributes, "保存实体功能对应组织架构成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?menuId="+sysTEntityuseorgan.getMenuId();
	}

	/**
	 * 删除实体功能对应组织架构
	 */
	@RequiresPermissions("sys:sysTEntityuseorgan:del")
	@RequestMapping(value = "delete")
	public String delete(SysTEntityuseorgan sysTEntityuseorgan, RedirectAttributes redirectAttributes) {
		sysTEntityuseorganService.delete(sysTEntityuseorgan);
		addMessage(redirectAttributes, "删除实体功能对应组织架构成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?repage";
	}

	/**
	 * 批量删除实体功能对应组织架构
	 */
	@RequiresPermissions("sys:sysTEntityuseorgan:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysTEntityuseorganService.delete(sysTEntityuseorganService.get(id));
		}
		addMessage(redirectAttributes, "删除实体功能对应组织架构成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysTEntityuseorgan:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysTEntityuseorgan sysTEntityuseorgan, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "实体功能对应组织架构"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysTEntityuseorgan> page = sysTEntityuseorganService.findPage(new Page<SysTEntityuseorgan>(request, response, -1), sysTEntityuseorgan);
    		new ExportExcel("实体功能对应组织架构", SysTEntityuseorgan.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出实体功能对应组织架构记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?menuId="+sysTEntityuseorgan.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysTEntityuseorgan:import")
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
				ei.write(response, "实体功能对应组织架构列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysTEntityuseorgan> list = ei.getDataList(SysTEntityuseorgan.class);
				for (SysTEntityuseorgan sysTEntityuseorgan : list){
					try{
						sysTEntityuseorganService.save(sysTEntityuseorgan);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条实体功能对应组织架构记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条实体功能对应组织架构记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入实体功能对应组织架构失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?menuId="+menuId;
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
	 * 下载导入实体功能对应组织架构数据模板
	 */
	@RequiresPermissions("sys:sysTEntityuseorgan:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "实体功能对应组织架构数据导入模板.xlsx";
    		List<SysTEntityuseorgan> list = Lists.newArrayList();
    		new ExportExcel("实体功能对应组织架构数据", SysTEntityuseorgan.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTEntityuseorgan/?repage";
    }




}