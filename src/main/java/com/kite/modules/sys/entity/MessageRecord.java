/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 用户接收短信情况记录Entity
 * @author zhangtao
 * @version 2019-05-13
 */
public class MessageRecord extends DataEntity<MessageRecord> {
	
	private static final long serialVersionUID = 1L;
	private String userName;		// 用户名称
	private String content;
	private String userId;		// 用户账号
	private String userPhone;		// 用户手机号
	private String userEmail;		// userEmail
	private String userLoginName;		// 用户登录名称
	private String isSuccess;		// 最近一次是否发送成功，0成功，-1失败
	private Integer successCount;		// 累计短信成功次数
	private Integer failCount;		// 累计短信失败次数
	private String updateSendMessageDate;		// 最新发送短信时间
	
	public MessageRecord() {
		super();
	}

	public MessageRecord(String id){
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
	
	@ExcelField(title="userEmail", align=2, sort=3)
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
	
	@ExcelField(title="最近一次是否发送成功，0成功，-1失败", align=2, sort=5)
	public String getIsSuccess() {
		return isSuccess;
	}

	public void setIsSuccess(String isSuccess) {
		this.isSuccess = isSuccess;
	}
	
	@ExcelField(title="累计短信成功次数", align=2, sort=6)
	public Integer getSuccessCount() {
		return successCount;
	}

	public void setSuccessCount(Integer successCount) {
		this.successCount = successCount;
	}
	
	@ExcelField(title="累计短信失败次数", align=2, sort=7)
	public Integer getFailCount() {
		return failCount;
	}

	public void setFailCount(Integer failCount) {
		this.failCount = failCount;
	}
	
	@ExcelField(title="最新发送短信时间", align=2, sort=8)
	public String getUpdateSendMessageDate() {
		return updateSendMessageDate;
	}

	public void setUpdateSendMessageDate(String updateSendMessageDate) {
		this.updateSendMessageDate = updateSendMessageDate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}