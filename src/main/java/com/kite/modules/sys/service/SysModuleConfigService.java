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
import com.kite.modules.sys.entity.SysModuleConfig;
import com.kite.modules.sys.dao.SysModuleConfigDao;

/**
 * 系统模块配置Service
 * @author cxh
 * @version 2019-04-29
 */
@Service
@Transactional(readOnly = true)
public class SysModuleConfigService extends CrudService<SysModuleConfigDao, SysModuleConfig> {

    @Autowired
	SysModuleConfigDao sysModuleConfigDao;
	@Override
	public SysModuleConfig get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysModuleConfig> findList(SysModuleConfig sysModuleConfig) {
		return super.findList(sysModuleConfig);
	}
	@Override
	public Page<SysModuleConfig> findPage(Page<SysModuleConfig> page, SysModuleConfig sysModuleConfig) {
		return super.findPage(page, sysModuleConfig);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysModuleConfig sysModuleConfig) {
		super.save(sysModuleConfig);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysModuleConfig sysModuleConfig) {
		super.delete(sysModuleConfig);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysModuleConfigDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}