/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerSale;

/**
 * 销售资料DAO接口
 * @author yyw
 * @version 2019-12-01
 */
@MyBatisDao
public interface SerSaleDao extends CrudDao<SerSale> {


    int findcount(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
}