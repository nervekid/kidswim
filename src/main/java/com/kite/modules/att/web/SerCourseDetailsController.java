package com.kite.modules.att.web;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
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
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.att.entity.SerCourse;
import com.kite.modules.att.entity.SerCourseDetails;
import com.kite.modules.att.service.SerCourseDetailsService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;

/**
 * 課程明細Controller
 * @author lyb
 * @version 2019-12-08
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serCourseDetails")
public class SerCourseDetailsController extends BaseController implements BasicVerification {

	@Autowired
	private SerCourseDetailsService serCourseDetailsService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否導入錯誤提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerCourseDetails get(@RequestParam(required=false) String id) {
		SerCourseDetails entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serCourseDetailsService.get(id);
		}
		if (entity == null){
			entity = new SerCourseDetails();
		}
		return entity;
	}

	/**
	 * 課程明細列表頁面
	 * @throws ParseException
	 */
	@RequiresPermissions("att:serCourseDetails:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerCourseDetails serCourseDetails, HttpServletRequest request, HttpServletResponse response, Model model) throws ParseException {
		Page<SerCourseDetails> page = this.serCourseDetailsService.findPage(new Page<SerCourseDetails>(request, response), serCourseDetails);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serCourseDetailsList";
	}


	/**
	 * 查看，增加，編輯課程明細表單頁面
	 */
	@RequiresPermissions(value={"att:serCourseDetails:view","att:serCourseDetails:add","att:serCourseDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerCourseDetails serCourseDetails, Model model) {
		model.addAttribute("serCourseDetails", serCourseDetails);
		if(serCourseDetails.getId()==null){
		}
		return "modules/att/serCourseDetailsForm";
	}

	/**
	 * 查看打印課程明細表單頁面
	 */
	@RequiresPermissions(value={"att:serCourseDetails:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerCourseDetails serCourseDetails, Model model) {
		model.addAttribute("serCourseDetails", serCourseDetails);
		return "modules/att/serCourseDetailsView";
	}

	/**
	 * 保存課程
	 */
	@RequiresPermissions(value={"att:serCourseDetails:add","att:serCourseDetails:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerCourseDetails serCourseDetails, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serCourseDetails)){
			return form(serCourseDetails, model);
		}
		if(!serCourseDetails.getIsNewRecord()){//編輯表單保存
			SerCourseDetails t = this.serCourseDetailsService.get(serCourseDetails.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(serCourseDetails, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			this.serCourseDetailsService.save(t);//保存
		}else{//新增表單保存
			serCourseDetailsService.save(serCourseDetails);//保存
		}
		addMessage(redirectAttributes, "保存課程明細成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourseDetails/?menuId="+serCourseDetails.getMenuId();
	}

	/**
	 * 刪除課程
	 */
	@RequiresPermissions("att:serCourseDetails:del")
	@RequestMapping(value = "delete")
	public String delete(SerCourseDetails serCourseDetails, RedirectAttributes redirectAttributes) {
		this.serCourseDetailsService.delete(serCourseDetails);
		addMessage(redirectAttributes, "刪除課程明細成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourseDetails/?repage";
	}

	/**
	 * 批量刪除課程明細
	 */
	@RequiresPermissions("att:serCourseDetails:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			this.serCourseDetailsService.delete(this.serCourseDetailsService.get(id));
		}
		addMessage(redirectAttributes, "刪除課程明細成功");
		return "redirect:"+Global.getAdminPath()+"/att/serCourseDetails/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:serCourseDetails:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerCourseDetails serCourseDetails, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "課程明細"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerCourseDetails> page = this.serCourseDetailsService.findPage(new Page<SerCourseDetails>(request, response, -1), serCourseDetails);
    		new ExportExcel("課程明細", SerCourseDetails.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出課程明細記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourseDetails/?menuId="+serCourseDetails.getMenuId();
    }

	/**
	 * 導入Excel數據

	 */
	@RequiresPermissions("att:serCourse:import")
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
				ei.write(response, "課程明細列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerCourseDetails> list = ei.getDataList(SerCourseDetails.class);
				for (SerCourseDetails serCourseDetails : list){
					try{
						this.serCourseDetailsService.save(serCourseDetails);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失敗 "+failureNum+" 條課程明細記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條課程明細記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入課程失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourseDetails/?menuId="+menuId;
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
	 * 下載導入課程數據模板
	 */
	@RequiresPermissions("att:serCourse:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "課程數據導入模板.xlsx";
    		List<SerCourse> list = Lists.newArrayList();
    		new ExportExcel("課程數據", SerCourse.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serCourse/?repage";
    }
}
