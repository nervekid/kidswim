/**
 * KITE
 */
package com.kite.modules.att.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.dao.SerGroupDetailsDao;
import com.kite.modules.att.entity.SerGroupDetails;
import com.kite.modules.att.enums.KidSwimDictEnum;

/**
 * 分组明细Service
 * @author lyb
 * @version 2019-12-19
 */
@Service
@Transactional(readOnly = true)
public class SerGroupDetailsService extends CrudService<SerGroupDetailsDao, SerGroupDetails> {

    @Autowired
	SerGroupDetailsDao serGroupDetailsDao;

    @Autowired
    SerSaleService serSaleService;

	@Override
	public SerGroupDetails get(String id) {
		return super.get(id);
	}
	@Override
	public List<SerGroupDetails> findList(SerGroupDetails serGroupDetails) {
		return super.findList(serGroupDetails);
	}
	@Override
	public Page<SerGroupDetails> findPage(Page<SerGroupDetails> page, SerGroupDetails serGroupDetails) {
		return super.findPage(page, serGroupDetails);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SerGroupDetails serGroupDetails) {
		super.save(serGroupDetails);
		this.serSaleService.updateSetGroupFlagStatus(serGroupDetails.getSaleId(), KidSwimDictEnum.yesNo.是.getName());
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SerGroupDetails serGroupDetails) {
		super.delete(serGroupDetails);
	}

		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();

		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serGroupDetailsDao.findCodeNumber(tablename, codename, beginString))));

		return serial.toString();
	}






}