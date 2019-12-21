/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerGroup;

/**
 * 分组DAO接口
 * @author lyb
 * @version 2019-12-19
 */
@MyBatisDao
public interface SerGroupDao extends CrudDao<SerGroup> {

	/**
	 * 根据地址及分组开始时间查找
	 * @param courseAddress
	 * @param groupLearnBeginTime
	 * @return
	 */
	public List<SerGroup> findSerGroupListByAddressAndBeginTime(@Param("courseAddress") String courseAddress,
			@Param("groupLearnBeginTime")String groupLearnBeginTime);

	/**
	 * 根据地址查看分组数量
	 * @param courseAddress
	 * @return
	 */
	public int findSerGroupByAddressNum(@Param("courseAddress") String courseAddress);
}