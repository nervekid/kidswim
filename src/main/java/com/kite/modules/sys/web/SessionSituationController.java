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
import com.kite.modules.sys.entity.SessionSituation;
import com.kite.modules.sys.service.SessionSituationService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.List;

/**
 * 系统会话Controller
 * @author lyb
 * @version 2018-03-15
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sessionSituation")
public class SessionSituationController extends BaseController {

	@Autowired
	private SessionSituationService sessionSituationService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	
	@ModelAttribute
	public SessionSituation get(@RequestParam(required=false) String id) {
		SessionSituation entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sessionSituationService.get(id);
		}
		if (entity == null){
			entity = new SessionSituation();
		}
		return entity;
	}
	
	/**
	 * 系统会话列表页面
	 */
	@RequiresPermissions("sys:sessionSituation:list")
	@RequestMapping(value = {"list", ""})
	public String list(SessionSituation sessionSituation, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SessionSituation> page = sessionSituationService.findPage(new Page<SessionSituation>(request, response), sessionSituation); 
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sessionSituationList";
	}


	/**
	 * 查看，增加，编辑系统会话表单页面
	 */
	@RequiresPermissions(value={"sys:sessionSituation:view","sys:sessionSituation:add","sys:sessionSituation:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SessionSituation sessionSituation, Model model) {
		model.addAttribute("sessionSituation", sessionSituation);
		if(sessionSituation.getId()==null){
			// sessionSituation.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sessionSituationForm";
	}

	/**
	 * 查看打印系统会话表单页面
	 */
	@RequiresPermissions(value={"sys:sessionSituation:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SessionSituation sessionSituation, Model model) {
		model.addAttribute("sessionSituation", sessionSituation);
		return "modules/sys/sessionSituationView";
	}

	/**
	 * 保存系统会话
	 */
	@RequiresPermissions(value={"sys:sessionSituation:add","sys:sessionSituation:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SessionSituation sessionSituation, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sessionSituation)){
			return form(sessionSituation, model);
		}
		if(!sessionSituation.getIsNewRecord()){//编辑表单保存
			SessionSituation t = sessionSituationService.get(sessionSituation.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sessionSituation, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sessionSituationService.save(t);//保存
		}else{//新增表单保存
			sessionSituationService.save(sessionSituation);//保存
		}
		addMessage(redirectAttributes, "保存系统会话成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sessionSituation/?menuId="+sessionSituation.getMenuId();
	}
	
	/**
	 * 删除系统会话
	 */
	@RequiresPermissions("sys:sessionSituation:del")
	@RequestMapping(value = "delete")
	public String delete(SessionSituation sessionSituation, RedirectAttributes redirectAttributes) {
		sessionSituationService.delete(sessionSituation);
		addMessage(redirectAttributes, "删除系统会话成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sessionSituation/?repage";
	}
	
	/**
	 * 批量删除系统会话
	 */
	@RequiresPermissions("sys:sessionSituation:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sessionSituationService.delete(sessionSituationService.get(id));
		}
		addMessage(redirectAttributes, "删除系统会话成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sessionSituation/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sessionSituation:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SessionSituation sessionSituation, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统会话"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SessionSituation> page = sessionSituationService.findPage(new Page<SessionSituation>(request, response, -1), sessionSituation);
    		new ExportExcel("系统会话", SessionSituation.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统会话记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sessionSituation/?menuId="+sessionSituation.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sessionSituation:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file,String menuId, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SessionSituation> list = ei.getDataList(SessionSituation.class);
			for (SessionSituation sessionSituation : list){
				try{
					sessionSituationService.save(sessionSituation);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条系统会话记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统会话记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统会话失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sessionSituation/?menuId="+menuId;
    }
	
	/**
	 * 下载导入系统会话数据模板
	 */
	@RequiresPermissions("sys:sessionSituation:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统会话数据导入模板.xlsx";
    		List<SessionSituation> list = Lists.newArrayList(); 
    		new ExportExcel("系统会话数据", SessionSituation.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sessionSituation/?repage";
    }
	
	
	

}