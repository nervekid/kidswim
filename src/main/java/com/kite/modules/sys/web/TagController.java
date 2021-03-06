/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.web;

import com.kite.common.web.BaseController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 标签Controller
 * @author kite
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/tag")
public class TagController extends BaseController {
	
	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "treeselect")
	public String treeselect(HttpServletRequest request, Model model) {
		treeSelectPublicMothod(request,model);
		return "modules/sys/tagTreeselect";
	}
	
	/**
	 * 图标选择标签（iconselect.tag）
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "iconselect")
	public String iconselect(HttpServletRequest request, Model model) {
		model.addAttribute("value", request.getParameter("value"));
		return "modules/sys/tagIconselect";
	}


	/**
	 * 树结构选择标签（materialtypeselect.tag）
	 */
	@RequestMapping(value = "materialtypeselect")
	public String materialtypeselect(HttpServletRequest request, Model model) {
		treeSelectPublicMothod(request,model);
		return "modules/sys/materialTypeSelect";
	}

	private void treeSelectPublicMothod(HttpServletRequest request, Model model){
		model.addAttribute("url", request.getParameter("url")); 	// 树结构数据URL
		model.addAttribute("extId", request.getParameter("extId")); // 排除的编号ID
		model.addAttribute("checked", request.getParameter("checked")); // 是否可复选
		model.addAttribute("selectIds", request.getParameter("selectIds")); // 指定默认选中的ID
		model.addAttribute("isAll", request.getParameter("isAll")); 	// 是否读取全部数据，不进行权限过滤
		model.addAttribute("module", request.getParameter("module"));	// 过滤栏目模型（仅针对CMS的Category树）
		model.addAttribute("yearMonth", request.getParameter("yearMonth"));	// 过滤栏目模型（仅针对CMS的Category树）
		model.addAttribute("organTag", request.getParameter("organTag"));	//多组织机构标识 1：代表职能线 3：代表提成线
	}


	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequestMapping(value = "treeselectAch")
	public String treeselectAch(HttpServletRequest request, Model model) {
		treeSelectPublicMothod(request,model);
		return "modules/sys/tagTreeselectAch";
	}

	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequestMapping(value = "treeselectMoc")
	public String treeselectMoc(HttpServletRequest request, Model model) {
		treeSelectPublicMothod(request,model);
		return "modules/sys/tagTreeselectMoc";
	}

	/**
	 * 树结构选择标签（treeselect.tag）
	 */
	@RequestMapping(value = "treeselectposition")
	public String treeselectposition(HttpServletRequest request, Model model) {
		treeSelectPublicMothod(request,model);
		return "modules/sys/tagTreeselectPosition";
	}
}
