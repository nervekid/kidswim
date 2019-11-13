/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.user.dao.user;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.user.entity.user.ComTUser;

/**
 * userDAO接口
 * @author czh
 * @version 2017-08-24
 */
@MyBatisDao
public interface ComTUserDao extends CrudDao<ComTUser> {

	
}