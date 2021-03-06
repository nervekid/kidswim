/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.entity;

import java.util.Date;
import java.util.List;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.collect.Lists;
import com.kite.common.config.Global;
import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.Collections3;
import com.kite.common.utils.StringUtils;
import com.kite.common.utils.excel.annotation.ExcelField;
import com.kite.common.utils.excel.fieldtype.RoleListType;
import com.kite.modules.sys.utils.UserUtils;

/**
 * 用户Entity
 * @author kite
 * @version 2013-12-05
 */
public class User extends DataEntity<User> {
	private static final Logger logger = LoggerFactory.getLogger(User.class);

	private static final long serialVersionUID = 1L;
	private Office company;	// 归属公司
	private Office office;	// 归属部门
	private String loginName;// 登录名
	private String password;// 密码
	private String no;		// 工号
	private String name;	// 姓名
	private String email;	// 邮箱
	private String phone;	// 电话
	private String mobile;	// 手机
	private String userType;// 用户类型
	private String loginIp;	// 最后登陆IP
	private Date loginDate;	// 最后登陆日期
	private String loginFlag;	// 是否允许登陆
	private String formalFlag;	// 是否已经转正
	private String photo;	// 头像
	private String qrCode;	//二维码
	private String oldLoginName;// 原登录名
	private String newPassword;	// 新密码
	private String sign;//签名

	private String oldLoginIp;	// 上次登陆IP
	private Date oldLoginDate;	// 上次登陆日期

	private Role role;	// 根据角色查询用户条件

	private String tmpPwd; //临时密码

	private Date tmpPwdCreateDate;//临时密码创建时间

	private List<String> listId;	//id主键集合用于搜索

	private List<Role> roleList = Lists.newArrayList(); // 拥有角色列表

	private String roleNameStr;	//角色名称

	private String listRoleId;	//角色id

	private String token;

	private String position;//职位；

	private String enable; //是否可用

	private String positionId; //职位id


	private String isAddRoleFlag; //是否是角色分配人员的方法调用，仅用作标记,不做导出，查询等其他用途

	public String getIsAddRoleFlag() {
		return isAddRoleFlag;
	}

	public void setIsAddRoleFlag(String isAddRoleFlag) {
		this.isAddRoleFlag = isAddRoleFlag;
	}

	public String getListRoleId() {
		return listRoleId;
	}

	public void setListRoleId(String listRoleId) {
		this.listRoleId = listRoleId;
	}

	public String getRoleNameStr() {
		return roleNameStr;
	}

	public void setRoleNameStr(String roleNameStr) {
		this.roleNameStr = roleNameStr;
	}

	public List<String> getListId() {
		return listId;
	}

	public void setListId(List<String> listId) {
		this.listId = listId;
	}

	public User() {
		super();
		this.loginFlag = Global.YES;
	}

	public User(String id){
		super(id);
	}

	public User(String id, String loginName){
		super(id);
		this.loginName = loginName;
	}

	public User(Role role){
		super();
		this.role = role;
	}

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public String getLoginFlag() {
		return loginFlag;
	}

	public void setLoginFlag(String loginFlag) {
		this.loginFlag = loginFlag;
	}

	public String getFormalFlag() {
		return formalFlag;
	}

	public void setFormalFlag(String formalFlag) {
		this.formalFlag = formalFlag;
	}
	@Override
	@ExcelField(title="ID", type=1, align=2, sort=1)
	public String getId() {
		return id;
	}

	public Office getCompany() {
		return company;
	}

	public void setCompany(Office company) {
		this.company = company;
	}

	public Office getOffice() {
		return office;
	}

	public void setOffice(Office office) {
		this.office = office;
	}

	@Length(min=1, max=100, message="登录名长度必须介于 1 和 100 之间")
	@ExcelField(title="登录名", align=2, sort=30)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	@JsonIgnore
	@Length(min=1, max=100, message="密码长度必须介于 1 和 100 之间")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Length(min=1, max=100, message="姓名长度必须介于 1 和 100 之间")
	@ExcelField(title="姓名", align=2, sort=40)
	public String getName() {
		return name;
	}

	@ExcelField(title="工号", align=2, sort=45)
	public String getNo() {
		return no;
	}

	public void setNo(String no) {
		this.no = no;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Email(message="邮箱格式不正确")
	@Length(min=0, max=200, message="邮箱长度必须介于 1 和 200 之间")
	@ExcelField(title="邮箱", align=1, sort=50)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Length(min=0, max=200, message="电话长度必须介于 1 和 200 之间")
	@ExcelField(title="电话", align=2, sort=60)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@Length(min=0, max=200, message="手机长度必须介于 1 和 200 之间")
	@ExcelField(title="手机", align=2, sort=70)
	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	@Override
	@ExcelField(title="备注", align=1, sort=900)
	public String getRemarks() {
		return remarks;
	}

	@Length(min=0, max=100, message="用户类型长度必须介于 1 和 100 之间")
	@ExcelField(title="用户类型", align=2, sort=80, dictType="sys_user_type")
	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}
	@Override
	@ExcelField(title="创建时间", type=0, align=1, sort=90)
	public Date getCreateDate() {
		return createDate;
	}

	@ExcelField(title="最后登录IP", type=1, align=1, sort=100)
	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="最后登录日期", type=1, align=1, sort=110)
	public Date getLoginDate() {
		return loginDate;
	}

	public void setLoginDate(Date loginDate) {
		this.loginDate = loginDate;
	}

	public String getOldLoginName() {
		return oldLoginName;
	}

	public void setOldLoginName(String oldLoginName) {
		this.oldLoginName = oldLoginName;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public String getOldLoginIp() {
		if (oldLoginIp == null){
			return loginIp;
		}
		return oldLoginIp;
	}

	public void setOldLoginIp(String oldLoginIp) {
		this.oldLoginIp = oldLoginIp;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	public Date getOldLoginDate() {
		if (oldLoginDate == null){
			return loginDate;
		}
		return oldLoginDate;
	}

	public void setOldLoginDate(Date oldLoginDate) {
		this.oldLoginDate = oldLoginDate;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}



	@ExcelField(title="拥有角色", align=1, sort=800, fieldType=RoleListType.class)
	public List<Role> getRoleList() {
		return roleList;
	}

	public void setRoleList(List<Role> roleList) {
		this.roleList = roleList;
	}

	@JsonIgnore
	public List<String> getRoleIdList() {
		List<String> roleIdList = Lists.newArrayList();
		for (Role role : roleList) {
			roleIdList.add(role.getId());
		}
		return roleIdList;
	}

	public void setRoleIdList(List<String> roleIdList) {
		roleList = Lists.newArrayList();
		for (String roleId : roleIdList) {
			Role role = new Role();
			role.setId(roleId);
			roleList.add(role);
		}
	}

	/**
	 * 用户拥有的角色名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleNames() {
		return Collections3.extractToString(roleList, "name", ",");
	}
	/**
	 * 用户拥有的角色英文名称字符串, 多个角色名称用','分隔.
	 */
	public String getRoleEnNames() {
		return Collections3.extractToString(roleList, "enname", ",");
	}

	public boolean isAdmin(){

		return isAdmin(this.id);
	}

	//根据角色判断是否超级管理员
	public boolean isAdminByRole() {

		String str = StringUtils.isEmpty(getRoleEnNames())?"":getRoleEnNames();
		String[] arr = str.split(",");
		boolean flag = false;
		if(arr.length == 0) {
			return flag;
		}else {
			for (String s : arr) {
				if(StringUtils.equals("admin",s)) {
					flag = true;
				}
			}
		}

		return flag;
	}

	public boolean ifRoleAdmin() {
		String str = StringUtils.isEmpty(getRoleEnNames())?"":getRoleEnNames();

		String[] arr = str.split(",");
		boolean flag = false;
		if(arr.length == 0) {
			return flag;
		}else {
			for (String s : arr) {
				if(StringUtils.equals("role-admin",s)) {
					flag = true;
				}
			}
		}

		return flag;
	}

	/**
	 * 是否是开发管理者、配置菜单权限、Dev_Admin
	 * @return
	 */
	public boolean ifRoleDevAdmin() {
		String str = UserUtils.getUser().getRoleEnNames();
		String[] arr = str.split(",");
		boolean flag = false;
		if(arr.length == 0) {
			return flag;
		}else {
			for (String s : arr) {
				if(StringUtils.equals("Dev_Admin",s)) {
					flag = true;
				}
			}
		}
		return flag;
	}

	public static boolean isAdmin(String id){
		//return id != null && "1".equals(id);
		logger.debug("id----->"+id);
		User u = new User();
		return u.ifRoleDevAdmin();
	}

	@Override
	public String toString() {
		return id;
	}

	public void setQrCode(String qrCode) {
		this.qrCode = qrCode;
	}

	public String getQrCode() {
		return qrCode;
	}

	/**
	 * @param sign the sign to set
	 */
	public void setSign(String sign) {
		this.sign = sign;
	}

	/**
	 * @return the sign
	 */
	public String getSign() {
		return sign;
	}

	public String getTmpPwd() {
		return tmpPwd;
	}

	public void setTmpPwd(String tmpPwd) {
		this.tmpPwd = tmpPwd;
	}

	public Date getTmpPwdCreateDate() {
		return tmpPwdCreateDate;
	}

	public void setTmpPwdCreateDate(Date tmpPwdCreateDate) {
		this.tmpPwdCreateDate = tmpPwdCreateDate;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public String getEnable() {
		return enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

	public String getPositionId() {
		return positionId;
	}

	public void setPositionId(String positionId) {
		this.positionId = positionId;
	}

}