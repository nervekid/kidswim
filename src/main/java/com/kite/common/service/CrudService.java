/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.common.service;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.DataEntity;
import com.kite.common.persistence.Page;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.dao.MenuDao;
import com.kite.modules.sys.dao.SysOrganizationalDao;
import com.kite.modules.sys.dao.SysTDataaccessDao;
import com.kite.modules.sys.dao.SysTDataaccessuserDao;
import com.kite.modules.sys.dao.SysTDataaccessuserentityDao;
import com.kite.modules.sys.dao.SysTEntityuseorganDao;
import com.kite.modules.sys.entity.Menu;
import com.kite.modules.sys.entity.SysOrganizational;
import com.kite.modules.sys.entity.SysTEntityuseorgan;
import com.kite.modules.sys.entity.User;

/**
 * Service基类
 * @author kite
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class CrudService<D extends CrudDao<T>, T extends DataEntity<T>> extends BaseService {

	/**
	 * 持久层对象
	 */
	@Autowired protected D dao;
	@Autowired protected SysTEntityuseorganDao sysTEntityuseorganDao;
	@Autowired protected MenuDao menuDao;
	@Autowired protected SysTDataaccessuserDao sysTDataaccessuserDao;
	@Autowired protected SysTDataaccessDao sysTDataaccessDao;
	@Autowired protected SysOrganizationalDao sysOrganizationalDao;
	@Autowired protected SysTDataaccessuserentityDao sysTDataaccessuserentityDao;
	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T get(String id) {
		return dao.get(id);
	}

	/**
	 * 获取单条数据
	 * @param entity
	 * @return
	 */
	public T get(T entity) {
		return dao.get(entity);
	}

	/**
	 * 查询列表数据
	 * @param entity
	 * @return
	 */
	public List<T> findList(T entity) {
		return dao.findList(entity);
	}

	/**
	 * 数据权限
	 * @param entity
	 * @param menuId
	 * @return
	 */
	public List<T> findListDateAuth(T entity, String menuId, User user) {
		entity= quertFilter(entity, user, menuId);
		return this.dao.findList(entity);
	}

	public  T quertFilter(T entity,User user,String menuId){
		//找不到菜单返回空
		Menu menu = this.menuDao.get(menuId);
		if (menu == null) {
			return null;
		}
		//菜单未关联实体表返回空
		if (menu.getEntityOrgId() == null || menu.getEntityOrgId().equals("")) {
			return null;
		}
		String entityOrgId = menu.getEntityOrgId();

		//实体未关联组织架构返回空
		SysTEntityuseorgan sysTEntityuseorgan = this.sysTEntityuseorganDao.get(entityOrgId);
		if (sysTEntityuseorgan == null) {
			return null;
		}

		List<String> accessIds = this.sysTDataaccessuserentityDao.findAccessIdByUserId(user.getId());
		StringBuilder str = new StringBuilder();
		accessIds.forEach(e -> {
			String result = this.sysTDataaccessDao.dataAccessSQls(e, entityOrgId);
			if(StringUtils.isNotEmpty(result)){
				str.append(result);
			}
		});
		if(StringUtils.isNotEmpty(entity.getExtendSql()) && (str.toString() == null || str.toString().equals(""))){
			str.append(entity.getExtendSql());
		}
		if (str.toString() == null || str.toString().equals("")) {
			return null;
		}
		String extendSql = str.toString();
		//替换常见标识 用户id
		extendSql = extendSql.replace(DBC.SQL识别标记.用户Id.type(),"'"+user.getId()+"'");
		//替换常见表示 用户名称
		if(extendSql.contains("%")){
			extendSql = extendSql.replace(DBC.SQL识别标记.用户名称.type(),user.getName());
		}else{
			extendSql = extendSql.replace(DBC.SQL识别标记.用户名称.type(),"'"+user.getName()+"'");
		}


		if (extendSql.contains(DBC.SQL识别标记.一级部门Id.type().toString()) || extendSql.contains(DBC.SQL识别标记.一级部门名称.type().toString()) ) {
			List<String> officeOneId = this.sysOrganizationalDao.findOfiiceOneIdByUserIdAndOrganTag(user.getId(), sysTEntityuseorgan.getOrganTag());
			if (!officeOneId.isEmpty()) {
				StringBuilder strBulider = new StringBuilder();
				StringBuilder userStrBulider = new StringBuilder();
				StringBuilder userNameBulider = new StringBuilder();
				StringBuffer officeOneNameBuilder = new StringBuffer();
				officeOneId.forEach(a -> {
					strBulider.append("'");
					strBulider.append(a);
					strBulider.append("'");
					strBulider.append(",");

					List<String> officeOneUserids = this.sysOrganizationalDao.findUserIdByOfficeOneIdAndOrganTag(a, sysTEntityuseorgan.getOrganTag());
					officeOneUserids.forEach(c -> {
						userStrBulider.append("'");
						userStrBulider.append(c);
						userStrBulider.append("'");
						userStrBulider.append(",");
					});

					List<String> companyUserNames = this.sysOrganizationalDao.findUserNameByOfficeOneIdAndOrganTag(a, sysTEntityuseorgan.getOrganTag());
					companyUserNames.forEach(d -> {
						userNameBulider.append("'");
						userNameBulider.append(d);
						userNameBulider.append("'");
						userNameBulider.append(",");
					});
					List<String> officeOneNames = this.sysOrganizationalDao.findOfficeNameByOfficeOneNameAndOrganTag(a, sysTEntityuseorgan.getOrganTag()).stream().distinct().collect(Collectors.toList());
					officeOneNames.forEach(d -> {
						officeOneNameBuilder.append("'");
						officeOneNameBuilder.append(d);
						officeOneNameBuilder.append("'");
						officeOneNameBuilder.append(",");
					});

				});
				if (strBulider.toString().length() > 0) {
					String companyIdStr = strBulider.toString().substring(0, strBulider.toString().length() - 1); //删尾巴,
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.一级部门Id.type(), companyIdStr);
				}
				if (userStrBulider.toString().length() >0) {
					String userIdSrr = userStrBulider.toString().substring(0, userStrBulider.toString().length() - 1); //删尾巴,
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.分公司或部门以下用户id.type(), userIdSrr);
				}
				if (userNameBulider.toString().length() >0) {
					String userNameSrr = userNameBulider.toString().substring(0, userNameBulider.toString().length() - 1); //删尾巴,
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.分公司或部门以下用户名称.type(), userNameSrr);
				}
				if (officeOneNameBuilder.toString().length() > 0) {
					String officeNameStr = officeOneNameBuilder.toString().substring(0, officeOneNameBuilder.toString().length() - 1); //删尾巴,
					if(extendSql.contains("%")){
						officeNameStr = officeNameStr.replace("'","");
					}
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.一级部门名称.type(), officeNameStr);
				}
			}
		}

		//部门
		if (extendSql.contains(DBC.SQL识别标记.部门Id.type().toString())  || extendSql.contains(DBC.SQL识别标记.部门名称.type().toString())
				|| extendSql.contains(DBC.SQL识别标记.分公司或部门以下用户id.type().toString())
				|| extendSql.contains(DBC.SQL识别标记.分公司或部门以下用户名称.type().toString())) {
			List<SysOrganizational> officeIds = this.sysOrganizationalDao.findOfficeIdByUserIdAndOrganTag(user.getId(), sysTEntityuseorgan.getOrganTag());
			if (!officeIds.isEmpty()) {
				StringBuilder strBulider = new StringBuilder();
				StringBuilder userStrBulider = new StringBuilder();
				StringBuilder userNameBulider = new StringBuilder();
				StringBuilder officeBulider = new StringBuilder();
				officeIds.forEach(a -> {
					strBulider.append("'");
					strBulider.append(a.getOffice().getId());
					strBulider.append("'");
					strBulider.append(",");

					officeBulider.append("'");
					officeBulider.append(a.getOffice().getName());
					officeBulider.append("'");
					officeBulider.append(",");

					List<String> officeUserids = this.sysOrganizationalDao.findUserIdByCompanyIdAndOrganTag(a.getOffice().getId(), sysTEntityuseorgan.getOrganTag());
					officeUserids.forEach(c -> {
						userStrBulider.append("'");
						userStrBulider.append(c);
						userStrBulider.append("'");
						userStrBulider.append(",");
					});

					List<String> officeUserNames = this.sysOrganizationalDao.findUserNameByCompanyIdAndOrganTag(a.getOffice().getId(), sysTEntityuseorgan.getOrganTag());
					officeUserNames.forEach(d -> {
						userNameBulider.append("'");
						userNameBulider.append(d);
						userNameBulider.append("'");
						userNameBulider.append(",");
					});


				});
				if (strBulider.toString().length() > 0) {
					String officeIdStr = strBulider.toString().substring(0, strBulider.toString().length() - 1); //删尾巴,
					if(extendSql.contains("%")){
						officeIdStr = officeIdStr.replace("'","");
					}
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.部门Id.type(), officeIdStr);
				}
				if (officeBulider.toString().length() > 0) {
					String officeNameStr = officeBulider.toString().substring(0, officeBulider.toString().length() - 1); //删尾巴,
					if(extendSql.contains("%")){
						officeNameStr = officeNameStr.replace("'","");
					}
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.部门名称.type(), officeNameStr);
				}
				if (userStrBulider.toString().length() >0) {
					String userIdSrr = userStrBulider.toString().substring(0, userStrBulider.toString().length() - 1); //删尾巴,
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.分公司或部门以下用户id.type(), userIdSrr);
				}
				if (userNameBulider.toString().length() >0) {
					String userNameSrr = userNameBulider.toString().substring(0, userNameBulider.toString().length() - 1); //删尾巴,
					extendSql = StringUtils.replace(extendSql, DBC.SQL识别标记.分公司或部门以下用户名称.type(), userNameSrr);
				}
			}
		}
		if(StringUtils.isNotEmpty(extendSql)){
			entity.setExtendSql(extendSql);
		}
		return entity;
	}


	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPage(Page<T> page, T entity)  {
		entity.setPage(page);
		page.setList(dao.findList(entity));
		return page;
	}

	/**
	 * 查询分页数据
	 * @param page 分页对象
	 * @param entity
	 * @return
	 */
	public Page<T> findPageDateAuth(Page<T> page, T entity, String menuId, User user)  {
		entity.setPage(page);
		page.setList(this.findListDateAuth(entity, menuId, user));
		return page;
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void save(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
	}

	/**
	 * 保存数据（插入或更新）
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public String saveAndReturnId(T entity) {
		if (entity.getIsNewRecord()){
			entity.preInsert();
			dao.insert(entity);
		}else{
			entity.preUpdate();
			dao.update(entity);
		}
		return entity.getId();
	}

	/**
	 * 删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void delete(T entity) {
		dao.delete(entity);
	}

	/**
	 * 逻辑删除数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void deleteByLogic(T entity) {
		dao.deleteByLogic(entity);
	}


	/**
	 * 删除全部数据
	 * @param entity
	 */
	@Transactional(readOnly = false)
	public void deleteAll(Collection<T> entitys) {
		for (T entity : entitys) {
			dao.delete(entity);
		}
	}


	/**
	 * 获取单条数据
	 * @param id
	 * @return
	 */
	public T findUniqueByProperty(String propertyName, Object value){
		return dao.findUniqueByProperty(propertyName, value);
	}

}


class DBC {
	public enum SQL识别标记 {
        用户Id("user_t_id"),
        用户名称("user_t_name"),
        一级部门Id("office_one_ids"),
		一级部门名称("office_one_names"),
        部门Id("office_t_ids"),
        部门名称("office_t_names"),
        分公司或部门以下用户id("user_t_ids"),
        分公司或部门以下用户名称("user_t_orgNames");

        private final String type;

        SQL识别标记(String type) {
            this.type = type;
        }

        public String type() {
            return type;
        }
    }
}