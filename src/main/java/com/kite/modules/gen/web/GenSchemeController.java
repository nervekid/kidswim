/**
 * MouTai
 */
package com.kite.modules.gen.web;

import com.google.common.collect.Lists;
import com.kite.common.persistence.Page;
import com.kite.common.utils.StringUtils;
import com.kite.common.web.BaseController;
import com.kite.modules.gen.entity.GenScheme;
import com.kite.modules.gen.service.GenSchemeService;
import com.kite.modules.gen.service.GenTableService;
import com.kite.modules.gen.util.GenUtils;
import com.kite.modules.sys.entity.Menu;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 生成方案Controller
 *
 * @author Czh
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genScheme")
public class GenSchemeController extends BaseController {

    @Autowired
    private GenSchemeService genSchemeService;
    @Autowired
    private GenTableService genTableService;
    @Autowired
    public SystemService systemService;

    @ModelAttribute
    public GenScheme get(@RequestParam(required = false) String id) {
        if (StringUtils.isNotBlank(id)) {
            return genSchemeService.get(id);
        } else {
            return new GenScheme();
        }
    }

    @RequiresPermissions("gen:genScheme:view")
    @RequestMapping(value = {"list", ""})
    public String list(GenScheme genScheme, HttpServletRequest request, HttpServletResponse response, Model model) {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            genScheme.setCreateBy(user);
        }
        Page<GenScheme> page = genSchemeService.find(new Page<GenScheme>(request, response), genScheme);
        model.addAttribute("page", page);

        return "modules/gen/genSchemeList";
    }

    @RequiresPermissions("gen:genScheme:view")
    @RequestMapping(value = "form")
    public String form(GenScheme genScheme, Model model) {
        if (StringUtils.isBlank(genScheme.getPackageName())) {
            genScheme.setPackageName("com.kite.modules");
        }
        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", genTableService.findAll());
        return "modules/gen/genSchemeForm";
    }

    @RequiresPermissions("gen:genScheme:edit")
    @RequestMapping(value = "save")
    public String save(GenScheme genScheme, Model model, RedirectAttributes redirectAttributes) {
        if (!this.beanValidator(model, genScheme, new Class[0])) {
            if (StringUtils.isBlank(genScheme.getPackageName())) {
                genScheme.setPackageName("com.kite.modules");
            }

            model.addAttribute("genScheme", genScheme);
            model.addAttribute("config", GenUtils.getConfig());
            model.addAttribute("tableList", this.genTableService.findAll());
            return "modules/gen/genSchemeForm";
        } else {
            String result = this.genSchemeService.save(genScheme);
            this.addMessage(redirectAttributes, new String[]{"操作生成方案'" + genScheme.getName() + "'成功<br/>" + result});
            return "redirect:" + this.adminPath + "/gen/genScheme/?repage";
        }
    }

    @RequiresPermissions("gen:genScheme:edit")
    @RequestMapping(value = "delete")
    public String delete(GenScheme genScheme, RedirectAttributes redirectAttributes) {
        genSchemeService.delete(genScheme);
        addMessage(redirectAttributes, new String[]{"删除生成方案成功"});
        return "redirect:" + adminPath + "/gen/genScheme/?repage";
    }

    @RequestMapping({"menuForm"})
    public String menuForm(String gen_table_id, Model model) {
        Menu menu = new Menu();
        if ((menu.getParent() == null) || (menu.getParent().getId() == null)) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(this.systemService.getMenu(menu.getParent().getId()));
        if (StringUtils.isBlank(menu.getId())) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> sourcelist = this.systemService.findAllMenu();
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSort(Integer.valueOf((list.get(list.size() - 1)).getSort().intValue() + 30));
            }
        }
        GenScheme genScheme;
        if ((genScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", gen_table_id)) != null) {
            menu.setName(genScheme.getFunctionName());
        }
        model.addAttribute("menu", menu);
        model.addAttribute("gen_table_id", gen_table_id);
        return "modules/gen/genMenuForm";
    }

    @RequestMapping({"createMenu"})
    public String createMenu(String gen_table_id, Menu menu, RedirectAttributes redirectAttributes) {
        GenScheme genScheme;
        if ((genScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", gen_table_id)) == null) {
            addMessage(redirectAttributes, new String[]{"创建菜单失败,请先生成代码!"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }
        genScheme.setGenTable(this.genTableService.get(gen_table_id));
        this.genSchemeService.createMenu(genScheme, menu);
        addMessage(redirectAttributes, new String[]{"创建菜单'" + genScheme.getFunctionName() + "'成功<br/>"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }
}
