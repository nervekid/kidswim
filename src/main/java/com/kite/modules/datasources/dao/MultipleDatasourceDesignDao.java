/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">kite</a> All rights reserved.
 */
package com.kite.modules.datasources.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.datasources.entity.MultipleDatasourceDesign;

/**
 * 多数据配置设计DAO接口
 * @author cxh
 * @version 2017-08-21
 */
@MyBatisDao
public interface MultipleDatasourceDesignDao extends CrudDao<MultipleDatasourceDesign> {

	
}