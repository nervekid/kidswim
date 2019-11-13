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
import com.kite.modules.sys.entity.SysTEntityuseorgan;
import com.kite.modules.sys.dao.SysTEntityuseorganDao;

/**
 * 实体功能对应组织架构Service
 * @author lyb
 * @version 2018-10-26
 */
@Service
@Transactional(readOnly = true)
public class SysTEntityuseorganService extends CrudService<SysTEntityuseorganDao, SysTEntityuseorgan> {

    @Autowired
	SysTEntityuseorganDao sysTEntityuseorganDao;
	@Override
	public SysTEntityuseorgan get(String id) {
		return super.get(id);
	}
	@Override
	public List<SysTEntityuseorgan> findList(SysTEntityuseorgan sysTEntityuseorgan) {
		return super.findList(sysTEntityuseorgan);
	}
	@Override
	public Page<SysTEntityuseorgan> findPage(Page<SysTEntityuseorgan> page, SysTEntityuseorgan sysTEntityuseorgan) {
		return super.findPage(page, sysTEntityuseorgan);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SysTEntityuseorgan sysTEntityuseorgan) {
		super.save(sysTEntityuseorgan);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SysTEntityuseorgan sysTEntityuseorgan) {
		super.delete(sysTEntityuseorgan);
	}

	@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(sysTEntityuseorganDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}

	/**
	 * 数据库表实体是否被重复使用
	 * @param dataTableName
	 * @param dataTableNameCN
	 * @return
	 */
	@Transactional(readOnly = false)
	public boolean isEntityOrganRepeat(String dataTableName) {
		if(this.dao.isEntityRepeat(dataTableName) > 0) {
			return true;
		}
		return false;
	}
}