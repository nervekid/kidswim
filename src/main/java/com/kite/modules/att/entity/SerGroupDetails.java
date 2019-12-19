/**
 * MouTai
 */
package com.kite.modules.att.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 分组明细Entity
 * @author lyb
 * @version 2019-12-19
 */
public class SerGroupDetails extends DataEntity<SerGroupDetails> {
	
	private static final long serialVersionUID = 1L;
	private String groupId;		// 分组id
	private String saleId;		// 销售单id
	
	public SerGroupDetails() {
		super();
	}

	public SerGroupDetails(String id){
		super(id);
	}

	@ExcelField(title="分组id", align=2, sort=1)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	
	@ExcelField(title="销售单id", align=2, sort=2)
	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}
	
}