package com.kite.modules.att.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerCourseDetails;

/**
 * 課程明細DAO接口
 * @author lyb
 * @version 2019-12-08
 */
@MyBatisDao
public interface SerCourseDetailsDao extends CrudDao<SerCourseDetails> {

	/**
	 * 根據父id查找課程明細
	 * @param courseId
	 * @return
	 */
	public List<SerCourseDetails> findSerCourseDetailsListByCourseId(@Param("courseId") String courseId);
}
