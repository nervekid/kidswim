/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.common.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.kite.common.persistence.BaseEntity;
import com.kite.common.utils.StringUtils;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.User;

/**
 * Service基类
 * @author kite
 * @version 2014-05-16
 */
@Transactional(readOnly = true)
public abstract class BaseService {

	/**
	 * 日志对象
	 */
	protected Logger logger = LoggerFactory.getLogger(getClass());

	/**
	 * 数据范围过滤
	 * @param user 当前用户对象，通过“entity.getCurrentUser()”获取
	 * @param officeAlias 机构表别名，多个用“,”逗号隔开。
	 * @param userAlias 用户表别名，多个用“,”逗号隔开，传递空，忽略此参数
	 * @return 标准连接条件对象
	 */
	public static String dataScopeFilter(User user, String officeAlias, String userAlias) {

		StringBuilder sqlString = new StringBuilder();

		// 进行权限过滤，多个角色权限范围之间为或者关系。
		List<String> dataScope = Lists.newArrayList();

		// 超级管理员，跳过权限过滤
		if (!user.isAdmin()){
			boolean isDataScopeAll = false;
			for (Role r : user.getRoleList()){
				for (String oa : StringUtils.split(officeAlias, ",")){
					if (!dataScope.contains(r.getDataScope()) && StringUtils.isNotBlank(oa)){
						if (Role.DATA_SCOPE_ALL.equals(r.getDataScope())){
							isDataScopeAll = true;
						}
						else if (Role.DATA_SCOPE_COMPANY_AND_CHILD.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getCompany().getParentIds() + user.getCompany().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_COMPANY.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getCompany().getId() + "'");
							// 包括本公司下的部门 （type=1:公司；type=2：部门）
							sqlString.append(" OR (" + oa + ".parent_id = '" + user.getCompany().getId() + "' AND " + oa + ".type = '2')");
						}
						else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_OFFICE.equals(r.getDataScope())){
							sqlString.append(" OR " + oa + ".id = '" + user.getOffice().getId() + "'");
							sqlString.append(" OR " + oa + ".parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%'");
						}
						else if (Role.DATA_SCOPE_CUSTOM.equals(r.getDataScope())){
//							String officeIds =  StringUtils.join(r.getOfficeIdList(), "','");
//							if (StringUtils.isNotEmpty(officeIds)){
//								sqlString.append(" OR " + oa + ".id IN ('" + officeIds + "')");
//							}
							sqlString.append(" OR EXISTS (SELECT 1 FROM sys_role_office WHERE role_id = '" + r.getId() + "'");
							sqlString.append(" AND office_id = " + oa +".id)");
						}
						//else if (Role.DATA_SCOPE_SELF.equals(r.getDataScope())){
						dataScope.add(r.getDataScope());
					}
				}
			}
			// 如果没有全部数据权限，并设置了用户别名，则当前权限为本人；如果未设置别名，当前无权限为已植入权限
			if (!isDataScopeAll){
				if (StringUtils.isNotBlank(userAlias)){
					for (String ua : StringUtils.split(userAlias, ",")){
						sqlString.append(" OR " + ua + ".id = '" + user.getId() + "'");
					}
				}else {
					for (String oa : StringUtils.split(officeAlias, ",")){
						//sqlString.append(" OR " + oa + ".id  = " + user.getOffice().getId());
						sqlString.append(" OR " + oa + ".id IS NULL");
					}
				}
			}else{
				// 如果包含全部权限，则去掉之前添加的所有条件，并跳出循环。
				sqlString = new StringBuilder();
			}
		}
		if (StringUtils.isNotBlank(sqlString.toString())){
			return " AND (" + sqlString.substring(4) + ")";
		}
		return "";
	}

	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 * @param entity 当前过滤的实体类
	 * @param sqlMapKey sqlMap的键值，例如设置“dsf”时，调用方法：${sqlMap.sdf}
	 * @param officeWheres office表条件，组成：部门表字段=业务表的部门字段
	 * @param userWheres user表条件，组成：用户表字段=业务表的用户字段
	 * @example
	 * 		dataScopeFilter(user, "dsf", "id=a.office_id", "id=a.create_by");
	 * 		dataScopeFilter(entity, "dsf", "code=a.jgdm", "no=a.cjr"); // 适应于业务表关联不同字段时使用，如果关联的不是机构id是code。
	 */
	public static void dataScopeFilter(BaseEntity<?> entity, String sqlMapKey, String officeWheres, String userWheres) {

		User user = entity.getCurrentUser();

		// 如果是超级管理员，则不过滤数据
		if (user.isAdmin()) {
			return;
		}

		// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
		StringBuilder sqlString = new StringBuilder();

		// 获取到最大的数据权限范围
		String roleId = "";
		int dataScopeInteger = 8;
		for (Role r : user.getRoleList()){
			int ds = Integer.valueOf(r.getDataScope());
			if (ds == 9){
				roleId = r.getId();
				dataScopeInteger = ds;
				break;
			}else if (ds < dataScopeInteger){
				roleId = r.getId();
				dataScopeInteger = ds;
			}
		}
		String dataScopeString = String.valueOf(dataScopeInteger);

		// 生成部门权限SQL语句
		for (String where : StringUtils.split(officeWheres, ",")){
			if (Role.DATA_SCOPE_COMPANY_AND_CHILD.equals(dataScopeString)){
				// 包括本公司下的部门 （type=1:公司；type=2：部门）
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE type='2'");
				sqlString.append(" AND (id = '" + user.getCompany().getId() + "'");
				sqlString.append(" OR parent_ids LIKE '" + user.getCompany().getParentIds() + user.getCompany().getId() + ",%')");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_COMPANY.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE type='2'");
				sqlString.append(" AND id = '" + user.getCompany().getId() + "'");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_OFFICE_AND_CHILD.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE (id = '" + user.getOffice().getId() + "'");
				sqlString.append(" OR parent_ids LIKE '" + user.getOffice().getParentIds() + user.getOffice().getId() + ",%')");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_OFFICE.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM SYS_OFFICE");
				sqlString.append(" WHERE id = '" + user.getOffice().getId() + "'");
				sqlString.append(" AND " + where +")");
			}
			else if (Role.DATA_SCOPE_CUSTOM.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM sys_role_office ro123456, sys_office o123456");
				sqlString.append(" WHERE ro123456.office_id = o123456.id");
				sqlString.append(" AND ro123456.role_id = '" + roleId + "'");
				sqlString.append(" AND o123456." + where +")");
			}
		}
		// 生成个人权限SQL语句
		for (String where : StringUtils.split(userWheres, ",")){
			if (Role.DATA_SCOPE_SELF.equals(dataScopeString)){
				sqlString.append(" AND EXISTS (SELECT 1 FROM sys_user");
				sqlString.append(" WHERE id='" + user.getId() + "'");
				sqlString.append(" AND " + where + ")");
			}
		}

//		System.out.println("dataScopeFilter: " + sqlString.toString());

		// 设置到自定义SQL对象
		entity.getSqlMap().put(sqlMapKey, sqlString.toString());

	}






	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 * @param entity 当前过滤的实体类 默
     *  默认根据user_id过滤
	 */
	public static void dataScopeFilter(BaseEntity<?> entity) {

		User user = entity.getCurrentUser();

		// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
		StringBuilder sqlString = new StringBuilder();
		sqlString.append(" and");
				sqlString.append(" (");
				sqlString.append(" (('8' in (SELECT sys_role.data_scope from sys_user");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_user.id=sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role on sys_user_role.role_id=sys_role.id");
				sqlString.append(" where user_id="+user.getId()+")) and a.user_id="+user.getId()+")");
				sqlString.append(" or");
				sqlString.append(" (('9' in (SELECT sys_role.data_scope from sys_user");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_user.id=sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role on sys_user_role.role_id=sys_role.id");
				sqlString.append(" where user_id="+user.getId()+")) and a.user_id in (");
				sqlString.append(" SELECT sys_user.id from  sys_role_office");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_role_office.role_id=sys_user_role.role_id");
				sqlString.append(" LEFT JOIN sys_user on sys_role_office.office_id=sys_user.office_id");
				sqlString.append(" where sys_user_role.user_id="+user.getId()+"");
				sqlString.append(" ))");
				sqlString.append(" or");
				sqlString.append(" (('5' in (SELECT sys_role.data_scope from sys_user");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_user.id=sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role on sys_user_role.role_id=sys_role.id");
				sqlString.append(" where user_id="+user.getId()+")) and a.user_id in (");
				sqlString.append(" SELECT sys_user.id from  sys_user where office_id in(");
				sqlString.append(" SELECT sys_user.office_id from  sys_user where sys_user.id="+user.getId()+"");
				sqlString.append(" )");
			    sqlString.append(" ))");
				sqlString.append(" or (('1' IN (");
				sqlString.append(" SELECT sys_role.data_scope FROM sys_user");
				sqlString.append(" LEFT JOIN sys_user_role ON sys_user.id = sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role ON sys_user_role.role_id = sys_role.id");
				sqlString.append(" WHERE user_id = "+user.getId()+")) AND 1=1 )");
				sqlString.append(" )");
		// 设置到自定义SQL对象
		entity.getSqlMap().put("xw", sqlString.toString());

	}




	/**
	 * 数据范围过滤（符合业务表字段不同的时候使用，采用exists方法）
	 * @param entity 当前过滤的实体类 默
                             根据filterid过滤,且本部门数据时，包括所有下级
	 */
	public static void dataScopeFilter(BaseEntity<?> entity,String filterid) {

		User user = entity.getCurrentUser();

	/*	// 数据范围（1：所有数据；2：所在公司及以下数据；3：所在公司数据；4：所在部门及以下数据；5：所在部门数据；8：仅本人数据；9：按明细设置）
		/*StringBuilder sqlString = new StringBuilder();

		sqlString.append(" and");
				sqlString.append(" (");
				sqlString.append(" (('8' in (SELECT sys_role.data_scope from sys_user");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_user.id=sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role on sys_user_role.role_id=sys_role.id");
				sqlString.append(" where user_id="+user.getId()+")) and "+filterid+"="+user.getId()+")");
				sqlString.append(" or");
				sqlString.append(" (('9' in (SELECT sys_role.data_scope from sys_user");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_user.id=sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role on sys_user_role.role_id=sys_role.id");
				sqlString.append(" where user_id="+user.getId()+")) and "+filterid+" in (");
				sqlString.append(" SELECT sys_user.id from  sys_role_office");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_role_office.role_id=sys_user_role.role_id");
				sqlString.append(" LEFT JOIN sys_user on sys_role_office.office_id=sys_user.office_id");
				sqlString.append(" where sys_user_role.user_id="+user.getId()+"");
				sqlString.append(" ))");
				sqlString.append(" or");
				sqlString.append(" (('5' in (SELECT sys_role.data_scope from sys_user");
				sqlString.append(" LEFT JOIN  sys_user_role on sys_user.id=sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role on sys_user_role.role_id=sys_role.id");
				sqlString.append(" where user_id="+user.getId()+")) and "+filterid+" in (");
				sqlString.append(" SELECT sys_user.id FROM sys_user");
				sqlString.append(" WHERE office_id IN (SELECT sys_office.id from sys_office where CONCAT(parent_ids,sys_office.id) like");
				sqlString.append(" CONCAT((SELECT CONCAT(parent_ids,sys_office.id) FROM sys_user LEFT JOIN sys_office on sys_user.office_id=sys_office.id");
				sqlString.append(" WHERE sys_user.id = "+user.getId()+"),'%'))");
				//sqlString.append(" SELECT sys_user.id from  sys_user where office_id in(");
				//sqlString.append(" SELECT sys_user.office_id from  sys_user where sys_user.id="+user.getId()+"");
				//sqlString.append(" )");
			    sqlString.append(" ))");
				sqlString.append(" or (('1' IN (");
				sqlString.append(" SELECT sys_role.data_scope FROM sys_user");
				sqlString.append(" LEFT JOIN sys_user_role ON sys_user.id = sys_user_role.user_id");
				sqlString.append(" LEFT JOIN sys_role ON sys_user_role.role_id = sys_role.id");
				sqlString.append(" WHERE user_id = "+user.getId()+")) AND 1=1 )");
				sqlString.append(" )");*/
		// 设置到自定义SQL对象
		user.getRoleList().stream().filter(e -> {
			return e.getSysModules().equals("0");
		}).limit(1);
		entity.getSqlMap().put("xw", " and filter_data("+filterid+",'"+user.getId()+"')");

	}

	public static void dataScopeFilter(BaseEntity<?> entity,String filterid, String modules) {

		User user = entity.getCurrentUser();

		user.getRoleList().stream().filter(e -> {
			return e.getSysModules().equals("0");
		}).limit(1);
		entity.getSqlMap().put("xw", " and filter_data("+filterid+",'"+user.getId()+"','" + modules + "')");

	}


	public void dataScopeFilterType2(BaseEntity<?> entity,String filterid, String modules) {

		User user = entity.getCurrentUser();

		user.getRoleList().stream().filter(e -> {
			return e.getSysModules().equals("0");
		}).limit(1);
		
	
		StringBuilder sqlString = new StringBuilder();

		sqlString.append("  "+filterid+" = '"+user.getId()+"'");
	
		sqlString.append(" or");
		sqlString.append(" (exists (");
		sqlString.append(" select sys_role.data_scope from sys_user");
		sqlString.append(" join sys_user_role on sys_user.id = sys_user_role.user_id");
		sqlString.append(" join sys_role on sys_user_role.role_id = sys_role.id ");
		sqlString.append(" and sys_role.sysModules = '"+modules+"'");
		sqlString.append(" where user_id = '"+user.getId()+ "' and sys_role.data_scope='8' )");
		sqlString.append(" and "+filterid+" = '"+user.getId()+"')");
		
		
		sqlString.append(" or");
		sqlString.append(" (exists (select sys_role.data_scope from sys_user join sys_user_role on sys_user.id = sys_user_role.user_id "
				          + "join sys_role on sys_user_role.role_id = sys_role.id  and sys_role.sysModules = '"+modules+"'");
		sqlString.append(" where user_id = '"+user.getId()+"' and sys_role.data_scope='9')");
		sqlString.append(" and exists (select sys_user.id from sys_role_office");
		sqlString.append(" join sys_user_role on sys_role_office.role_id = sys_user_role.role_id");
		sqlString.append(" join sys_user on sys_role_office.office_id = sys_user.office_id");
		sqlString.append(" where sys_user_role.user_id = '"+user.getId()+"' and sys_user.id="+filterid+"))");
		
		sqlString.append(" or");
		sqlString.append(" (exists (select sys_role.data_scope from sys_user");
		sqlString.append(" 	join sys_user_role on sys_user.id = sys_user_role.user_id");
		sqlString.append(" join sys_role on sys_user_role.role_id = sys_role.id  and sys_role.sysModules = '"+modules+"'");
		sqlString.append(" where user_id = '"+user.getId()+"' and sys_role.data_scope='5'");
		sqlString.append(" 	)and exists(select sys_user.id from sys_user where exists (select sys_office.id");
		sqlString.append(" from sys_office	where CONCAT(parent_ids,sys_office.id) like concat(");
		sqlString.append(" 	(select CONCAT(parent_ids,sys_office.id) from sys_user");
		sqlString.append(" 	join sys_office on sys_user.office_id = sys_office.id");
		sqlString.append(" 	where sys_user.id = '"+user.getId()+"'),'%') and sys_office.id=office_id");
		sqlString.append(" )and "+filterid+"=sys_user.id))");
		
	
		
		sqlString.append(" or");
		sqlString.append(" (exists (select sys_role.data_scope from sys_user ");
		sqlString.append(" join sys_user_role on sys_user.id = sys_user_role.user_id");
		sqlString.append(" join sys_role on sys_user_role.role_id = sys_role.id ");
		sqlString.append("   and sys_role.sysModules = '"+modules+"'");
		sqlString.append(" 	where user_id =  '"+user.getId()+"' and sys_role.data_scope='1'))");
		
		entity.getSqlMap().put("xw",sqlString.toString());
	

	}

	/**
	 　* @description: 组织架构数据权限过滤sql
	 　* @param [entity, user, sqlMapKey]
	 　* @return void
	 　* @throws
	 　* @author cxh
	 　* @date 2019/9/6 9:33 
	 　*/
	public static void  dataScopeFilter(BaseEntity<?> entity,User user,String sqlMapKey,String officeKey) {
		List<Role> roleList = user.getRoleList();
		if(roleList!=null){
			List<String> dataScopeList = roleList.stream().map(Role::getDataScope).collect(Collectors.toList());
			boolean contains = dataScopeList.contains("1");
			if(!contains){
				StringBuffer buffer = new StringBuffer();
				buffer.append(" and  EXISTS ( select s.id from sys_office s INNER JOIN sys_role_office sro on s.id = sro.office_id");
				buffer.append(" INNER JOIN sys_role sr on sro.role_id = sr.id  and sr.del_flag=0");
				buffer.append(" INNER JOIN sys_user_role sur on sur.role_id = sr.id");
				buffer.append(" INNER JOIN sys_user su on su.id = sur.user_id and su.del_flag=0");
				buffer.append(" where su.id='"+user.getId()+"' and ("+officeKey+" =s.id or "+officeKey+" = s.parent_id) and s.del_flag=0 )");
				entity.getSqlMap().put(sqlMapKey,buffer.toString());
			}
		}
	}
























}
