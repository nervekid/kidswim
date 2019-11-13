/**
 * MouTai
 */
package com.kite.modules.sys.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * xuanwuEntity
 * @author cxh
 * @version 2017-12-27
 */
public class XuanwuCheck extends DataEntity<XuanwuCheck> {
	
	private static final long serialVersionUID = 1L;
	private String xuanwuu;		// xuanwuu
	private String xuanwup;		// xuanwup
	
	public XuanwuCheck() {
		super();
	}

	public XuanwuCheck(String id){
		super(id);
	}

	@ExcelField(title="xuanwuu", align=2, sort=0)
	public String getXuanwuu() {
		return xuanwuu;
	}

	public void setXuanwuu(String xuanwuu) {
		this.xuanwuu = xuanwuu;
	}
	
	@ExcelField(title="xuanwup", align=2, sort=1)
	public String getXuanwup() {
		return xuanwup;
	}

	public void setXuanwup(String xuanwup) {
		this.xuanwup = xuanwup;
	}
	
}