/**
 * MouTai
 */
package com.kite.modules.gen.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.gen.entity.GenTable;
import com.kite.modules.gen.entity.GenTableColumn;

/**
 * 业务表字段DAO接口
 * @author Czh
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableColumnDao extends CrudDao<GenTableColumn> {
	void deleteByGenTable(GenTable var1);
}
