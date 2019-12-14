/**
 * KITE
 */
package com.kite.modules.att.service;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.dao.SerCourseLevelCostDao;
import com.kite.modules.att.entity.SerCourseLevelCost;

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
		super.deleteByLogic(serCourseLevelCost);
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

	/**
	 * 根据泳池地址及课程等级查找收费
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public BigDecimal findCostAmountByCourseAddressAndCourseLevelFlag(String courseLevelFlag, String courseAddress) {
		return this.dao.findCostAmountByCourseAddressAndCourseLevelFlag(courseLevelFlag, courseAddress);
	}

	/**
	 * 根据泳池地址及课程等级查找收费标准
	 * @param courseLevelFlag
	 * @param courseAddress
	 * @return
	 */
	public String findCostStandardFlagByCourseAddressAndCourseLevelFlag(String courseLevelFlag, String courseAddress) {
		return this.dao.findcostStandardFlagByCourseAddressAndCourseLevelFlag(courseLevelFlag, courseAddress);
	}
}