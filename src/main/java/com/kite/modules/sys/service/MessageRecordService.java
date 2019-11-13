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
import com.kite.modules.sys.entity.MessageRecord;
import com.kite.modules.sys.dao.MessageRecordDao;

/**
 * 用户接收短信情况记录Service
 * @author zhangtao
 * @version 2019-05-13
 */
@Service
@Transactional(readOnly = true)
public class MessageRecordService extends CrudService<MessageRecordDao, MessageRecord> {

    @Autowired
	MessageRecordDao messageRecordDao;
	@Override
	public MessageRecord get(String id) {
		return super.get(id);
	}
	@Override
	public List<MessageRecord> findList(MessageRecord messageRecord) {
		return super.findList(messageRecord);
	}

	@Override
	public Page<MessageRecord> findPage(Page<MessageRecord> page, MessageRecord messageRecord) {
		return super.findPage(page, messageRecord);
	}

	@Override
	@Transactional(readOnly = false)
	public void save(MessageRecord messageRecord) {
		super.save(messageRecord);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(MessageRecord messageRecord) {
		super.delete(messageRecord);
	}
	
	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(messageRecordDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}


	@Transactional(readOnly = false)
	public void updateMessageInfo(MessageRecord messageRecord) {
		messageRecordDao.updateMessageInfo(messageRecord);
	}
	
	
}