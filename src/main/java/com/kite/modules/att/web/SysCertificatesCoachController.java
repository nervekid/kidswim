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
import com.kite.modules.att.entity.SysCertificatesCoach;
import com.kite.modules.att.service.SysCertificatesCoachService;

/**
 * 教練員資格Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/sysCertificatesCoach")
public class SysCertificatesCoachController extends BaseController implements BasicVerification {

	@Autowired
	private SysCertificatesCoachService sysCertificatesCoachService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否導入錯誤提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysCertificatesCoach get(@RequestParam(required=false) String id) {
		SysCertificatesCoach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysCertificatesCoachService.get(id);
		}
		if (entity == null){
			entity = new SysCertificatesCoach();
		}
		return entity;
	}

	/**
	 * 教練員資格列表頁面
	 */
	@RequiresPermissions("att:sysCertificatesCoach:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysCertificatesCoach sysCertificatesCoach, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysCertificatesCoach> page = sysCertificatesCoachService.findPage(new Page<SysCertificatesCoach>(request, response), sysCertificatesCoach);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/sysCertificatesCoachList";
	}


	/**
	 * 查看，增加，編輯教練員資格表單頁面
	 */
	@RequiresPermissions(value={"att:sysCertificatesCoach:view","att:sysCertificatesCoach:add","att:sysCertificatesCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysCertificatesCoach sysCertificatesCoach, Model model) {
		model.addAttribute("sysCertificatesCoach", sysCertificatesCoach);
		if(sysCertificatesCoach.getId()==null){
			// sysCertificatesCoach.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//設置編碼
		}
		return "modules/att/sysCertificatesCoachForm";
	}

	/**
	 * 查看打印教練員資格表單頁面
	 */
	@RequiresPermissions(value={"att:sysCertificatesCoach:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysCertificatesCoach sysCertificatesCoach, Model model) {
		model.addAttribute("sysCertificatesCoach", sysCertificatesCoach);
		return "modules/att/sysCertificatesCoachView";
	}

	/**
	 * 保存教練員資格
	 */
	@RequiresPermissions(value={"att:sysCertificatesCoach:add","att:sysCertificatesCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysCertificatesCoach sysCertificatesCoach, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysCertificatesCoach)){
			return form(sysCertificatesCoach, model);
		}
		if(!sysCertificatesCoach.getIsNewRecord()){//編輯表單保存
			SysCertificatesCoach t = sysCertificatesCoachService.get(sysCertificatesCoach.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(sysCertificatesCoach, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			sysCertificatesCoachService.save(t);//保存
		}else{//新增表單保存
			sysCertificatesCoachService.save(sysCertificatesCoach);//保存
		}
		addMessage(redirectAttributes, "保存教練員資格成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?menuId="+sysCertificatesCoach.getMenuId();
	}

	/**
	 * 刪除教練員資格
	 */
	@RequiresPermissions("att:sysCertificatesCoach:del")
	@RequestMapping(value = "delete")
	public String delete(SysCertificatesCoach sysCertificatesCoach, RedirectAttributes redirectAttributes) {
		sysCertificatesCoachService.delete(sysCertificatesCoach);
		addMessage(redirectAttributes, "刪除教練員資格成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?repage";
	}

	/**
	 * 批量刪除教練員資格
	 */
	@RequiresPermissions("att:sysCertificatesCoach:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysCertificatesCoachService.delete(sysCertificatesCoachService.get(id));
		}
		addMessage(redirectAttributes, "刪除教練員資格成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:sysCertificatesCoach:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysCertificatesCoach sysCertificatesCoach, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教練員資格"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysCertificatesCoach> page = sysCertificatesCoachService.findPage(new Page<SysCertificatesCoach>(request, response, -1), sysCertificatesCoach);
    		new ExportExcel("教練員資格", SysCertificatesCoach.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出教練員資格記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?menuId="+sysCertificatesCoach.getMenuId();
    }

	/**
	 * 導入Excel數據

	 */
	@RequiresPermissions("att:sysCertificatesCoach:import")
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
				ei.write(response, "教練員資格列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysCertificatesCoach> list = ei.getDataList(SysCertificatesCoach.class);
				for (SysCertificatesCoach sysCertificatesCoach : list){
					try{
						sysCertificatesCoachService.save(sysCertificatesCoach);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失敗 "+failureNum+" 條教練員資格記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條教練員資格記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入教練員資格失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?menuId="+menuId;
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
	 * 下載導入教練員資格數據模板
	 */
	@RequiresPermissions("att:sysCertificatesCoach:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教練員資格數據導入模板.xlsx";
    		List<SysCertificatesCoach> list = Lists.newArrayList();
    		new ExportExcel("教練員資格數據", SysCertificatesCoach.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysCertificatesCoach/?repage";
    }




}