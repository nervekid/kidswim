/**
 * MouTai
 */
package com.kite.modules.sys.dao;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SessionSituation;

/**
 * 系统会话DAO接口
 * @author lyb
 * @version 2018-03-15
 */
@MyBatisDao
public interface SessionSituationDao extends CrudDao<SessionSituation> {

	SessionSituation findBySessionId(@Param("sessionId") String sId);
}