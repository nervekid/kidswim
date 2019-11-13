/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysOrganizationalApproval;

/**
 * 组织架构职级表DAO接口
 * @author lyb
 * @version 2019-08-19
 */
@MyBatisDao
public interface SysOrganizationalApprovalDao extends CrudDao<SysOrganizationalApproval> {

	
}