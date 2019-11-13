/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysTDataaccessentity;

/**
 * 多组织架构数据权限DAO接口
 * @author lyb
 * @version 2018-10-30
 */
@MyBatisDao
public interface SysTDataaccessentityDao extends CrudDao<SysTDataaccessentity> {

	
}