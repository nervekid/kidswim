/**
 * MouTai
 */
package com.kite.modules.sys.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 系统会话Entity
 * @author lyb
 * @version 2018-03-15
 */
public class SessionSituation extends DataEntity<SessionSituation> {
	
	private static final long serialVersionUID = 1L;
	private String sessionId;		// 会话id
	private String userName;		// 用户名称
	private Date createTime;		// 会话创建时间
	private Date stopTime;		// 会话退出时间
	private Date expireTime;		// 会话超时时间
	private Integer planExitTime;		// 计划会话存活时间
	private Integer actualExitTime;		// 实际会话存活时间
	private String isAbnormal;		// 是否异常
	
	public SessionSituation() {
		super();
	}

	public SessionSituation(String id){
		super(id);
	}

	@ExcelField(title="会话id", align=2, sort=1)
	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}
	
	@ExcelField(title="用户名称", align=2, sort=2)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会话创建时间", align=2, sort=3)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会话退出时间", align=2, sort=4)
	public Date getStopTime() {
		return stopTime;
	}

	public void setStopTime(Date stopTime) {
		this.stopTime = stopTime;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="会话超时时间", align=2, sort=5)
	public Date getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(Date expireTime) {
		this.expireTime = expireTime;
	}
	
	@ExcelField(title="计划会话存活时间", align=2, sort=6)
	public Integer getPlanExitTime() {
		return planExitTime;
	}

	public void setPlanExitTime(Integer planExitTime) {
		this.planExitTime = planExitTime;
	}
	
	@ExcelField(title="实际会话存活时间", align=2, sort=7)
	public Integer getActualExitTime() {
		return actualExitTime;
	}

	public void setActualExitTime(Integer actualExitTime) {
		this.actualExitTime = actualExitTime;
	}
	
	@ExcelField(title="是否异常", align=2, sort=8)
	public String getIsAbnormal() {
		return isAbnormal;
	}

	public void setIsAbnormal(String isAbnormal) {
		this.isAbnormal = isAbnormal;
	}
	
}