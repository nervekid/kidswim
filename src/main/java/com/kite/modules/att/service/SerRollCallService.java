/**
 * KITE
 */
package com.kite.modules.att.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.att.entity.SerRollCall;
import com.kite.modules.att.dao.SerRollCallDao;

/**
 * 点名Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SerRollCallService extends CrudService<SerRollCallDao, SerRollCall> {

    @Autowired
	SerRollCallDao serRollCallDao;
	@Override
	public SerRollCall get(String id) {
		return super.get(id);
	}
	@Override
	public List<SerRollCall> findList(SerRollCall serRollCall) {
		return super.findList(serRollCall);
	}
	@Override
	public Page<SerRollCall> findPage(Page<SerRollCall> page, SerRollCall serRollCall) {
		return super.findPage(page, serRollCall);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SerRollCall serRollCall) {
		super.save(serRollCall);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SerRollCall serRollCall) {
		super.delete(serRollCall);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serRollCallDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}