/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.command.RpcRollCallShowCommand;
import com.kite.modules.att.command.RpcSaleStudentCommand;
import com.kite.modules.att.entity.SerSale;

/**
 * 銷售資料DAO接口
 * @author yyw
 * @version 2019-12-01
 */
@MyBatisDao
public interface SerSaleDao extends CrudDao<SerSale> {

	/**
	 * 查看销售单数量
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
    public int findcount(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);

    /**
     * 根据课程地址同课程开始时间查询销售单学员信息
     * @param courseAddress
     * @param learnBeginTime
     * @param beginDate
     * @param endDate
     * @return
     */
    public List<RpcSaleStudentCommand> findRpcSaleStudentCommandByAddressAndBeginTime(
    		@Param("courseAddress")String courseAddress,
    		@Param("courseLavel")String courseLavel,
    		@Param("learnBeginTime")String learnBeginTime,
    		@Param("queryBeginDate")Date queryBeginDate,
    		@Param("queryEndDate")Date queryEndDate);


    /**
     * 更改分组状态
     * @param groupId
     */
    public void updateGroupStatus(@Param("saleId") String saleId, @Param("status") String status);

    /**
     * 根据条件查找点名原型
     * @param address 地址
     * @param beginDateStr 开始日期字符串
     * @param endDateStr 结束日期字符串 yp0920930
     * @param beginTimeStr 开始时间字符串
     * @param endTimeStr 结束时间字符串
     * @return
     */
    public List<RpcRollCallShowCommand> findRpcRollCallShowCommandByCondition(@Param("address")String address,
    		@Param("beginDateStr")Date beginDateStr, @Param("endDateStr")Date endDateStr,
    		@Param("beginTime")Date beginTime);

}