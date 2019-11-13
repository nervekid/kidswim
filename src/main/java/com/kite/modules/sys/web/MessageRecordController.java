/**
 * KITE
 */
package com.kite.modules.sys.web;

import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.MessageRecord;
import com.kite.modules.sys.service.MessageRecordService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户接收短信情况记录Controller
 * @author zhangtao
 * @version 2019-05-13
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/messageRecord")
public class MessageRecordController extends BaseController implements BasicVerification {

	@Autowired
	private MessageRecordService messageRecordService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public MessageRecord get(@RequestParam(required=false) String id) {
		MessageRecord entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = messageRecordService.get(id);
		}
		if (entity == null){
			entity = new MessageRecord();
		}
		return entity;
	}

	/**
	 * 用户接收短信情况列表页面
	 */
	@RequiresPermissions("sys:messageRecord:list")
	@RequestMapping(value = {"list", ""})
	public String list(MessageRecord messageRecord, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<MessageRecord> page = messageRecordService.findPage(new Page<MessageRecord>(request, response), messageRecord);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/messageRecordList";
	}


	/**
	 * 导出excel文件
	 */
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(MessageRecord messageRecord, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "用户接收短信情况"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<MessageRecord> page = messageRecordService.findPage(new Page<MessageRecord>(request, response, -1), messageRecord);
    		new ExportExcel("用户接收短信情况", MessageRecord.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出用户接收短信情况记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/messageRecord/?menuId="+messageRecord.getMenuId();
    }
}