package com.kite.modules.att.rpc;

import java.util.List;

public class RpcAttGroupDetailsCommand {

	private static final long serialVersionUID = 1L;

	/**1.[不可空] 学员id*/
	private String userId;

	/**2.[不可空] 分组编号*/
	private String groupCode;

	/**3.[不可空] 销售单id*/
	private List<String> saleIds;

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

	public List<String> getSaleIds() {
		return saleIds;
	}

	public void setSaleIds(List<String> saleIds) {
		this.saleIds = saleIds;
	}

}
