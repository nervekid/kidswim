/**
 * MouTai
 */
package com.kite.modules.att.entity;


import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 教練員資格Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SysCertificatesCoach extends DataEntity<SysCertificatesCoach> {

	private static final long serialVersionUID = 1L;
	private String coathId;		// 教練員id
	private String qualification;		// 資格名稱
	private String obtainYearMonth;		// 考獲年月

	public SysCertificatesCoach() {
		super();
	}

	public SysCertificatesCoach(String id){
		super(id);
	}

	@ExcelField(title="教練員id", align=2, sort=1)
	public String getCoathId() {
		return coathId;
	}

	public void setCoathId(String coathId) {
		this.coathId = coathId;
	}

	@ExcelField(title="資格名稱", align=2, sort=2)
	public String getQualification() {
		return qualification;
	}

	public void setQualification(String qualification) {
		this.qualification = qualification;
	}

	@ExcelField(title="考獲年月", align=2, sort=3)
	public String getObtainYearMonth() {
		return obtainYearMonth;
	}

	public void setObtainYearMonth(String obtainYearMonth) {
		this.obtainYearMonth = obtainYearMonth;
	}

}