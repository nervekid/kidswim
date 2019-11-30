/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.service.CrudService;
import com.kite.modules.sys.dao.DictDao;
import com.kite.modules.sys.entity.Dict;

/**
 * 字典Service
 * @author kite
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class DictService extends CrudService<DictDao, Dict> {

	@Autowired
	private DictDao dictDao;

	/**
	 * 查询字段类型列表
	 * @return
	 */
	public List<String> findTypeList(){
		return dao.findTypeList(new Dict());
	}

	@Override
	@Transactional(readOnly = false)
	public void save(Dict dict) {
		super.save(dict);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Dict dict) {
		super.delete(dict);
	}

	/**
	 * 根据类型数组查询字典数据
	 * @param dictType
	 * @return
	 */
	public List<Dict> findListByTypeArr(String[] dictType) {
		if (dictType == null || dictType.length == 0) {
			return null;
		}
		return dictDao.findListByTypeArr(dictType);
	}

	public Map<String, String> getDictByTypeAndValue(String type, String value){
		if ((type == null || type.equals("")) || (value == null || value.equals(""))) {
			return null;
		}
		return dictDao.getDictByTypeAndValue(type, value);
	}

	@Transactional(readOnly = false)
	public void updateStatus(Dict dict) {
		dictDao.updateStatus(dict);
	}
}
