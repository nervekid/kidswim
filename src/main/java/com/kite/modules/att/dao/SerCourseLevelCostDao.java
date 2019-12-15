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
 * 課程等級對應收費DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SerCourseLevelCostDao extends CrudDao<SerCourseLevelCost> {

	/**
	 * 查找對應等級與泳池的收費
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public BigDecimal findCostAmountByCourseAddressAndCourseLevelFlag(@Param("courseLevelFlag")String courseLevelFlag, @Param("courseAddress")String courseAddress);

	/**
	 * 找對應等級與泳池的收費標準
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public String findcostStandardFlagByCourseAddressAndCourseLevelFlag(@Param("courseLevelFlag")String courseLevelFlag, @Param("courseAddress")String courseAddress);

	/**
	 * 根據對應等級與泳池的收費標準
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public SerCourseLevelCost findSerCourseLevelCostByCourseAddressAndCourseLevelFlag(@Param("courseLevelFlag")String courseLevelFlag, @Param("courseAddress")String courseAddress);
}