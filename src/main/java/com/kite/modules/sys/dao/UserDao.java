/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.Role;
import com.kite.modules.sys.entity.User;

/**
 * 用户DAO接口
 * @author kite
 * @version 2014-05-16
 */
@MyBatisDao
public interface UserDao extends CrudDao<User> {

	/**
	 * 根据登录名称查询用户
	 * @param user
	 * @return
	 */
	public User getByLoginName(User user);

	/**
	 * 根据用户名称查询用户
	 * @param name
	 * @return
	 */
	public User getByName(@Param("name") String name);

	/**
	 * 根据登录名获取用户
	 * @param name
	 * @return
	 */
	public String getByLoginNameVer(@Param("name") String name, @Param("flag") String flag);

	/**
	 * 通过OfficeId获取用户列表，仅返回用户id和name（树查询用户时用）
	 * @param user
	 * @return
	 */
	public List<User> findUserByOfficeId(User user);

	/**
	 * 查询全部用户数目
	 * @return
	 */
	public long findAllCount(User user);

	/**
	 * 更新用户密码
	 * @param user
	 * @return
	 */
	public int updatePasswordById(User user);

	/**
	 * 更新登录信息，如：登录IP、登录时间
	 * @param user
	 * @return
	 */
	public int updateLoginInfo(User user);

	/**
	 * 删除用户角色关联数据
	 * @param user
	 * @return
	 */
	public int deleteUserRole(User user);

	/**
	 * 插入用户角色关联数据
	 * @param user
	 * @return
	 */
	public int insertUserRole(User user);

	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 */
	public int updateUserInfo(User user);


	/**
	 *
	 * 查询用户-->用来添加到常用联系人
	 *
	 */
	public List<User> searchUsers(User user);

	/**
	 *
	 */

	public List<User>  findListByOffice(User user);


	public User  findUserByName(User user);

	public List<User> findUserByRoleName(@Param("roleName") String roleName);

	/**
	 * 通过登录名称以及邮件获取用户信息
	 * @param user
	 * @return
	 */
	public User getByLoginNameAndEmail(User user);

	public List<User> getByPhone(List<String> list);

    List<User> findUserByIds(List<String> list);

    List<Role> getRoleByUserId(@RequestParam(value = "userId") String userId);

    /**
     * 根据企业号id获取kite的UserId
     * @param qyhId
     * @return
     */
    String getQyhUserId(@RequestParam(value = "qyhId") String qyhId);

    List<User> findUserList(@Param("officeid") String officeid, @Param("organTag") String organTag);


	List<User> findUsersByOfficeIds(@Param("officeids") List<String> ids, @Param("organTag") String organTag);

    String getUserNameByUserId(@Param("userId") String userID);


	/**
	 * 根据企业号id获取kite的UserId
	 * @param userId
	 * @return
	 */
	String getQyhUserIdByUserId(@RequestParam(value = "userId") String userId);

	/**
	 * 新增企业微信用户与Kite平台用户关联管理表数据1
	 * @param userId
	 */
	void updateEmailQyhUser(@Param(value = "userId") String userId, @Param("emailaccount") String emailaccount);


	/**
	 * 查询企业微信用户与Kite平台用户关联管理表数据
	 * @param userId
	 */
	String getEmailQyhUser(@Param(value = "userId") String userId);

	/**
	 * 查询企业微信用户与Kite平台用户关联管理表 --密码
	 * @param userId
	 */
	String getPasswordEmailQyhUser(@Param(value = "userId") String userId);

	String getTencentEmailPassword();

	public String findQyhUserId(@Param("id") String id);

	/**
	 * 删除企业微信用户与Kite平台用户关联管理表数据
	 * @param
	 */
	void deleteQyhUser(@Param("qywxUserId") String qywxUserId);


	public int getUserCountByOfficeId(@Param("officeId") String officeId);


	List<User> findNoSelectList(@Param("organTag") String organTag);

	List<User> findAllSelectList();

	public int getCountByUserId(@Param("userId") String userId);

	/**
	 * 查找有效用户数
	 * @return
	 */
	public int findAllEffectiveUserNum();

	/**
	 * ehr系统入职，修改kite系统登录标记
	 * login_flag 修改为1
	 * del_flag 修改为0
	 * enable 修改为1
	 * @param userId
	 */
	public void ehrValidLogin(@Param("userId") String userId, @Param("password") String password, @Param("email") String email);

	/**
	 * 根据手机号和姓名获取用户信息
	 * @param phone
	 * @param name
	 */
	public User selectUserByPhoneAndName(@Param("phone") String phone, @Param("name") String name);
}
