/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * token配置信息Entity
 * @author cxh
 * @version 2019-01-24
 */
public class SysTToken extends DataEntity<SysTToken> {
	
	private static final long serialVersionUID = 1L;
	private String name;		// 名称
	private String pid;		// pid
	private String secret;		// secret
	private String ip; //
	private int accessNum;

	private String username;		// 人员/部门
	private String token;		// token码
	private String workspaceId;		// TAPD项目ID
	private String projectName;
	private String describe;		// 授权描述
	private String useflag;		// 0:停 1:启
	private String createUserName;
	private String updateUserName;


	
	public SysTToken() {
		super();
	}

	public SysTToken(String id){
		super(id);
	}

	@ExcelField(title="名称", align=2, sort=1)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@ExcelField(title="pid", align=2, sort=2)
	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}
	
	@ExcelField(title="secret", align=2, sort=3)
	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getAccessNum() {
		return accessNum;
	}

	public void setAccessNum(int accessNum) {
		this.accessNum = accessNum;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getWorkspaceId() {
		return workspaceId;
	}

	public void setWorkspaceId(String workspaceId) {
		this.workspaceId = workspaceId;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public String getDescribe() {
		return describe;
	}

	public void setDescribe(String describe) {
		this.describe = describe;
	}

	public String getUseflag() {
		return useflag;
	}

	public void setUseflag(String useflag) {
		this.useflag = useflag;
	}

	public String getCreateUserName() {
		return createUserName;
	}

	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}

	public String getUpdateUserName() {
		return updateUserName;
	}

	public void setUpdateUserName(String updateUserName) {
		this.updateUserName = updateUserName;
	}
}