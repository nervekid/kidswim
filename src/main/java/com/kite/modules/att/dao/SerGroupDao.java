/**
 * KITE
 */
package com.kite.modules.att.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerGroup;

/**
 * 分组DAO接口
 * @author lyb
 * @version 2019-12-19
 */
@MyBatisDao
public interface SerGroupDao extends CrudDao<SerGroup> {

	
}