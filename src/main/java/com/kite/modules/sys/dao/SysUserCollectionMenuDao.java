/**
 * MouTai
 */
package com.kite.modules.sys.dao;

import com.kite.common.persistence.CrudDao;
import com.kite.common.persistence.annotation.MyBatisDao;
import com.kite.modules.sys.entity.SysUserCollectionMenu;

/**
 * 用户收藏菜单DAO接口
 * @author cxh
 * @version 2017-12-15
 */
@MyBatisDao
public interface SysUserCollectionMenuDao extends CrudDao<SysUserCollectionMenu> {


    public  SysUserCollectionMenu getDataByUserAndMenu(SysUserCollectionMenu sysUserCollectionMenu);

    void deleteDataByUserAndMenu(SysUserCollectionMenu menu);
}