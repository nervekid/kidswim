/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

import com.kite.common.service.BaseService;
import com.kite.common.sms.SMSUtils;
import com.kite.common.utils.SpringContextHolder;
import com.kite.modules.sys.dao.MenuDao;
import com.kite.modules.sys.dao.OfficeDao;
import com.kite.modules.sys.dao.RoleDao;
import com.kite.modules.sys.dao.SysOrganizationalDao;
import com.kite.modules.sys.dao.UserDao;
import com.kite.modules.sys.entity.Menu;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.SysOrganizational;
import com.kite.modules.sys.entity.User;
import com.kite.modules.sys.security.SystemAuthorizingRealm.Principal;
import com.kite.modules.user.dao.user.ComTDatasourceDao;
import com.kite.modules.user.entity.user.ComTDatasource;

import redis.clients.jedis.exceptions.JedisException;

/**
 * 用户工具类
 * @author kite
 * @version 2013-12-05
 */
public class UserUtils {

	private static UserDao userDao = SpringContextHolder.getBean(UserDao.class);
	private static RoleDao roleDao = SpringContextHolder.getBean(RoleDao.class);
	private static MenuDao menuDao = SpringContextHolder.getBean(MenuDao.class);
	private static SysOrganizationalDao sysOrganizationalDao = SpringContextHolder.getBean(SysOrganizationalDao.class);

	private static ComTDatasourceDao comtdatasourceDao = SpringContextHolder.getBean(ComTDatasourceDao.class);

	//数据源
	private static OfficeDao officeDao = SpringContextHolder.getBean(OfficeDao.class);

	public static final String USER_CACHE = "userCache";
	public static final String USER_CACHE_ID_ = "id_";
	public static final String USER_CACHE_LOGIN_NAME_ = "ln";
	public static final String USER_CACHE_LIST_BY_OFFICE_ID_ = "oid_";

	public static final String CACHE_ROLE_LIST = "roleList";
	public static final String CACHE_MENU_LIST = "menuList";
	public static final String CACHE_AREA_LIST = "areaList";
	public static final String CACHE_OFFICE_LIST = "officeList";
	public static final String CACHE_OFFICE_ALL_LIST = "officeAllList";

	public static final int expireInt = 30;


	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<ComTDatasource> getComTDatasourceList(){
		ComTDatasource role = new ComTDatasource();
		List<ComTDatasource> comtdatasourceList = comtdatasourceDao.findList(role);
		return comtdatasourceList;
	}

	/**
	 * 根据ID获取用户R
	 * @param id
	 * @return 取不到返回null
	 */
	public static User get(String id){
		User user = userDao.get(id);
		if (user == null){
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}


	/**
	 * 根据企业号id获取用户
	 * @param
	 * @return 取不到返回null
	 */
	public static User getByQyhId(String qyhId){

		String id = userDao.getQyhUserId(qyhId);
		User user = userDao.get(id);
		if (user == null){
			return null;
		}
		return user;
	}

	/**
	 * 根据登录名获取用户
	 * @param loginName
	 * @return 取不到返回null
	 */
	public static User getByLoginName(String loginName){
		// 20181115 cxh 修改使用redis进行数据缓存
		User user = userDao.getByLoginName(new User(null, loginName));
		if (user == null){
			return null;
		}
		user.setRoleList(roleDao.findList(new Role(user)));
		return user;
	}

	/**
	 * 清除当前用户缓存
	 */
/*	public static void clearCache(){
		removeCache(CACHE_ROLE_LIST+"*");
		removeCache(CACHE_MENU_LIST+"*");
		removeCache(CACHE_AREA_LIST+"*");
		removeCache(CACHE_OFFICE_LIST+"*");
		removeCache(CACHE_OFFICE_ALL_LIST+"*");
		UserUtils.clearCache(getUser());
	}*/

	/**
	 * 清除指定用户缓存
	 * @param user
	 */
/*	public static void clearCache(User user){
		Jedis jedis =null;
		try {
			jedis =
			.getResource();
			jedis.del(USER_CACHE_ID_ + user.getId());
			jedis.del(USER_CACHE_LOGIN_NAME_ + user.getLoginName());
			jedis.del(USER_CACHE_LOGIN_NAME_ + user.getOldLoginName());
			if (user.getOffice() != null && user.getOffice().getId() != null){
				jedis.del(USER_CACHE_LIST_BY_OFFICE_ID_ + user.getOffice().getId());
			}
		} catch (JedisException e) {
			e.printStackTrace();
		} finally {

			.returnResource(jedis);
		}
	}*/

	/**
	 * 获取当前用户
	 * @return 取不到返回 new User()
	 */
	public static User getUser(){
		Principal principal = getPrincipal();
		if (principal!=null){
			User user = get(principal.getId());
			if (user != null){
				return user;
			}
			return new User();
		}
		// 如果没有登录，则返回实例化空的User对象。
		return new User();
	}

	/**
	 * 获取当前用户角色列表
	 * @return
	 */
	public static List<Role> getRoleList(){
		User user = getUser();
		List<Role> roleList =null;
		if (user.isAdmin()){
			roleList = roleDao.findAllList(new Role());
		}else{
			Role role = new Role();
			role.getSqlMap().put("dsf", BaseService.dataScopeFilter(user.getCurrentUser(), "o", "u"));
			roleList = roleDao.findList(role);
		}
		return roleList;
	}

	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static List<Menu> getMenuList(){
		List<Menu> menuList = new ArrayList<>();
		User user = getUser();
		if (user.isAdmin()){
			menuList = menuDao.findAllList(new Menu());
		}else{
			Menu m = new Menu();
			m.setUserId(user.getId());
			menuList = menuDao.findByUserId(m);
		}
		return menuList;
	}

	/**
	 * 获取当前用户授权菜单
	 * @return
	 */
	public static Menu getTopMenu(){
		Menu topMenu =  getMenuList().get(0);
		return topMenu;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeList(String organTag, String unOrganTag){
		List<Office> officeList  = null;
		try {
			User user = UserUtils.getUser();
                Office office = new Office();
                office.setOrganTag(organTag);
                office.setUnOrganTag(unOrganTag);
                if (user.isAdmin()){
                    officeList = officeDao.findAllList(office);
                }else{
                    officeList = officeDao.findList(office);
                }
		} catch (JedisException e) {
			e.printStackTrace();
		}
		return officeList;
	}

	/**
	 * 获取当前用户有权限访问的部门
	 * @return
	 */
	public static List<Office> getOfficeAllList(String organTag, String unOrganTag){
		// 20181115 cxh 修改使用redis进行数据缓存
		Office office = new Office();
		office.setOrganTag(organTag);
		office.setUnOrganTag(unOrganTag);
		List<Office> officeList = officeDao.findAllList(office);
		return officeList;
	}

	/**
	 * 获取授权主要对象
	 */
	public static Subject getSubject(){
		return SecurityUtils.getSubject();
	}

	/**
	 * 获取当前登录者对象
	 */
	public static Principal getPrincipal(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Principal principal = (Principal)subject.getPrincipal();
			if (principal != null){
				return principal;
			}
//			subject.logout();
		}catch (UnavailableSecurityManagerException e) {

		}catch (InvalidSessionException e){

		}
		return null;
	}

	public static Session getSession(){
		try{
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession(false);
			if (session == null){
				session = subject.getSession();
			}
			if (session != null){
				return session;
			}
//			subject.logout();
		}catch (InvalidSessionException e){

		}
		return null;
	}

	// ============== User Cache ==============

//	public static Object getCache(String key) {
//		return getCache(key, null);
//	}

//	public static Object getCache(String key, Object defaultValue) {
////		Object obj = getCacheMap().get(key);
//		Object obj = getSession().getAttribute(key);
//		return obj==null?defaultValue:obj;
//	}

//	public static void putCache(String key, Object value) {
////		getCacheMap().put(key, value);
//		getSession().setAttribute(key, value);
//	}

	/*public static void removeCache(String key) {
		Jedis jedis =getResource();
		Set<String> keys = jedis.keys(key + "*");
		Iterator<String> it = keys.iterator();
		while(it.hasNext()){
			String keyStr = it.next();
			jedis.del(keyStr);
		}
	}*/

	public static String getTime(Date date){
		StringBuffer time = new StringBuffer();
        Date date2 = new Date();
        long temp = date2.getTime() - date.getTime();
        long days = temp / 1000 / 3600/24;                //相差小时数
        if(days>0){
        	time.append(days+"天");
        }
        long temp1 = temp % (1000 * 3600*24);
        long hours = temp1 / 1000 / 3600;                //相差小时数
        if(days>0 || hours>0){
        	time.append(hours+"小时");
        }
        long temp2 = temp1 % (1000 * 3600);
        long mins = temp2 / 1000 / 60;                    //相差分钟数
        time.append(mins + "分钟");
        return  time.toString();
	}


	//发送注册码
	public static String sendRandomCode(String uid, String pwd, String tel, String randomCode) throws IOException {
		//发送内容
		String content = "您的验证码是："+randomCode+"，有效期30分钟，请在有效期内使用。";

		return SMSUtils.send(uid, pwd, tel, content);

	}

	//注册用户重置密码
	public static String sendPass(String uid, String pwd, String tel, String password) throws IOException {
		//发送内容
		String content = "您的新密码是："+password+"，请登录系统，重新设置密码。";
		return SMSUtils.send(uid, pwd, tel, content);

	}

	/**
	 * 导出Excel调用,根据姓名转换为ID
	 */
	public static User getByUserName(String name){
		User u = new User();
		u.setName(name);
		List<User> list = userDao.findList(u);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new User();
		}
	}
	/**
	 * 导出Excel使用，根据名字转换为id
	 */
	public static Office getByOfficeName(String name){
		//TODO 取第一个？？？
		Office o = new Office();
		o.setName(name);
		List<Office> list = officeDao.findList(o);
		if(list.size()>0){
			return list.get(0);
		}else{
			return new Office();
		}
	}
	/**
	 * t
	 * @return
	 */
	public static Menu getMenuById(String id){
		Menu menu = menuDao.get(id);
		return menu;
	}

	/**
	 * 查找角色名称下的所有用户ID
	 * @param name
	 * @return
	 */
	public static int getUserIdByRoleName(String name, String userId) {
		return roleDao.findRoleUserId(name, userId);
	}

	/**
	 * 查找用户归属的一级部门
	 * @param
	 * @return
	 */
	public static Office getDepartment(User user, String orgTag){

		List<SysOrganizational> sysOrganizationals = sysOrganizationalDao.findOfficeIdByUserIdAndOrganTag(user.getId(), orgTag);
		Office o = null;
		if(sysOrganizationals!=null && sysOrganizationals.size()>0){
			o = sysOrganizationals.get(0).getOffice();
			Office office = officeDao.get(o.getId());
			if(office == null) {
				return null;
			}
			String levOne[] = office.getParentIds().split(",");
			if(levOne.length>=3){
				office = officeDao.get(levOne[2]);
			}
			// 刘汉威的一级部门特殊处理
			if(user.getName().equals("刘汉威")){
				office = officeDao.get("11329544-3eeb-11e8-961c-005056aed64e");
			}
			return office;
		}
		return null;
	}

	/**
	 * 查找部门归属的一级部门
	 * @param
	 * @return
	 */
	public static Office getDepartment(String officeId){
		Office o = officeDao.get(officeId);
		if(o == null) {
			return null;
		}
		String levOne[] = o.getParentIds().split(",");
		if(levOne.length>=3){
			o = officeDao.get(levOne[2]);
		}
		return o;
	}

	/*public static void main(String[] args) {
		clearCache();
	}*/

	/**
	 * 菜单实体是否重复使用
	 * @param entityOrgId
	 * @return
	 */
	public static boolean isMenuRepeatEntity(String entityOrgId) {
		if(menuDao.isEntityRepeat(entityOrgId) > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 根据MenuId查找组织架构标记
	 * @param menuId
	 * @return
	 */
	public static String findOrganTagByMenuId(String menuId) {
		return menuDao.findOrganTagByMenuId(menuId);
	}


    /**
     * 根据用户ID查找姓名
     * @param userId
     * @return userName
     */
    public static String getUserNameByUserId(String userId) {
    	if(null==userId||"".equals(userId)){
    		return "";
		}else if(null==userDao.get(userId)){//若根据用户ID找不到用户，页面有可能会报错，进行预先处理
			return "";
		}
        return userDao.get(userId).getName();
    }


	/**
	 * 获取所有用户的基本信息供后期使用
	 * @param
	 * @return userName
	 */
	public static List<User> getAllUsers() {
		List<User> users = new ArrayList<>();
		users = userDao.findAllList(new User());
		return users;
	}

	/**
	 * ehr系统入职，修改kite系统登录标记
	 * @param userId
	 * @param password
	 * @param email
	 */
	public static void validEhrLoginKite(String userId, String password, String email) {
		userDao.ehrValidLogin(userId, password, email);
	}

}
