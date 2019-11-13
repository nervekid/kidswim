/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.Log;
import com.kite.modules.sys.entity.LogOfObject;

/**
 * 日志DAO接口
 * @author kite
 * @version 2014-05-16
 */
@MyBatisDao
public interface LogDao extends CrudDao<Log> {

	public void empty();

	/**
	 *记录Object 修改前后的 Json 对象
	 */
	public  void saveChangeOfObeject(LogOfObject  logOfObject);


}
