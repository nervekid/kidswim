/**
 * KITE
 */
package com.kite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.entity.SysTDataaccess;
import com.kite.modules.sys.dao.SysTDataaccessDao;


import com.kite.modules.sys.entity.SysTDataaccessentity;
import com.kite.modules.sys.dao.SysTDataaccessentityDao;

/**
 * 多组织架构数据权限Service
 * @author lyb
 * @version 2018-10-30
 */
@Service
@Transactional(readOnly = true)
public class SysTDataaccessService extends CrudService<SysTDataaccessDao, SysTDataaccess> {

	@Autowired
	private SysTDataaccessentityDao sysTDataaccessentityDao;
	@Override
	public SysTDataaccess get(String id) {
		SysTDataaccess sysTDataaccess = super.get(id);
		sysTDataaccess.setSysTDataaccessentityList(sysTDataaccessentityDao.findList(new SysTDataaccessentity(sysTDataaccess)));
		return sysTDataaccess;
	}
	@Override
	public List<SysTDataaccess> findList(SysTDataaccess sysTDataaccess) {
		return super.findList(sysTDataaccess);
	}
	@Override
	public Page<SysTDataaccess> findPage(Page<SysTDataaccess> page, SysTDataaccess sysTDataaccess) {
		return super.findPage(page, sysTDataaccess);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysTDataaccess sysTDataaccess) {
		super.save(sysTDataaccess);
		for (SysTDataaccessentity sysTDataaccessentity : sysTDataaccess.getSysTDataaccessentityList()){
			if (sysTDataaccessentity.getId() == null){
				continue;
			}
			if (SysTDataaccessentity.DEL_FLAG_NORMAL.equals(sysTDataaccessentity.getDelFlag())){
				if (StringUtils.isBlank(sysTDataaccessentity.getId())){
					sysTDataaccessentity.preInsert();
					sysTDataaccessentity.setParentId(sysTDataaccess.getId());
					sysTDataaccessentityDao.insert(sysTDataaccessentity);
				}else{
					sysTDataaccessentity.preUpdate();
					sysTDataaccessentity.setParentId(sysTDataaccess.getId());
					sysTDataaccessentityDao.update(sysTDataaccessentity);
				}
			}else{
				sysTDataaccessentityDao.delete(sysTDataaccessentity);
			}
		}
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysTDataaccess sysTDataaccess) {
		super.delete(sysTDataaccess);
		sysTDataaccessentityDao.delete(new SysTDataaccessentity(sysTDataaccess));
	}


}