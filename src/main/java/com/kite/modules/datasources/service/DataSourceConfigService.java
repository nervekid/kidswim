/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.service;

import java.sql.Connection;
import java.util.List;

import com.kite.common.datasource.query.Queryer;
import com.kite.common.datasource.query.QueryerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.modules.datasources.entity.DataSourceConfig;
import com.kite.modules.datasources.dao.DataSourceConfigDao;

/**
 * 多数据配置Service
 * @author cxh
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class DataSourceConfigService extends CrudService<DataSourceConfigDao, DataSourceConfig> {
	@Override
	public DataSourceConfig get(String id) {
		return super.get(id);
	}
	@Override
	public List<DataSourceConfig> findList(DataSourceConfig dataSourceConfig) {
		return super.findList(dataSourceConfig);
	}
	@Override
	public Page<DataSourceConfig> findPage(Page<DataSourceConfig> page, DataSourceConfig dataSourceConfig) {
		return super.findPage(page, dataSourceConfig);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(DataSourceConfig dataSourceConfig) {
		super.save(dataSourceConfig);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(DataSourceConfig dataSourceConfig) {
		super.delete(dataSourceConfig);
	}

	public boolean testConnection(String url, String user, String pass) {
		Connection jdbcConnection = null;
		try {
			Queryer queryer  = QueryerFactory.create(url, user, pass);
			jdbcConnection =  queryer.getJdbcConnection();
			if(jdbcConnection!=null){
				return true;
			}else{
				return false;
			}
		} catch (Exception e) {
			return false;
		}finally {
			if(jdbcConnection!=null){
				QueryerFactory.releaseJdbcResource(jdbcConnection,null,null);
			}
		}
	}
	
	
}