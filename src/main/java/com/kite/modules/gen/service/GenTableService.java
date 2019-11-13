/**
 * MouTai
 */
package com.kite.modules.gen.service;

import com.kite.common.persistence.Page;
import com.kite.common.service.BaseService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.gen.dao.GenDataBaseDictDao;
import com.kite.modules.gen.dao.GenTableColumnDao;
import com.kite.modules.gen.dao.GenTableDao;
import com.kite.modules.gen.entity.GenTable;
import com.kite.modules.gen.entity.GenTableColumn;
import com.kite.modules.gen.util.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 业务表Service
 *
 * @author Czh
 * @version 2013-10-15
 */
@Service
@Transactional(readOnly = true)
public class GenTableService extends BaseService {

    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;
    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;

    public GenTable get(String id) {
        GenTable genTable = genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
        genTable.setPage(page);
        page.setList(genTableDao.findList(genTable));
        return page;
    }

    public List<GenTable> findAll() {
        return genTableDao.findAllList(new GenTable());
    }

    /**
     * 获取物理数据表列表
     *
     * @param genTable
     * @return
     */
    public List<GenTable> findTableListFormDb(GenTable genTable) {
        return genDataBaseDictDao.findTableList(genTable);
    }

    /**
     * 验证表名是否可用，如果已存在，则返回false
     *
     * @param tableName
     * @return
     */
    public boolean checkTableName(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);
        List<GenTable> list = genTableDao.findList(genTable);
        return list.size() == 0;
    }

    public boolean checkTableNameFromDB(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);

        return this.genDataBaseDictDao.findTableList(genTable).size() == 0;
    }

    /**
     * 获取物理数据表列表
     *
     * @param genTable
     * @return
     */
    public GenTable getTableFormDb(GenTable genTable) {
        // 如果有表名，则获取物理表
        if (StringUtils.isNotBlank(genTable.getName())) {

            List<GenTable> list = genDataBaseDictDao.findTableList(genTable);
            System.out.println("list.size()==============="+list.size());
            if (list.size() > 0) {

                // 如果是新增，初始化表属性
                if (StringUtils.isBlank(genTable.getId())) {
                    genTable = list.get(0);
                    // 设置字段说明
                    if (StringUtils.isBlank(genTable.getComments())) {
                        genTable.setComments(genTable.getName());
                    }
                    genTable.setClassName(StringUtils.toCapitalizeCamelCase(genTable.getName()));
                }

                // 添加新列
                List<GenTableColumn> columnList = genDataBaseDictDao.findTableColumnList(genTable);
                for (GenTableColumn column : columnList) {
                    boolean b = false;
                    for (GenTableColumn e : genTable.getColumnList()) {
                        if (e.getName() != null && e.getName().equals(column.getName())) {
                            b = true;
                        }
                    }
                    if (!b) {
                        genTable.getColumnList().add(column);
                    }
                }

                // 删除已删除的列
                for (GenTableColumn e : genTable.getColumnList()) {
                    boolean b = false;
                    for (GenTableColumn column : columnList) {
                        if (column.getName().equals(e.getName())) {
                            b = true;
                        }
                    }
                    if (!b) {
                        e.setDelFlag(GenTableColumn.DEL_FLAG_DELETE);
                    }
                }

                // 获取主键
                genTable.setPkList(genDataBaseDictDao.findTablePK(genTable));

                // 初始化列属性字段
                GenUtils.initColumnField(genTable);
            }
        }
        return genTable;
    }

    @Transactional(readOnly = false)
    public void save(GenTable genTable) {
        boolean isSync = true;
        if (StringUtils.isBlank(genTable.getId())) {
            isSync = false;
        } else {
            GenTable oldTable = get(genTable.getId());
            if ((oldTable.getColumnList().size() != genTable.getColumnList().size()) || (!oldTable.getName().equals(genTable.getName())) || (!oldTable.getComments().equals(genTable.getComments()))) {
                isSync = false;
            } else {
                List<GenTableColumn> columnList = genTable.getColumnList();
                for (GenTableColumn oldColumn : oldTable.getColumnList()) {
                    for (GenTableColumn column : columnList) {
                        if ((StringUtils.isBlank(column.getId())) ||
                                (!(oldColumn = this.genTableColumnDao.get(column.getId())).getName().equals(column.getName())) ||
                                (!oldColumn.getJdbcType().equals(column.getJdbcType())) ||
                                (!oldColumn.getIsPk().equals(column.getIsPk())) ||
                                (!oldColumn.getComments().equals(column.getComments()))) {
                            isSync = false;
                        }
                    }
                }
            }
        }
        if (!isSync) {
            genTable.setIsSync("0");
        }
        if (StringUtils.isBlank(genTable.getId())) {
            genTable.preInsert();
            genTableDao.insert(genTable);
        } else {
            genTable.preUpdate();
            genTableDao.update(genTable);
        }

        this.genTableColumnDao.deleteByGenTable(genTable);
        // 保存列
        for (GenTableColumn column : genTable.getColumnList()) {
            column.setGenTable(genTable);
            column.setId(null);
            column.preInsert();
            this.genTableColumnDao.insert(column);
        }
    }

    @Transactional(readOnly = false)
    public void syncSave(GenTable genTable) {
        genTable.setIsSync("1");
        this.genTableDao.update(genTable);
    }

    @Transactional(readOnly = false)
    public void saveFromDB(GenTable genTable) {
        genTable.preInsert();
        this.genTableDao.insert(genTable);
        List<GenTableColumn> columnList = genTable.getColumnList();
        for (GenTableColumn column : columnList) {
            column.setGenTable(genTable);
            column.setId(null);
            column.preInsert();
            this.genTableColumnDao.insert(column);
        }
    }

    @Transactional(readOnly = false)
    public void delete(GenTable genTable) {
        this.genTableDao.delete(genTable);
        this.genTableColumnDao.deleteByGenTable(genTable);
    }

    @Transactional(readOnly = false)
    public void buildTable(String sql) {
        this.genTableDao.buildTable(sql);
    }
	@Transactional(readOnly = false)
    public String findCommentsByName(String name) {
    	return this.genTableDao.getCommentsByName(name);
    }
}
