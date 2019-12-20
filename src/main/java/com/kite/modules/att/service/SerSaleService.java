/**
 * KITE
 */
package com.kite.modules.att.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.command.RpcSaleStudentCommand;
import com.kite.modules.att.dao.SerSaleDao;
import com.kite.modules.att.dao.SysBaseStudentDao;
import com.kite.modules.att.entity.SerSale;
import com.kite.modules.att.entity.SysBaseStudent;

/**
 * 銷售資料Service
 * @author yyw
 * @version 2019-12-01
 */
@Service
@Transactional(readOnly = true)
public class SerSaleService extends CrudService<SerSaleDao, SerSale> {

    @Autowired
	SerSaleDao serSaleDao;

    @Autowired
	SysBaseStudentDao sysBaseStudentDao;

	@Override
	public SerSale get(String id) {

		SerSale serSale = super.get(id);
		String studentCode = serSale.getStudentCode();
		if(StringUtils.isNotEmpty(studentCode)) {
			SysBaseStudent sysBaseStudent = sysBaseStudentDao.getByCode(studentCode);
			if(sysBaseStudent != null) {
				String nameEn = sysBaseStudent.getNameEn();
				String nameCn = sysBaseStudent.getNameCn();

				String name = nameCn + "(" + nameEn + ")";
				serSale.setStudentName(name);
			}
		}
		return serSale;
	}
	@Override
	public List<SerSale> findList(SerSale serSale) {
		List<SerSale> list = super.findList(serSale);

		return list;
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

	public int findcount(Date beginTime, Date endTime) {
		return serSaleDao.findcount(beginTime, endTime);
	}

	/**
	 * 根据地址及开始时间查找
	 * @param courseAddress
	 * @param learnBeginTime
	 * @return
	 */
	public List<RpcSaleStudentCommand> findRpcSaleStudentCommandByAddressAndBeginTime(String courseAddress, String learnBeginTime) {
		return this.serSaleDao.findRpcSaleStudentCommandByAddressAndBeginTime(courseAddress, learnBeginTime);
	}
}