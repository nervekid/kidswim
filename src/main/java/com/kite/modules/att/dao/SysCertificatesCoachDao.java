/**
 * KITE
 */
package com.kite.modules.att.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.att.entity.SysCertificatesCoach;

/**
 * 教練員資格DAO接口
 * @author lyb
 * @version 2019-11-13
 */
@MyBatisDao
public interface SysCertificatesCoachDao extends CrudDao<SysCertificatesCoach> {

	/**
	 *
	 * @param coachId
	 * @return
	 */
	public List<SysCertificatesCoach> findSysCertificatesCoachListByCoachId(@Param("coachId")String coachId);

	/**
	 * 根據coachId刪除教練資格
	 * @param coachId
	 */
	public void deleteSysCertificatesCoachByCoachId(@Param("coachId") String coachId);

}