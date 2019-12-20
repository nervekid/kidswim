package com.kite.modules.att.command;

/**
 * 泳班系统-远程调用-登录信息
 * @author lyb 2019-12-20
 *
 */
public class RpcUserCommand {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 用户id*/
	private String id;

	/**2.[不可空] 用户登录名*/
	private String loginName;

	/**3.[不可空] 用户名称*/
	private String userName;

	/**4.[不可空] 用户手机号码*/
	private String phone;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
