/**
 * KITE
 */
package com.kite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.sys.entity.SysOrganizationalApproval;
import com.kite.modules.sys.dao.SysOrganizationalApprovalDao;

/**
 * 组织架构职级表Service
 * @author lyb
 * @version 2019-08-19
 */
@Service
@Transactional(readOnly = true)
public class SysOrganizationalApprovalService extends CrudService<SysOrganizationalApprovalDao, SysOrganizationalApproval> {

    @Autowired
	SysOrganizationalApprovalDao sysOrganizationalApprovalDao;
	@Override
	public SysOrganizationalApproval get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysOrganizationalApproval> findList(SysOrganizationalApproval sysOrganizationalApproval) {
		return super.findList(sysOrganizationalApproval);
	}
	@Override
	public Page<SysOrganizationalApproval> findPage(Page<SysOrganizationalApproval> page, SysOrganizationalApproval sysOrganizationalApproval) {
		return super.findPage(page, sysOrganizationalApproval);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysOrganizationalApproval sysOrganizationalApproval) {
		super.save(sysOrganizationalApproval);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysOrganizationalApproval sysOrganizationalApproval) {
		super.delete(sysOrganizationalApproval);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysOrganizationalApprovalDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}