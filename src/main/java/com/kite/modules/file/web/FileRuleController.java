/**
 * KITE
 */
package com.kite.modules.file.web;

import java.util.*;

import javax.annotation.processing.Filer;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolationException;

import com.kite.common.result.ResultSupport;
import com.kite.common.utils.*;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.OfficeService;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.file.entity.FileRule;
import com.kite.modules.file.service.FileRuleService;

/**
 * 文件权限规则Controller
 * @author yyw
 * @version 2018-09-20
 */
@Controller
@RequestMapping(value = "${adminPath}/file/fileRule")
public class FileRuleController extends BaseController implements BasicVerification {

	@Autowired
	private FileRuleService fileRuleService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public FileRule get(@RequestParam(required=false) String id) {
		FileRule entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fileRuleService.get(id);
		}
		if (entity == null){
			entity = new FileRule();
		}
		return entity;
	}

	/**
	 * 文件权限规则列表页面
	 */
	@RequiresPermissions("file:fileRule:list")
	@RequestMapping(value = {"list", ""})
	public String list(FileRule fileRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FileRule> page = fileRuleService.findPage(new Page<FileRule>(request, response), fileRule);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/file/fileRuleList";
	}

	@RequestMapping(value = "openRule")
	public String openRule(FileRule fileRule, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FileRule> page = fileRuleService.findPage(new Page<FileRule>(request, response), fileRule);
		model.addAttribute("page", page);
		model.addAttribute("listAuthorityId",fileRule.getListAuthorityId());
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/file/openRule";
	}



	/**
	 * 查看，增加，编辑文件权限规则表单页面
	 */
	@RequiresPermissions(value={"file:fileRule:view","file:fileRule:add","file:fileRule:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FileRule fileRule, Model model) {
		model.addAttribute("officeList", officeService.findList(true, null, null));
		model.addAttribute("fileRule", fileRule);
		if(fileRule.getId()==null){
			// fileRule.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/file/fileRuleForm";
	}

	/**
	 * 查看打印文件权限规则表单页面
	 */
	@RequiresPermissions(value={"file:fileRule:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(FileRule fileRule, Model model) {
		model.addAttribute("fileRule", fileRule);
		return "modules/file/fileRuleView";
	}

	/**
	 * 保存文件权限规则
	 */
	@RequiresPermissions(value={"file:fileRule:add","file:fileRule:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FileRule fileRule, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if(!fileRule.getIsNewRecord()){//编辑表单保存
			FileRule t = fileRuleService.get(fileRule.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(fileRule, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			fileRuleService.save(t);//保存
		}else{//新增表单保存
			fileRuleService.save(fileRule);//保存
		}
		addMessage(redirectAttributes, "保存文件权限规则成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileRule/?menuId="+fileRule.getMenuId();
	}

	/**
	 * 删除文件权限规则
	 */
	@RequiresPermissions("file:fileRule:del")
	@RequestMapping(value = "delete")
	public String delete(FileRule fileRule, RedirectAttributes redirectAttributes) {
		fileRuleService.delete(fileRule);
		addMessage(redirectAttributes, "删除文件权限规则成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileRule/?repage";
	}

	/**
	 * 批量删除文件权限规则
	 */
	@RequiresPermissions("file:fileRule:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String[] idArray=ids.split(",");
		List<String> list = Arrays.asList(idArray);
		fileRuleService.deleteList(list);
		addMessage(redirectAttributes, "删除文件权限规则成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileRule/?repage";
	}

	@RequestMapping(value = "openUser")
	public String assign(String id,String ruleId,Model model) {

		FileRule fileRule = fileRuleService.get(id);

		String userId = fileRule == null?"":fileRule.getUserId();

		List<String> listUserId = new ArrayList<String>();
		if(StringUtils.isNotEmpty(userId)) {
			String[] usrArr = userId.split(",");
			listUserId = Arrays.asList(usrArr);
		}

		User select = new User();
		select.setListId(listUserId);

		List<User> userList = ListUtils.isNull(listUserId)?new ArrayList<User>():systemService.list(select);

		model.addAttribute("userList", userList);
		model.addAttribute("fileRule",fileRule);
		model.addAttribute("ruleId",ruleId);
		return "modules/file/openRuleUser";
	}

	/**
	 　　* @Description: 获取用户信息
	 　　* @author yyw
	 　　* @date 2018/9/3 13:50
	 */
	@RequestMapping(value = "getUserValue",method = RequestMethod.POST)
	@ResponseBody
	public ResultSupport<FileRule> getUserValue(@RequestBody FileRule fileRule) {
		ResultSupport<FileRule> result = ResultSupport.createMisResp();
		User user = UserUtils.getUser();
		try{

			String str = fileRuleService.getUserValue(fileRule);
			fileRule.setUserName(str);
			ResultSupportUtilsV2.fillResultSupport(result, "获取用户信息成功", fileRule);

		} catch (Exception e) {
			e.printStackTrace();
			ResultSupportUtilsV2.fillError(result,"系统出现异常！");
		}
		return result;
	}

    @RequestMapping(value = "updateUser")
    public String updataUser( HttpServletRequest request,FileRule fileRule, Model model) {

        //获取对应的用户实体
        String id = fileRuleService.updataUserInfo(fileRule);
        return "redirect:"+Global.getAdminPath()+"/file/fileRule/openUser?id="+fileRule.getId();
    }

//	//获取对应的用户实体
//	String ruleId =  fileRuleService.updataUserInfo(fileRule);
//	HttpSession session = request.getSession();
//
//	Map<String, String> map = new HashMap<String, String>();
//		map.put("ruleId", ruleId);
//		session.setAttribute("map",map);
//        return "redirect:"+Global.getAdminPath()+"/file/fileRule/openUser?id="+fileRule.getId();

    @RequestMapping(value = "deleteUser")
    public String deleteUser(FileRule fileRule, Model model) {

        String id = fileRule.getId();
        String deleteId = fileRule.getUserId();


        //获取原先数据
		FileRule f = fileRuleService.get(id);
        if(f == null) {
            model.addAttribute("message", "删除用户失败");
            return "modules/file/openRuleUser";
        }

        String userId = f.getUserId();
        String temp = userId.replace(deleteId,"");

        String[] arr = temp.split(",");

        StringBuffer sb = new StringBuffer("");

        for (String s : arr) {
            if(org.apache.commons.lang3.StringUtils.isNotEmpty(s)) {
                sb.append(s).append(",");
            }
        }

        String str = sb.toString();

		fileRuleService.updataUser(id, str.substring(0,str.length() - 1));

        return "redirect:"+Global.getAdminPath()+"/file/fileRule/openUser?id="+ id;
    }

	@RequestMapping(value = "listTree")
	public String listTree(FileRule fileRule, Model model) {

		FileRule f = fileRuleService.get(fileRule.getId());



		String userId = f == null?"":f.getUserId();
		List<String> list = new ArrayList<String>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(userId)) {
			String[] userArr = userId.split(",");
			if(userArr != null && userArr.length > 0) {
				list = Arrays.asList(userArr);
			}
		}

		User user = new User();
		user.setListId(list);

		model.addAttribute("fileRule", f);
		model.addAttribute("userList", ListUtils.isNull(list)?new ArrayList<User>():systemService.findUser(user));
		model.addAttribute("selectIds", Collections3.extractToString(ListUtils.isNull(list)?new ArrayList<User>():systemService.findUser(user), "name", ","));
		model.addAttribute("officeList", officeService.findList(true, null, null));

		return "modules/crmreport/crmPermissionsUserlistTree";
	}

}