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
import com.kite.modules.att.entity.SysCertificatesCoach;
import com.kite.modules.att.dao.SysCertificatesCoachDao;

/**
 * 教練員資格Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SysCertificatesCoachService extends CrudService<SysCertificatesCoachDao, SysCertificatesCoach> {

    @Autowired
	private SysCertificatesCoachDao sysCertificatesCoachDao;

	@Override
	public SysCertificatesCoach get(String id) {
		return super.get(id);
	}

	@Override
	public List<SysCertificatesCoach> findList(SysCertificatesCoach sysCertificatesCoach) {
		return super.findList(sysCertificatesCoach);
	}

	@Override
	public Page<SysCertificatesCoach> findPage(Page<SysCertificatesCoach> page, SysCertificatesCoach sysCertificatesCoach) {
		return super.findPage(page, sysCertificatesCoach);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SysCertificatesCoach sysCertificatesCoach) {
		super.save(sysCertificatesCoach);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SysCertificatesCoach sysCertificatesCoach) {
		super.delete(sysCertificatesCoach);
	}

	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysCertificatesCoachDao.findCodeNumber(tablename, codename, beginString))));
		return serial.toString();
	}

	/**
	 * 根據教練ID查找教練資料列表
	 * @param coachId
	 * @return
	 */
	public List<SysCertificatesCoach> findSysCertificatesCoachListByCoachId(String coachId) {
		return this.dao.findSysCertificatesCoachListByCoachId(coachId);
	}
}