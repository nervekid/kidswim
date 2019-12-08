/**
 * KITE
 */
package com.kite.modules.att.dao;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerCourse;

/**
 * 课程DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SerCourseDao extends CrudDao<SerCourse> {

	/**
	 * 根据课程等级查找
	 * @param courseLevel
	 * @return
	 */
	public int findCourseByLevel(@Param("level") String courseLevel);

}