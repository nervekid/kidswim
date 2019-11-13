/**
 * KITE
 */
package com.kite.modules.att.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.att.entity.SerSale;
import com.kite.modules.att.dao.SerSaleDao;

/**
 * 销售资料Service
 * @author lyb
 * @version 2019-11-13
 */
@Service
@Transactional(readOnly = true)
public class SerSaleService extends CrudService<SerSaleDao, SerSale> {

    @Autowired
	SerSaleDao serSaleDao;
	@Override
	public SerSale get(String id) {
		return super.get(id);
	}
	@Override
	public List<SerSale> findList(SerSale serSale) {
		return super.findList(serSale);
	}
	@Override
	public Page<SerSale> findPage(Page<SerSale> page, SerSale serSale) {
		return super.findPage(page, serSale);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(SerSale serSale) {
		super.save(serSale);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(SerSale serSale) {
		super.delete(serSale);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(serSaleDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}
	
	
	
	
	
	
}