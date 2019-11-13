/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.MessageRecord;

/**
 * 用户接收短信情况记录DAO接口
 * @author zhangtao
 * @version 2019-05-13
 */
@MyBatisDao
public interface MessageRecordDao extends CrudDao<MessageRecord> {


    void updateMessageInfo(MessageRecord messageRecord);
}