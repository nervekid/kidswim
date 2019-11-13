/**
 * KITE
 */
package com.kite.modules.file.web;

import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.kite.common.result.ResultSupport;
import com.kite.common.utils.*;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.modules.file.entity.FileCatalog;
import com.kite.modules.file.entity.FileFastdfs;
import com.kite.modules.file.entity.FileRule;
import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.security.UserRealm;
import com.kite.modules.sys.service.OfficeService;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.result.ResultSupport;
import com.kite.common.utils.Collections3;
import com.kite.common.utils.ListUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.file.entity.FileAuthority;
import com.kite.modules.file.service.FileAuthorityService;

import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.OfficeService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 文件权限Controller
 * @author yyw
 * @version 2018-08-29
 */
@Controller
@RequestMapping(value = "${adminPath}/file/fileAuthority")
public class FileAuthorityController extends BaseController implements BasicVerification {

	@Autowired
	private FileAuthorityService fileAuthorityService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private FileFastdfsService fileFastdfsService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;


	/**
	　　* @Description: 列表
	　　* @author yyw
	　　* @date 2018/8/29 14:21
	*/
	@RequiresPermissions("file:fileAuthority:list")
	@RequestMapping(value = "fileAuthorityList")
	public String fileAuthorityList(FileAuthority fileAuthority, Model model, HttpServletRequest request, HttpServletResponse response) {
		model.addAttribute("page",  fileAuthorityService.findPage(new Page<FileAuthority>(request, response), fileAuthority));
		return "modules/file/fileAuthorityList";
	}

	/**
	　　* @Description: 权限树
	　　* @author yyw
	　　* @date 2018/8/29 14:21
	*/
	@RequiresPermissions("file:fileAuthority:list")
	@RequestMapping(value = "fileAuthorityTree")
	public String fileAuthorityTree(FileAuthority fileAuthority, Model model) throws Exception{
		return "modules/file/fileAuthorityTree";
	}


	/**
	　　* @Description: 修改文件等级
	　　* @author yyw
	　　* @date 2018/8/29 15:19
	*/
	@RequiresPermissions("file:fileAuthority:uploadFileLevel")
	@RequestMapping(value = "uploadFileLevel",method = RequestMethod.POST)
	public String updataCatalog( FileAuthority fileAuthority,RedirectAttributes redirectAttributes) {
		try{
			fileAuthorityService.updataCatalog(fileAuthority);
            addMessage(redirectAttributes, "修改文件等级成功");
		} catch (Exception e) {
			e.printStackTrace();
            addMessage(redirectAttributes, "修改文件等级失败");
		}
        return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/fileAuthorityList/?repage";
	}

	@ModelAttribute
	public FileAuthority get(@RequestParam(required=false) String id) {
//		FileAuthority entity = null;
//		if (StringUtils.isNotBlank(id)){
//			entity = fileAuthorityService.getByFile(id);
//
//			//设置FileAuthority的主键值
//			//若该根据该文件id获取到权限信息，则设置对应的主键id，否则设置未空
//			if(entity != null) {
//				FileAuthority exist = fileAuthorityService.getByFileId(id);
//				if(exist != null ) {
//					entity.setId(exist.getId());
//				}else {
//					entity.setId(null);
//				}
//			}
//		}
//		if (entity == null){
//			entity = new FileAuthority();
//		}
//		return entity;

		FileAuthority entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fileAuthorityService.get(id);
		}
		if (entity == null){
			entity = new FileAuthority();
		}
		return entity;
	}

	/**
	 * 查看，增加，编辑文件权限表单页面
	 */
	@RequiresPermissions(value={"file:fileAuthority:view","file:fileAuthority:add","file:fileAuthority:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FileAuthority fileAuthority, Model model) {
		model.addAttribute("officeList", officeService.findList(true, null, null));
		model.addAttribute("fileAuthority", fileAuthority);
		if(fileAuthority.getId()==null){
			// fileAuthority.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/file/fileAuthorityForm";
	}

	@RequestMapping(value = "openUser")
	public String assign(String  id,Model model) {

		FileAuthority fileAuthority = fileAuthorityService.get(id);

		if(fileAuthority == null) {
			model.addAttribute("message", "打开用户列表失败");
			return "modules/file/openUser";
		}

		String userId = fileAuthority == null?"":fileAuthority.getUserId();

		List<String> listUserId = new ArrayList<String>();
		if(org.apache.commons.lang3.StringUtils.isNotEmpty(userId)) {
			String[] usrArr = userId.split(",");
			listUserId = Arrays.asList(usrArr);
		}

		User select = new User();
		select.setListId(listUserId);

		List<User> userList = ListUtils.isNull(listUserId)?new ArrayList<User>():systemService.list(select);

		model.addAttribute("userList", userList);
		model.addAttribute("fileAuthority",fileAuthority);
		return "modules/file/openUser";
	}


    @RequestMapping(value = "listTree")
    public String listTree(FileAuthority fileAuthority, Model model) {

		FileAuthority f = fileAuthorityService.get(fileAuthority.getId());

        String userId = f.getUserId();
        List<String> list = new ArrayList<String>();
        if(org.apache.commons.lang3.StringUtils.isNotEmpty(userId)) {
            String[] userArr = userId.split(",");
            if(userArr != null && userArr.length > 0) {
                list = Arrays.asList(userArr);
            }
        }

        User user = new User();
        user.setListId(list);

        model.addAttribute("fileAuthority", f);
        model.addAttribute("userList", ListUtils.isNull(list)?new ArrayList<User>():systemService.findUser(user));
        model.addAttribute("selectIds", Collections3.extractToString(ListUtils.isNull(list)?new ArrayList<User>():systemService.findUser(user), "name", ","));
        model.addAttribute("officeList", officeService.findList(true, null, null));

        return "modules/crmreport/crmPermissionsUserlistTree";
    }


    @RequestMapping(value = "updateUser")
    public String updataUser(FileAuthority fileAuthority, Model model) {


        //获取对应的用户实体
        fileAuthorityService.updataUserInfo(fileAuthority);

//        //返回最新数据到页面
//        List<User>  userList = new ArrayList<User>();
//        if(org.apache.commons.lang3.StringUtils.isNotEmpty(insertId)) {
//            String[] arr = insertId.split(",");
//            if(arr != null && arr.length > 0) {
//                User select = new User();
//                select.setListId(Arrays.asList(arr));
//                userList = systemService.findUser(select);
//            }
//        }


//        model.addAttribute("userList", userList);
//        model.addAttribute("fileId", f.getFileId());
        return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/openUser?id="+fileAuthority.getId();
    }


    /**
    　　* @Description: 获取用户信息
    　　* @author yyw
    　　* @date 2018/9/3 13:50
    */
	@RequestMapping(value = "getUserValue",method = RequestMethod.POST)
	@ResponseBody
	public ResultSupport<FileAuthority> getUserValue(@RequestBody FileAuthority fileAuthority) {
		ResultSupport<FileAuthority> result = ResultSupport.createMisResp();
		User user = UserUtils.getUser();
		try{

			String str = fileAuthorityService.getUserValue(fileAuthority);
			fileAuthority.setListUser(str);
			ResultSupportUtilsV2.fillResultSupport(result, "获取用户信息成功", fileAuthority);
		} catch (Exception e) {
			e.printStackTrace();
			ResultSupportUtilsV2.fillError(result,"系统出现异常！");
		}
		return result;
	}

    @RequestMapping(value = "deleteUser")
    public String deleteUser(FileAuthority fileAuthority, Model model) {

	    String id = fileAuthority.getId();
	    String deleteId = fileAuthority.getUserId();


	    //获取原先数据
        FileAuthority f = fileAuthorityService.get(id);
        if(f == null) {
            model.addAttribute("message", "删除用户失败");
            return "modules/file/openUser";
        }

        String userId = f.getUserId();
        String temp = userId.replace(deleteId,"");

        String[] arr = temp.split(",");

        StringBuffer sb = new StringBuffer("");
        for (String s : arr) {
            if(StringUtils.isNotEmpty(s)) {
                sb.append(s).append(",");
            }
        }

        String str = sb.toString();

		String userStr = StringUtils.isEmpty(str)?"":str.substring(0,str.length() - 1);


        fileAuthorityService.updataUser(id,userStr);

        return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/openUser?id=	"+ id;
    }



	/**
	 * 查看打印文件权限表单页面
	 */
	@RequiresPermissions(value={"file:fileAuthority:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(FileAuthority fileAuthority, Model model) {
		model.addAttribute("fileAuthority", fileAuthority);
		return "modules/file/fileAuthorityView";
	}

	/**
	 * 保存文件权限
	 */
	@RequiresPermissions(value={"file:fileAuthority:add","file:fileAuthority:edit"},logical=Logical.OR)
	@RequestMapping(value = "save",method = RequestMethod.POST)
	public String save(FileAuthority fileAuthority, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, fileAuthority)){
			return form(fileAuthority, model);
		}

		//此处id的值对应的是fileid，并非主键
		//FileAuthority exist = fileAuthorityService.getByFileId(fileAuthority.getId());
		//如果该数据存在于权限表中，则证明是修改。否则是新增


		//if(exist != null){//编辑表单保存
		if(fileAuthority.getIsNewRecord()){//编辑表单保存
			//修改的情况，id fileId取数据库
			//fileAuthority.setId(exist.getId());
			//fileAuthority.setFileId(exist.getFileId());
			FileAuthority t = fileAuthorityService.get(fileAuthority.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(fileAuthority, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			fileAuthorityService.save(t);//保存
		}else{//新增表单保存
			//新增的情况，设置fileId 为id，设置id为空
			//fileAuthority.setFileId(fileAuthority.getId());
			//fileAuthority.setId(null);
			fileAuthorityService.save(fileAuthority);//保存
		}
		addMessage(redirectAttributes, "保存文件权限成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/fileAuthorityTree";
	}

	/**
	 * 删除文件权限
	 */
	@RequiresPermissions("file:fileAuthority:del")
	@RequestMapping(value = "delete")
	public String delete(FileAuthority fileAuthority, RedirectAttributes redirectAttributes) {
		fileAuthorityService.delete(fileAuthority);
		addMessage(redirectAttributes, "删除文件权限成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/?repage";
	}

	/**
	 * 批量删除文件权限
	 */
	@RequiresPermissions("file:fileAuthority:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fileAuthorityService.delete(fileAuthorityService.get(id));
		}
		addMessage(redirectAttributes, "删除文件权限成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/?repage";
	}



	@RequestMapping(value = "addRule")
	public String addRule(FileAuthority fileAuthority, HttpServletRequest request,RedirectAttributes redirectAttributes, HttpServletResponse response, Model model) {

		System.out.println(fileAuthority.getListAuthorityId());
		System.out.println(fileAuthority.getRuleId());
		fileAuthorityService.addRule(fileAuthority);
		addMessage(redirectAttributes, "保存文件权限成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileAuthority/fileAuthorityTree";
	}



}