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
import com.kite.modules.att.entity.SerGroupDetails;
import com.kite.modules.att.service.SerGroupDetailsService;

/**
 * 分组明细Controller
 * @author lyb
 * @version 2019-12-19
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serGroupDetails")
public class SerGroupDetailsController extends BaseController implements BasicVerification {

	@Autowired
	private SerGroupDetailsService serGroupDetailsService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerGroupDetails get(@RequestParam(required=false) String id) {
		SerGroupDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serGroupDetailsService.get(id);
		}
		if (entity == null){
			entity = new SerGroupDetails();
		}
		return entity;
	}

	/**
	 * 分组明细列表页面
	 */
	@RequiresPermissions("att:serGroupDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerGroupDetails serGroupDetails, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerGroupDetails> page = serGroupDetailsService.findPage(new Page<SerGroupDetails>(request, response), serGroupDetails);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serGroupDetailsList";
	}


	/**
	 * 查看，增加，编辑分组明细表单页面
	 */
	@RequiresPermissions(value={"att:serGroupDetails:view","att:serGroupDetails:add","att:serGroupDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerGroupDetails serGroupDetails, Model model) {
		model.addAttribute("serGroupDetails", serGroupDetails);
		if(serGroupDetails.getId()==null){
			// serGroupDetails.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/serGroupDetailsForm";
	}

	/**
	 * 查看打印分组明细表单页面
	 */
	@RequiresPermissions(value={"att:serGroupDetails:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerGroupDetails serGroupDetails, Model model) {
		model.addAttribute("serGroupDetails", serGroupDetails);
		return "modules/att/serGroupDetailsView";
	}

	/**
	 * 保存分组明细
	 */
	@RequiresPermissions(value={"att:serGroupDetails:add","att:serGroupDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerGroupDetails serGroupDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serGroupDetails)){
			return form(serGroupDetails, model);
		}
		if(!serGroupDetails.getIsNewRecord()){//编辑表单保存
			SerGroupDetails t = serGroupDetailsService.get(serGroupDetails.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(serGroupDetails, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			serGroupDetailsService.save(t);//保存
		}else{//新增表单保存
			serGroupDetailsService.save(serGroupDetails);//保存
		}
		addMessage(redirectAttributes, "保存分组明细成功");
		return "redirect:"+Global.getAdminPath()+"/att/serGroupDetails/?menuId="+serGroupDetails.getMenuId();
	}

	/**
	 * 删除分组明细
	 */
	@RequiresPermissions("att:serGroupDetails:del")
	@RequestMapping(value = "delete")
	public String delete(SerGroupDetails serGroupDetails, RedirectAttributes redirectAttributes) {
		serGroupDetailsService.delete(serGroupDetails);
		addMessage(redirectAttributes, "删除分组明细成功");
		return "redirect:"+Global.getAdminPath()+"/att/serGroupDetails/?repage";
	}

	/**
	 * 批量删除分组明细
	 */
	@RequiresPermissions("att:serGroupDetails:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serGroupDetailsService.delete(serGroupDetailsService.get(id));
		}
		addMessage(redirectAttributes, "删除分组明细成功");
		return "redirect:"+Global.getAdminPath()+"/att/serGroupDetails/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:serGroupDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerGroupDetails serGroupDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分组明细"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerGroupDetails> page = serGroupDetailsService.findPage(new Page<SerGroupDetails>(request, response, -1), serGroupDetails);
    		new ExportExcel("分组明细", SerGroupDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出分组明细记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serGroupDetails/?menuId="+serGroupDetails.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:serGroupDetails:import")
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
				ei.write(response, "分组明细列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerGroupDetails> list = ei.getDataList(SerGroupDetails.class);
				for (SerGroupDetails serGroupDetails : list){
					try{
						serGroupDetailsService.save(serGroupDetails);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条分组明细记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条分组明细记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入分组明细失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serGroupDetails/?menuId="+menuId;
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
	 * 下载导入分组明细数据模板
	 */
	@RequiresPermissions("att:serGroupDetails:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "分组明细数据导入模板.xlsx";
    		List<SerGroupDetails> list = Lists.newArrayList();
    		new ExportExcel("分组明细数据", SerGroupDetails.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serGroupDetails/?repage";
    }




}