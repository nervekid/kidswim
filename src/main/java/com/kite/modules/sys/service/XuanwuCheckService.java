/**
 * MouTai
 */
package com.kite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.sys.entity.XuanwuCheck;
import com.kite.modules.sys.dao.XuanwuCheckDao;

/**
 * xuanwuService
 * @author cxh
 * @version 2017-12-27
 */
@Service
@Transactional(readOnly = true)
public class XuanwuCheckService extends CrudService<XuanwuCheckDao, XuanwuCheck> {

    @Autowired
	XuanwuCheckDao xuanwuCheckDao;
	@Override
	public XuanwuCheck get(String id) {
		return super.get(id);
	}
	@Override
	public List<XuanwuCheck> findList(XuanwuCheck xuanwuCheck) {
		return super.findList(xuanwuCheck);
	}
	@Override
	public Page<XuanwuCheck> findPage(Page<XuanwuCheck> page, XuanwuCheck xuanwuCheck) {
		return super.findPage(page, xuanwuCheck);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(XuanwuCheck xuanwuCheck) {
		super.save(xuanwuCheck);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(XuanwuCheck xuanwuCheck) {
		super.delete(xuanwuCheck);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(xuanwuCheckDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}