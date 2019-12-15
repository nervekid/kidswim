/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 教練員Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SysBaseCoach extends DataEntity<SysBaseCoach> {

	private static final long serialVersionUID = 1L;
	private String code;		// 教練編碼 C0001 C0002 自增字段
	private String nameCn;		// 中文名
	private String nameEn;		// 英文名
	private String sex;		// 性別 字典枚舉 sex_flag 1:男 2:女
	private String phone;		// 電話號碼
	private String idNo;		// 身份證號碼
	private String email;		// 電郵
	private String address;		// 地址
	private String educationLevel;		// 教育程度
	private String entryYear;		// 入職年月
	private String entryPosition;		// 入職職位
	private BigDecimal entryHourWage;		// 入職時薪 單位(港幣)
	private String presentPosition;		// 現時職位
	private BigDecimal presentHourWage;		// 現時時薪 單位(港幣)
	private BigDecimal industryExperience;		// 行業經驗 單位(年)
	private String contractFlag;		// 有否合約 字典枚舉 yes_no 1:是 0:否
	private BigDecimal accumulatedTeachingHours;		// 累計教導時數 單位(小時) 從點名課時數累計

	private List<SysCertificatesCoach> sysCertificatesCoachList = new ArrayList<SysCertificatesCoach>();

	public SysBaseCoach() {
		super();
	}

	public SysBaseCoach(String id){
		super(id);
	}

	@ExcelField(title="教練編碼", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ExcelField(title="中文名", align=2, sort=2)
	public String getNameCn() {
		return nameCn;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	@ExcelField(title="英文名", align=2, sort=3)
	public String getNameEn() {
		return nameEn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	@ExcelField(title="性別", dictType="sex_flag", align=2, sort=4)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title="電話號碼", align=2, sort=5)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@ExcelField(title="身份證號碼", align=2, sort=6)
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title="電郵", align=2, sort=7)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ExcelField(title="地址", align=2, sort=8)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@ExcelField(title="教育程度", align=2, sort=9)
	public String getEducationLevel() {
		return educationLevel;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public String getEntryYear() {
		return entryYear;
	}

	public void setEntryYear(String entryYear) {
		this.entryYear = entryYear;
	}

	@ExcelField(title="入職職位", align=2, sort=11)
	public String getEntryPosition() {
		return entryPosition;
	}

	public void setEntryPosition(String entryPosition) {
		this.entryPosition = entryPosition;
	}

	@ExcelField(title="入職時薪 單位(港幣)", align=2, sort=12)
	public BigDecimal getEntryHourWage() {
		return entryHourWage;
	}

	public void setEntryHourWage(BigDecimal entryHourWage) {
		this.entryHourWage = entryHourWage;
	}

	@ExcelField(title="現時職位", align=2, sort=13)
	public String getPresentPosition() {
		return presentPosition;
	}

	public void setPresentPosition(String presentPosition) {
		this.presentPosition = presentPosition;
	}

	@ExcelField(title="現時時薪 單位(港幣)", align=2, sort=14)
	public BigDecimal getPresentHourWage() {
		return presentHourWage;
	}

	public void setPresentHourWage(BigDecimal presentHourWage) {
		this.presentHourWage = presentHourWage;
	}

	@ExcelField(title="行業經驗 單位(年)", align=2, sort=15)
	public BigDecimal getIndustryExperience() {
		return industryExperience;
	}

	public void setIndustryExperience(BigDecimal industryExperience) {
		this.industryExperience = industryExperience;
	}

	@ExcelField(title="有否合約", dictType="yes_no", align=2, sort=16)
	public String getContractFlag() {
		return contractFlag;
	}

	public void setContractFlag(String contractFlag) {
		this.contractFlag = contractFlag;
	}

	@ExcelField(title="累計教導時數 單位(小時)", align=2, sort=17)
	public BigDecimal getAccumulatedTeachingHours() {
		return accumulatedTeachingHours;
	}

	public void setAccumulatedTeachingHours(BigDecimal accumulatedTeachingHours) {
		this.accumulatedTeachingHours = accumulatedTeachingHours;
	}

	public List<SysCertificatesCoach> getSysCertificatesCoachList() {
		return sysCertificatesCoachList;
	}

	public void setSysCertificatesCoachList(List<SysCertificatesCoach> sysCertificatesCoachList) {
		this.sysCertificatesCoachList = sysCertificatesCoachList;
	}

}