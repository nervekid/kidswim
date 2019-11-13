/**
 * KITE
 */
package com.kite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysTDataaccessuserentity;

/**
 * 多组织架构用户对应数据权限组分录DAO接口
 * @author lyb
 * @version 2018-11-12
 */
@MyBatisDao
public interface SysTDataaccessuserentityDao extends CrudDao<SysTDataaccessuserentity> {

	/**
	 * 根据数据权限对应用户关系表id查找组下所有的用户id
	 * @param parentId
	 * @return
	 */
	public List<String> findUseridsByParentId(@Param("parentId") String parentId);

	/**
	 * 根据用用户ID查找所有该用户已的数据权限对应户用户关系主表id
	 * @param userId
	 * @return
	 */
	public List<String> findAccessIdByUserId(@Param("userId") String userId);

	public void deleteByUser(@Param("parentId") String parentId, @Param("userId") String userId);

	int findIsExitByPraentIdAndUserId(@Param("parentId") String parentId, @Param("userId") String userId);
}