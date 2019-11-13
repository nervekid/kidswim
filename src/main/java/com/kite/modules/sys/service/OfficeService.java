/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.service.TreeService;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.dao.UserDao;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.OfficeGrade;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 机构Service
 * @author kite
 * @version 2014-05-16
 */
@Service
@Transactional(readOnly = true)
public class OfficeService extends TreeService<OfficeDao, Office> {

	@Autowired
	private UserDao userDao;
	@Autowired
	private OfficeDao officeDao;

	public List<Office> findAll(String orgTag, String unOrganTag){
		return UserUtils.getOfficeList(orgTag, unOrganTag);
	}

	public List<Office> findList(Boolean isAll, String orgTag, String unOrganTag){
		Office office = new Office();
		office.setOrganTag(orgTag);
		office.setUnOrganTag(unOrganTag);
		if (isAll != null && isAll){
			return  officeDao.findAllList(office);
		}else{
			dataScopeFilter(office,UserUtils.getUser(),"dfs","a.id");
			return  officeDao.findList(office);
		}
	}

	public List<Office> findListByOwer(String organTag,Boolean isAll){
		Office office = new Office();
		office.setOrganTag(organTag);
		if(isAll==null){
			dataScopeFilter(office,UserUtils.getUser(),"dfs","a.id");
		}
		return  officeDao.findList(office);
	}

	@Override
	@Transactional(readOnly = true)
	public List<Office> findList(Office office){
		//office.setParentIds(office.getParentIds()+"%");
		return dao.findByParentIdsLike(office);
	}

	@Transactional(readOnly = true)
	public Office getByCode(String code){
		return dao.getByCode(code);
	}

	public List<Office> findAllList(Office office){
		return dao.findAllList(office);
	}

	public List<Office> findApiList(Office office){
		return dao.findAllDataList(office);
	}

	public Integer getNextSort(String organTag){
		Integer maxSort=dao.findNextSort(organTag);
		return (maxSort==null?1:maxSort+1);
	}
	@Override
	@Transactional(readOnly = false)
	public void save(Office office) {
		boolean flag = false;
		if(StringUtils.isEmpty(office.getId())){
			flag = true;
		}
		super.save(office);
	}

	@Override
	@Transactional(readOnly = false)
	public void delete(Office office) {
		int count = userDao.getUserCountByOfficeId(office.getId());
		if(count!=0){
			throw new RuntimeException("删除失败！该部门下人员不为空！");
		}
		List<Office> officeList = dao.findByParentIdsLike(office);
		if(officeList.size()>1){
			throw new RuntimeException("删除失败！该部门下存在子部门！");
		}
		//企业微信，企业邮箱同步功能 --验证完成
		/*if("1".equals(office.getOrganTag())){
			tencentEmailOfficeService.deleteOffice(office);
		    xwOfficeService.deleteQywxOffice(office);
		}*/
		super.delete(office);
		//UserUtils.removeCache(UserUtils.CACHE_OFFICE_LIST);
	}


	public OfficeGrade getOfficeGradeById(String officeId){
		if(StringUtils.isEmpty(officeId)){
			officeId = "--";
		}
		return officeDao.getOfficeGradeById(officeId);
	}


}

