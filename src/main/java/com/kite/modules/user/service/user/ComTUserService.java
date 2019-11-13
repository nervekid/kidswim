/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.user.service.user;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.xw_db.DataSource;
import com.kite.modules.user.entity.user.ComTUser;
import com.kite.modules.user.dao.user.ComTUserDao;

/**
 * userService
 * @author czh
 * @version 2017-08-24
 */
@Service
@Transactional(readOnly = true)
public class ComTUserService extends CrudService<ComTUserDao, ComTUser> {

	public ComTUser get(String datasource,String id) {
		return super.get(id);
	}
	
	public List<ComTUser> findList(String datasource,ComTUser comTUser) {
		return super.findList(comTUser);
	}
	
	public Page<ComTUser> findPage(String datasource,Page<ComTUser> page, ComTUser comTUser) {
		return super.findPage(page, comTUser);
	}
	
	@Transactional(readOnly = false)
	public void save(String datasource,ComTUser comTUser) {
		super.save(comTUser);
	}
	
	@Transactional(readOnly = false)
	public void delete(String datasource,ComTUser comTUser) {
		super.delete(comTUser);
	}
	
	
	
	
}