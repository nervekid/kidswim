/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 邮件发送情况记录Entity
 * @author wusida
 * @version 2019-10-24
 */
public class MessageRecordEmail extends DataEntity<MessageRecordEmail> {
	
	private static final long serialVersionUID = 1L;
	private String userName;		// 用户名称
	private String userId;		// 用户账号
	private String userPhone;		// 用户手机号
	private String userEmail;		// 用户邮箱
	private String userLoginName;		// 用户登录名称
	private String content;		// 内容
	private String isSuccess;		// 最近一次是否发送成功，0成功，-1失败
	private Integer successCount;		// 累计邮件成功次数
	private Integer failCount;		// 累计邮件失败次数
	private String updateSendMessageDate;		// 最新发送邮件时间
	
	public MessageRecordEmail() {
		super();
	}

	public MessageRecordEmail(String id){
		super(id);
	}

	@ExcelField(title="用户名称", align=2, sort=0)
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@ExcelField(title="用户账号", align=2, sort=1)
	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@ExcelField(title="用户手机号", align=2, sort=2)
	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
	@ExcelField(title="用户邮箱", align=2, sort=3)
	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	
	@ExcelField(title="用户登录名称", align=2, sort=4)
	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}
	
	@ExcelField(title="内容", align=2, sort=5)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="最近一次是否发送成功，0成功，-1失败", align=2, sort=6)
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@ExcelField(title="累计邮件成功次数", align=2, sort=7)
	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	
	@ExcelField(title="累计邮件失败次数", align=2, sort=8)
	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}
	
	@ExcelField(title="最新发送邮件时间", align=2, sort=9)
	public String getUpdateSendMessageDate() {
		return updateSendMessageDate;
	}

	public void setUpdateSendMessageDate(String updateSendMessageDate) {
		this.updateSendMessageDate = updateSendMessageDate;
	}
	
}