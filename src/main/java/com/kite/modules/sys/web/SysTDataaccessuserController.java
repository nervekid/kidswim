/**
 * KITE
 */
package com.kite.modules.sys.web;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.Collections3;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.SysTDataaccess;
import com.kite.modules.sys.entity.SysTDataaccessuser;
import com.kite.modules.sys.entity.SysTDataaccessuserentity;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.*;
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
 * 多组织架构用户对应数据权限组Controller
 * @author lyb
 * @version 2018-10-31
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTDataaccessuser")
public class SysTDataaccessuserController extends BaseController implements BasicVerification {

	@Autowired
	private SysTDataaccessuserService sysTDataaccessuserService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SysTDataaccessService sysTDataaccessService;
	@Autowired
	private SysTDataaccessuserentityService sysTDataaccessuserentityService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private OfficeService officeService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysTDataaccessuser get(@RequestParam(required=false) String id) {
		SysTDataaccessuser entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysTDataaccessuserService.get(id);
		}
		if (entity == null){
			entity = new SysTDataaccessuser();
		}
		return entity;
	}

	/**
	 * 多组织架构用户对应数据权限组列表页面
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysTDataaccessuser sysTDataaccessuser, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysTDataaccessuser> page = sysTDataaccessuserService.findPage(new Page<SysTDataaccessuser>(request, response), sysTDataaccessuser);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysTDataaccessuserList";
	}


	/**
	 * 查看，增加，编辑多组织架构用户对应数据权限组表单页面
	 */
	@RequiresPermissions(value={"sys:sysTDataaccessuser:view","sys:sysTDataaccessuser:add","sys:sysTDataaccessuser:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysTDataaccessuser sysTDataaccessuser, Model model) {
		sysTDataaccessuser.setOrganTag("1");
		List<SysTDataaccess> dataAccessList = this.sysTDataaccessService.findList(new SysTDataaccess());
		model.addAttribute("sysTDataaccessuser", sysTDataaccessuser);
		model.addAttribute("dataAccessList", dataAccessList);
		if(sysTDataaccessuser.getId()==null){
			// sysTDataaccessuser.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysTDataaccessuserForm";
	}

	/**
	 * 查看打印多组织架构用户对应数据权限组表单页面
	 */
	@RequiresPermissions(value={"sys:sysTDataaccessuser:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysTDataaccessuser sysTDataaccessuser, Model model) {
		model.addAttribute("sysTDataaccessuser", sysTDataaccessuser);
		return "modules/sys/sysTDataaccessuserView";
	}

	/**
	 * 保存多组织架构用户对应数据权限组
	 */
	@RequiresPermissions(value={"sys:sysTDataaccessuser:add","sys:sysTDataaccessuser:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysTDataaccessuser sysTDataaccessuser, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysTDataaccessuser)){
			return form(sysTDataaccessuser, model);
		}
		if(!sysTDataaccessuser.getIsNewRecord()){//编辑表单保存
			SysTDataaccessuser t = sysTDataaccessuserService.get(sysTDataaccessuser.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysTDataaccessuser, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysTDataaccessuserService.save(t);//保存
		}else{//新增表单保存
			sysTDataaccessuserService.save(sysTDataaccessuser);//保存
		}
		addMessage(redirectAttributes, "保存多组织架构用户对应数据权限组成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuser/?menuId="+sysTDataaccessuser.getMenuId();
	}

	/**
	 * 删除多组织架构用户对应数据权限组
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:del")
	@RequestMapping(value = "delete")
	public String delete(SysTDataaccessuser sysTDataaccessuser, RedirectAttributes redirectAttributes) {
		sysTDataaccessuserService.delete(sysTDataaccessuser);
		addMessage(redirectAttributes, "删除多组织架构用户对应数据权限组成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuser/?repage";
	}

	/**
	 * 批量删除多组织架构用户对应数据权限组
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysTDataaccessuserService.delete(sysTDataaccessuserService.get(id));
		}
		addMessage(redirectAttributes, "删除多组织架构用户对应数据权限组成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuser/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysTDataaccessuser sysTDataaccessuser, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多组织架构用户对应数据权限组"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysTDataaccessuser> page = sysTDataaccessuserService.findPage(new Page<SysTDataaccessuser>(request, response, -1), sysTDataaccessuser);
    		new ExportExcel("多组织架构用户对应数据权限组", SysTDataaccessuser.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出多组织架构用户对应数据权限组记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuser/?menuId="+sysTDataaccessuser.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysTDataaccessuser:import")
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
				ei.write(response, "多组织架构用户对应数据权限组列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysTDataaccessuser> list = ei.getDataList(SysTDataaccessuser.class);
				for (SysTDataaccessuser sysTDataaccessuser : list){
					try{
						sysTDataaccessuserService.save(sysTDataaccessuser);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条多组织架构用户对应数据权限组记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条多组织架构用户对应数据权限组记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入多组织架构用户对应数据权限组失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuser/?menuId="+menuId;
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
	 * 下载导入多组织架构用户对应数据权限组数据模板
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "多组织架构用户对应数据权限组数据导入模板.xlsx";
    		List<SysTDataaccessuser> list = Lists.newArrayList();
    		new ExportExcel("多组织架构用户对应数据权限组数据", SysTDataaccessuser.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTDataaccessuser/?repage";
    }


	/**
	 * 用户分配页面
	 * @param sysTDataaccessuser
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:assign")
	@RequestMapping(value = "assign")
	public String assign(SysTDataaccessuser sysTDataaccessuser, Model model) {
		List<User> userList = this.sysTDataaccessuserService.findUserListByAccessId(sysTDataaccessuser.getId());
		model.addAttribute("userList", userList);
		model.addAttribute("sysTDataaccessuser", sysTDataaccessuser);
		return "modules/sys/accessAssign";
	}

	/**
	 * 用户分配 -- 打开用户分配对话框
	 * @param sysTDataaccessuser
	 * @param model
	 * @return
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:assign")
	@RequestMapping(value = "usertorole")
	public String selectUserToRole(SysTDataaccessuser sysTDataaccessuser, Model model) {
		if (sysTDataaccessuser.getOrganTag() == null || sysTDataaccessuser.getOrganTag().equals("")) {
			sysTDataaccessuser.setOrganTag("1");
		}
		List<User> userList = this.sysTDataaccessuserService.findUserListByAccessId(sysTDataaccessuser.getId());
		model.addAttribute("sysTDataaccessuser", sysTDataaccessuser);
		model.addAttribute("userList", userList);
		model.addAttribute("selectIds", Collections3.extractToString(userList, "id", ","));
		model.addAttribute("officeList", officeService.findAll(sysTDataaccessuser.getOrganTag(), null));
		return "modules/sys/selectUserToAccess";
	}

	/**
	 * 角色分配 -- 根据部门编号获取用户列表
	 * @param officeId
	 * @param response
	 * @return
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:assign")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String[] officeIds,String officeId, String organTag, HttpServletResponse response) {
		//List<Map<String, Object>> mapList = Lists.newArrayList();
		//User user = new User();
		//user.setOffice(new Office(officeId));
		//Page<User> page = systemService.findUserSelect(new Page<User>(1, -1), user, officeId, organTag);
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> userList = systemService.findUsersByOfficeIds(officeIds ,organTag);
		//for (User e : page.getList()) {
		for (User e : userList) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 角色分配
	 * @param role
	 * @param idsArr
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:assign")
	@RequestMapping(value = "assignUser")
	public String assignRole(SysTDataaccessuser sysTDataaccessuser, String[] idsArr, RedirectAttributes redirectAttributes) {
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		for (int i = 0; i < idsArr.length; i++) {
			String userId = idsArr[i];
			if (!this.sysTDataaccessuserentityService.isUserExited(sysTDataaccessuser.getId(), userId)) {
				SysTDataaccessuserentity sysTDataaccessuserentity = new SysTDataaccessuserentity();
				sysTDataaccessuserentity.setParentId(sysTDataaccessuser.getId());
				sysTDataaccessuserentity.setUserId(userId);
				this.sysTDataaccessuserentityService.save(sysTDataaccessuserentity);
				newNum++;
			}
		}
		addMessage(redirectAttributes, "已成功分配 "+newNum+" 个用户"+msg);
		return "redirect:" + adminPath + "/sys/sysTDataaccessuser/assign?id="+sysTDataaccessuser.getId();
	}

	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:sysTDataaccessuser:assign")
	@RequestMapping(value = "outUser")
	public String outrole(String userId, String id, RedirectAttributes redirectAttributes) {
		this.sysTDataaccessuserentityService.deleteByUserId(id, userId);
		return "redirect:" + adminPath + "/sys/sysTDataaccessuser/assign?id="+id;
	}

}