/**
 * KITE
 */
package com.kite.modules.sys.service;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.SysTTokenDao;
import com.kite.modules.sys.entity.SysTToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * token配置信息Service
 * @author cxh
 * @version 2019-01-24
 */
@Service
@Transactional(readOnly = true)
public class SysTTokenService extends CrudService<SysTTokenDao, SysTToken> {

    @Autowired
	SysTTokenDao sysTTokenDao;
	@Override
	public SysTToken get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysTToken> findList(SysTToken sysTToken) {
		return super.findList(sysTToken);
	}
	@Override
	public Page<SysTToken> findPage(Page<SysTToken> page, SysTToken sysTToken) {
		return super.findPage(page, sysTToken);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysTToken sysTToken) {
		super.save(sysTToken);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysTToken sysTToken) {
		super.delete(sysTToken);
	}
	
	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysTTokenDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}



	public SysTToken getByPidAndSecret(String pid,String secret) {
		return  dao.getByPidAndSecret(pid,secret);
	}

	public SysTToken getByIp(String ip) {
		return  dao.getByIp(ip);
	}


	public  boolean checkPidAndScretAndWorspaceIdExist(SysTToken  sysTToken){
		return dao.checkPidAndScretAndWorspaceIdExist(sysTToken) >0 ;
	}


	public boolean checkPidSecrectAndWorkspaceId(String pid, String secret ,String workspaceid){
		return dao.checkPidSecrectAndWorkspaceId( pid, secret ,workspaceid )>0;
	}

	public SysTToken getByPidSecretAndWorkspaceId(SysTToken sysTToken) {
		return  dao.getByPidSecretAndWorkspaceId( sysTToken );
	}
	
	
}