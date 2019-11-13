/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.service;

import java.sql.Connection;
import java.util.List;

import com.google.common.collect.Lists;
import com.kite.common.datasource.query.Queryer;
import com.kite.common.datasource.query.QueryerFactory;
import com.kite.modules.datasources.entity.DataSourceConfig;
import com.kite.modules.datasources.entity.MultipleDatasourceDesignDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.modules.datasources.entity.MultipleDatasourceDesign;
import com.kite.modules.datasources.dao.MultipleDatasourceDesignDao;

/**
 * 多数据配置设计Service
 * @author cxh
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class MultipleDatasourceDesignService extends CrudService<MultipleDatasourceDesignDao, MultipleDatasourceDesign> {

	@Autowired
	private DataSourceConfigService dataSourceConfigService;
	@Override
	public MultipleDatasourceDesign get(String id) {
		return super.get(id);
	}
	@Override
	public List<MultipleDatasourceDesign> findList(MultipleDatasourceDesign multipleDatasourceDesign) {
		return super.findList(multipleDatasourceDesign);
	}
	@Override
	public Page<MultipleDatasourceDesign> findPage(Page<MultipleDatasourceDesign> page, MultipleDatasourceDesign multipleDatasourceDesign) {
		return super.findPage(page, multipleDatasourceDesign);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(MultipleDatasourceDesign multipleDatasourceDesign) {
		super.save(multipleDatasourceDesign);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(MultipleDatasourceDesign multipleDatasourceDesign) {
		super.delete(multipleDatasourceDesign);
	}


    public List<MultipleDatasourceDesignDetail> getReportMetaDataColumns(String dataSourceConfigId, String sqlText) {
		List<MultipleDatasourceDesignDetail> multipleDatasourceDesignDetailList = Lists.newArrayList();
		DataSourceConfig config = dataSourceConfigService.get(dataSourceConfigId);
		if(config!=null){
			Queryer queryer = QueryerFactory.create(config.getJdbcUrl(), config.getUser(), config.getPassword());
			if(queryer!=null){
				multipleDatasourceDesignDetailList = QueryerFactory.parseMetaDataColumns(sqlText,queryer);
			}
		}
		return multipleDatasourceDesignDetailList;
	}
}