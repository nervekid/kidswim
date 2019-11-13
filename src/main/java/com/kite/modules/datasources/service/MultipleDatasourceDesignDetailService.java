/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.modules.datasources.entity.MultipleDatasourceDesignDetail;
import com.kite.modules.datasources.dao.MultipleDatasourceDesignDetailDao;

/**
 * 多数据配置设计详情Service
 * @author cxh
 * @version 2017-08-21
 */
@Service
@Transactional(readOnly = true)
public class MultipleDatasourceDesignDetailService extends CrudService<MultipleDatasourceDesignDetailDao, MultipleDatasourceDesignDetail> {
	@Override
	public MultipleDatasourceDesignDetail get(String id) {
		return super.get(id);
	}
	@Override
	public List<MultipleDatasourceDesignDetail> findList(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail) {
		return super.findList(multipleDatasourceDesignDetail);
	}
	@Override
	public Page<MultipleDatasourceDesignDetail> findPage(Page<MultipleDatasourceDesignDetail> page, MultipleDatasourceDesignDetail multipleDatasourceDesignDetail) {
		return super.findPage(page, multipleDatasourceDesignDetail);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail) {
		super.save(multipleDatasourceDesignDetail);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(MultipleDatasourceDesignDetail multipleDatasourceDesignDetail) {
		super.delete(multipleDatasourceDesignDetail);
	}
	
	
	
	
}