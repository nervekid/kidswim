/**
 * KITE
 */
package com.kite.modules.sys.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.file.service.FileFastdfsService;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.dao.SysOrganizationalDao;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.SysOrganizational;
import com.kite.modules.sys.entity.SysOrganizationalApproval;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 系统组织架构Service
 * @author lyb
 * @version 2018-10-18
 */
@Service
@Transactional(readOnly = true)
public class SysOrganizationalService extends CrudService<SysOrganizationalDao, SysOrganizational> {

    @Autowired private SysOrganizationalDao sysOrganizationalDao;

	@Autowired private OfficeService officeService;

	@Autowired private OfficeDao officeDao;

	@Autowired private SysOrganizationalApprovalService sysOrganizationalApprovalService;

	@Autowired private FileFastdfsService fileFastdfsService;

	@Autowired private SystemService systemService;

	@Override
	public SysOrganizational get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysOrganizational> findList(SysOrganizational sysOrganizational) {
		return super.findList(sysOrganizational);
	}
	@Override
	public Page<SysOrganizational> findPage(Page<SysOrganizational> page, SysOrganizational sysOrganizational) {
		if (sysOrganizational.getOrganTag() == null || sysOrganizational.getOrganTag().equals("")) {
			sysOrganizational.setOrganTag("0");
		}
		dataScopeFilter(sysOrganizational,UserUtils.getUser(),"dfs","office.id");
		return super.findPage(page, sysOrganizational);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(SysOrganizational sysOrganizational) {
		boolean flag = false;
		if(StringUtils.isEmpty(sysOrganizational.getId())){
			flag = true;
		}
		super.save(sysOrganizational);
		String orgId = super.saveAndReturnId(sysOrganizational);
		//企业微信，邮箱同步功能
		User user = sysOrganizational.getUser();

		//增加与组织架构职级表的关系
		SysOrganizationalApproval sysOrganizationalApproval = new SysOrganizationalApproval();
		if (sysOrganizational.getRankFlag() != null || !"".equals(sysOrganizational.getRankFlag())) {
			sysOrganizationalApproval.setRankFlag(sysOrganizational.getRankFlag());
		}
		sysOrganizationalApproval.setOrgId(orgId);
		sysOrganizationalApproval.setSuperiorFlag(sysOrganizational.getSuperiorFlag());
		this.sysOrganizationalApprovalService.save(sysOrganizationalApproval);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(SysOrganizational sysOrganizational) {
		//企业微信，邮箱同步功能
		super.delete(sysOrganizational);
	}

		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysOrganizationalDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}


	public List<SysOrganizational> findOfficeIdByUserIdAndOrganTag(String userId,String organTag){
		return dao.findOfficeIdByUserIdAndOrganTag(userId,organTag);
	}

	public SysOrganizational findOfficeIdByUserIdAndOrganTagOne(String userId,String organTag){
		return dao.findOfficeIdByUserIdAndOrganTagOne(userId,organTag);
	}

	@Transactional(readOnly = false)
	public void changeUserId(User user,String delflag){
		sysOrganizationalDao.deleteByUserId(user.getId(),delflag);
	}

	@Transactional(readOnly = false)
	public String findOfficeNameByOfficeOneNameAndOrganTag(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysOrganizationalDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	public Office getOfficeOneIByUserIddAndOrganTag(String userId,String organTay){
		return officeService.get((sysOrganizationalDao.findOfficeOneIdByUserIdAndOrganTag(userId,organTay)));
	}

	/**
	 * 指定日期未来指定月数的日期
	 * @param thatDay
	 * @param num
	 * @return
	 */
	public static Date getPreMonthDate(Date thatDay, int num) {
        Calendar c = Calendar.getInstance();
        c.setTime(thatDay);
        c.add(Calendar.MONTH, +num);
        Date d = c.getTime();
        return d;
    }

	/*
     * 比较两个时间点相差多少年。
     */
    private static int dayComparePrecise(Date fromDate,Date toDate){
    	if(fromDate==null || toDate==null){
    		return 0;
		}
        Calendar  from  =  Calendar.getInstance();
        from.setTime(fromDate);
        Calendar  to  =  Calendar.getInstance();
        to.setTime(toDate);

        int fromYear = from.get(Calendar.YEAR);

        int toYear = to.get(Calendar.YEAR);
        int year = toYear  -  fromYear;
        return year;
    }

}