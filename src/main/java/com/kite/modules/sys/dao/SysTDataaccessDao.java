/**
 * KITE
 */
package com.kite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysTDataaccess;

/**
 * 多组织架构数据权限DAO接口
 * @author lyb
 * @version 2018-10-30
 */
@MyBatisDao
public interface SysTDataaccessDao extends CrudDao<SysTDataaccess> {

	String dataAccessSQls(@Param("dataAccessId") String dataAccessId, @Param("entityOrgId") String entityOrgId);
}