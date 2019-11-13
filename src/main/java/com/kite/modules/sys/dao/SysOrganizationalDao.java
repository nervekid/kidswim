/**
 * KITE
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysOrganizational;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统组织架构DAO接口
 * @author lyb
 * @version 2018-10-18
 */
@MyBatisDao
public interface SysOrganizationalDao extends CrudDao<SysOrganizational> {

	/**
	 * 根据用户ID与组织架构标记找到上级部门
	 * @param userId
	 * @param organTag
	 * @return
	 */
	List<String> findOfiiceOneIdByUserIdAndOrganTag(@Param("userId") String userId, @Param("organTag") String organTag);

	/**
	 * 根据用户ID与组织架构标记找到所属部门信息
	 * @param userId
	 * @param organTag
	 * @return
	 */
	List<SysOrganizational> findOfficeIdByUserIdAndOrganTag(@Param("userId") String userId, @Param("organTag") String organTag);

	/**
	 * 取唯一值
	 * @param userId
	 * @param organTag
	 * @return
	 */
	SysOrganizational findOfficeIdByUserIdAndOrganTagOne(@Param("userId") String userId, @Param("organTag") String organTag);

	List<String> findUserIdByCompanyIdAndOrganTag(@Param("companyId") String companyId, @Param("organTag") String organTag);

	List<String> findUserNameByCompanyIdAndOrganTag(@Param("companyId") String companyId, @Param("organTag") String organTag);

	List<String> findUserIdByOfficeOneIdAndOrganTag(@Param("officeOneId") String officeOneId, @Param("organTag") String organTag);

	List<String> findUserNameByOfficeOneIdAndOrganTag(@Param("officeOneId") String officeOneId, @Param("organTag") String organTag);

	List<String> findOfficeNameByOfficeOneNameAndOrganTag(@Param("officeOneId") String officeOneId, @Param("organTag") String organTag);

	/**
	 * 根据userId进行逻辑删除
	 * @param userId
	 * @param delflag
	 */
	public void deleteByUserId(@Param("userId") String userId, @Param("delflag") String delflag);

	public SysOrganizational findEntityByUserIdAndOrganTag(@Param("userId") String userId, @Param("organTag") String organTag);

	public String findOfficeOneIdByUserIdAndOrganTag(@Param("userId") String userId, @Param("organTag") String organTag);

	public List<SysOrganizational>  findListOfficeOneIdAndName();

	/**
	 * 根据userId进行物理删除
	 * @param userId
	 * @return
	 */
	int deleteDataByUserId(@Param("userId") String userId);
}