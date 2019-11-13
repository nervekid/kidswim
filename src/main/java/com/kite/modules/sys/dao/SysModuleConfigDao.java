/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysModuleConfig;

/**
 * 系统模块配置DAO接口
 * @author cxh
 * @version 2019-04-29
 */
@MyBatisDao
public interface SysModuleConfigDao extends CrudDao<SysModuleConfig> {

	
}