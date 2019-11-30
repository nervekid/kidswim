/**
 * KITE2
 */
package com.kite.modules.att.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SysBaseStudent;

/**
 * 学员DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SysBaseStudentDao extends CrudDao<SysBaseStudent> {

	/**
	 * 查找学生表当前数量
	 * @return
	 */
	public int findStudentCount(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
}