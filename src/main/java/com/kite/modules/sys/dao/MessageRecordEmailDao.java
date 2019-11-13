/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.MessageRecordEmail;

/**
 * 邮件发送情况记录DAO接口
 * @author wusida
 * @version 2019-10-24
 */
@MyBatisDao
public interface MessageRecordEmailDao extends CrudDao<MessageRecordEmail> {

    void updateMessageInfo(MessageRecordEmail messageRecordEmail);
}