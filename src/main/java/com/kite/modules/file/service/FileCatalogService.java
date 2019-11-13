/**
 * KITE
 */
package com.kite.modules.file.service;

import java.util.List;

import com.kite.common.service.TreeService;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.entity.Office;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.Page;
import com.kite.common.service.CrudService;
import com.kite.common.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import com.kite.modules.file.entity.FileCatalog;
import com.kite.modules.file.dao.FileCatalogDao;

/**
 * 文档目录Service
 * @author yyw
 * @version 2018-08-22
 */
@Service
@Transactional(readOnly = true)
public class FileCatalogService  extends TreeService<FileCatalogDao, FileCatalog> {

    @Autowired
	FileCatalogDao fileCatalogDao;
	@Override
	public FileCatalog get(String id) {
		return super.get(id);
	}
	@Override
	public List<FileCatalog> findList(FileCatalog fileCatalog) {
		return super.findList(fileCatalog);
	}
	@Override
	public Page<FileCatalog> findPage(Page<FileCatalog> page, FileCatalog fileCatalog) {
		return super.findPage(page, fileCatalog);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(FileCatalog fileCatalog) {
		super.save(fileCatalog);
	}
	@Override
	@Transactional(readOnly = false)
	public void delete(FileCatalog fileCatalog) {
		super.delete(fileCatalog);
	}
	
		@Transactional(readOnly = false)
	public String findCodeNumber(String tablename,String codename,String beginString){
		StringBuffer serial=new StringBuffer();
		
		serial.append(beginString);
		serial.append("-");
		serial.append(StringUtils.getNowYearMonth());
		serial.append("-");
		serial.append(String.format("%04d", Integer.parseInt(fileCatalogDao.findCodeNumber(tablename, codename, beginString))));
		
		return serial.toString();
	}

	
}