/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.web;


import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.esms.common.entity.GsmsResponse;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.kite.common.beanvalidator.BeanValidators;
import com.kite.common.config.Global;
import com.kite.common.json.AjaxJson;
import com.kite.common.mail.MailSendUtils;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.DesUtil;
import com.kite.common.utils.ListUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.utils.verification.BasicVerificationUtil;
import com.kite.common.web.BaseController;
import com.kite.modules.file.service.FileCatalogService;
import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.sys.dao.RoleDao;
import com.kite.modules.sys.dao.UserDao;
import com.kite.modules.sys.entity.Dict;
import com.kite.modules.sys.entity.MessageRecord;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.SystemConfig;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.entity.XuanwuCheck;
import com.kite.modules.sys.security.UsernamePasswordToken;
import com.kite.modules.sys.service.MessageRecordService;
import com.kite.modules.sys.service.SysOrganizationalService;
import com.kite.modules.sys.service.SystemConfigService;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.service.XuanwuCheckService;
import com.kite.modules.sys.utils.UserUtils;

import sun.misc.BASE64Decoder;

//				 _ooOoo_
//				o8888888o
//				88" . "88
//				(| -_- |)
//	 			 O\ = /O
//			 ____/`---'\____
// 		   .   ' \\| |// `.
//  	    / \\||| : |||// \
//  	   /_||||| -:- |||||- \
//  	    | | \\\ - /// | |
//  	  | \_| ''\---/'' | |
//         \ .-\__ `-` ___/-. /
//    ___  `. .' /--.--\ `. . __
//   ."" '< `.___\_<|>_/___.' >'"".
//  | | : `- \`.;`\ _ /`;.`/ - ` : | |
//\ \ `-. \_ __\ /__ _/ .-` / /
//======`-.____`-.___\_____/___.-`____.-'======
//   `=---='
//.............................................
//佛祖保佑             永无BUG
//佛曰:
//写字楼里写字间，写字间里程序员；
//程序人员写程序，又拿程序换酒钱。
//酒醒只在网上坐，酒醉还来网下眠；
//酒醉酒醒日复日，网上网下年复年。
//但愿老死电脑间，不愿鞠躬老板前；
//奔驰宝马贵者趣，公交自行程序员。
//别人笑我忒疯癫，我笑自己命太贱；
//不见满街漂亮妹，哪个归得程序员？

/**
 * 用户Controller
 *
 * @author kite
 * @version 2013-8-29
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/user")
public class UserController extends BaseController implements BasicVerification {


	@Autowired
	private SystemConfigService systemConfigService;

	@Autowired
	private SystemService systemService;
	@Autowired
	private UserDao userDao;
	@Autowired
	private FileFastdfsService fileFastdfsService;
	@Autowired
	private FileCatalogService fileCatalogService;
	@Autowired
	private XuanwuCheckService xuanwuCheckService;
	@Autowired
	private SysOrganizationalService sysOrganizationalService;
	@Autowired
    private RoleDao  roleDao;
	@Autowired
	private MessageRecordService messageRecordService;


	/*** 是否导入错误提示 */
	private boolean isTip = false;

	@ModelAttribute
	public User get(@RequestParam(required = false) String id) {
		if (StringUtils.isNotBlank(id)) {
			User user = systemService.getUser(id);

			if (user != null) {
				List<Role> listRole = user.getRoleList();
				StringBuffer sb = new StringBuffer("");
				StringBuffer roleIdSb = new StringBuffer("");
				if (ListUtils.isNotNull(listRole)) {

					Map<String, String> map = new HashMap<>();

					for (Role role : listRole) {
						String key = role.getRoleLabel();
						String value = role.getSimpleName();
						String roleId = role.getId();

						if (map.containsKey(key)) {
							String temp = map.get(key);
							map.put(key, temp + "   " + value);
						} else {
							map.put(key, value);
						}
						roleIdSb.append(roleId).append(",");
					}

					if (!map.isEmpty()) {
						for (String key : map.keySet()) {
							String temp = "【" + key + "】" + map.get(key);
							sb.append(temp).append("\n");
						}
					}
				}

				user.setRoleNameStr(sb.toString());
				if (StringUtils.isNotEmpty(roleIdSb)) {
					user.setListRoleId(roleIdSb.substring(0, roleIdSb.length() - 1).toString());
				}
			}
			return user;
		} else {
			return new User();
		}
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = { "index" })
	public String index(User user, Model model) {
		return "modules/sys/userIndex";
	}

	@RequiresPermissions("sys:user:index")
	@RequestMapping(value = { "list", "" })
	public String list(User user, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<User> page = systemService.findUser(new Page<User>(request, response), user);
		model.addAttribute("page", page);
		return "modules/sys/userList";
	}

	@RequiresPermissions(value = { "sys:user:formal" }, logical = Logical.OR)
	@RequestMapping(value = "formal")
	public String formal(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
		model.addAttribute("user", user);
		model.addAttribute("allRoles", systemService.findAllRole());
		return "modules/sys/userFormal";
	}

	@RequiresPermissions(value = { "sys:user:formal" }, logical = Logical.OR)
	@RequestMapping(value = "updateFormal")
	public String updateFormal(User user, HttpServletRequest request, Model model,
			RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}

		// 保存用户信息
		systemService.saveUser(user);

		addMessage(redirectAttributes, "修改用户转正设置'" + user.getLoginName() + "'成功");
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@RequiresPermissions(value = { "sys:user:view", "sys:user:add", "sys:user:edit" }, logical = Logical.OR)
	@RequestMapping(value = "form")
	public String form(User user, Model model) {
		if (user.getCompany() == null || user.getCompany().getId() == null) {
			user.setCompany(UserUtils.getUser().getCompany());
		}
		if (user.getOffice() == null || user.getOffice().getId() == null) {
			user.setOffice(UserUtils.getUser().getOffice());
		}
	/*	if(StringUtils.isEmpty(user.getId())){
			String password = CreatePasswordUtil.makeRandomAcapital(1) + CreatePasswordUtil.makeRandomLowercase(1) + CreatePasswordUtil.makeRandomNum(6);
			model.addAttribute("createPassword",password);
		}
*/
		// 获取所有的角色信息
		List<Role> listRole = systemService.findAllRole();

		// 获取角色的父类信息
		Dict select = new Dict();
		select.setType("sysModules");
		select.setDelFlag("0");
		List<Dict> listDict = systemService.listDict(select);

		if (ListUtils.isNotNull(listDict)) {
			for (Dict dict : listDict) {
				Role r = new Role();
				r.setId(dict.getValue());
				r.setSimpleName(dict.getLabel());
				r.setDataScope("0");
				listRole.add(r);
			}

		}
		if(StringUtils.isEmpty(user.getListRoleId())){
			user.setListRoleId("6a9f45c46bae44d98dcad6b55d97726d");
			user.setRoleNameStr("【系统默认模块】默认角色");
			user.setRoleList(Lists.newArrayList(roleDao.get("6a9f45c46bae44d98dcad6b55d97726d"))); //默认角色
		}
		// 获取对应用户的id
		model.addAttribute("user", user);
		model.addAttribute("allRoles", listRole);

		return "modules/sys/userForm";
	}

	@RequiresPermissions(value = { "sys:user:add", "sys:user:edit" }, logical = Logical.OR)
	@RequestMapping(value = "save")
	public String save(User user, HttpServletRequest request, Model model, RedirectAttributes redirectAttributes) {
		try {
			if (Global.isDemoMode()) {
                addMessage(redirectAttributes, "演示模式，不允许操作！");
                return "redirect:" + adminPath + "/sys/user/list?repage";
            }
            boolean isNew = false;
            if(StringUtils.isEmpty(user.getId())){
				isNew = true;
			}
			// 修正引用赋值问题，不知道为何，Company和Office引用的一个实例地址，修改了一个，另外一个跟着修改。
			//user.setCompany(new Office(request.getParameter("company.id")));
			//user.setOffice(new Office(request.getParameter("office.id")));
			// 如果新密码为空，则不更换密码
			String newPassword = user.getNewPassword();
			if (StringUtils.isNotBlank(newPassword)) {
                user.setPassword(SystemService.entryptPasswordByMD5(user.getNewPassword()));
                //修改crm密码
                //this.systemService.resetCrmPassword(user.getMobile(), user.getNewPassword());
            }
			if (!beanValidator(model, user)) {
                return form(user, model);
            }
			if (!"true".equals(checkLoginName(user.getOldLoginName(), user.getLoginName()))) {
                addMessage(model, "保存用户'" + user.getLoginName() + "'失败，登录名已存在");
                return form(user, model);
            }
			// 角色数据有效性验证，过滤不在授权内的角色
			List<Role> roleList = Lists.newArrayList();
			String roleIdList = user.getListRoleId();
			List<Role> listAllRole = systemService.findAllRole();
			for (Role r : listAllRole) {
                if (roleIdList.contains(r.getId())) {
                    roleList.add(r);
                }
            }
			user.setRoleList(roleList);
			// 保存用户信息
			systemService.saveUser(user);
			if(StringUtils.isNotEmpty(newPassword) && !isNew){
				//SMSUtils.send(user.getLoginName(),"密码重置为："+newPassword);
			}
		/*	if(isNew && StringUtils.isNotEmpty(newPassword)){
				String content = "邮箱账号:"+user.getEmail()+" 邮箱密码、U客100网站密码:"+newPassword;
				GsmsResponse response = SMSUtils.send(user.getLoginName(),content);
				saveMessageRecord(user,content,response);
				logger.debug("response------------>"+response);
			}*/

			addMessage(redirectAttributes, "保存用户'" + user.getLoginName() + "'成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, "保存用户失败"+e.getMessage());
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "delete")
	public String delete(User user, RedirectAttributes redirectAttributes) {
		try {
			if (Global.isDemoMode()) {
                addMessage(redirectAttributes, "演示模式，不允许操作！");
                return "redirect:" + adminPath + "/sys/user/list?repage";
            }
			if (UserUtils.getUser().getId().equals(user.getId())) {
                addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
            } else if (!user.isAdmin()) {
                addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
            } else {
                systemService.deleteUser(user);
                addMessage(redirectAttributes, "删除用户成功");
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@RequiresPermissions("sys:user:enable")
	@RequestMapping(value = "enable")
	public String enable(User user, RedirectAttributes redirectAttributes) {
		try {
			if (Global.isDemoMode()) {
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/sys/user/list?repage";
			}
			if (UserUtils.getUser().getId().equals(user.getId())) {
				addMessage(redirectAttributes, "操作用户失败, 不允许停用当前用户");
			} else if (User.isAdmin(user.getId())) {
				addMessage(redirectAttributes, "操作用户失败, 不允许停用超级管理员用户");
			} else {
				systemService.saveUser(user);
				String enable ="1";
				if("1".equals(user.getEnable())){
					enable="0";
				}else if("0".equals(user.getEnable())){
					enable="1";
				}
				addMessage(redirectAttributes, "操作成功");
				sysOrganizationalService.changeUserId(user,enable);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}



	/**
	 * 批量删除用户
	 */
	@RequiresPermissions("sys:user:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] = ids.split(",");
		for (String id : idArray) {
			User user = systemService.getUser(id);
			if (Global.isDemoMode()) {
				addMessage(redirectAttributes, "演示模式，不允许操作！");
				return "redirect:" + adminPath + "/sys/user/list?repage";
			}
			if (UserUtils.getUser().getId().equals(user.getId())) {
				addMessage(redirectAttributes, "删除用户失败, 不允许删除当前用户");
			} else if (!user.isAdmin()) {
				addMessage(redirectAttributes, "删除用户失败, 不允许删除超级管理员用户");
			} else {
				systemService.deleteUser(user);
				addMessage(redirectAttributes, "删除用户成功");
			}
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导出用户数据
	 *
	 * @param user
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:export")
	@RequestMapping(value = "export", method = RequestMethod.POST)
	public String exportFile(User user, HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx";
			Page<User> page = systemService.findUser(new Page<User>(request, response, -1), user);
			new ExportExcel("用户数据", User.class).setDataList(page.getList()).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 导入用户数据
	 *
	 * @param file
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public String importFile(MultipartFile file, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		if (Global.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/user/list?repage";
		}
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			this.check(ei);
			if (!ei.isCheckOk) {
				this.isTip = true;
				ei.write(response, "用户列表导入失败结果" + DateUtils.getDate("yyyyMMddHHmmss") + ".xlsx");
				return null;
			} else {
				this.isTip = false;
				List<User> list = ei.getDataList(User.class);
				for (User user : list) {
					try {
						if ("true".equals(checkLoginName("", user.getLoginName()))) {
							user.setPassword(SystemService.entryptPasswordByMD5("123456"));
							BeanValidators.validateWithException(validator, user);
							systemService.saveUser(user);
							successNum++;
						} else {
							failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
							failureNum++;
						}
					} catch (ConstraintViolationException ex) {
						failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
						List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
						for (String message : messageList) {
							failureMsg.append(message + "; ");
							failureNum++;
						}
					} catch (Exception ex) {
						failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
					}
				}
				if (failureNum > 0) {
					failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
				}
				addMessage(redirectAttributes, "已成功导入 " + successNum + " 条用户" + failureMsg);
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入用户失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	@Override
	public void check(ImportExcel ei) {
		for (int i = ei.getRowFirstNum(); i <= ei.getRowLastNum(); i++) {
			Row row = ei.getSheet().getRow(i);

			/*
			 * // 校验公司部门名称 if
			 * (!this.systemService.isExitOfficeByName(row.getCell(0).toString()
			 * )) { ei.structureCheckResult(0, row, "归属公司名称已注册"); }
			 *
			 * // 校验公司部门名称 if
			 * (!this.systemService.isExitOfficeByName(row.getCell(1).toString()
			 * )) { ei.structureCheckResult(1, row, "归属部门名称已注册"); }
			 */

			// 校验登录名称
			Cell loginNameCell = row.getCell(0);
			loginNameCell.setCellType(Cell.CELL_TYPE_STRING);
			if (!this.systemService.isExitRepeatLoginName(loginNameCell.toString())) {
				ei.structureCheckResult(0, row, "用户登录名称不能与已存在的用户登录名称重复");
			}

			// 校验邮箱
			Cell emailCell = row.getCell(3);
			emailCell.setCellType(Cell.CELL_TYPE_STRING);
			if (!BasicVerificationUtil.emailCheck(emailCell.toString())) {
				ei.structureCheckResult(3, row, "正确的邮箱格式");
			}

			// 校验11位手机号码
			row.getCell(5).setCellType(Cell.CELL_TYPE_STRING);
			if (!BasicVerificationUtil.phoneElevenCheck(row.getCell(5).getStringCellValue())) {
				ei.structureCheckResult(5, row, "11位手机号码,以13,15,18开头");
			}

			/*
			 * // 校验角色名称 Cell roleNameCell = row.getCell(8);
			 * roleNameCell.setCellType(Cell.CELL_TYPE_STRING); if
			 * (!this.systemService.isExitRoleByNameVer(roleNameCell.toString())
			 * ) { ei.structureCheckResult(8, row, "拥有角色名称必须已注册"); }
			 */

		}
	}

	/**
	 * 下载导入用户数据模板
	 *
	 * @param response
	 * @param redirectAttributes
	 * @return
	 */
	@RequiresPermissions("sys:user:import")
	@RequestMapping(value = "import/template")
	public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
			String fileName = "用户数据导入模板.xlsx";
			List<User> list = Lists.newArrayList();
			list.add(UserUtils.getUser());
			new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
			return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息：" + e.getMessage());
		}
		return "redirect:" + adminPath + "/sys/user/list?repage";
	}

	/**
	 * 验证登录名是否有效
	 *
	 * @param oldLoginName
	 * @param loginName
	 * @return
	 */
	@ResponseBody
	@RequiresPermissions(value = { "sys:user:add", "sys:user:edit" }, logical = Logical.OR)
	@RequestMapping(value = "checkLoginName")
	public String checkLoginName(String oldLoginName, String loginName) {
		if (loginName != null && loginName.equals(oldLoginName)) {
			return "true";
		} else if (loginName != null && systemService.getUserByLoginName(loginName) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 用户信息显示
	 *
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "info")
	public String info(HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfo";
	}

	/**
	 * 用户信息显示编辑保存
	 *
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "infoEdit")
	public String infoEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if (user.getName() != null) {
				currentUser.setName(user.getName());
			}
			if (user.getEmail() != null) {
				currentUser.setEmail(user.getEmail());
			}
			if (user.getPhone() != null) {
				currentUser.setPhone(user.getPhone());
			}
			if (user.getMobile() != null) {
				currentUser.setMobile(user.getMobile());
			}
			if (user.getRemarks() != null) {
				currentUser.setRemarks(user.getRemarks());
			}
			// if(user.getPhoto() !=null )
			// currentUser.setPhoto(user.getPhoto());
			systemService.updateUserInfo(currentUser);
			if (__ajax) {// 手机访问
				AjaxJson j = new AjaxJson();
				j.setSuccess(true);
				j.setMsg("修改个人资料成功!");
				return renderString(response, j);
			}
			model.addAttribute("user", currentUser);
			model.addAttribute("Global", new Global());
			model.addAttribute("message", "保存用户信息成功");
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userInfoEdit";
	}

	/**
	 * 用户头像显示编辑保存
	 *
	 * @param user
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "imageEdit")
	public String imageEdit(User user, boolean __ajax, HttpServletResponse response, Model model) {
		User currentUser = UserUtils.getUser();
		if (StringUtils.isNotBlank(user.getName())) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userInfo";
			}
			if (user.getPhoto() != null) {
				currentUser.setPhoto(user.getPhoto());
			}
			systemService.updateUserInfo(currentUser);
			if (__ajax) {// 手机访问
				AjaxJson j = new AjaxJson();
				j.setSuccess(true);
				j.setMsg("修改个人头像成功!");
				return renderString(response, j);
			}
			model.addAttribute("message", "保存用户信息成功");
			return "modules/sys/userInfo";
		}
		model.addAttribute("user", currentUser);
		model.addAttribute("Global", new Global());
		return "modules/sys/userImageEdit";
	}

	/**
	 * 返回用户信息
	 *
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "infoData")
	public AjaxJson infoData() {
		AjaxJson j = new AjaxJson();
		j.setSuccess(true);
		j.setErrorCode("-1");
		j.setMsg("获取个人信息成功!");
		j.put("data", UserUtils.getUser());
		return j;
	}

	/**
	 * 修改个人用户密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "modifyPwd")
	public String modifyPwd(String oldPassword, String newPassword, Model model,
			RedirectAttributes redirectAttributes) {
		User user = UserUtils.getUser();
		if (StringUtils.isNotBlank(oldPassword) && StringUtils.isNotBlank(newPassword)) {
			if (Global.isDemoMode()) {
				model.addAttribute("message", "演示模式，不允许操作！");
				return "modules/sys/userModifyPwd";
			}
			if (SystemService.validatePasswordByMD5(oldPassword, user.getPassword())) {
				systemService.updatePasswordById(user.getId(), user.getLoginName(), newPassword);
				model.addAttribute("message", "修改密码成功");
			} else {
				model.addAttribute("message", "修改密码失败，旧密码错误");
			}
			return "modules/sys/userModifyPwd";
		}
		model.addAttribute("user", user);
		return "modules/sys/userModifyPwd";
	}

	/**
	 * 保存签名
	 */
	@ResponseBody
	@RequestMapping(value = "saveSign")
	public AjaxJson saveSign(User user, boolean __ajax, HttpServletResponse response, Model model) throws Exception {
		AjaxJson j = new AjaxJson();
		User currentUser = UserUtils.getUser();
		currentUser.setSign(user.getSign());
		systemService.updateUserInfo(currentUser);
		j.setMsg("设置签名成功");
		return j;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		// CacheUtils.remove(UserUtils.USER_CACHE,
		// UserUtils.USER_CACHE_LIST_BY_OFFICE_ID_ + officeId);
		List<User> list = systemService.findUserByOfficeId(officeId);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("pId", officeId);
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataAll")
	public List<Map<String, Object>> treeDataAll(HttpServletResponse response,String organTag) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findNoSelect(organTag);
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}

	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeDataAllIncludeSlectd")
	public List<Map<String, Object>> treeDataAllIncludeSlectd(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<User> list = systemService.findAllSelectList();
		for (int i = 0; i < list.size(); i++) {
			User e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", "u_" + e.getId());
			map.put("name", StringUtils.replace(e.getName(), " ", ""));
			mapList.add(map);
		}
		return mapList;
	}


	/**
	 * web端ajax验证用户名是否可用
	 *
	 * @param loginName
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "validateLoginName")
	public boolean validateLoginName(String loginName, HttpServletResponse response) {

		User user = userDao.findUniqueByProperty("login_name", loginName);
		if (user == null) {
			return true;
		} else {
			return false;
		}

	}

	/**
	 * web端ajax验证手机号是否可以注册（数据库中不存在）
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobile")
	public boolean validateMobile(String mobile, HttpServletResponse response, Model model) {
		User user = userDao.findUniqueByProperty("mobile", mobile);
		if (user == null) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * web端ajax验证手机号是否已经注册（数据库中已存在）
	 */
	@ResponseBody
	@RequestMapping(value = "validateMobileExist")
	public boolean validateMobileExist(String mobile, HttpServletResponse response, Model model) {
		User user = userDao.findUniqueByProperty("mobile", mobile);
		if (user != null) {
			return true;
		} else {
			return false;
		}
	}

	// @InitBinder
	// public void initBinder(WebDataBinder b) {
	// b.registerCustomEditor(List.class, "roleList", new
	// PropertyEditorSupport(){
	// @Autowired
	// private SystemService systemService;
	// @Override
	// public void setAsText(String text) throws IllegalArgumentException {
	// String[] ids = StringUtils.split(text, ",");
	// List<Role> roles = new ArrayList<Role>();
	// for (String id : ids) {
	// Role role = systemService.getRole(Long.valueOf(id));
	// roles.add(role);
	// }
	// setValue(roles);
	// }
	// @Override
	// public String getAsText() {
	// return Collections3.extractToString((List) getValue(), "id", ",");
	// }
	// });
	// }

	/**
	 * 忘记密码
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "resetPasswordIndex")
	public String resetPasswordIndex(Model model, RedirectAttributes redirectAttributes) {

		return "modules/sys/resetPasswordIndex";
	}

	/**
	 * @Description: 找回方式界面 @author yyw @date 2018/10/12 14:27
	 */
	@RequestMapping(value = "resetPasswordChoose")
	public String resetPasswordChoose(Model model, RedirectAttributes redirectAttributes) {
		return "modules/sys/resetPasswordChoose";
	}

	/**
	 * @Description: 短信忘记密码 @author yyw @date 2018/10/11 13:41
	 */
	@RequestMapping(value = "resetPasswordSmsIndex")
	public String resetPasswordSmsIndex(Model model, RedirectAttributes redirectAttributes) {

		return "modules/sys/resetPasswordSmsIndex";
	}

	@ResponseBody
	@RequestMapping(value = "resetPasswordAjax")
	public AjaxJson resetPasswordAjax(String loginName, String email, HttpServletResponse response, Model model) {
		AjaxJson j = new AjaxJson();
		try {
			if (StringUtils.isEmpty(loginName) || StringUtils.isEmpty(email)) {
				j.setSuccess(false);
				j.setMsg("登录名，邮箱不能为空!");
				j.setErrorCode("1");
				return j;
			}

			User userTmp = getUserByLoginNameAndEmail(loginName, email);
			if (userTmp == null) {
				j.setSuccess(false);
				j.setMsg("登录名和邮箱未能找到相对应的用户!");
				j.setErrorCode("1");
				return j;
			}
			int tmpPwd = (int) ((Math.random() * 9 + 1) * 100000);
			String _param1 = URLEncoder.encode(DesUtil.encrypt(loginName, Global.emailDesKey), "utf-8");
			String _param2 = URLEncoder.encode(DesUtil.encrypt(email, Global.emailDesKey), "utf-8");

			String materialContent = "<h1>您的临时密码为：" + tmpPwd
					+ " &nbsp;<forn><font color='red'>有效期为30分钟</font></h1><h2>请点击以下链接,或者复制到浏览器访问进行重置密码<br/></h2><h3><a href='https://kite.wxchina.com"
					+ adminPath + "/sys/user/resetPassword?_param1=" + _param1 + "&_param2=" + _param2
					+ "'>https://kite.wxchina.com" + adminPath + "/sys/user/resetPassword?_param1=" + _param1
					+ "&_param2=" + _param2 + "</a></h3>";
			SystemConfig config = systemConfigService.get("1");
			boolean isSuccess = MailSendUtils.sendEmail(config.getSmtp(), config.getPort(), config.getMailName(),
					config.getMailPassword(), email, "密码重置", materialContent, "0");
			if (isSuccess) {
				j.setSuccess(true);
				j.setErrorCode("-1");
				j.setMsg("邮件发送成功，请在邮箱查收邮件!");
				userTmp.setTmpPwd(tmpPwd + "");
				userTmp.setTmpPwdCreateDate(new Date());
				userDao.update(userTmp);
			}
		} catch (Exception e) {
			j.setSuccess(false);
			j.setErrorCode("3");
			j.setMsg("因未知原因导致短信发送失败，请联系管理员。");
			e.printStackTrace();
		}
		return j;
	}

	@RequestMapping(value = "resetPassword")
	public String resetPassword(String _param1, String _param2, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) throws UnsupportedEncodingException {
		String loginName = URLDecoder.decode(DesUtil.decrypt(_param1, Global.emailDesKey), "utf-8");
		String email = URLDecoder.decode(DesUtil.decrypt(_param2, Global.emailDesKey), "utf-8");
		User user = getUserByLoginNameAndEmail(loginName, email);
		boolean result = false;
		if (user != null) {
			Date tmpPwdCreateDate = user.getTmpPwdCreateDate();
			if (tmpPwdCreateDate != null) {
				if (System.currentTimeMillis() - tmpPwdCreateDate.getTime() > 60 * 30 * 1000) {
					result = true;
				}
			} else {
				result = true;
			}
		} else {
			addMessage(redirectAttributes, "系统内部出现问题，请向管理员反馈！");
			return "redirect:" + adminPath + "/login";
		}
		if (result) {
			addMessage(redirectAttributes, "此链接已经失效，请重新操作忘记密码操作！");
			return "redirect:" + adminPath + "/login";
		}
		String tmpPwd = user.getTmpPwd();
		if (StringUtils.isNotEmpty(tmpPwd)) {
			user.setTmpPwd("");
			user.setTmpPwdCreateDate(null);
			user.setPassword(SystemService.entryptPasswordByMD5(tmpPwd));
			userDao.update(user);
			// UserUtils.clearCache(user);
		}
		addMessage(redirectAttributes, "重置密码成功");
		return "redirect:" + adminPath + "/login";
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
	 * 通过登录名以及邮箱获取用户信息
	 *
	 * @param loginName
	 * @param email
	 * @return
	 */
	private User getUserByLoginNameAndEmail(String loginName, String email) {
		User user = new User();
		user.setLoginName(loginName);
		user.setEmail(email);
		User userTmp = userDao.getByLoginNameAndEmail(user);
		return userTmp;
	}

	@RequestMapping(value = "getByPhone", method = RequestMethod.POST)
	@ResponseBody
	public String getByPhone(String phone, HttpServletRequest request, HttpServletResponse response, Model model) {
		try {

			System.out.println(phone);

			List<String> list = new ArrayList<String>();
			list.add(phone);
			List<User> result = systemService.getByPhone(list);

			if (ListUtils.isNull(result)) {
				return "";
			}

			return result.get(0).getId();
		} catch (Exception e) {
			e.printStackTrace();
			addMessage(model, "通过电话获取用户失败，发生异常");
			return "";
		}
	}

	/**
	 * @Description: 手机短信重置界面 @author yyw @date 2018/10/12 9:13
	 */
	@RequestMapping(value = "openResetPassword", method = RequestMethod.POST)
	public String openResetPassword(String phone, HttpServletRequest request, HttpServletResponse response,
			Model model) {
		try {

			if (StringUtils.isEmpty(phone)) {
				addMessage(model, "打开界面失败");
				return "modules/sys/sysLogin";
			}

			model.addAttribute("phone", phone);
			addMessage(model, "页面有效期为3分钟，请尽快操作");

		} catch (Exception e) {
			e.printStackTrace();
			addMessage(model, "打开用户选择界面失败，发生异常");
		}
		return "modules/sys/resetPassword";
	}

	@RequestMapping(value = "resetPasswordBySms")
	public String resetPasswordBySms(String encodePhone, String encodepasword, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) throws Exception {

		if (StringUtils.isEmpty(encodePhone)) {
			addMessage(model, "你未输入用户名，请重新操作");
			return "modules/sys/resetPassword";
		}
		model.addAttribute("phone", new String(Base64.getDecoder().decode(encodePhone)));

		if (StringUtils.isEmpty(encodepasword)) {
			addMessage(model, "你未输入密码，请重新操作");
			return "modules/sys/resetPassword";
		}

		System.out.println(encodePhone);
		System.out.println(encodepasword);

		BASE64Decoder decoder = new BASE64Decoder();
		String phone = new String(decoder.decodeBuffer(encodePhone));
		String password = new String(decoder.decodeBuffer(encodepasword));

		System.out.println(phone);
		System.out.println(password);

		User user = systemService.getUserByLoginName(phone);

		if (user == null) {
			addMessage(model, "该用户已经失效");
			return "redirect:" + adminPath + "/login";
		}

		if (StringUtils.isNotEmpty(password)) {
			user.setTmpPwd("");
			user.setTmpPwdCreateDate(null);

			user.setPassword(SystemService.entryptPasswordByMD5(password));
			userDao.update(user);
			// UserUtils.clearCache(user);

			//更新crm密码
			//this.systemService.resetCrmPassword(user.getPhone(), password);
		}
		//tencentEmailUserService.updateTencentUserInfo(user);

		addMessage(redirectAttributes, "重置密码成功");
		return "redirect:" + adminPath + "/login";

	}

	@RequestMapping(value = "openIdentifyCode")
	public String openIdentifyCode(String encodePhone, HttpServletResponse response, Model model,
			RedirectAttributes redirectAttributes) throws Exception {

		if (StringUtils.isEmpty(encodePhone)) {
			addMessage(redirectAttributes, "你未输入用户名，请重新操作");
			return "modules/sys/resetPasswordSmsIndex";
		}

		BASE64Decoder decoder = new BASE64Decoder();
		String phone = new String(decoder.decodeBuffer(encodePhone));

		System.out.println(phone);
		model.addAttribute("phone", phone);
		return "modules/sys/inputIdentifyCode";
	}

	@RequestMapping(value = "autoLogin/{loginAcount}")
	public String autoLogin(@PathVariable(value = "loginAcount") String loginAcount, HttpServletResponse response,
			Model model, RedirectAttributes redirectAttributes) throws Exception {

		if (StringUtils.isEmpty(loginAcount)) {
			return "redirect:" + adminPath;
		}

		String decodeUserId = "";
		try {
			byte[] bytes = Base64.getDecoder().decode(loginAcount.getBytes());
			decodeUserId = new String(bytes, "UTF-8");
			System.out.println("==================" + decodeUserId);
		} catch (Exception e) {
			e.printStackTrace();
			return "error";
		}

		Subject currentUser = SecurityUtils.getSubject();
		List<XuanwuCheck> list = xuanwuCheckService.findList(new XuanwuCheck());
		XuanwuCheck xuanwuCheck = null;

		if (list != null && list.size() > 0) {
			xuanwuCheck = list.get(0);
		}
		UsernamePasswordToken token = null;

		if (xuanwuCheck != null) {
			String decrypt = Global.scanCodeExpress;
			token = new UsernamePasswordToken(decodeUserId, decrypt.toCharArray(), false, null, "null", false, true,
					xuanwuCheck.getXuanwup());
			currentUser.login(token);
		}

		User user = UserUtils.getUser();

		model.addAttribute("user", user);

		// return "redirect:" + adminPath;
		return "modules/sys/sysIndex";
	}

    @RequestMapping(value = "autoLoginCrm/{loginAcount}")
    public String  autoLoginCrm(@PathVariable(value="loginAcount") String loginAcount, HttpServletResponse response, Model model, RedirectAttributes redirectAttributes) throws Exception {
        if(StringUtils.isEmpty(loginAcount)) {
            return "redirect:" + adminPath;
        }
        String decodeUserId = "";
        try {
            byte[] bytes = Base64.getDecoder().decode(loginAcount.getBytes());
            decodeUserId = new String(bytes, "UTF-8");
            System.out.println("==================" + decodeUserId);
        } catch (Exception e) {
            e.printStackTrace();
            return "error";
        }

        Subject currentUser = SecurityUtils.getSubject();
        List<XuanwuCheck> list = xuanwuCheckService.findList(new XuanwuCheck());
        XuanwuCheck xuanwuCheck = null;

        if (list != null && list.size() > 0) {
            xuanwuCheck = list.get(0);
        }
        UsernamePasswordToken token = null;

        if (xuanwuCheck != null) {
            String decrypt = Global.scanCodeExpress;
            token = new UsernamePasswordToken(decodeUserId, decrypt.toCharArray(), false, null, "null", false, true, xuanwuCheck.getXuanwup());
            currentUser.login(token);
        }

        User user = UserUtils.getUser();

        model.addAttribute("user", user);

        //return "redirect:" + adminPath;
        return "redirect:" + adminPath + "/personal_tax/xwPersonalIncomeTax";
    }

    private void saveMessageRecord(User user,String content ,GsmsResponse resp){
		//短信发送成功、记录到message_record表
		MessageRecord messageRecord = new MessageRecord();
		messageRecord.setUpdateSendMessageDate(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
		messageRecord.setUserId(user.getId());
		messageRecord.setContent(content);
		messageRecord.setUserEmail(user.getEmail());
		messageRecord.setUserName(user.getName());
		messageRecord.setUserPhone(user.getMobile());
		messageRecord.setUserLoginName(user.getLoginName());
		//根据用户手机号查询之前发送次数、成功+1，失败+1
		List<MessageRecord> recordList = messageRecordService.findList(messageRecord);
		int successCount = 0;
		int failCount = 0;
		//第一次发送并且成功
		if(recordList.size()==0 && resp.getResult()==0) {
			messageRecord.setSuccessCount(1);
			messageRecord.setFailCount(0);
			messageRecord.setIsSuccess("0");
			messageRecordService.save(messageRecord);
		}
		//第一次发送并且失败
		if(recordList.size()==0 && resp.getResult()!=0){
			messageRecord.setSuccessCount(0);
			messageRecord.setFailCount(1);
			messageRecord.setIsSuccess("-1");
			messageRecordService.save(messageRecord);
		}
		//数次发送成功更新
		if(recordList.size()>0 && resp.getResult()==0){
			successCount= recordList.get(0).getSuccessCount();
			failCount = recordList.get(0).getFailCount();
			messageRecord.setSuccessCount(successCount+1);
			messageRecord.setFailCount(failCount);
			messageRecord.setIsSuccess("0");
			messageRecord.setUpdateSendMessageDate(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
			messageRecordService.updateMessageInfo(messageRecord);
		}
		//数次发送失败更新
		if(recordList.size()>0 && resp.getResult()!=0){
			successCount= recordList.get(0).getSuccessCount();
			failCount = recordList.get(0).getFailCount();
			messageRecord.setSuccessCount(successCount);
			messageRecord.setFailCount(failCount+1);
			messageRecord.setIsSuccess("-1");
			messageRecord.setUpdateSendMessageDate(new SimpleDateFormat("yyyy-MM-dd HH:ss:mm").format(new Date()));
			messageRecordService.updateMessageInfo(messageRecord);
		}
	}

	/**
	 * @Description: 短信忘记密码 @author yyw @date 2018/10/11 13:41
	 */
	@RequestMapping(value = "fileUpload")
	public String fileUpload(Model model, RedirectAttributes redirectAttributes) {

		return "modules/sys/fileUpload";
	}
}
