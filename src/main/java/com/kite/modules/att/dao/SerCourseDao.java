/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.List;

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
	 * 根据课程等级及上课地址查找
	 * @param courseLevel
	 * @return
	 */
	public int findCourseByLevelAndAddress(@Param("level") String courseLevel, @Param("address") String courseAddress);

	/**
	 * 根据课程编号查找课程id
	 * @param code
	 * @return
	 */
	public String findCourseIdByCode(@Param("code") String code);

	/**
	 * 根据编号模糊查找
	 * @param code
	 * @return
	 */
	public List<SerCourse> findByLikeCode(@Param("code") String code);
}