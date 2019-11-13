/**
 * KITE
 */
package com.kite.modules.sys.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.SysTDataaccessuserDao;
import com.kite.modules.sys.dao.SysTDataaccessuserentityDao;
import com.kite.modules.sys.entity.SysTDataaccessuser;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 多组织架构用户对应数据权限组Service
 * @author lyb
 * @version 2018-10-31
 */
@Service
@Transactional(readOnly = true)
public class SysTDataaccessuserService extends CrudService<SysTDataaccessuserDao, SysTDataaccessuser> {

	@Autowired SysTDataaccessuserDao sysTDataaccessuserDao;
    @Autowired SysTDataaccessuserentityDao sysTDataaccessuserentityDao;
	@Override
	public SysTDataaccessuser get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysTDataaccessuser> findList(SysTDataaccessuser sysTDataaccessuser) {
		return super.findList(sysTDataaccessuser);
	}
	@Override
	public Page<SysTDataaccessuser> findPage(Page<SysTDataaccessuser> page, SysTDataaccessuser sysTDataaccessuser) {
		return super.findPage(page, sysTDataaccessuser);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysTDataaccessuser sysTDataaccessuser) {
		super.save(sysTDataaccessuser);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysTDataaccessuser sysTDataaccessuser) {
		super.delete(sysTDataaccessuser);
	}

		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysTDataaccessuserDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	/**
	 * 获取权限组以下的成员
	 * @param parentId
	 * @return
	 */
	public List<User> findUserListByAccessId(String parentId) {
		List<User> users = new ArrayList<User>();
		List<String> userIds = this.sysTDataaccessuserentityDao.findUseridsByParentId(parentId);
		userIds.forEach(e -> {
			User u = UserUtils.get(e);
			if (u != null) {
				users.add(u);
			}
		});
		return users;
	}



}