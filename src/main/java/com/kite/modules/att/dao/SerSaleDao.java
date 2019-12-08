/**
 * KITE
 */
package com.kite.modules.att.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SerSale;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * 销售资料DAO接口
 * @author yyw
 * @version 2019-12-01
 */
@MyBatisDao
public interface SerSaleDao extends CrudDao<SerSale> {


    int findcount(@Param("beginTime")Date beginTime, @Param("endTime")Date endTime);
}