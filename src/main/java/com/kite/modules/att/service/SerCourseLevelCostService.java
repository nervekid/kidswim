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
import com.kite.modules.att.entity.SerCourseLevelCost;
import com.kite.modules.att.dao.SerCourseLevelCostDao;

/**
 * 课程等级对应收费Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SerCourseLevelCostService extends CrudService<SerCourseLevelCostDao, SerCourseLevelCost> {

    @Autowired
	SerCourseLevelCostDao serCourseLevelCostDao;
	@Override
	public SerCourseLevelCost get(String id) {
		return super.get(id);
	}
	@Override
	public List<SerCourseLevelCost> findList(SerCourseLevelCost serCourseLevelCost) {
		return super.findList(serCourseLevelCost);
	}
	@Override
	public Page<SerCourseLevelCost> findPage(Page<SerCourseLevelCost> page, SerCourseLevelCost serCourseLevelCost) {
		return super.findPage(page, serCourseLevelCost);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SerCourseLevelCost serCourseLevelCost) {
		super.save(serCourseLevelCost);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SerCourseLevelCost serCourseLevelCost) {
		super.delete(serCourseLevelCost);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serCourseLevelCostDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}