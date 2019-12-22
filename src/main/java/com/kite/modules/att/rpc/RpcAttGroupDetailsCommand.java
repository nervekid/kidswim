package com.kite.modules.att.rpc;

public class RpcAttGroupDetailsCommand {

	private static final long serialVersionUID = 1L;

	private String userId;

	/**1.[不可空] 分组编号*/
	private String groupCode;

	/**2.[不可空] 销售单id*/
	private String saleId;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getGroupCode() {
		return groupCode;
	}

	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	public String getSaleId() {
		return saleId;
	}

	public void setSaleId(String saleId) {
		this.saleId = saleId;
	}

}
