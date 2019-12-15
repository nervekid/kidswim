package com.kite.modules.att.entity;

import java.util.Date;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

public class SysBaseStudentImport extends DataEntity<SysBaseStudentImport> {

	private static final long serialVersionUID = 1L;
	private String nameCn;		// 中文名
	private String nameEn;		// 英文名
	private String idNo;		// 身份證號碼
	private String sex;		// 性別 字典枚舉 sex_flag 1:男 2:女
	private String email;		// 電郵
	private String phone;		// 電話號碼
	private Date birthday;		// 出生日期
	private String contactAddress;		// 聯系地址
	private String attendingSchool;		// 就讀學校
	private String grade;		// 年級
	private String studiedSwimFlag;		// 是否曾學習過遊泳 字典枚舉 yes_no 1:是 0:否
	private String studySwimmingOrgan;		// 習泳機構
	private String studiedSwimmingStyle;		// 已懂泳式 以,號分割
	private String drownedFlag;		// 是否曾遇溺 字典枚舉 yes_no 1:是 0:否
	private String drownedAddressFlag; //遇溺地點 字典枚舉 drowned_address_flag 1:泳池 0:海灘
	private Integer drownedAge;		// 預溺歲數
	private String longTermDisease;		// 長期病患
	private String longTermMedicine;		// 長期服藥
	private String courseLevelFlag;		// 課程等級 字典枚舉 courseLevel_flag 1:NA 2:BB 3:CA 4:CB 5:CC 6:AD 7:TA 8:TB
	private String contactPhone;		// 聯系人號碼
	private String contactRelationship;		// 聯系人關系
	private String urgentPhone;		// 緊急聯系人號碼
	private String urgentRelationship;		// 緊急聯系人關系
	private String guardianName;		// 監護人姓名
	private String guardianPhone;		// 監護人手機號碼
	private String guardianIdNo;		// 監護人身份證號碼
	private String guardianRelationship;		// 監護人關系
	private String facebook;		// facebook賬號

	@ExcelField(title="中文名", align=2, sort=1)
	public String getNameCn() {
		return nameCn;
	}

	@ExcelField(title="英文名", align=2, sort=2)
	public String getNameEn() {
		return nameEn;
	}

	@ExcelField(title="身份證號碼", align=2, sort=3)
	public String getIdNo() {
		return idNo;
	}

	@ExcelField(title="性別 輸入男或者女", align=2, sort=4)
	public String getSex() {
		return sex;
	}

	@ExcelField(title="電郵", align=2, sort=5)
	public String getEmail() {
		return email;
	}

	@ExcelField(title="電話號碼", align=2, sort=6)
	public String getPhone() {
		return phone;
	}

	@ExcelField(title="出生日期", align=2, sort=7)
	public Date getBirthday() {
		return birthday;
	}

	@ExcelField(title="聯系地址", align=2, sort=8)
	public String getContactAddress() {
		return contactAddress;
	}

	@ExcelField(title="就讀學校", align=2, sort=9)
	public String getAttendingSchool() {
		return attendingSchool;
	}

	@ExcelField(title="年級", align=2, sort=10)
	public String getGrade() {
		return grade;
	}

	@ExcelField(title="是否曾學習過遊泳 輸入是或否", align=2, sort=11)
	public String getStudiedSwimFlag() {
		return studiedSwimFlag;
	}

	@ExcelField(title="習泳機構", align=2, sort=12)
	public String getStudySwimmingOrgan() {
		return studySwimmingOrgan;
	}

	@ExcelField(title="已懂泳式", align=2, sort=13)
	public String getStudiedSwimmingStyle() {
		return studiedSwimmingStyle;
	}

	@ExcelField(title="是否曾遇溺  輸入是或否", align=2, sort=14)
	public String getDrownedFlag() {
		return drownedFlag;
	}

	@ExcelField(title="是否曾遇溺  輸入泳池或海灘", align=2, sort=15)
	public String getDrownedAddressFlag() {
		return drownedAddressFlag;
	}

	@ExcelField(title="預溺歲數", align=2, sort=16)
	public Integer getDrownedAge() {
		return drownedAge;
	}

	@ExcelField(title="長期病患", align=2, sort=17)
	public String getLongTermDisease() {
		return longTermDisease;
	}

	@ExcelField(title="長期服藥", align=2, sort=18)
	public String getLongTermMedicine() {
		return longTermMedicine;
	}

	@ExcelField(title="課程等級 輸入:NA,BB,CA,CB,CC,AD,TA,TB", align=2, sort=19)
	public String getCourseLevelFlag() {
		return courseLevelFlag;
	}

	@ExcelField(title="聯系人號碼", align=2, sort=20)
	public String getContactPhone() {
		return contactPhone;
	}

	@ExcelField(title="聯系人關系", align=2, sort=21)
	public String getContactRelationship() {
		return contactRelationship;
	}

	@ExcelField(title="緊急聯系人號碼", align=2, sort=22)
	public String getUrgentPhone() {
		return urgentPhone;
	}

	@ExcelField(title="緊急聯系人關系", align=2, sort=23)
	public String getUrgentRelationship() {
		return urgentRelationship;
	}

	@ExcelField(title="監護人姓名", align=2, sort=24)
	public String getGuardianName() {
		return guardianName;
	}

	@ExcelField(title="監護人手機號碼", align=2, sort=25)
	public String getGuardianPhone() {
		return guardianPhone;
	}

	@ExcelField(title="監護人身份證號碼", align=2, sort=26)
	public String getGuardianIdNo() {
		return guardianIdNo;
	}

	@ExcelField(title="監護人關系", align=2, sort=26)
	public String getGuardianRelationship() {
		return guardianRelationship;
	}

	@ExcelField(title="facebook賬號", align=2, sort=27)
	public String getFacebook() {
		return facebook;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}
	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}
	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}
	public void setAttendingSchool(String attendingSchool) {
		this.attendingSchool = attendingSchool;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public void setStudiedSwimFlag(String studiedSwimFlag) {
		this.studiedSwimFlag = studiedSwimFlag;
	}
	public void setStudySwimmingOrgan(String studySwimmingOrgan) {
		this.studySwimmingOrgan = studySwimmingOrgan;
	}
	public void setStudiedSwimmingStyle(String studiedSwimmingStyle) {
		this.studiedSwimmingStyle = studiedSwimmingStyle;
	}
	public void setDrownedFlag(String drownedFlag) {
		this.drownedFlag = drownedFlag;
	}
	public void setDrownedAge(Integer drownedAge) {
		this.drownedAge = drownedAge;
	}
	public void setLongTermDisease(String longTermDisease) {
		this.longTermDisease = longTermDisease;
	}
	public void setLongTermMedicine(String longTermMedicine) {
		this.longTermMedicine = longTermMedicine;
	}
	public void setCourseLevelFlag(String courseLevelFlag) {
		this.courseLevelFlag = courseLevelFlag;
	}
	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}
	public void setContactRelationship(String contactRelationship) {
		this.contactRelationship = contactRelationship;
	}
	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}
	public void setUrgentRelationship(String urgentRelationship) {
		this.urgentRelationship = urgentRelationship;
	}
	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}
	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}
	public void setGuardianIdNo(String guardianIdNo) {
		this.guardianIdNo = guardianIdNo;
	}
	public void setGuardianRelationship(String guardianRelationship) {
		this.guardianRelationship = guardianRelationship;
	}
	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}
	public void setDrownedAddressFlag(String drownedAddressFlag) {
		this.drownedAddressFlag = drownedAddressFlag;
	}
}
