/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.Role;

/**
 * 角色DAO接口
 * @author kite
 * @version 2013-12-05
 */
@MyBatisDao
public interface RoleDao extends CrudDao<Role> {

	/**
	 * 根据用户名称查询角色
	 * @param names
	 * @return
	 */
	public String getRoleByName(@Param("name") String name, @Param("flag") String flag);

	public Role getByName(Role role);

	public Role getByEnname(Role role);

	/**
	 * 维护角色与菜单权限关系
	 * @param role
	 * @return
	 */
	public int deleteRoleMenu(Role role);

	public int insertRoleMenu(Role role);

	/**
	 * 维护角色与公司部门关系
	 * @param role
	 * @return
	 */
	public int deleteRoleOffice(Role role);

	public int insertRoleOffice(Role role);

	/**
	 * 角色名称查找用户ID
	 * @param name
	 * @return
	 */
	public int findRoleUserId(@Param("name") String name, @Param("userId") String userId);

	/**
	 * 查找用户角色
	 * @param enname
	 * @param roletype
	 * @return
	 */
	public Role findRoleByEnNameAndRoleType(@Param("enname") String enname, @Param("roletype") String roletype);


}
