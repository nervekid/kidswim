/**
 * KITE
 */
package com.kite.modules.file.web;

import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.result.ResultSupport;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.file.entity.FileCatalog;
import com.kite.modules.file.entity.FileFastdfs;
import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * fastdfs文件管理Controller
 * @author yyw
 * @version 2018-08-17
 */
@Controller
@RequestMapping(value = "${adminPath}/file/fileFastdfs")
public class FileFastdfsController extends BaseController implements BasicVerification {

	@Autowired
	private FileFastdfsService fileFastdfsService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public FileFastdfs get(@RequestParam(required=false) String id) {
		FileFastdfs entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fileFastdfsService.get(id);
		}
		if (entity == null){
			entity = new FileFastdfs();
		}
		return entity;
	}

	/**
	 * 文件列表页面
	 */
	@RequiresPermissions("file:fileFastdfs:list")
	@RequestMapping(value = "list")
	public String list(FileFastdfs fileFastdfs, HttpServletRequest request, HttpServletResponse response, Model model) {

		fileFastdfs.setDelFlag("0");
		Page<FileFastdfs> page = fileFastdfsService.findPage(new Page<FileFastdfs>(request, response), fileFastdfs);

		model.addAttribute("page", page);

		sysUserCollectionMenuService.initCollectionMenu(request,model);

		return "modules/file/fileFastdfsList";
	}


	/**
	 * 查看，增加，编辑文件表单页面
	 */
	@RequiresPermissions(value={"file:fileFastdfs:view","file:fileFastdfs:add","file:fileFastdfs:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FileFastdfs fileFastdfs, Model model) {
		model.addAttribute("fileFastdfs", fileFastdfs);
		if(fileFastdfs.getId()==null){
			// fileFastdfs.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/file/fileFastdfsForm";
	}

	/**
	　　* @Description: 打开上传窗口
	　　* @author yyw
	　　* @date 2018/8/23 10:19
	*/
    @RequiresPermissions("file:fileFastdfs:upload")
	@RequestMapping(value = "open")
	public String open(@RequestParam(value="group",required = false)String group,
                       @RequestParam(value="catalogId",required = false) String catalogId,
					   @RequestParam(value="catalogName",required = false)String catalogName,
                       @RequestParam(value="hidden",required = false,defaultValue = "false")Boolean ifHidden,   //是否隐藏按钮
                       FileFastdfs fileFastdfs, Model model) {

		//System.out.println(KitePropertiesUtil.getString("fastdfs.tomcat.url"));
		UserUtils.getUser();
    	fileFastdfs.setGroup(org.apache.commons.lang3.StringUtils.isEmpty(group)?"":group);
		fileFastdfs.setCatalogId(org.apache.commons.lang3.StringUtils.isEmpty(catalogId)?"":catalogId);
		fileFastdfs.setCatalogName(org.apache.commons.lang3.StringUtils.isEmpty(catalogName)?"":catalogName);

		model.addAttribute("fileFastdfs", fileFastdfs);
        model.addAttribute("hidden", ifHidden);
		if(fileFastdfs.getId()==null){
			// fileFastdfs.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/file/fileFastdfsOpen";
	}

	/**
	 * 查看打印文件表单页面
	 */
	@RequiresPermissions(value={"file:fileFastdfs:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(FileFastdfs fileFastdfs, Model model) {
		model.addAttribute("fileFastdfs", fileFastdfs);
		return "modules/file/fileFastdfsView";
	}

	/**
	 * 保存文件
	 */
	@RequiresPermissions(value={"file:fileFastdfs:add","file:fileFastdfs:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FileFastdfs fileFastdfs, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, fileFastdfs)){
			return form(fileFastdfs, model);
		}
		if(!fileFastdfs.getIsNewRecord()){//编辑表单保存

			List<String> list = new ArrayList<String>();
			list.add(fileFastdfs.getId());
			boolean flag = checkAuthority(list);

			if(!flag) {
				addMessage(redirectAttributes, "保存文件失败，你没有修改该文件的权限");
				return "redirect:"+Global.getAdminPath()+"/file/fileFastdfs/catalogFile/?repage";
			}

			FileFastdfs t = fileFastdfsService.get(fileFastdfs.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(fileFastdfs, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			fileFastdfsService.save(t);//保存
		}else{//新增表单保存
			fileFastdfsService.save(fileFastdfs);//保存
		}
		addMessage(redirectAttributes, "保存文件成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileFastdfs/catalogFile/?repage";
	}

	/**
	 * 删除文件
	 */
	@RequiresPermissions("file:fileFastdfs:del")
	@RequestMapping(value = "delete")
	public String delete(FileFastdfs fileFastdfs, RedirectAttributes redirectAttributes) {
		fileFastdfsService.delete(fileFastdfs);
		addMessage(redirectAttributes, "删除文件成功");
		return "redirect:"+Global.getAdminPath()+"/file/fileFastdfs/listAll/?repage";
	}

	/**
	 * 批量删除文件
	 */
	@RequiresPermissions("file:fileFastdfs:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids,RedirectAttributes redirectAttributes) {
		String catalogId;
	     try{
             String[] idArray =ids.split(",");

			catalogId= fileFastdfsService.get(idArray[0]).getCatalogId();
             User user = UserUtils.getUser();
             if(user != null && org.apache.commons.lang3.StringUtils.isNotEmpty(user.getId())) {

				 List<String> list = Arrays.asList(idArray);
				 boolean flag = checkAuthority(list);

				 if(!flag) {
					 addMessage(redirectAttributes, "删除文件失败，你没有删除该文件的权限");
					 return "redirect:"+Global.getAdminPath()+"/file/fileFastdfs/listAll/?catalogId="+catalogId+"&repage";
				 }

				 fileFastdfsService.deleteList(list,new Date(),user.getId());
                 addMessage(redirectAttributes, "删除文件成功");
             }else {
                 addMessage(redirectAttributes, "删除文件失败");
                 return "redirect:"+Global.getAdminPath()+"/file/fileFastdfs/listAll/?repage";
             }
         } catch (Exception e) {
             e.printStackTrace();
             throw new RuntimeException("删除文件信息出错");
         }

		return "redirect:"+Global.getAdminPath()+"/file/fileFastdfs/listAll/?catalogId="+catalogId+"&repage";

	}

    @RequiresPermissions("file:fileFastdfs:upload")
	@RequestMapping(value = "insert",method = RequestMethod.POST)
	@ResponseBody
	public List<FileFastdfs> insert(@RequestBody FileFastdfs fileFastdfs, Model model) throws Exception{
		try {
			List<FileFastdfs> list = fileFastdfsService.insert(fileFastdfs);//保存
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("添加文件信息出错");
		}
	}

	@RequiresPermissions("file:fileFastdfs:list")
	@RequestMapping(value = "listAll")
	public String listAll(FileFastdfs fileFastdfs, Model model,HttpServletRequest request, HttpServletResponse response) {

		fileFastdfs.setDelFlag("0");
		String param=request.getParameter("catalogId");
		String catalogId="";
		if(param!=null && !"".equals(param) ){
			catalogId =  request.getParameter("catalogId").replace("?datasource=dataSource1", "");
		}

		fileFastdfs.setCatalogId(catalogId);

		model.addAttribute("page",  fileFastdfsService.findPage(new Page<FileFastdfs>(request, response), fileFastdfs));

		return "modules/file/fileFastdfsAll";
	}

	@RequiresPermissions("file:fileFastdfs:list")
	@RequestMapping(value = "catalogFile")
	public String catalogFile(FileFastdfs fileFastdfs, Model model) throws Exception{
		return "modules/file/fileFastdfsTree";
	}

	/**
	　　* @Description: 文件转移
	　　* @author yyw
	　　* @date 2018/8/27 16:45
	*/
	@RequiresPermissions("file:fileFastdfs:updataCatalog")
	@RequestMapping(value = "updataCatalog",method = RequestMethod.POST)
    @ResponseBody
	public ResultSupport<FileFastdfs> updataCatalog(@RequestBody FileFastdfs fileFastdfs) {
		ResultSupport<FileFastdfs> result = ResultSupport.createMisResp();
		User user = UserUtils.getUser();
		try{

            List<String> list = fileFastdfs.getListId();
            boolean flag = checkAuthority(list);

            if(!flag) {
                ResultSupportUtilsV2.fillError(result,"转移文件失败，你没有转移该文件的权限");
				return result;
            }


            fileFastdfsService.updataCatalog(list,fileFastdfs.getCatalogId());
			ResultSupportUtilsV2.fillResultSupport(result, "文件转移成功", new FileFastdfs());
		} catch (Exception e) {
			e.printStackTrace();
			ResultSupportUtilsV2.fillError(result,"系统出现异常！");
		}
		return result;
	}


	private boolean checkAuthority(List<String> list) {
		return fileFastdfsService.checkAuthority(list);
	}
}