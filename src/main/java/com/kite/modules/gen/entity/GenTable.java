/**
 * MouTai
 */
package com.kite.modules.gen.entity;

import com.google.common.collect.Lists;
import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.StringUtils;
import org.hibernate.validator.constraints.Length;

import java.util.List;

/**
 * 业务表Entity
 *
 * @author Czh
 * @version 2013-10-15
 */
public class GenTable extends DataEntity<GenTable> {

    private static final long serialVersionUID = 1L;
    private String name;    // 名称
    private String comments;        // 描述
    private String tableType;
    private String className;        // 实体类名称
    private String parentTable;        // 关联父表
    private String parentTableFk;        // 关联父表外键
    private String isSync;
    private List<GenTableColumn> columnList = Lists.newArrayList();    // 表列

    private String nameLike;    // 按名称模糊查询

    private List<String> pkList; // 当前表主键列表

    private GenTable parent;    // 父表对象
    private List<GenTable> childList = Lists.newArrayList();    // 子表列表

    public GenTable() {
        super();
    }

    public GenTable(String id) {
        super(id);
    }

    @Length(min = 1, max = 200)
    public String getName() {
        return StringUtils.lowerCase(name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getParentTable() {
        return StringUtils.lowerCase(parentTable);
    }

    public void setParentTable(String parentTable) {
        this.parentTable = parentTable;
    }

    public String getParentTableFk() {
        return StringUtils.lowerCase(parentTableFk);
    }

    public void setParentTableFk(String parentTableFk) {
        this.parentTableFk = parentTableFk;
    }

    public List<String> getPkList() {
        return pkList;
    }

    public void setPkList(List<String> pkList) {
        this.pkList = pkList;
    }

    public String getNameLike() {
        return nameLike;
    }

    public void setNameLike(String nameLike) {
        this.nameLike = nameLike;
    }

    public GenTable getParent() {
        return parent;
    }

    public void setParent(GenTable parent) {
        this.parent = parent;
    }

    public List<GenTableColumn> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<GenTableColumn> columnList) {
        this.columnList = columnList;
    }

    public List<GenTable> getChildList() {
        return childList;
    }

    public void setChildList(List<GenTable> childList) {
        this.childList = childList;
    }

    /**
     * 获取列名和说明
     *
     * @return
     */
    public String getNameAndComments() {
        return this.getName() + (this.comments == null ? "" : "  :  " + this.comments);
    }

    /**
     * 获取导入依赖包字符串
     *
     * @return
     */
    public List<String> getImportList() {
        List<String> importList = Lists.newArrayList(); // 引用列表
        for (GenTableColumn column : getColumnList()) {
            if ((column.getIsNotBaseField().booleanValue()) || (("1".equals(column.getIsQuery())) && ("between".equals(column.getQueryType())) && (
                    ("createDate".equals(column.getSimpleJavaField())) || ("updateDate".equals(column.getSimpleJavaField()))))) {
                // 导入类型依赖包， 如果类型中包含“.”，则需要导入引用。
                if ((StringUtils.indexOf(column.getJavaType(), ".") != -1) && (!importList.contains(column.getJavaType()))) {
                    importList.add(column.getJavaType());
                }
            }
            if (column.getIsNotBaseField().booleanValue()) {
                // 导入JSR303、Json等依赖包
                for (String ann : column.getAnnotationList()) {
                    if (!importList.contains(StringUtils.substringBeforeLast(ann, "("))) {
                        importList.add(StringUtils.substringBefore(ann, "("));
                    }
                }
            }
        }
        // 如果有子表，则需要导入List相关引用
        if ((getChildList() != null) && (getChildList().size() > 0)) {
            if (!importList.contains("java.util.List")) {
                importList.add("java.util.List");
            }
            if (!importList.contains("com.google.common.collect.Lists")) {
                importList.add("com.google.common.collect.Lists");
            }
        }
        return importList;
    }

    public List<String> getImportGridJavaList() {
        List<String> importList = (List) Lists.newArrayList();
        for (GenTableColumn column : getColumnList()) {
            if ((column.getTableName() != null) && (!column.getTableName().equals(""))) {
                if ((StringUtils.indexOf(column.getJavaType(), ".") != -1) && (!importList.contains(column.getJavaType()))) {
                    importList.add(column.getJavaType());
                }
            }
        }
        return importList;
    }

    public List<String> getImportGridJavaDaoList() {
        boolean isNeedList = false;
        List<String> importList = (List) Lists.newArrayList();
        for (GenTableColumn column : getColumnList()) {
            if ((column.getTableName() != null) && (!column.getTableName().equals(""))) {
                if ((StringUtils.indexOf(column.getJavaType(), ".") != -1) && (!importList.contains(column.getJavaType()))) {
                    importList.add(column.getJavaType());
                    isNeedList = true;
                }
            }
        }
        if ((isNeedList) &&
                (!importList.contains("java.util.List"))) {
            importList.add("java.util.List");
        }
        return importList;
    }

    public Boolean getParentExists() {
        if ((this.parent != null) && (StringUtils.isNotBlank(this.parentTable)) && (StringUtils.isNotBlank(this.parentTableFk))) {
            return Boolean.valueOf(true);
        }
        return Boolean.valueOf(false);
    }

    public Boolean getCreateDateExists() {
        for (GenTableColumn c : this.columnList) {
            if ("create_date".equals(c.getName())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    /**
     * 是否存在update_date列
     *
     * @return
     */
    public Boolean getUpdateDateExists() {
        for (GenTableColumn c : this.columnList) {
            if ("update_date".equals(c.getName())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    /**
     * 是否存在del_flag列
     *
     * @return
     */
    public Boolean getDelFlagExists() {
        for (GenTableColumn c : this.columnList) {
            if ("del_flag".equals(c.getName())) {
                return Boolean.valueOf(true);
            }
        }
        return Boolean.valueOf(false);
    }

    public void setIsSync(String isSync) {
        this.isSync = isSync;
    }

    public String getIsSync() {
        return this.isSync;
    }

    public void setTableType(String tableType) {
        this.tableType = tableType;
    }

    public String getTableType() {
        return this.tableType;
    }
}


