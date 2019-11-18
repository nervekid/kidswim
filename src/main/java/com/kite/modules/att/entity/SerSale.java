/**
 * MouTai
 */
package com.kite.modules.att.entity;

import java.math.BigDecimal;
import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;

import com.kite.common.persistence.DataEntity;
import com.kite.common.utils.excel.annotation.ExcelField;

/**
 * 销售资料Entity
 * @author lyb
 * @version 2019-11-13
 */
public class SerSale extends DataEntity<SerSale> {

	private static final long serialVersionUID = 1L;
	private String code;		// 销售单编号 P+年月+流水码 例如:P201901000001 按照规则编码
	private String courseCode;		// 课程id
	private String studentId;		// 学员id
	private Double discount;		// 折扣 1 或者0.9，0.85计算
	private BigDecimal payAmount;		// 付款金额 按照课程收费标准及折扣进行计算 单位(港币)
	private String paidFlag;		// 是否付款 字典枚举 yes_no 1:是 0:否
	private Date paidDate;		// 付款日期
	private String paymentType;		// 付款方式

	public SerSale() {
		super();
	}

	public SerSale(String id){
		super(id);
	}

	@ExcelField(title="销售单编号 P+年月+流水码 例如:P201901000001 按照规则编码", align=2, sort=1)
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@ExcelField(title="学员id", align=2, sort=3)
	public String getStudentId() {
		return studentId;
	}

	@ExcelField(title="课程编号", align=2, sort=2)
	public String getCourseCode() {
		return courseCode;
	}

	public void setCourseCode(String courseCode) {
		this.courseCode = courseCode;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	@ExcelField(title="折扣 1 或者0.9，0.85计算", align=2, sort=4)
	public Double getDiscount() {
		return discount;
	}

	public void setDiscount(Double discount) {
		this.discount = discount;
	}

	@ExcelField(title="付款金额 按照课程收费标准及折扣进行计算 单位(港币)", align=2, sort=5)
	public BigDecimal getPayAmount() {
		return payAmount;
	}

	public void setPayAmount(BigDecimal payAmount) {
		this.payAmount = payAmount;
	}

	@ExcelField(title="是否付款 字典枚举 yes_no 1:是 0:否", align=2, sort=6)
	public String getPaidFlag() {
		return paidFlag;
	}

	public void setPaidFlag(String paidFlag) {
		this.paidFlag = paidFlag;
	}

	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
	@ExcelField(title="付款日期", align=2, sort=7)
	public Date getPaidDate() {
		return paidDate;
	}

	public void setPaidDate(Date paidDate) {
		this.paidDate = paidDate;
	}

	@ExcelField(title="付款方式", align=2, sort=8)
	public String getPaymentType() {
		return paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

}