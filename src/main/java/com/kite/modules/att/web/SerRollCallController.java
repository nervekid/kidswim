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
 * 點名Controller
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

	/*** 是否導入錯誤提示*/
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
	 * 點名列表頁面
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
	 * 查看，增加，編輯點名表單頁面
	 */
	@RequiresPermissions(value={"att:serRollCall:view","att:serRollCall:add","att:serRollCall:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerRollCall serRollCall, Model model) {
		model.addAttribute("serRollCall", serRollCall);
		if(serRollCall.getId()==null){
			// serRollCall.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//設置編碼
		}
		return "modules/att/serRollCallForm";
	}

	/**
	 * 查看打印點名表單頁面
	 */
	@RequiresPermissions(value={"att:serRollCall:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerRollCall serRollCall, Model model) {
		model.addAttribute("serRollCall", serRollCall);
		return "modules/att/serRollCallView";
	}

	/**
	 * 保存點名
	 */
	@RequiresPermissions(value={"att:serRollCall:add","att:serRollCall:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerRollCall serRollCall, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serRollCall)){
			return form(serRollCall, model);
		}
		if(!serRollCall.getIsNewRecord()){//編輯表單保存
			SerRollCall t = serRollCallService.get(serRollCall.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(serRollCall, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			serRollCallService.save(t);//保存
		}else{//新增表單保存
			serRollCallService.save(serRollCall);//保存
		}
		addMessage(redirectAttributes, "保存點名成功");
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?menuId="+serRollCall.getMenuId();
	}

	/**
	 * 刪除點名
	 */
	@RequiresPermissions("att:serRollCall:del")
	@RequestMapping(value = "delete")
	public String delete(SerRollCall serRollCall, RedirectAttributes redirectAttributes) {
		serRollCallService.delete(serRollCall);
		addMessage(redirectAttributes, "刪除點名成功");
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?repage";
	}

	/**
	 * 批量刪除點名
	 */
	@RequiresPermissions("att:serRollCall:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serRollCallService.delete(serRollCallService.get(id));
		}
		addMessage(redirectAttributes, "刪除點名成功");
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:serRollCall:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerRollCall serRollCall, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "點名"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerRollCall> page = serRollCallService.findPage(new Page<SerRollCall>(request, response, -1), serRollCall);
    		new ExportExcel("點名", SerRollCall.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出點名記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?menuId="+serRollCall.getMenuId();
    }

	/**
	 * 導入Excel數據

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
				ei.write(response, "點名列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
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
					failureMsg.insert(0, "，失敗 "+failureNum+" 條點名記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條點名記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入點名失敗！失敗信息："+e.getMessage());
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
	 * 下載導入點名數據模板
	 */
	@RequiresPermissions("att:serRollCall:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "點名數據導入模板.xlsx";
    		List<SerRollCall> list = Lists.newArrayList();
    		new ExportExcel("點名數據", SerRollCall.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serRollCall/?repage";
    }
}