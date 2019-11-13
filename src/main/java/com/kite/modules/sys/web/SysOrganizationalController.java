/**
 * KITE
 */
package com.kite.modules.sys.web;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import com.kite.modules.file.service.FileCatalogService;
import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.SysOrganizational;
import com.kite.modules.sys.service.OfficeService;
import com.kite.modules.sys.service.SysOrganizationalService;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 系统组织架构Controller
 * @author lyb
 * @version 2018-10-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysOrganizational")
public class SysOrganizationalController extends BaseController implements BasicVerification {

	@Autowired
	private SysOrganizationalService sysOrganizationalService;
	@Autowired
	private OfficeService officeService;
	@Autowired
	private SystemService systemService;
	@Autowired
	private FileFastdfsService fileFastdfsService;
	@Autowired
	private FileCatalogService fileCatalogService;

	private static Logger logger = LoggerFactory.getLogger(SysOrganizationalController.class);

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SysOrganizational get(@RequestParam(required=false) String id) {
		SysOrganizational entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysOrganizationalService.get(id);
		}
		if (entity == null){
			entity = new SysOrganizational();
		}
		entity.setCurrentUserId(UserUtils.getUser().getId());
		return entity;
	}

	/**
	 * 系统组织架构入口
	 */
	@RequiresPermissions("sys:sysOrganizational:index")
	@RequestMapping(value = {"index", ""})
	public String index(SysOrganizational sysOrganizational, HttpServletRequest request, HttpServletResponse response, Model model) {
		String orgTag = sysOrganizational.getOrganTag();
		logger.info("已经进入了系统组织架构页面, id = {}", orgTag);
		sysOrganizational.setOrganTag(orgTag);
		Page<SysOrganizational> page = sysOrganizationalService.findPage(new Page<SysOrganizational>(request, response), sysOrganizational);
		model.addAttribute("page", page);
		model.addAttribute("sysOrganizational", sysOrganizational);
		return "modules/sys/organizationIndex";
	}

	/**
	 * 系统组织架构列表页面
	 * @throws UnsupportedEncodingException
	 */
	@RequiresPermissions("sys:sysOrganizational:list")
	@RequestMapping(value = {"list"})
	public String list(@ModelAttribute SysOrganizational sysOrganizational, HttpServletRequest request,
			HttpServletResponse response, Model model, @RequestParam(required=false) String orgTag) throws UnsupportedEncodingException {
		if (sysOrganizational.getOffice() != null) {
			String officeName = URLDecoder.decode(sysOrganizational.getOffice().getName(), "utf-8");
			sysOrganizational.getOffice().setName(officeName);
		}
		if(StringUtils.isNotEmpty(orgTag)){
			sysOrganizational.setOrganTag(orgTag);
		}
		Page<SysOrganizational> page = sysOrganizationalService.findPage(new Page<SysOrganizational>(request, response), sysOrganizational);
		model.addAttribute("page", page);
		return "modules/sys/sysOrganizationalList";
	}

	/**
	 * 查看，增加，编辑系统组织架构表单页面
	 */
	@RequiresPermissions(value={"sys:sysOrganizational:view","sys:sysOrganizational:add","sys:sysOrganizational:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysOrganizational sysOrganizational, Model model, @RequestParam(required = false) String orgTag,
			@RequestParam(required = false) String oId, @RequestParam(required = false) String editFlag) {
		if (sysOrganizational.getOrganTag() == null || sysOrganizational.getOrganTag().equals("")) {
			sysOrganizational.setOrganTag(orgTag);
		}
		if (oId != null && !oId.equals("")) {
			Office office = this.officeService.get(oId);
			sysOrganizational.setOffice(office);
			if (office != null) {
				Office officeParent = this.officeService.get(office.getParentId());
				sysOrganizational.setCompanyId(officeParent.getId());
				sysOrganizational.setCompanyName(officeParent.getName());
			}
			Office officeOne = UserUtils.getDepartment(oId);
			if (officeOne != null) {
				sysOrganizational.setOfficeOne(officeOne);

			}
		}
		model.addAttribute("sysOrganizational", sysOrganizational);
		model.addAttribute("editFlag", editFlag);
		if(sysOrganizational.getId()==null){
			// sysOrganizational.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/sys/sysOrganizationalForm";
	}


	/**
	 * 上传证件相片
	 */
	@RequiresPermissions(value={"sys:ehrSysOrganization:edit"})
	@RequestMapping(value = "imageEdit")
	public String imageEdit(Model model, @RequestParam(required = true) String userId) {
		model.addAttribute("userId", userId);
		return "modules/sys/eHRPersonInfoFormImg";
	}

	/**
	 * 查看打印系统组织架构表单页面
	 */
	@RequiresPermissions(value={"sys:sysOrganizational:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SysOrganizational sysOrganizational, Model model) {
		model.addAttribute("sysOrganizational", sysOrganizational);
		return "modules/sys/sysOrganizationalView";
	}

	/**
	 * 保存系统组织架构
	 */
	@RequiresPermissions(value={"sys:sysOrganizational:add","sys:sysOrganizational:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysOrganizational sysOrganizational, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysOrganizational)){
			return form(sysOrganizational, model, sysOrganizational.getOrganTag(), sysOrganizational.getOffice().getId(), null);
		}
		try {
			if(!sysOrganizational.getIsNewRecord()){//编辑表单保存
                SysOrganizational t = sysOrganizationalService.get(sysOrganizational.getId());//从数据库取出记录的值
                MyBeanUtils.copyBeanNotNull2Bean(sysOrganizational, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
                sysOrganizationalService.save(t);//保存
            }else{//新增表单保存
                sysOrganizationalService.save(sysOrganizational);//保存
            }
			addMessage(redirectAttributes, "保存系统组织架构成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, e.getMessage());
			e.printStackTrace();
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizational/index?organTag=" + sysOrganizational.getOrganTag();
	}

	/**
	 * 删除系统组织架构
	 */
	@RequiresPermissions("sys:sysOrganizational:del")
	@RequestMapping(value = "delete")
	public String delete(SysOrganizational sysOrganizational, RedirectAttributes redirectAttributes) {
		try {
			sysOrganizationalService.delete(sysOrganizational);
			addMessage(redirectAttributes, "删除系统组织架构成功");
		} catch (Exception e) {
			addMessage(redirectAttributes, e.getMessage());
			e.printStackTrace();
		}
		return "modules/sys/sysOrganizationalList/list";
	}

	/**
	 * 批量删除系统组织架构
	 */
	@RequiresPermissions("sys:sysOrganizational:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		String organTag = "";
		String officeId="";
		String officeName = "";
		for(String id : idArray){
			SysOrganizational sysOrganizational = sysOrganizationalService.get(id);
			organTag = sysOrganizational.getOrganTag();
			if(sysOrganizational.getOffice()!=null){
				officeId = sysOrganizational.getOffice().getId();
				officeName =sysOrganizational.getOffice().getName();
			}
			sysOrganizationalService.delete(sysOrganizational);
		}
		addMessage(redirectAttributes, "删除系统组织架构成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizational/list?organTag="+organTag+"&office.id="+officeId+"&office.name="+officeName;
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysOrganizational:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysOrganizational sysOrganizational, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统组织架构"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysOrganizational> page = sysOrganizationalService.findPage(new Page<SysOrganizational>(request, response, -1), sysOrganizational);
    		new ExportExcel("系统组织架构", SysOrganizational.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出系统组织架构记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizational/?menuId="+sysOrganizational.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("sys:sysOrganizational:import")
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
				ei.write(response, "系统组织架构列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SysOrganizational> list = ei.getDataList(SysOrganizational.class);
				for (SysOrganizational sysOrganizational : list){
					try{
						sysOrganizationalService.save(sysOrganizational);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条系统组织架构记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条系统组织架构记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入系统组织架构失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizational/?menuId="+menuId;
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
	 * 下载导入系统组织架构数据模板
	 */
	@RequiresPermissions("sys:sysOrganizational:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "系统组织架构数据导入模板.xlsx";
    		List<SysOrganizational> list = Lists.newArrayList();
    		new ExportExcel("系统组织架构数据", SysOrganizational.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysOrganizational/?repage";
    }

	/**
	 * 指定日期未来指定月数的日期
	 * @param thatDay
	 * @param num
	 * @return
	 */
	private Date getPreMonthDate(Date thatDay, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.add(Calendar.MONTH, +num);
        Date d = c.getTime();
        return d;
    }



}