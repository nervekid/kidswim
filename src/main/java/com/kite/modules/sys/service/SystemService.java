/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.shiro.session.Session;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kite.common.config.Global;
import com.kite.common.persistence.Page;
import com.kite.common.security.Digests;
import com.kite.common.security.shiro.session.SessionDAO;
import com.kite.common.service.BaseService;
import com.kite.common.service.ServiceException;
import com.kite.common.utils.Encodes;
import com.kite.common.utils.StringUtils;
import com.kite.modules.att.command.RpcUserCommand;
import com.kite.modules.sys.dao.DictDao;
import com.kite.modules.sys.dao.MenuDao;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.dao.RoleDao;
import com.kite.modules.sys.dao.SysOrganizationalDao;
import com.kite.modules.sys.dao.UserDao;
import com.kite.modules.sys.entity.Dict;
import com.kite.modules.sys.entity.Menu;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.SysOrganizational;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.security.SystemAuthorizingRealm;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 系统管理，安全相关实体的管理类,包括用户、角色、菜单.
 * @author kite
 * @version 2013-12-05
 */
@Service
@Transactional(readOnly = true)
public class SystemService extends BaseService implements InitializingBean {

	public static final String HASH_ALGORITHM = "SHA-1";
	public static final int HASH_INTERATIONS = 1024;
	public static final int SALT_SIZE = 8;

	@Autowired
	private UserDao userDao;
	@Autowired
	private RoleDao roleDao;
	@Autowired
	private MenuDao menuDao;
	@Autowired
	private SessionDAO sessionDao;
	@Autowired
	private SystemAuthorizingRealm systemRealm;
	@Autowired
	private OfficeDao officeDao;
	@Autowired
	private DictDao dictDao;

	public SessionDAO getSessionDao() {
		return sessionDao;
	}
	@Autowired
	private SysOrganizationalDao sysOrganizationalDao;

	//-- User Service --//

	/**
	 * 获取用户
	 * @param id
	 * @return
	 */
	public User getUser(String id) {
		return UserUtils.get(id);
	}

	/**
	 * 根据类型及标签名称获取值
	 * 系统字典
	 * @param type
	 * @param label
	 * @return
	 */
	public String findDictValueByTypeAndLabel(String type, String label) {
		return this.dictDao.findValueByTypeAndLabel(type, label);
	}

	/**
	 * 根据类型、标签名称校验是否存在启用状态的字典
	 * @param type
	 * @param label
	 * @return
	 */
	public Boolean checkByTypeAndLabelAndFlag(String type, String label) {
		return (this.dictDao.findCountByTypeAndLabelAndFlag(type, label,"0").equals("0")?false:true);
	}

	/**
	 * 根据类型输出启用状态的字典
	 * @param type
	 * @return
	 */
	public String findStringByType(String type) {
		return this.dictDao.findStringByTypeAndFlag(type,"0");
	}

//	public List<Role> getByUser(String id) {
//		return roleDao.getByUser(id);
//	}


	public List<Dict> listDict(Dict select) {
		return dictDao.findList(select);
	}

	/**
	 * 是否存在公司部门
	 * @param name 名称
	 * @return
	 */
	public boolean isExitOfficeByName(String name) {
		if (this.officeDao.getByName(name.trim(), "0") == null || this.officeDao.getByName(name.trim(), "0").equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 是否存在用户
	 * @param name 名称
	 * @return
	 */
	public boolean isExitUserByName(String name) {
		if (this.userDao.getByName(name.trim()) == null) {
			return false;
		}
		return true;
	}

	/**
	 * 是否存在角色
	 * @param name 名称
	 * @return
	 */
	public boolean isExitRoleByNameVer(String name) {
		if (this.roleDao.getRoleByName(name.trim(), "0") == null || this.roleDao.getRoleByName(name.trim(), "0").equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 是否存在相同登录名且未被禁用的用户
	 * @param name
	 * @return
	 */
	public boolean isExitRepeatLoginName(String name) {
		if (this.userDao.getByLoginNameVer(name.trim(), "0") == null || this.userDao.getByLoginNameVer(name.trim(), "0").equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 设备状态是否存在
	 * @param
	 * @return
	 */
	public boolean isExitDictStatusType(String type, String tarName) {
		List<String> list = this.dictDao.findDictLabelListByType(type, "0");
		if (list.isEmpty()) {
			return false;
		}
		else {
			for (String d : list) {
				if (d.equals(tarName.trim())) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 查找字典状态label集合
	 * @param type
	 * @param tarName
	 * @return
	 */
	public List<String> findDictStatusLabelList(String type, String tarName) {
		List<String> list = this.dictDao.findDictLabelListByType(type, "0");
		return list;
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return
	 */
	public User getUserByLoginName(String loginName) {
		return UserUtils.getByLoginName(loginName);
	}

	public Page<User> findUser(Page<User> page, User user) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findList(user));
		return page;
	}

	public Page<User> findUserSelect(Page<User> page, User user, String officeId, String organTag) {
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		// 设置分页参数
		user.setPage(page);
		// 执行分页查询
		page.setList(userDao.findUserList(officeId, organTag));
		return page;
	}

	public List<User> findUsersByOfficeIds(String[] officeIds ,String orgTag){
	    List<User>  userList = null ;
	    try {
	        List<String>  ids = new ArrayList<>();
	        for (String  id:officeIds){
	            ids.add(id);
            }
            userList = userDao.findUsersByOfficeIds(ids ,orgTag);
        }catch (Exception e){
	        e.printStackTrace();
        }
	    return userList;
    }


	/**
	 * 无分页查询人员列表
	 * @param user
	 * @return
	 */
	public List<User> findUser(User user){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）
		user.getSqlMap().put("dsf", dataScopeFilter(user.getCurrentUser(), "o", "a"));
		List<User> list = userDao.findList(user);
		return list;
	}

	public List<User> list(User user){
		List<User> list = userDao.findList(user);
		return list;
	}

	public User findUserByName(String username){
		// 生成数据权限过滤条件（dsf为dataScopeFilter的简写，在xml中使用 ${sqlMap.dsf}调用权限SQL）

		User user = userDao.findUniqueByProperty("name",username);
		return user;
	}

	/**
	 * 通过部门ID获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<User> findUserByOfficeId(String officeId) {
		User user = new User();
		user.setOffice(new Office(officeId));
		return userDao.findUserByOfficeId(user);
	}

	@Transactional(readOnly = false)
	public void saveUser(User user) {
		SysOrganizational sysOrganizational = null;

		Office office = officeDao.get(user.getOffice().getId());
		Office parent = null;
		if(office!=null){
			parent = office.getParent();
			user.setCompany(parent);
		}
		if (StringUtils.isBlank(user.getId())){
			user.preInsert();
			userDao.insert(user);
			if(office!=null && parent!=null){
				sysOrganizational = new SysOrganizational();
				sysOrganizational.setOrganTag("1");
				sysOrganizational.setOfficeOne(UserUtils.getDepartment(parent.getId()));
				sysOrganizational.setOffice(office);
				sysOrganizational.setCompanyId(parent.getId());
				sysOrganizational.setCompanyName(parent.getName());
				sysOrganizational.setUser(user);
				sysOrganizational.preInsert();
				sysOrganizationalDao.insert(sysOrganizational);
				if ("roleFlag".equals(user.getIsAddRoleFlag())){
					logger.info("---------------setRoleFlag-----------");
				}else {
				}
			}
		}else{
			// 清除原用户机构用户缓存
			User oldUser = userDao.get(user.getId());
			if (oldUser.getOffice() != null && oldUser.getOffice().getId() != null){
			}
			// 更新用户数据
			user.preUpdate();
			userDao.update(user);
			SysOrganizational entity = sysOrganizationalDao.findEntityByUserIdAndOrganTag(user.getId(), "1");
			if(office!=null && parent!=null){
				entity.preUpdate();
				entity.setOffice(office);
				entity.setOfficeOne(UserUtils.getDepartment(parent.getId()));
				entity.setCompanyId(parent.getId());
				entity.setCompanyName(parent.getName());
				sysOrganizationalDao.update(entity);
			}
			if ("roleFlag".equals(user.getIsAddRoleFlag())){
				logger.info("---------------setRoleFlag-----------");
			}else {
			}
		}
		if (StringUtils.isNotBlank(user.getId())){
			// 更新用户与角色关联
			userDao.deleteUserRole(user);
			if (user.getRoleList() != null && user.getRoleList().size() > 0){
				userDao.insertUserRole(user);
			}else{
				throw new ServiceException(user.getLoginName() + "没有设置角色！");
			}
			// 清除权限缓存
			systemRealm.clearAllCachedAuthorizationInfo();
		}
	}

	@Transactional(readOnly = false)
	public void updateUserInfo(User user) {
		user.preUpdate();
		userDao.updateUserInfo(user);
		// 清除用户缓存
		/*UserUtils.clearCache(user);*/
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteUser(User user) {
		//企业微信，企业邮箱同步功能
		sysOrganizationalDao.deleteByUserId(user.getId(),"1");
		user.setUpdateDate(new Date());
		user.setEnable("0");
		userDao.update(user);
		// 清除用户缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void updatePasswordById(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(entryptPasswordByMD5(newPassword));
		userDao.updatePasswordById(user);
		// 清除用户缓存
		user.setLoginName(loginName);
		//UserUtils.clearCache(user);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		User newUser = userDao.get(id);
	}

	@Transactional(readOnly = false)
	public void updatePasswordByIdWithMd5(String id, String loginName, String newPassword) {
		User user = new User(id);
		user.setPassword(newPassword);
		userDao.updatePasswordById(user);
		user.setLoginName(loginName);
		// 清除用户缓存
		//UserUtils.clearCache(user);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void updateUserLoginInfo(User user) {
		// 保存上次登录信息
		user.setOldLoginIp(user.getLoginIp());
		user.setOldLoginDate(user.getLoginDate());
		// 更新本次登录信息
		user.setLoginIp(UserUtils.getSession().getHost());
		user.setLoginDate(new Date());
		userDao.updateLoginInfo(user);
	}

	/**
	 * 生成安全的密码，生成随机的16位salt并经过1024次 sha-1 hash
	 */
	public static String entryptPassword(String plainPassword) {
		byte[] salt = Digests.generateSalt(SALT_SIZE);
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword);
	}


	public static String entryptPasswordByMD5(String password) {
		return DigestUtils.md5Hex(password);
	}

	/**
	 * 验证密码
	 * @param plainPassword 明文密码
	 * @param password 密文密码
	 * @return 验证成功返回true
	 */
	public static boolean validatePassword(String plainPassword, String password) {
		byte[] salt = Encodes.decodeHex(password.substring(0,16));
		byte[] hashPassword = Digests.sha1(plainPassword.getBytes(), salt, HASH_INTERATIONS);
		return password.equals(Encodes.encodeHex(salt)+Encodes.encodeHex(hashPassword));
	}



	public static boolean validatePasswordByMD5(String plainPassword, String password) {
		System.out.println(plainPassword);
		System.out.println(password);
		String oldPwd =  entryptPasswordByMD5(plainPassword);
		return StringUtils.equals(oldPwd,password);
	}

	/**
	 * 获得活动会话
	 * @return
	 */
	public Collection<Session> getActiveSessions(){
		return sessionDao.getActiveSessions(false);
	}

	//-- Role Service --//

	public Role getRole(String id) {
		return roleDao.get(id);
	}

	public Role getRoleByName(String name) {
		Role r = new Role();
		r.setName(name);
		return roleDao.getByName(r);
	}

	public Role getRoleByEnname(String enname) {
		Role r = new Role();
		r.setEnname(enname);
		return roleDao.getByEnname(r);
	}

	public List<Role> findRole(Role role){
		return roleDao.findList(role);
	}

	public List<Role> findAllRole(){
		return UserUtils.getRoleList();
	}

	@Transactional(readOnly = false)
	public void saveRole(Role role) {
		if (StringUtils.isBlank(role.getId())){
			role.preInsert();
			roleDao.insert(role);
		}else{
			role.preUpdate();
			roleDao.update(role);
		}
		// 更新角色与菜单关联
		roleDao.deleteRoleMenu(role);
		if (role.getMenuList().size() > 0){
			roleDao.insertRoleMenu(role);
		}
		// 更新角色与部门关联
		roleDao.deleteRoleOffice(role);
		if (role.getOfficeList().size() > 0 && "9".equalsIgnoreCase(role.getDataScope())){
			roleDao.insertRoleOffice(role);
		}
		// 清除用户角色缓存
		//UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		//  UserUtils.clearCache();
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public void deleteRole(Role role) {
		roleDao.delete(role);
		// 清除用户角色缓存
		//UserUtils.removeCache(UserUtils.CACHE_ROLE_LIST);
		//UserUtils.clearCache();
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
	}

	@Transactional(readOnly = false)
	public Boolean outUserInRole(Role role, User user) {
		List<Role> roles = user.getRoleList();
		for (Role e : roles){
			if (e.getId().equals(role.getId())){
				roles.remove(e);
				saveUser(user);
				return true;
			}
		}
		return false;
	}

	@Transactional(readOnly = false)
	public User assignUserToRole(Role role, User user) {
		if (user == null){
			return null;
		}
		List<String> roleIds = user.getRoleIdList();
		if (roleIds.contains(role.getId())) {
			return null;
		}
		user.setIsAddRoleFlag("roleFlag");
		user.getRoleList().add(role);
		saveUser(user);
		return user;
	}

	//-- Menu Service --//

	public Menu getMenu(String id) {
		return menuDao.get(id);
	}

	public List<Menu> findAllMenu(){
		return UserUtils.getMenuList();
	}

	@Transactional(readOnly = false)
	public void saveMenu(Menu menu) {

		// 获取父节点实体
		menu.setParent(this.getMenu(menu.getParent().getId()));

		// 获取修改前的parentIds，用于更新子节点的parentIds
		String oldParentIds = menu.getParentIds();

		// 设置新的父节点串
		menu.setParentIds(menu.getParent().getParentIds()+menu.getParent().getId()+",");

		// 保存或更新实体
		if (StringUtils.isBlank(menu.getId())){
			menu.preInsert();
			menuDao.insert(menu);
		}else{
			menu.preUpdate();
			menuDao.update(menu);
		}

		// 更新子节点 parentIds
		Menu m = new Menu();
		m.setParentIds("%,"+menu.getId()+",%");
		List<Menu> list = menuDao.findByParentIdsLike(m);
		for (Menu e : list){
			e.setParentIds(e.getParentIds().replace(oldParentIds, menu.getParentIds()));
			menuDao.updateParentIds(e);
		}
		// 清除用户菜单缓存
		//UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		//UserUtils.clearCache();
//		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
//		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void updateMenuSort(Menu menu) {
		menuDao.updateSort(menu);
		// 清除用户菜单缓存
		//UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
//		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	@Transactional(readOnly = false)
	public void deleteMenu(Menu menu) {
		menuDao.delete(menu);
		// 清除用户菜单缓存
		//UserUtils.removeCache(UserUtils.CACHE_MENU_LIST);
		// 清除权限缓存
		systemRealm.clearAllCachedAuthorizationInfo();
		// 清除日志相关缓存
//		CacheUtils.remove(LogUtils.CACHE_MENU_NAME_PATH_MAP);
	}

	/**
	 * 获取Key加载信息
	 */
	public static boolean printKeyLoadMessage(){
		StringBuilder sb = new StringBuilder();
		sb.append("\r\n======================================================================\r\n");
		sb.append("\r\n    欢迎使用 "+Global.getConfig("productName"));
		sb.append("\r\n======================================================================\r\n");
		System.out.println(sb.toString());
		return true;
	}


	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub

	}


	public List<User> getByPhone(List<String> list) {
		return userDao.getByPhone(list);

	}

	/**
	 * 通过userid集合获取user对象
	 * @param listIds
	 * @return
	 */
	public List<User> findUserByIds(List<String> listIds) {
		return userDao.findUserByIds(listIds);
	}

	/**
	 * 查找用户的企业号id
	 * @param id
	 * @return
	 */
	public String findQyhIdByKiteId(String id) {
		return this.userDao.findQyhUserId(id);
	}

	public List<User> findUserList(String officeId,String organTag){
		return userDao.findUserList(officeId,organTag);
	}

	/**
	 * 无分页查询人员列表 未被选择到的
	 * @param organTag
	 * @return
	 */
	public List<User> findNoSelect(String organTag){
		List<User> list = userDao.findNoSelectList(organTag);
		return list;
	}

	/**
	 * 无分页查询人员列表
	 * @param organTag
	 * @return
	 */
	public List<User> findAllSelectList(){
		List<User> list = userDao.findAllSelectList();
		return list;
	}

	/**
	 * 根据登录名获取用户
	 * @param qyhId
	 * @return
	 */
	public User getUserByQyhUserid(String qyhId) {
		return UserUtils.getByQyhId(qyhId);
	}

	/**
	 * 查找当前有效用户数
	 * @return
	 */
	public int findAllEffectiveUserNum() {
		return this.userDao.findAllEffectiveUserNum();
	}

	/**
	 * 根据类型以及值查找字典标签
	 * @param type
	 * @param value
	 * @return
	 */
	public String findLabelByTypeAndValueStr(String type, String value) {
		return this.dictDao.findLabeByTypeAndValue(type, value);
	}

	/**
	 * 根据用户登录名及密码，密码已经过md5加密
	 * @param userLoginName
	 * @param userPassword
	 * @return
	 */
	public RpcUserCommand findByUserLoginNameAndPassword(String userLoginName, String userPassword) {
		return this.userDao.findByUserLoginNameAndPassword(userLoginName, userPassword);
	}

	/**
	 * 根据类型查找字典列表
	 * @param type
	 * @return
	 */
	public List<Dict> findDictListByType(String type) {
		return this.dictDao.findDictListByTypeNew(type);
	}

}
