/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.user.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.modules.user.entity.user.ComTDatasource;
import com.kite.modules.user.dao.user.ComTDatasourceDao;

/**
 * 数据源Service
 * @author czh
 * @version 2017-08-24
 */
@Service
@Transactional(readOnly = true)
public class ComTDatasourceService extends CrudService<ComTDatasourceDao, ComTDatasource> {
	@Override
	public ComTDatasource get(String id) {
		return super.get(id);
	}
	@Override
	public List<ComTDatasource> findList(ComTDatasource comTDatasource) {
		return super.findList(comTDatasource);
	}
	@Override
	public Page<ComTDatasource> findPage(Page<ComTDatasource> page, ComTDatasource comTDatasource) {
		return super.findPage(page, comTDatasource);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(ComTDatasource comTDatasource) {
		super.save(comTDatasource);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(ComTDatasource comTDatasource) {
		super.delete(comTDatasource);
	}
	
	
	
	
}