/**
 * KITE
 */
package com.kite.modules.att.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerRollCall;

/**
 * 點名DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SerRollCallDao extends CrudDao<SerRollCall> {


}