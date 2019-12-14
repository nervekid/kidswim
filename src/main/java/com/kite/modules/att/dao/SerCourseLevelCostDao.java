/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.math.BigDecimal;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerCourseLevelCost;

/**
 * 课程等级对应收费DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SerCourseLevelCostDao extends CrudDao<SerCourseLevelCost> {

	/**
	 * 查找对应等级与泳池的收费
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public BigDecimal findCostAmountByCourseAddressAndCourseLevelFlag(@Param("courseLevelFlag")String courseLevelFlag, @Param("courseAddress")String courseAddress);

	/**
	 * 找对应等级与泳池的收费标准
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public String findcostStandardFlagByCourseAddressAndCourseLevelFlag(@Param("courseLevelFlag")String courseLevelFlag, @Param("courseAddress")String courseAddress);
}