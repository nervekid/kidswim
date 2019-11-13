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
import com.kite.modules.sys.entity.SysTDataaccessuserentity;
import com.kite.modules.sys.dao.SysTDataaccessuserentityDao;

/**
 * 多组织架构用户对应数据权限组分录Service
 * @author lyb
 * @version 2018-11-12
 */
@Service
@Transactional(readOnly = true)
public class SysTDataaccessuserentityService extends CrudService<SysTDataaccessuserentityDao, SysTDataaccessuserentity> {

    @Autowired
	SysTDataaccessuserentityDao sysTDataaccessuserentityDao;
	@Override
	public SysTDataaccessuserentity get(String id) {
		return super.get(id);
	}

	public List<String> findAccessIdByUserId(String userId) {
		return this.sysTDataaccessuserentityDao.findAccessIdByUserId(userId);
	}
	@Override
	public List<SysTDataaccessuserentity> findList(SysTDataaccessuserentity sysTDataaccessuserentity) {
		return super.findList(sysTDataaccessuserentity);
	}
	@Override
	public Page<SysTDataaccessuserentity> findPage(Page<SysTDataaccessuserentity> page, SysTDataaccessuserentity sysTDataaccessuserentity) {
		return super.findPage(page, sysTDataaccessuserentity);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysTDataaccessuserentity sysTDataaccessuserentity) {
		super.save(sysTDataaccessuserentity);
	}

	@Transactional(readOnly = false)
	public void deleteByUserId(String parentId, String userId) {
		this.dao.deleteByUser(parentId, userId);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysTDataaccessuserentity sysTDataaccessuserentity) {
		super.delete(sysTDataaccessuserentity);
	}

		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysTDataaccessuserentityDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	public boolean isUserExited(String parentId, String userId) {
		if (this.sysTDataaccessuserentityDao.findIsExitByPraentIdAndUserId(parentId, userId) > 0) {
			return true;
		}
		return false;
	}

}