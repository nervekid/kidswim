/**
 * KITE
 */
package com.kite.modules.att.dao;

import javax.websocket.server.PathParam;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SysBaseCoach;

/**
 * 教练员DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SysBaseCoachDao extends CrudDao<SysBaseCoach> {

	/**
	 * 查找已存在的教练数量
	 * @return
	 */
	public int findSysBaseCoachCount();

	/**
	 * 根据
	 * @param code
	 * @return
	 */
	public String findSysBaseCoachIdByCode(@Param("code")String code);
}