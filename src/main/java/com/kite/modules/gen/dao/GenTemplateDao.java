/**
 * MouTai
 */
package com.kite.modules.gen.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.gen.entity.GenTemplate;

/**
 * 代码模板DAO接口
 * @author Czh
 * @version 2013-10-15
 */
@MyBatisDao
public interface GenTemplateDao extends CrudDao<GenTemplate> {
	
}
