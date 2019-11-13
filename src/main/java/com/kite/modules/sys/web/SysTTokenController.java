/**
 * KITE
 */
package com.kite.modules.sys.web;

import com.google.common.collect.Lists;
import com.kite.common.config.Global;
import com.kite.common.json.AjaxJson;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.sys.entity.SysTToken;
import com.kite.modules.sys.service.SysTTokenService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * token配置信息Controller
 * @author cxh
 * @version 2019-01-24
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysTToken")
public class SysTTokenController extends BaseController implements BasicVerification {

	@Autowired
	private SysTTokenService sysTTokenService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysTToken get(@RequestParam(required=false) String id) {
		SysTToken entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysTTokenService.get(id);
		}
		if (entity == null){
			entity = new SysTToken();
		}
		return entity;
	}

	/**
	 * token配置信息列表页面
	 */
	@RequiresPermissions("sys:sysTToken:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysTToken sysTToken, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysTToken> page = sysTTokenService.findPage(new Page<SysTToken>(request, response), sysTToken);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/sys/sysTTokenList";
	}


	/**
	 * 查看，增加，编辑token配置信息表单页面
	 */
	@RequiresPermissions(value={"sys:sysTToken:view","sys:sysTToken:add","sys:sysTToken:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysTToken sysTToken, Model model) {
		model.addAttribute("sysTToken", sysTToken);
		if(sysTToken.getId()==null){
			// sysTToken.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysTTokenForm";
	}

	/**
	 * 查看打印token配置信息表单页面
	 */
	@RequiresPermissions(value={"sys:sysTToken:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysTToken sysTToken, Model model) {
		model.addAttribute("sysTToken", sysTToken);
		return "modules/sys/sysTTokenView";
	}


	@RequestMapping(value = "checkPidAndScretAndWorspaceIdExist")
	@ResponseBody
	public AjaxJson checkDeptAndUserExist(SysTToken sysTToken  , HttpServletResponse response, RedirectAttributes redirectAttributes) {
		AjaxJson json = null ;
		try {
			json = new AjaxJson();
			boolean flag = sysTTokenService.checkPidAndScretAndWorspaceIdExist(sysTToken);
			if (flag) {
				json.setMsg("已经存在该Pid , Scret 和 WorSpaceID 的对应关系！");
				json.setSuccess(true);
			}else{
				json.setSuccess(false);
			}
		} catch (Exception e) {
			addMessage(redirectAttributes, "Pid , Secret 和 WorSpaceID 的对应关系查询失败！失败信息："+e.getMessage());
			json.setSuccess(true);
			e.printStackTrace();
		}
		return json;
	}


	/**
	 * 保存token配置信息
	 */
	@RequiresPermissions(value={"sys:sysTToken:add","sys:sysTToken:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysTToken sysTToken, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysTToken)){
			return form(sysTToken, model);
		}
		if(!sysTToken.getIsNewRecord()){//编辑表单保存
			SysTToken t = sysTTokenService.get(sysTToken.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysTToken, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysTTokenService.save(t);//保存
		}else{//新增表单保存
			sysTTokenService.save(sysTToken);//保存
		}
		addMessage(redirectAttributes, "保存token配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTToken/?menuId="+sysTToken.getMenuId();
	}

	/**
	 * 删除token配置信息
	 */
	@RequiresPermissions("sys:sysTToken:del")
	@RequestMapping(value = "delete")
	public String delete(SysTToken sysTToken, RedirectAttributes redirectAttributes) {
		sysTTokenService.delete(sysTToken);
		addMessage(redirectAttributes, "删除token配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTToken/?repage";
	}

	/**
	 * 批量删除token配置信息
	 */
	@RequiresPermissions("sys:sysTToken:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysTTokenService.delete(sysTTokenService.get(id));
		}
		addMessage(redirectAttributes, "删除token配置信息成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysTToken/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysTToken:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysTToken sysTToken, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "token配置信息"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysTToken> page = sysTTokenService.findPage(new Page<SysTToken>(request, response, -1), sysTToken);
    		new ExportExcel("token配置信息", SysTToken.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出token配置信息记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTToken/?menuId="+sysTToken.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysTToken:import")
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
				ei.write(response, "token配置信息列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysTToken> list = ei.getDataList(SysTToken.class);
				for (SysTToken sysTToken : list){
					try{
						sysTTokenService.save(sysTToken);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条token配置信息记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条token配置信息记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入token配置信息失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTToken/?menuId="+menuId;
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
	 * 下载导入token配置信息数据模板
	 */
	@RequiresPermissions("sys:sysTToken:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "token配置信息数据导入模板.xlsx";
    		List<SysTToken> list = Lists.newArrayList();
    		new ExportExcel("token配置信息数据", SysTToken.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysTToken/?repage";
    }




}