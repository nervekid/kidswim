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
 * 課程DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SerCourseDao extends CrudDao<SerCourse> {

	/**
	 * 根據課程等級及上課地址查找
	 * @param courseLevel
	 * @return
	 */
	public int findCourseByLevelAndAddress(@Param("level") String courseLevel, @Param("address") String courseAddress);

	/**
	 * 根據課程編號查找課程id
	 * @param code
	 * @return
	 */
	public String findCourseIdByCode(@Param("code") String code);

	/**
	 * 根據編號模糊查找
	 * @param code
	 * @return
	 */
	public List<SerCourse> findByLikeCode(@Param("code") String code);

	/**
	 * 根據編號唯壹查找
	 * @param code
	 * @return
	 */
	public SerCourse findByLikeCodeOnly (@Param("code") String code);
}