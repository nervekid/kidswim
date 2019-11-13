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
import com.kite.modules.att.entity.SerRollCall;
import com.kite.modules.att.service.SerRollCallService;

/**
 * 点名Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serRollCall")
public class SerRollCallController extends BaseController implements BasicVerification {

	@Autowired
	private SerRollCallService serRollCallService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerRollCall get(@RequestParam(required=false) String id) {
		SerRollCall entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serRollCallService.get(id);
		}
		if (entity == null){
			entity = new SerRollCall();
		}
		return entity;
	}

	/**
	 * 点名列表页面
	 */
	@RequiresPermissions("att:serRollCall:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerRollCall serRollCall, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerRollCall> page = serRollCallService.findPage(new Page<SerRollCall>(request, response), serRollCall);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serRollCallList";
	}


	/**
	 * 查看，增加，编辑点名表单页面
	 */
	@RequiresPermissions(value={"att:serRollCall:view","att:serRollCall:add","att:serRollCall:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerRollCall serRollCall, Model model) {
		model.addAttribute("serRollCall", serRollCall);
		if(serRollCall.getId()==null){
			// serRollCall.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/serRollCallForm";
	}

	/**
	 * 查看打印点名表单页面
	 */
	@RequiresPermissions(value={"att:serRollCall:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerRollCall serRollCall, Model model) {
		model.addAttribute("serRollCall", serRollCall);
		return "modules/att/serRollCallView";
	}

	/**
	 * 保存点名
	 */
	@RequiresPermissions(value={"att:serRollCall:add","att:serRollCall:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerRollCall serRollCall, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serRollCall)){
			return form(serRollCall, model);
		}
		if(!serRollCall.getIsNewRecord()){//编辑表单保存
			SerRollCall t = serRollCallService.get(serRollCall.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(serRollCall, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			serRollCallService.save(t);//保存
		}else{//新增表单保存
			serRollCallService.save(serRollCall);//保存
		}
		addMessage(redirectAttributes, "保存点名成功");
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?menuId="+serRollCall.getMenuId();
	}

	/**
	 * 删除点名
	 */
	@RequiresPermissions("att:serRollCall:del")
	@RequestMapping(value = "delete")
	public String delete(SerRollCall serRollCall, RedirectAttributes redirectAttributes) {
		serRollCallService.delete(serRollCall);
		addMessage(redirectAttributes, "删除点名成功");
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?repage";
	}

	/**
	 * 批量删除点名
	 */
	@RequiresPermissions("att:serRollCall:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serRollCallService.delete(serRollCallService.get(id));
		}
		addMessage(redirectAttributes, "删除点名成功");
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:serRollCall:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerRollCall serRollCall, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "点名"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerRollCall> page = serRollCallService.findPage(new Page<SerRollCall>(request, response, -1), serRollCall);
    		new ExportExcel("点名", SerRollCall.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出点名记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?menuId="+serRollCall.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:serRollCall:import")
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
				ei.write(response, "点名列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerRollCall> list = ei.getDataList(SerRollCall.class);
				for (SerRollCall serRollCall : list){
					try{
						serRollCallService.save(serRollCall);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条点名记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条点名记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入点名失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?menuId="+menuId;
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
	 * 下载导入点名数据模板
	 */
	@RequiresPermissions("att:serRollCall:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "点名数据导入模板.xlsx";
    		List<SerRollCall> list = Lists.newArrayList();
    		new ExportExcel("点名数据", SerRollCall.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?repage";
    }




}