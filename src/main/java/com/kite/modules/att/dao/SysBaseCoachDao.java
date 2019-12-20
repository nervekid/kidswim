/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SysBaseCoach;

/**
 * 教練員DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SysBaseCoachDao extends CrudDao<SysBaseCoach> {

	/**
	 * 查找已存在的教練數量
	 * @return
	 */
	public int findSysBaseCoachCount();

	/**
	 * 根據
	 * @param code
	 * @return
	 */
	public String findSysBaseCoachIdByCode(@Param("code")String code);

	/**
	 * 根據教練員id獲取教練員編碼
	 * @param id
	 * @return
	 */
	public String findSysBaseCoachByCoachid(@Param("codchId")String id);

	/**
	 * 根据名称查找教练列表
	 * @param coachName
	 * @return
	 */
	public List<SysBaseCoach> findSysBaseCoachListByCoachName(@Param("coachName")String coachName);
}