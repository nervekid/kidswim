/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.service;

import com.kite.modules.sys.entity.LogOfObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.DateUtils;
import com.kite.modules.sys.dao.LogDao;
import com.kite.modules.sys.entity.Log;

/**
 * 日志Service
 * @author kite
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class LogService extends CrudService<LogDao, Log> {

	@Autowired
	private LogDao logDao;
	@Override
	public Page<Log> findPage(Page<Log> page, Log log) {
		
		// 设置默认时间范围，默认当前月
		if (log.getBeginDate() == null){
			log.setBeginDate(DateUtils.setDays(DateUtils.parseDate(DateUtils.getDate()), 1));
		}
		if (log.getEndDate() == null){
			log.setEndDate(DateUtils.addMonths(log.getBeginDate(), 1));
		}
		
		return super.findPage(page, log);
		
	}
	
	/**
	 * 删除全部数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void empty(){
		
		logDao.empty();
	}

	/**
	 * 保存对象修改前后的实体类型
	 * @param logOfObject
	 */
	@Transactional(readOnly = false)
	public void saveObjectChange(LogOfObject logOfObject){
		logDao.saveChangeOfObeject(logOfObject);
	}


}
