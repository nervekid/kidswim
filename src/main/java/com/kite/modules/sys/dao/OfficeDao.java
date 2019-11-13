/**
 * Copyright &copy; 2015-2020 <a href="http://www.kite.org/">JeePlus</a> All rights reserved.
 */
package com.kite.modules.sys.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.kite.common.persistence.TreeDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.Office;
import com.kite.modules.sys.entity.OfficeGrade;

/**
 * 机构DAO接口
 * @author kite
 * @version 2014-05-16
 */
@MyBatisDao
public interface OfficeDao extends TreeDao<Office> {

	public Office getByCode(String code);

	/**
	 * 根据用户名称查询公司部门
	 * @param name
	 * @return
	 */
	public String getByName(@Param("name") String name, @Param("flag") String flag);



	public void updateTencentOfficeId(@Param("officeId") String officeId, @Param("tencentId") String tencentId, @Param("tencentParentOfficeId") String tencentParentOfficeId);


	/**
	 * 更新腾讯邮箱和kite平台组织架构关系表中腾讯邮箱部门表中上级部门集合数据
	 * @param tencentOfficeId
	 */
    public void updateTencentOfficeParentIds(Long tencentOfficeId);


	public void  deleteTencentOfficeId(String officeId);

	/**
	 * 更新企业微信与Kite平台部门关联关系表中上级部门集合数据
	 * @param qywxOfficeId
	 */
	public void updateQywxOfficeParentIds(Integer qywxOfficeId);

	void deleteQywxOfficeId(String id);

	public Long getTencentOfficeIsCreate(String officeId);


	public List<Office> findAllDataList(Office office);

	/**
	 * 根据组织架构类型，获取下一个sort
	 * @param organTag
	 * @return Integer
	 */
	public Integer findNextSort(String organTag);

	/**
	 　* @description: 获取某个人在某个职能线上的数据权限
	 　* @param [organTag, userId]
	 　* @return java.util.List<com.kite.modules.sys.entity.Office>
	 　* @throws
	 　* @author cxh
	 　* @date 2019/9/5 15:16
	 　*/
    List<Office> findListByOwer(@Param("organTag") String organTag,@Param("userId") String userId);

	public OfficeGrade  getOfficeGradeById(@Param("officeId") String officeId);

}
