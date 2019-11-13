/**
 * KITE
 */
package com.kite.modules.att.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerSale;

/**
 * 销售资料DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SerSaleDao extends CrudDao<SerSale> {

	
}