/**
 * MouTai
 */
package com.kite.modules.gen.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.gen.entity.GenTable;
import org.apache.ibatis.annotations.Param;

/**
 * 业务表DAO接口
 * @author Czh
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTableDao extends CrudDao<GenTable> {
    public abstract int buildTable(@Param("sql") String paramString);
	public String getCommentsByName(@Param("name") String name);
}
