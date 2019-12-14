/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 学员Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SysBaseStudent extends DataEntity<SysBaseStudent> {

	private static final long serialVersionUID = 1L;
	private String code;		// 学员编号 入学年份+月份+流水码 如:201901000001
	private String nameCn;		// 中文名
	private String nameEn;		// 英文名
	private String idNo;		// 身份证号码
	private String sex;		// 性别 字典枚举 sex_flag 1:男 2:女
	private String email;		// 电邮
	private String phone;		// 电话号码
	private Date birthday;		// 出生日期
	private String contactAddress;		// 联系地址
	private String attendingSchool;		// 就读学校
	private String grade;		// 年级
	private String studiedSwimFlag;		// 是否曾学习过游泳 字典枚举 yes_no 1:是 0:否
	private String studySwimmingOrgan;		// 习泳机构
	private String studiedSwimmingStyle;		// 已懂泳式 以,号分割
	private String drownedFlag;		// 是否曾遇溺 字典枚举 yes_no 1:是 0:否
	private String drownedAddressFlag; //遇溺地点 字典枚举 drowned_address_flag 1:泳池 0:海滩
	private Integer drownedAge;		// 预溺岁数
	private String longTermDisease;		// 长期病患
	private String longTermMedicine;		// 长期服药
	private String courseLevelFlag;		// 课程等级 字典枚举 courseLevel_flag 1:NA 2:BB 3:CA 4:CB 5:CC 6:AD 7:TA 8:TB
	private String contactPhone;		// 联系人号码
	private String contactRelationship;		// 联系人关系
	private String urgentPhone;		// 紧急联系人号码
	private String urgentRelationship;		// 紧急联系人关系
	private String guardianName;		// 监护人姓名
	private String guardianPhone;		// 监护人手机号码
	private String guardianIdNo;		// 监护人身份证号码
	private String guardianRelationship;		// 监护人关系
	private String facebook;		// facebook账号
	private String photoId;		// 照片id (暂缺,留位)


	public SysBaseStudent() {
		super();
	}

	public SysBaseStudent(String id){
		super(id);
	}

	@ExcelField(title="学员编码", align=2, sort=1)
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

	@ExcelField(title="身份证号码", align=2, sort=4)
	public String getIdNo() {
		return idNo;
	}

	public void setIdNo(String idNo) {
		this.idNo = idNo;
	}

	@ExcelField(title="性别", dictType="sex_flag", align=2, sort=5)
	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	@ExcelField(title="电邮", align=2, sort=6)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@ExcelField(title="电话号码", align=2, sort=7)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	@JsonFormat(pattern = "yyyy-MM-dd")
	@ExcelField(title="出生日期", align=2, sort=8)
	public Date getBirthday() {
		return birthday;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	@ExcelField(title="联系地址", align=2, sort=9)
	public String getContactAddress() {
		return contactAddress;
	}

	public void setContactAddress(String contactAddress) {
		this.contactAddress = contactAddress;
	}

	@ExcelField(title="就读学校", align=2, sort=10)
	public String getAttendingSchool() {
		return attendingSchool;
	}

	public void setAttendingSchool(String attendingSchool) {
		this.attendingSchool = attendingSchool;
	}

	@ExcelField(title="年级", align=2, sort=11)
	public String getGrade() {
		return grade;
	}

	public void setGrade(String grade) {
		this.grade = grade;
	}

	@ExcelField(title="是否曾学习过游泳", dictType="yes_no", align=2, sort=12)
	public String getStudiedSwimFlag() {
		return studiedSwimFlag;
	}

	public void setStudiedSwimFlag(String studiedSwimFlag) {
		this.studiedSwimFlag = studiedSwimFlag;
	}

	@ExcelField(title="习泳机构", align=2, sort=13)
	public String getStudySwimmingOrgan() {
		return studySwimmingOrgan;
	}

	public void setStudySwimmingOrgan(String studySwimmingOrgan) {
		this.studySwimmingOrgan = studySwimmingOrgan;
	}

	@ExcelField(title="已懂泳式 以,号分割", align=2, sort=14)
	public String getStudiedSwimmingStyle() {
		return studiedSwimmingStyle;
	}

	public void setStudiedSwimmingStyle(String studiedSwimmingStyle) {
		this.studiedSwimmingStyle = studiedSwimmingStyle;
	}

	@ExcelField(title="是否曾遇溺", dictType="yes_no", align=2, sort=15)
	public String getDrownedFlag() {
		return drownedFlag;
	}

	public void setDrownedFlag(String drownedFlag) {
		this.drownedFlag = drownedFlag;
	}

	@ExcelField(title="遇溺地点", dictType="drowned_address_flag", align=2, sort=16)
	public String getDrownedAddressFlag() {
		return drownedAddressFlag;
	}

	public void setDrownedAddressFlag(String drownedAddressFlag) {
		this.drownedAddressFlag = drownedAddressFlag;
	}

	@ExcelField(title="预溺岁数", align=2, sort=17)
	public Integer getDrownedAge() {
		return drownedAge;
	}

	public void setDrownedAge(Integer drownedAge) {
		this.drownedAge = drownedAge;
	}

	@ExcelField(title="长期病患", align=2, sort=18)
	public String getLongTermDisease() {
		return longTermDisease;
	}

	public void setLongTermDisease(String longTermDisease) {
		this.longTermDisease = longTermDisease;
	}

	@ExcelField(title="长期服药", align=2, sort=19)
	public String getLongTermMedicine() {
		return longTermMedicine;
	}

	public void setLongTermMedicine(String longTermMedicine) {
		this.longTermMedicine = longTermMedicine;
	}

	@ExcelField(title="课程等级", dictType="courseLevel_flag", align=2, sort=20)
	public String getCourseLevelFlag() {
		return courseLevelFlag;
	}

	public void setCourseLevelFlag(String courseLevelFlag) {
		this.courseLevelFlag = courseLevelFlag;
	}

	@ExcelField(title="联系人号码", align=2, sort=21)
	public String getContactPhone() {
		return contactPhone;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	@ExcelField(title="联系人关系", align=2, sort=22)
	public String getContactRelationship() {
		return contactRelationship;
	}

	public void setContactRelationship(String contactRelationship) {
		this.contactRelationship = contactRelationship;
	}

	@ExcelField(title="紧急联系人号码", align=2, sort=23)
	public String getUrgentPhone() {
		return urgentPhone;
	}

	public void setUrgentPhone(String urgentPhone) {
		this.urgentPhone = urgentPhone;
	}

	@ExcelField(title="紧急联系人关系", align=2, sort=24)
	public String getUrgentRelationship() {
		return urgentRelationship;
	}

	public void setUrgentRelationship(String urgentRelationship) {
		this.urgentRelationship = urgentRelationship;
	}

	@ExcelField(title="监护人姓名", align=2, sort=25)
	public String getGuardianName() {
		return guardianName;
	}

	public void setGuardianName(String guardianName) {
		this.guardianName = guardianName;
	}

	@ExcelField(title="监护人手机号码", align=2, sort=26)
	public String getGuardianPhone() {
		return guardianPhone;
	}

	public void setGuardianPhone(String guardianPhone) {
		this.guardianPhone = guardianPhone;
	}

	@ExcelField(title="监护人身份证号码", align=2, sort=27)
	public String getGuardianIdNo() {
		return guardianIdNo;
	}

	public void setGuardianIdNo(String guardianIdNo) {
		this.guardianIdNo = guardianIdNo;
	}

	@ExcelField(title="监护人关系", align=2, sort=28)
	public String getGuardianRelationship() {
		return guardianRelationship;
	}

	public void setGuardianRelationship(String guardianRelationship) {
		this.guardianRelationship = guardianRelationship;
	}

	@ExcelField(title="facebook账号", align=2, sort=29)
	public String getFacebook() {
		return facebook;
	}

	public void setFacebook(String facebook) {
		this.facebook = facebook;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

}