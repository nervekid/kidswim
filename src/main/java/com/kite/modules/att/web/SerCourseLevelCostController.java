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
import com.kite.modules.att.entity.SerCourseLevelCost;
import com.kite.modules.att.service.SerCourseLevelCostService;

/**
 * 課程等級對應收費Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serCourseLevelCost")
public class SerCourseLevelCostController extends BaseController implements BasicVerification {

	@Autowired
	private SerCourseLevelCostService serCourseLevelCostService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否導入錯誤提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerCourseLevelCost get(@RequestParam(required=false) String id) {
		SerCourseLevelCost entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serCourseLevelCostService.get(id);
		}
		if (entity == null){
			entity = new SerCourseLevelCost();
		}
		return entity;
	}

	/**
	 * 課程等級對應收費列表頁面
	 */
	@RequiresPermissions("att:serCourseLevelCost:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerCourseLevelCost serCourseLevelCost, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerCourseLevelCost> page = serCourseLevelCostService.findPage(new Page<SerCourseLevelCost>(request, response), serCourseLevelCost);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serCourseLevelCostList";
	}


	/**
	 * 查看，增加，編輯課程等級對應收費表單頁面
	 */
	@RequiresPermissions(value={"att:serCourseLevelCost:view","att:serCourseLevelCost:add","att:serCourseLevelCost:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerCourseLevelCost serCourseLevelCost, Model model) {
		model.addAttribute("serCourseLevelCost", serCourseLevelCost);
		if(serCourseLevelCost.getId()==null){
			// serCourseLevelCost.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//設置編碼
		}
		return "modules/att/serCourseLevelCostForm";
	}

	/**
	 * 查看打印課程等級對應收費表單頁面
	 */
	@RequiresPermissions(value={"att:serCourseLevelCost:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerCourseLevelCost serCourseLevelCost, Model model) {
		model.addAttribute("serCourseLevelCost", serCourseLevelCost);
		return "modules/att/serCourseLevelCostView";
	}

	/**
	 * 保存課程等級對應收費
	 */
	@RequiresPermissions(value={"att:serCourseLevelCost:add","att:serCourseLevelCost:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerCourseLevelCost serCourseLevelCost, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serCourseLevelCost)){
			return form(serCourseLevelCost, model);
		}
		if(!serCourseLevelCost.getIsNewRecord()){//編輯表單保存
			SerCourseLevelCost t = serCourseLevelCostService.get(serCourseLevelCost.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(serCourseLevelCost, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			serCourseLevelCostService.save(t);//保存
		}else{//新增表單保存
			serCourseLevelCostService.save(serCourseLevelCost);//保存
		}
		addMessage(redirectAttributes, "保存課程等級對應收費成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourseLevelCost/?menuId="+serCourseLevelCost.getMenuId();
	}

	/**
	 * 刪除課程等級對應收費
	 */
	@RequiresPermissions("att:serCourseLevelCost:del")
	@RequestMapping(value = "delete")
	public String delete(SerCourseLevelCost serCourseLevelCost, RedirectAttributes redirectAttributes) {
		serCourseLevelCostService.delete(serCourseLevelCost);
		addMessage(redirectAttributes, "刪除課程等級對應收費成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourseLevelCost/?repage";
	}

	/**
	 * 批量刪除課程等級對應收費
	 */
	@RequiresPermissions("att:serCourseLevelCost:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serCourseLevelCostService.delete(serCourseLevelCostService.get(id));
		}
		addMessage(redirectAttributes, "刪除課程等級對應收費成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourseLevelCost/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:serCourseLevelCost:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerCourseLevelCost serCourseLevelCost, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "課程等級對應收費"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerCourseLevelCost> page = serCourseLevelCostService.findPage(new Page<SerCourseLevelCost>(request, response, -1), serCourseLevelCost);
    		new ExportExcel("課程等級對應收費", SerCourseLevelCost.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出課程等級對應收費記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourseLevelCost/?menuId="+serCourseLevelCost.getMenuId();
    }

	/**
	 * 導入Excel數據

	 */
	@RequiresPermissions("att:serCourseLevelCost:import")
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
				ei.write(response, "課程等級對應收費列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerCourseLevelCost> list = ei.getDataList(SerCourseLevelCost.class);
				for (SerCourseLevelCost serCourseLevelCost : list){
					try{
						serCourseLevelCostService.save(serCourseLevelCost);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失敗 "+failureNum+" 條課程等級對應收費記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條課程等級對應收費記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入課程等級對應收費失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourseLevelCost/?menuId="+menuId;
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
	 * 下載導入課程等級對應收費數據模板
	 */
	@RequiresPermissions("att:serCourseLevelCost:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "課程等級對應收費數據導入模板.xlsx";
    		List<SerCourseLevelCost> list = Lists.newArrayList();
    		new ExportExcel("課程等級對應收費數據", SerCourseLevelCost.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourseLevelCost/?repage";
    }
}