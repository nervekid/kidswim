/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysTDataaccessuser;

/**
 * 多组织架构用户对应数据权限组DAO接口
 * @author lyb
 * @version 2018-10-31
 */
@MyBatisDao
public interface SysTDataaccessuserDao extends CrudDao<SysTDataaccessuser> {

}