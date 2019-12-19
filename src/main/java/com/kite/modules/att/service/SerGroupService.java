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
import com.kite.modules.att.entity.SerGroup;
import com.kite.modules.att.dao.SerGroupDao;

/**
 * 分组Service
 * @author lyb
 * @version 2019-12-19
 */
@Service
@Transactional(readOnly = true)
public class SerGroupService extends CrudService<SerGroupDao, SerGroup> {

    @Autowired
	SerGroupDao serGroupDao;
	@Override
	public SerGroup get(String id) {
		return super.get(id);
	}
	@Override
	public List<SerGroup> findList(SerGroup serGroup) {
		return super.findList(serGroup);
	}
	@Override
	public Page<SerGroup> findPage(Page<SerGroup> page, SerGroup serGroup) {
		return super.findPage(page, serGroup);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SerGroup serGroup) {
		super.save(serGroup);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SerGroup serGroup) {
		super.delete(serGroup);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serGroupDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}