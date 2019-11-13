/**
 * MouTai
 */
package com.kite.modules.xw_tools.service;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.MessageUtils;
import com.kite.modules.sys.utils.UserUtils;
import com.kite.modules.xw_tools.dao.XwPushmessageDao;
import com.kite.modules.xw_tools.dao.XwPushmessageRecordDao;
import com.kite.modules.xw_tools.entity.XwPushmessage;
import com.kite.modules.xw_tools.entity.XwPushmessageRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 消息推送Service
 * @author czh
 * @version 2017-12-11
 */
@Service
@Transactional(readOnly = true)
public class XwPushmessageService extends CrudService<XwPushmessageDao, XwPushmessage> {

	@Autowired
	private XwPushmessageRecordDao xwPushmessageRecordDao;
	@Override
	public XwPushmessage get(String id) {
		XwPushmessage xwPushmessage = dao.get(id);
		//XwPushmessage xwPushmessage = super.get(id);
		/*XwPushmessageRecord record = new XwPushmessageRecord(xwPushmessage);
		record.setUser(UserUtils.getUser());
		xwPushmessage.setXwPushmessageRecordList(xwPushmessageRecordDao.findList(record));*/
		return xwPushmessage;
	}
	@Override
	public List<XwPushmessage> findList(XwPushmessage xwPushmessage) {
		return super.findList(xwPushmessage);
	}
	@Override
	public Page<XwPushmessage> findPage(Page<XwPushmessage> page, XwPushmessage xwPushmessage) {
		return super.findPage(page, xwPushmessage);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(XwPushmessage xwPushmessage) {
		super.save(xwPushmessage);
		for (XwPushmessageRecord xwPushmessageRecord : xwPushmessage.getXwPushmessageRecordList()){
			if (xwPushmessageRecord.getId() == null){
				continue;
			}
			if (XwPushmessageRecord.DEL_FLAG_NORMAL.equals(xwPushmessageRecord.getDelFlag())){
				if (StringUtils.isBlank(xwPushmessageRecord.getId())){
					xwPushmessageRecord.preInsert();
					xwPushmessageRecordDao.insert(xwPushmessageRecord);
				}else{
					xwPushmessageRecord.preUpdate();
					xwPushmessageRecordDao.update(xwPushmessageRecord);
				}
			}else{
				xwPushmessageRecordDao.delete(xwPushmessageRecord);
			}
		}
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(XwPushmessage xwPushmessage) {
		super.delete(xwPushmessage);
		xwPushmessageRecordDao.delete(new XwPushmessageRecord(xwPushmessage));
	}

	public Page<XwPushmessage> find(Page<XwPushmessage> page, XwPushmessage xwPushmessage) {
		xwPushmessage.setPage(page);
		page.setList(dao.findList(xwPushmessage));
		return page;
	}

	@Transactional(readOnly = false)
	public Page<XwPushmessage> findOne(Page<XwPushmessage> page, XwPushmessage xwPushmessage) {
		xwPushmessage.setPage(page);
		List<XwPushmessage> xwPushmessageList = dao.findListById(xwPushmessage);
		for(XwPushmessage pushmessage:xwPushmessageList){
			MessageUtils.readXwMessage(pushmessage,UserUtils.getUser());
		}
		page.setList(xwPushmessageList);
		return page;
	}

	@Transactional(readOnly = false)
	public XwPushmessage viewMessage(XwPushmessage xwPushmessage) {
		if(xwPushmessage.isSelf()){
			MessageUtils.readXwMessage(xwPushmessage,UserUtils.getUser());
		}
		xwPushmessage.setXwPushmessageRecordList(xwPushmessageRecordDao.findList(new XwPushmessageRecord(xwPushmessage)));
		return xwPushmessage;
	}

	/**
	 * 查阅数据
	 * @param ids
	 */
	@Transactional(readOnly = false)
	public void consultMessage(String ids) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			XwPushmessage xwPushmessage = this.get(id);
			MessageUtils.readXwMessage(xwPushmessage,UserUtils.getUser());
		}
	}

	/**
	 * 保存系统消息
	 * @param params
	 */
	public void generateSysMsg(Map<String, Object> params) {
		XwPushmessage xwPushmessage = new XwPushmessage();
		xwPushmessage.setTitle(String.valueOf(params.get("title")));
		xwPushmessage.setContent(String.valueOf(params.get("content")));
		super.save(xwPushmessage);
		List<String> userIds = (List<String>) params.get("user_lists");
		XwPushmessageRecord xwPushmessageRecord;
		for (String userId : userIds) {
			xwPushmessageRecord = new XwPushmessageRecord();
			xwPushmessageRecord.setPushmeaasgeId(xwPushmessage);
			xwPushmessageRecord.setUser(new User(userId));
			xwPushmessageRecord.setReadFlag("0");
			xwPushmessageRecord.preInsert();
			xwPushmessageRecordDao.insert(xwPushmessageRecord);
		}
	}
}