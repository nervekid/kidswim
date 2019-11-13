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
import com.kite.modules.att.entity.SerSale;
import com.kite.modules.att.service.SerSaleService;

/**
 * 销售资料Controller
 * @author lyb
 * @version 2019-11-13
 */
@Controller
@RequestMapping(value = "${adminPath}/att/serSale")
public class SerSaleController extends BaseController implements BasicVerification {

	@Autowired
	private SerSaleService serSaleService;
	@Autowired
	private SysUserCollectionMenuService sysUserCollectionMenuService;

	/*** 是否导入错误提示*/
	private boolean isTip = false;

	@ModelAttribute
	public SerSale get(@RequestParam(required=false) String id) {
		SerSale entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = serSaleService.get(id);
		}
		if (entity == null){
			entity = new SerSale();
		}
		return entity;
	}

	/**
	 * 销售资料列表页面
	 */
	@RequiresPermissions("att:serSale:list")
	@RequestMapping(value = {"list", ""})
	public String list(SerSale serSale, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SerSale> page = serSaleService.findPage(new Page<SerSale>(request, response), serSale);
		model.addAttribute("page", page);
		sysUserCollectionMenuService.initCollectionMenu(request,model);
		return "modules/att/serSaleList";
	}


	/**
	 * 查看，增加，编辑销售资料表单页面
	 */
	@RequiresPermissions(value={"att:serSale:view","att:serSale:add","att:serSale:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SerSale serSale, Model model) {
		model.addAttribute("serSale", serSale);
		if(serSale.getId()==null){
			// serSale.setMaterialnumber(materialService.findCodeNumber("src_t_material", "materialnumber","LCD"));
			//设置编码
		}
		return "modules/att/serSaleForm";
	}

	/**
	 * 查看打印销售资料表单页面
	 */
	@RequiresPermissions(value={"att:serSale:view"},logical=Logical.OR)
	@RequestMapping(value = "view")
	public String view(SerSale serSale, Model model) {
		model.addAttribute("serSale", serSale);
		return "modules/att/serSaleView";
	}

	/**
	 * 保存销售资料
	 */
	@RequiresPermissions(value={"att:serSale:add","att:serSale:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SerSale serSale, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, serSale)){
			return form(serSale, model);
		}
		if(!serSale.getIsNewRecord()){//编辑表单保存
			SerSale t = serSaleService.get(serSale.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(serSale, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			serSaleService.save(t);//保存
		}else{//新增表单保存
			serSaleService.save(serSale);//保存
		}
		addMessage(redirectAttributes, "保存销售资料成功");
		return "redirect:"+Global.getAdminPath()+"/att/serSale/?menuId="+serSale.getMenuId();
	}

	/**
	 * 删除销售资料
	 */
	@RequiresPermissions("att:serSale:del")
	@RequestMapping(value = "delete")
	public String delete(SerSale serSale, RedirectAttributes redirectAttributes) {
		serSaleService.delete(serSale);
		addMessage(redirectAttributes, "删除销售资料成功");
		return "redirect:"+Global.getAdminPath()+"/att/serSale/?repage";
	}

	/**
	 * 批量删除销售资料
	 */
	@RequiresPermissions("att:serSale:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			serSaleService.delete(serSaleService.get(id));
		}
		addMessage(redirectAttributes, "删除销售资料成功");
		return "redirect:"+Global.getAdminPath()+"/att/serSale/?repage";
	}

	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("att:serSale:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SerSale serSale, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "销售资料"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SerSale> page = serSaleService.findPage(new Page<SerSale>(request, response, -1), serSale);
    		new ExportExcel("销售资料", SerSale.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出销售资料记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serSale/?menuId="+serSale.getMenuId();
    }

	/**
	 * 导入Excel数据

	 */
	@RequiresPermissions("att:serSale:import")
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
				ei.write(response, "销售资料列表导入失败结果"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx");
				return null;
			}
			else {
				this.isTip = false;
				List<SerSale> list = ei.getDataList(SerSale.class);
				for (SerSale serSale : list){
					try{
						serSaleService.save(serSale);
						successNum++;
					}catch(ConstraintViolationException ex){
						failureNum++;
					}catch (Exception ex) {
						failureNum++;
					}
				}
				if (failureNum>0){
					failureMsg.insert(0, "，失败 "+failureNum+" 条销售资料记录。");
				}
				addMessage(redirectAttributes, "已成功导入 "+successNum+" 条销售资料记录"+failureMsg);
			}

		} catch (Exception e) {
			addMessage(redirectAttributes, "导入销售资料失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serSale/?menuId="+menuId;
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
	 * 下载导入销售资料数据模板
	 */
	@RequiresPermissions("att:serSale:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "销售资料数据导入模板.xlsx";
    		List<SerSale> list = Lists.newArrayList();
    		new ExportExcel("销售资料数据", SerSale.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/att/serSale/?repage";
    }




}