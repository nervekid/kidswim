/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.command.RpcAllCourseBeginTimeCommand;
import com.kite.modules.att.command.RpcCourseBeginInfo;
import com.kite.modules.att.command.UnGroupLevelCorrespondCountCommand;
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

	/**
	 * 查询未分组人员对应级别情况
	 * @param addressStr
	 * @param learnBeginTimeStr
	 * @param queryBeginDateTime
	 * @param queryEndDateTime
	 * @return
	 */
	public List<UnGroupLevelCorrespondCountCommand> findUnGroupLevelCorrespondCount(
			@Param("addressStr") String addressStr,
			@Param("learnBeginTimeStr") String learnBeginTimeStr,
			@Param("queryBeginDateTime") Date queryBeginDateTime,
			@Param("queryEndDateTime") Date queryEndDateTime);

	/**
	 *
	 * @param address
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public List<RpcAllCourseBeginTimeCommand> findCourseBeginTimeByAddressAndDate(@Param("address")String address,
			@Param("beginDate")Date beginDate, @Param("endDate")Date endDate);

	/**
	 * 根据开始时间，结束时间，查找
	 * @param beginDate
	 * @param endDate
	 * @param address
	 * @return
	 */
	public List<RpcCourseBeginInfo> findRpcCourseBeginInfoByCourseBeginDateAndAddress(@Param("beginDate") Date beginDate,
			@Param("endDate")Date endDate, @Param("address")String address);
}