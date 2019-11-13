/**
 * KITE
 */
package com.kite.modules.sys.service;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.MessageRecordEmailDao;
import com.kite.modules.sys.entity.MessageRecordEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 邮件发送情况记录Service
 * @author wusida
 * @version 2019-10-24
 */
@Service
@Transactional(readOnly = true)
public class MessageRecordEmailService extends CrudService<MessageRecordEmailDao, MessageRecordEmail> {

    @Autowired
	MessageRecordEmailDao messageRecordEmailDao;
	@Override
	public MessageRecordEmail get(String id) {
		return super.get(id);
	}
	@Override
	public List<MessageRecordEmail> findList(MessageRecordEmail messageRecordEmail) {
		return super.findList(messageRecordEmail);
	}
	@Override
	public Page<MessageRecordEmail> findPage(Page<MessageRecordEmail> page, MessageRecordEmail messageRecordEmail) {
		return super.findPage(page, messageRecordEmail);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(MessageRecordEmail messageRecordEmail) {
		super.save(messageRecordEmail);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(MessageRecordEmail messageRecordEmail) {
		super.delete(messageRecordEmail);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(messageRecordEmailDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}

	@Transactional(readOnly = false)
	public void updateMessageInfo(MessageRecordEmail messageRecordEmail) {
		messageRecordEmailDao.updateMessageInfo(messageRecordEmail);
	}
	
	
	
	
}