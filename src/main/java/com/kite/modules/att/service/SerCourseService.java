/**
 * KITE
 */
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
import com.kite.modules.att.entity.SerCourse;
import com.kite.modules.att.entity.SerCourseDetails;

/**
 * 课程Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SerCourseService extends CrudService<SerCourseDao, SerCourse> {

    @Autowired
	private SerCourseDao serCourseDao;
    @Autowired
	private SerCourseDetailsDao serCourseDetailsDao;

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

	/**
	 * 根据地址及课程等级查找当前表格的数量
	 * @param level
	 * @param address
	 * @return
	 */
	public int findCountByLevelAndAddress(String level, String address) {
		return this.dao.findCourseByLevelAndAddress(level, address);
	}

	/**
	 * 根据课程编号查找课程id
	 * @param code
	 * @return
	 */
	public String findCourseIdByCode(String code) {
		return this.dao.findCourseIdByCode(code);
	}

	/**
	 * 根据课程父id查找课程明细
	 * @param courseId
	 * @return
	 */
	public List<SerCourseDetails> findSerCourseDetailsByCourseId(String courseId) {
		return this.serCourseDetailsDao.findSerCourseDetailsListByCourseId(courseId);
	}

	/**
	 * 根据编号查找
	 * @param code
	 * @return
	 */
	public List<SerCourse> findLikeByCode(String code) {
		return this.dao.findByLikeCode(code);
	}

}