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
import com.kite.modules.att.entity.SerCourse;
import com.kite.modules.att.dao.SerCourseDao;

/**
 * 课程Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SerCourseService extends CrudService<SerCourseDao, SerCourse> {

    @Autowired
	SerCourseDao serCourseDao;
	@Override
	public SerCourse get(String id) {
		return super.get(id);
	}
	@Override
	public List<SerCourse> findList(SerCourse serCourse) {
		return super.findList(serCourse);
	}
	@Override
	public Page<SerCourse> findPage(Page<SerCourse> page, SerCourse serCourse) {
		return super.findPage(page, serCourse);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SerCourse serCourse) {
		super.save(serCourse);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SerCourse serCourse) {
		super.delete(serCourse);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serCourseDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}