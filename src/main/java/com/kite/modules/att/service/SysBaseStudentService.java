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
import com.kite.modules.att.entity.SysBaseStudent;
import com.kite.modules.att.dao.SysBaseStudentDao;

/**
 * 学员Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SysBaseStudentService extends CrudService<SysBaseStudentDao, SysBaseStudent> {

    @Autowired
	SysBaseStudentDao sysBaseStudentDao;
	@Override
	public SysBaseStudent get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysBaseStudent> findList(SysBaseStudent sysBaseStudent) {
		return super.findList(sysBaseStudent);
	}
	@Override
	public Page<SysBaseStudent> findPage(Page<SysBaseStudent> page, SysBaseStudent sysBaseStudent) {
		return super.findPage(page, sysBaseStudent);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysBaseStudent sysBaseStudent) {
		super.save(sysBaseStudent);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysBaseStudent sysBaseStudent) {
		super.delete(sysBaseStudent);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysBaseStudentDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}