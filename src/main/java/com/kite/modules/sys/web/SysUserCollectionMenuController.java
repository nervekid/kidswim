/**
 * MouTai
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
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.SysUserCollectionMenu;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.utils.UserUtils;
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
import java.util.Date;
import java.util.List;

/**
 * 用户收藏菜单Controller
 * @author cxh
 * @version 2017-12-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysUserCollectionMenu")
public class SysUserCollectionMenuController extends BaseController {

	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	
	@ModelAttribute
	public SysUserCollectionMenu get(@RequestParam(required=false) String id) {
		SysUserCollectionMenu entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysUserCollectionMenuService.get(id);
		}
		if (entity == null){
			entity = new SysUserCollectionMenu();
		}
		return entity;
	}
	
	/**
	 * 用户收藏菜单列表页面
	 */
	@RequiresPermissions("sys:sysUserCollectionMenu:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysUserCollectionMenu sysUserCollectionMenu, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysUserCollectionMenu> page = sysUserCollectionMenuService.findPage(new Page<SysUserCollectionMenu>(request, response), sysUserCollectionMenu); 
		model.addAttribute("page", page);
		return "modules/sys/sysUserCollectionMenuList";
	}

	/**
	 * 查看，增加，编辑用户收藏菜单表单页面
	 */
	@RequiresPermissions(value={"sys:sysUserCollectionMenu:view","sys:sysUserCollectionMenu:add","sys:sysUserCollectionMenu:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysUserCollectionMenu sysUserCollectionMenu, Model model) {
		model.addAttribute("sysUserCollectionMenu", sysUserCollectionMenu);
		if(sysUserCollectionMenu.getId()==null){
			// sysUserCollectionMenu.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysUserCollectionMenuForm";
	}

	/**
	 * 查看打印用户收藏菜单表单页面
	 */
	@RequiresPermissions(value={"sys:sysUserCollectionMenu:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysUserCollectionMenu sysUserCollectionMenu, Model model) {
		model.addAttribute("sysUserCollectionMenu", sysUserCollectionMenu);
		return "modules/sys/sysUserCollectionMenuView";
	}

	/**
	 * 保存用户收藏菜单
	 */
	@RequiresPermissions(value={"sys:sysUserCollectionMenu:add","sys:sysUserCollectionMenu:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysUserCollectionMenu sysUserCollectionMenu, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysUserCollectionMenu)){
			return form(sysUserCollectionMenu, model);
		}
		if(!sysUserCollectionMenu.getIsNewRecord()){//编辑表单保存
			SysUserCollectionMenu t = sysUserCollectionMenuService.get(sysUserCollectionMenu.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysUserCollectionMenu, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysUserCollectionMenuService.save(t);//保存
		}else{//新增表单保存
			sysUserCollectionMenuService.save(sysUserCollectionMenu);//保存
		}
		addMessage(redirectAttributes, "保存用户收藏菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserCollectionMenu/?repage";
	}
	
	/**
	 * 删除用户收藏菜单
	 */
	@RequiresPermissions("sys:sysUserCollectionMenu:del")
	@RequestMapping(value = "delete")
	public String delete(SysUserCollectionMenu sysUserCollectionMenu, RedirectAttributes redirectAttributes) {
		sysUserCollectionMenuService.delete(sysUserCollectionMenu);
		addMessage(redirectAttributes, "删除用户收藏菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserCollectionMenu/?repage";
	}
	
	/**
	 * 批量删除用户收藏菜单
	 */
	@RequiresPermissions("sys:sysUserCollectionMenu:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysUserCollectionMenuService.delete(sysUserCollectionMenuService.get(id));
		}
		addMessage(redirectAttributes, "删除用户收藏菜单成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserCollectionMenu/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysUserCollectionMenu:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysUserCollectionMenu sysUserCollectionMenu, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户收藏菜单"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysUserCollectionMenu> page = sysUserCollectionMenuService.findPage(new Page<SysUserCollectionMenu>(request, response, -1), sysUserCollectionMenu);
    		new ExportExcel("用户收藏菜单", SysUserCollectionMenu.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户收藏菜单记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserCollectionMenu/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysUserCollectionMenu:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysUserCollectionMenu> list = ei.getDataList(SysUserCollectionMenu.class);
			for (SysUserCollectionMenu sysUserCollectionMenu : list){
				try{
					sysUserCollectionMenuService.save(sysUserCollectionMenu);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条用户收藏菜单记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条用户收藏菜单记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户收藏菜单失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserCollectionMenu/?repage";
    }
	
	/**
	 * 下载导入用户收藏菜单数据模板
	 */
	@RequiresPermissions("sys:sysUserCollectionMenu:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户收藏菜单数据导入模板.xlsx";
    		List<SysUserCollectionMenu> list = Lists.newArrayList(); 
    		new ExportExcel("用户收藏菜单数据", SysUserCollectionMenu.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysUserCollectionMenu/?repage";
    }


	@RequestMapping(value = "collectionMenu")
	@ResponseBody
	public String collectionMenu(Model model,SysUserCollectionMenu sysUserCollectionMenu){
		User user = UserUtils.getUser();
		String result = "";
		try {
			sysUserCollectionMenu.setUser(user);
			sysUserCollectionMenu.setCreateDate(new Date());
			String href = sysUserCollectionMenu.getMenuUrl();
			if(StringUtils.isNotEmpty(href)){
				String menuId = sysUserCollectionMenu.getMenuId();
				if(href.contains("?")){
					href+="&menuId="+menuId;
				}else{
					href+="?menuId="+menuId;
				}
			}
			sysUserCollectionMenu.setMenuUrl(href);
			if("add".equals(sysUserCollectionMenu.getFlag())){
                sysUserCollectionMenuService.save(sysUserCollectionMenu);
				result = "收藏成功！";

            }else if("delete".equals(sysUserCollectionMenu.getFlag())){
                sysUserCollectionMenuService.deleteDataByUserAndMenu(sysUserCollectionMenu);
				result = "取消收藏成功！";
            }
		} catch (Exception e) {
			result = "操作失败！";
			e.printStackTrace();
		}
		return  result;
	}

}