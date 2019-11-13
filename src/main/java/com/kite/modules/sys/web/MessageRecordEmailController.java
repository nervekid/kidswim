/**
 * KITE
 */
package com.kite.modules.sys.web;

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
import com.kite.modules.sys.entity.MessageRecordEmail;
import com.kite.modules.sys.service.MessageRecordEmailService;

/**
 * 邮件发送情况记录Controller
 * @author wusida
 * @version 2019-10-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/messageRecordEmail")
public class MessageRecordEmailController extends BaseController implements BasicVerification {

	@Autowired
	private MessageRecordEmailService messageRecordEmailService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public MessageRecordEmail get(@RequestParam(required=false) String id) {
		MessageRecordEmail entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageRecordEmailService.get(id);
		}
		if (entity == null){
			entity = new MessageRecordEmail();
		}
		return entity;
	}

	/**
	 * 邮件发送情况记录列表页面
	 */
	@RequiresPermissions("sys:messageRecordEmail:list")
	@RequestMapping(value = {"list", ""})
	public String list(MessageRecordEmail messageRecordEmail, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageRecordEmail> page = messageRecordEmailService.findPage(new Page<MessageRecordEmail>(request, response), messageRecordEmail);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/messageRecordEmailList";
	}


	/**
	 * 查看，增加，编辑邮件发送情况记录表单页面
	 */
	@RequiresPermissions(value={"sys:messageRecordEmail:view","sys:messageRecordEmail:add","sys:messageRecordEmail:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(MessageRecordEmail messageRecordEmail, Model model) {
		model.addAttribute("messageRecordEmail", messageRecordEmail);
		if(messageRecordEmail.getId()==null){
			// messageRecordEmail.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/messageRecordEmailForm";
	}

	/**
	 * 查看打印邮件发送情况记录表单页面
	 */
	@RequiresPermissions(value={"sys:messageRecordEmail:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(MessageRecordEmail messageRecordEmail, Model model) {
		model.addAttribute("messageRecordEmail", messageRecordEmail);
		return "modules/sys/messageRecordEmailView";
	}

	/**
	 * 保存邮件发送情况记录
	 */
	@RequiresPermissions(value={"sys:messageRecordEmail:add","sys:messageRecordEmail:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(MessageRecordEmail messageRecordEmail, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, messageRecordEmail)){
			return form(messageRecordEmail, model);
		}
		if(!messageRecordEmail.getIsNewRecord()){//编辑表单保存
			MessageRecordEmail t = messageRecordEmailService.get(messageRecordEmail.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(messageRecordEmail, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			messageRecordEmailService.save(t);//保存
		}else{//新增表单保存
			messageRecordEmailService.save(messageRecordEmail);//保存
		}
		addMessage(redirectAttributes, "保存邮件发送情况记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecordEmail/?menuId="+messageRecordEmail.getMenuId();
	}

	/**
	 * 删除邮件发送情况记录
	 */
	@RequiresPermissions("sys:messageRecordEmail:del")
	@RequestMapping(value = "delete")
	public String delete(MessageRecordEmail messageRecordEmail, RedirectAttributes redirectAttributes) {
		messageRecordEmailService.delete(messageRecordEmail);
		addMessage(redirectAttributes, "删除邮件发送情况记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecordEmail/?repage";
	}

	/**
	 * 批量删除邮件发送情况记录
	 */
	@RequiresPermissions("sys:messageRecordEmail:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			messageRecordEmailService.delete(messageRecordEmailService.get(id));
		}
		addMessage(redirectAttributes, "删除邮件发送情况记录成功");
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecordEmail/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:messageRecordEmail:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MessageRecordEmail messageRecordEmail, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮件发送情况记录"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MessageRecordEmail> page = messageRecordEmailService.findPage(new Page<MessageRecordEmail>(request, response, -1), messageRecordEmail);
    		new ExportExcel("邮件发送情况记录", MessageRecordEmail.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出邮件发送情况记录记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecordEmail/?menuId="+messageRecordEmail.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:messageRecordEmail:import")
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
				ei.write(response, "邮件发送情况记录列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<MessageRecordEmail> list = ei.getDataList(MessageRecordEmail.class);
				for (MessageRecordEmail messageRecordEmail : list){
					try{
						messageRecordEmailService.save(messageRecordEmail);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条邮件发送情况记录记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条邮件发送情况记录记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入邮件发送情况记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecordEmail/?menuId="+menuId;
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
	 * 下载导入邮件发送情况记录数据模板
	 */
	@RequiresPermissions("sys:messageRecordEmail:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "邮件发送情况记录数据导入模板.xlsx";
    		List<MessageRecordEmail> list = Lists.newArrayList();
    		new ExportExcel("邮件发送情况记录数据", MessageRecordEmail.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecordEmail/?repage";
    }




}