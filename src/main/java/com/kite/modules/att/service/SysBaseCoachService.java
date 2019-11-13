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
import com.kite.modules.att.entity.SysBaseCoach;
import com.kite.modules.att.dao.SysBaseCoachDao;

/**
 * 教练员Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SysBaseCoachService extends CrudService<SysBaseCoachDao, SysBaseCoach> {

    @Autowired
	SysBaseCoachDao sysBaseCoachDao;
	@Override
	public SysBaseCoach get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysBaseCoach> findList(SysBaseCoach sysBaseCoach) {
		return super.findList(sysBaseCoach);
	}
	@Override
	public Page<SysBaseCoach> findPage(Page<SysBaseCoach> page, SysBaseCoach sysBaseCoach) {
		return super.findPage(page, sysBaseCoach);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysBaseCoach sysBaseCoach) {
		super.save(sysBaseCoach);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysBaseCoach sysBaseCoach) {
		super.delete(sysBaseCoach);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysBaseCoachDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}