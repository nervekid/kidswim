package com.kite.modules.gen.service;

import com.kite.common.persistence.Page;
import com.kite.common.service.BaseService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.gen.dao.GenDataBaseDictDao;
import com.kite.modules.gen.dao.GenTableColumnDao;
import com.kite.modules.gen.dao.GenTableDao;
import com.kite.modules.gen.entity.GenScheme;
import com.kite.modules.gen.entity.GenTable;
import com.kite.modules.gen.entity.GenTableColumn;
import com.kite.modules.gen.template.TemplateProcessor;
import com.kite.modules.gen.util.GenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
public class CgAutoListService extends BaseService {

    @Autowired
    private GenTableDao genTableDao;
    @Autowired
    private GenTableColumnDao genTableColumnDao;
    @Autowired
    private GenDataBaseDictDao genDataBaseDictDao;

    public GenTable get(String id) {
        GenTable genTable = this.genTableDao.get(id);
        GenTableColumn genTableColumn = new GenTableColumn();
        genTableColumn.setGenTable(new GenTable(genTable.getId()));
        genTable.setColumnList(this.genTableColumnDao.findList(genTableColumn));
        return genTable;
    }

    public Page<GenTable> find(Page<GenTable> page, GenTable genTable) {
        genTable.setPage(page);
        page.setList(this.genTableDao.findList(genTable));
        return page;
    }

    public List<GenTable> findAll() {
        return this.genTableDao.findAllList(new GenTable());
    }

    public List<GenTable> findTableListFormDb(GenTable genTable) {
        return this.genDataBaseDictDao.findTableList(genTable);
    }

    public boolean checkTableName(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);

        return this.genTableDao.findList(genTable).size() == 0;
    }

    public boolean checkTableNameFromDB(String tableName) {
        if (StringUtils.isBlank(tableName)) {
            return true;
        }
        GenTable genTable = new GenTable();
        genTable.setName(tableName);

        return this.genDataBaseDictDao.findTableList(genTable).size() == 0;
    }

    public String generateCode(GenScheme genScheme) {
        GenTable genTable = this.genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(this.genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        GenUtils.getConfig();
        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
        String var5 = "/com/mtsite/modules/gen/template/viewList.ftl";
        return TemplateProcessor.process(var5, "utf-8", model);
    }

    public String generateListCode(GenScheme genScheme) {
        GenTable genTable = this.genTableDao.get(genScheme.getGenTable().getId());
        genTable.setColumnList(this.genTableColumnDao.findList(new GenTableColumn(new GenTable(genTable.getId()))));
        GenUtils.getConfig();
        genScheme.setGenTable(genTable);
        Map<String, Object> model = GenUtils.getDataModel(genScheme);
//        new com.kite.modules.gen.template.a();
        String var5 = "/com/mtsite/modules/gen/template/findList.ftl";
        return TemplateProcessor.process(var5, "utf-8", model);
    }
}

