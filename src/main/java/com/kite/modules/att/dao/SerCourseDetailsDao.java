package com.kite.modules.att.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

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

	/**
	 * 根据父id查找课程明细
	 * @param courseId
	 * @return
	 */
	public List<SerCourseDetails> findSerCourseDetailsListByCourseId(@Param("courseId") String courseId);
}
