/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysTEntityuseorgan;
import org.apache.ibatis.annotations.Param;

/**
 * 实体功能对应组织架构DAO接口
 * @author lyb
 * @version 2018-10-26
 */
@MyBatisDao
public interface SysTEntityuseorganDao extends CrudDao<SysTEntityuseorgan> {

	public int isEntityRepeat(@Param("dataTableName") String dataTableName);
}