/**
 * MouTai
 */
package com.kite.modules.xw_tools.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.xw_tools.entity.XwPushmessage;

import java.util.List;

/**
 * 消息推送DAO接口
 * @author czh
 * @version 2017-12-11
 */
@MyBatisDao
public interface XwPushmessageDao extends CrudDao<XwPushmessage> {

    public List<XwPushmessage> findListById(XwPushmessage xwPushmessage);
	
}