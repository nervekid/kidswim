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
import com.kite.modules.sys.entity.SessionSituation;
import com.kite.modules.sys.dao.SessionSituationDao;

/**
 * 系统会话Service
 * @author lyb
 * @version 2018-03-15
 */
@Service
@Transactional(readOnly = true)
public class SessionSituationService extends CrudService<SessionSituationDao, SessionSituation> {

    @Autowired
	SessionSituationDao sessionSituationDao;

    public SessionSituation findBySessionId(String sId) {
		return this.sessionSituationDao.findBySessionId(sId);
	}
	@Override
	public SessionSituation get(String id) {
		return super.get(id);
	}
	@Override
	public List<SessionSituation> findList(SessionSituation sessionSituation) {
		return super.findList(sessionSituation);
	}
	@Override
	public Page<SessionSituation> findPage(Page<SessionSituation> page, SessionSituation sessionSituation) {
		return super.findPage(page, sessionSituation);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SessionSituation sessionSituation) {
		super.save(sessionSituation);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SessionSituation sessionSituation) {
		super.delete(sessionSituation);
	}

		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sessionSituationDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}






}