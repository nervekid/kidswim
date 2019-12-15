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
import com.kite.modules.att.dao.SysBaseCoachDao;
import com.kite.modules.att.dao.SysCertificatesCoachDao;
import com.kite.modules.att.entity.SysBaseCoach;
import com.kite.modules.att.entity.SysCertificatesCoach;

/**
 * 教練員Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SysBaseCoachService extends CrudService<SysBaseCoachDao, SysBaseCoach> {

    @Autowired
	private SysBaseCoachDao sysBaseCoachDao;
    @Autowired
    private SysCertificatesCoachDao sysCertificatesCoachDao;
    @Autowired
    private SysCertificatesCoachService sysCertificatesCoachService;

    @Override
	public SysBaseCoach get(String id) {
		return super.get(id);
	}

	@Override
	public List<SysBaseCoach> findList(SysBaseCoach sysBaseCoach) {
		return super.findList(sysBaseCoach);
	}

	@Override
	public Page<SysBaseCoach> findPage(Page<SysBaseCoach> page, SysBaseCoach sysBaseCoach) {

		Page<SysBaseCoach> pageSysBaseCoach =  super.findPage(page, sysBaseCoach);
		List<SysBaseCoach> sysBaseCoachList = pageSysBaseCoach.getList();

		//添加證書列表
		for (int i = 0; i < sysBaseCoachList.size(); i++) {
			List<SysCertificatesCoach> sysCertificatesCoachList = this.sysCertificatesCoachDao.findSysCertificatesCoachListByCoachId(sysBaseCoachList.get(i).getId());
			sysBaseCoachList.get(i).setSysCertificatesCoachList(sysCertificatesCoachList);
		}
		pageSysBaseCoach.setList(sysBaseCoachList);
		return pageSysBaseCoach;
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SysBaseCoach sysBaseCoach) {
		//修改教練資格表
		super.save(sysBaseCoach);

		//查找coachId
		String coachId = this.dao.findSysBaseCoachIdByCode(sysBaseCoach.getCode());
		//修改年月
		List<SysCertificatesCoach> sysCertificatesCoachList = sysBaseCoach.getSysCertificatesCoachList();
		for (int i = 0; i < sysCertificatesCoachList.size(); i++) {
			if (sysCertificatesCoachList.get(i).getId() != null && !sysCertificatesCoachList.get(i).getId().equals("")) {
				SysCertificatesCoach sysCertificatesCoach = this.sysCertificatesCoachService.get(sysCertificatesCoachList.get(i).getId());
				if (sysCertificatesCoachList.get(i).getObtainYearMonth() != null && !sysCertificatesCoachList.get(i).getObtainYearMonth().equals("")) {
					String yearMonth = sysCertificatesCoachList.get(i).getObtainYearMonth();
					yearMonth = yearMonth.replace("-", "");
					sysCertificatesCoach.setObtainYearMonth(yearMonth);
				}
				sysCertificatesCoach.setCoathId(coachId);
				sysCertificatesCoach.setQualification(sysCertificatesCoachList.get(i).getQualification());
				sysCertificatesCoach.setDelFlag(sysCertificatesCoachList.get(i).getDelFlag());
				this.sysCertificatesCoachService.save(sysCertificatesCoach);
			}
			else {
				SysCertificatesCoach sysCertificatesCoach = new SysCertificatesCoach();
				if (sysCertificatesCoachList.get(i).getObtainYearMonth() != null && !sysCertificatesCoachList.get(i).getObtainYearMonth().equals("")) {
					String yearMonth = sysCertificatesCoachList.get(i).getObtainYearMonth();
					yearMonth = yearMonth.replace("-", "");
					sysCertificatesCoach.setObtainYearMonth(yearMonth);
				}
				sysCertificatesCoach.setCoathId(coachId);
				sysCertificatesCoach.setQualification(sysCertificatesCoachList.get(i).getQualification());
				sysCertificatesCoach.setDelFlag(sysCertificatesCoachList.get(i).getDelFlag());
				this.sysCertificatesCoachService.save(sysCertificatesCoach);
			}
		}

	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SysBaseCoach sysBaseCoach) {
		//刪除分錄
		this.sysCertificatesCoachDao.deleteSysCertificatesCoachByCoachId(sysBaseCoach.getId());
		super.deleteByLogic(sysBaseCoach);
	}

	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysBaseCoachDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	/**
	 * 查找已存在的教練員數量
	 */
	public int findExitSysBaseCoachNum() {
		return this.dao.findSysBaseCoachCount();
	}

	/**
	 * 根據教練id查找教練編碼
	 * @param id
	 * @return
	 */
	public String findCoachCodeByCoachId(String id) {
		return this.dao.findSysBaseCoachByCoachid(id);
	}
}