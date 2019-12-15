package com.kite.modules.att.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.dao.SerCourseDao;
import com.kite.modules.att.dao.SerCourseDetailsDao;
import com.kite.modules.att.entity.SerCourseDetails;

/**
 * 課程明細Service
 * @author lyb
 * @version 2019-12-08
 */
@Service
@Transactional(readOnly = true)
public class SerCourseDetailsService extends CrudService<SerCourseDetailsDao, SerCourseDetails> {
	@Autowired
	private SerCourseDao serCourseDao;

	@Override
	public SerCourseDetails get(String id) {
		return super.get(id);
	}

	@Override
	public List<SerCourseDetails> findList(SerCourseDetails serCourseDetails) {
		return super.findList(serCourseDetails);
	}

	@Override
	public Page<SerCourseDetails> findPage(Page<SerCourseDetails> page, SerCourseDetails serCourseDetails) {
		return super.findPage(page, serCourseDetails);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SerCourseDetails serCourseDetails) {
		super.save(serCourseDetails);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SerCourseDetails serCourseDetails) {
		super.delete(serCourseDetails);
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
