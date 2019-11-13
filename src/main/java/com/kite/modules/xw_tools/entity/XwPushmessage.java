/**
 * MouTai
 */
package com.kite.modules.xw_tools.entity;

import java.util.List;
import com.google.common.collect.Lists;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 消息推送Entity
 * @author czh
 * @version 2017-12-11
 */
public class XwPushmessage extends DataEntity<XwPushmessage> {
	
	private static final long serialVersionUID = 1L;
	private String title;		// 标题
	private String content;		// 内容
	private String status;		// 状态
	private List<XwPushmessageRecord> xwPushmessageRecordList = Lists.newArrayList();		// 子表列表
	private boolean isSelf;		// 是否只查询自己的通知
	private String readFlag;	// 本人阅读状态

	private String readNum;		// 已读
	private String unReadNum;	// 未读

	public XwPushmessage() {
		super();
	}

	public XwPushmessage(String id){
		super(id);
	}

	@ExcelField(title="标题", align=2, sort=1)
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
	
	@ExcelField(title="内容", align=2, sort=2)
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	@ExcelField(title="状态", align=2, sort=3)
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public List<XwPushmessageRecord> getXwPushmessageRecordList() {
		return xwPushmessageRecordList;
	}

	public void setXwPushmessageRecordList(List<XwPushmessageRecord> xwPushmessageRecordList) {
		this.xwPushmessageRecordList = xwPushmessageRecordList;
	}

	public boolean isSelf() {
		return isSelf;
	}

	public void setSelf(boolean self) {
		isSelf = self;
	}

	public String getReadFlag() {
		return readFlag;
	}

	public void setReadFlag(String readFlag) {
		this.readFlag = readFlag;
	}

	public String getReadNum() {
		return readNum;
	}

	public void setReadNum(String readNum) {
		this.readNum = readNum;
	}

	public String getUnReadNum() {
		return unReadNum;
	}

	public void setUnReadNum(String unReadNum) {
		this.unReadNum = unReadNum;
	}
}