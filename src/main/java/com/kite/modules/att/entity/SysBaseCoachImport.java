package com.kite.modules.att.entity;

import java.math.BigDecimal;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 教练员导入Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SysBaseCoachImport extends DataEntity<SysBaseCoachImport> {

	private static final long serialVersionUID = 1L;
	private String nameCn;		// 中文名
	private String nameEn;		// 英文名
	private String sex;		// 性别 字典枚举 sex_flag 1:男 2:女
	private String phone;		// 电话号码
	private String idNo;		// 身份证号码
	private String email;		// 电邮
	private String address;		// 地址
	private String educationLevel;		// 教育程度
	private String entryPosition;		// 入职职位
	private BigDecimal entryHourWage;		// 入职时薪 单位(港币)
	private String presentPosition;		// 现时职位
	private BigDecimal presentHourWage;		// 现时时薪 单位(港币)
	private BigDecimal industryExperience;		// 行业经验 单位(年)
	private String contractFlag;		// 有否合约 字典枚举 yes_no 1:是 0:否

	@ExcelField(title="中文名", align=2, sort=1)
	public String getNameCn() {
		return nameCn;
	}

	@ExcelField(title="英文名", align=2, sort=2)
	public String getNameEn() {
		return nameEn;
	}

	@ExcelField(title="性别 输入男或女", align=2, sort=3)
	public String getSex() {
		return sex;
	}

	@ExcelField(title="电话号码", align=2, sort=4)
	public String getPhone() {
		return phone;
	}

	@ExcelField(title="身份证号码", align=2, sort=5)
	public String getIdNo() {
		return idNo;
	}

	@ExcelField(title="电邮", align=2, sort=6)
	public String getEmail() {
		return email;
	}

	@ExcelField(title="联络地址", align=2, sort=7)
	public String getAddress() {
		return address;
	}

	@ExcelField(title="教育程度", align=2, sort=8)
	public String getEducationLevel() {
		return educationLevel;
	}

	@ExcelField(title="入职职位", align=2, sort=10)
	public String getEntryPosition() {
		return entryPosition;
	}

	@ExcelField(title="入职时薪 单位(港币)", align=2, sort=11)
	public BigDecimal getEntryHourWage() {
		return entryHourWage;
	}

	@ExcelField(title="现时职位", align=2, sort=12)
	public String getPresentPosition() {
		return presentPosition;
	}

	@ExcelField(title="现时时薪 单位(港币)", align=2, sort=13)
	public BigDecimal getPresentHourWage() {
		return presentHourWage;
	}

	@ExcelField(title="行业经验 单位(年)", align=2, sort=14)
	public BigDecimal getIndustryExperience() {
		return industryExperience;
	}

	@ExcelField(title="有否合约 是或否", align=2, sort=15)
	public String getContractFlag() {
		return contractFlag;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setEducationLevel(String educationLevel) {
		this.educationLevel = educationLevel;
	}

	public void setEntryPosition(String entryPosition) {
		this.entryPosition = entryPosition;
	}

	public void setEntryHourWage(BigDecimal entryHourWage) {
		this.entryHourWage = entryHourWage;
	}

	public void setPresentPosition(String presentPosition) {
		this.presentPosition = presentPosition;
	}

	public void setPresentHourWage(BigDecimal presentHourWage) {
		this.presentHourWage = presentHourWage;
	}

	public void setIndustryExperience(BigDecimal industryExperience) {
		this.industryExperience = industryExperience;
	}

	public void setContractFlag(String contractFlag) {
		this.contractFlag = contractFlag;
	}

}
