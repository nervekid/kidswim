/**
 * KITE
 */
package com.kite.modules.att.web;

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
import com.google.common.collect.Maps;
import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.DateUtils;
import com.kite.common.utils.MyBeanUtils;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.ExportExcel;
import com.kite.common.utils.excel.ImportExcel;
import com.kite.common.utils.verification.BasicVerification;
import com.kite.common.web.BaseController;
import com.kite.modules.att.entity.SysBaseCoach;
import com.kite.modules.att.entity.SysBaseCoachImport;
import com.kite.modules.att.entity.SysCertificatesCoach;
import com.kite.modules.att.service.SysBaseCoachService;
import com.kite.modules.att.service.SysCertificatesCoachService;
import com.kite.modules.sys.service.SysUserCollectionMenuService;
import com.kite.modules.sys.service.SystemService;

/**
 * 教練員Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/sysBaseCoach")
public class SysBaseCoachController extends BaseController implements BasicVerification {

	@Autowired
	private SysBaseCoachService sysBaseCoachService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;
	@Autowired
	private SysCertificatesCoachService sysCertificatesCoachService;
	@Autowired
	private SystemService systemService;

	/*** 是否導入錯誤提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysBaseCoach get(@RequestParam(required=false) String id) {
		SysBaseCoach entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysBaseCoachService.get(id);
		}
		if (entity == null){
			entity = new SysBaseCoach();
		}
		return entity;
	}

	/**
	 * 教練員列表頁面
	 */
	@RequiresPermissions("att:sysBaseCoach:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysBaseCoach sysBaseCoach, HttpServletRequest request, HttpServletResponse response, Model model) {
		if (sysBaseCoach.getEntryYear() != null && !sysBaseCoach.getEntryYear().equals("")) {
			String yearMonth = sysBaseCoach.getEntryYear();
			yearMonth = yearMonth.replace("-", "");
			sysBaseCoach.setEntryYear(yearMonth);
		}
		Page<SysBaseCoach> page = sysBaseCoachService.findPage(new Page<SysBaseCoach>(request, response), sysBaseCoach);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/sysBaseCoachList";
	}

	/**
	 * 查看，增加，編輯教練員表單頁面
	 */
	@RequiresPermissions(value={"att:sysBaseCoach:view","att:sysBaseCoach:add","att:sysBaseCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysBaseCoach sysBaseCoach, Model model) {
		List<SysCertificatesCoach> sysCertificatesCoachList = this.sysCertificatesCoachService.findSysCertificatesCoachListByCoachId(sysBaseCoach.getId());
		sysBaseCoach.setSysCertificatesCoachList(sysCertificatesCoachList);
		model.addAttribute("sysBaseCoach", sysBaseCoach);
		model.addAttribute("sysCertificatesCoachList", sysCertificatesCoachList);

		if(sysBaseCoach.getId()==null){
			// sysBaseCoach.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//設置編碼
		}
		return "modules/att/sysBaseCoachForm";
	}

	/**
	 * 查看打印教練員表單頁面
	 */
	@RequiresPermissions(value={"att:sysBaseCoach:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysBaseCoach sysBaseCoach, Model model) {
		List<SysCertificatesCoach> sysCertificatesCoachList = this.sysCertificatesCoachService.findSysCertificatesCoachListByCoachId(sysBaseCoach.getId());
		sysBaseCoach.setSysCertificatesCoachList(sysCertificatesCoachList);
		model.addAttribute("sysBaseCoach", sysBaseCoach);
		model.addAttribute("sysCertificatesCoachList", sysCertificatesCoachList);
		return "modules/att/sysBaseCoachView";
	}

	/**
	 * 保存教練員
	 */
	@RequiresPermissions(value={"att:sysBaseCoach:add","att:sysBaseCoach:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysBaseCoach sysBaseCoach, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysBaseCoach)){
			return form(sysBaseCoach, model);
		}
		if(!sysBaseCoach.getIsNewRecord()){//編輯表單保存
			SysBaseCoach t = sysBaseCoachService.get(sysBaseCoach.getId());//從數據庫取出記錄的值
			MyBeanUtils.copyBeanNotNull2Bean(sysBaseCoach, t);//將編輯表單中的非NULL值覆蓋數據庫記錄中的值
			//1.年月修改
			if (sysBaseCoach.getEntryYear() != null && !sysBaseCoach.getEntryYear().equals("")) {
				String yearMonth = sysBaseCoach.getEntryYear();
				yearMonth = yearMonth.replace("-", "");
				t.setEntryYear(yearMonth);
			}
			sysBaseCoachService.save(t);//保存
		}else{//新增表單保存
			//1.編碼給定
			int countLastNum = this.sysBaseCoachService.findExitSysBaseCoachNum();
			int nextCodeNum = countLastNum ++;
			String code = "C" + com.kite.common.utils.date.DateUtils.transformThousandBitNumString(nextCodeNum);
			sysBaseCoach.setCode(code);
			//2.年月修改
			if (sysBaseCoach.getEntryYear() != null && !sysBaseCoach.getEntryYear().equals("")) {
				String yearMonth = sysBaseCoach.getEntryYear();
				yearMonth = yearMonth.replace("-", "");
				sysBaseCoach.setEntryYear(yearMonth);
			}
			sysBaseCoachService.save(sysBaseCoach);//保存
		}
		addMessage(redirectAttributes, "保存教練員成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?menuId="+sysBaseCoach.getMenuId();
	}

	/**
	 * 刪除教練員
	 */
	@RequiresPermissions("att:sysBaseCoach:del")
	@RequestMapping(value = "delete")
	public String delete(SysBaseCoach sysBaseCoach, RedirectAttributes redirectAttributes) {
		sysBaseCoachService.delete(sysBaseCoach);
		addMessage(redirectAttributes, "刪除教練員成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?repage";
	}

	/**
	 * 批量刪除教練員
	 */
	@RequiresPermissions("att:sysBaseCoach:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysBaseCoachService.delete(sysBaseCoachService.get(id));
		}
		addMessage(redirectAttributes, "刪除教練員成功");
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?repage";
	}

	/**
	 * 導出excel文件
	 */
	@RequiresPermissions("att:sysBaseCoach:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysBaseCoach sysBaseCoach, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教練員"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysBaseCoach> page = sysBaseCoachService.findPage(new Page<SysBaseCoach>(request, response, -1), sysBaseCoach);
    		new ExportExcel("教練員", SysBaseCoach.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導出教練員記錄失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?menuId="+sysBaseCoach.getMenuId();
    }

	/**
	 * 導入Excel數據
	 */
	@RequiresPermissions("att:sysBaseCoach:import")
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
				ei.write(response, "教練員列表導入失敗結果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysBaseCoachImport> list = ei.getDataList(SysBaseCoachImport.class);
				for (SysBaseCoachImport sBaseCoachImport : list){
					try{
						SysBaseCoach sysBaseCoach = new SysBaseCoach();
						//1.編碼給定
						int countLastNum = this.sysBaseCoachService.findExitSysBaseCoachNum();
						int nextCodeNum = countLastNum ++;
						String code = "C" + com.kite.common.utils.date.DateUtils.transformThousandBitNumString(nextCodeNum);
						sysBaseCoach.setCode(code);
						//2.年月修改
						if (sysBaseCoach.getEntryYear() != null && !sysBaseCoach.getEntryYear().equals("")) {
							String yearMonth = sysBaseCoach.getEntryYear();
							yearMonth = yearMonth.replace("-", "");
							sysBaseCoach.setEntryYear(yearMonth);
						}
						//字典轉值
						String sexValue = this.systemService.findDictValueByTypeAndLabel("sex_flag", sBaseCoachImport.getSex());
						String contractValue = this.systemService.findDictValueByTypeAndLabel("yes_no", sBaseCoachImport.getContractFlag());
						sysBaseCoach.setNameCn(sBaseCoachImport.getNameCn());
						sysBaseCoach.setNameEn(sBaseCoachImport.getNameEn());
						sysBaseCoach.setSex(sexValue);
						sysBaseCoach.setPhone(sBaseCoachImport.getPhone());
						sysBaseCoach.setIdNo(sBaseCoachImport.getIdNo());
						sysBaseCoach.setEmail(sBaseCoachImport.getEmail());
						sysBaseCoach.setAddress(sBaseCoachImport.getAddress());
						sysBaseCoach.setEducationLevel(sBaseCoachImport.getEducationLevel());
						sysBaseCoach.setEntryPosition(sBaseCoachImport.getEntryPosition());
						sysBaseCoach.setEntryHourWage(sBaseCoachImport.getEntryHourWage());
						sysBaseCoach.setPresentPosition(sBaseCoachImport.getPresentPosition());
						sysBaseCoach.setPresentHourWage(sBaseCoachImport.getPresentHourWage());
						sysBaseCoach.setIndustryExperience(sBaseCoachImport.getIndustryExperience());
						sysBaseCoach.setContractFlag(contractValue);
						sysBaseCoachService.save(sysBaseCoach);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失敗 "+failureNum+" 條教練員記錄。");
				}
				addMessage(redirectAttributes, "已成功導入 "+successNum+" 條教練員記錄"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "導入教練員失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?menuId="+menuId;
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
	 * 下載導入教練員數據模板
	 */
	@RequiresPermissions("att:sysBaseCoach:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "教練員數據導入模板.xlsx";
    		List<SysBaseCoachImport> list = Lists.newArrayList();
    		new ExportExcel("教練員數據", SysBaseCoachImport.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "導入模板下載失敗！失敗信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/sysBaseCoach/?repage";
    }


	/**
	 * 獲取教練員列表
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		SysBaseCoach sysBaseCoach = new SysBaseCoach();
		sysBaseCoach.setDelFlag("0");
		List<SysBaseCoach> list = this.sysBaseCoachService.findList(sysBaseCoach);
		for (int i=0; i<list.size(); i++){
			SysBaseCoach e = list.get(i);
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("name", e.getNameCn() + e.getNameEn());
			mapList.add(map);
		}
		return mapList;
	}
}