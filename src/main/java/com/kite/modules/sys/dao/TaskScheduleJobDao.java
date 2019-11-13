/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.TaskScheduleJob;

/**
 * 定时任务DAO接口
 * @author cxh
 * @version 2018-10-11
 */
@MyBatisDao
public interface TaskScheduleJobDao extends CrudDao<TaskScheduleJob> {

	
}