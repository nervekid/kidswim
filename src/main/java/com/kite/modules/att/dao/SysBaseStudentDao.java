/**
 * KITE2
 */
package com.kite.modules.att.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SysBaseStudent;

/**
 * 學員DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SysBaseStudentDao extends CrudDao<SysBaseStudent> {

	/**
	 * 查找學生表當前數量
	 * @return
	 */
	public int findStudentCount(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);

	List<SysBaseStudent> findByName(@Param("name")String subName);

    SysBaseStudent getByCode(@Param("code")String studentCode);
}