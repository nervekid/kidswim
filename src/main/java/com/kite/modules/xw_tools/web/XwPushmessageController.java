/**
 * MouTai
 */
package com.kite.modules.xw_tools.web;

import java.util.List;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.web.BaseController;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.MessageUtils;
import com.kite.modules.sys.utils.UserUtils;
import com.kite.modules.xw_tools.entity.XwPushmessage;
import com.kite.modules.xw_tools.service.XwPushmessageService;

/**
 * 消息推送Controller
 * @author czh
 * @version 2017-12-11
 */
@Controller
@RequestMapping(value = "${adminPath}/xw_tools/xwPushmessage")
public class XwPushmessageController extends BaseController {

	@Autowired
	private XwPushmessageService xwPushmessageService;
	
	@ModelAttribute
	public XwPushmessage get(@RequestParam(required=false) String id) {
		XwPushmessage entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = xwPushmessageService.get(id);
		}
		if (entity == null){
			entity = new XwPushmessage();
		}
		return entity;
	}
	

	/**
	 * 消息推送列表页面
	 */
	@RequestMapping(value = {"list", ""})
	public String list(XwPushmessage xwPushmessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<XwPushmessage> page = xwPushmessageService.find(new Page<XwPushmessage>(request, response), xwPushmessage);
		model.addAttribute("page", page);
		return "modules/xw_tools/xwPushmessageList";
	}

	/**
	 * 查看，增加，编辑消息推送表单页面
	 */
	@RequestMapping(value = "form")
	public String form(XwPushmessage xwPushmessage, Model model) {
		if (StringUtils.isNotBlank(xwPushmessage.getId())){
			xwPushmessage = xwPushmessageService.viewMessage(xwPushmessage);
			model.addAttribute("xwPushmessage", xwPushmessage);
		}
		return "modules/xw_tools/xwPushmessageForm";
	}

	/**
	 * 查看打印消息推送表单页面
	 */
	@RequestMapping(value = "view")
	public String view(XwPushmessage xwPushmessage, Model model) {
		if (StringUtils.isNotBlank(xwPushmessage.getId())){
			xwPushmessage = xwPushmessageService.viewMessage(xwPushmessage);
			model.addAttribute("xwPushmessage", xwPushmessage);
		}
		return "modules/xw_tools/xwPushmessageView";
	}

	/**
	 * 保存消息推送
	 */
	@RequestMapping(value = "save")
	public String save(XwPushmessage xwPushmessage, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, xwPushmessage)){
			return form(xwPushmessage, model);
		}
		if(!xwPushmessage.getIsNewRecord()){//编辑表单保存
			XwPushmessage t = xwPushmessageService.get(xwPushmessage.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(xwPushmessage, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			xwPushmessageService.save(t);//保存
		}else{//新增表单保存
			xwPushmessageService.save(xwPushmessage);//保存
		}
		addMessage(redirectAttributes, "保存消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/?repage";
	}
	
	/**
	 * 删除消息推送
	 */
	@RequestMapping(value = "delete")
	public String delete(XwPushmessage xwPushmessage, RedirectAttributes redirectAttributes) {
		xwPushmessageService.delete(xwPushmessage);
		addMessage(redirectAttributes, "删除消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/?repage";
	}
	
	/**
	 * 批量删除消息推送
	 */
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			xwPushmessageService.delete(xwPushmessageService.get(id));
		}
		addMessage(redirectAttributes, "删除消息推送成功");
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/?repage";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("xw_tools:xwPushmessage:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(XwPushmessage xwPushmessage, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "消息推送"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<XwPushmessage> page = xwPushmessageService.findPage(new Page<XwPushmessage>(request, response, -1), xwPushmessage);
    		new ExportExcel("消息推送", XwPushmessage.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出消息推送记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/?repage";
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("xw_tools:xwPushmessage:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<XwPushmessage> list = ei.getDataList(XwPushmessage.class);
			for (XwPushmessage xwPushmessage : list){
				try{
					xwPushmessageService.save(xwPushmessage);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条消息推送记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条消息推送记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入消息推送失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/?repage";
    }
	
	/**
	 * 下载导入消息推送数据模板
	 */
	@RequiresPermissions("xw_tools:xwPushmessage:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "消息推送数据导入模板.xlsx";
    		List<XwPushmessage> list = Lists.newArrayList(); 
    		new ExportExcel("消息推送数据", XwPushmessage.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/?repage";
    }


	/**
	 * 我的通知列表
	 */
	@RequestMapping(value = "self")
	public String selfList(XwPushmessage xwPushmessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		xwPushmessage.setSelf(true);
		Page<XwPushmessage> page = xwPushmessageService.find(new Page<XwPushmessage>(request, response), xwPushmessage);
		model.addAttribute("page", page);
		model.addAttribute("self",true);
		return "modules/xw_tools/xwPushmessageList";
	}

	/**
	 * 消息推送列表页面
	 */
	@RequestMapping(value = {"oneList"})
	public String oneList(XwPushmessage xwPushmessage, HttpServletRequest request, HttpServletResponse response, Model model) {
		xwPushmessage.setSelf(true);
		Page<XwPushmessage> page = xwPushmessageService.findOne(new Page<XwPushmessage>(request, response), xwPushmessage);
		model.addAttribute("page", page);
		model.addAttribute("self",true);
		return "modules/xw_tools/xwPushmessageList";
	}


	/**
	 * 批量删除消息推送
	 */
	@RequestMapping(value = "consultMessage")
	public String consultMessage(String ids, RedirectAttributes redirectAttributes) {
		try {
			xwPushmessageService.consultMessage(ids);
			addMessage(redirectAttributes, "查阅消息成功！");
		} catch (Exception e) {
			addMessage(redirectAttributes, "查阅消息失败！");
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/xw_tools/xwPushmessage/self?repage";
	}

}