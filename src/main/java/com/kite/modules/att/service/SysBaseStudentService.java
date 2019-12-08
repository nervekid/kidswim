/**
 * KITE
 */
package com.kite.modules.att.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.dao.SysBaseStudentDao;
import com.kite.modules.att.entity.SysBaseStudent;

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
		super.deleteByLogic(sysBaseStudent);
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

	/**
	 * 查找当前学生表数量
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	public int findCountOfStudents(Date beginTime, Date endTime) {
		return this.dao.findStudentCount(beginTime, endTime);
	}

	public List<SysBaseStudent> findByName(String subName) {
		return sysBaseStudentDao.findByName(subName);
    }

    public SysBaseStudent getByCode(String studentCode) {
		return sysBaseStudentDao.getByCode(studentCode);
    }
}