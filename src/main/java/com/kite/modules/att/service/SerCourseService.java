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
 * 課程Service
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
		List<SerCourseDetails> serCourseDetailsList = this.serCourseDetailsDao.findSerCourseDetailsListByCourseId(serCourse.getId());
		serCourseDetailsList.forEach(e -> {
			this.serCourseDetailsDao.deleteByLogic(e);
		});
		super.deleteByLogic(serCourse);
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
	 * 根據地址及課程等級查找當前表格的數量
	 * @param level
	 * @param address
	 * @return
	 */
	public int findCountByLevelAndAddress(String level, String address) {
		return this.dao.findCourseByLevelAndAddress(level, address);
	}

	/**
	 * 根據課程編號查找課程id
	 * @param code
	 * @return
	 */
	public String findCourseIdByCode(String code) {
		return this.dao.findCourseIdByCode(code);
	}

	/**
	 * 根據課程父id查找課程明細
	 * @param courseId
	 * @return
	 */
	public List<SerCourseDetails> findSerCourseDetailsByCourseId(String courseId) {
		return this.serCourseDetailsDao.findSerCourseDetailsListByCourseId(courseId);
	}

	/**
	 * 根據編號查找
	 * @param code
	 * @return
	 */
	public List<SerCourse> findLikeByCode(String code) {
		return this.dao.findByLikeCode(code);
	}

	/**
	 * 根據編號唯壹查找
	 * @param code
	 * @return
	 */
	public SerCourse findSerCourseByCode(String code) {
		return this.dao.findByLikeCodeOnly(code);
	}

}