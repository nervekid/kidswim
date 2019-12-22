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

	/**
	 * 根据课程地址及分组开始时间查找分组列表
	 * @param courseAddress
	 * @param groupLearnBeginTime
	 * @return
	 */
	public List<SerGroup> findSerGroupListByAddressAndBeginTime(String courseAddress, String groupLearnBeginTime) {
		return this.dao.findSerGroupListByAddressAndBeginTime(courseAddress, groupLearnBeginTime);
	}

	/**
	 * 根据课程地址查看分组数量
	 * @param courseAddress
	 * @return
	 */
	public int findSerGroupByAddressNum(String courseAddress) {
		return this.dao.findSerGroupByAddressNum(courseAddress);
	}

	/**
	 * 根据编号查找分组
	 * @param code
	 * @return
	 */
	public SerGroup findSerGroupByCode(String code) {
		return this.dao.findSerGroupByCode(code);
	}
}