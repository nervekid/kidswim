package com.kite.modules.att.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerCourseDetails;

/**
 * 课程明细DAO接口
 * @author lyb
 * @version 2019-12-08
 */
@MyBatisDao
public interface SerCourseDetailsDao extends CrudDao<SerCourseDetails> {

}
