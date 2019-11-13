/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.user.entity.user;

import org.hibernate.validator.constraints.Length;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 数据源Entity
 * @author czh
 * @version 2017-08-24
 */
public class ComTDatasource extends DataEntity<ComTDatasource> {
	
	private static final long serialVersionUID = 1L;
	private String dataflag;		// dataflag
	private String datasourcename;		// datasourcename
	
	public ComTDatasource() {
		super();
	}

	public ComTDatasource(String id){
		super(id);
	}

	@Length(min=0, max=255, message="dataflag长度必须介于 0 和 255 之间")
	@ExcelField(title="dataflag", align=2, sort=0)
	public String getDataflag() {
		return dataflag;
	}

	public void setDataflag(String dataflag) {
		this.dataflag = dataflag;
	}
	
	@Length(min=0, max=255, message="datasourcename长度必须介于 0 和 255 之间")
	@ExcelField(title="datasourcename", align=2, sort=1)
	public String getDatasourcename() {
		return datasourcename;
	}

	public void setDatasourcename(String datasourcename) {
		this.datasourcename = datasourcename;
	}
	
}