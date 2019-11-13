package com.kite.common.utils.verification;

import com.kite.common.utils.excel.ImportExcel;

/**
 * 校验接口
 * @author lyb
 *
 */
public interface BasicVerification {

	/**1. 校验*/
	 default  void check(ImportExcel ei){};

}
