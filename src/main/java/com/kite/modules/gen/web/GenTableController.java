/**
 * MouTai
 */
package com.kite.modules.gen.web;

import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.utils.StringUtils;
import com.kite.common.web.BaseController;
import com.kite.modules.gen.dao.GenTemplateDao;
import com.kite.modules.gen.entity.GenScheme;
import com.kite.modules.gen.entity.GenTable;
import com.kite.modules.gen.entity.GenTableColumn;
import com.kite.modules.gen.service.GenSchemeService;
import com.kite.modules.gen.service.GenTableService;
import com.kite.modules.gen.service.GenTemplateService;
import com.kite.modules.gen.util.GenUtils;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.service.SystemService;
import com.kite.modules.sys.utils.UserUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * 业务表Controller
 *
 * @author Czh
 * @version 2013-10-15
 */
@Controller
@RequestMapping(value = "${adminPath}/gen/genTable")
public class GenTableController extends BaseController {
    @Autowired
    public SystemService systemService;

    @Autowired
    public GenTemplateService genTemplateService;

    @Autowired
    public GenTableService genTableService;

    @Autowired
    public GenSchemeService genSchemeService;

    @Autowired
    public GenTemplateDao genTemplateDao;
    @Override
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.setAutoGrowCollectionLimit(1024);
    }

//    @ModelAttribute
//    public GenTable get(@RequestParam(required = false) String id) {
//        if (StringUtils.isNotBlank(id)) {
//            return genTableService.get(id);
//        } else {
//            return new GenTable();
//        }
//    }

    public GenTable get(GenTable genTable) {
        return StringUtils.isNotBlank(genTable.getId()) ? this.genTableService.get(genTable.getId()) : genTable;
    }

//    @RequiresPermissions("gen:genTable:view")
//    @RequestMapping(value = "form")
//    public String form(GenTable genTable, Model model) {
//        // 获取物理表列表
//        List<GenTable> tableList = genTableService.findTableListFormDb(new GenTable());
//        model.addAttribute("tableList", tableList);
//        // 验证表是否存在
//        if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())) {
//            addMessage(model, "下一步失败！" + genTable.getName() + " 表已经添加！");
//            genTable.setName("");
//        }
//        // 获取物理表字段
//        else {
//            genTable = genTableService.getTableFormDb(genTable);
//        }
//        model.addAttribute("genTable", genTable);
//        model.addAttribute("config", GenUtils.getConfig());
//        return "modules/gen/genTableForm";
//    }
//
//    @RequiresPermissions("gen:genTable:edit")
//    @RequestMapping(value = "save")
//    public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
//        if (!beanValidator(model, genTable)) {
//            return form(genTable, model);
//        }
//        // 验证表是否已经存在
//        if (StringUtils.isBlank(genTable.getId()) && !genTableService.checkTableName(genTable.getName())) {
//            addMessage(model, "保存失败！" + genTable.getName() + " 表已经存在！");
//            genTable.setName("");
//            return form(genTable, model);
//        }
//        genTableService.save(genTable);
//        addMessage(redirectAttributes, "保存业务表'" + genTable.getName() + "'成功");
//        return "redirect:" + adminPath + "/gen/genTable/?repage";
//    }
//
//    @RequiresPermissions("gen:genTable:edit")
//    @RequestMapping(value = "delete")
//    public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
//        genTableService.delete(genTable);
//        addMessage(redirectAttributes, "删除业务表成功");
//        return "redirect:" + adminPath + "/gen/genTable/?repage";
//    }

    @RequiresPermissions({"gen:genTable:list"})
    @RequestMapping({"list", ""})
    public String list(GenTable genTable, HttpServletRequest request, HttpServletResponse response, Model model) throws IOException {
        User user = UserUtils.getUser();
        if (!user.isAdmin()) {
            genTable.setCreateBy(user);
        }
        Page<GenTable> page = genTableService.find(new Page<GenTable>(request, response), genTable);
        model.addAttribute("page", page);
        return "modules/gen/genTableList";
    }

    @RequiresPermissions(value = {"gen:genTable:view", "gen:genTable:add", "gen:genTable:edit"}, logical = Logical.OR)
    @RequestMapping({"form"})
    public String form(GenTable genTable, HttpServletResponse response, Model model) throws IOException {
      logger.error("view form ============================");
        genTable = get(genTable);
        model.addAttribute("genTable", genTable);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", this.genTableService.findAll());
        return "modules/gen/genTableForm";
    }

    @RequiresPermissions(value = {"gen:genTable:add", "gen:genTable:edit"}, logical = Logical.OR)
    @RequestMapping({"save"})
    public String save(GenTable genTable, Model model, RedirectAttributes redirectAttributes, HttpServletResponse response) throws IOException {
        if (!beanValidator(model, genTable, new Class[0])) {
            GenTable model1 = get(genTable);
            model.addAttribute("genTable", model1);
            model.addAttribute("config", GenUtils.getConfig());
            model.addAttribute("tableList", this.genTableService.findAll());
            return "modules/gen/genTableForm";
        } else if (StringUtils.isBlank(genTable.getId()) && !this.genTableService.checkTableName(genTable.getName())) {
            this.addMessage(redirectAttributes, new String[]{"添加失败！" + genTable.getName() + " 记录已存在！"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        } else if (StringUtils.isBlank(genTable.getId()) && !this.genTableService.checkTableNameFromDB(genTable.getName())) {
            this.addMessage(redirectAttributes, new String[]{"添加失败！" + genTable.getName() + "表已经在数据库中存在,请从数据库导入表单！"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        } else {
            this.genTableService.save(genTable);
            this.addMessage(redirectAttributes, new String[]{"保存业务表'" + genTable.getName() + "'成功"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }
    }

    @RequiresPermissions({"gen:genTable:importDb"})
    @RequestMapping({"importTableFromDB"})
    public String importTableFromDB(GenTable genTable, Model model, RedirectAttributes redirectAttributes) {
        genTable = get(genTable);
        genTable.setTableType("0");
        if (!StringUtils.isBlank(genTable.getName())) {
            if (!this.genTableService.checkTableName(genTable.getName())) {
                this.addMessage(redirectAttributes, new String[]{"下一步失败！" + genTable.getName() + " 表已经添加！"});
                return "redirect:" + this.adminPath + "/gen/genTable/?repage";
            } else {
                (genTable = this.genTableService.getTableFormDb(genTable)).setTableType("0");
                this.genTableService.saveFromDB(genTable);
                this.addMessage(redirectAttributes, new String[]{"数据库导入表单'" + genTable.getName() + "'成功"});
                return "redirect:" + this.adminPath + "/gen/genTable/?repage";
            }
        } else {
            List<GenTable> tableList = this.genTableService.findTableListFormDb(new GenTable());
            model.addAttribute("tableList", tableList);
            model.addAttribute("config", GenUtils.getConfig());
            return "modules/gen/importTableFromDB";
        }
    }

    @RequiresPermissions({"gen:genTable:del"})
    @RequestMapping({"delete"})
    public String delete(GenTable genTable, RedirectAttributes redirectAttributes) {
        genTable = this.get(genTable);
        this.genTableService.delete(genTable);
        this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
        this.addMessage(redirectAttributes, new String[]{"移除业务表记录成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    @RequiresPermissions({"gen:genTable:del"})
    @RequestMapping({"deleteDb"})
    public String deleteDb(GenTable genTable, RedirectAttributes redirectAttributes) {
        if (Global.isDemoMode().booleanValue()) {
            this.addMessage(redirectAttributes, new String[]{"演示模式，不允许操作！"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        } else {
            genTable = this.get(genTable);
            this.genTableService.delete(genTable);
            this.genSchemeService.delete(this.genSchemeService.findUniqueByProperty("gen_table_id", genTable.getId()));
            StringBuffer sql = new StringBuffer();
            String dbType = Global.getConfig("jdbc.type");
            if ("mysql".equals(dbType)) {
                sql.append("drop table if exists " + genTable.getName() + " ;");
            } else if ("oracle".equals(dbType)) {
                try {
                    sql.append("DROP TABLE " + genTable.getName());
                } catch (Exception var5) {
                    ;
                }
            } else if ("mssql".equals(dbType) || "sqlserver".equals(dbType)) {
                sql.append("if exists (select * from sysobjects where id = object_id(N'[" + genTable.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + genTable.getName() + "]");
            }

            this.genTableService.buildTable(sql.toString());
            this.addMessage(redirectAttributes, new String[]{"删除业务表记录和数据库表成功"});
            return "redirect:" + this.adminPath + "/gen/genTable/?repage";
        }
    }

    @RequiresPermissions({"gen:genTable:del"})
    @RequestMapping({"deleteAll"})
    public String deleteAll(String id, RedirectAttributes redirectAttributes) {
        String[] arrId = id.split(",");
        int idLength = arrId.length;

        for (int i = 0; i < idLength; ++i) {
            id = arrId[i];
            this.genTableService.delete(this.genTableService.get(id));
        }

        this.addMessage(redirectAttributes, new String[]{"删除业务表成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    @RequiresPermissions({"gen:genTable:synchDb"})
    @RequestMapping({"synchDb"})
    public String synchDb(GenTable genTable, RedirectAttributes redirectAttributes) {
        String dbType = Global.getConfig("jdbc.type");
        genTable = this.get(genTable);
        List<GenTableColumn> getTableColumnList = genTable.getColumnList();
        String pk;
        GenTableColumn column;
        Iterator tableColumnIterator;
        StringBuffer sql;
        if ("mysql".equals(dbType)) {
            sql = new StringBuffer();
            sql.append("drop table if exists " + genTable.getName() + " ;");
            this.genTableService.buildTable(sql.toString());
            sql = new StringBuffer();
            sql.append("create table " + genTable.getName() + " (");
            pk = "";
            tableColumnIterator = getTableColumnList.iterator();

            while (tableColumnIterator.hasNext()) {
                if ((column = (GenTableColumn) tableColumnIterator.next()).getIsPk().equals("1")) {
                    sql.append("  " + column.getName() + " " + column.getJdbcType() + " comment '" + column.getComments() + "',");
                    pk = pk + column.getName() + ",";
                } else {
                    sql.append("  " + column.getName() + " " + column.getJdbcType() + " comment '" + column.getComments() + "',");
                }
            }

            sql.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");
            sql.append(") comment '" + genTable.getComments() + "'");
            this.genTableService.buildTable(sql.toString());
        } else if ("oracle".equals(dbType)) {
            sql = new StringBuffer();

            try {
                sql.append("DROP TABLE " + genTable.getName());
                this.genTableService.buildTable(sql.toString());
            } catch (Exception var9) {
                ;
            }

            (sql = new StringBuffer()).append("create table " + genTable.getName() + " (");
            pk = "";
            tableColumnIterator = getTableColumnList.iterator();

            while (tableColumnIterator.hasNext()) {
                String jdbctype;
                if ((jdbctype = (column = (GenTableColumn) tableColumnIterator.next()).getJdbcType()).equalsIgnoreCase("integer")) {
                    jdbctype = "number(10,0)";
                } else if (jdbctype.equalsIgnoreCase("datetime")) {
                    jdbctype = "date";
                } else if (jdbctype.contains("nvarchar(")) {
                    jdbctype = jdbctype.replace("nvarchar", "nvarchar2");
                } else if (jdbctype.contains("varchar(")) {
                    jdbctype = jdbctype.replace("varchar", "varchar2");
                } else if (jdbctype.equalsIgnoreCase("double")) {
                    jdbctype = "float(24)";
                } else if (jdbctype.equalsIgnoreCase("longblob")) {
                    jdbctype = "blob raw";
                } else if (jdbctype.equalsIgnoreCase("longtext")) {
                    jdbctype = "clob raw";
                }

                if (column.getIsPk().equals("1")) {
                    sql.append("  " + column.getName() + " " + jdbctype + ",");
                    pk = pk + column.getName();
                } else {
                    sql.append("  " + column.getName() + " " + jdbctype + ",");
                }
            }

            sql = new StringBuffer(sql.substring(0, sql.length() - 1) + ")");
            this.genTableService.buildTable(sql.toString());
            this.genTableService.buildTable("comment on table " + genTable.getName() + " is  '" + genTable.getComments() + "'");
            tableColumnIterator = getTableColumnList.iterator();

            while (tableColumnIterator.hasNext()) {
                column = (GenTableColumn) tableColumnIterator.next();
                this.genTableService.buildTable("comment on column " + genTable.getName() + "." + column.getName() + " is  '" + column.getComments() + "'");
            }

            this.genTableService.buildTable("alter table " + genTable.getName() + " add constraint PK_" + genTable.getName() + "_" + pk + " primary key (" + pk + ") ");
        } else if ("mssql".equals(dbType) || "sqlserver".equals(dbType)) {
            (sql = new StringBuffer()).append("if exists (select * from sysobjects where id = object_id(N'[" + genTable.getName() + "]') and OBJECTPROPERTY(id, N'IsUserTable') = 1)  drop table [" + genTable.getName() + "]");
            this.genTableService.buildTable(sql.toString());
            (sql = new StringBuffer()).append("create table " + genTable.getName() + " (");
            pk = "";
            tableColumnIterator = getTableColumnList.iterator();

            while (tableColumnIterator.hasNext()) {
                if ((column = (GenTableColumn) tableColumnIterator.next()).getIsPk().equals("1")) {
                    sql.append("  " + column.getName() + " " + column.getJdbcType() + ",");
                    pk = pk + column.getName() + ",";
                } else {
                    sql.append("  " + column.getName() + " " + column.getJdbcType() + ",");
                }
            }

            sql.append("primary key (" + pk.substring(0, pk.length() - 1) + ") ");
            sql.append(")");
            this.genTableService.buildTable(sql.toString());
        }

        this.genTableService.syncSave(genTable);
        this.addMessage(redirectAttributes, new String[]{"强制同步数据库表成功"});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }

    @RequiresPermissions({"gen:genTable:genCode"})
    @RequestMapping({"genCodeForm"})
    public String genCodeForm(GenScheme genScheme, Model model) {
        if (StringUtils.isBlank(genScheme.getPackageName())) {
            genScheme.setPackageName("com.kite.modules");
        }

        GenScheme oldGenScheme;
        if ((oldGenScheme = this.genSchemeService.findUniqueByProperty("gen_table_id", genScheme.getGenTable().getId())) != null) {
            genScheme = oldGenScheme;
        }

        model.addAttribute("genScheme", genScheme);
        model.addAttribute("config", GenUtils.getConfig());
        model.addAttribute("tableList", this.genTableService.findAll());
        return "modules/gen/genCodeForm";
    }

    @RequestMapping({"genCode"})
    public String genCode(GenScheme genScheme, RedirectAttributes redirectAttributes) {
        genScheme.setFlag("1");
        genScheme.setReplaceFile(true);
        String result = this.genSchemeService.save(genScheme);
        this.addMessage(redirectAttributes, new String[]{genScheme.getGenTable().getName() + "代码生成成功<br/>" + result});
        return "redirect:" + this.adminPath + "/gen/genTable/?repage";
    }
}