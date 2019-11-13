/**
 * MouTai
 */
package com.kite.modules.xw_tools.entity;

import com.kite.modules.sys.entity.User;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 消息推送Entity
 * @author czh
 * @version 2017-12-11
 */
public class XwPushmessageRecord extends DataEntity<XwPushmessageRecord> {
	
	private static final long serialVersionUID = 1L;
	private XwPushmessage pushmeaasgeId;		// 通知通告ID
	private User user;		// 接受人
	private String readFlag;		// 阅读标记
	private Date readDate;		// 阅读时间
	
	public XwPushmessageRecord() {
		super();
	}

	public XwPushmessageRecord(String id){
		super(id);
	}

	public XwPushmessageRecord(XwPushmessage pushmeaasgeId){
		this.pushmeaasgeId= pushmeaasgeId;
	}
	@ExcelField(title="通知通告ID", align=2, sort=1)
	public XwPushmessage getPushmeaasgeId() {
		return pushmeaasgeId;
	}

	public void setPushmeaasgeId(XwPushmessage pushmeaasgeId) {
		this.pushmeaasgeId = pushmeaasgeId;
	}
	
	@ExcelField(title="接受人", fieldType=User.class, value="user.name", align=2, sort=2)
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	@ExcelField(title="阅读标记", align=2, sort=3)
	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}
	
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="阅读时间", align=2, sort=4)
	public Date getReadDate() {
		return readDate;
	}

	public void setReadDate(Date readDate) {
		this.readDate = readDate;
	}
	
}