/**
 * KITE
 */
package com.kite.modules.file.web;

import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import com.google.common.collect.Maps;
import com.kite.common.result.ResultSupport;
import com.kite.common.utils.ListUtils;
import com.kite.common.utils.api.ResultSupportUtilsV2;
import com.kite.modules.file.entity.FileFastdfs;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.User;
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
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import org.apache.commons.lang3.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.file.entity.FileCatalog;
import com.kite.modules.file.service.FileCatalogService;

/**
 * 文档目录Controller
 * @author yyw
 * @version 2018-08-22
 */
@Controller
@RequestMapping(value = "${adminPath}/file/fileCatalog")
public class FileCatalogController extends BaseController implements BasicVerification {

	@Autowired
	private FileCatalogService fileCatalogService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public FileCatalog get(@RequestParam(required=false) String id) {
		FileCatalog entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = fileCatalogService.get(id);
		}
		if (entity == null){
			entity = new FileCatalog();
		}
		return entity;
	}

	/**
	 * 文档目录列表页面
	 */
	@RequiresPermissions("file:fileCatalog:list")
	@RequestMapping(value = {"list", ""})
	public String list(FileCatalog fileCatalog, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<FileCatalog> page = fileCatalogService.findPage(new Page<FileCatalog>(request, response), fileCatalog);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/file/fileCatalogList";
	}


	/**
	 * 查看，增加，编辑文档目录表单页面
	 */
	@RequiresPermissions(value={"file:fileCatalog:view","file:fileCatalog:add","file:fileCatalog:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(FileCatalog fileCatalog, Model model) {

	    if(StringUtils.isNotEmpty(fileCatalog.getParentId())) {
	        FileCatalog parent = fileCatalogService.get(fileCatalog.getParentId());
	        if(parent != null) {
	            fileCatalog.setParentName(parent.getName());
            }
        }

		model.addAttribute("fileCatalog", fileCatalog);
		if(fileCatalog.getId()==null){
			// fileCatalog.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/file/fileCatalogForm";
	}



	/**
	 * 查看打印文档目录表单页面
	 */
	@RequiresPermissions(value={"file:fileCatalog:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(FileCatalog fileCatalog, Model model) {
		model.addAttribute("fileCatalog", fileCatalog);
		return "modules/file/fileCatalogView";
	}

	/**
	 * 保存文档目录
	 */
	@RequiresPermissions(value={"file:fileCatalog:add","file:fileCatalog:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(FileCatalog fileCatalog, Model model, RedirectAttributes redirectAttributes) throws Exception{

		//设置父信息
		String parentId = fileCatalog.getParentId();
		if(StringUtils.isNotEmpty(parentId)) {
			FileCatalog parent = fileCatalogService.get(parentId);
			if(parent != null) {
				fileCatalog.setParent(parent);
			}
		}else {
			//若没有父id，则设置0
			fileCatalog.setParentId("0");
		}

		//保存
		fileCatalogService.save(fileCatalog);

		addMessage(redirectAttributes, "保存文档目录成功");
		return "redirect:" + adminPath + "/file/fileCatalog/listAll";
	}
//
//	@RequiresPermissions(value={"file:fileCatalog:add","file:fileCatalog:edit"},logical=Logical.OR)
//	@RequestMapping(value = "save")
//	public String save(FileCatalog fileCatalog, Model model, RedirectAttributes redirectAttributes) throws Exception{
//		if (!beanValidator(model, fileCatalog)){
//			return form(fileCatalog, model);
//		}
//		if(!fileCatalog.getIsNewRecord()){//编辑表单保存
//			FileCatalog t = fileCatalogService.get(fileCatalog.getId());//从数据库取出记录的值
//			MyBeanUtils.copyBeanNotNull2Bean(fileCatalog, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
//			fileCatalogService.save(t);//保存
//		}else{//新增表单保存
//			fileCatalogService.save(fileCatalog);//保存
//		}
//		addMessage(redirectAttributes, "保存文档目录成功");
//		return "redirect:"+Global.getAdminPath()+"/file/fileCatalog/?menuId="+fileCatalog.getMenuId();
//	}

	/**
	 * 删除文档目录
	 */
	@RequiresPermissions("file:fileCatalog:del")
	@RequestMapping(value = "delete")
	public String delete(FileCatalog fileCatalog, RedirectAttributes redirectAttributes) {
		fileCatalogService.delete(fileCatalog);
		addMessage(redirectAttributes, "删除文档目录成功");
		return "redirect:" + adminPath + "/file/fileCatalog/listAll";
	}

	/**
	 * 批量删除文档目录
	 */
	@RequiresPermissions("file:fileCatalog:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			fileCatalogService.delete(fileCatalogService.get(id));
		}
		addMessage(redirectAttributes, "删除文档目录成功");
		return "redirect:"+Global.getAdminPath()+"/file/listAll/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("file:fileCatalog:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(FileCatalog fileCatalog, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文档目录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<FileCatalog> page = fileCatalogService.findPage(new Page<FileCatalog>(request, response, -1), fileCatalog);
    		new ExportExcel("文档目录", FileCatalog.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出文档目录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/file/fileCatalog/?menuId="+fileCatalog.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("file:fileCatalog:import")
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
				ei.write(response, "文档目录列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<FileCatalog> list = ei.getDataList(FileCatalog.class);
				for (FileCatalog fileCatalog : list){
					try{
						fileCatalogService.save(fileCatalog);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条文档目录记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条文档目录记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入文档目录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/file/fileCatalog/?menuId="+menuId;
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
	 * 下载导入文档目录数据模板
	 */
	@RequiresPermissions("file:fileCatalog:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "文档目录数据导入模板.xlsx";
    		List<FileCatalog> list = Lists.newArrayList();
    		new ExportExcel("文档目录数据", FileCatalog.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/file/fileCatalog/?repage";
    }

	@RequiresPermissions("file:fileCatalog:list")
	@RequestMapping(value = "listAll")
	public String listAll(FileCatalog fileCatalog, Model model) {
		model.addAttribute("list", fileCatalogService.findList(fileCatalog));
		return "modules/file/fileCatalogAllList";
	}


	@RequiresPermissions("file:fileCatalog:tree")
	@RequestMapping(value = "listTree")
	public String listTree(FileCatalog fileCatalog, Model model) {
		return "modules/file/fileCatalogTreeList";
	}

    @RequiresPermissions("file:fileCatalog:tree")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId,HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        FileCatalog select = new FileCatalog();
        select.setExtId(extId);
        List<FileCatalog> list = fileCatalogService.findList(select);
        for (int i=0; i<list.size(); i++){
            FileCatalog fc = list.get(i);

            Map<String, Object> map = Maps.newHashMap();
            map.put("id", fc.getId());
            map.put("pId", fc.getParentId());
            map.put("pIds", fc.getParentIds());
            map.put("name", fc.getName());
            mapList.add(map);
        }
        return mapList;
    }

	@ResponseBody
	@RequestMapping(value = "getByCatalogName")
	public ResultSupport<FileCatalog> getByCatalogName(@RequestBody  FileCatalog fileCatalog) {

		ResultSupport<FileCatalog> result = ResultSupport.createMisResp();
		User user = UserUtils.getUser();

		try{

			if(StringUtils.isEmpty(fileCatalog.getName())) {
				ResultSupportUtilsV2.fillError(result,"目录查找失败，传入的目录名称为空");
				return result;
			}

//			FileCatalog select = new FileCatalog();
//			select.setName(groupName);
			List<FileCatalog> list = fileCatalogService.findList(fileCatalog);

			if(ListUtils.isNull(list) || list.size() > 1) {
				ResultSupportUtilsV2.fillError(result,"目录获取失败，数据维护出现问题");
				return result;
			}

			ResultSupportUtilsV2.fillResultSupport(result, "文件目录查找成功", list.get(0));
		} catch (Exception e) {
			e.printStackTrace();
			ResultSupportUtilsV2.fillError(result,"系统出现异常！");
		}

		return result;
	}

}