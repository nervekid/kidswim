/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.command.GroupDetailsInfo;
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

	/**
	 * 根据编号查找
	 * @param code
	 * @return
	 */
	public SerGroup findSerGroupByCode(@Param("code") String code);

	/**
	 * 根据条件查询分组编号列表
	 * @param addressStr
	 * @param learnBeginStr
	 * @param groupBeginDateTime
	 * @param groupEndDateTime
	 * @return
	 */
	public List<GroupDetailsInfo> findCodesByCondition(@Param("addressStr") String addressStr,
			@Param("learnBeginStr") String learnBeginStr,
			@Param("groupBeginDateTime") Date groupBeginDateTime,
			@Param("groupEndDateTime") Date groupEndDateTime);

	/**
     * 根据编号查找分组人数
     * @param code
     * @return
     */
    public int findGroupSaleNum(@Param("code") String code);
}